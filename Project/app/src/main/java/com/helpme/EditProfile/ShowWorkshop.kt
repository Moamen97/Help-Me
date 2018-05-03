/*package com.helpme.EditProfile

import Model.Workshop
import Model.workshopAdapter
import Model.workshopListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.helpme.Comment.commentAdapterListener
import com.helpme.R
import kotlinx.android.synthetic.main.activity_edit_workshop.*

class ShowWorkshop : AppCompatActivity(), workshopListener {

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
            ListOFWorkshops.add(("", "", "", 10, ""))
        }
    }

}*/
