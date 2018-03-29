package com.helpme

import Model.WorkshopTestingVersion
import Model.workshopAdapter
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_edit_workshop.*

class EditWorkshop : AppCompatActivity() {
    lateinit var  Adapter: workshopAdapter
    var ListOFWorkshops = ArrayList<WorkshopTestingVersion>()
    lateinit var  recyclerView:RecyclerView
    private lateinit var LayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_workshop)
        loadWorkshopsEditForms()
        recyclerView = findViewById(R.id.ListOfWorkShops)
        LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = LayoutManager


        Adapter = workshopAdapter(this, ListOFWorkshops)



        ListOfWorkShops.adapter = Adapter




        /*recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

                if (LayoutManager.findLastCompletelyVisibleItemPosition() == ListOFWorkshops.count() - 1) {
                    loadWorkshopsEditForms(movies[movies.count() - 1].id)
                }

            }
        })*/
    }

    private  fun loadWorkshopsEditForms() {

        ListOFWorkshops.add(WorkshopTestingVersion("", "", "", 10, ""))
        ListOFWorkshops.add(WorkshopTestingVersion("", "", "", 10, ""))
        ListOFWorkshops.add(WorkshopTestingVersion("", "", "", 10, ""))

    }


}
