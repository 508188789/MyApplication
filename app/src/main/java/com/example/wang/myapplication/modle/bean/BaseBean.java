package com.example.wang.myapplication.modle.bean;

import java.io.Serializable;

/**
 * Created by ${151530502} on 2018/11/18.
 */
public class BaseBean implements Serializable {
    /**
     * code : Number
     * msg : String
     */

    private int code;
    private String msg;


    public int getCode() {
        return code;
    }

    public BaseBean setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public BaseBean setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
