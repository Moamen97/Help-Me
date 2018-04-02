package com.helpme.Home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.helpme.Authentication.SignIn;
import com.helpme.DoctorFragment;
import com.helpme.EditProfile.AddProfession;
import com.helpme.EditProfile.EditProfile;
import com.helpme.EditProfile.MyProfile;
import com.helpme.Fragments.CarpenterFragment;
import com.helpme.Fragments.CookingFragment;
import com.helpme.Fragments.EngineerFragment;
import com.helpme.Fragments.MechanicFragment;
import com.helpme.Fragments.PlumberFragment;
import com.helpme.Fragments.ViewPagerAdapter;
import com.helpme.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Control.PostControl;
import Control.UserControl;
import Model.postData.post;

public class home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView userName;
    RecyclerView recycleMenu;
    RecyclerView.LayoutManager layoutManager;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private Dialog dialog;
    private UserControl UserController = UserControl.Companion.getInstance(null, null, this, null);
    private PostControl PostController = PostControl.Companion.getInstance(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Posts");
        dialog = new Dialog(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddPostFragment();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Here is the power
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutId);
        viewPager = (ViewPager) findViewById(R.id.viewPagerId);


        // adding fragments;
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragments(new DoctorFragment(), "Doctor");
        adapter.addFragments(new EngineerFragment(), "Engineer");
        adapter.addFragments(new CarpenterFragment(), "Carpenter");
        adapter.addFragments(new CookingFragment(), "Cooking");
        adapter.addFragments(new PlumberFragment(), "Plumber");
        adapter.addFragments(new MechanicFragment(), "Mechanic");

        // viewPager adapter set
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.MyProfile) {
            Intent intent = new Intent(this, MyProfile.class);
            startActivity(intent);
        } else if (id == R.id.EditMyInfo) {
            Intent intent = new Intent(this, EditProfile.class);
            startActivity(intent);
        } else if (id == R.id.MyPosts) {

        } else if (id == R.id.AddProfession) {
            Intent intent = new Intent(this, AddProfession.class);
            startActivity(intent);
        } else if (id == R.id.Logout) {
            finish();
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void ShowToast(String toastMsg) {
        Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show();
    }

    public void showAddPostFragment() {
        dialog.setContentView(R.layout.add_post);
        EditText postContent = dialog.findViewById(R.id.postContent);
        Button postButton = dialog.findViewById(R.id.postButton);
        Button plumberButton = dialog.findViewById(R.id.plumberButton);
        Button carpenterButton = dialog.findViewById(R.id.carpenterButton);
        Button mechanicButton = dialog.findViewById(R.id.mechanicButton);
        Button engineerButton = dialog.findViewById(R.id.engineerButton);
        Button cookingButton = dialog.findViewById(R.id.cookingButton);
        Button doctorButton = dialog.findViewById(R.id.doctorButton);
        TextView postTypeTextView = dialog.findViewById(R.id.postTypeTextView);
        TextView postContentTextView = dialog.findViewById(R.id.postContentTextView);
        postTypeTextView.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Nabila.ttf"));
        postContentTextView.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Nabila.ttf"));
        buttonsEffect(cookingButton, plumberButton, carpenterButton, mechanicButton, engineerButton, doctorButton, postButton, postContent, dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void buttonsEffect(final Button cookingButton, final Button plumberButton, final Button carpenterButton,
                              final Button mechanicButton, final Button engineerButton, final Button doctorButton,
                              final Button postButton, final EditText postContent, final Dialog dialog) {
        final StringBuilder selectedButton = new StringBuilder();
        selectedButton.append("");
        cookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookingButton.setEnabled(false);
                plumberButton.setEnabled(true);
                carpenterButton.setEnabled(true);
                mechanicButton.setEnabled(true);
                engineerButton.setEnabled(true);
                doctorButton.setEnabled(true);
                selectedButton.append("Cooking");
            }
        });
        plumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookingButton.setEnabled(true);
                plumberButton.setEnabled(false);
                carpenterButton.setEnabled(true);
                mechanicButton.setEnabled(true);
                engineerButton.setEnabled(true);
                doctorButton.setEnabled(true);
                selectedButton.delete(0, selectedButton.length());
                selectedButton.append("Plumber");
            }
        });
        carpenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookingButton.setEnabled(true);
                plumberButton.setEnabled(true);
                carpenterButton.setEnabled(false);
                mechanicButton.setEnabled(true);
                engineerButton.setEnabled(true);
                doctorButton.setEnabled(true);
                selectedButton.delete(0, selectedButton.length());
                selectedButton.append("Carpenter");

            }
        });
        mechanicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookingButton.setEnabled(true);
                plumberButton.setEnabled(true);
                carpenterButton.setEnabled(true);
                mechanicButton.setEnabled(false);
                engineerButton.setEnabled(true);
                doctorButton.setEnabled(true);
                selectedButton.delete(0, selectedButton.length());
                selectedButton.append("Mechanic");

            }
        });
        engineerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookingButton.setEnabled(true);
                plumberButton.setEnabled(true);
                carpenterButton.setEnabled(true);
                mechanicButton.setEnabled(true);
                engineerButton.setEnabled(false);
                doctorButton.setEnabled(true);
                selectedButton.delete(0, selectedButton.length());
                selectedButton.append("Engineer");

            }
        });
        doctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookingButton.setEnabled(true);
                plumberButton.setEnabled(true);
                carpenterButton.setEnabled(true);
                mechanicButton.setEnabled(true);
                engineerButton.setEnabled(true);
                doctorButton.setEnabled(false);
                selectedButton.delete(0, selectedButton.length());
                selectedButton.append("Doctor");
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = selectedButton.toString();
                ArrayList<String> comments = new ArrayList<>();
                String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                switch (s) {
                    case "Carpenter":
                        PostControl.Companion.addPost (new post(postContent.getText().toString(), "", timeStamp, "Carpenter", comments));
                        dialog.dismiss();
                        break;
                    case "Doctor":
                        PostControl.Companion.addPost(new post(postContent.getText().toString(), "", timeStamp, "Doctor", comments));
                        dialog.dismiss();
                        break;
                    case "Mechanic":
                        PostControl.Companion.addPost(new post(postContent.getText().toString(), "", timeStamp, "Mechanic", comments));
                        dialog.dismiss();
                        break;
                    case "Plumber":
                        PostControl.Companion.addPost(new post(postContent.getText().toString(), "", timeStamp, "Plumber", comments));
                        dialog.dismiss();
                        break;
                    case "Engineer":
                        PostControl.Companion.addPost(new post(postContent.getText().toString(), "", timeStamp, "Engineer", comments));
                        dialog.dismiss();
                        break;
                    case "Cooking":
                        PostControl.Companion.addPost(new post(postContent.getText().toString(), "", timeStamp, "Cooking", comments));
                        dialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
