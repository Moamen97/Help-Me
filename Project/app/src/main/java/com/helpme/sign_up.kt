package com.helpme

import Model.add_user
import Model.users
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore


class sign_up : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        var gui = GUI(this);
        GUI.changeTypeFace(this.findViewById(R.id.Logo));
        GUI.changeTypeFace(this.findViewById(R.id.sign_upText));

        var db: FirebaseFirestore = FirebaseFirestore.getInstance();

        var newUser = users(R.id.userEmail.toString(), R.id.firstName.toString(), R.id.MidName.toString(), R.id.LastName.toString(), "M", "", R.id.userPassword.toString(), R.id.phoneNumber.toString(), R.id.userName.toString())

        var add = add_user(newUser, this);


    }
}
