package com.example.wang.myapplication.view.iview;

import  com.example.wang.myapplication.modle.bean.Food;
import  com.example.wang.myapplication.modle.bean.Store;

import java.util.List;

/**
 * Created by longer on 2017/5/15.
 */

public interface IStore_ActivityView {

    void getFoodFailed(String e);

    void showrefresh(boolean isshow);

    void addData(List<Food> list);

    void setData(List<Food> list);

    void createBottomSheetDialog(List<Store> list);

    void getStoreSuccess(List<Store> list);

}
