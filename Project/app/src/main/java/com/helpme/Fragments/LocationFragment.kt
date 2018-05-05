package com.helpme

import Control.PostControl
import Model.postData.post
import Model.postData.postAdapter
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class LocationFragment : android.support.v4.app.Fragment() {

    private val POST_TYPE = R.drawable.engineer;
    private var postList = arrayListOf<post>()
    private var dialog: Dialog? = null;
    private var PostController: PostControl = PostControl.getInstance();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.plumber_fragment, container, false)
        var recyclerView = view!!.findViewById<RecyclerView>(R.id.post_recycler_view)
        var adapter = postAdapter(this.context!!, postList, POST_TYPE)
        var mLayoutManager = GridLayoutManager(this.context, 1)
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.itemAnimator = object : DefaultItemAnimator() {}
        var templist = PostController.getlist("Location")
        postList.clear()
        for (temp in templist) {
            temp.color = getRandomMaterialColor()
            postList.add(temp)
        }
        fun selector(p:post): Int = p.postRate
        postList.sortByDescending { selector(it) }
        adapter.notifyDataSetChanged()
        recyclerView?.adapter = adapter;

        return view
    }


    private fun getRandomMaterialColor(): Int {
        var returnColor = Color.GRAY
        val arrayId = resources.getIdentifier("shuffle", "array", context!!.packageName)
        if (arrayId != 0) {
            val colors = resources.obtainTypedArray(arrayId)
            val index = (Math.random() * colors.length()).toInt()
            returnColor = colors.getColor(index, Color.GRAY)
            colors.recycle()
        }
        return returnColor
    }
}