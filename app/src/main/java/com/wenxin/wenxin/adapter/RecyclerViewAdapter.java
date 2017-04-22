package com.wenxin.wenxin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wenxin.wenxin.Model.GoodsModel;
import com.wenxin.wenxin.R;
import com.wenxin.wenxin.Url;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linSir on 17/3/14.所有产品界面的适配器
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int FOOTER_TYPE = 0;//最后一个的类型
    public static final int HAS_IMG_TYPE = 1;//有图片的类型

    private List<GoodsModel> dataList;

    private ProgressBar mProgress;
    private TextView mNoMore;

    private Context context;

    public RecyclerViewAdapter(Context context) {
        dataList = new ArrayList<>();
        this.context = context;
    }

    public void addData(List<GoodsModel> list) {
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_TYPE) {
            return new FooterItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false));
        } else {
            return new AllAddressAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == FOOTER_TYPE) {
            bindFooterView((FooterItemViewHolder) holder);
        } else {

            if (dataList.get(position) != null) {
                try {
                    bindView((AllAddressAdapterViewHolder) holder, dataList.get(position));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return FOOTER_TYPE;
        } else {
            GoodsModel news = dataList.get(position);
            return HAS_IMG_TYPE;
        }
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    private void bindView(AllAddressAdapterViewHolder holder, GoodsModel data) throws IOException {

        holder.price.setText(data.getPrice());
        holder.title.setText(data.getName());
        Log.i("lin", "---lin--->" + Url.url + "static/img/" + data.getImg());
        Picasso
                .with(context)
                .load(Url.url + "static/img/" + data.getImg() + ".png")
                .resize(176, 200)
                .into(holder.img);
    }


    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size() + 1;
    }

    public static class AllAddressAdapterViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView title;
        private TextView price;


        public AllAddressAdapterViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.item_shopping_img);
            title = (TextView) itemView.findViewById(R.id.item_shopping_title);
            price = (TextView) itemView.findViewById(R.id.item_shopping_price);

        }
    }

    /**
     * 刷新列表
     */
    public void refreshList(List<GoodsModel> list) {
        dataList.clear();
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 加载更多
     */
    public void loadMoreList(List<GoodsModel> list) {
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 显示没有更多
     */
    public void showNoMore() {
        if (getItemCount() > 0) {
            if (mProgress != null && mNoMore != null) {
                mNoMore.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.GONE);
            }
        }
    }


    /**
     * 显示加载更多
     */
    public void showLoadMore() {
        if (mProgress != null && mNoMore != null) {
            mProgress.setVisibility(View.VISIBLE);
            mNoMore.setVisibility(View.GONE);
        }
    }

    private void bindFooterView(FooterItemViewHolder viewHolder) {
        mProgress = viewHolder.mProgress;
        mNoMore = viewHolder.tvNoMore;
    }


    public static class FooterItemViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar mProgress;
        private TextView tvNoMore;

        public FooterItemViewHolder(View itemView) {
            super(itemView);
            mProgress = (ProgressBar) itemView.findViewById(R.id.pb_footer_load_more);
            tvNoMore = (TextView) itemView.findViewById(R.id.tv_footer_no_more);
        }
    }


    /**
     * 获取点击的 item,如果是最后一个,则返回 null
     */
    public GoodsModel getClickItem(int position) {
        if (position < dataList.size()) {
            return dataList.get(position);
        } else {
            return null;
        }
    }


}
