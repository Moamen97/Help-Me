package com.helpme.EditProfile

import Common.mySelf
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
import android.widget.Toast
import com.helpme.Comment.commentAdapterListener
import com.helpme.R
import kotlinx.android.synthetic.main.activity_edit_workshop.*

class AddWorkShop : AppCompatActivity(), workshopListener {
    var WorkshopController:WorkShopControl = WorkShopControl.getInstance()
    lateinit var Adapter: workshopAdapter
    var ListOFWorkshops = ArrayList<Workshop>()
    lateinit var recyclerView: RecyclerView
    var listener: commentAdapterListener? = null
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
            ListOFWorkshops.add(Workshop("", "", "","", ""))
        }
    }
    fun btnAddWorkShop(view: View)
    {
        var wname:String = (findViewById<(AutoCompleteTextView)>(R.id.WorkShopNameEditText)).text.toString();
        var wloc:String = (findViewById<(AutoCompleteTextView)>(R.id.WorkshopLocationEditText)).text.toString();
        var wprof:String = (findViewById<(AutoCompleteTextView)>(R.id.WorkshopProfessionEditText)).text.toString();
        var wnum:String = (findViewById<(AutoCompleteTextView)>(R.id.WorkshopPhoneNumberEditText)).text.toString();
        var oname = mySelf.get_userName()
        if (!wprof.contains("lumber")&&!wprof.contains("penter")&&!wprof.contains("ooking")&&!wprof.contains("octor")&&
                !wprof.contains("ngineer")&&!wprof.contains("echanic"))
        {
            Toast.makeText(this,"UnSupported Profession", Toast.LENGTH_LONG).show();

            return
        }
        try {
            val regex = Regex("01[0-2][0-9]{8}")
            require(wnum.matches(regex)) { "Error:wrong phone number format" }
        }catch (e:Exception)
        {
            Toast.makeText(this,"Error:wrong phone number format", Toast.LENGTH_LONG).show();
            return
        }
        var NewWorkShop = Workshop(wloc,wname,wnum,wprof,oname);
        WorkshopController.AddWorkShop(NewWorkShop);
        Toast.makeText(this,"Done", Toast.LENGTH_LONG).show();
    }

}
