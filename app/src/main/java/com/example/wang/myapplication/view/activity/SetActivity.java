package com.example.wang.myapplication.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import  com.example.wang.myapplication.Application;
import  com.example.wang.myapplication.R;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetActivity extends AppCompatActivity {
    private Context context;
    //    private CardView but_kcb;
    private String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Application.theme);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                }
            });
        }
        inti();
    }

    public void inti() {
//        but_kcb = (CardView) findViewById(R.id.set_kcb);
        context = SetActivity.this;
//        but_kcb.setOnClickListener(new Onclick_kcb());
        login =  com.example.wang.myapplication.utils.FileTools.getshare(context, "login");
    }

    @OnClick({R.id.cardview_set1, R.id.cardview_set2, R.id.cardview_set3, R.id.cardview_set4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cardview_set1:
                 com.example.wang.myapplication.utils.FileTools.saveshareInt("bg_course", R.drawable.bg_course1);
                finish();
                break;
            case R.id.cardview_set2:
                 com.example.wang.myapplication.utils.FileTools.saveshareInt("bg_course", R.drawable.bg_course2);
                finish();
                break;
            case R.id.cardview_set3:
                 com.example.wang.myapplication.utils.FileTools.saveshareInt("bg_course", R.drawable.bg_course3);
                finish();
                break;
            case R.id.cardview_set4:
                 com.example.wang.myapplication.utils.FileTools.saveshareInt("bg_course", R.drawable.bg_course4);
                finish();
                break;
        }
         com.example.wang.myapplication.utils.Toast.show("设置成功");
         com.example.wang.myapplication.utils.FileTools.saveshareString("bg_course_diy", "");
    }

    protected void onResume() {
        super.onResume();
        MiStatInterface.recordPageStart(this, "设置");
    }

    protected void onPause() {
        super.onPause();
        MiStatInterface.recordPageEnd();
    }

}
