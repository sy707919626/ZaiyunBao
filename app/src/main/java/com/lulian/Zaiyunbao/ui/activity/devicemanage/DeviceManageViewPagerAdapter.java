package com.lulian.Zaiyunbao.ui.activity.devicemanage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lulian.Zaiyunbao.ui.base.DeviceFragmentInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/29.
 */

public class DeviceManageViewPagerAdapter extends FragmentPagerAdapter {

    private List<DeviceFragmentInfo> mFragment = new ArrayList<>(3);

    public DeviceManageViewPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragment();
    }


    private void initFragment() {

        mFragment.add(new DeviceFragmentInfo("全部", DeviceManageAllFragment.class));
        mFragment.add(new DeviceFragmentInfo("闲置", DeviceManageXZFragment.class));
        mFragment.add(new DeviceFragmentInfo("占用", DeviceManageZYFragment.class));
//        mFragment.add(new FragmentInfo("报修", DeviceManageBXFragment.class));
//        mFragment.add(new FragmentInfo("报废", DeviceManageBXFragment.class));
    }

    @Override
    public Fragment getItem(int position) {

        try {
            return (Fragment) mFragment.get(position).getFragment().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;

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
