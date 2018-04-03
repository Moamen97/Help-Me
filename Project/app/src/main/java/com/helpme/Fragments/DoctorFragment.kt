package com.helpme

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

        val link = "https://scontent-mrs1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/21317730_1777007029006285_7633832584887544173_n.jpg?_nc_cat=0&efg=eyJpIjoidCJ9&oh=f6e3d8c614edc9f2e2a671043270be56&oe=5B29CBCF"

        postList.add(post("Moamen Hassan", "", "", "Doctor", arrayListOf(), link, "Moamen Hassan Attia", getRandomMaterialColor()))
        postList.add(post("Moamen Hassan", "", "", "Doctor", arrayListOf(), link, "Moamen Hassan Attia", getRandomMaterialColor()))
        postList.add(post("Moamen Hassan", "", "", "Doctor", arrayListOf(), link, "Moamen Hassan Attia", getRandomMaterialColor()))
        postList.add(post("Moamen Hassan", "", "", "Doctor", arrayListOf(), link, "Moamen Hassan Attia", getRandomMaterialColor()))
        postList.add(post("Moamen Hassan", "", "", "Doctor", arrayListOf(), link, "Moamen Hassan Attia", getRandomMaterialColor()))
        adapter.notifyDataSetChanged()
    }

    private fun getRandomMaterialColor(): Int {
        var returnColor = Color.GRAY
        val arrayId = resources.getIdentifier("shuffle", "array", context.packageName)
        if (arrayId != 0) {
            val colors = resources.obtainTypedArray(arrayId)
            val index = (Math.random() * colors.length()).toInt()
            returnColor = colors.getColor(index, Color.GRAY)
            colors.recycle()
        }
        return returnColor
    }
}