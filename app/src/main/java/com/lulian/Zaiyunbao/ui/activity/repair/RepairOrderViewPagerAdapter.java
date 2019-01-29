package com.lulian.Zaiyunbao.ui.activity.repair;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lulian.Zaiyunbao.ui.base.FragmentInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/29.
 */

public class RepairOrderViewPagerAdapter extends FragmentPagerAdapter {
    private List<FragmentInfo> mFragment = new ArrayList<>(2);

    public RepairOrderViewPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragment();
    }


    private void initFragment() {
        mFragment.add(new FragmentInfo("处理中", new RepairOrderFragment("1")));
        mFragment.add(new FragmentInfo("已完成", new RepairOrderFragment("2")));
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
