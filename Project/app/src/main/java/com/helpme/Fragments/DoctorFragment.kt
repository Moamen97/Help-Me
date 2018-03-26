package com.helpme.Fragments

import Model.MyDividerItemDecoration
import Model.postData.*;
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.helpme.R


class DoctorFragment : android.support.v4.app.Fragment() {

    private val POST_TYPE = R.drawable.doctor;

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.doctor_fragment, container, false)

        var postList = arrayListOf<post>();
        var recyclerView = view?.findViewById<RecyclerView>(R.id.post_recycler_view)
        var adapter = postAdapter(this.context, postList, POST_TYPE)
        var mLayoutManager = GridLayoutManager(this.context, 1)
        recyclerView?.layoutManager = mLayoutManager
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView?.addItemDecoration(object : MyDividerItemDecoration(this.context, LinearLayoutManager.VERTICAL, 16) {})
        recyclerView?.itemAnimator = object : DefaultItemAnimator() {}
        preparePosts(postList, adapter);
        recyclerView?.adapter = adapter;
        return view
    }

    private fun getRandomBackgroundColor(): Int {
        var returnColor = Color.GRAY;
        var arrayId: Int = resources.getIdentifier("mdcolor_", "array", activity.packageName)
        if (arrayId != 0) {
            var colors: TypedArray = resources.obtainTypedArray(arrayId);
            var index = random(0, colors.length())
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor
    }

    private fun random(from: Int, to: Int) = (Math.random() * (to - from) + from).toInt()

    private fun preparePosts(postList: ArrayList<post>, adapter: postAdapter) {

        val covers = arrayListOf(
                R.drawable.welcombackgroung, R.drawable.welcombackgroung,
                R.drawable.welcombackgroung, R.drawable.welcombackgroung, R.drawable.welcombackgroung,
                R.drawable.welcombackgroung, R.drawable.welcombackgroung, R.drawable.welcombackgroung,
                R.drawable.welcombackgroung, R.drawable.welcombackgroung, R.drawable.welcombackgroung
        )
        postList.add(post(arrayListOf("Moamen", "Hassan", "Attia"), "Doctor Posts", "August", covers[0]))
        postList.add(post(arrayListOf("Moamen", "Hassan", "Attia"), "Doctor Posts", "August", covers[1]))
        postList.add(post(arrayListOf("Moamen", "Hassan", "Attia"), "Doctor Posts", "August", covers[2]))
        postList.add(post(arrayListOf("Moamen", "Hassan", "Attia"), "Doctor Posts", "August", covers[3]))
        postList.add(post(arrayListOf("Moamen", "Hassan", "Attia"), "Doctor Posts", "August", covers[4]))
        postList.add(post(arrayListOf("Moamen", "Hassan", "Attia"), "Doctor Posts", "August", covers[5]))
        postList.add(post(arrayListOf("Moamen", "Hassan", "Attia"), "Doctor Posts", "August", covers[6]))
        postList.add(post(arrayListOf("Moamen", "Hassan", "Attia"), "Doctor Posts", "August", covers[7]))
        postList.add(post(arrayListOf("Moamen", "Hassan", "Attia"), "Doctor Posts", "August", covers[8]))
        postList.add(post(arrayListOf("Moamen", "Hassan", "Attia"), "Doctor Posts", "August", covers[9]))
        postList.add(post(arrayListOf("Moamen", "Hassan", "Attia"), "Doctor Posts", "August", covers[10]))
        adapter.notifyDataSetChanged()
    }
}
