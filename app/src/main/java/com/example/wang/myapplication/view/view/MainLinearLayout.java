package com.example.wang.myapplication.view.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.wang.myapplication.view.activity.QueryActivity;

/**
 * Created by ${151530502} on 2018/12/4.
 */
public class MainLinearLayout extends LinearLayout {
    float oldX = 0;
    float oldY = 0;
    int dx = 0;
    private QueryActivity activity;

    public MainLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public MainLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MainLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        Log.d("dingyl","init");
        activity = (QueryActivity)context;
    }
}
