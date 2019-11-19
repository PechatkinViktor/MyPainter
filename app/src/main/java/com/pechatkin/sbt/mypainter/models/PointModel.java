package com.pechatkin.sbt.mypainter.models;

import android.graphics.Path;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

public class PointModel {

    private List<PointF> mPoints;
    private final int mColor;
    private final Types mType;
    private Path mPath;

    public PointModel(List<PointF> points, Types type, int color){
        mPoints = new ArrayList<>(points);
        mType = type;
        mColor = color;
        mPath = new Path();
        if(points.get(0) != null)
            mPath.moveTo(points.get(0).x, points.get(0).y);
    }

    public List<PointF> getPoints(){
        return mPoints;
    }

    public int getColor(){
        return mColor;
    }

    public Types getDrawType(){
        return mType;
    }

    public Path getPath(){
        return mPath;
    }
}
