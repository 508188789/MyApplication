package com.example.wang.myapplication.view.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.wang.myapplication.Application;
import com.example.wang.myapplication.R;
import com.example.wang.myapplication.utils.tools.SharedPreferenceUtil;
import com.example.wang.myapplication.utils.tools.ToastUtil;
import com.example.wang.myapplication.view.activity.QueryActivity;
import com.example.wang.myapplication.view.view.RightButton;

/**
 * Created by ${151530502} on 2018/12/4.
 */
public class RightDialogFragment extends DialogFragment implements View.OnClickListener{
    private RightButton collectButton;
    private RightButton shareButton;
    private RightButton beforeButton;
    private RightButton randomButton;
    private RightButton nextButton;
    private RightButton todayButton;
    private boolean isToday = false;
    private SharedPreferenceUtil sharedPreferenceUtil;
    private QueryActivity activity;
    private boolean isCollected = false;
    private ToastUtil toastUtil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setWindowAnimations(R.style.right_dialog_anim);
        //getDialog().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        View view = inflater.inflate(R.layout.right_layout, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        initListener();
    }

    private void init(){
        toastUtil = new ToastUtil(getContext());
        activity = (QueryActivity)getActivity();
        sharedPreferenceUtil = new SharedPreferenceUtil(getContext());
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setDimAmount(0.25f); //设置外部背景
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.TOP|Gravity.END|Gravity.BOTTOM;
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(lp);
        invalidate();
        if (Application.databaseUtil.getData(activity.currData.getDate().getCurr()).getContent()!=null){
            isCollected = true;
        }else {
            isCollected = false;
        }
        Log.d("dingyl","isCollected : " + isCollected);
        if (isCollected){
            collectButton.setImageView(R.drawable.right_collect_icon_red);
        }else {
            collectButton.setImageView(R.drawable.right_collect_icon);
        }
    }

    private void initView(View view){
        collectButton = view.findViewById(R.id.collect_button);
       // shareButton = view.findViewById(R.id.share_button);
        beforeButton = view.findViewById(R.id.before_button);
        randomButton = view.findViewById(R.id.random_button);
        nextButton = view.findViewById(R.id.next_button);
        todayButton = view.findViewById(R.id.today_button);
    }

    private void initListener(){
        collectButton.setOnClickListener(this);
      //  shareButton.setOnClickListener(this);
        beforeButton.setOnClickListener(this);
        randomButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        todayButton.setOnClickListener(this);
    }

    private void invalidate(){
        isToday = sharedPreferenceUtil.getCurrentDate().equals(sharedPreferenceUtil.getTodayDate());
        if (isToday){
            nextButton.setVisibility(View.GONE);
            todayButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.collect_button:
                if (!isCollected){
                    Application.databaseUtil.insertData(activity.currData);
                    toastUtil.setToastText(getString(R.string.collect_success));
                }else {
                    Application.databaseUtil.deleteData(activity.currData.getDate().getCurr());
                    toastUtil.setToastText(getString(R.string.uncollect_success));
                }

                getDialog().dismiss();
                break;
           /* case R.id.share_button:

                // TODO for share
                getDialog().dismiss();
                break;*/
            case R.id.before_button:
                activity.presenter.requestDateBefore();
                getDialog().dismiss();
                activity.scrollToTop();
                break;
            case R.id.random_button:
                activity.presenter.requestDataRandom();
                getDialog().dismiss();
                activity.scrollToTop();
                break;
            case R.id.next_button:
                activity.presenter.requestDateNext();
                getDialog().dismiss();
                activity.scrollToTop();
                break;
            case R.id.today_button:
                activity.presenter.requestData();
                getDialog().dismiss();
                activity.scrollToTop();
                break;
        }
    }
}
