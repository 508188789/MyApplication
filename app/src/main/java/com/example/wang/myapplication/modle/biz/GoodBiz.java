package com.example.wang.myapplication.modle.biz;

import com.example.wang.myapplication.modle.bean.Good;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by longer on 2017/4/23.
 */

public class GoodBiz implements Good.GoodBiz {
    @Override
    public void getgoods(final Good.OnGetGoods onGetGoods) {
        //得到数据
        BmobQuery<Good> query = new BmobQuery<Good>();
        query.order("-createdAt");
        query.setLimit(6);
        query.findObjects(new FindListener<Good>() {
              @Override
              public void done(List<Good> list, BmobException e) {
                  if (e != null) {
                      e.printStackTrace();
                      onGetGoods.Failed();
                  }else{
                      onGetGoods.Success(list);
                  }
              }
          }
        );
    }
}
