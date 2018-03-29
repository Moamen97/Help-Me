package com.helpme.Authentication

import Model.Functionalities
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import com.helpme.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class SignIn : AppCompatActivity() {
    private var controller = Functionalities(this);
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val face = Typeface.createFromAsset(assets, "Fonts/Nabila.ttf");
        (findViewById<TextView>(R.id.Logo)).typeface = face;
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
        controller.signIn((findViewById<(AutoCompleteTextView)>(R.id.userName)).text.toString(),
                (findViewById<(AutoCompleteTextView)>(R.id.password)).text.toString()
        );

    }
}

