package com.example.wang.myapplication.appwidget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.wang.myapplication.R;
import com.example.wang.myapplication.modle.ScheduleDao;
import com.example.wang.myapplication.modle.TimetableModel;
import com.example.wang.myapplication.utils.tools.TimetableTools;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ${151530502} on 2018/11/19.
 */
public class ScheduleService extends RemoteViewsService {
    private static final String TAG = "ScheduleService";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ScheduleRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    private class ScheduleRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Intent intent;
        Context context;

        List<Schedule> data;

        public ScheduleRemoteViewsFactory(Context context, Intent intent) {
            this.context = context;
            this.intent = intent;
        }

        @Override
        public void onCreate() {
            data=new ArrayList<>();
            data.addAll(findTodayData(context));
        }

        @Override
        public void onDataSetChanged() {
            data.clear();
            data.addAll(findTodayData(context));
        }

        @Override
        public void onDestroy() {
            data.clear();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.schedule_app_widget_item);
            Schedule schedule=data.get(i);
            if(schedule==null) return views;

            views.setTextViewText(R.id.widget_tv_start,""+schedule.getStart()+" - "+(schedule.getStep()+schedule.getStart()-1)+"节在<"+schedule.getRoom()+">上");

            views.setTextViewText(R.id.widget_tv_name,""+schedule.getName());

//            TimetableView timetableView = new TimetableView(context, null);
//            int curWeek = TimetableTools.getCurWeek(context);
//            if (data == null) data = new ArrayList<>();
//            data.clear();
//            data.addAll(findData(context));
//
//            boolean max15 = WidgetConfig.get(context, WidgetConfig.CONFIG_MAX_ITEM);
//            int maxItem = 10;
//            if (max15) maxItem = 15;
//
//            CustomDateBuildAdapter dateAdapter = new CustomDateBuildAdapter();
//            OnSlideBuildAdapter slideAdapter = (OnSlideBuildAdapter) timetableView.onSlideBuildListener();
//            slideAdapter.setTextSize(15);
//            dateAdapter.setTextSize(15);
//
//            slideAdapter.setTextColor(Color.BLACK);
//            dateAdapter.setColor(Color.BLACK);
//
//            boolean hideWeeks = WidgetConfig.get(context, WidgetConfig.CONFIG_HIDE_WEEKS);
//            boolean hideDate = WidgetConfig.get(context, WidgetConfig.CONFIG_HIDE_DATE);
//            if(hideDate) timetableView.hideDateView();
//            if(hideWeeks) timetableView.isShowWeekends(false);
//
//            timetableView.data(data)
//                    .curWeek(curWeek)
//                    .maxSlideItem(maxItem)
//                    .isShowNotCurWeek(false)
//                    .alpha(0f, 0f, 0.6f)
//                    .marLeft(ScreenUtils.dip2px(context, 3))
//                    .marTop(ScreenUtils.dip2px(context, 3))
//                    .itemHeight(ScreenUtils.dip2px(context, 45))
//                    .callback(dateAdapter)
//                    .showView();

//            layoutView(timetableView, ScreenUtils.dip2px(context, 375f), ScreenUtils.dip2px(context, 50) + timetableView.itemHeight() * timetableView.maxSlideItem()+timetableView.marTop()*(timetableView.maxSlideItem()));
//            views.setBitmap(R.id.iv_imgview, "setImageBitmap", getViewBitmap(timetableView));
//            Schedule schedule = data.get(i);
//            views.setTextViewText(R.id.id_widget_item_name, schedule.getName());
//            views.setTextViewText(R.id.id_widget_item_room, schedule.getRoom());
//            views.setTextViewText(R.id.id_widget_item_start, schedule.getStart() + "-" + (schedule.getStart() + schedule.getStep() - 1));

            return views;
        }

        public void layoutView(View v, int width, int height) {
            // validate view.width and view.height
            v.layout(0, 0, width, height);
            int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
            int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

            // validate view.measurewidth and view.measureheight
            v.measure(measuredWidth, measuredHeight);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }

        public Bitmap getViewBitmap(ViewGroup viewGroup) {
            int h = viewGroup.getHeight();
            ;
            Bitmap bitmap;

            // 创建对应大小的bitmap
            bitmap = Bitmap.createBitmap(viewGroup.getWidth(), h,
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            viewGroup.draw(canvas);
            return bitmap;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

    /**
     * 获取数据
     *
     * @return
     */
    public List<Schedule> findData(Context context) {
        if (context == null) return null;
        int id = ScheduleDao.getApplyScheduleId(this);
        List<TimetableModel> dataModels = ScheduleDao.getAllWithScheduleId(id);
        if (dataModels == null) return null;
        return ScheduleSupport.transform(dataModels);
    }

    public List<Schedule> findTodayData(Context context) {
        List<Schedule> allModels = findData(context);
        if (allModels == null) return new ArrayList<>();
        int curWeek = TimetableTools.getCurWeek(context);
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 2;
        if (dayOfWeek == -1) dayOfWeek = 6;
        List<Schedule> list = ScheduleSupport.getHaveSubjectsWithDay(allModels, curWeek, dayOfWeek);
        if(list==null) return new ArrayList<>();
        return list;
    }
}
