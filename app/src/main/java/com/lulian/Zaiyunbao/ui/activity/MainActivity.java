package com.lulian.Zaiyunbao.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.lulian.Zaiyunbao.ui.fragment.BuyFragment;
import com.lulian.Zaiyunbao.ui.fragment.LeaseFragment;
import com.lulian.Zaiyunbao.ui.fragment.ManageFragment;
import com.lulian.Zaiyunbao.ui.fragment.MeFragment;
import com.lulian.Zaiyunbao.ui.fragment.ServiceFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    public static final int INDEX_HOME = 0;
    public static final int INDEX_GUANLI = 1;
    public static final int INDEX_GOUMAI = 2;
    public static final int INDEX_FUWU = 3;
    public static final int INDEX_ME = 4;
    @BindView(R.id.main_content)
    FrameLayout mainContent;
    @BindView(R.id.tab_separate_imgv)
    ImageView tabSeparateImgv;
    @BindView(R.id.rbtn_home)
    RadioButton rbtnHome;
    @BindView(R.id.rbtn_guanli)
    RadioButton rbtnGuanli;
    @BindView(R.id.rbtn_fuwu)
    RadioButton rbtnFuwu;
    @BindView(R.id.rbtn_mine)
    RadioButton rbtnMine;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.rbtn_goumai)
    RadioButton rbtnGoumai;
    FragmentManager mFragmentManager;
    LeaseFragment mLeaseFragment;
    ManageFragment mManageFragment;
    ServiceFragment mServiceFragment;
    BuyFragment mBuyFragment;
    MeFragment mMeFragment;
    private Fragment currentFragment = new Fragment();
    /**
     * 主页监听按两次返回键退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    private long mExitTime;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        ImmersionBar.with(this).init();
        initView();
    }

    private void initView() {
        mFragmentManager = getSupportFragmentManager();
        mLeaseFragment = new LeaseFragment();
        mManageFragment = new ManageFragment();
        mBuyFragment = new BuyFragment();
        mServiceFragment = new ServiceFragment();
        mMeFragment = new MeFragment();

        radioGroup.setOnCheckedChangeListener(this);
        setSelectIndex(INDEX_HOME);
    }

    /**
     * 展示Fragment
     */
    public void showFragment(Fragment fragment) {
        if (currentFragment != fragment) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.hide(currentFragment);
            currentFragment = fragment;
            if (!fragment.isAdded()) {
                transaction.add(R.id.main_content, fragment).show(fragment).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.rbtn_home:
                setSelectIndex(INDEX_HOME);
                break;

            case R.id.rbtn_guanli:
                setSelectIndex(INDEX_GUANLI);
                break;

            case R.id.rbtn_goumai:
                setSelectIndex(INDEX_GOUMAI);
                break;

            case R.id.rbtn_fuwu:
                setSelectIndex(INDEX_FUWU);
                break;

            case R.id.rbtn_mine:
                setSelectIndex(INDEX_ME);
                break;
        }
    }

    public void setSelectIndex(int index) {
        switch (index) {
            case INDEX_HOME:
                showFragment(mLeaseFragment);
                rbtnHome.setTextColor(getResources().getColor(R.color.radio_color_select));
                rbtnGuanli.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnGoumai.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnFuwu.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnMine.setTextColor(getResources().getColor(R.color.radio_color));

                break;
            case INDEX_GUANLI:
                showFragment(mManageFragment);
                rbtnHome.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnGuanli.setTextColor(getResources().getColor(R.color.radio_color_select));
                rbtnGoumai.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnFuwu.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnMine.setTextColor(getResources().getColor(R.color.radio_color));
                break;

            case INDEX_GOUMAI:
                showFragment(mBuyFragment);
                rbtnHome.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnGuanli.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnGoumai.setTextColor(getResources().getColor(R.color.radio_color_select));
                rbtnFuwu.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnMine.setTextColor(getResources().getColor(R.color.radio_color));
                break;

            case INDEX_FUWU:
                showFragment(mServiceFragment);
                rbtnHome.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnGuanli.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnGoumai.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnFuwu.setTextColor(getResources().getColor(R.color.radio_color_select));
                rbtnMine.setTextColor(getResources().getColor(R.color.radio_color));
                break;
            case INDEX_ME:
                showFragment(mMeFragment);
                rbtnHome.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnGuanli.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnGoumai.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnFuwu.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnMine.setTextColor(getResources().getColor(R.color.radio_color_select));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finishAll();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
