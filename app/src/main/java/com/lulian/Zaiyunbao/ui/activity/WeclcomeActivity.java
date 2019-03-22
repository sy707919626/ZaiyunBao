package com.lulian.Zaiyunbao.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.DicItemBean;
import com.lulian.Zaiyunbao.Bean.EquipmentTypeBean;
import com.lulian.Zaiyunbao.Bean.TaskInfo;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.DownloadRunnable;
import com.lulian.Zaiyunbao.ui.LevelDialog;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.taobao.sophix.SophixManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.alibaba.fastjson.JSON.parseArray;

/**
 * Created by Administrator on 2018/9/18.
 */

public class WeclcomeActivity extends BaseActivity {
    @BindView(R.id.weclcome_img)
    ImageView weclcomeImg;
    private boolean isSuccessToken = false;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_weclcome;
    }

    @Override
    protected void init() {
        ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_HIDE_BAR)
                .init();

//        //	注意：这里就是创建了Animation对象，关联了刚才写的anim文件里的东西
//        Animation anim= AnimationUtils.loadAnimation(this, R.anim.activity_rotate);
//        //	将ImageView对象执行动画就可以了
//        weclcomeImg.startAnimation(anim);
        GlobalParams.setToken("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1Njc4NDY2ODYsInVzZXIiOnsiVXNlcklEIjoiMSIsIlVzZXJOYW1lIjoi6LaF57qn566h55CG5ZGYIn19.U4mnFIJpioXChcKIBD29MLx1UL-NfPiK6hM1XVrw2U8");

        //        getEquipmentType(); //获取设备类型
        getDicItem();//获取字典
        getEquipmentType();
        skipLogin();
    }


    private void skipLogin() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(WeclcomeActivity.this, LoginActivity.class));
                WeclcomeActivity.this.finish();
            }
        }, 3000);
    }

    private void getEquipmentType() {
        final List<String> SBTypeList = new ArrayList<>(); //设备类型

        mApi.equipmentTypeList(GlobalParams.sToken)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        GlobalParams.setsEquipmentTypeBean(parseArray(s, EquipmentTypeBean.class));

                        for (EquipmentTypeBean equipmentTypeBean: GlobalParams.sEquipmentTypeBean){
                            SBTypeList.add(equipmentTypeBean.getTypeName());
                        }

                        GlobalParams.setSBTypeList(SBTypeList);
                    }
                });
    }

    private void getDicItem() {
        final List<String> userTypeList = new ArrayList<>(); //用户类型
        final List<String> FHTypeList = new ArrayList<>(); //发货类型
        final List<String> JSTypeList = new ArrayList<>(); //结算类型
        final List<String> ZLTypeList = new ArrayList<>();  //租赁模式
        final List<String> ZFTypeList = new ArrayList<>();  //支付模式（购买）


        mApi.GetDicItem(GlobalParams.sToken)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        GlobalParams.setsDicItemBean(parseArray(s, DicItemBean.class));

                        //DT001/用户类型、DT002/发货方式、DT003/结算方式、DT004/租赁方式
                        for (DicItemBean dicItemBean : GlobalParams.sDicItemBean) {
                            if (dicItemBean.getDicTypeCode().equals("DT001")) {
                                userTypeList.add(dicItemBean.getItemName());

                            } else if (dicItemBean.getDicTypeCode().equals("DT002")) {
                                FHTypeList.add(dicItemBean.getItemName());
                            } else if (dicItemBean.getDicTypeCode().equals("DT003")) {
                                JSTypeList.add(dicItemBean.getItemName());
                            } else if (dicItemBean.getDicTypeCode().equals("DT004")) {
                                ZLTypeList.add(dicItemBean.getItemName());
                            } else if (dicItemBean.getDicTypeCode().equals("DT005")) {
                                ZFTypeList.add(dicItemBean.getItemName());
                            }
                        }

                        GlobalParams.setUserTypeList(userTypeList);
                        GlobalParams.setFHTypeList(FHTypeList);
                        GlobalParams.setJSTypeList(JSTypeList);
                        GlobalParams.setZLTypeList(ZLTypeList);
                        GlobalParams.setZFTypeList(ZFTypeList);
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
