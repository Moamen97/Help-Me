package com.helpme.EditProfile

import Common.mySelf
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.helpme.R

class MyProfile : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        val name=mySelf.firstName+" "+mySelf.lastName
        val NoOFPosts =mySelf.myPosts.size
        val Rate = mySelf.behaveRate.toString()
        val mail =mySelf.email
        val Number=mySelf.phoneNum
        val BirthDate=mySelf.birthDate

        val nameView=findViewById<TextView>(R.id.personName)
        val professionView=findViewById<TextView>(R.id.personProfession)
        val NoOfPostsView=findViewById<TextView>(R.id.personNoOfPosts)
        val RateView=findViewById<TextView>(R.id.personRate)
        val mailView=findViewById<TextView>(R.id.personEmail)
        val NumberView=findViewById<TextView>(R.id.personNumber)
        val birthdateView=findViewById<TextView>(R.id.personBirthDate)


        nameView.text=name
        professionView.text=""
        NoOfPostsView.text=NoOFPosts.toString()
        RateView.text=Rate.toString()
        mailView.text=mail
        NumberView.text=Number
        birthdateView.text=BirthDate


    }
}
