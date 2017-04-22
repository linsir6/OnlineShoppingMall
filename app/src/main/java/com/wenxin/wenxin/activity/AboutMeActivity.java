package com.wenxin.wenxin.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wenxin.wenxin.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by linSir
 * date at 2017/4/14.
 * describe:
 */

public class AboutMeActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_me_about)
    public void back(){
        finish();
    }






}
