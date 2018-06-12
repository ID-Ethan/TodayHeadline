package com.mj.todayheadline.activity;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mj.todayheadline.R;
import com.mj.todayheadline.domain.Content;
import com.mj.todayheadline.util.DBHelper;
import com.mj.todayheadline.view.FragmentIndex;
import com.mj.todayheadline.view.FragmentMe;
import com.mj.todayheadline.view.FragmentVideo;
import com.mj.todayheadline.view.FragmentWei;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup
        .OnCheckedChangeListener {
    private boolean isLogin = false;    //登录标志，默认是未登录
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;    //用于获取登录标志
    private final int USER_LOGIN_STATE = 100;
    private FrameLayout flMainContent;
    private RadioGroup rgBottomTag;
    private RadioButton rbUserCenter;
    private RadioButton rbIndex;
    private RadioButton rbVideo;
    private RadioButton rbWeiTouTiao;


    private FragmentIndex fragmentIndex;
    private FragmentVideo fragmentVideo;
    private FragmentWei fragmentWei;
    private FragmentMe fragmentMe;
    private String[] fragmentTags = {"fragmentIndex", "fragmentVideo", "fragmentWei", "fragmentMe"};
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case USER_LOGIN_STATE:
                    //检查isLogin的状态
                    if (!isLogin) {
                        //注销登录或者未登录
                        rbUserCenter.setText("未登录");
                    } else {
                        rbUserCenter.setText("我");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化视图组件
        initView();
        //授权
        isGrantExternalRW(MainActivity.this);
        //设置RadioGroup的监听
        rgBottomTag.setOnCheckedChangeListener(this);
        //设置点击事件
        rbUserCenter.setOnClickListener(this);
        //判断是否登录
        isLogin();
        //加载默认页面
        initDefultView();
    }

    /**
     * 确认授权：sdcard读写权限
     */
    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET

            }, 1);
            return false;
        }
        return true;
    }

    private void initDefultView() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        FragmentIndex fragmentIndex = new FragmentIndex();
        ft.add(R.id.fl_main_content, fragmentIndex);
        ft.commit();
    }

    /*检测用户是否登录*/
    private void isLogin() {
        sharedPreferences = getSharedPreferences("news", Context.MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin) {
            //发送消息修改UI
            handler.sendEmptyMessage(USER_LOGIN_STATE);
        }
    }

    /*初始化视图组件*/
    private void initView() {
        flMainContent = findViewById(R.id.fl_main_content);
        rgBottomTag = findViewById(R.id.rg_bottom_tag);
        rbUserCenter = findViewById(R.id.rb_user_center);
        rbIndex = findViewById(R.id.rb_index);
        rbVideo = findViewById(R.id.rb_video);
        rbWeiTouTiao = findViewById(R.id.rb_weitoutiao);
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
//                else {
//                    //弹出对话框注销
//                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
//                            .setTitle("zzzzzzZZZZZZ")
//                            .setMessage("是否注销？")
//                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Toast.makeText(MainActivity.this, "取消注销~", Toast.LENGTH_SHORT)
//                                            .show();
//                                    dialog.dismiss();
//                                }
//                            })
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Toast.makeText(MainActivity.this, "正在注销...", Toast
// .LENGTH_SHORT)
//                                            .show();
//                                    //修改登录状态
//                                    editor = sharedPreferences.edit();
//                                    editor.putBoolean("isLogin", false);
//                                    isLogin = false;
//                                    //发送信息修改UI
//                                    handler.sendEmptyMessageDelayed(USER_LOGIN_STATE, 200);
//                                    dialog.dismiss();
//                                }
//                            }).show();
//                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (checkedId) {
            default:
                //加载首页
                if (fragmentIndex == null) {
                    fragmentIndex = new FragmentIndex();
                    fragmentTransaction.replace(R.id.fl_main_content, fragmentIndex,
                            fragmentTags[0]);
                    fragmentTransaction.addToBackStack(fragmentTags[0]);
                } else {
                    Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags[0]);
                    fragmentTransaction.replace(R.id.fl_main_content, fragment, fragmentTags[0]);
                }
                fragmentTransaction.commit();
                break;
            case R.id.rb_video:
                //加载西瓜视频
                if (fragmentVideo == null) {
                    fragmentVideo = new FragmentVideo();
                    fragmentTransaction.replace(R.id.fl_main_content, fragmentVideo,
                            fragmentTags[1]);
                    fragmentTransaction.addToBackStack(fragmentTags[1]);
                } else {
                    Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags[1]);
                    fragmentTransaction.replace(R.id.fl_main_content, fragment, fragmentTags[1]);
                }
                fragmentTransaction.commit();
                break;
            case R.id.rb_weitoutiao:
                //加载微头条
                if (fragmentWei == null) {
                    fragmentWei = new FragmentWei();
                    fragmentTransaction.replace(R.id.fl_main_content, fragmentWei, fragmentTags[2]);
                    fragmentTransaction.addToBackStack(fragmentTags[2]);
                } else {
                    Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags[2]);
                    fragmentTransaction.replace(R.id.fl_main_content, fragment, fragmentTags[2]);
                }
                fragmentTransaction.commit();
                break;
            case R.id.rb_user_center:
                //加载用户中心
                if (fragmentMe == null) {
                    fragmentMe = new FragmentMe();
                    fragmentTransaction.replace(R.id.fl_main_content, fragmentMe, fragmentTags[3]);
                    fragmentTransaction.addToBackStack(fragmentTags[3]);
                } else {
                    Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags[3]);
                    fragmentTransaction.replace(R.id.fl_main_content, fragment, fragmentTags[3]);
                }
                fragmentTransaction.commit();
                break;
        }
    }
}
