package com.wenxin.wenxin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.squareup.okhttp.Request;
import com.wenxin.wenxin.Model.GoodsModel;
import com.wenxin.wenxin.R;
import com.wenxin.wenxin.Url;
import com.wenxin.wenxin.activity.GoodsDetailsActivity;
import com.wenxin.wenxin.adapter.RecyclerItemClickListener;
import com.wenxin.wenxin.adapter.SortRecyclerViewAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by linSir on 17/3/10.分类界面
 */
public class SortFragment extends Fragment {

    @BindView(R.id.fr_sort_recyclerView2) RecyclerView recyclerView;
    @BindView(R.id.fr_sort_radioButton1) RadioButton radioButton;
    @BindView(R.id.fr_sort_radioButton2) RadioButton radioButton2;
    @BindView(R.id.fr_sort_radioButton3) RadioButton radioButton3;
    @BindView(R.id.fr_sort_radioButton4) RadioButton radioButton4;


    private SortRecyclerViewAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private List<GoodsModel> mList;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        ButterKnife.bind(this, view);
        adapter = new SortRecyclerViewAdapter(getContext());
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), onItemClickListener));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        String url = Url.url + "get_sort_product";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("type", "Android")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.i("lin", "---lin---> 33" + e.toString());
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.i("lin", "---lin---> 11");
                        try {
                            JSONArray jsonArray = new JSONArray(response);


                            List<GoodsModel> list = new ArrayList<GoodsModel>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.i("lin", "---lin---->" + jsonArray.getJSONObject(i));
                                GoodsModel obj = new GoodsModel(jsonArray.getJSONObject(i).getString("id"), jsonArray.getJSONObject(i).getString("name"), "￥ " + jsonArray.getJSONObject(i).getString("price"), jsonArray.getJSONObject(i).getString("type"), jsonArray.getJSONObject(i).getString("img"), jsonArray.getJSONObject(i).getString("details"));
                                list.add(obj);
                            }
                            adapter.refreshList(list);
                            mList = list;

                        } catch (Exception e) {
                            Log.i("lin", "---lin---> 22" + e.toString());
                        }

                    }
                });

        return view;
    }

    @OnCheckedChanged({R.id.fr_sort_radioButton1, R.id.fr_sort_radioButton2, R.id.fr_sort_radioButton3, R.id.fr_sort_radioButton4})
    public void onRadionButtonChange() {
        String type = "";
        if (radioButton.isChecked()) {
            type = "Android";
        } else if (radioButton2.isChecked()) {
            type = "iPhone";
        } else if (radioButton3.isChecked()) {
            type = "充电宝";
        } else if (radioButton4.isChecked()) {
            type = "笔记本电脑";
        }

        String url = Url.url + "get_sort_product";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("type", type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.i("lin", "---lin---> 33" + e.toString());
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.i("lin", "---lin---> 11");
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            List<GoodsModel> list = new ArrayList<GoodsModel>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.i("lin", "---lin---->" + jsonArray.getJSONObject(i));
                                GoodsModel obj = new GoodsModel(jsonArray.getJSONObject(i).getString("id"),
                                        jsonArray.getJSONObject(i).getString("name"),
                                        "￥ " + jsonArray.getJSONObject(i).getString("price"),
                                        jsonArray.getJSONObject(i).getString("type"),
                                        jsonArray.getJSONObject(i).getString("img"),
                                        jsonArray.getJSONObject(i).getString("details"));
                                list.add(obj);
                            }
                            mList = list;
                            adapter.refreshList(list);

                        } catch (Exception e) {
                            Log.i("lin", "---lin---> 22" + e.toString());
                        }

                    }
                });
    }

    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Intent intent = new Intent(getActivity(), GoodsDetailsActivity.class);
            intent.putExtra("img", mList.get(position).getImg());
            intent.putExtra("title", mList.get(position).getName());
            intent.putExtra("details", mList.get(position).getDetails());
            intent.putExtra("price", mList.get(position).getPrice());
            intent.putExtra("type", mList.get(position).getType());

            startActivity(intent);
        }
    };

}
