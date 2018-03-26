package com.helpme.Authentication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.widget.LinearLayout
import com.helpme.R
import com.helpme.SlideAdapter

class Welcome_page : AppCompatActivity() {

    private var mDotsLayout: LinearLayout? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_welcome_page)
        var SlideAdapter = SlideAdapter(this);
        var viewpager: ViewPager = findViewById(R.id.viewPager);
        viewpager.adapter = SlideAdapter;
    }


}
