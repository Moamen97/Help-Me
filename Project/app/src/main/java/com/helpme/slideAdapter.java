package com.helpme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.zip.Inflater;

public class slideAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Typeface typeface;
    private String CarpenterDescription = "Our Program includes a lot of Carpenters that they can help you fix anything you need in the field of wood or you can invite them";
    private String PlumberDescription = "Our Program includes a lot of Plumbers that they can help you fix anything you need in the bathrooms or you can invite them";
    private String MechanicDescription = "Our Program includes a lot of Mechanics that they can help you fix anything you need in your car or you can invite them";


    // ourList of Images
    public int[] imgs = {R.drawable.carpenter, R.drawable.plumber,R.drawable.mechanic};
    public String[] Titles = {"Carpenter", "Plumber", "Mechanic"};
    public String[] Description = {CarpenterDescription, PlumberDescription, MechanicDescription};
    public int[] bkgnd = {
            R.drawable.gradient,
            R.drawable.gradient,
            R.drawable.gradient,
    };


    public slideAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide, container, false);
        LinearLayout layoutSlide = (LinearLayout) view.findViewById(R.id.slideLinearLayout);
        ImageView img = (ImageView) view.findViewById(R.id.slideImg);
        TextView tilte = (TextView) view.findViewById(R.id.titleSlide);
        TextView description = (TextView) view.findViewById(R.id.textDesciption);
        GUI gui = new GUI(this.context);
        GUI.changeTypeFace(tilte);

        tilte.setTextColor(Color.rgb(255, 255, 255));
        description.setTextColor(Color.rgb(255, 255, 255));
        layoutSlide.setBackgroundResource(bkgnd[position]);
        img.setImageResource(imgs[position]);
        tilte.setText(Titles[position]);
        description.setText(Description[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

}
