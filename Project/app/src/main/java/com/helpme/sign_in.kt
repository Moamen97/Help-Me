package com.helpme

import Model.users
import android.media.tv.TvContract
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore

class sign_in : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        val gui = GUI(this);
        GUI.changeTypeFace(this.findViewById(R.id.Logo));
        var db: FirebaseFirestore = FirebaseFirestore.getInstance();


    }

    fun btnSignInClick(view: View) {

    }
}
