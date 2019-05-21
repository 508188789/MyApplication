package com.example.wang.myapplication.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import  com.example.wang.myapplication.Application;
import  com.example.wang.myapplication.R;
import  com.example.wang.myapplication.modle.bean.User;
import  com.example.wang.myapplication.presenter.UserInfor_ActivityPresenter;
import  com.example.wang.myapplication.view.iview.IUserInfor_Activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfor_Activity extends AppCompatActivity implements IUserInfor_Activity {

    public Context context;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_userinfor_head)
    ImageView ivUserinforHead;
    @BindView(R.id.tv_user_phone_state)
    TextView tvUserPhoneState;
    @BindView(R.id.card_userinfor)
    CardView cardUserinfor;
    @BindView(R.id.edt_userinfor_nicename)
    EditText edtUserinforNicename;
    @BindView(R.id.eadio_userinfor_boy)
    RadioButton eadioUserinforBoy;
    @BindView(R.id.eadio_userinfor_girl)
    RadioButton eadioUserinforGirl;
    @BindView(R.id.ll_userinfor)
    LinearLayout llUserinfor;
    @BindView(R.id.edt_userinfor_room)
    EditText edtUserinforRoom;
    ProgressDialog dialog;
    private UserInfor_ActivityPresenter activityPresenter = new UserInfor_ActivityPresenter(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Application.theme);
        setContentView(R.layout.activity_userinfor);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("个人信息");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        inti();
    }

    public void inti() {
        context = UserInfor_Activity.this;

        dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("正在更新用户信息...");
        eadioUserinforBoy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    eadioUserinforGirl.setChecked(false);
                }
            }
        });
        eadioUserinforGirl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    eadioUserinforBoy.setChecked(false);
                }
            }
        });


        initView();
    }

    public void initView() {
        User user = Application.my;
        if (user != null && user.getMobilePhoneNumberVerified() != null && user.getMobilePhoneNumberVerified()) {
            setCardVisibale(false);
        }else {
            setLLVisibale(false);
            return;
        }

        //设置头像
        if(user.getHeadpic() != null){
            Glide.with(context).load(user.getHeadpic().getUrl())
                    .centerCrop()
                    .transform(new  com.example.wang.myapplication.utils.GlideCircleTransform(context, 1, context.getResources().getColor(R.color.white)))
                    .error(R.drawable.head_boy)
                    .into(ivUserinforHead);
        }else{
            Glide.with(context).load(R.drawable.head_boy)
                    .centerCrop()
                    .transform(new  com.example.wang.myapplication.utils.GlideCircleTransform(context, 1, context.getResources().getColor(R.color.white)))
                    .into(ivUserinforHead);
        }

        //设置昵称
        if(user.getNickname() != null){
            edtUserinforNicename.setText(user.getNickname());
        }
        //性别
        if(user.getSex() != null){
            if("boy".equals(user.getSex())){
                eadioUserinforBoy.setChecked(true);
                eadioUserinforGirl.setChecked(false);
            }else{
                eadioUserinforBoy.setChecked(false);
                eadioUserinforGirl.setChecked(true);
            }
        }

        //寝室
        if(user.getRoom() != null){
            edtUserinforRoom.setText(user.getRoom());
        }


    }

    /**
     * 三点菜单
     * @param menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_userinfor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_userinfor_commit) {
            if(cardUserinfor.isShown()){
                 com.example.wang.myapplication.utils.Toast.show("不能提交修改");
                return true;
            }
            if(getnicename().isEmpty() || getroom().isEmpty()){
                 com.example.wang.myapplication.utils.Toast.show("不能为空哦");
                return true;
            }
            activityPresenter.updata();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getnicename() {
        return edtUserinforNicename.getText().toString().trim();
    }

    @Override
    public String getsex() {
        return eadioUserinforBoy.isChecked() ? "boy" : "girl";
    }

    @Override
    public String getroom() {
        return edtUserinforRoom.getText().toString().trim();
    }

    @Override
    public void setCardVisibale(boolean isshow) {
        if(isshow){
            cardUserinfor.setVisibility(View.VISIBLE);
        }else{
            cardUserinfor.setVisibility(View.GONE);
        }
    }

    @Override
    public void setLLVisibale(boolean isshow) {
        if(isshow){
            llUserinfor.setVisibility(View.VISIBLE);
        }else{
            llUserinfor.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSubmiting() {
        dialog.show();
    }

    @Override
    public void hideSubmiting() {
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void toUser_Activity() {
        User_Activity.instant.finish();
        finish();
    }

    @Override
    public void SubmitSuccess() {
         com.example.wang.myapplication.utils.Toast.show("修改成功");
    }

    @Override
    public void SubmitFailed() {
         com.example.wang.myapplication.utils.Toast.show("修改失败");
    }

    @OnClick({R.id.card_userinfor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.card_userinfor:
                startActivity(new Intent(context,Register_Activity.class));
                break;
        }
    }
}
