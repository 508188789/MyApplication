package com.example.wang.myapplication.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.TimeUtils;
import com.bumptech.glide.Glide;
import com.example.wang.myapplication.Application;
import com.example.wang.myapplication.utils.PublicTools;
import com.example.wang.myapplication.view.activity.Calan_Activity;
import com.example.wang.myapplication.view.activity.ImageActivity;
import com.example.wang.myapplication.view.activity.Info_Activity;
import com.example.wang.myapplication.view.activity.SetActivity;
import com.example.wang.myapplication.view.activity.SkinActivity;
import com.example.wang.myapplication.view.activity.User_Activity;
import com.example.wang.myapplication.view.iview.OnNoticeUpdateListener;
import com.example.wang.myapplication.view.iview.OnSwitchPagerListener;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import  com.example.wang.myapplication.R;
import  com.example.wang.myapplication.adapter.MenuGoodsAdapter;
import  com.example.wang.myapplication.adapter.MenuLoveAdapter;
import  com.example.wang.myapplication.modle.bean.CourseClass;
import  com.example.wang.myapplication.modle.bean.Good;
import  com.example.wang.myapplication.modle.bean.Lost;
import  com.example.wang.myapplication.modle.bean.Love;
import  com.example.wang.myapplication.modle.bean.PicHeadTip;
import  com.example.wang.myapplication.modle.bean.SchoolMes;
import  com.example.wang.myapplication.presenter.Fragement_MenuPresenter;
import  com.example.wang.myapplication.utils.LoginService;
import  com.example.wang.myapplication.view.activity.NewsActivity;
import  com.example.wang.myapplication.view.activity.QueryActivity;
import  com.example.wang.myapplication.view.activity.SignUp_Activity;
import  com.example.wang.myapplication.view.iview.IFragment_MenuView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

@SuppressLint({ "NewApi", "ValidFragment" })

public class Fragment_Menu extends Fragment implements OnNoticeUpdateListener,IFragment_MenuView {
    @BindView(R.id.tv_menu_mes_qq)
    TextView tvMenuMesQq;
    @BindView(R.id.tv_menu_mes_sign)
    TextView tvMenuMesSign;
    @BindView(R.id.ll_menu_mes)
    LinearLayout llMenuMes;
    @BindView(R.id.card_menu_message)
    CardView cardMenuMessage;
    @BindView(R.id.card_menu_love)
    CardView cardMenuLove;
    @BindView(R.id.tv_menu_mes_type)
    TextView tvMenuMesType;
    @BindView(R.id.tv_menu_mes_info)
    TextView tvMenuMesInfo;
    @BindView(R.id.tv_menu_mes_sponsor)
    TextView tvMenuMesSponsor;
    @BindView(R.id.tv_menu_mes_time)
    TextView tvMenuMesTime;
    @BindView(R.id.recycle_item_menu_love)
    RecyclerView recycleItemMenuLove;
    @BindView(R.id.recycle_item_menu_goods)
    RecyclerView recycleItemMenuGoods;
    private RollPagerView mRollViewPager;
    private TextView tv_money;//一卡通卡片,余额
    private View view;
    private Context context;
    private static boolean login;
    final  com.example.wang.myapplication.view.activity.MainActivity mainActivity =  com.example.wang.myapplication.view.activity.MainActivity.instance;;

