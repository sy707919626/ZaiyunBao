package com.lulian.Zaiyunbao.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.StaticsBean;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/2/13.
 */

public class App_Statistic_Activity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.guarantee_text)
    TextView guaranteeText;
    @BindView(R.id.sublet_text)
    TextView subletText;
    @BindView(R.id.shebei_sum_text)
    TextView shebeiSumText;
    @BindView(R.id.shiyong_sum_text)
    TextView shiyongSumText;
    @BindView(R.id.xianzhi_sum_text)
    TextView xianzhiSumText;
    @BindView(R.id.baofei_sum_text)
    TextView baofeiSumText;
    @BindView(R.id.maifei_sum_text)
    TextView maifeiSumText;
    @BindView(R.id.chaoshi_sum_text)
    TextView chaoshiSumText;
    @BindView(R.id.zhuanzu_sum_text)
    TextView zhuanzuSumText;
    private StaticsBean mStatics;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_app_statistic;
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

        textDetailContent.setText("数据统计");
        textDetailRight.setVisibility(View.GONE);
        getData();
    }

    public void getData() {
        mApi.StatisticsEquipmentInfo(GlobalParams.sToken, GlobalParams.sUserId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        mStatics = MyApplication.get().getAppComponent().getGson().fromJson(s, StaticsBean.class);
                        initView();
                    }
                });
    }

    private void initView() {
        guaranteeText.setText(String.valueOf(mStatics.getDeposits()));
        subletText.setText(String.valueOf(mStatics.getZDeposits()));
        shebeiSumText.setText(String.valueOf(mStatics.getQuantity()));
        chaoshiSumText.setText(String.valueOf(mStatics.getChaishi()));
        xianzhiSumText.setText(String.valueOf(mStatics.getXianzhi()));
        baofeiSumText.setText(String.valueOf(mStatics.getBaofei()));
        maifeiSumText.setText(String.valueOf(mStatics.getMianfei()));
        zhuanzuSumText.setText(String.valueOf(mStatics.getZQuantity()));

        int shiyongsum = mStatics.getQuantity() - mStatics.getBaofei()- mStatics.getXianzhi();
        shiyongSumText.setText(String.valueOf(shiyongsum));
    }
}
