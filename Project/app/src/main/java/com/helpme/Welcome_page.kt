package com.helpme

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager

class Welcome_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_welcome_page)
        var SlideAdapter = slideAdapter(this);
        var viewpager: ViewPager = findViewById(R.id.viewPager);
        viewpager.adapter = SlideAdapter;
    }

}