    private final int refresh_success = 1;//刷新一卡通余额成功
    private final int refresh_nochange = 2;//刷新一卡通余额没有变化
    private final int refresh_fail = 3;
    private Fragement_MenuPresenter menuPresenter = new Fragement_MenuPresenter(this);
    Unbinder mUnbinder;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case refresh_success:
                    tv_money.setText( com.example.wang.myapplication.utils.FileTools.getshare(context, "money"));
                    break;
                case refresh_nochange:
                    break;
                case refresh_fail:
                     com.example.wang.myapplication.utils.Toast.show("啊呀，获取余额失败鸟~");
                    break;
            }
        }

        ;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, null);
        context = getActivity();
        mUnbinder =  ButterKnife.bind(this, view);
         com.example.wang.myapplication.view.activity.MainActivity.setToolBarVisibale(true);
        init();
        setpic();
        return view;
    }


    /**
     * 设置图片轮显
     */
    public void setpic() {
        mRollViewPager = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        mRollViewPager.setAnimationDurtion(700);
        mRollViewPager.setAdapter(new TestNormalAdapter());
        mRollViewPager.setHintView(new ColorPointHintView(context, Color.parseColor("#f0FF4081"), Color.parseColor("#a0ffffff")));

        menuPresenter.setHeadPic();
    }

    @Override
    public void setHeadPic(List<PicHeadTip> list) {
        mRollViewPager.setAdapter(new Test_networkAdapter(list));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }


    @Override
    public void onUpdateNotice() {

    }


    private class TestNormalAdapter extends StaticPagerAdapter {
        private int[] imgs = {
                R.mipmap.home_1,
                R.mipmap.home_2,
                R.mipmap.home_3,
                R.mipmap.home_4
        };

        @Override
        public View getView(ViewGroup container, final int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }
    }

    private class Test_networkAdapter extends StaticPagerAdapter {
        private List<PicHeadTip> list;

        public Test_networkAdapter(List<PicHeadTip> list) {
            this.list = list;
        }

        private int[] imgs = {
                R.mipmap.home_1,
                R.mipmap.home_2,
                R.mipmap.home_3,
                R.mipmap.home_4,
                R.mipmap.home_2,
                R.mipmap.home_3
        };

        @Override
        public View getView(ViewGroup container, final int position) {
            ImageView view = new ImageView(container.getContext());
            Glide.with(context).load(list.get(position).getUrl())
                    .centerCrop()
                    .placeholder(imgs[position])
                    .error(imgs[position])
                    .crossFade(1000) // 可设置时长，默认“300ms”
                    .into(view);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return list.size() <= 4 ? 4 : list.size();
        }
    }

    public void init() {
        login = false;
        String str =  com.example.wang.myapplication.utils.FileTools.getshare(context, "login");
        if ("true".equals(str)) {// 表示已经成功登录过
            login = true;
        }

        view.findViewById(R.id.menu_calan).setOnClickListener(listener_calan);//校历
        view.findViewById(R.id.menu_course).setOnClickListener(listener_course);//课表
        view.findViewById(R.id.menu_zfxt).setOnClickListener(listener_zfxt);//正方系统
        view.findViewById(R.id.menu_library).setOnClickListener(listener_library);//图书借阅
        view.findViewById(R.id.menu_love).setOnClickListener(listener_love);//表白墙
        view.findViewById(R.id.menu_money).setOnClickListener(listener_money);//消费记录
        view.findViewById(R.id.menu_yellow).setOnClickListener(listener_yellow);//学校黄历
        view.findViewById(R.id.menu_more).setOnClickListener(listener_more);//更多功能


        mainActivity.findViewById(R.id.ll_menu_down).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                 com.example.wang.myapplication.view.activity.MainActivity.openmore();
            }
        });
        mainActivity.findViewById(R.id.view_menu_down).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                 com.example.wang.myapplication.view.activity.MainActivity.openmore();
            }
        });
        mainActivity.findViewById(R.id.menu_news).setOnClickListener(listener_news);//校园新闻
        mainActivity.findViewById(R.id.menu_car).setOnClickListener(listener_bus);//校车
        mainActivity.findViewById(R.id.menu_select_card).setOnClickListener(listener_select_card);//一卡通查询
        mainActivity.findViewById(R.id.menu_student_select).setOnClickListener(listener_student_select);//学生信息查询
        mainActivity.findViewById(R.id.menu_card_tools).setOnClickListener(listener_card_tools);//一卡通工具
        mainActivity.findViewById(R.id.menu_student).setOnClickListener(listener_student);//学生证
        mainActivity.findViewById(R.id.menu_vip).setOnClickListener(listener_vip);//免费会员
        mainActivity.findViewById(R.id.menu_store).setOnClickListener(listener_store);//同学的店
        ///-------------------------------------------------------------------------------------------------------

        //setmenu_card();//一卡通消费记录卡片
        //setmenu_course();//设置课表卡片
        menuPresenter.setMenuLove();//设置表白墙卡片
        menuPresenter.setMenuMessage();//设置校园活动通知卡片
        setmenu_lost();//设置失物招领卡片
        menuPresenter.setMenuGoods();//设置跳蚤市场卡片
    }
    //////////////////////////////////////////////////////////////////////////////////////



    /**
     * 设置课表卡片
     */
   /* private void setmenu_course() {
        //先将卡片隐藏
        CardView cardView = (CardView) view.findViewById(R.id.card_menu_course);
        cardView.setVisibility(View.GONE);
        //得到今天的课表
        List<CourseClass> classes =  com.example.wang.myapplication.utils.StreamTools.gettodaycourse();
        if (classes == null) {//如果为空则不显示
            return;
        }
        cardView.setVisibility(View.VISIBLE);
        TextView tv_jie = (TextView) view.findViewById(R.id.tv_menu_course_jie);
        int coursenum = classes.size();
        String strcourse = coursenum == 0 ? "今天没课，好好休息哟~" : "今天有" + coursenum + "节课";
        tv_jie.setText(strcourse);

        TextView tv = (TextView) view.findViewById(R.id.tv_menu_course_zhou);
        tv.setText("第" +  com.example.wang.myapplication.utils.TimeTools.course_zhoushu() + "周");

        int[] ll = {R.id.ll_menu_course1, R.id.ll_menu_course2, R.id.ll_menu_course3, R.id.ll_menu_course4, R.id.ll_menu_course5};
        int[] tv_name = {R.id.tv_menu_course_name1, R.id.tv_menu_course_name2, R.id.tv_menu_course_name3, R.id.tv_menu_course_name4, R.id.tv_menu_course_name5};
        int[] tv_info = {R.id.tv_menu_course_info1, R.id.tv_menu_course_info2, R.id.tv_menu_course_info3, R.id.tv_menu_course_info4, R.id.tv_menu_course_info5};

        CourseClass course = null;
        Log.d("有多少节课：", classes.size() + "");
        for (int i = 0; i < classes.size(); i++) {
            LinearLayout llyout = (LinearLayout) view.findViewById(ll[i]);
            llyout.setVisibility(View.VISIBLE);
            llyout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context,  com.example.wang.myapplication.view.courseclass.CourseActivity.class));
                }
            });
            course = classes.get(i);// 拿到当前课程
            tv = (TextView) view.findViewById(tv_name[i]);
            tv.setText(course.getName());

            tv = (TextView) view.findViewById(tv_info[i]);
            StringBuffer sb = new StringBuffer();
            int x = (course.getId()) % 10;//行   得到对应的位置
//            Log.d("tip", "行" + x);
            switch (x) {
                case 1:
                    sb.append("上午1-2");
                    break;
                case 2:
                    sb.append("上午3-4");
                    break;
                case 3:
                    sb.append("下午1-2");
                    break;
                case 4:
                    sb.append("下午3-4");
                    break;
                case 5:
                    sb.append("晚上");
                    break;
            }
            sb.append("   ");
            sb.append(course.getRoom());
            tv.setText(sb.toString());
        }

    }
*/

    /**
     * 设置失物招领卡片
     */
    private void setmenu_lost() {
        view.findViewById(R.id.tv_menu_lostadd).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {//底部我要发布按钮
                startActivity(new Intent(context,  com.example.wang.myapplication.view.activity.Add_lostActivity.class));
            }
        });
        //得到数据
        BmobQuery<Lost> query = new BmobQuery<Lost>();
        query.order("-createdAt");
        query.setLimit(1);
        query.findObjects(new FindListener<Lost>() {
                              @Override
                              public void done(List<Lost> list, BmobException e) {
                                  if (e != null) {
                                      Log.d("查询失物招领出错", e.toString());
                                      e.printStackTrace();
                                       com.example.wang.myapplication.utils.Toast.show("查询出错(" + e.getErrorCode() + ")");
                                      return;
                                  }
                                  Lost good = list.get(0);
                                  ImageView iv = (ImageView) view.findViewById(R.id.iv_menu_lost);
                                  if (good.getPic1() != null) {
                                      Glide.with(context).load(good.getPic1().getUrl())
                                              .placeholder(R.mipmap.imageselector_photo)
                                              .error(R.mipmap.imageselector_photo)
                                              .into(iv);
                                  } else {
                                      Glide.with(context).load(R.drawable.lost_nopic)
                                              .into(iv);
                                  }
                                  TextView tv = (TextView) view.findViewById(R.id.tv_menu_lost_name);
                                  tv.setText(good.getTitle());
                                  tv = (TextView) view.findViewById(R.id.tv_menu_lost_info);
                                  tv.setText(good.getInfor());
                                  tv = (TextView) view.findViewById(R.id.tv_menu_lost_time);
                                  tv.setText(TimeUtils.getFriendlyTimeSpanByNow(good.getCreatedAt()));

                                  //设置失物的点击事件
                                  final Lost good1 = good;
                                  view.findViewById(R.id.ll_menu_lost).setOnClickListener(new OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent intent = new Intent(context,  com.example.wang.myapplication.view.activity.Lost_Activity.class);
                                          ActivityOptionsCompat options =
                                                  ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                                          v.findViewById(R.id.iv_menu_lost), getString(R.string.transition_goods_img));
                                          intent.putExtra("Lost", good1);
                                          ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                                      }
                                  });
                              }
                          }
        );
    }

    /**
     * 设置校园通知卡片
     */
    @Override
    public void setmenu_message(final SchoolMes schoolMes) {
        tvMenuMesType.setText(schoolMes.getType());
        tvMenuMesInfo.setText("        " + schoolMes.getInfor());
        tvMenuMesSponsor.setText(schoolMes.getSponsor());
        tvMenuMesTime.setText(schoolMes.getTime());

        if (schoolMes.getLink() != null && !schoolMes.getLink().isEmpty()) {//不为空我们可以设置他的点击事件，可以跳转到H5网页
            cardMenuMessage.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                     com.example.wang.myapplication.utils.PublicTools.openhtml(context, schoolMes.getLink());
                }
            });
        }
        if (schoolMes.isSign()) {
            setmenu_message_llshow();
            setmenu_message_signshow();
            tvMenuMesSign.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SignUp_Activity.class);
                    intent.putExtra("sponsor", schoolMes.getSponsor());
                    startActivity(intent);
                }
            });
        }
        if (schoolMes.getQqgroup() != null && !schoolMes.getQqgroup().isEmpty()) {
            setmenu_message_llshow();
            setmenu_message_qqshow();
            tvMenuMesQq.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                     com.example.wang.myapplication.utils.PublicTools.joinQQGroup(context, schoolMes.getQqgroup());
                }
            });
        }
    }

    @Override
    public void setmenu_message_llshow() {
        llMenuMes.setVisibility(View.VISIBLE);
    }

    @Override
    public void setmenu_message_llhide() {
        llMenuMes.setVisibility(View.GONE);
    }

    @Override
    public void setmenu_message_cardshow() {
        cardMenuMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void setmenu_message_cardhide() {
        cardMenuMessage.setVisibility(View.GONE);
    }

    @Override
    public void setmenu_message_qqshow() {
        tvMenuMesQq.setVisibility(View.VISIBLE);
    }

    @Override
    public void setmenu_message_qqhide() {
        tvMenuMesQq.setVisibility(View.GONE);
    }

    @Override
    public void setmenu_message_signshow() {
        tvMenuMesSign.setVisibility(View.VISIBLE);
    }

    @Override
    public void setmenu_message_signhide() {
        tvMenuMesSign.setVisibility(View.GONE);
    }

    /**
     * 设置表白墙卡片
     */
    @Override
    public void setmenu_love(List<Love> list) {
        view.findViewById(R.id.ll_item_menu_love).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {//底部更多表白按钮
                startActivity(new Intent(context,  com.example.wang.myapplication.view.activity.LoveActivity.class));
            }
        });
        recycleItemMenuLove.setHasFixedSize(true);

        SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
       // recycleItemMenuLove.onFlingListener = null;
        snapHelperStart.attachToRecyclerView(recycleItemMenuLove);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recycleItemMenuLove.setLayoutManager(layoutManager);

        MenuLoveAdapter mAdapter = new MenuLoveAdapter(list, context);
        recycleItemMenuLove.setAdapter(mAdapter);
        mAdapter.addEnditem();

    }

    @Override
    public void setmenu_love_cardshow() {
        cardMenuLove.setVisibility(View.VISIBLE);
    }

    @Override
    public void setmenu_love_cardhide() {
        cardMenuLove.setVisibility(View.GONE);
    }



    /**
     * 设置跳蚤市场卡片
     */
    @Override
    public void setmenu_goodsdata(List<Good> list) {
        view.findViewById(R.id.ll_item_menu_goods).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {//底部更多商品
                 com.example.wang.myapplication.view.activity.MainActivity.setBottomNavigation(1);
            }
        });
        recycleItemMenuGoods.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recycleItemMenuGoods.setLayoutManager(layoutManager);
        MenuGoodsAdapter mAdapter = new MenuGoodsAdapter(list, context);
        recycleItemMenuGoods.setAdapter(mAdapter);
    }


    /////////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    OnClickListener listener_ = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };


    /**
     * 表白墙
     */
    OnClickListener listener_love = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(context,  com.example.wang.myapplication.view.activity.LoveActivity.class));
        }
    };

    /**
     * 更多功能
     */
    OnClickListener listener_more = new OnClickListener() {
        @Override
        public void onClick(View v) {
             com.example.wang.myapplication.view.activity.MainActivity.openmore();
        }
    };


    /**
     * 校园公告
     */
    OnClickListener listener_news = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(context, NewsActivity.class));
        }
    };

    /**
     * 校园一隅
     */
    OnClickListener listener_yellow = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "还没写", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(context, Sqlite_selectActivity.class));
        }
    };

    /**
     * 更换主题
     */
    OnClickListener listener_bus = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, SkinActivity.class);
            startActivity(intent);
        }
    };

    /**
     * 个人中新
     */
    OnClickListener listener_zfxt = new OnClickListener() {
        @Override
        public void onClick(View v) {
                Intent intent = new Intent(context, User_Activity.class);
                startActivity(intent);
        }
    };

    /**
     * 图书馆
     */
    OnClickListener listener_library = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "不知道写什么", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 关于
     */
    OnClickListener listener_money = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(context, Info_Activity.class));
            //Toast.makeText(context, "不知道写什么", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 课表
     */
    OnClickListener listener_course = new OnClickListener() {
        public void onClick(View v) {

            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.drawer_layout,new ScheduleFragment()).commit();
            // 判断是否已经登录
/*
                Intent intent = new Intent(context, CourseActivity.class);
                startActivity(intent);*/

        }
    };

    OnSwitchPagerListener onSwitchPagerListener;
    @OnClick(R.id.menu_course)
    public void toScheduleFragment(){
        if(onSwitchPagerListener!=null){
            onSwitchPagerListener.onPagerSwitch();
        }
    }





    /**
     * 校历
     */
    OnClickListener listener_calan = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(context, Calan_Activity.class));
        }
    };

    /**
     * 不知道写啥
     */
    OnClickListener listener_store = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "不知道写什么", Toast.LENGTH_SHORT).show();
           // startActivity(new Intent(context, Info_Activity.class));
        }
    };

    /**
     * 不知道写什么
     */
    OnClickListener listener_student = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "还没写", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(context, StudentActivity.class));
        }
    };

    /**
     * 设置
     */
    OnClickListener listener_card_tools = new OnClickListener() {
        @Override
        public void onClick(View v) {
                startActivity(new Intent(context, SetActivity.class));
        }
    };
    /**
     * 不知道写什么
     */
    OnClickListener listener_vip = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "还没写", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 校园新闻
     */
    OnClickListener listener_select_card = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "还没写", Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(context, Query_cardActivity.class));
        }
    };
    /**
     * 每日一文
     */
    OnClickListener listener_student_select = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // 判断是否已经登录
            startActivity(new Intent(context, QueryActivity.class));
           /* if (login) {// 表示已经成功登录过
                startActivity(new Intent(context, QueryActivity.class));
            } else {
                showsnack();
            }*/
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @return show  snack
     */
    public void showsnack() {
        Snackbar.make( com.example.wang.myapplication.view.activity.MainActivity.bottomNavigation, "登录校园帐号才能使用", Snackbar.LENGTH_LONG).setAction("登录", new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,  com.example.wang.myapplication.view.activity.LoginSchool_Activity.class));
            }
        }).show();
    }



}
