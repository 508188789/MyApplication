package com.example.wang.myapplication.modle.biz;

import com.example.wang.myapplication.modle.bean.SchoolMes;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by longer on 2017/4/17.
 */

public class SchoolMesBiz implements SchoolMes.ISchoolMesBiz {
    @Override
    public void getschoolmes(final SchoolMes.OngetSchoolMes ongetSchoolMes) {
        //得到数据
        BmobQuery<SchoolMes> query = new BmobQuery<SchoolMes>();
        query.order("-createdAt");
        query.setLimit(1);//我们只需要第一条消息
        query.findObjects(new FindListener<SchoolMes>() {
            @Override
            public void done(List<SchoolMes> list, BmobException e) {
                if (e == null) {
                    SchoolMes schoolMes = list.get(0);
                    ongetSchoolMes.Success(schoolMes);
                } else {
                    e.printStackTrace();
                    ongetSchoolMes.Failes();
                }
            }


        });
    }
}
