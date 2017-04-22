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

import com.squareup.okhttp.Request;
import com.wenxin.wenxin.Model.GoodsModel;
import com.wenxin.wenxin.R;
import com.wenxin.wenxin.Url;
import com.wenxin.wenxin.activity.GoodsDetailsActivity;
import com.wenxin.wenxin.adapter.RecyclerItemClickListener;
import com.wenxin.wenxin.adapter.RecyclerViewAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linSir on 17/3/10.商城首页
 */
public class ShoppingFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private List<GoodsModel> temp = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fr_shopping_list);
        adapter = new RecyclerViewAdapter(getContext());


        gridLayoutManager = new GridLayoutManager(getActivity(), 2);


        //recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), onItemClickListener));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        Log.i("lin", "---lin---> 44");
        String url = Url.url + "get_all_product";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("a", "")
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
                            adapter.loadMoreList(list);
                            temp = list;
                        } catch (Exception e) {
                            Log.i("lin", "---lin---> 22" + e.toString());
                        }

                    }
                });
        return view;
    }

    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

            Intent intent = new Intent(getActivity(), GoodsDetailsActivity.class);
            intent.putExtra("img", temp.get(position).getImg());
            intent.putExtra("title", temp.get(position).getName());
            intent.putExtra("details", temp.get(position).getDetails());
            intent.putExtra("price", temp.get(position).getPrice());
            intent.putExtra("type",temp.get(position).getType());

            startActivity(intent);
        }
    };

}
