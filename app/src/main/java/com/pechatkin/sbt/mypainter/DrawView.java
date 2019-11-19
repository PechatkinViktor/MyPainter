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
                case POINT:
                    canvas.drawPoint(points.get(0).x, points.get(0).y, mPaint);
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
                actionMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            default:
                return super.onTouchEvent(event);
        }

        invalidate();
        return true;
    }

    private void actionMove(float x, float y) {
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
