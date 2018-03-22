package com.helpme

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore

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

        val db: FirebaseFirestore = FirebaseFirestore.getInstance();
        val citiesRef = db.collection("cities");
        val query = citiesRef.whereEqualTo("state", "CA")
        val capitalCities = db.collection("cities").whereEqualTo("capital", true)

        /* var col= FirebaseFirestore.getInstance().collection("users")
         col.whereEqualTo("user_name","shehab11").get()
                 .addOnSuccessListener(object:OnSuccessListener<QuerySnapshot>{
                     override fun onSuccess(p0: QuerySnapshot) {
                         println("aaaaaaaaaaaaaa\n" +
                                 "aaaaaaaaaaaa\n" +
                                 "aaaaaaaaaaa\n" +
                                 "aaaaaaaaaaaa\n" +
                                 "aaaaaaaaaaaaa\n" +
                                 p0.documents[0].getString("e-mail"))

                         p0.documents[0].reference.collection("Profession")
                                 .whereEqualTo("professional_category","ComputerEngeering")
                                 .get()
                                 .addOnSuccessListener(object:OnSuccessListener<QuerySnapshot>{
                                     override fun onSuccess(p1: QuerySnapshot) {
                                         println("aaaaaaaaaaaaaa\n" +
                                                 "aaaaaaaaaaaa\n" +
                                                 "aaaaaaaaaaa\n" +
                                                 "aaaaaaaaaaaa\n" +
                                                 "aaaaaaaaaaaaa\n" +
                                                 p1.documents[0].getString("professional_category"))
                                     }
                                 })

                     }
                 }

         )
         */
    }
}
