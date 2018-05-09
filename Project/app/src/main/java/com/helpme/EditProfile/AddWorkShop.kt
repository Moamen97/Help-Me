package com.helpme.EditProfile

import Common.mySelf
import Control.PostControl
import Control.WorkShopControl
import Model.Workshop
import Model.workshopAdapter
import Model.workshopListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import com.helpme.R
import kotlinx.android.synthetic.main.activity_edit_workshop.*

class AddWorkShop : AppCompatActivity(), workshopListener {
    var WorkshopController: WorkShopControl = WorkShopControl.getInstance(this)
    lateinit var Adapter: workshopAdapter
    var ListOFWorkshops = ArrayList<Workshop>()
    lateinit var recyclerView: RecyclerView
    var count: Int = 1;
    private lateinit var LayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_workshop)
        recyclerView = findViewById(R.id.ListOfWorkShops)
        LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = LayoutManager
        Adapter = workshopAdapter(this, ListOFWorkshops, this)
        recyclerView.adapter = Adapter
        loadWorkshopsEditForms();

    }

    override fun onClicK(position: Int) {
        count++;
    }

    private fun loadWorkshopsEditForms() {
        for (i in 1..count) {
            ListOFWorkshops.add(Workshop("", "", "", "", "", "", ""))
        }
    }

    override fun onBackPressed() {
        var PostController:PostControl = PostControl.getInstance()
        PostController.getPostsByType("Doctor")
        PostController.getPostsByType("Engineer")
        PostController.getPostsByType("Cooking")
        PostController.getPostsByType("Carpenter")
        PostController.getPostsByType("Mechanic")
        PostController.getPostsByType("Plumber")
        super.onBackPressed()
    }


    fun btnAddWorkShop(view: View) {
        var wname: String = (findViewById<(AutoCompleteTextView)>(R.id.WorkShopNameEditText)).text.toString();
        var wloc: String = (findViewById<(AutoCompleteTextView)>(R.id.WorkshopLocationEditText)).text.toString();
        var wnum: String = (findViewById<(AutoCompleteTextView)>(R.id.WorkshopPhoneNumberEditText)).text.toString();
        var wid = mySelf.get_userName()
        var oname = mySelf.get_userName()
        var ofname = mySelf.get_firstName() + " " + mySelf.get_midName() + " " + mySelf.get_lastName()
        var wprof: String?
        if ((findViewById<(RadioButton)>(R.id.radioButton)).isChecked) {
            wprof = (findViewById<(RadioButton)>(R.id.radioButton)).text.toString()
            wid +="_Clinic"
        } else if ((findViewById<(RadioButton)>(R.id.radioButton2)).isChecked) {
            wprof = (findViewById<(RadioButton)>(R.id.radioButton2)).text.toString()
            wid +="_Engineer"
        } else if ((findViewById<(RadioButton)>(R.id.radioButton3)).isChecked) {
            wprof = (findViewById<(RadioButton)>(R.id.radioButton3)).text.toString()
            wid +="_Plumber"
        } else if ((findViewById<(RadioButton)>(R.id.radioButton4)).isChecked) {
            wprof = (findViewById<(RadioButton)>(R.id.radioButton4)).text.toString()
            wid +="_Mechanic"
        } else if ((findViewById<(RadioButton)>(R.id.radioButton5)).isChecked) {
            wprof = (findViewById<(RadioButton)>(R.id.radioButton5)).text.toString()
            wid +="_Carpenter"
        } else if ((findViewById<(RadioButton)>(R.id.radioButton6)).isChecked) {
            wprof = (findViewById<(RadioButton)>(R.id.radioButton6)).text.toString()
            wid +="_Cooking"
        } else {
            Toast.makeText(this, "UnSupported Profession", Toast.LENGTH_LONG).show();
            return
        }
        try {
            val regex = Regex("01[0-2][0-9]{8}")
            require(wnum.matches(regex)) { "Error:wrong phone number format" }
        } catch (e: Exception) {
            Toast.makeText(this, "Error:wrong phone number format", Toast.LENGTH_LONG).show();
            return
        }
        var NewWorkShop = Workshop(wloc, wname, wnum, wprof, oname, wid, ofname);
        WorkshopController.AddWorkShop(NewWorkShop);
        var PostController = PostControl.getInstance()
        PostController.getPostsByType("Doctor")
        PostController.getPostsByType("Engineer")
        PostController.getPostsByType("Cooking")
        PostController.getPostsByType("Carpenter")
        PostController.getPostsByType("Mechanic")
        PostController.getPostsByType("Plumber")
    }

    fun ShowToast(Msg: String) {
        Toast.makeText(this, Msg, Toast.LENGTH_LONG).show();
        if(Msg == "Done")
        {
            super.onBackPressed()
        }
    }

}
