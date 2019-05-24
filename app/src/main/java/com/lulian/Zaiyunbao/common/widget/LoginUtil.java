package com.lulian.Zaiyunbao.common.widget;

import android.content.Intent;

import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.ui.activity.LoginActivity;


public class LoginUtil {
    public static void loginOut() {
//        GlobalParams.clear();

        Intent intent = new Intent(MyApplication.get(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyApplication.get().startActivity(intent);
    }

}
