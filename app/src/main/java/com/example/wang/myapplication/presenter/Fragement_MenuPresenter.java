package com.example.wang.myapplication.presenter;


import android.os.Handler;
import android.util.Log;

import  com.example.wang.myapplication.modle.bean.Good;
import  com.example.wang.myapplication.modle.bean.Love;
import  com.example.wang.myapplication.modle.bean.PicHeadTip;
import  com.example.wang.myapplication.modle.bean.SchoolMes;
import  com.example.wang.myapplication.modle.biz.GoodBiz;
import  com.example.wang.myapplication.modle.biz.LoveBiz;
import  com.example.wang.myapplication.modle.biz.PicHeadTipBiz;
import  com.example.wang.myapplication.modle.biz.SchoolMesBiz;
import  com.example.wang.myapplication.view.iview.IFragment_MenuView;

import java.util.List;

/**
 * Created by longer on 2017/4/13.
 */

public class Fragement_MenuPresenter {

    private IFragment_MenuView fragmentMenuView;

    private PicHeadTip.IPicHeadTipBiz picHeadTipBiz;
    private SchoolMes.ISchoolMesBiz schoolMesBiz;
    private Love.ILoveBiz loveBiz;
    private Good.GoodBiz goodBiz;
    private Handler mHander = new Handler();

    public Fragement_MenuPresenter(IFragment_MenuView fragmentMenuView) {
        this.fragmentMenuView = fragmentMenuView;
        picHeadTipBiz = new PicHeadTipBiz();
        schoolMesBiz = new SchoolMesBiz();
        loveBiz = new LoveBiz();
        goodBiz = new GoodBiz();
    }

    public void setHeadPic() {
        picHeadTipBiz.getpicheadtip(new PicHeadTip.OngetPicHeadTip() {
            @Override
            public void Success(List<PicHeadTip> list) {
//                Log.d("tip","长度：" + list.size());
                fragmentMenuView.setHeadPic(list);
            }

            @Override
            public void Failed() {

            }
        });
    }

    public void setMenuMessage() {
        fragmentMenuView.setmenu_message_llhide();
        fragmentMenuView.setmenu_message_cardhide();
        fragmentMenuView.setmenu_message_qqhide();
        fragmentMenuView.setmenu_message_signhide();
        schoolMesBiz.getschoolmes(new SchoolMes.OngetSchoolMes() {
            @Override
            public void Success(SchoolMes schoolMes) {
                if (schoolMes.isShow()) {
                    fragmentMenuView.setmenu_message_cardshow();
                    fragmentMenuView.setmenu_message(schoolMes);
                }
            }

            @Override
            public void Failes() {

            }
        });
    }

    public void setMenuLove() {
        fragmentMenuView.setmenu_love_cardhide();
        loveBiz.getLove(new Love.OnGetLove() {
            @Override
            public void Success(List<Love> list) {
                fragmentMenuView.setmenu_love(list);
                Log.d("表白卡片","长度：" + list.size());
                fragmentMenuView.setmenu_love_cardshow();
            }

            @Override
            public void Failed() {

            }
        });
    }



    public void setMenuGoods(){
        goodBiz.getgoods(new Good.OnGetGoods() {
            @Override
            public void Success(List<Good> list) {
                fragmentMenuView.setmenu_goodsdata(list);
            }

            @Override
            public void Failed() {

            }
        });
    }


}
