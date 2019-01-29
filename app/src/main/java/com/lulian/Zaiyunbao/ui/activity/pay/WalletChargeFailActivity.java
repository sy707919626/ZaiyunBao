package com.lulian.Zaiyunbao.ui.activity.pay;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.alipay.AliPayManager;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.exception.BaseException;
import com.lulian.Zaiyunbao.common.exception.RxErrorHandler;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.lulian.Zaiyunbao.wxapi.WxPayManager;
import com.lulian.Zaiyunbao.wxapi.WxPrePayInfo;

import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 钱包充值失败 结果显示界面
 */
public class WalletChargeFailActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.charge_fail_msg)
    TextView chargeFailMsg;
    @BindView(R.id.charge_fail_alipay_pay)
    RadioButton chargeFailAlipayPay;
    @BindView(R.id.charge_fail_weixin_pay)
    RadioButton chargeFailWeixinPay;
    @BindView(R.id.charge_fail_payWay_choose)
    RadioGroup chargeFailPayWayChoose;
    @BindView(R.id.charge_fail_btn)
    Button chargeFailBtn;

    private int payWay = 0;
    private String payMoney;
    private String AccountNo; //账号（支付）

    @Override
    protected int setLayoutId() {
        return R.layout.activity_wallet_charge_fail;
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

        textDetailContent.setText("钱包充值");
        textDetailRight.setVisibility(View.GONE);

        //获取支付账号
        getData();

        chargeFailMsg.setText(getIntent().getStringExtra("payMsg"));
        payMoney = getIntent().getStringExtra("money");


        chargeFailPayWayChoose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (chargeFailAlipayPay.getId() == checkedId) {
                    payWay = 1;

                } else if (chargeFailWeixinPay.getId() == checkedId) {
                    payWay = 2;

                }
            }
        });


        chargeFailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payWay == 0) {
                    RxToast.warning("请选择支付方式");
                } else if (payWay == 1) {
                    //支付宝
                    requestAliPayInfo();
                } else if (payWay == 2) {
                    requestWxPrePay();
                }
            }
        });
    }

    //获取支付账号
    private void getData() {
        mApi.myMoneyInfo(GlobalParams.sToken, GlobalParams.sUserId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        JSONObject jsonObject = JSONObject.parseObject(s);
                        AccountNo = jsonObject.getString("AccountNo");
                    }
                });
    }

    /**
     * 请求服务端,获取支付宝支付的相关信息
     */
    private void requestAliPayInfo() {
        Date date = new Date();
        String tradeNo = String.valueOf(date.getTime());

        JSONObject root = new JSONObject();
        root.put("trade_no", tradeNo);
        root.put("subject", "载运保预付押金");
        root.put("total_amount", Float.valueOf(payMoney));
        root.put("UserId", GlobalParams.sUserId);
        root.put("body", "pay_body");
        root.put("AccountNo", AccountNo);

        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                root.toString());

        mApi.aliPayGetInfo(GlobalParams.sToken, GlobalParams.sUserId, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        performAliPay(s);
                    }

                });
    }


    private void performAliPay(String orderInfo) {
        AliPayManager mgr = new AliPayManager(this);

        mgr.reqPay(orderInfo, new AliPayManager.Callback() {
            @Override
            public void onPayResult(Map<String, String> returnData) {
                String resultStatus = returnData.get("resultStatus");
                if (resultStatus.equals("9000")) {
                    String result = returnData.get("result");
                    requestAliPaySuccCofirm(result);
                }
            }
        });
    }

    /**
     * 请求服务端,进行支付宝支付的最终成功确认
     */
    private void requestAliPaySuccCofirm(String resultJson) {
        JSONObject resultJo = JSON.parseObject(resultJson);
        JSONObject tradePayRespJo = resultJo.getJSONObject("alipay_trade_app_pay_response");

        mApi.alipaySuccConfirm(GlobalParams.sToken, tradePayRespJo.getString("out_trade_no"))
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        jumpToChargeResult(true, "");
                    }

                    @Override
                    public void onError(Throwable t) {
                        RxErrorHandler mRxErrorHandler = new RxErrorHandler();
                        BaseException exception = mRxErrorHandler.handlerError(t);
                        jumpToChargeResult(false, exception.getDisplayMessage());
                    }
                });
    }

    /**
     * 跳转至充值结果界面
     */
    private void jumpToChargeResult(boolean isSuccess, String msg) {
        if (isSuccess) {
            startActivity(new Intent(this, WalletChargeResultActivity.class));
        } else {
            chargeFailMsg.setText(msg);
        }
    }

    /**
     * 请求服务端微信预支付
     */
    private void requestWxPrePay() {
        JSONObject root = new JSONObject();
        root.put("UserId", GlobalParams.sUserId);
        root.put("AccountType", 1); //0：对私账户，1：对公账户
        root.put("FeeMoney", Float.valueOf(payMoney));
        root.put("AppId", Constants.WX_APPID);

        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                root.toString());

        mApi.wxPrePay(GlobalParams.sToken, GlobalParams.sUserId, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        WxPrePayInfo wxPrePayInfo = new Gson().fromJson(s, WxPrePayInfo.class);
                        WxPayManager wxPayMgr = new WxPayManager(WalletChargeFailActivity.this);
                        wxPayMgr.reqPay(wxPrePayInfo);
                        finish();
                    }

                });
    }

}
