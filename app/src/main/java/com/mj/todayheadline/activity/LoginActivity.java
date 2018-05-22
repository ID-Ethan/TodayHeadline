package com.mj.todayheadline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mj.todayheadline.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivUser;
    private EditText etEmail;
    private Button showPwdBtn;
    private EditText etPassword;
    private Button loginBtn;
    /*密码是否隐藏，默认隐藏为true*/
    private boolean isPasswordHide = true;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-05-21 16:30:56 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        ivUser = (ImageView) findViewById(R.id.iv_user);
        etEmail = (EditText) findViewById(R.id.et_email);
        showPwdBtn = (Button) findViewById(R.id.showPwdBtn);
        etPassword = (EditText) findViewById(R.id.et_password);
        loginBtn = (Button) findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(this);
        showPwdBtn.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-05-21 16:30:56 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showPwdBtn:
                showOrHidePwd();
                break;
            case R.id.login_btn:
                login();
                break;
        }
    }

    /*显示和隐藏密码*/
    private void showOrHidePwd() {
        if (isPasswordHide) {   //显示密码
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            //修改密码图标
            showPwdBtn.setBackgroundResource(R.drawable.password_show);
            isPasswordHide = false;
        } else {    //隐藏密码
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showPwdBtn.setBackgroundResource(R.drawable.password_hide);
            isPasswordHide = true;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化组件
        findViews();
    }


    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-05-21 14:53:59 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */

    /*登录*/
    private void login() {
        //查询数据库等耗时操作要new一个线程执行
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted Exception！");
                }
            }
        }
        ).start();
        if ("admin".equals(etEmail.getText().toString()) &&
                "admin".equals(etPassword.getText().toString())) {
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
            //跳转
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Faild!!!", Toast.LENGTH_SHORT).show();
        }
    }


}
