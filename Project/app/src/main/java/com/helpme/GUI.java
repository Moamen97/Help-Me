package com.helpme;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

import java.io.InputStream;

public class GUI {
    public static Typeface typeface;
    public GUI(Context myContext){
        AssetManager mngr = myContext.getAssets();
        this.typeface=Typeface.createFromAsset(mngr,"Fonts/Nabila.ttf");
    }

    public static void changeTypeFace(final TextView textView) {
        textView.setTypeface(typeface);
    }

}
