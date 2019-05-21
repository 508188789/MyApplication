package com.example.wang.myapplication.utils.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wang.myapplication.Config;

import java.lang.ref.WeakReference;

/**
 * Created by ${151530502} on 2018/12/4.
 */
public class SharedPreferenceUtil {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferenceUtil(Context context){
        WeakReference<Context> weakReference = new WeakReference<>(context);
        Context mContext = weakReference.get();
        sharedPreferences = mContext.getSharedPreferences(Config.DATE_SP,Context.MODE_PRIVATE);
    }

    public synchronized String getBeforeDate(){
        return sharedPreferences.getString(Config.BEFORE_DATE,"");
    }

    public synchronized void setBeforeDate(String date){
        editor = sharedPreferences.edit();
        editor.putString(Config.BEFORE_DATE,date);
        editor.apply();
    }

    public synchronized String getNextDate(){
        return sharedPreferences.getString(Config.NEXT_DATE,"");
    }

    public synchronized void setNextDate(String date){
        editor = sharedPreferences.edit();
        editor.putString(Config.NEXT_DATE,date);
        editor.apply();
    }

    public synchronized void setTodayDate(String todayDate){
        editor = sharedPreferences.edit();
        editor.putString(Config.TODAY_DATE,todayDate);
        editor.apply();
    }

    public synchronized String getTodayDate(){
        return sharedPreferences.getString(Config.TODAY_DATE,"");
    }

    public synchronized String getCurrentDate(){
        return sharedPreferences.getString(Config.CURRENT_DATE,"");
    }

    public synchronized void setCurrentDate(String currentDate){
        editor = sharedPreferences.edit();
        editor.putString(Config.CURRENT_DATE,currentDate);
        editor.apply();
    }

    public synchronized void setTextSizeIdx(int idx){
        editor = sharedPreferences.edit();
        editor.putInt(Config.TEXT_SIZE,idx);
        editor.apply();
    }

    public synchronized int getTextSizeIdx(){
        return sharedPreferences.getInt(Config.TEXT_SIZE,1);
    }

    public synchronized void setBackColor(int idx){
        editor = sharedPreferences.edit();
        editor.putInt(Config.BACK_COLOR,idx);
        editor.apply();
    }

    public synchronized int getBackColor(){
        return sharedPreferences.getInt(Config.BACK_COLOR,0);
    }

    public synchronized void setEnableNightMode(boolean b){
        editor = sharedPreferences.edit();
        editor.putBoolean(Config.NIGHT_MODE,b);
        editor.apply();
    }

    public synchronized boolean getEnableNightMode(){
        return sharedPreferences.getBoolean(Config.NIGHT_MODE,false);
    }
}
