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
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /*
    By Mohamed
    */
    val UserController: UserControl = UserControl.getInstance(null,null,
            null,this)
    /////////////////////////////////////////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
    }
    fun btnEditWorkshopInfo(view:View)
    {
        var intent = Intent(this, EditWorkshop::class.java)
        startActivity(intent)
    }
    //added functions by mohamed start here
    fun btnSaveChanges(view:View)
    {
        var newname:String? = (findViewById<(EditText)>(R.id.editusernametext)).text.toString();
        var newemail:String? = (findViewById<(EditText)>(R.id.editemailtext)).text.toString();
        var newmobile:String?= (findViewById<(EditText)>(R.id.editphonetext)).text.toString();
        var newbdate:String? = (findViewById<(EditText)>(R.id.editbdatetext)).text.toString();

        if (newname == ""||newname == "Name")
            newname = null
        if (newemail == ""||newemail == "Email")
            newemail = null
        if (newmobile == ""||newmobile == "Mobile Number")
            newmobile = null
        if (newbdate == ""||newbdate == "Birth date")
            newbdate = null

        UserController.UpdateUserInfo(newname,newemail,newmobile,newbdate)
    }
    fun ShowToast(Msg:String)
    {
        Toast.makeText(this, Msg , Toast.LENGTH_LONG).show();
    }
}
