package com.helpme

import Control.PostControl
import Control.WorkShopControl
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.helpme.Home.UserPosts

class UserProfile : AppCompatActivity() {
    var WorkShopController = WorkShopControl.getInstance(null)
    var PostController = PostControl.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        findViewById<(Button)>(R.id.ShowWorkShops).setVisibility(View.GONE)
    }

    fun ShowInfo(view:View)
    {
        findViewById<(Button)>(R.id.ShowWorkShops).setVisibility(View.VISIBLE)
        findViewById<(Button)>(R.id.Showinfo).setVisibility(View.GONE)

        val pname = findViewById<(TextView)>(R.id.personName)
        val wloc = findViewById<(TextView)>(R.id.WorkshopLocation)
        val wname = findViewById<(TextView)>(R.id.WorkshopName)
        val wnum = findViewById<(TextView)>(R.id.Workshopnumber)
        val wprof = findViewById<(TextView)>(R.id.WorkshopProfession)

        pname.text = "Owner Name : \n"+WorkShopController.WorkShop!!.OwnerFullName
        wloc.text = "WorkShop Location : \n"+WorkShopController.WorkShop!!.location
        wname.text = "WorkShop Name : \n"+WorkShopController.WorkShop!!.workshopName
        wnum.text = "WorkShop Phone : \n"+WorkShopController.WorkShop!!.workshopPhoneNum
        wprof.text = "WorkShop Profession : \n"+WorkShopController.WorkShop!!.profession
        PostController.getPostOfWS()

    }
    fun ShowWS(view: View)
    {
        val intent = Intent(this, UserPosts::class.java)
        startActivity(intent)
    }
}
