package com.example.wang.myapplication.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.wang.myapplication.Application;
import com.example.wang.myapplication.R;
import com.example.wang.myapplication.adapter.CollectRecyclerAdapter;
import com.example.wang.myapplication.modle.bean.MainData;

import java.util.ArrayList;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * Created by ${151530502} on 2018/12/4.
 */
public class CollectActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TextView collectText;
    private CollectRecyclerAdapter adapter;
    private ArrayList<MainData> mainDataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        recyclerView = findViewById(R.id.collect_recycler);
        collectText = findViewById(R.id.text_not_collect);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initData(){
        mainDataArrayList = new ArrayList<>();
        mainDataArrayList = Application.databaseUtil.getAllData();
        adapter = new CollectRecyclerAdapter(this,mainDataArrayList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,VERTICAL));
        if (mainDataArrayList.size() == 0){
            collectText.setVisibility(View.VISIBLE);
        }else{
            collectText.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
