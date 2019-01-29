package com.lulian.Zaiyunbao.ui.activity.myorder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lulian.Zaiyunbao.ui.base.FragmentInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/29.
 */

public class OrderViewPagerAdapter extends FragmentPagerAdapter {

    private List<FragmentInfo> mFragment = new ArrayList<>(8);

    public OrderViewPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragment();
    }

    //未接单 = 0, 已接单 = 1,订单确认待支付 = 2,
    // 已支付待发货 = 3,已发货 = 5,已收货 = 6,退租中 = 7,已退租 = 8,已结算 = 9,已撤销 = 10
    private void initFragment() {
//        mFragment.add(new FragmentInfo("全部", OrderAllFragment.class));
//        mFragment.add(new FragmentInfo("待确认", OrderReceiptFragment.class));
//        mFragment.add(new FragmentInfo("待支付", OrderWaitPayFragment.class));
//        mFragment.add(new FragmentInfo("待发货", OrderWaitFHFragment.class));
//        mFragment.add(new FragmentInfo("待收货", OrderWaitSHFragment.class));
//        mFragment.add(new FragmentInfo("已收货 ", OrderSHFragment.class));
//        mFragment.add(new FragmentInfo("已撤单 ", OrderCDFragment.class));
//        mFragment.add(new FragmentInfo("已完成 ", OrderCompleteFragment.class));
        mFragment.add(new FragmentInfo("全部", new OrderFragment("")));
        mFragment.add(new FragmentInfo("待确认", new OrderFragment("1")));
        mFragment.add(new FragmentInfo("待支付", new OrderFragment("2")));
        mFragment.add(new FragmentInfo("待发货", new OrderFragment("3")));
        mFragment.add(new FragmentInfo("待收货", new OrderFragment("5")));
        mFragment.add(new FragmentInfo("已收货 ", new OrderFragment("6")));
        mFragment.add(new FragmentInfo("已撤单 ", new OrderFragment("10")));
        mFragment.add(new FragmentInfo("已完成 ", new OrderFragment("9")));
    }

    @Override
    public Fragment getItem(int position) {

//        try {
//            return (Fragment) mFragment.get(position).getFragment().newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return null;

        return mFragment.get(position).getFragment();

    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragment.get(position).getTitle();
    }
}
