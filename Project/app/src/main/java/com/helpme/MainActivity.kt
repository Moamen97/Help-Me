package com.helpme

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gui = GUI(this);
        GUI.changeTypeFace(this.findViewById(R.id.Logo));

    }

    fun btnSignUpClick(view: View) {
        val signUp = Intent(this@MainActivity, sign_up::class.java)
        startActivity(signUp)
    }

    fun btnSignInClick(view: View) {
        val signIn = Intent(this@MainActivity, sign_in::class.java)
        startActivity(signIn)
    }
}
