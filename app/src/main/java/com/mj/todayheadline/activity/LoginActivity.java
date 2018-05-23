package com.mj.todayheadline.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mj.todayheadline.R;
import com.mj.todayheadline.util.DBHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivUser;
    private EditText etUsername;
    private Button showPwdBtn;
    private EditText etPassword;
    private CheckBox cbRememberPwd;
    private CheckBox cbAutoLogin;
    private Button loginBtn;
    private ProgressBar pbLogin;
    private Button btnToRegister;
    /*密码是否隐藏的标志*/
    private boolean isPasswordHide = true;
    /*数据库工具类*/
    private DBHelper dbHelper;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-05-22 10:55:53 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        ivUser = (ImageView) findViewById(R.id.iv_user);
        etUsername = (EditText) findViewById(R.id.et_username);
        showPwdBtn = (Button) findViewById(R.id.showPwdBtn);
        etPassword = (EditText) findViewById(R.id.et_password);
        cbRememberPwd = (CheckBox) findViewById(R.id.cb_remember_pwd);
        cbAutoLogin = (CheckBox) findViewById(R.id.cb_auto_login);
        loginBtn = (Button) findViewById(R.id.login_btn);
        pbLogin = (ProgressBar) findViewById(R.id.pb_login);
        btnToRegister = findViewById(R.id.btn_to_register);

        showPwdBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        btnToRegister.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-05-22 10:55:53 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == showPwdBtn) {
            // Handle clicks for showPwdBtn
            showOrHidePwd();
        } else if (v == loginBtn) {
            // Handle clicks for loginBtn
            login(etUsername.getText().toString(), etPassword.getText().toString());
        } else if (v == btnToRegister) {
            //跳转到注册页面
            Intent skipIntent = new Intent(this, RegisterActivity.class);
            startActivity(skipIntent);
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
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                    .TYPE_TEXT_VARIATION_PASSWORD);
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
        //确认授权
        isGrantExternalRW(this);
        //自定义存储xml文件，文件名为：news
        sharedPreferences = getSharedPreferences("news", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //如果本地有已经记住的账户，自动填写username和password
        etUsername.setText(sharedPreferences.getString("username", null));
        etPassword.setText(sharedPreferences.getString("password", null));
        cbRememberPwd.setChecked(sharedPreferences.getBoolean("cbRememberPwd", false));
        boolean isAutoLogin = sharedPreferences.getBoolean("cbAutoLogin", false);
        if (isAutoLogin) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-05-21 14:53:59 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */

    /*登录*/
    private void login(String username, String password) {
        //显示进度条
        pbLogin.setVisibility(View.VISIBLE);
        dbHelper = new DBHelper(this);
        if (dbHelper.login(username, password)) {
            if (cbRememberPwd.isChecked()) {    //记住密码已经选中
                editor.putString("username", username);
                editor.putString("password", password);
                editor.putBoolean("cbRememberPwd", true);
                editor.commit();
                if (cbAutoLogin.isChecked()) {  //自动登录已经选中
                    editor.putBoolean("cbAutoLogin", true);
                    editor.commit();
                }
            }
            Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
            //跳转
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "用户名/密码错误，请重新登录！", Toast.LENGTH_SHORT).show();
            etUsername.setText("");
            etPassword.setText("");
            pbLogin.setVisibility(View.GONE);   //隐藏进度条
        }
    }

    /**
     * 确认授权（SD卡读写权限）
     */
    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);
            return false;
        }
        return true;
    }


}
