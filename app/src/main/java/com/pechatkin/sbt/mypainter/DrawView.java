package com.pechatkin.sbt.mypainter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.pechatkin.sbt.mypainter.models.PointModel;
import com.pechatkin.sbt.mypainter.models.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class DrawView extends View {

    private static final float STROKE_WIDTH = 10f;

    private Paint mPaint = new Paint();
    private Paint mBackgroundPaint = new Paint();
    private Path mPath = new Path();

    private int mCurrentColor = Color.BLACK;
    private Types mCurrentType;

    private List<PointModel> mPointModels = new ArrayList<>();

    public DrawView(Context context) {
        super(context, null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUpPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(PointModel model: mPointModels) {
            List<PointF> points = model.getPoints();
            mPaint.setColor(model.getColor());
            switch (model.getDrawType()) {
                case RECT:
                    canvas.drawRect(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y, mPaint);
                    break;
                case LINE:
                    canvas.drawLine(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y, mPaint);
                    break;
                case PATH:
                    canvas.drawPath(model.getPath(), mPaint);
                    break;
                case POINT:
                    canvas.drawPoint(points.get(0).x, points.get(0).y, mPaint);
                    break;
                case MULTITOUCH:
                    if(points.size() == 1) {
                        canvas.drawPoint(points.get(0).x, points.get(0).y, mPaint);
                    } else
                        for (int i = 0; i < points.size(); i++) {
                            float x = points.get(i).x;
                            float y = points.get(i).y;
                            float nextX = (i+1 == points.size())? points.get(0).x : points.get(i+1).x;
                            float nextY = (i+1 == points.size())? points.get(0).y : points.get(i+1).y;
                            canvas.drawLine(x, y, nextX, nextY, mPaint);
                        }
                     break;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                actionDown(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                actionMove(x, y, event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if(mCurrentType == Types.MULTITOUCH)
                    actionPointerDown(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            default:
                return super.onTouchEvent(event);
        }

        invalidate();
        return true;
    }

    private void actionPointerDown(MotionEvent event) {
        int pointerId = event.getPointerId(event.getActionIndex());
        List<PointF> points = mPointModels.get(mPointModels.size()-1).getPoints();
        if(points.size() == pointerId){
            points.add(new PointF());
        }
        PointF point = points.get(pointerId);
        point.x = event.getX(event.getActionIndex());
        point.y = event.getY(event.getActionIndex());
    }

    private void actionMove(float x, float y, MotionEvent event) {
        List<PointF> points = mPointModels.get(mPointModels.size()-1).getPoints();
        switch (mCurrentType){
            case LINE:
            case RECT:
                points.get(1).x = x;
                points.get(1).y = y;
            case POINT:
                break;
            case PATH:
                mPointModels.get(mPointModels.size()-1).getPath().lineTo(x, y);
                break;
            case MULTITOUCH:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    int id = event.getPointerId(i);
                    PointF point = points.get(id);
                    point.x = event.getX(i);
                    point.y = event.getY(i);
                }
                break;
        }
    }

    private void actionDown(float x, float y) {

        List<PointF> points = null;
        switch (mCurrentType){
            case RECT:
            case LINE:
                points = new ArrayList<>(Arrays.asList(new PointF(x, y), new PointF(x, y)));
                break;
            case PATH:
            case POINT:
            case MULTITOUCH:
                points = new ArrayList<>(Collections.singletonList(new PointF(x, y)));
                break;
        }
        mPointModels.add(new PointModel(points, mCurrentType, mCurrentColor));
    }

    public void reset() {
        mPointModels.clear();
        invalidate();
    }

    public void setColor(int color){
        mCurrentColor = color;
    }

    public void setType(Types drawType){
        mCurrentType = drawType;
    }

    private void setUpPaint() {
        mBackgroundPaint.setColor(mCurrentColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(STROKE_WIDTH);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }
}
