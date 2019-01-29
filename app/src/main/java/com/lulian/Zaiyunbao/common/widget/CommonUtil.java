package com.lulian.Zaiyunbao.common.widget;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.lulian.Zaiyunbao.MyApplication;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * @description：
 */
public class CommonUtil {

    public static boolean isEmpty(TextView textView) {
        return TextUtils.isEmpty(textView.getText().toString().trim());
    }


    public static String replaceNull(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (text.equals("null")) {
                return "";
            }
        } else {
            return "";
        }
        return text;
    }

    /**
     * map 参数拼接
     *
     * @param map map
     * @return 参数串
     */
    public static String paramsToGet(Map<String, String> map) {
        String split = "";
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            sb.append(split).append(key).append("=").append(map.get(key));
            split = "&";
        }
        return sb.toString().trim();
    }

    public static String paramsToPath(String... paths) {
        StringBuilder sb = new StringBuilder();
        String split = "";
        for (String key : paths) {
            sb.append(split).append(key);
            split = "/";
        }
        return sb.toString().trim();
    }

    /**
     * 格式化double保留两位小数点
     *
     * @param value
     * @return
     */
    public static String decimalFormat2(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(value);
    }


    /**
     * 为textview 设置文字
     *
     * @param textView
     * @param resId
     * @param formatArgs
     */
    public static void setTextByResId(TextView textView, @StringRes int resId, Object... formatArgs) {

        textView.setText(MyApplication.get().getResources().getString(resId, formatArgs));
    }


    /**
     * 为textview 设置文字
     *
     * @param textView
     * @param resId
     * @param formatArgs
     */
    public static void setTextHttpByResId(TextView textView, @StringRes int resId, Object... formatArgs) {

        textView.setText(Html.fromHtml(MyApplication.get().getResources().getString(resId, formatArgs)));
    }

    /**
     * 为textview 设置文字
     *
     * @param textView
     * @param resId
     */
    public static void setTextByResId(TextView textView, @StringRes int resId) {

        textView.setText(MyApplication.get().getResources().getString(resId));
    }

    public static void setTextColor(TextView textView, @ColorRes int resId) {
        textView.setTextColor(MyApplication.get().getResources().getColor(resId));
    }
}
