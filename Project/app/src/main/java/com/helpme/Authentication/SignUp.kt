package com.helpme.Authentication

import Control.UserControl
import Model.profession
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import com.helpme.R
import Model.user

class SignUp : AppCompatActivity() {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                          //
    By Mohamed                                                                                 //
    */                                                                                        //
    val UserController: UserControl = UserControl.getInstance(null,this) //
    ///////////////////////////////////////////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val face = Typeface.createFromAsset(assets, "Fonts/Nabila.ttf");
        (findViewById<TextView>(R.id.Logo)).typeface = face;
    }

    fun btnRegisterClicked(view: View) {
     /*   val newUser = user(
                (findViewById<(AutoCompleteTextView)>(R.id.eMail)).text.toString(),
                (findViewById<(AutoCompleteTextView)>(R.id.firstName)).text.toString(),
                (findViewById<(AutoCompleteTextView)>(R.id.midName)).text.toString(),
                (findViewById<(AutoCompleteTextView)>(R.id.lastName)).text.toString(),
                "Male",
                "",
                (findViewById<(AutoCompleteTextView)>(R.id.password)).text.toString(),
                (findViewById<(AutoCompleteTextView)>(R.id.phoneNumber)).text.toString(),
                (findViewById<(AutoCompleteTextView)>(R.id.userName)).text.toString(),
                10,
                null,
                null,
                null,
                profession()
        );

        UserController.signUp(newUser)
        */
    }
    // added by mohamed
    fun Signup()
    {
        val intent = Intent(this, SignIn::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra("EXIT", true)
        startActivity(intent)
    }

    fun ShowToast(Msg:String)
    {
        Toast.makeText(this, Msg , Toast.LENGTH_LONG).show();
    }
}
