package com.example.wang.myapplication.utils.mipush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import  com.example.wang.myapplication.R;
import  com.example.wang.myapplication.utils.FileTools;
import  com.example.wang.myapplication.utils.LoginService;
import  com.example.wang.myapplication.utils.TimeTools;
import  com.example.wang.myapplication.view.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Axu on 2016/9/29.
 */

public class Myservice extends Service {
    private Thread thread;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        thread = new Thread() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    String text = FileTools.getFile(Myservice.this, "card.txt");
                    if (!text.equals("")) {



//                        Log.d("run", "###");
                    }
                    synchronized (thread) {//使线程释放并不消耗CPU
                        try {
                            thread.wait(180000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    synchronized (thread) {//激活线程
                        thread.notify();
                    }
                }

            }

            ;
        };
        thread.start();
        flags = START_STICKY;//据说当内存充足时，服务会再次启动
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        Intent localIntent = new Intent();
        localIntent.setClass(this, Myservice.class);
        this.startService(localIntent);
        super.onDestroy();
    }
}
