package com.helpme

import Model.Functionalities
import Model.profession
import Model.user
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.TextView

class SignUp : AppCompatActivity() {
    private var controller = Functionalities(this);
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val face = Typeface.createFromAsset(assets, "Fonts/Nabila.ttf");
        (findViewById<TextView>(R.id.Logo)).typeface = face;
    }

    fun btnRegisterClicked(view: View) {
        val newUser = user(
                (findViewById<(AutoCompleteTextView)>(R.id.eMail)).text.toString(),
                (findViewById<(AutoCompleteTextView)>(R.id.firstName)).text.toString(),
                (findViewById<(AutoCompleteTextView)>(R.id.midName)).text.toString(),
                (findViewById<(AutoCompleteTextView)>(R.id.lastName)).text.toString(),
                "Male",
                "",
                (findViewById<(AutoCompleteTextView)>(R.id.password)).text.toString(),
                (findViewById<(AutoCompleteTextView)>(R.id.phoneNumber)).text.toString(),
                (findViewById<(AutoCompleteTextView)>(R.id.userName)).text.toString(),
                10,
                null,
                null,
                null,
                profession()
        );
        controller.signUp(newUser);
    }
}
