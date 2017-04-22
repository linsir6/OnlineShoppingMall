package com.wenxin.wenxin.adapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenxin.wenxin.R;
import com.wenxin.wenxin.fragment.MeFragment;
import com.wenxin.wenxin.fragment.ShoppingCarFragment;
import com.wenxin.wenxin.fragment.ShoppingFragment;
import com.wenxin.wenxin.fragment.SortFragment;

/**
 * Created by linSir on 17/3/10.主界面的适配器
 */
public class MainActivityAdapter extends FragmentPagerAdapter {
    private Context context;
    private View view;
    private ImageView iv;
    private Fragment[] fragments = {new ShoppingFragment(), new SortFragment(), new ShoppingCarFragment(), new MeFragment()};
    private int[] imgSelectIds = {R.mipmap.icon_home_press, R.mipmap.icon_hot_press, R.mipmap.icon_discover_press, R.mipmap.icon_user_press};
    private int[] imgNormalIds = {R.mipmap.icon_home, R.mipmap.icon_hot, R.mipmap.icon_discover, R.mipmap.icon_user};
    private String[] title = {"商城", "分类", "购物车", "我的"};

    public MainActivityAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

    }

    public void setupTabLayout(final TabLayout tabLayout, final ViewPager viewPager) {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
                View view = tab.getCustomView();
                ImageView iv = (ImageView) view.findViewById(R.id.iv_item_custom_tab);
                iv.setImageResource(imgSelectIds[tab.getPosition()]);
                TextView tv = (TextView) view.findViewById(R.id.tv_item_custom_tab);
                tv.setTextColor(0xffffc107);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                ImageView iv = (ImageView) view.findViewById(R.id.iv_item_custom_tab);
                iv.setImageResource(imgNormalIds[tab.getPosition()]);
                TextView tv = (TextView) view.findViewById(R.id.tv_item_custom_tab);
                tv.setTextColor(0xffaeaeae);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    public View getTabView(int position, Context context, boolean select) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_tablayout_item, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_item_custom_tab);
        iv = (ImageView) view.findViewById(R.id.iv_item_custom_tab);
        tv.setText(title[position]);
        if (select) {
            iv.setImageResource(imgSelectIds[position]);
            tv.setTextColor(0xffffc107);
        } else {
            iv.setImageResource(imgNormalIds[position]);
            tv.setTextColor(0xffaeaeae);
        }
        return view;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

}
