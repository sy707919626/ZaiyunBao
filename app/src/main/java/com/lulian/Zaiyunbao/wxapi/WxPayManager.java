package com.lulian.Zaiyunbao.wxapi;


import android.content.Context;

import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.common.widget.TimeUtil;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * 微信支付工具类
 *
 * @author hxb
 */
public class WxPayManager {

    /**
     * 支付结果errorCode
     */
    public static final int PAY_RESULT_SUCC = 1; //支付成功
    public static final int PAY_RESULT_ERROR = -1; //错误
    public static final int PAY_RESULT_CANCEL = 2; //用户取消

    private Context context;

    public WxPayManager(Context context) {
        this.context = context;

    }

    /**
     * 发起微信支付;
     */
    public void reqPay(WxPrePayInfo wppi) {
        if (wppi.getPrepayid() == null || wppi.getPrepayid().isEmpty()) {
            RxToast.warning("预支付信息错误");
            return;
        }

        IWXAPI api = WXAPIFactory.createWXAPI(context, Constants.WX_APPID);
        api.registerApp(Constants.WX_APPID);

        PayReq payReq = new PayReq();
        payReq.appId = Constants.WX_APPID;//appId
        payReq.partnerId = wppi.getMchid();//商户id
        payReq.prepayId = wppi.getPrepayid();//
        payReq.packageValue = wppi.getPackageName();
        payReq.nonceStr = wppi.getNoncestr();

        //将服务端获取的时间字符串转成 秒级的 时间戳(微信支付的PayReq对象中要求传入秒级的时间戳)
        long milli = TimeUtil.timeStrToSecond(wppi.getTimestamp());
        long secondTimeStamp = milli / 1000;
        payReq.timeStamp = secondTimeStamp + "";

        payReq.sign = wppi.getSign();

        api.sendReq(payReq);

    }


}
