package com.lulian.Zaiyunbao.ui.base;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2018/3/9.
 */

@SuppressLint("ValidFragment")
public class FragmentInfo extends Fragment {
    private String title;
    private Fragment fragment;

    public FragmentInfo(String title, Fragment fragment) {
        this.fragment = fragment;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }


}
