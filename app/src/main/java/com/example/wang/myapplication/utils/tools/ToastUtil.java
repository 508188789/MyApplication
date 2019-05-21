package com.example.wang.myapplication.utils.tools;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wang.myapplication.R;

/**
 * Created by ${151530502} on 2018/12/4.
 */
public class ToastUtil {
    private static Toast toast;
    private TextView toastText;
    private View view;

    public ToastUtil(Context context){
        toast = new Toast(context);
        view = LayoutInflater.from(context).inflate(R.layout.toast_layout2,null);
        toastText = view.findViewById(R.id.toast_text);
    }

    public void setToastText(String text){
        toastText.setText(text);
        toast.setGravity(Gravity.CENTER,12,10);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
