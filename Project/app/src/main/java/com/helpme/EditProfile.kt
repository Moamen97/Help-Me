package com.helpme

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class EditProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
    }
    fun btnEditWorkshopInfo(view:View)
    {
        var intent = Intent(this, EditWorkshop::class.java)
        startActivity(intent)
    }
}
