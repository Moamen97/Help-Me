package com.helpme

import Model.Functionalities
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.TextView

class SignIn : AppCompatActivity() {
    private var controller = Functionalities(this);
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val face = Typeface.createFromAsset(assets, "Fonts/Nabila.ttf");
        (findViewById<TextView>(R.id.Logo)).typeface = face;
    }

    fun btnSignInClick(view: View) {
        controller.signIn(
                (findViewById<(AutoCompleteTextView)>(R.id.userName)).text.toString(),
                (findViewById<(AutoCompleteTextView)>(R.id.password)).text.toString()
        );
    }
}

