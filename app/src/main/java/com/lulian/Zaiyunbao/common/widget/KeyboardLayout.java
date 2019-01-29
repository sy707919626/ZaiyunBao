package com.lulian.Zaiyunbao.common.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ScrollView;

/**
 * 登录键盘挡住布局问题
 *
 * @author 22064
 * @date 2018/3/31
 */
public class KeyboardLayout extends FrameLayout {

    private KeyboardLayoutListener mListener;
    private boolean mIsKeyboardActive = false; //输入法是否激活
    private int mKeyboardHeight = 0; // 输入法高度

    public KeyboardLayout(Context context) {
        this(context, null, 0);
    }

    public KeyboardLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyboardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 监听布局变化
        getViewTreeObserver().addOnGlobalLayoutListener(new KeyboardOnGlobalChangeListener());
    }

    public KeyboardLayoutListener getKeyboardListener() {
        return mListener;
    }

    public void setKeyboardListener(KeyboardLayoutListener listener) {
        mListener = listener;
    }

    public boolean isKeyboardActive() {
        return mIsKeyboardActive;
    }

    /**
     * 获取输入法高度
     */
    public int getKeyboardHeight() {
        return mKeyboardHeight;
    }

    /**
     * 键盘遮挡处理
     */
    public void keyBardInit(Activity activity, KeyboardLayout keyboardLayout, final ScrollView scrollView) {
        AndroidBug5497Workaround.assistActivity(activity);
        // 监听软键盘弹出 让ScrollView 滚动
        keyboardLayout.setKeyboardListener(new KeyboardLayout.KeyboardLayoutListener() {
            @Override
            public void onKeyboardStateChanged(boolean isActive, int keyboardHeight) {
                if (isActive) {
                    scrollView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, scrollView.getBottom());
                        }
                    }, 100);
                }
            }
        });
    }

    public interface KeyboardLayoutListener {
        /**
         * 键盘状态监听
         *
         * @param isActive       输入法是否激活
         * @param keyboardHeight 输入法面板高度
         */
        void onKeyboardStateChanged(boolean isActive, int keyboardHeight);
    }

    private class KeyboardOnGlobalChangeListener implements ViewTreeObserver.OnGlobalLayoutListener {

        int mScreenHeight = 0;

        private int getScreenHeight() {
            if (mScreenHeight > 0) {
                return mScreenHeight;
            }
            mScreenHeight = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getHeight();
            return mScreenHeight;
        }

        @Override
        public void onGlobalLayout() {
            Rect rect = new Rect();
            // 获取当前页面窗口的显示范围
            ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int screenHeight = getScreenHeight();
            // 输入法的高度
            int keyboardHeight = screenHeight - rect.bottom;
            boolean isActive = false;
            if (Math.abs(keyboardHeight) > screenHeight / 4) {
                // 超过屏幕五分之一则表示弹出了输入法
                isActive = true;
                mKeyboardHeight = keyboardHeight;
            }
            mIsKeyboardActive = isActive;
            if (mListener != null) {
                mListener.onKeyboardStateChanged(isActive, keyboardHeight);
            }
        }
    }
}
