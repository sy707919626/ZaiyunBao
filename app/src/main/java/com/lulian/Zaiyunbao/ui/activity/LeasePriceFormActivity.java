package com.lulian.Zaiyunbao.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.LeasePriceFromBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RecyclerItemDecoration;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.adapter.LeasePriceFormAdapter;
import com.lulian.Zaiyunbao.ui.adapter.LeasePriceFormSumAdapter;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alibaba.fastjson.JSON.parseArray;

/**
 * Created by Administrator on 2018/11/2.
 */

public class LeasePriceFormActivity extends BaseActivity {


    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.scrollable_panel_data)
    RecyclerView scrollablePanelData;
    @BindView(R.id.scrollable_panel_sum)
    RecyclerView scrollablePanelSum;


    private LeasePriceFormAdapter mAdapter;
    private LeasePriceFormSumAdapter mAdapterSum;
    private List<LeasePriceFromBean> mLeasePriceFromBean = new ArrayList<>();
    private List<LeasePriceFromBean> mLeasePriceFromBeanSum = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.activity_lease_pricefrom;
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
        getData();

        textDetailContent.setText("租赁价格表");
        textDetailRight.setVisibility(View.GONE);
        scrollablePanelData.setItemAnimator(new DefaultItemAnimator());
        scrollablePanelData.setLayoutManager(new LinearLayoutManager(LeasePriceFormActivity.this));
        scrollablePanelData.addItemDecoration(new RecyclerItemDecoration(LeasePriceFormActivity.this, DividerItemDecoration.VERTICAL));


        scrollablePanelSum.setItemAnimator(new DefaultItemAnimator());
        scrollablePanelSum.setLayoutManager(new LinearLayoutManager(LeasePriceFormActivity.this));
        scrollablePanelSum.addItemDecoration(new RecyclerItemDecoration(LeasePriceFormActivity.this, DividerItemDecoration.VERTICAL));
    }

    private void getData() {
        mApi.rentPriceListAll(GlobalParams.sToken, "")
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        List<LeasePriceFromBean> list = parseArray(s, LeasePriceFromBean.class);

                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getRentWay() == 1) {
                                mLeasePriceFromBean.add(list.get(i));
                            } else {
                                mLeasePriceFromBeanSum.add(list.get(i));
                            }
                        }

//                        mLeasePriceFromBean.addAll(list);
                        mAdapter = new LeasePriceFormAdapter(LeasePriceFormActivity.this, mLeasePriceFromBean);
                        scrollablePanelData.setAdapter(mAdapter);

                        mAdapterSum = new LeasePriceFormSumAdapter(LeasePriceFormActivity.this, mLeasePriceFromBeanSum);
                        scrollablePanelSum.setAdapter(mAdapterSum);


                    }
                });
    }

}
