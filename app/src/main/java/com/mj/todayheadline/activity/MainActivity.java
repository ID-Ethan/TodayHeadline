package com.mj.todayheadline.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.mj.todayheadline.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isLogin = false;    //登录标志，默认是未登录
    private RadioButton rbUserCenter;
    SharedPreferences sharedPreferences;    //用于获取登录标志

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化视图组件
        initView();
        //判断是否登录
        isLogin();
        //设置点击事件
        rbUserCenter.setOnClickListener(this);


    }

    /*检测用户是否登录*/
    private void isLogin() {
        sharedPreferences = getSharedPreferences("news", Context.MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin) {
            //修改UI
            rbUserCenter.setText("我");
        }
    }


    private void initView() {
        rbUserCenter = findViewById(R.id.rb_user_center);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_user_center:
                if (!isLogin) {
                    //跳转登录
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}
