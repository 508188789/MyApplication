package com.example.wang.myapplication.modle.biz;

import com.example.wang.myapplication.Application;
import com.example.wang.myapplication.modle.bean.SignUp;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by longer on 2017/4/17.
 */

public class SignUpBiz implements SignUp.ISignUpBiz {

    @Override
    public void set(String object, String name, String tel, String bj, String infor, final SignUp.OnsetSignUpLister onsetSignUpLister) {
        SignUp signUp = new SignUp();
        signUp.setMy(Application.my);
        signUp.setBj(bj);
        signUp.setName(name);
        signUp.setTel(tel);
        signUp.setInfor(infor);
        signUp.setObject(object);
        signUp.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e != null) {
                    e.printStackTrace();
                    onsetSignUpLister.Failed();
                } else {
                    onsetSignUpLister.Success();
                }
            }
        });
    }
}
