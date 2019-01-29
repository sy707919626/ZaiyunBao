package com.lulian.Zaiyunbao.common.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lulian.Zaiyunbao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部tab切换控件
 *
 * @author 22064
 */
public class TabLayout extends LinearLayout implements View.OnClickListener {
    private FragmentManager mFragmentManager;
    private int mContainerResId;
    private ArrayList<Tab> tabs;
    private OnTabClickListener listener;
    private View selectView;
    private int tabCount;

    //是否能切换
    private boolean isCanTab = true;

    public TabLayout(Context context) {
        super(context);
        setUpView();
    }


    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpView();
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpView();
    }

    public void setCanTab(boolean canTab) {
        isCanTab = canTab;
    }

    private void setUpView() {
        setOrientation(HORIZONTAL);

    }

    public void setUpData(int containerResId, FragmentManager fragmentManager, ArrayList<Tab> tabs,
                          OnTabClickListener listener) {
        this.tabs = tabs;
        this.listener = listener;
        this.mContainerResId = containerResId;
        this.mFragmentManager = fragmentManager;

        if (tabs != null && tabs.size() > 0) {
            tabCount = tabs.size();
            TabView mTabView;
            LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            params.weight = 1;
            for (int i = 0; i < tabs.size(); i++) {
                mTabView = new TabView(getContext());
                mTabView.setTag(tabs.get(i));
                mTabView.setUpData(tabs.get(i));
                mTabView.setOnClickListener(this);
                addView(mTabView, params);
            }
        } else {
            throw new IllegalArgumentException("tabs can't be empty");
        }
    }


    public void setCurrentTab(int i) {
        if (i < tabCount && i >= 0) {
            View view = getChildAt(i);
            onClick(view);
        }
    }

    public void onDataChanged(int i, int badgeCount) {
        if (i < tabCount && i >= 0) {
            TabView view = (TabView) getChildAt(i);
            view.onDataChanged(badgeCount);
        }
    }

    public void onDataChanged(int i, Drawable drawable) {
        if (i < tabCount && i >= 0) {
            TabView view = (TabView) getChildAt(i);
            view.onDrawableChanged(drawable);
        }
    }

    @Override
    public void onClick(View tmpV) {
        listener.onTabBefore((Tab) tmpV.getTag());

        if (selectView != tmpV && isCanTab) {
            /** 界面切换控制 */
            Tab selectTab = null;

            tmpV.setSelected(true);
            if (selectView != null) {
                selectTab = (Tab) selectView.getTag();
                selectView.setSelected(false);
            }
            selectView = tmpV;

            //Fragment切换
            setFragments((Tab) tmpV.getTag(), selectTab);
        }
    }


    public void setFragments(Tab tab, Tab currentTab) {
        try {
            Fragment tmpFragment = mFragmentManager.findFragmentByTag(tab.targetFragmentClz.getSimpleName());
            Fragment currentFragment = null;
            if (currentTab != null) {
                currentFragment = mFragmentManager.findFragmentByTag(currentTab.targetFragmentClz.getSimpleName());
            }
            FragmentTransaction transaction = mFragmentManager.beginTransaction();

            if (tmpFragment == null) {

                tmpFragment = tab.targetFragmentClz.newInstance();
                transaction.add(mContainerResId, tmpFragment, tab.targetFragmentClz.getSimpleName());
                //由于应用重启 或者强杀的情况 Activity会保存Fragment状态，导致同时会显示强杀之前的Fragment和当前需要显示的Fragment
                //造成Fragment重叠 所以这里把非需要显示的Fragment 全部再hide一次
                hideFragment(tmpFragment, currentFragment, transaction);
            } else {
                transaction.show(tmpFragment);
                hideFragment(tmpFragment, currentFragment, transaction);
            }
            transaction.commitAllowingStateLoss();

            listener.onTabClick(tab, tmpFragment);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏Fragment
     *
     * @param tmpFragment     需要显示的Fragment
     * @param currentFragment 当前显示的Fragment
     * @param transaction     事物
     */
    private void hideFragment(Fragment tmpFragment, Fragment currentFragment, FragmentTransaction transaction) {
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        } else {
            List<Fragment> fragments = mFragmentManager.getFragments();
            for (int i = 0; i < fragments.size(); i++) {
                Fragment fragment = fragments.get(i);
                if (fragment != tmpFragment) {
                    transaction.hide(fragment);
                }
            }
        }
    }

    public interface OnTabClickListener {
        void onTabClick(Tab tab, Fragment currentFragment);

        void onTabBefore(Tab tab);
    }

    public static class Tab {
        public int imgResId;
        public int labelResId;
        public int badgeCount;
        public Drawable mDrawable;

        public Class<? extends Fragment> targetFragmentClz;

        public Tab(int imgResId, int labelResId) {
            this.imgResId = imgResId;
            this.labelResId = labelResId;
        }

        public Tab(int imgResId, int labelResId, int badgeCount) {
            this.imgResId = imgResId;
            this.labelResId = labelResId;
            this.badgeCount = badgeCount;
        }

        public Tab(int imgResId, int labelResId, Class<? extends Fragment> targetFragmentClz) {
            this.imgResId = imgResId;
            this.labelResId = labelResId;
            this.targetFragmentClz = targetFragmentClz;
        }

        public Drawable getDrawable() {
            return mDrawable;
        }

        public void setDrawable(Drawable drawable) {
            mDrawable = drawable;
        }
    }

    public class TabView extends LinearLayout {
        private ImageView mTabImg;
        private TextView mTabLabel;

        public TabView(Context context) {
            super(context);
            setUpView();
        }

        public TabView(Context context, AttributeSet attrs) {
            super(context, attrs);
            setUpView();
        }

        public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            setUpView();
        }


        private void setUpView() {
            LayoutInflater.from(getContext()).inflate(R.layout.widget_tab_view, this, true);
            setOrientation(VERTICAL);
            setGravity(Gravity.CENTER);
            mTabImg = findViewById(R.id.mTabImg);
            mTabLabel = findViewById(R.id.mTabLabel);
        }

        public void setUpData(Tab tab) {
            mTabImg.setBackgroundResource(tab.imgResId);
            mTabLabel.setText(tab.labelResId);
        }

        public void onDrawableChanged(Drawable drawable) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mTabImg.setBackground(drawable);
            }
        }


        public void onDataChanged(int badgeCount) {
            //  TODO notify new message, change the badgeView
        }
    }
}
