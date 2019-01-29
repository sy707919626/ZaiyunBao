package com.lulian.Zaiyunbao.common.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.lulian.Zaiyunbao.R;

/**
 * 这个帮助类用来提供一个加载失败提示View显示到界面
 */
public class LoadFailTipHelper {

    private Context mContext;
    private View wrapperView;
    private View contentView;//实际界面内容view
    private View loadFailTipView;//加载失败的提示View;

    private Callback mCallback;
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mCallback != null) {
                mCallback.onReloadClicked();
            }
        }
    };

    public LoadFailTipHelper(@NonNull Context mContext, int contentViewLayoutResId) {
        this.mContext = mContext;
        init(contentViewLayoutResId);
    }

    private void init(int contentViewLayoutResId) {
        loadFailTipView = LayoutInflater.from(mContext).inflate(R.layout.load_fail_tip, null);
        loadFailTipView.setOnClickListener(mClickListener);
        loadFailTipView.setVisibility(View.GONE);

        contentView = LayoutInflater.from(mContext).inflate(contentViewLayoutResId, null);
        contentView.setVisibility(View.GONE);

        FrameLayout fl = new FrameLayout(mContext);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        fl.setLayoutParams(lp);

        //添加加载失败提示view
        fl.addView(loadFailTipView);
        //添加内容view
        fl.addView(contentView);

        wrapperView = fl;

    }

    /**
     * 设置监听器
     *
     * @param callback
     * @return
     */
    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    /**
     * 显示内容view
     */
    public void showContentView() {
        contentView.setVisibility(View.VISIBLE);
        loadFailTipView.setVisibility(View.GONE);
    }

    /**
     * 显示加载提示view
     */
    public void showFailTipView() {
        loadFailTipView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
    }

    /**
     * 获得包装后的View
     *
     * @return
     */
    public View getWrapperView() {
        return wrapperView;
    }

    public interface Callback {
        void onReloadClicked();
    }


}
