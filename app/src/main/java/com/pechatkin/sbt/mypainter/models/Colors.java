package com.pechatkin.sbt.mypainter.models;

import android.graphics.Color;

import com.pechatkin.sbt.mypainter.R;

public enum Colors {

    BLACK(Color.BLACK, R.string.color_black),
    RED(Color.RED, R.string.color_red),
    GREEN(Color.GREEN, R.string.color_green),
    BLUE(Color.BLUE, R.string.color_blue);

    public final int mColor;
    public final int mColorName;

    Colors(int color, int colorName){
        mColor = color;
        mColorName = colorName;
    }
}
