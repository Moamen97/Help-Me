package com.helpme

import Control.PostControl
import Control.UserControl
import Control.WorkShopControl
import Model.user
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.helpme.EditProfile.gridViewAdabpter
import kotlinx.android.synthetic.main.activity_loading.*
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfile : AppCompatActivity() {
    var WorkShopController = WorkShopControl.getInstance(null)
    var PostController = PostControl.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        ShowInfo()
    }

    fun ShowInfo() {
        var User = WorkShopController.USER!!
        User.CheckSet_userName(WorkShopController.UWorkShop!!.OwnerName)
        findViewById<(Button)>(R.id.ShowWorkShops).setVisibility(View.GONE)
        findViewById<(Button)>(R.id.Showinfo).setVisibility(View.GONE)
        findViewById<(GridView)>(R.id.worksImagesGVu).setVisibility(View.VISIBLE)
        findViewById<(ImageView)>(R.id.userImage1).setVisibility(View.VISIBLE)
        updateWorksImage(User)

        val pname = findViewById<(TextView)>(R.id.personName)
        val wloc = findViewById<(TextView)>(R.id.WorkshopLocation)
        val wname = findViewById<(TextView)>(R.id.WorkshopName)
        val wnum = findViewById<(TextView)>(R.id.Workshopnumber)
        val wprof = findViewById<(TextView)>(R.id.WorkshopProfession)

        pname.text = "Owner Name : \n" + WorkShopController.UWorkShop!!.OwnerFullName
        wloc.text = "WorkShop Location : \n" + WorkShopController.UWorkShop!!.location
        wname.text = "WorkShop Name : \n" + WorkShopController.UWorkShop!!.workshopName
        wnum.text = "WorkShop Phone : \n" + WorkShopController.UWorkShop!!.workshopPhoneNum
        wprof.text = "WorkShop Profession : \n" + WorkShopController.UWorkShop!!.profession


        var handler = Handler()
        val r = object : Runnable {
            var done: Boolean = false
            var lastsize: Int = 0
            override fun run() {
                if (!done) {
                    if (User.get_ProfileImage() != null && User.get_ProfileImage()?.imageData != null) {
                        userImage1.setImageBitmap(User.get_ProfileImage()!!.imageData)
                        done = true
                    }
                }
                if (User!!.get_worksImages().size > lastsize) {
                    updateWorksImage(User)
                    lastsize = User!!.get_worksImages().size
                }
                handler.postDelayed(this, 500)
            }
        }
        handler.postDelayed(r, 500)

    }


    fun updateWorksImage(u: user) {
            var gva = gridViewAdabpter(this@UserProfile, u.get_worksImages())
            var gridView: GridView = findViewById(R.id.worksImagesGVu)
            gridView.adapter = gva
        }
}


