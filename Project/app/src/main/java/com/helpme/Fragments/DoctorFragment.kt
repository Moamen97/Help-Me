package com.helpme

import Model.postData.post
import Model.postData.postAdapter
import android.app.Dialog
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class DoctorFragment : android.support.v4.app.Fragment() {

    private val POST_TYPE = R.drawable.engineer;
    private var postList = arrayListOf<post>()
    private var dialog: Dialog? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.doctor_fragment, container, false)

        var recyclerView = view?.findViewById<RecyclerView>(R.id.post_recycler_view)
        var adapter = postAdapter(this.context, postList, POST_TYPE)
        var mLayoutManager = GridLayoutManager(this.context, 1)
        recyclerView?.layoutManager = mLayoutManager
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView?.itemAnimator = object : DefaultItemAnimator() {}
        preparePosts(adapter);
        recyclerView?.adapter = adapter;
        return view
    }

    private fun preparePosts(adapter: postAdapter) {
        val covers = arrayListOf(R.drawable.carpenter, R.drawable.carpenter,
                R.drawable.mechanic, R.drawable.mechanic, R.drawable.mechanic,
                R.drawable.mechanic, R.drawable.plumber, R.drawable.plumber,
                R.drawable.plumber, R.drawable.carpenter, R.drawable.carpenter
        )
        postList.add(post("Moamen Hassan", "", "", "Doctor", arrayListOf()))
        postList.add(post("Moamen Hassan", "", "", "Doctor", arrayListOf()))
        postList.add(post("Moamen Hassan", "", "", "Doctor", arrayListOf()))
        postList.add(post("Moamen Hassan", "", "", "Doctor", arrayListOf()))
        postList.add(post("Moamen Hassan", "", "", "Doctor", arrayListOf()))
        adapter.notifyDataSetChanged()
    }
}