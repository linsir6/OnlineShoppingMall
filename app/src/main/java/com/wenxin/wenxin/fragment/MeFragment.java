package com.wenxin.wenxin.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wenxin.wenxin.R;
import com.wenxin.wenxin.activity.AboutMeActivity;
import com.wenxin.wenxin.activity.MeOrderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by linSir on 17/3/10.我的个人界面
 */
public class MeFragment extends Fragment {

    @BindView(R.id.fr_me_user_name) TextView userName;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences pref = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        String name = pref.getString("user_name", "null");
        userName.setText(name);
        return view;

    }

    @OnClick(R.id.fr_me_order)
    public void onClick_me_order() {
        Intent intent = new Intent(getActivity(), MeOrderActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.fr_me_about_me)
    public void onclick_me_about_me() {
        Intent intent = new Intent(getActivity(), AboutMeActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.fr_me_check_app)
    public void onclick_me_check_app() {
        Toast.makeText(getActivity(), "当前版本，1.0.0，已经是最新版本了", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fr_me_quit)
    public void onclick_me_quit() {

    }


}
