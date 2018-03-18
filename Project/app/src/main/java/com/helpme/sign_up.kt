package com.helpme

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class sign_up : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        var gui = GUI(this);
        GUI.changeTypeFace(this.findViewById(R.id.Logo));
        GUI.changeTypeFace(this.findViewById(R.id.sign_upText));
    }
}
