package com.wenxin.wenxin.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
 * Created by linSir on 17/3/13.登录界面
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.ac_login_userName) EditText username;
    @BindView(R.id.ac_login_pwd) EditText pwd;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ac_login_login)
    public void login() {


        String url = Url.url + "login";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("user_phone", username.getText().toString().trim())
                .addParams("pwd", pwd.getText().toString().trim())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response) {
                        if (response.split(",")[0].equals("100")) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user_name", response.split(",")[1]);
                            editor.putString("user_phone", username.getText().toString().trim());
                            editor.putString("user_pwd", username.getText().toString().trim());
                            editor.apply();


                            finish();
                        } else if (response.equals("101")) {
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }

    @OnClick(R.id.ac_login_register)
    public void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
