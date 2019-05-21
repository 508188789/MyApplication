package com.example.wang.myapplication.modle;

import android.text.TextUtils;

import com.example.wang.myapplication.utils.tools.TimetableTools;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleEnable;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${151530502} on 2018/11/19.
 */
public class TimetableModel extends DataSupport implements ScheduleEnable,Serializable {
    private static final String TAG = "TimetableModel";

    public static final String EXTRA_ID="id";

    private ScheduleName scheduleName;

    private int id;

    private String major;

    private int tag=0;

    /**
     * 课程名
     */
    private String name;

    private String time;

    /**
     * 教室
     */
    private String room;

    /**
     * 教师
     */
    private String teacher;

    /**
     * 第几周至第几周上
     */
    private List<Integer> weekList=new ArrayList<>();

    /**
     * 开始上课的节次
     */
    private int start;

    /**
     * 上课节数
     */
    private int step;

    /**
     * 周几上
     */
    private int day;

    private String term;

    /**
     *  一个随机数，用于对应课程的颜色
     */
    private int colorRandom = 0;

    private String weeks;

    public TimetableModel() {
        // TODO Auto-generated constructor stub
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
        setWeekList(TimetableTools.getWeekList(weeks));
    }

    public void setScheduleName(ScheduleName scheduleName) {
        this.scheduleName = scheduleName;
    }

    public ScheduleName getScheduleName() {
        return scheduleName;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }


    public TimetableModel(String term, String name, String room, String teacher, List<Integer> weekList, int start, int step, int day, int colorRandom, String time) {
        super();
        this.term=term;
        this.name = name;
        this.room = room;
        this.teacher = teacher;
        this.weekList=weekList;
        this.start = start;
        this.step = step;
        this.day = day;
        this.colorRandom = colorRandom;
        this.time=time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setWeekList(List<Integer> weekList) {
        this.weekList = weekList;
    }

    public List<Integer> getWeekList() {
        return weekList;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getColorRandom() {
        return colorRandom;
    }

    public void setColorRandom(int colorRandom) {
        this.colorRandom = colorRandom;
    }

    @Override
    public Schedule getSchedule() {
        Schedule schedule=new Schedule();
        schedule.setDay(getDay());
        schedule.setName(getName());
        schedule.setRoom(getRoom());
        schedule.setStart(getStart());
        schedule.setStep(getStep());
        schedule.setTeacher(getTeacher());
        if(!TextUtils.isEmpty(getWeeks())){
            setWeekList(TimetableTools.getWeekList(getWeeks()));
        }
        schedule.setWeekList(getWeekList());
        schedule.setColorRandom(2);
        schedule.putExtras(EXTRA_ID,getId());
        return schedule;
    }

    public int getId() {
        return id;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }
}