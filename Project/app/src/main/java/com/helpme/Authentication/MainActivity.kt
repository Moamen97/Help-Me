package com.helpme.Authentication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.helpme.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)
        val slideAdapter = SlideAdapter(this)
        val viewpager: ViewPager = findViewById(R.id.viewPager)
        viewpager.adapter = slideAdapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // val signIn = Intent(this@MainActivity, SignIn::class.java)
        //startActivity(signIn)

        val signIn = Intent(this@MainActivity, SignIn::class.java)
        startActivity(signIn)

        //overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
    }

}
