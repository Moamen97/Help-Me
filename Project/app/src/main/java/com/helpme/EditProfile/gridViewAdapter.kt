package com.helpme.EditProfile

import Model.Image
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.helpme.R

class gridViewAdabpter(con: Context, imgs: List<Image?>) : BaseAdapter() {
    var imgesList: List<Image?> = emptyList()
    lateinit var con: Context
    lateinit var inflator: LayoutInflater

    init {
        imgesList = imgs
        this.con = con
        inflator = (con as Activity).layoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = inflator.inflate(R.layout.row_layout_gridview, null)
        var imageView: ImageView = view.findViewById(R.id.workImage)
        if (imgesList[position] != null && imgesList[position]?.imageData != null) {
            imageView.setImageBitmap(imgesList[position]!!.imageData)
        }
        return view
    }

    override fun getItem(position: Int): Any? {
        return imgesList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return imgesList.size
    }

}