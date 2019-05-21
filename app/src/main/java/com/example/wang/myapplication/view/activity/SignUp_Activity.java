package com.example.wang.myapplication.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.utils.RegexUtils;
import  com.example.wang.myapplication.Application;
import  com.example.wang.myapplication.R;
import  com.example.wang.myapplication.presenter.SignUp_ActivityPresenter;
import  com.example.wang.myapplication.view.iview.ISignUpView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUp_Activity extends AppCompatActivity implements ISignUpView {

    public Context context;
    @BindView(R.id.tv_signup_name)
    EditText tvSignupName;
    @BindView(R.id.tv_signup_bj)
    EditText tvSignupBj;
    @BindView(R.id.tv_signup_tel)
    EditText tvSignupTel;
    @BindView(R.id.tv_signup_infor)
    EditText tvSignupInfor;
    @BindView(R.id.btn_signup)
    Button btnSignup;
    private ProgressDialog dialog;
    private String sponsor;

    private SignUp_ActivityPresenter signUpActivityPresenter = new SignUp_ActivityPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Application.theme);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("报名");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        sponsor = (String) getIntent().getSerializableExtra("sponsor");
        inti();
    }

    public void inti() {
        context = SignUp_Activity.this;
        dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("正在提交评论...");

        signUpActivityPresenter.setdata();
    }


    @Override
    public String getname() {
        return tvSignupName.getText().toString().trim();
    }

    @Override
    public String gettel() {
        return tvSignupTel.getText().toString().trim();
    }

    @Override
    public String getbj() {
        return tvSignupBj.getText().toString().trim();
    }

    @Override
    public void setname() {
        String name =  com.example.wang.myapplication.utils.FileTools.getshareString("name");
        if (!"".equals(name)) {
            tvSignupName.setText(name);
        }
    }

    @Override
    public void setbj() {
        String bj =  com.example.wang.myapplication.utils.FileTools.getshareString("banji");
        if (!"".equals(bj)) {
            tvSignupBj.setText(bj);
        }
    }

    @Override
    public void settel() {
        String tel =  com.example.wang.myapplication.utils.FileTools.getshareString("tel");
        if (!"".equals(tel)) {
            tvSignupTel.setText(tel);
        }
    }

    @Override
    public void showdialog() {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void hidedialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void setenabletrue() {
        btnSignup.setEnabled(true);
        btnSignup.setText("提交");
    }

    @Override
    public void setenablefalse() {
        btnSignup.setEnabled(false);
        btnSignup.setText("提交中...");
    }

    @Override
    public void SignUpSuccess() {
         com.example.wang.myapplication.utils.Toast.show("报名成功");
    }

    @Override
    public void SignUpFailed() {
         com.example.wang.myapplication.utils.Toast.show("提交失败");
    }

    @OnClick(R.id.btn_signup)
    public void onViewClicked() {
        String name = tvSignupName.getText().toString().trim();
        String bj = tvSignupBj.getText().toString().trim();
        String tel = tvSignupTel.getText().toString().trim();

        if (name.isEmpty() || bj.isEmpty() || tel.isEmpty()) {
             com.example.wang.myapplication.utils.Toast.show("不能为空");
            return;
        }
        if (!RegexUtils.isMobileExact(tel)) {
             com.example.wang.myapplication.utils.Toast.show("手机号好像不正确啊");
            return;
        }
        String infor = tvSignupInfor.getText().toString().trim();
        signUpActivityPresenter.putsignup(sponsor, name, tel, bj, infor);
    }


}
