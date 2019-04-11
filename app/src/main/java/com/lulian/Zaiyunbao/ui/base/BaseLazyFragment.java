package com.lulian.Zaiyunbao.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description： 懒加载步骤
 * 1.setUpView()初始化V
 * 2.重写enableLazyLoad() 设置是否使用懒加载
 * 3.重写isDataLoaded()为View返回是否有数据
 * 4.重写 getData()实现数据加载请求
 * 5.重写 setUpData() 为View设置数据 并且getData中获取数据后 显示调用setUpData()为View设置数据
 */

public abstract class BaseLazyFragment extends BaseFragment {

    private static final String TAG = "BaseLazyFragment";
    private boolean isViewCreated;//控件是否初始化完成
    //是否能够加载数据
    //控制 当在Fragment 创建完成时就对用户可见  导致 多次调用加载数据
    private boolean isCanLoading = true;

    public static Date stringToDate(String dateString) {
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateValue = simpleDateFormat.parse(dateString, position);
        return dateValue;
    }

    /**
     * 为View数据设置Data
     */
    protected void setUpData() {
        isCanLoading = true;
    }

    /**
     * 当Adapter为FragmentStatePagerAdapter时 可以使用
     * onSaveInstanceState 保存相关实例数据
     * 使用onRestoreInstanceState 取出相关数据
     * 从而不用重复去请求数据
     */
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    /**
     * 设置是否进行懒加载
     */
    public boolean enableLazyLoad() {
        return false;
    }

    /**
     * 是否已经加载数据
     *
     * @return
     */
    public boolean isDataLoaded() {
        return false;
    }

    /**
     * Fragment 可见时 检测是否需要加载数据
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint: " + isVisibleToUser);
        checkIfLoadData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        isViewCreated = true;
        if (!enableLazyLoad()) {
            getData();
        } else {
            if (savedInstanceState != null) {
                onRestoreInstanceState(savedInstanceState);
            }
            if (isDataLoaded()) {
                setUpData();
            } else {
                checkIfLoadData();
            }

        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void checkIfLoadData() {
        //第一次加载数据
        if (getUserVisibleHint() && isViewCreated && !isDataLoaded() && isCanLoading) {
            Log.d(TAG, "checkIfLoadData: ");
            isCanLoading = false;
            getData();
        }
    }

    /**
     * 获取数据
     */
    public abstract void getData();

    public void onRefresh(final String condition) {

    }
}
