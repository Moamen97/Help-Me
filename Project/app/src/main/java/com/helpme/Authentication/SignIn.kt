package com.helpme.Authentication

//import Model.Functionalities
import Control.PostControl
import Control.UserControl
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.helpme.Home.home
import com.helpme.R
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.util.*

class SignIn : AppCompatActivity() {
    /*
    By Mohamed
    */
    val UserController: UserControl = UserControl.getInstance(this)
    val PostController:PostControl = PostControl.getInstance()
    //////////////////////////////////////add_post////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        PostController.getPostsByType("Doctor")
        PostController.getPostsByType("Engineer")
        PostController.getPostsByType("Cooking")
        PostController.getPostsByType("Carpenter")
        PostController.getPostsByType("Mechanic")
        PostController.getPostsByType("Plumber")
        //startMagic()
    }

    fun btnSignUpClick(view: View) {
        val intent = Intent(this@SignIn, SignUp::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    fun btnSignInClick(view: View) {
        this.onTouchEvent(event = null)
        signInBtn.isEnabled = false
        signUpBtn.isEnabled = false
        val buttonTimer = Timer()
        buttonTimer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    signInBtn.isEnabled=true
                    signUpBtn.isEnabled=true
                }
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
       // intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
      //  intent.putExtra("EXIT", true)
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val view = this.currentFocus
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.hideSoftInputFromWindow(view.windowToken, 0)
        return true
    }
}

