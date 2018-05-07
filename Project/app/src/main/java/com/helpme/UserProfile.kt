package com.helpme

import Control.PostControl
import Control.UserControl
import Control.WorkShopControl
import Model.user
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.helpme.EditProfile.gridViewAdabpter
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfile : AppCompatActivity() {
    var WorkShopController = WorkShopControl.getInstance(null)
    var PostController = PostControl.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        findViewById<(Button)>(R.id.ShowWorkShops).setVisibility(View.GONE)
        findViewById<(GridView)>(R.id.worksImagesGVu).setVisibility(View.GONE)


    }

    fun ShowInfo(view: View) {
        var User = WorkShopController.USER!!
        User.CheckSet_userName(WorkShopController.UWorkShop!!.OwnerName)
        findViewById<(Button)>(R.id.ShowWorkShops).setVisibility(View.GONE)
        findViewById<(Button)>(R.id.Showinfo).setVisibility(View.GONE)
        findViewById<(GridView)>(R.id.worksImagesGVu).setVisibility(View.VISIBLE)
        findViewById<(ImageView)>(R.id.userImage1).setVisibility(View.VISIBLE)



        worksImagesGVu.onItemClickListener = object: AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var popupMenu= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if(position%2==1)
                        PopupMenu(applicationContext,parent, Gravity.RIGHT)
                    else
                        PopupMenu(applicationContext,parent, Gravity.LEFT)

                } else {
                    PopupMenu(applicationContext,parent)
                }
            }
        }
        updateWorksImage(User)

        UserControl.getUnChangedInstance()
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
        try {
            userImage1.setImageBitmap(User.get_ProfileImage()!!.imageData)
        }catch (e:Exception){userImage1.setImageBitmap(null)}

      //  PostController.getPostOfWS()

    }
    fun updateWorksImage(u: user) {
            var gva = gridViewAdabpter(this@UserProfile, u.get_worksImages())
            var gridView: GridView = findViewById(R.id.worksImagesGVu)
            gridView.adapter = gva
        }
}


