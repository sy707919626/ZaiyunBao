package com.lulian.Zaiyunbao.ui.activity.bank;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.BankBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/1/21.
 */

public class BankCardActivity extends BaseActivity {
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.message_recyclerview)
    RecyclerView messageRecyclerview;
    @BindView(R.id.add_bank_layout)
    LinearLayout addBankLayout;

    private List<BankBean> mBankCardList = new ArrayList<>();
    private BankListAdapter mAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_bankcard;
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

        textDetailContent.setText("我的银行卡");
        textDetailRight.setVisibility(View.GONE);


        getData();
    }

    @OnClick({R.id.add_bank_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_bank_layout:
                //添加
                startActivity(new Intent(this, BankAddActivity.class));
                break;

            default:
                break;
        }
    }

    public void getData() {
        messageRecyclerview.setItemAnimator(new DefaultItemAnimator());
        messageRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        mApi.MyBankBindList(GlobalParams.sToken, GlobalParams.sUserId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .compose(this.<String>bindUntilEvent(ActivityEvent.STOP))
                .compose(this.<String>bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        mBankCardList.clear();
                        mBankCardList.addAll(JSONObject.parseArray(s, BankBean.class));

                        mAdapter = new BankListAdapter(BankCardActivity.this, mBankCardList);

                        mAdapter.notifyDataSetChanged();
                        messageRecyclerview.setAdapter(mAdapter);

                        mAdapter.setOnItemClickListener(new BankListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(BankBean mBankBean) {

                                Intent intents = new Intent(BankCardActivity.this, BankDetailActivity.class);
                                intents.putExtra("BankCardNo", mBankBean.getAccountNo()); //卡号
                                intents.putExtra("AccountName", mBankBean.getAccountName()); //持卡人
                                intents.putExtra("AccountBank", mBankBean.getAccountBank()); //卡名
                                intents.putExtra("BankId", mBankBean.getId()); //
                                startActivity(intents);
                            }
                        });
                    }

                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData(); //触发自动刷新
    }
}
