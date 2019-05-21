package com.example.wang.myapplication.presenter;

import  com.example.wang.myapplication.Application;
import  com.example.wang.myapplication.modle.bean.User;
import  com.example.wang.myapplication.modle.biz.UserBiz;
import  com.example.wang.myapplication.view.activity.UserInfor_Activity;
import  com.example.wang.myapplication.view.iview.IUserInfor_Activity;

/**
 * Created by longer on 2017/4/27.
 */

public class UserInfor_ActivityPresenter {

    private IUserInfor_Activity inforActivity;
    private User.UserBiz userBiz;

    public UserInfor_ActivityPresenter(UserInfor_Activity inforActivity) {
        this.inforActivity = inforActivity;
        userBiz = new UserBiz();
    }

    public void updata(){
        inforActivity.showSubmiting();
        userBiz.upDataNicenameandRoom(Application.my, inforActivity.getnicename(), inforActivity.getsex(), inforActivity.getroom(), new User.OnUpUser() {
            @Override
            public void Success() {
                inforActivity.hideSubmiting();
                inforActivity.SubmitSuccess();
                inforActivity.toUser_Activity();
            }

            @Override
            public void Failed() {
                inforActivity.hideSubmiting();
                inforActivity.SubmitFailed();
            }
        });
    }


}
