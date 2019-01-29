package com.lulian.Zaiyunbao.ui.base;

/**
 * Created by Administrator on 2018/3/9.
 */

public class DeviceFragmentInfo {
    private String title;
    private Class fragment;

    public DeviceFragmentInfo(String title, Class fragment) {
        this.fragment = fragment;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }


}
