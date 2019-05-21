package com.example.wang.myapplication.modle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ${151530502} on 2018/11/19.
 */
public class TimetableResultModel {
    @SerializedName("havetime")
    private List<TimetableModel> haveList;

    @SerializedName("notime")
    private List<TimetableModel> notimeList;

    public List<TimetableModel> getNotimeList() {
        return notimeList;
    }

    public void setNotimeList(List<TimetableModel> notimeList) {
        this.notimeList = notimeList;
    }

    public List<TimetableModel> getHaveList() {
        return haveList;
    }

    public void setHaveList(List<TimetableModel> haveList) {
        this.haveList = haveList;
    }
}
