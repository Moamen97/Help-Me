package com.helpme

import Control.PostControl
import Control.WorkShopControl
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class UserProfile : AppCompatActivity() {
    var WorkShopController = WorkShopControl.getInstance(null)
    var PostController = PostControl.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        findViewById<(Button)>(R.id.ShowWorkShops).setVisibility(View.GONE)

    }

    fun ShowInfo(view: View) {
        findViewById<(Button)>(R.id.ShowWorkShops).setVisibility(View.GONE)
        findViewById<(Button)>(R.id.Showinfo).setVisibility(View.GONE)

        val pname = findViewById<(TextView)>(R.id.personName)
        val wloc = findViewById<(TextView)>(R.id.WorkshopLocation)
        val wname = findViewById<(TextView)>(R.id.WorkshopName)
        val wnum = findViewById<(TextView)>(R.id.Workshopnumber)
        val wprof = findViewById<(TextView)>(R.id.WorkshopProfession)

        pname.text = "Owner Name : \n"+WorkShopController.UWorkShop!!.OwnerFullName
        wloc.text = "WorkShop Location : \n"+WorkShopController.UWorkShop!!.location
        wname.text = "WorkShop Name : \n"+WorkShopController.UWorkShop!!.workshopName
        wnum.text = "WorkShop Phone : \n"+WorkShopController.UWorkShop!!.workshopPhoneNum
        wprof.text = "WorkShop Profession : \n"+WorkShopController.UWorkShop!!.profession

      //  PostController.getPostOfWS()

    }

  /*  fun ShowWS(view: View) {
        val intent = Intent(this, UserPosts::class.java)
        startActivity(intent)
    }*/
}
