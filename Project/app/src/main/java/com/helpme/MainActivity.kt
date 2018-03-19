package com.helpme

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Welcome = Intent(this@MainActivity, Welcome_page::class.java);
        startActivity(Welcome);
        setContentView(R.layout.activity_main)
        var gui = GUI(this);
        GUI.changeTypeFace(this.findViewById(R.id.Logo));

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
