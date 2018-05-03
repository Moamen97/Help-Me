package Model.postData.Feedback;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.helpme.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.helpme.Comment.ShowFeedbacks;

import Model.Magic.CircleTransform;
import Model.Magic.FlipAnimator;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackViewHolder> {
    private Context mContext;
    private List<Feedback> comments = new ArrayList<>();
    private FeedbackAdapterListener listener;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private FeedbackViewHolder mHolder;
    // array used to perform multiple animation at once
    private SparseBooleanArray animationItemsIndex = new SparseBooleanArray();
    private boolean reverseAllAnimations = false;

    // index is used to animate only the selected row
    // dirty fix, find a better solution
    private static int currentSelectedIndex = -1;

    public FeedbackAdapter(Context mContext, FeedbackAdapterListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    public void refresh(List<Feedback> comments) {
        comments.clear();
        this.selectedItems.clear();
        this.animationItemsIndex.clear();
        this.comments = comments;
        this.notifyDataSetChanged();
    }

    @Override
    public FeedbackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_row, parent, false);
        return new FeedbackViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedbackViewHolder holder, int position) {
        mHolder = holder;
        Feedback message = comments.get(position);

        // displaying text view data
        holder.getFrom().setText(message.getFirstName() + " " + message.getMidName() + " " + message.getLastName());
        holder.getSubject().setText(message.getMessage());

        long TimeStamp = Long.parseLong(message.getTimestamp());
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(TimeStamp);
        final String timeString = new SimpleDateFormat("hh:mm a").format(cal.getTime());

        holder.getTimestamp().setText(timeString);

        // displaying the first letter of From in icon text
        holder.getIconText().setText(message.getFirstName().substring(0, 1));

        // change the row state to activated
        holder.itemView.setActivated(selectedItems.get(position, false));

        // apply click events
        applyClickEvents(holder, position, message);

        // handle comment mood
        //applyImportant(holder, message);

        applyRate(holder, message);
        // handle icon animation
        applyIconAnimation(holder, position);

        // display profile image
        applyProfilePicture(holder, message);

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


    private void applyClickEvents(FeedbackViewHolder holder, final int position, final Feedback message) {
        holder.getIconContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIconClicked(position);
            }
        });

        holder.getMessageContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMessageRowClicked(position);
            }
        });

        holder.getMessageContainer().
                setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        listener.onRowLongClicked(position);
                        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                        return true;
                    }
                });
    }

    private void applyProfilePicture(FeedbackViewHolder holder, Feedback Comment) {
        if (!TextUtils.isEmpty(Comment.getUserImage())) {
            Glide.with(mContext).load(Comment.getUserImage()).thumbnail(0.5f).crossFade().transform(new CircleTransform(mContext)).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.getImgProfile());
            holder.getImgProfile().setColorFilter(null);
            holder.getIconText().setVisibility(View.GONE);
        } else {
            holder.getImgProfile().setImageResource(R.drawable.bg_circle);
            holder.getImgProfile().setColorFilter(getRandomMaterialColor());
            holder.getIconText().setVisibility(View.VISIBLE);
        }
    }

    private void applyIconAnimation(FeedbackViewHolder holder, int position) {
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


//    private void applyImportant(FeedbackViewHolder holder, Feedback Comment) {
//        if (!Comment.isImportant() && !Comment.isRead()) {
//            holder.getIconImp().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
//            holder.getMoodSad().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
//        } else if (Comment.isImportant()) {
//            holder.getIconImp().setColorFilter(ContextCompat.getColor(mContext, R.color.md_green_600));
//            holder.getMoodSad().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
//        } else {
//            holder.getIconImp().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
//            holder.getMoodSad().setColorFilter(ContextCompat.getColor(mContext, R.color.md_red_500));
//        }
//    }

    private void applyRate(FeedbackViewHolder holder, Feedback Comment) {

        switch (Comment.getRate()) {
            case "0":
                holder.getFirstStar().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
                holder.getSecondStar().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
                holder.getThirdStar().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
                holder.getFourthStar().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
                holder.getFifthStar().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
                break;
            case "1":
                holder.getFirstStar().setColorFilter(ContextCompat.getColor(mContext, R.color.md_yellow_600));
                holder.getSecondStar().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
                holder.getThirdStar().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
                holder.getFourthStar().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
                holder.getFifthStar().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
                break;
            case "2":
                holder.getFirstStar().setColorFilter(ContextCompat.getColor(mContext, R.color.md_yellow_600));
                holder.getSecondStar().setColorFilter(ContextCompat.getColor(mContext, R.color.md_yellow_600));
                holder.getThirdStar().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
                holder.getFourthStar().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
                holder.getFifthStar().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
                break;
            case "3":
                holder.getFirstStar().setColorFilter(ContextCompat.getColor(mContext, R.color.md_yellow_600));
                holder.getSecondStar().setColorFilter(ContextCompat.getColor(mContext, R.color.md_yellow_600));
                holder.getThirdStar().setColorFilter(ContextCompat.getColor(mContext, R.color.md_yellow_600));
                holder.getFourthStar().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
                holder.getFifthStar().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
                break;
            case "4":
                holder.getFirstStar().setColorFilter(ContextCompat.getColor(mContext, R.color.md_yellow_600));
                holder.getSecondStar().setColorFilter(ContextCompat.getColor(mContext, R.color.md_yellow_600));
                holder.getThirdStar().setColorFilter(ContextCompat.getColor(mContext, R.color.md_yellow_600));
                holder.getFourthStar().setColorFilter(ContextCompat.getColor(mContext, R.color.md_yellow_600));
                holder.getFifthStar().setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
                break;
            case "5":
                holder.getFirstStar().setColorFilter(ContextCompat.getColor(mContext, R.color.md_yellow_600));
                holder.getSecondStar().setColorFilter(ContextCompat.getColor(mContext, R.color.md_yellow_600));
                holder.getThirdStar().setColorFilter(ContextCompat.getColor(mContext, R.color.md_yellow_600));
                holder.getFourthStar().setColorFilter(ContextCompat.getColor(mContext, R.color.md_yellow_600));
                holder.getFifthStar().setColorFilter(ContextCompat.getColor(mContext, R.color.md_yellow_600));
                break;
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
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public Feedback getSelectedItem(int position) {
        return comments.get(position);
    }

    public void makeMagicSlideUp(final int position, Animation anim) {
        if (getSelectedItem(position).getDeleteIt()) {
            mHolder.getMessageContainer().startAnimation(anim);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    removeData(position);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    public void removeData(int position) {
        comments.remove(position);
        resetCurrentIndex();
    }

    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }


}