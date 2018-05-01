package com.helpme.Comment;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.helpme.R;

import Model.postData.Feedback.*;

import java.util.ArrayList;
import java.util.List;


public class ShowFeedbacks extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, FeedbackAdapterListener {
    private ArrayList<Feedback> feedbacks = new ArrayList<>();
    private RecyclerView recyclerView;
    private FeedbackAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    private Dialog addCommentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_activity);
        this.setFinishOnTouchOutside(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Moamen");
        addCommentDialog = new Dialog(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCommentFragment();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new FeedbackAdapter(this, feedbacks, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        actionModeCallback = new ActionModeCallback();
        prepareMessages();
    }

    public void showAddCommentFragment() {
        addCommentDialog.setContentView(R.layout.add_comment);
        LinearLayout addCommentPanel = addCommentDialog.findViewById(R.id.addCommentPanel);
        EditText commentContent = addCommentDialog.findViewById(R.id.commentContent);
        final ImageView firstStar = addCommentDialog.findViewById(R.id.firstStar);
        final ImageView secondStar = addCommentDialog.findViewById(R.id.secondStar);
        final ImageView thirdStar = addCommentDialog.findViewById(R.id.thirdStar);
        final ImageView fourthStar = addCommentDialog.findViewById(R.id.fourthStar);
        final ImageView fifthStar = addCommentDialog.findViewById(R.id.fifthStar);
        final Context context = this;

        firstStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstStar.setColorFilter(ContextCompat.getColor(context, R.color.md_yellow_600));
                secondStar.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_normal));
                thirdStar.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_normal));
                fourthStar.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_normal));
                fifthStar.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_normal));

            }
        });
        secondStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstStar.setColorFilter(ContextCompat.getColor(context, R.color.md_yellow_600));
                secondStar.setColorFilter(ContextCompat.getColor(context, R.color.md_yellow_600));
                thirdStar.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_normal));
                fourthStar.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_normal));
                fifthStar.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_normal));

            }
        });
        thirdStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstStar.setColorFilter(ContextCompat.getColor(context, R.color.md_yellow_600));
                secondStar.setColorFilter(ContextCompat.getColor(context, R.color.md_yellow_600));
                thirdStar.setColorFilter(ContextCompat.getColor(context, R.color.md_yellow_600));
                fourthStar.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_normal));
                fifthStar.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_normal));

            }
        });
        fourthStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstStar.setColorFilter(ContextCompat.getColor(context, R.color.md_yellow_600));
                secondStar.setColorFilter(ContextCompat.getColor(context, R.color.md_yellow_600));
                thirdStar.setColorFilter(ContextCompat.getColor(context, R.color.md_yellow_600));
                fourthStar.setColorFilter(ContextCompat.getColor(context, R.color.md_yellow_600));
                fifthStar.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_normal));

            }
        });
        fifthStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstStar.setColorFilter(ContextCompat.getColor(context, R.color.md_yellow_600));
                secondStar.setColorFilter(ContextCompat.getColor(context, R.color.md_yellow_600));
                thirdStar.setColorFilter(ContextCompat.getColor(context, R.color.md_yellow_600));
                fourthStar.setColorFilter(ContextCompat.getColor(context, R.color.md_yellow_600));
                fifthStar.setColorFilter(ContextCompat.getColor(context, R.color.md_yellow_600));

            }
        });


        addCommentPanel.setBackgroundColor(getRandomMaterialColor());
        addCommentDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addCommentDialog.show();
    }


    private void prepareMessages() {
        String link = "https://scontent-mrs1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/21317730_1777007029006285_7633832584887544173_n.jpg?_nc_cat=0&efg=eyJpIjoidCJ9&oh=f6e3d8c614edc9f2e2a671043270be56&oe=5B29CBCF";
        for (int i = 0; i < 200; ++i) {
            feedbacks.add(new Feedback(1, link, "Moamen Hassan Attia",
                    "Hello Salama ", "10:30 AM", getRandomMaterialColor(), false, 0));
        }
    }

    private int getRandomMaterialColor() {
        int returnColor = Color.GRAY;
        int arrayId = getResources().getIdentifier("shuffle", "array", getPackageName());

        if (arrayId != 0) {
            TypedArray colors = getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "Search...", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        // swipe refresh is performed, fetch the messages again
        prepareMessages();
    }

    @Override
    public void onIconClicked(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    @Override
    public void onMessageRowClicked(int position) {
        // verify whether action mode is enabled or not
        // if enabled, change the row state to activated
        if (mAdapter.getSelectedItems().size() > 0) {
            enableActionMode(position);
        } else {
            // read the message which removes bold from the row
            Feedback message = feedbacks.get(position);
            feedbacks.set(position, message);
            mAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "Read: " + message.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRowLongClicked(int position) {
        // long press is performed, enable action mode
        enableActionMode(position);
    }

    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItems().size();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    @Override
    public void onRateFirstStarClicked(int position) {
        Feedback message = feedbacks.get(position);
        if (message.getRate() == 1)
            message.setRate(0);
        else
            message.setRate(1);
        feedbacks.set(position, message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRateSecondStarClicked(int position) {
        Feedback message = feedbacks.get(position);
        if (message.getRate() == 2)
            message.setRate(0);
        else
            message.setRate(2);
        feedbacks.set(position, message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRateThirdStarClicked(int position) {
        Feedback message = feedbacks.get(position);
        if (message.getRate() == 3)
            message.setRate(0);
        else
            message.setRate(3);
        feedbacks.set(position, message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRateFourthStarClicked(int position) {
        Feedback message = feedbacks.get(position);
        if (message.getRate() == 4)
            message.setRate(0);
        else
            message.setRate(4);
        feedbacks.set(position, message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRateFifthStarClicked(int position) {
        Feedback message = feedbacks.get(position);
        if (message.getRate() == 5)
            message.setRate(0);
        else
            message.setRate(5);
        feedbacks.set(position, message);
        mAdapter.notifyDataSetChanged();
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);

            // disable swipe refresh if action mode is enabled
            swipeRefreshLayout.setEnabled(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    // delete all the selected messages
                    deleteMessages();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.clearSelections();
            swipeRefreshLayout.setEnabled(true);
            actionMode = null;
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.resetAnimationIndex();
                    // mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    // deleting the messages from recycler view
    private void deleteMessages() {
        Animation animation = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.slide_up);
        mAdapter.resetAnimationIndex();
        List<Integer> selectedItemPositions = mAdapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            mAdapter.removeData(selectedItemPositions.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }
}