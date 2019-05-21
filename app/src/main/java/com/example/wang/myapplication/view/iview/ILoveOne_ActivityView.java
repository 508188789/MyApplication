package com.example.wang.myapplication.view.iview;

import android.widget.EditText;

import  com.example.wang.myapplication.modle.bean.Love;
import  com.example.wang.myapplication.modle.bean.LoveComment;

import java.util.List;

/**
 * Created by longer on 2017/4/13.
 */

public interface ILoveOne_ActivityView {

    void setSms(Integer sms);

    void setLike(Integer num);

    void showCommentView();

    void hideComentView();

    void showCommenting();

    void setCommentData(List<LoveComment> list);

    void addCommentData(LoveComment comment);

    void showsetCommentDataFail();

    void showCommentFail();

    void clearCommnet();

    Love getLove();

    String getComment();

    EditText getedittext();

    boolean checksblike();



}
