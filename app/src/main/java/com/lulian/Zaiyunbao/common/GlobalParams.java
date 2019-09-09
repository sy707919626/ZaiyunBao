package com.lulian.Zaiyunbao.common;

import android.text.TextUtils;

import com.lulian.Zaiyunbao.Bean.DicItemBean;
import com.lulian.Zaiyunbao.Bean.EquipmentTypeBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GlobalParams {
    public static String sToken;
    public static String sUserPhone;
    public static String sUserId;
    public static String sUserName;
    public static String sUserType;
    public static int sUserClass;
    public static boolean isPayPwd; //验证是否有支付密码


    public static String district; //区县
    public static double latitude; //纬度
    public static double longitude; //经度

    public static List<EquipmentTypeBean> sEquipmentTypeBean;
    public static List<DicItemBean> sDicItemBean;

    public static List<String> SBTypeList; //设备类型
    public static List<String> userTypeList; //用户类型
    public static List<String> FHTypeList; //发货类型
    public static List<String> JSTypeList; //结算类型
    public static List<String> ZLTypeList; //租赁方式
    public static List<String> ZFTypeList; //支付模式（购买）


    public static String[] shebeiTypeList = {"保温箱"};
    public static String[] shebeiSpecList = {"尺寸1"};

    public static void setSBTypeList(List<String> SBTypeList) {
        GlobalParams.SBTypeList = SBTypeList;
    }

    public static void setsEquipmentTypeBean(List<EquipmentTypeBean> sEquipmentTypeBean) {
        GlobalParams.sEquipmentTypeBean = sEquipmentTypeBean;
    }

    public static void setIsPayPwd(boolean isPayPwd) {
        GlobalParams.isPayPwd = isPayPwd;
    }

    public static void setLatitude(double latitude) {
        GlobalParams.latitude = latitude;
    }

    public static void setLongitude(double longitude) {
        GlobalParams.longitude = longitude;
    }

    public static void setDistrict(String district) {
        GlobalParams.district = district;
    }



    public static void setUserTypeList(List<String> userTypeList) {
        GlobalParams.userTypeList = userTypeList;
    }

    public static void setZFTypeList(List<String> ZFTypeList) {
        GlobalParams.ZFTypeList = ZFTypeList;
    }

    public static void setFHTypeList(List<String> FHTypeList) {
        GlobalParams.FHTypeList = FHTypeList;
    }

    public static void setJSTypeList(List<String> JSTypeList) {
        GlobalParams.JSTypeList = JSTypeList;
    }

    public static void setZLTypeList(List<String> ZLTypeList) {
        GlobalParams.ZLTypeList = ZLTypeList;
    }

    public static void setsDicItemBean(List<DicItemBean> sDicItemBean) {
        GlobalParams.sDicItemBean = sDicItemBean;
    }

    public static void setToken(String token) {
        sToken = token;
    }

    public static void setuserPhone(String userPhone) {
        sUserPhone = userPhone;
    }

    public static void setuserName(String userName) {
        sUserName = userName;
    }

    public static void setuserId(String userId) {
        sUserId = userId;
    }

    public static void setUserClass(int userClass) {
        sUserClass = userClass;
    }

    public static void setuserType(String userType) {
        sUserType = userType;
    }

    public static String getUserHeader() {
        JSONObject user = new JSONObject();
        try {
            user.put("userid", sUserId);
            user.put("name", sUserName);
            user.put("usertype", sUserType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user.toString();
    }

    /**
     * 是否登录
     */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(sToken);
    }

}
