package com.helpme.EditProfile

import Model.WorkshopTestingVersion
import Model.postData.Feedback.FeedbackAdapterListener
import Model.workshopAdapter
import Model.workshopListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.helpme.R
import kotlinx.android.synthetic.main.activity_edit_workshop.*

class EditWorkshop : AppCompatActivity(), workshopListener {

    lateinit var Adapter: workshopAdapter
    var ListOFWorkshops = ArrayList<WorkshopTestingVersion>()
    lateinit var recyclerView: RecyclerView
    var listener: FeedbackAdapterListener? = null
    var count: Int = 2;
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
            ListOFWorkshops.add(WorkshopTestingVersion("", "", "", 10, ""))
        }
    }

}
