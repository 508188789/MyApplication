package com.example.wang.myapplication.view.iview;

import  com.example.wang.myapplication.modle.bean.User;

/**
 * Created by longer on 2017/4/28.
 */

public interface IRegister_ActivityView {

    void setBtnRegisterEnable(boolean enable);

    boolean getischecked();

    void sendSMSSuccess();

    void sendSMSFailed(int code);

    void showRegisterError(int code);

    void RegisterSuccess();

    void showSendSMSdialogTip(String phone);

    void showSendSMSdialog();

    void hideSendSMSdialog();

    void showUserhaveRegister();//提示已经注册

    void showBindingSchoolDialog(User user);

    void registerSuccess();

    void registerFailed();

    String getPhone();

    String getYzm();

    void toMain_Activity();

}
