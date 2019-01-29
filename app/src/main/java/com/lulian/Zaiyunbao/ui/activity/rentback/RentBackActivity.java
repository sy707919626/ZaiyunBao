package com.lulian.Zaiyunbao.ui.activity.rentback;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/11/6.
 */

public class RentBackActivity extends BaseActivity {

    FragmentManager mFragmentManager;
    RentBackAllFragment mRentBackAllFragment;

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.rent_back_fragment)
    FrameLayout rentBackFragment;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_rent_back;
    }

    @Override
    protected void init() {

        ImmersionBar.with(this)
                .titleBar(R.id.detail_bar_title)
                .titleBarMarginTop(R.id.detail_bar_title)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        textDetailContent.setText("退租订单");
        textDetailRight.setVisibility(View.GONE);

        mRentBackAllFragment = new RentBackAllFragment();
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.rent_back_fragment, mRentBackAllFragment);
        transaction.commit();

    }

}
