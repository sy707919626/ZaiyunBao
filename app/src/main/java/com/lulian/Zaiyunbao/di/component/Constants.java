package com.lulian.Zaiyunbao.di.component;

/**
 * @description：
 * @author：bux on 2018/3/22 16:12
 * @email: 471025316@qq.com
 */

public class Constants {
    public static final String BASE_URL = "http://192.168.1.242:8086";

    /**
     * 微信sdk的appId
     */
    public static final String WX_APPID = "wxa18f5e18919cccc7";
    /**
     * 微信支付商户Id
     */
    public static final String WX_PAY_PARTNERID = "";

    public static boolean isAutoRefresh = false;

    public static void setIsAutoRefresh(boolean isAutoRefresh) {
        Constants.isAutoRefresh = isAutoRefresh;
    }
}
