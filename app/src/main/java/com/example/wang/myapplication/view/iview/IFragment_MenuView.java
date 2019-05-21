package com.example.wang.myapplication.view.iview;

import  com.example.wang.myapplication.modle.bean.Good;
import  com.example.wang.myapplication.modle.bean.Love;
import  com.example.wang.myapplication.modle.bean.PicHeadTip;
import  com.example.wang.myapplication.modle.bean.SchoolMes;

import java.util.List;

/**
 * Created by longer on 2017/4/17.
 */

public interface IFragment_MenuView {

    void setHeadPic(List<PicHeadTip> list);

    //设置校园消息
    void setmenu_message(SchoolMes schoolMes);

    void setmenu_message_llshow();

    void setmenu_message_llhide();

    void setmenu_message_cardshow();

    void setmenu_message_cardhide();

    void setmenu_message_qqshow();

    void setmenu_message_qqhide();

    void setmenu_message_signshow();

    void setmenu_message_signhide();

    //设置表白墙
    void setmenu_love(List<Love> list);

    void setmenu_love_cardshow();

    void setmenu_love_cardhide();



    //设置跳蚤市场
    void setmenu_goodsdata(List<Good> list);
}
