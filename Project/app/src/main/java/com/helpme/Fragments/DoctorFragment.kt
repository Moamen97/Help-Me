package com.helpme

import Common.mySelf
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
import java.util.ArrayList


class DoctorFragment : android.support.v4.app.Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.doctor_fragment, container, false)

        var recyclerView = view?.findViewById<RecyclerView>(R.id.post_recycler_view)
        var adapter = postAdapter(this.context!!, postList, POST_TYPE)
        var mLayoutManager = GridLayoutManager(this.context, 1)
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.itemAnimator = object : DefaultItemAnimator() {}
        var templist = PostController.getlist("Doctor")
        postList.clear()
        for (temp in templist)
        {
            temp.color = getRandomMaterialColor()
            postList.add(temp)
        }
        recyclerView?.adapter = adapter;
        PostController.getPostsByType("Doctor")
        PostController.getPostsByType("Engineer")
        PostController.getPostsByType("Cooking")
        PostController.getPostsByType("Carpenter")
        PostController.getPostsByType("Mechanic")
        PostController.getPostsByType("Plumber")
        PostController.getMyPosts()

        return view
    }

    private val POST_TYPE = R.drawable.engineer;
    private var postList = arrayListOf<post>()
    private var dialog: Dialog? = null;
    private var PostController:PostControl = PostControl.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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