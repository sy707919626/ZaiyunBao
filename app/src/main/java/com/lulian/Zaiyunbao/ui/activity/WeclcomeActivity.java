package com.lulian.Zaiyunbao.ui.activity;

import android.content.Intent;
import android.widget.ImageView;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.DicItemBean;
import com.lulian.Zaiyunbao.Bean.EquipmentTypeBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

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

//        BarUtils.setStatusBarVisibility(this, false);

//        //	注意：这里就是创建了Animation对象，关联了刚才写的anim文件里的东西
//        Animation anim= AnimationUtils.loadAnimation(this, R.anim.activity_rotate);
//        //	将ImageView对象执行动画就可以了
//        weclcomeImg.startAnimation(anim);
        GlobalParams.setToken("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1Njc4NDY2ODYsInVzZXIiOnsiVXNlcklEIjoiMSIsIlVzZXJOYW1lIjoi6LaF57qn566h55CG5ZGYIn19.U4mnFIJpioXChcKIBD29MLx1UL-NfPiK6hM1XVrw2U8");
//        getEquipmentType(); //获取设备类型
        getDicItem();//获取字典
        skipLogin();
    }


    private void skipLogin() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(WeclcomeActivity.this, LoginActivity.class));
                WeclcomeActivity.this.finish();
            }
        }, 1000);
    }

    private void getEquipmentType() {
        mApi.equipmentTypeList(GlobalParams.sToken)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        GlobalParams.setEquipmentTypeList(parseArray(s, EquipmentTypeBean.class));
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

}
