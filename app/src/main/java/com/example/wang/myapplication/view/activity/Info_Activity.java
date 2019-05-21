package com.example.wang.myapplication.view.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.wang.myapplication.Application;
import com.example.wang.myapplication.Config;
import com.example.wang.myapplication.R;
import com.example.wang.myapplication.modle.bean.Appinfor;
import com.example.wang.myapplication.utils.PublicTools;
import com.example.wang.myapplication.utils.Toast;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by ${151530502} on 2018/10/29.
 */
public class Info_Activity   extends AppCompatActivity {
    private Context context;
   // private CardView cardShare;
   // private CardView card_rate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Application.theme);
        setContentView(R.layout.activity_info);



        context = Info_Activity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setTitle("关于APP");
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#00000000"));//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色

        TextView tv_version = (TextView) findViewById(R.id.tv_info_version);
        tv_version.setText("在平院APP V" + Config.version);

        findViewById(R.id.tv_info_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicTools.joinQQGroup(context, "Sk6KVwbjQ3oYH8GUEfypL6FMRSrgBtUW");
            }
        });

        findViewById(R.id.tv_info_qqnum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicTools.copy("508188789");
            }
        });
        CardView card = (CardView) findViewById(R.id.card_info_gx);
        card.setOnClickListener(onClickListener);

        CardView cardShare = (CardView) findViewById(R.id.card_share) ;
        CardView card_rate = (CardView) findViewById(R.id.card_rate) ;
        cardShare.setOnClickListener(onClickListener1);
        card_rate.setOnClickListener(onClickListener2);
    }
    /**
     * 检查更新
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final ProgressDialog pg = new ProgressDialog(context);
            pg.setMessage("正在检查新版本...");
            pg.setCanceledOnTouchOutside(true);// 点击对话框以外是否消失
            pg.show();
            BmobQuery<Appinfor> bmobQuery = new BmobQuery<Appinfor>();
            bmobQuery.order("-updatedAt");
            bmobQuery.addWhereGreaterThan("version", Config.version);//大于当前版本
            bmobQuery.findObjects(new FindListener<Appinfor>() {
                @Override
                public void done(List<Appinfor> list, BmobException e) {
                    if (e == null && list != null) {
                        pg.dismiss();
                        Log.d("1", "发现新版本");
                        updata(list.get(0));
                    } else {
                        Log.d("info_activity", "没有发现新版本或者出异常了");
                        pg.dismiss();
                        Toast.show("没有发现新版本");
                    }
                }
            });
        }
    };

    /**
     * 分享
     */
    View.OnClickListener onClickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PublicTools.shareapp(context);
        }
    };
    /**
     * 好评
     */
    View.OnClickListener onClickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PublicTools.comment(context);
        }
    };


    /**
     * 有可用更新
     */
    public void updata(final Appinfor appinfor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("发现新版本");
        builder.setCancelable(true);
        builder.setMessage(appinfor.getInfor());
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                download(appinfor);
            }
        });

        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();

    }

    /**
     * 下载最新文件
     */
    public void download(Appinfor appinfor) {
        final ProgressDialog downloadDialog = new ProgressDialog(context);
        downloadDialog.setTitle("下载中");
        downloadDialog.setMax(100);//设置最大值
        downloadDialog.setProgress(0);//进度归零
        downloadDialog.setCancelable(true);//按返回键取消
        downloadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置风格为长方形
        downloadDialog.setCanceledOnTouchOutside(false);//点击外部取消
        downloadDialog.setIndeterminate(false);//设置进度是否明确
        downloadDialog.show();

        File path = new File(Environment.getExternalStorageDirectory() + "/" + appinfor.getApp().getFilename());
        final BmobFile bmobfile = new BmobFile("pdsu.apk", "", appinfor.getApp().getUrl());
        final File saveFile = new File(path, bmobfile.getFilename());
        bmobfile.download(saveFile, new DownloadFileListener() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.show("下载成功,保存路径:" + s);
                    if (s != null) {
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_VIEW);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.setDataAndType(Uri.fromFile(new File(s)), "application/vnd.android.package-archive");
                        startActivity(i);
                    }
                } else {
                    Toast.show("下载失败：" + s);
                }
            }

            @Override
            public void onProgress(Integer integer, long l) {
                downloadDialog.setProgress(integer);
            }

            @Override
            public void onStart() {
                super.onStart();
                Toast.show("开始下载...");
            }
        });

    }
    protected void onResume() {
        super.onResume();
        MiStatInterface.recordPageStart(this, "关于app");
    }

    protected void onPause() {
        super.onPause();
        MiStatInterface.recordPageEnd();
    }

}
