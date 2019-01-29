package com.lulian.Zaiyunbao.ui.activity.leaseorder;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/11/6.
 */

public class LeaseOrderActivity extends BaseActivity {


    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.tab_lease_layout)
    TabLayout tabLeaseLayout;
    @BindView(R.id.view_lease_pager)
    ViewPager viewLeasePager;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_lease_order;
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

        textDetailContent.setText("租赁订单");
        textDetailRight.setVisibility(View.GONE);


        PagerAdapter adapter = new LeaseOrderViewPagerAdapter(this.getSupportFragmentManager());
        viewLeasePager.setAdapter(adapter);
        tabLeaseLayout.setupWithViewPager(viewLeasePager);
        tabLeaseLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

}
