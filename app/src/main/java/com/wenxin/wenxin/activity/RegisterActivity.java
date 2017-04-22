package com.wenxin.wenxin.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.wenxin.wenxin.R;
import com.wenxin.wenxin.Url;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by linSir on 17/3/13.注册界面
 */
public class RegisterActivity extends AppCompatActivity {


    @BindView(R.id.ac_register_username) EditText userName;
    @BindView(R.id.ac_register_userphone) EditText userPhone;
    @BindView(R.id.ac_register_userpwd) EditText userPwd;
    @BindView(R.id.ac_register_userpwd2) EditText userPwd2;


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ac_register_register)
    public void onClickRegister() {

        if (userName.getText().toString().equals("") ||
                userPhone.getText().toString().equals("") ||
                userPwd.getText().toString().equals("") ||
                userPwd2.getText().toString().equals("")) {
            Toast.makeText(RegisterActivity.this, "请检查输入，输入的所有值不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!userPwd.getText().toString().equals(userPwd2.getText().toString())){
            Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }


        String url = Url.url + "register";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("user_name", userName.getText().toString().trim())
                .addParams("user_phone", userPhone.getText().toString().trim())
                .addParams("pwd", userPwd.getText().toString().trim())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        if (response.equals("100")) {
                            Toast.makeText(RegisterActivity.this, "注册成功,请登录", Toast.LENGTH_SHORT).show();
                            finish();
                        } else if (response.equals("101")) {
                            Toast.makeText(RegisterActivity.this, "注册失败--用户已存在", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }


}
