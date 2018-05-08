package com.helpme.Comment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import com.helpme.R;

import Common.mySelf;
import Control.FeedbackControl;
import Control.PostControl;
import Control.UserControl;
import Model.postData.Feedback.*;
import Model.user;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShowFeedbacks extends AppCompatActivity implements android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener, FeedbackAdapterListener {

    private ArrayList<Model.postData.Feedback.Feedback> feedbacks = new ArrayList<>();
    private RecyclerView recyclerView;
    private FeedbackAdapter mAdapter = new FeedbackAdapter(this, this);
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    private Dialog addCommentDialog;
    private ProgressDialog mDialog;


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
        prepareMessages();
        mAdapter.refresh(feedbacks);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        actionModeCallback = new ActionModeCallback();
    }

    public void showAddCommentFragment() {
        addCommentDialog.setContentView(R.layout.add_comment);
        LinearLayout addCommentPanel = addCommentDialog.findViewById(R.id.addCommentPanel);
        final EditText commentContent = addCommentDialog.findViewById(R.id.commentContent);
        final ImageView firstStar = addCommentDialog.findViewById(R.id.firstStar);
        final ImageView secondStar = addCommentDialog.findViewById(R.id.secondStar);
        final ImageView thirdStar = addCommentDialog.findViewById(R.id.thirdStar);
        final ImageView fourthStar = addCommentDialog.findViewById(R.id.fourthStar);
        final ImageView fifthStar = addCommentDialog.findViewById(R.id.fifthStar);
        final Context context = this;
        final String[] rate = {"0"};
        firstStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstStar.setColorFilter(ContextCompat.getColor(context, R.color.md_yellow_600));
                secondStar.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_normal));
                thirdStar.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_normal));
                fourthStar.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_normal));
                fifthStar.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_normal));
                rate[0] = "1";
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
                rate[0] = "2";
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
                rate[0] = "3";
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
                rate[0] = "4";
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
                rate[0] = "5";
            }
        });
        final Button addFeedbackButton = addCommentDialog.findViewById(R.id.addFeedbackButton);
        addFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserControl userControl = UserControl.Companion.getInstance(null, null, null, null);
                Calendar calander = Calendar.getInstance();
                String timestamp = String.valueOf(System.currentTimeMillis());
                String message = commentContent.getText().toString();
                user User = userControl.getUser();
                String from = User.get_userName();
                String userImage = "";
                Feedback feedback = new Feedback(userImage, from, message, timestamp, false, rate[0], User.get_firstName(), User.get_midName(), User.get_lastName());
                FeedbackControl.Companion.addFeedback(feedback, ShowFeedbacks.this);
                PostControl PostController = PostControl.Companion.getInstance(0);
                switch (rate[0]) {
                    case "0":
                        userControl.updateBehaviourRate(User.get_behaveRate());
                        PostController.updateBehaviourRate(User.get_behaveRate());
                        break;
                    case "1":
                        userControl.updateBehaviourRate((User.get_behaveRate() + 1) / 2);
                        PostController.updateBehaviourRate((User.get_behaveRate() + 1) / 2);
                        break;
                    case "2":
                        userControl.updateBehaviourRate((User.get_behaveRate() + 2) / 2);
                        PostController.updateBehaviourRate((User.get_behaveRate() + 2) / 2);
                        break;
                    case "3":
                        userControl.updateBehaviourRate((User.get_behaveRate() + 3) / 2);
                        PostController.updateBehaviourRate((User.get_behaveRate() + 3) / 2);
                        break;
                    case "4":
                        userControl.updateBehaviourRate((User.get_behaveRate() + 4) / 2);
                        PostController.updateBehaviourRate((User.get_behaveRate() + 4) / 2);
                        break;
                    case "5":
                        userControl.updateBehaviourRate((User.get_behaveRate() + 5) / 2);
                        PostController.updateBehaviourRate((User.get_behaveRate() + 5) / 2);
                        break;
                    default:
                        break;
                }
                mAdapter.notifyDataSetChanged();
                addCommentDialog.dismiss();
            }
        });
        addCommentPanel.setBackgroundColor(getRandomMaterialColor());
        addCommentDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addCommentDialog.show();
    }


    private void prepareMessages() {
        String link = "https://scontent-mrs1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/21317730_1777007029006285_7633832584887544173_n.jpg?_nc_cat=0&efg=eyJpIjoidCJ9&oh=f6e3d8c614edc9f2e2a671043270be56&oe=5B29CBCF";
        swipeRefreshLayout.setRefreshing(true);
        FeedbackControl.Companion.prepareFeedback(ShowFeedbacks.this);
        swipeRefreshLayout.setRefreshing(false);
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

    public void refreshYBasha() {
        ArrayList<Feedback> tempList = FeedbackControl.Companion.getSortedFeedbacksList();
        Set<Feedback> set = new HashSet<>();

        if (tempList.size() == 0) {
            toastMessage("Nothing to show here");
        }
        filterFeedbacks(tempList);
        for (int i = 0; i < tempList.size(); ++i) {
            if (checkArrayContains(tempList.get(i))) continue;
            feedbacks.add(new Feedback(tempList.get(i).getUserImage(), tempList.get(i).getFrom(), tempList.get(i).getMessage(), tempList.get(i).getTimestamp(), tempList.get(i).getDeleteIt(), tempList.get(i).getRate(), tempList.get(i).getFirstName(), tempList.get(i).getMidName(), tempList.get(i).getLastName()));
            feedbacks.get(feedbacks.size() - 1).setFeedbackId(tempList.get(i).getFeedbackId());
        }
        set.addAll(feedbacks);
        feedbacks.clear();
        Object[] array = set.toArray();
        for (int i = 0; i < array.length; ++i)
            feedbacks.add((Feedback) array[i]);

        mAdapter.notifyDataSetChanged();
    }

    private Boolean checkArrayContains(Feedback feedback) {
        for (int i = 0; i < feedbacks.size(); ++i) {
            if (feedbacks.get(i) == feedback) return true;
        }
        return false;
    }

    private void filterFeedbacks(ArrayList<Feedback> TempList) {
        for (int i = 0; i < feedbacks.size(); ++i) {
            boolean found = false;
            for (int j = 0; j < TempList.size(); ++j)
                if (TempList.get(i) == feedbacks.get(i)) {
                    found = true;
                    break;
                }
            if (!found) feedbacks.remove(feedbacks.get(i));
        }
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
        if (message.getRate().equals("1")) message.setRate("0");
        else message.setRate("1");
        feedbacks.set(position, message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRateSecondStarClicked(int position) {
        Feedback message = feedbacks.get(position);
        if (message.getRate().equals("2")) message.setRate("0");
        else message.setRate("2");
        feedbacks.set(position, message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRateThirdStarClicked(int position) {
        Feedback message = feedbacks.get(position);
        if (message.getRate().equals("3")) message.setRate("0");
        else message.setRate("3");
        feedbacks.set(position, message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRateFourthStarClicked(int position) {
        Feedback message = feedbacks.get(position);
        if (message.getRate().equals("4")) message.setRate("0");
        else message.setRate("4");
        feedbacks.set(position, message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRateFifthStarClicked(int position) {
        Feedback message = feedbacks.get(position);
        if (message.getRate().equals("5")) message.setRate("0");
        else message.setRate("5");
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

    public void toastMessage(String Message) {
        Toast.makeText(this, Message, Toast.LENGTH_SHORT).show();
    }

}
