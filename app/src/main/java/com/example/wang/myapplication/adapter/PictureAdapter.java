package com.example.wang.myapplication.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wang.myapplication.R;
import com.example.wang.myapplication.utils.PinchImageView;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Axu on 2016/9/20.
 */
public class PictureAdapter extends PagerAdapter {
    public Context context;
    public  com.example.wang.myapplication.utils.PinchImageViewPager pager;
    public ArrayList<String> url = new ArrayList<String>();
    final LinkedList<PinchImageView> viewCache = new LinkedList<PinchImageView>();

    public PictureAdapter(Context context, ArrayList<String> url,  com.example.wang.myapplication.utils.PinchImageViewPager pager) {
        this.context = context;
        this.pager=pager;
        this.url = url;

    }

    public int getCount() {
        return url.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        com.example.wang.myapplication.utils.PinchImageView piv;
        if (viewCache.size() > 0) {
            piv = viewCache.remove();
            piv.reset();
        } else {
            piv = new com.example.wang.myapplication.utils.PinchImageView(context);
        }
        String url1 = url.get(position);
        Glide.with(context).load(url1)
//                .placeholder(R.mipmap.imageselector_photo)
                .error(R.mipmap.imageselector_photo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(piv);
        container.addView(piv);
        return piv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        com.example.wang.myapplication.utils.PinchImageView piv = (com.example.wang.myapplication.utils.PinchImageView) object;
        container.removeView(piv);
        viewCache.add(piv);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        com.example.wang.myapplication.utils.PinchImageView piv = (com.example.wang.myapplication.utils.PinchImageView) object;
        String url1 = url.get(position);
//        Picasso.with(context).load(url1)
//                .placeholder(R.mipmap.imageselector_photo)
//                .error(R.mipmap.imageselector_photo)
//                .into(piv);
        Glide.with(context).load(url1).fitCenter()
//                .placeholder(R.mipmap.imageselector_photo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.imageselector_photo)
                .into(piv);
        pager.setMainPinchImageView(piv);
    }
}
