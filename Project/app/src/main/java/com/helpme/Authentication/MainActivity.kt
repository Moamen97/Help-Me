package com.helpme.Authentication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.widget.LinearLayout
import com.helpme.Comment.ShowComments
import com.helpme.R

class MainActivity : AppCompatActivity() {
    private var mDotsLayout: LinearLayout? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)
        var SlideAdapter = SlideAdapter(this);
        var viewpager: ViewPager = findViewById(R.id.viewPager);
        viewpager.adapter = SlideAdapter;
    }

    override fun onBackPressed() {
        super.onBackPressed()
       // val signIn = Intent(this@MainActivity, SignIn::class.java)
        //startActivity(signIn)

         val signIn = Intent(this@MainActivity, ShowComments::class.java)
        startActivity(signIn)

        //overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
    }

}
