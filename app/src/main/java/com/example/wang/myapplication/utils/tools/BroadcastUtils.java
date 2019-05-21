package com.example.wang.myapplication.utils.tools;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.wang.myapplication.appwidget.ScheduleAppWidget;

/**
 * Created by ${151530502} on 2018/11/19.
 */
public class BroadcastUtils {
    public static void refreshAppWidget(Context context) {
        Intent intent = new Intent(ScheduleAppWidget.UPDATE_ACTION);
        intent.putExtra(ScheduleAppWidget.INT_EXTRA_START,0);
        intent.setComponent(new ComponentName(context, ScheduleAppWidget.class));
        context.sendBroadcast(intent);
    }
}
