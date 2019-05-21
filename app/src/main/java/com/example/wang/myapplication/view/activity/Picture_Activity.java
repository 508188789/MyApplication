package com.example.wang.myapplication.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import  com.example.wang.myapplication.R;
import  com.example.wang.myapplication.adapter.PictureAdapter;

import java.util.ArrayList;

/**
 * Created by Axu on 2016/9/20.
 */
public class Picture_Activity extends Activity {
    private String str;
    private TextView tv_posotion;
    public Context context;
    public int position;
    public PictureAdapter imageAdapter;

    public ArrayList<String> url = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        tv_posotion = (TextView) findViewById(R.id.tv_picture_position);
        context = Picture_Activity.this;
        Intent intent = getIntent();
        url = (ArrayList<String>) intent.getSerializableExtra("url");
        position = (int) intent.getSerializableExtra("position");
        str = Integer.toString(position + 1) + "/" + Integer.toString(url.size());
        tv_posotion.setText(str);
        final  com.example.wang.myapplication.utils.PinchImageViewPager pager = ( com.example.wang.myapplication.utils.PinchImageViewPager) findViewById(R.id.pic);
        pager.setOnPageChangeListener(listen);
        imageAdapter = new PictureAdapter(context, url, pager);
        pager.setAdapter(imageAdapter);
        pager.setCurrentItem(position);
    }


    public  com.example.wang.myapplication.utils.PinchImageViewPager.OnPageChangeListener listen = new  com.example.wang.myapplication.utils.PinchImageViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            str = Integer.toString(position + 1) + "/" + Integer.toString(url.size());
            tv_posotion.setText(str);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
}