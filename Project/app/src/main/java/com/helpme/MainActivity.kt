package com.helpme

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var SlideAdapter = slideAdapter(this);
        var viewpager: ViewPager = findViewById(R.id.viewPager);
        viewpager.adapter = SlideAdapter;

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
