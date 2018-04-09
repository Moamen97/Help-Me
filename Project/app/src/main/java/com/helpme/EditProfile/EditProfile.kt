package com.helpme.EditProfile

import Control.UserControl
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.helpme.R

class EditProfile : AppCompatActivity() {

    val UserController: UserControl = UserControl.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
    }
    fun btnEditWorkshopInfo(view:View)
    {
        var intent = Intent(this, EditWorkshop::class.java)
        startActivity(intent)
    }
    fun btnSaveChanges(view:View)
    {
        var newemail:String = (findViewById<(EditText)>(R.id.editemailtext)).text.toString();
        var newmobile:String= (findViewById<(EditText)>(R.id.editmobiletextbox)).text.toString();
        var newpassword:String = (findViewById<(EditText)>(R.id.editPasstextbox)).text.toString();
        var newimageid:String = (findViewById<(EditText)>(R.id.editimageidtextbox)).text.toString();


        if (newemail == "") {
            this.ShowToast("Email Cannot Be Empty")
            return
        }
        if (newmobile == ""||newmobile == "Mobile Number")
        {
            this.ShowToast("Mobile Cannot Be Empty")
            return
        }
     //   UserController.UpdateUserInfo(newemail,newmobile,newimageid,newpassword)
    }
    fun ShowToast(Msg:String)
    {
        Toast.makeText(this, Msg , Toast.LENGTH_LONG).show();
    }
}
