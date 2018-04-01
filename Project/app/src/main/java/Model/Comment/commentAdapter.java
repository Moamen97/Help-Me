package Model.Comment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.helpme.Comment.commentAdapterListener;
import com.helpme.R;

import java.util.ArrayList;
import java.util.List;

import Model.Magic.CircleTransform;
import Model.Magic.FlipAnimator;

public class commentAdapter extends RecyclerView.Adapter<commentViewHolder> {
    private Context mContext;
    private List<comment> comments;
    private commentAdapterListener listener;
    private SparseBooleanArray selectedItems;

    // array used to perform multiple animation at once
    private SparseBooleanArray animationItemsIndex;
    private boolean reverseAllAnimations = false;

    // index is used to animate only the selected row
    // dirty fix, find a better solution
    private static int currentSelectedIndex = -1;

    public commentAdapter(Context mContext, List<comment> comments, commentAdapterListener listener) {
        this.mContext = mContext;
        this.comments = comments;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        animationItemsIndex = new SparseBooleanArray();
    }

    @Override
    public commentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_row, parent, false);
        itemView.setBackgroundColor(getRandomMaterialColor());
        return new commentViewHolder(itemView);
    }

    private int getRandomMaterialColor() {
        int returnColor = Color.GRAY;
        int arrayId = mContext.getResources().getIdentifier("shuffle", "array", mContext.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = mContext.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }

    @Override
    public void onBindViewHolder(commentViewHolder holder, int position) {
        comment message = comments.get(position);

        // displaying text view data
        holder.getFrom().setText(message.getFrom());
        holder.getSubject().setText(message.getSubject());
        holder.getTimestamp().setText(message.getTimestamp());

        // displaying the first letter of From in icon text
        holder.getIconText().setText(message.getFrom().substring(0, 1));

        // change the row state to activated
        holder.itemView.setActivated(selectedItems.get(position, false));

        // change the font style depending on message read status
        applyReadStatus(holder, message);

        // handle message star
        applyImportant(holder, message);

        // handle icon animation
        applyIconAnimation(holder, position);

        // display profile image
        applyProfilePicture(holder, message);

        // apply click events
        applyClickEvents(holder, position);
    }

    private void applyClickEvents(commentViewHolder holder, final int position) {
        holder.getIconContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIconClicked(position);
            }
        });

        holder.getIconImp().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIconImportantClicked(position);
            }
        });

        holder.getMessageContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMessageRowClicked(position);
            }
        });

        holder.getMessageContainer().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onRowLongClicked(position);
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });
    }

    private void applyProfilePicture(commentViewHolder holder, comment Comment) {
        if (!TextUtils.isEmpty(Comment.getPicture())) {
            Glide.with(mContext).load(Comment.getPicture())
                    .thumbnail(0.5f)
                    .crossFade()
                    .transform(new CircleTransform(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.getImgProfile());
            holder.getImgProfile().setColorFilter(null);
            holder.getIconText().setVisibility(View.GONE);
        } else {
            holder.getImgProfile().setImageResource(R.drawable.bg_circle);
            holder.getImgProfile().setColorFilter(Comment.getColor());
            holder.getIconText().setVisibility(View.VISIBLE);
        }
    }

    private void applyIconAnimation(commentViewHolder holder, int position) {
        if (selectedItems.get(position, false)) {
            holder.getIconFront().setVisibility(View.GONE);
            resetIconYAxis(holder.getIconBack());
            holder.getIconBack().setVisibility(View.VISIBLE);
            holder.getIconBack().setAlpha(1);
            if (currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.getIconBack(), holder.getIconFront(), true);
                resetCurrentIndex();
            }
        } else {
            holder.getIconBack().setVisibility(View.GONE);
            resetIconYAxis(holder.getIconFront());
            holder.getIconFront().setVisibility(View.VISIBLE);
            holder.getIconFront().setAlpha(1);
            if ((reverseAllAnimations && animationItemsIndex.get(position, false)) || currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.getIconBack(), holder.getIconFront(), false);
                resetCurrentIndex();
            }
        }
    }


    // As the views will be reused, sometimes the icon appears as
    // flipped because older view is reused. Reset the Y-axis to 0
    private void resetIconYAxis(View view) {
        if (view.getRotationY() != 0) {
            view.setRotationY(0);
        }
    }

    public void resetAnimationIndex() {
        reverseAllAnimations = false;
        animationItemsIndex.clear();
    }

    @Override
    public long getItemId(int position) {
        return comments.get(position).getId();
    }

    private void applyImportant(commentViewHolder holder, comment Comment) {
        if (Comment.isImportant()) {
            holder.getIconImp().setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_star_black_24dp));
            holder.getIconImp().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_selected));
        } else {
            holder.getIconImp().setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_star_border_black_24dp));
            holder.getIconImp().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
        }
    }

    private void applyReadStatus(commentViewHolder holder, comment Comment) {
        if (Comment.isRead()) {
            holder.getFrom().setTypeface(null, Typeface.NORMAL);
            holder.getSubject().setTypeface(null, Typeface.NORMAL);
            holder.getFrom().setTextColor(ContextCompat.getColor(mContext, R.color.subject));
            holder.getSubject().setTextColor(ContextCompat.getColor(mContext, R.color.message));
        } else {
            holder.getFrom().setTypeface(null, Typeface.BOLD);
            holder.getSubject().setTypeface(null, Typeface.BOLD);
            holder.getFrom().setTextColor(ContextCompat.getColor(mContext, R.color.from));
            holder.getSubject().setTextColor(ContextCompat.getColor(mContext, R.color.subject));
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void toggleSelection(int pos) {
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
            animationItemsIndex.delete(pos);
        } else {
            selectedItems.put(pos, true);
            animationItemsIndex.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        reverseAllAnimations = true;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void removeData(int position) {
        comments.remove(position);
        resetCurrentIndex();
    }

    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }

    public interface MessageAdapterListener {
        void onIconClicked(int position);

        void onIconImportantClicked(int position);

        void onMessageRowClicked(int position);

        void onRowLongClicked(int position);
    }
}