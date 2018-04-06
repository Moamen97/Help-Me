package com.helpme.EditProfile

import Common.mySelf
import Control.UserControl
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.helpme.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class EditProfile : AppCompatActivity() {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /*
    By Mohamed
    */
  //  val UserController: UserControl = UserControl.getInstance(null,null, null,this)
    /////////////////////////////////////////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val firstname= mySelf.get_firstName()
        val midname =mySelf.get_midName()
        val lastname=mySelf.get_lastName()
        val mail = mySelf.get_email()
        val Number= mySelf.get_phoneNum()
        val BirthDate= mySelf.get_birthDate()
        val gender=mySelf.get_gender()
        val pass=mySelf.get_password()

        val firstnameView=findViewById<TextView>(R.id.editFirstNametextbox)
        val midnameView=findViewById<TextView>(R.id.editMidNametextbox)
        val lastnameView=findViewById<TextView>(R.id.editLastNametextbox)
        val genderView=findViewById<TextView>(R.id.editGendertextbox)
        val emailView=findViewById<TextView>(R.id.editEmailtextbox)
        val passView=findViewById<TextView>(R.id.editPassWordtextbox)
        val phonenumView=findViewById<TextView>(R.id.editPhonetextbox)
        val birthdateView=findViewById<TextView>(R.id.editBirthDatetextbox)

        firstnameView.text=firstname
        midnameView.text=midname
        lastnameView.text=lastname
        genderView.text=gender as String
        emailView.text=mail
        passView.text=pass
        phonenumView.text=Number
        birthdateView.text=BirthDate
    }
    fun btnEditWorkshopInfo(view:View)
    {
        var intent = Intent(this, EditWorkshop::class.java)
        startActivity(intent)
    }

    fun btnSaveChanges(view:View)
    {
        var newFirstName:String = (findViewById<(EditText)>(R.id.editFirstNametextbox)).text.toString();
        var newMidName:String = (findViewById<(EditText)>(R.id.editMidNametextbox)).text.toString();
        var newLastName:String = (findViewById<(EditText)>(R.id.editLastNametextbox)).text.toString();
        var newGender:String = (findViewById<(EditText)>(R.id.editGendertextbox)).text.toString();
        var newemail:String = (findViewById<(EditText)>(R.id.editEmailtextbox)).text.toString();
        var newpassword:String = (findViewById<(EditText)>(R.id.editPassWordtextbox)).text.toString();
        var newmobile:String= (findViewById<(EditText)>(R.id.editPhonetextbox)).text.toString();
        var newBirthDate:String = (findViewById<(EditText)>(R.id.editBirthDatetextbox)).text.toString();


        if (newemail == "") {
            this.ShowToast("Email Cannot Be Empty")
            return
        }
        if (newpassword == "")
        {
            this.ShowToast("Password Cannot Be Empty")
            return
        }
        if (newBirthDate!=""&&newBirthDate[2] != '/'&&newBirthDate[5] != '/')
        {
            this.ShowToast("Birth Date should be like dd/mm/yyyy")
            return
        }
        //UserController.UpdateUserInfo(newFirstName,newMidName,newLastName,newGender,newemail,newpassword,newmobile,newBirthDate)
    }
    fun ShowToast(Msg:String)
    {
        Toast.makeText(this, Msg , Toast.LENGTH_LONG).show();
    }
}
