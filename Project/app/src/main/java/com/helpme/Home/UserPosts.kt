package com.helpme.Home

import android.app.Application
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import Common.mySelf

import com.helpme.Authentication.SignIn
import com.helpme.EditProfile.AddProfession
import com.helpme.EditProfile.MyProfile
import com.helpme.Fragments.ViewPagerAdapter

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

import Control.PostControl
import Control.UserControl
import Control.WorkShopControl
import Model.postData.post
import com.facebook.Profile
import com.facebook.login.LoginManager
import com.helpme.*
import kotlinx.android.synthetic.main.message_list_row.view.*

class UserPosts : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    internal var userName: TextView? = null
    internal var recycleMenu: RecyclerView? = null
    internal var layoutManager: RecyclerView.LayoutManager? = null
    private var tabLayout: TabLayout? = null
    private val appBarLayout: AppBarLayout? = null
    private var viewPager: ViewPager? = null
    private var dialog: Dialog? = null
    private val UserController = UserControl.getInstance(null, null, null, null)
    private val PostController = PostControl.getInstance(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = "Posts"
        dialog = Dialog(this)
        /* val fab = findViewById<View>(R.id.fab) as FloatingActionButton
         fab.setOnClickListener { showAddPostFragment() }*/

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()


        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        // Here is the power
        tabLayout = findViewById<View>(R.id.tabLayoutId) as TabLayout
        viewPager = findViewById<View>(R.id.viewPagerId) as ViewPager


        // adding fragments;
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragments(MypostsFragment(), "Owner Posts")



        // viewPager adapter set
        viewPager!!.adapter = adapter
        tabLayout!!.setupWithViewPager(viewPager)

        val actionBar = supportActionBar
        actionBar!!.elevation = 0f
        val profile = Profile.getCurrentProfile()
        if (profile != null) {
            LoginManager.getInstance().logOut()

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.MyProfile) {
            val intent = Intent(this, MyProfile::class.java)
            startActivity(intent)
        } else if (id == R.id.MyPosts) {

        } else if (id == R.id.AddProfession) {
            val intent = Intent(this, AddProfession::class.java)
            startActivity(intent)
        } else if (id == R.id.Logout) {
            val intent = Intent(applicationContext, SignIn::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            //finish();
            //Intent intent2 = new Intent(this, SignIn.class);
            //startActivity(intent2);
        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
    fun  ShowProfile(view: View)
    {
        var workshopinfo = (view as TextView).text.toString()
        val workshopid = workshopinfo.substring(0, workshopinfo.indexOf("\n"))
        var WorkShopController:WorkShopControl = WorkShopControl.getInstance(null)
        WorkShopController.GetWorkShopByID(workshopid)
        val intent = Intent(this, UserProfile::class.java)
        startActivity(intent)
    }

    fun ShowToast(toastMsg: String) {
        Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show()
    }
/*
    fun showAddPostFragment() {
        dialog!!.setContentView(R.layout.add_post)
        val postContent = dialog!!.findViewById<EditText>(R.id.postContent)
        val postButton = dialog!!.findViewById<Button>(R.id.postButton)
        val plumberButton = dialog!!.findViewById<Button>(R.id.plumberButton)
        val carpenterButton = dialog!!.findViewById<Button>(R.id.carpenterButton)
        val mechanicButton = dialog!!.findViewById<Button>(R.id.mechanicButton)
        val engineerButton = dialog!!.findViewById<Button>(R.id.engineerButton)
        val cookingButton = dialog!!.findViewById<Button>(R.id.cookingButton)
        val doctorButton = dialog!!.findViewById<Button>(R.id.doctorButton)
        val postTypeTextView = dialog!!.findViewById<TextView>(R.id.postTypeTextView)
        val postContentTextView = dialog!!.findViewById<TextView>(R.id.postContentTextView)
        postTypeTextView.typeface = Typeface.createFromAsset(assets, "Fonts/Nabila.ttf")
        postContentTextView.typeface = Typeface.createFromAsset(assets, "Fonts/Nabila.ttf")
        buttonsEffect(cookingButton, plumberButton, carpenterButton, mechanicButton, engineerButton, doctorButton, postButton, postContent , dialog!!)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.show()
    }

    fun buttonsEffect(cookingButton: Button, plumberButton: Button, carpenterButton: Button,
                      mechanicButton: Button, engineerButton: Button, doctorButton: Button,
                      postButton: Button, postContent: EditText, dialog: Dialog) {
        val selectedButton = StringBuilder()
        selectedButton.append("")
        cookingButton.setOnClickListener {
            cookingButton.isEnabled = false
            plumberButton.isEnabled = true
            carpenterButton.isEnabled = true
            mechanicButton.isEnabled = true
            engineerButton.isEnabled = true
            doctorButton.isEnabled = true
            selectedButton.append("Cooking")
        }
        plumberButton.setOnClickListener {
            cookingButton.isEnabled = true
            plumberButton.isEnabled = false
            carpenterButton.isEnabled = true
            mechanicButton.isEnabled = true
            engineerButton.isEnabled = true
            doctorButton.isEnabled = true
            selectedButton.delete(0, selectedButton.length)
            selectedButton.append("Plumber")
        }
        carpenterButton.setOnClickListener {
            cookingButton.isEnabled = true
            plumberButton.isEnabled = true
            carpenterButton.isEnabled = false
            mechanicButton.isEnabled = true
            engineerButton.isEnabled = true
            doctorButton.isEnabled = true
            selectedButton.delete(0, selectedButton.length)
            selectedButton.append("Carpenter")
        }
        mechanicButton.setOnClickListener {
            cookingButton.isEnabled = true
            plumberButton.isEnabled = true
            carpenterButton.isEnabled = true
            mechanicButton.isEnabled = false
            engineerButton.isEnabled = true
            doctorButton.isEnabled = true
            selectedButton.delete(0, selectedButton.length)
            selectedButton.append("Mechanic")
        }
        engineerButton.setOnClickListener {
            cookingButton.isEnabled = true
            plumberButton.isEnabled = true
            carpenterButton.isEnabled = true
            mechanicButton.isEnabled = true
            engineerButton.isEnabled = false
            doctorButton.isEnabled = true
            selectedButton.delete(0, selectedButton.length)
            selectedButton.append("Engineer")
        }
        doctorButton.setOnClickListener {
            cookingButton.isEnabled = true
            plumberButton.isEnabled = true
            carpenterButton.isEnabled = true
            mechanicButton.isEnabled = true
            engineerButton.isEnabled = true
            doctorButton.isEnabled = false
            selectedButton.delete(0, selectedButton.length)
            selectedButton.append("Doctor")
        }

        postButton.setOnClickListener {
            val s = selectedButton.toString()
            val comments = ArrayList<String>()
            val posterinfo = mySelf
            val timeStamp = SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(Date())
            val NEWpost = post(postContent.text.toString(), "", timeStamp.toString(), "Doctor", comments,
                    posterinfo.get_imageID(), posterinfo.get_userName())
            when (s) {
                "Carpenter" -> {
                    NEWpost.edittype("Carpenter")
                    PostController.addPost(NEWpost)
                    dialog.dismiss()
                }
                "Doctor" -> {
                    NEWpost.edittype("Doctor")
                    PostController.addPost(NEWpost)
                    dialog.dismiss()
                }
                "Mechanic" -> {
                    NEWpost.edittype("Mechanic")
                    PostController.addPost(NEWpost)
                    dialog.dismiss()
                }
                "Plumber" -> {
                    NEWpost.edittype("Plumber")
                    PostController.addPost(NEWpost)
                    dialog.dismiss()
                }
                "Engineer" -> {
                    NEWpost.edittype("Engineer")
                    PostController.addPost(NEWpost)
                    dialog.dismiss()
                }
                "Cooking" -> {
                    NEWpost.edittype("Cooking")
                    PostController.addPost(NEWpost)
                    dialog.dismiss()
                }
                else -> {
                }
            }
        }

    }*/

}
