package com.helpme

import android.media.tv.TvContract
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class sign_in : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        val gui = GUI(this);
        GUI.changeTypeFace(this.findViewById(R.id.Logo));
    }
}
