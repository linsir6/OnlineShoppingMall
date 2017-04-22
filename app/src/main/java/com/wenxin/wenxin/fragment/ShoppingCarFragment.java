package com.wenxin.wenxin.fragment;

import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.wenxin.wenxin.Model.GoodsModel;
import com.wenxin.wenxin.R;
import com.wenxin.wenxin.Url;
import com.wenxin.wenxin.adapter.RecyclerItemClickListener;
import com.wenxin.wenxin.adapter.ShoppingCarAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by linSir on 17/3/10.购物车界面
 */
public class ShoppingCarFragment extends Fragment {

    private RecyclerView recyclerView;
    private ShoppingCarAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private Button pay, delete;
    private List<GoodsModel> mList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_car, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fr_shopping_car_list);
        pay = (Button) view.findViewById(R.id.fr_shopping_pay);
        delete = (Button) view.findViewById(R.id.fr_shopping_clear_car);
        adapter = new ShoppingCarAdapter(getActivity());
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        //recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), onItemClickListener));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        pay.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Map<Integer, Boolean> checked = adapter.getChecked();
                String text = "";
                for (Map.Entry<Integer, Boolean> entry : checked.entrySet()) {
                    if (entry.getValue()) {
                        text += mList.get(entry.getKey()).getId() + ",";
                    }
                }

                if (text.equals("")) {
                    Toast.makeText(getActivity(), "请选择结算的产品，才能结算", Toast.LENGTH_SHORT).show();
                    return;
                }


                String url = Url.url + "pay";
                OkHttpUtils
                        .get()
                        .url(url)
                        .addParams("ids", text)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Request request, Exception e) {

                            }

                            @Override
                            public void onResponse(String response) {
                                if (response.equals("100")) {
                                    Toast.makeText(getActivity(), "购买成功", Toast.LENGTH_SHORT).show();
                                    getShoppingCar();
                                }
                            }
                        });

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Map<Integer, Boolean> checked = adapter.getChecked();
                String text = "";
                for (Map.Entry<Integer, Boolean> entry : checked.entrySet()) {
                    if (entry.getValue()) {
                        text += mList.get(entry.getKey()).getId() + ",";
                    }
                }

                if (text.equals("")) {
                    Toast.makeText(getActivity(), "请选择要删除的商品", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = Url.url + "delete_shopping_car";

                Log.i("lin", "---lin's log--->   test    " + text);

                OkHttpUtils
                        .get()
                        .url(url)
                        .addParams("ids", text)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Request request, Exception e) {
                                Log.i("lin", "---lin's log--->   error   " + e.toString());
                            }

                            @Override
                            public void onResponse(String response) {
                                Log.i("lin", "---lin's log--->   response    " + response);
                                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                                getShoppingCar();
                            }
                        });

            }
        });

        return view;
    }

    @Override public void onResume() {
        super.onResume();
        getShoppingCar();
    }

    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
//            Intent intent = new Intent(getActivity(), TravelNotesActivity.class);
//            intent.putExtra("id", position);
//            startActivity(intent);
        }
    };


    public void getShoppingCar() {
        String url = Url.url + "get_car_product";
        SharedPreferences pref = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        String phone = pref.getString("user_phone", "null");
        OkHttpUtils
                .get()
                .url(url)
                .addParams("id", phone)
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
                                mList = list;
                            }
                            adapter.refreshList(list);

                        } catch (Exception e) {
                            Log.i("lin", "---lin---> 22" + e.toString());
                        }
                    }
                });
    }

}












