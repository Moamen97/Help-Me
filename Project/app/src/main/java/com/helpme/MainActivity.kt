package com.helpme

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Welcome = Intent(this@MainActivity, Welcome_page::class.java);
        startActivity(Welcome);
        setContentView(R.layout.activity_main)
        var face = Typeface.createFromAsset(assets, "Fonts/Nabila.ttf");
        (this.findViewById<TextView>(R.id.Logo)).typeface = face;

        //var myDialog = Dialog(this);
        //myDialog.setContentView(R.layout.activity_edit_profile);

        //myDialog.window.setBackgroundDrawable(object : ColorDrawable(Color.TRANSPARENT) {});
        //myDialog.show();
    }

    fun btnSignUpClick(view: View) {
        val signUp = Intent(this@MainActivity, SignUp::class.java)
        startActivity(signUp)
    }

    fun btnSignInClick(view: View) {
        val signIn = Intent(this@MainActivity, SignIn::class.java)
        startActivity(signIn)
    }

}
