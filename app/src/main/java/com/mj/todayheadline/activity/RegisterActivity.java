package com.mj.todayheadline.activity;

import android.content.ContentValues;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mj.todayheadline.R;
import com.mj.todayheadline.util.DBHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivUserRe;
    private EditText etUsernameRe;
    private Button showPwdBtnRe;
    private EditText etPasswordRe;
    private Button registerBtn;
    private ProgressBar pbRegister;
    private boolean isPasswordHide = true;
    private DBHelper dbHelper;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-05-23 15:52:55 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        ivUserRe = (ImageView) findViewById(R.id.iv_user_re);
        etUsernameRe = (EditText) findViewById(R.id.et_username_re);
        showPwdBtnRe = (Button) findViewById(R.id.showPwdBtn_re);
        etPasswordRe = (EditText) findViewById(R.id.et_password_re);
        registerBtn = (Button) findViewById(R.id.register_btn);
        pbRegister = (ProgressBar) findViewById(R.id.pb_register);

        showPwdBtnRe.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-05-23 15:52:55 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == showPwdBtnRe) {
            // Handle clicks for showPwdBtnRe
        } else if (v == registerBtn) {
            // Handle clicks for registerBtn
            String username = etUsernameRe.getText().toString();
            String password = etPasswordRe.getText().toString();
            if ("".equals(username) || "".equals(password)) {
                Toast.makeText(this, "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
            } else {
                boolean state = register(username, password);
                if (state) {
                    Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                    finish();   //退出当前Activity
                } else {
                    Toast.makeText(this, "注册失败，请重新注册！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化组件
        findViews();
    }

    private boolean register(String username, String password) {
        dbHelper = new DBHelper(this);
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        if (dbHelper.register(contentValues) != 0) {
            return true;
        } else {
            return false;
        }
    }

    /*显示和隐藏密码*/
    private void showOrHidePwd() {
        if (isPasswordHide) {   //显示密码
            etPasswordRe.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            //修改密码图标
            showPwdBtnRe.setBackgroundResource(R.drawable.password_show);
            isPasswordHide = false;
        } else {    //隐藏密码
            etPasswordRe.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                    .TYPE_TEXT_VARIATION_PASSWORD);
            showPwdBtnRe.setBackgroundResource(R.drawable.password_hide);
            isPasswordHide = true;
        }
    }
}
