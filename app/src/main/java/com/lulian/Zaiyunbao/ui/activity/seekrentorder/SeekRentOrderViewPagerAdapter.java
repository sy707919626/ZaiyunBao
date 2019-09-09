package com.lulian.Zaiyunbao.ui.activity.seekrentorder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lulian.Zaiyunbao.ui.base.FragmentInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/29.
 */

public class SeekRentOrderViewPagerAdapter extends FragmentPagerAdapter {

    private List<FragmentInfo> mFragment = new ArrayList<>(7);

    public SeekRentOrderViewPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragment();
    }

    //未接单 = 0, 已接单 = 1,订单确认待支付 = 2,
    // 已支付待发货 = 3,已发货 = 5,已收货 = 6,退租中 = 7,已退租 = 8,已结算 = 9,已撤销 = 10
    private void initFragment() {
        mFragment.add(new FragmentInfo("全部", new SeekRentOrderFragment("")));
        mFragment.add(new FragmentInfo("待接单", new SeekRentOrderFragment("0")));
        mFragment.add(new FragmentInfo("待确认", new SeekRentOrderFragment("1")));
        mFragment.add(new FragmentInfo("待支付", new SeekRentOrderFragment("2")));
        mFragment.add(new FragmentInfo("待收货", new SeekRentOrderFragment("5")));
        mFragment.add(new FragmentInfo("已收货", new SeekRentOrderFragment("6")));
        mFragment.add(new FragmentInfo("已退租", new SeekRentOrderFragment("8")));
    }

    @Override
    public Fragment getItem(int position) {
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
