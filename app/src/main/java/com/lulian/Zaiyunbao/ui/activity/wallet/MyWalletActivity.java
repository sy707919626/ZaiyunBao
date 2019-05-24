package com.lulian.Zaiyunbao.ui.activity.wallet;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.activity.Update_Paypwd_Activity;
import com.lulian.Zaiyunbao.ui.activity.Wallet_Detail_ListActivity;
import com.lulian.Zaiyunbao.ui.activity.bank.BankCardActivity;
import com.lulian.Zaiyunbao.ui.activity.bank.CashBankMoneyActivity;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/10/30.
 */

public class MyWalletActivity extends BaseActivity {


    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.my_balance)
    TextView myBalance;
    @BindView(R.id.my_deposit)
    TextView myDeposit;
    @BindView(R.id.mywallet_bank_relative)
    RelativeLayout mywalletBankRelative;
    @BindView(R.id.mydata_recharge_relative)
    RelativeLayout mydataRechargeRelative;
    @BindView(R.id.mydata_putforward_relative)
    RelativeLayout mydataPutforwardRelative;
    @BindView(R.id.mydata_updatePayPwd_relative)
    RelativeLayout mydataUpdatePayPwdRelative;
    @BindView(R.id.mydata_updatePayPwd_text)
    TextView mydataUpdatePayPwdText;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mywallet;
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

        textDetailContent.setText("我的钱包");
        textDetailRight.setText("明细");

        if (GlobalParams.isPayPwd) {
            mydataUpdatePayPwdText.setText("修改支付密码");
        } else {
            mydataUpdatePayPwdText.setText("设置支付密码");
        }

        getData();
    }

    private void getData() {
        mApi.myMoneyInfo(GlobalParams.sToken, GlobalParams.sUserId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .compose(this.<String>bindUntilEvent(ActivityEvent.STOP))
                .compose(this.<String>bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        JSONObject jsonObject = JSONObject.parseObject(s);

                        if (TextUtils.isEmpty(jsonObject.getString("Balance"))){
                            myBalance.setText("0元");
                        } else {
                            myBalance.setText(jsonObject.getString("Balance") + "元");
                        }

                        if (TextUtils.isEmpty(jsonObject.getString("Deposit"))){
                            myDeposit.setText("0元");
                        } else {
                            myDeposit.setText(jsonObject.getString("Deposit") + "元");
                        }
                    }
                });
    }

    @OnClick({R.id.mywallet_bank_relative, R.id.mydata_recharge_relative, R.id.mydata_putforward_relative,
            R.id.mydata_updatePayPwd_relative, R.id.text_detail_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mywallet_bank_relative:
                //银行卡
                startActivity(new Intent(this, BankCardActivity.class));
                break;
            case R.id.mydata_recharge_relative:
                //充值
                startActivity(new Intent(this, WalletRechargeActivity.class));
                break;
            case R.id.mydata_putforward_relative:
                //提现
                startActivity(new Intent(this, CashBankMoneyActivity.class));
                break;

            case R.id.mydata_updatePayPwd_relative:
                startActivity(new Intent(this, Update_Paypwd_Activity.class));
                break;

            case R.id.text_detail_right:
                startActivity(new Intent(this, Wallet_Detail_ListActivity.class));
                //明细
                break;
            default:
                break;
        }
    }
}
