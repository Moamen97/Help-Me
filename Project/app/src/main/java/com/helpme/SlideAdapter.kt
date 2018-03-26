package com.helpme

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView


class SlideAdapter(var context: Context) : PagerAdapter() {

    private var CarpenterDescription: String = "Our Program includes a lot of Carpenters that they can help you fix anything you need in the field of wood or you can invite them";
    private var PlumberDescription: String = "Our Program includes a lot of Plumbers that they can help you fix anything you need in the bathrooms or you can invite them";
    private var MechanicDescription: String = "Our Program includes a lot of Mechanics that they can help you fix anything you need in your car or you can invite them";

    private val imgs = arrayListOf<Int>(R.drawable.carpenter, R.drawable.plumber, R.drawable.mechanic)
    private val Titles = arrayListOf<String>("Carpenter", "Plumber", "Mechanic")
    private val Description = arrayListOf<String>(CarpenterDescription, PlumberDescription, MechanicDescription)
    private val bkgnd = arrayListOf<Int>(R.drawable.gradient, R.drawable.gradient, R.drawable.gradient)


    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slide, container, false)
        val layoutSlide = view.findViewById<LinearLayout>(R.id.slideLinearLayout)
        val img = view.findViewById<ImageView>(R.id.slideImg)
        val title = view.findViewById<TextView>(R.id.titleSlide)
        val description = view.findViewById<TextView>(R.id.textDesciption)

        title.typeface = Typeface.createFromAsset(context.assets, "Fonts/Nabila.ttf");
        title.setTextColor(Color.rgb(255, 255, 255))
        description.setTextColor(Color.rgb(255, 255, 255))
        layoutSlide.setBackgroundResource(bkgnd[position])
        img.setImageResource(imgs[position])
        title.text = Titles[position]
        description.text = Description[position]
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as LinearLayout
    }

    override fun getCount(): Int {
        return imgs.size
    }
}