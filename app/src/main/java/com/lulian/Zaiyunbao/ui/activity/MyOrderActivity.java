package com.lulian.Zaiyunbao.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.ui.activity.buyorder.BuyOrderActivity;
import com.lulian.Zaiyunbao.ui.activity.leaseorder.LeaseOrderActivity;
import com.lulian.Zaiyunbao.ui.activity.myorder.OrderViewPagerAdapter;
import com.lulian.Zaiyunbao.ui.activity.rentback.RentBackActivity;
import com.lulian.Zaiyunbao.ui.activity.seekrentorder.SeekRentOrderActivity;
import com.lulian.Zaiyunbao.ui.activity.subleaseorder.SubleaseOrderActivity;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/11/6.
 */

public class MyOrderActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.rbtn_zulin)
    Button rbtnZulin;
    @BindView(R.id.rbtn_zhuanzu)
    Button rbtnZhuanzu;
    @BindView(R.id.rbtn_qiuzu)
    Button rbtnQiuzu;
    @BindView(R.id.rbtn_goumai)
    Button rbtnGoumai;
    @BindView(R.id.rbtn_tuizu)
    Button rbtnTuizu;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.order_view)
    LinearLayout orderView;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void init() {
        ImmersionBar.with(this)
                .titleBar(R.id.detail_bar_title)
                .titleBarMarginTop(R.id.detail_bar_title)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        textDetailContent.setText("我的订单");
        textDetailRight.setVisibility(View.GONE);
        PagerAdapter adapter = new OrderViewPagerAdapter(this.getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }



    @OnClick({R.id.rbtn_zulin, R.id.rbtn_zhuanzu, R.id.rbtn_qiuzu, R.id.rbtn_goumai, R.id.rbtn_tuizu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.rbtn_zulin:
//                //租赁
//                startActivity(new Intent(MyOrderActivity.this, LeaseOrderActivity.class));
//
//                break;
            case R.id.rbtn_zhuanzu:
                //转租
                startActivity(new Intent(MyOrderActivity.this, SubleaseOrderActivity.class));
                break;

            case R.id.rbtn_qiuzu:
                //求租
                startActivity(new Intent(MyOrderActivity.this, SeekRentOrderActivity.class));
                break;

            case R.id.rbtn_goumai:
                //购买
                startActivity(new Intent(MyOrderActivity.this, BuyOrderActivity.class));
                break;

            case R.id.rbtn_tuizu:
                //退租
                startActivity(new Intent(MyOrderActivity.this, RentBackActivity.class));
                break;
        }
    }
}
