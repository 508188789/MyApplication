package com.example.wang.myapplication.modle.biz;

import com.example.wang.myapplication.modle.bean.PicHeadTip;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by longer on 2017/4/17.
 */

public class PicHeadTipBiz implements PicHeadTip.IPicHeadTipBiz {
    @Override
    public void getpicheadtip(final PicHeadTip.OngetPicHeadTip ongetPicHeadTip) {

        BmobQuery<PicHeadTip> query = new BmobQuery<PicHeadTip>();
        query.order("-createdAt");
        query.setLimit(6);
        query.findObjects(new FindListener<PicHeadTip>() {
            @Override
            public void done(List<PicHeadTip> list, BmobException e) {
                if (e != null) {
                    ongetPicHeadTip.Failed();
                } else {
                    ongetPicHeadTip.Success(list);
                }
            }
        });
    }
}
