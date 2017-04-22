package com.wenxin.wenxin.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.wenxin.wenxin.Model.GoodsModel;
import com.wenxin.wenxin.R;
import com.wenxin.wenxin.Url;
import com.wenxin.wenxin.adapter.MeOrderAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linSir on 17/3/14.我的订单界面
 */
public class MeOrderActivity extends AppCompatActivity {

    private RecyclerView meOrder;
    private MeOrderAdapter meOrderAdapter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_order);
        meOrder = (RecyclerView) findViewById(R.id.me_order_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        meOrderAdapter = new MeOrderAdapter(this);
        meOrder.setAdapter(meOrderAdapter);
        meOrder.setLayoutManager(layoutManager);

        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        String userId = pref.getString("user_phone", "null");

        String url = Url.url + "order";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("userId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(MeOrderActivity.this, "获取我的订单失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MeOrderActivity.this, "获取我的订单成功", Toast.LENGTH_SHORT).show();
                        List<GoodsModel> list = new ArrayList<GoodsModel>();

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                GoodsModel goodmodel = new GoodsModel(
                                        jsonArray.getJSONObject(i).optString("number"),
                                        jsonArray.getJSONObject(i).optString("name"),
                                        jsonArray.getJSONObject(i).optString("price"),
                                        jsonArray.getJSONObject(i).optString("type"),
                                        jsonArray.getJSONObject(i).optString("image"),
                                        jsonArray.getJSONObject(i).optString("details"));

                                list.add(goodmodel);
                            }

                            meOrderAdapter.refreshList(list);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });


    }
}
