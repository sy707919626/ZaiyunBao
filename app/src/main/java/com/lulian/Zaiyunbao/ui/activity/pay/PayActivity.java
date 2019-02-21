package com.lulian.Zaiyunbao.ui.activity.pay;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.alipay.AliPayManager;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.event.PayEvent;
import com.lulian.Zaiyunbao.common.exception.BaseException;
import com.lulian.Zaiyunbao.common.exception.RxErrorHandler;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.ui.activity.Update_Paypwd_Activity;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.lulian.Zaiyunbao.wxapi.WxPayManager;
import com.lulian.Zaiyunbao.wxapi.WxPrePayInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/11/27.
 */

public class PayActivity extends BaseActivity implements PayDialog.PayInterface {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.pay_money_text)
    TextView payMoneyText;
    @BindView(R.id.alipay_pay)
    RadioButton alipayPay;
    @BindView(R.id.weixin_pay)
    RadioButton weixinPay;
    @BindView(R.id.yue_pay)
    RadioButton yuePay;
    @BindView(R.id.payWay_choose)
    RadioGroup payWayChoose;
    @BindView(R.id.pay_next_btn)
    Button payNextBtn;
    private int payWay = 0;
    private PayDialog payDialog;

    private String userID;
    private float money;
    private String ordersId;
    private int OrderType;
    private String AccountNo; //账号（支付）

    @Override
    protected int setLayoutId() {
        return R.layout.activity_pay;
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

        textDetailContent.setText("支付");
        textDetailRight.setVisibility(View.GONE);
        //获取支付账号
        getData();


        userID = getIntent().getStringExtra("UserId");
        money = getIntent().getFloatExtra("money", 0f);
        ordersId = getIntent().getStringExtra("orderId");
        OrderType = getIntent().getIntExtra("OrderType", 0); //1租赁单2购买单

        payMoneyText.setText(money + "");

        payWayChoose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (alipayPay.getId() == checkedId) {
                    payWay = 1;

                } else if (weixinPay.getId() == checkedId) {
                    payWay = 2;

                } else if (yuePay.getId() == checkedId) {
                    payWay = 3;

                }
            }
        });

        payNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payWay == 0) {
                    RxToast.warning("请选择支付方式");
                } else if (payWay == 1) {
                    //支付宝
                    requestAliPayInfo();
                } else if (payWay == 2) {
                    //微信
                    requestWxPrePay();
                } else if (payWay == 3) {
                    //余额
                    if (GlobalParams.isPayPwd) {
                        payDialog = new PayDialog(PayActivity.this, "支付金额：" + money + "元", PayActivity.this);
                        payDialog.show();
                    } else {
                        showPayDialog("您还未设置支付密码，是否前往设置？");
                    }
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

    @Override
    public void Payfinish(String password) {
        //这里是当用户输入密码完成时 得到输入密码的回调方法
        payOrder(password);
    }

    @Override
    public void onSucc() {
        //回调 成功，关闭dialog 做自己的操作
        payDialog.cancel();
    }

    @Override
    public void onForget() {
        //当progress显示时，说明在请求网络，这时点击忘记密码不作处理
        if (payDialog.payPassView.progress.getVisibility() != View.VISIBLE) {
            Toast.makeText(PayActivity.this, "去找回密码", Toast.LENGTH_SHORT).show();

        }
    }

    //订单支付(押金或租金)余额
    private void payOrder(String password) {
        JSONObject obj = new JSONObject();
        obj.put("OrderId", ordersId);
        obj.put("PayPassword", password);
        obj.put("Money", money);
        obj.put("UserId", userID);
        obj.put("PayRemark", "");
        obj.put("OrderType", OrderType);

        String messages = obj.toString();
        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                messages);

        mApi.payOrder(GlobalParams.sToken, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        Constants.setIsAutoRefresh(true);
                        RxToast.success("支付成功");
                        payDialog.cancel();
                        EventBus.getDefault().post(new PayEvent());
                        finish();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        payDialog.cancel();
                    }
                });
    }

    /**
     * 请求服务端微信预支付
     */
    private void requestWxPrePay() {
        JSONObject root = new JSONObject();
        root.put("UserId", GlobalParams.sUserId);
        root.put("AccountType", 1); //0：对私账户，1：对公账户
        root.put("FeeMoney", money);
        root.put("AppId", Constants.WX_APPID);

        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                root.toString());

        mApi.wxPrePay(GlobalParams.sToken, GlobalParams.sUserId, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        WxPrePayInfo wxPrePayInfo = new Gson().fromJson(s, WxPrePayInfo.class);
                        WxPayManager wxPayMgr = new WxPayManager(PayActivity.this);
                        wxPayMgr.reqPay(wxPrePayInfo);
                        finish();
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
        root.put("total_amount", money);
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
            Intent intent = new Intent(this, WalletChargeFailActivity.class);
            intent.putExtra("payMsg", msg);
            intent.putExtra("money", money + "");
            startActivity(intent);
        }
    }

    //余额支付，设置支付密码
    private void showPayDialog(String content) {
        final AlertDialog builder = new AlertDialog.Builder(mContext)
                .create();
        builder.show();

        Window dialogWindow = builder.getWindow();
        if (dialogWindow == null) return;
        dialogWindow.setContentView(R.layout.pop_user);//设置弹出框加载的布局

        TextView titles = builder.findViewById(R.id.tv_name);
        TextView msg = builder.findViewById(R.id.tv_msg);
        Button cancle = builder.findViewById(R.id.btn_cancle);
        Button sure = builder.findViewById(R.id.btn_sure);
        cancle.setText("否");
        sure.setText("是");

        msg.setText(content);
        titles.setText("提示");

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        final WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.5
        dialogWindow.setAttributes(p);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PayActivity.this, Update_Paypwd_Activity.class));
                builder.dismiss();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface p1) {
                builder.dismiss();
            }
        });

    }
}
