package com.helpme.Authentication

//import Model.Functionalities
import Control.PostControl
import Control.UserControl
import Model.postData.post
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
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.helpme.Home.home
import com.helpme.R
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.util.*

class SignIn : AppCompatActivity() {
    //facebook info
    var SfacebookID = ""
    var Sfname = ""
    var Smname = ""
    var Slname = ""
    var Semail = ""
    var Surl = ""
    var Sbirthday = ""
    //facebook configuration
    var callbackmanager: CallbackManager? = null
    /*
    By Mohamed
    */
    val UserController: UserControl = UserControl.getInstance(this)
    val PostController: PostControl = PostControl.getInstance()
    //////////////////////////////////////add_post////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //facebook Configuration
        callbackmanager = CallbackManager.Factory.create()
        ///////////////////////////////////////////////////
        setContentView(R.layout.activity_sign_in)
        PostController.getPostsByType("Doctor")
        PostController.getPostsByType("Engineer")
        PostController.getPostsByType("Cooking")
        PostController.getPostsByType("Carpenter")
        PostController.getPostsByType("Mechanic")
        PostController.getPostsByType("Plumber")
        //startMagic()
        /////////////////////////////////////////////////
        //facebook button
        login_button.setOnClickListener {
            faceBookInitialize()
        }
    }

    fun faceBookInitialize() {

        login_button.setReadPermissions("email")
        login_button.setReadPermissions("user_photos")
        login_button.setReadPermissions("public_profile")
        login_button.setReadPermissions("user_birthday")

        LoginManager.getInstance().registerCallback(callbackmanager!!, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                println("=========================onsuccess")
                val accessToken = AccessToken.getCurrentAccessToken()
                val request = GraphRequest.newMeRequest(accessToken) { `object`, response ->
                    println("===================JSON++" + `object`)

                    try {

                        if (`object`.has("id")) {
                            SfacebookID = `object`.getString("id")
                        }

                        if (`object`.has("first_name")) {
                            Sfname = `object`.getString("first_name")
                        }
                        if (`object`.has("middle_name")) {
                            Smname = `object`.getString("middle_name")
                        }
                        if (`object`.has("last_name")) {
                            Slname = `object`.getString("last_name")
                        }

                        if (`object`.has("email")) {
                            Semail = `object`.getString("email")
                        }
                        if (`object`.has("picture")) {
                            Surl = `object`.getJSONObject("picture").getJSONObject("data").getString("url")
                        }

                        if (`object`.has("birthday")) {
                            Sbirthday = `object`.getString("birthday")
                        }
                        UserController.CreateNewUserFB("$SfacebookID", "$Semail", Sfname + Smname + Slname + Sfname, Sfname + Smname + Slname + Sfname,
                                "$Sfname", "$Smname", "$Slname", "", "$Sbirthday", "", Sfname + Smname + Slname + Sfname)
                        signInBtn.isEnabled = false
                        signUpBtn.isEnabled = false
                        login_button.isEnabled = false
                        val buttonTimer = Timer()
                        buttonTimer.schedule(object : TimerTask() {
                            override fun run() {
                                runOnUiThread {
                                    signInBtn.isEnabled = true
                                    signUpBtn.isEnabled = true
                                    login_button.isEnabled = true
                                }
                            }
                        }, 5000)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                val parameters = Bundle()
                parameters.putString("fields", "id,first_name,last_name,middle_name,link,email,picture,gender, birthday")
                request.parameters = parameters
                request.executeAsync()

            }

            override fun onCancel() {
                //TODO Auto-generated method stub
                println("=========================onCancel")
                ShowToast("Canceled")
            }

            override fun onError(error: FacebookException) {
                //TODO Auto-generated method stub
                println("=========================onError" + error.toString())
                ShowToast("Error")
            }
        })
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
                    signInBtn.isEnabled = true
                    signUpBtn.isEnabled = true
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
        logo.typeface = face
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        callbackmanager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}

