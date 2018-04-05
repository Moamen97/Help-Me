package com.helpme.Authentication

//import Model.Functionalities
import Control.UserControl
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.helpme.Home.home
import com.helpme.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class SignIn : AppCompatActivity() {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /*                                                                                          //
    By Mohamed :V :V :V  34an ta5od drgat ma4y ma4y :V                                         //
    */                                                                                        //
    val UserController: UserControl = UserControl.getInstance(this)                //

    //////////////////////////////////////add_post////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        //startMagic()
    }


    fun btnSignUpClick(view: View) {
        val signUp = Intent(this@SignIn, SignUp::class.java)
        startActivity(signUp)
    }

    fun btnSignInClick(view: View) {
        signInBtn.isEnabled = false
        val buttonTimer = Timer()
        buttonTimer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread { signInBtn.setEnabled(true) }
            }
        }, 2000)
        UserController.Login(
                (findViewById<(AutoCompleteTextView)>(R.id.userName)).text.toString(),
                (findViewById<(AutoCompleteTextView)>(R.id.password)).text.toString()
        );

    }

    //added functions by mohamed start here
    fun LogIn() {
        val intent = Intent(this, home::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra("EXIT", true)
        startActivity(intent)
    }

    fun ShowToast(Msg: String) {
        Toast.makeText(this, Msg, Toast.LENGTH_LONG).show();
    }

    fun startMagic() {
        val face = Typeface.createFromAsset(assets, "Fonts/Nabila.ttf");
        val linearLayoutPanel = findViewById<LinearLayout>(R.id.singInPanel)
        val logo = findViewById<TextView>(R.id.Logo)
        logo.typeface = face;
        val textViewAnimation: Animation = AnimationUtils.loadAnimation(this.applicationContext, R.anim.sequential)
        val linearLayoutAnimation: Animation = AnimationUtils.loadAnimation(this.applicationContext, R.anim.slide_down)
        linearLayoutPanel.animation = linearLayoutAnimation
        linearLayoutPanel.visibility = View.GONE
        logo.startAnimation(textViewAnimation)
        textViewAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {}
            override fun onAnimationStart(p0: Animation?) {}
            override fun onAnimationEnd(p0: Animation?) {
                linearLayoutPanel.visibility = View.VISIBLE
                linearLayoutPanel.startAnimation(linearLayoutAnimation)
            }
        })
    }
}

