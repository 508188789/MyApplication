package com.example.wang.myapplication.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.example.wang.myapplication.view.activity.QueryActivity;

/**
 * Created by ${151530502} on 2018/12/4.
 */
public class MainScrollView extends ScrollView {
    private QueryActivity activity;


    public MainScrollView(Context context) {
        super(context);
    }

    public MainScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return QueryActivity.isLeftMenuOpen || super.onTouchEvent(ev);
    }
}
