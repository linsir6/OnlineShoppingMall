package com.wenxin.wenxin.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;
import com.wenxin.wenxin.R;
import com.wenxin.wenxin.Url;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by linSir on 17/3/16.商品详情页面
 */
public class GoodsDetailsActivity extends AppCompatActivity {

    private ImageView img;
    private TextView title;
    private TextView details;
    private TextView price;
    private TextView go_shopping_car;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        price = (TextView) findViewById(R.id.ac_goods_details_price);
        img = (ImageView) findViewById(R.id.ac_goods_details_img);
        title = (TextView) findViewById(R.id.ac_goods_details_name);
        details = (TextView) findViewById(R.id.ac_goods_details_details);
        go_shopping_car = (TextView) findViewById(R.id.ac_goods_details_car);

        Intent intent = getIntent();
        title.setText(intent.getExtras().getString("title"));
        details.setText(intent.getExtras().getString("details"));
        price.setText(intent.getExtras().getString("price"));
        Picasso
                .with(this)
                .load(Url.url + "static/img/" + intent.getExtras().get("img") + ".png")
                .into(img);

        go_shopping_car.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = getIntent();
                String url = Url.url + "save_in_car";
                SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
                String phone = pref.getString("user_phone", "null");
                OkHttpUtils
                        .get()
                        .url(url)
                        .addParams("name", intent.getExtras().getString("title"))
                        .addParams("price", intent.getExtras().getString("price"))
                        .addParams("type", intent.getExtras().getString("type"))
                        .addParams("img", intent.getExtras().getString("img"))
                        .addParams("details", intent.getExtras().getString("details"))
                        .addParams("count", "1")
                        .addParams("userId", phone)
                        .build()
                        .execute(new StringCallback() {
                                     @Override
                                     public void onError(Request request, Exception e) {

                                     }

                                     @Override
                                     public void onResponse(String response) {
                                         Toast.makeText(GoodsDetailsActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
                                         finish();
                                     }
                                 }
                        );
            }
        });
    }
}








