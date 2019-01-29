package com.lulian.Zaiyunbao.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class FragmentAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragmentList;
    ArrayList<String> titles;

    public FragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList, ArrayList<String> titles) {
        super(fm);
        fm.beginTransaction().commitAllowingStateLoss();
        this.fragmentList = fragmentList;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        if (fragmentList != null) {
            return fragmentList.size();
        } else {
            return 0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null) {
            return titles.get(position);
        } else {
            return null;
        }
    }
}
