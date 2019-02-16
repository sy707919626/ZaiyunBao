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
import android.view.View;
import android.widget.ImageView;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.DicItemBean;
import com.lulian.Zaiyunbao.Bean.EquipmentTypeBean;
import com.lulian.Zaiyunbao.Bean.TaskInfo;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.DownloadRunnable;
import com.lulian.Zaiyunbao.ui.LevelDialog;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.opengles.GL;

import butterknife.BindView;

import static com.alibaba.fastjson.JSON.parseArray;

/**
 * Created by Administrator on 2018/9/18.
 */

public class WeclcomeActivity extends BaseActivity {
    @BindView(R.id.weclcome_img)
    ImageView weclcomeImg;
    private boolean isSuccessToken = false;

    LevelDialog dialog;
    private TaskInfo info;//任务信息
    private DownloadRunnable runnable;//下载任务

    public final static int REQUEST_READ_PHONE_STATE = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};

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
        getEquipmentType();

        skipLogin();
        verifyStoragePermissions(WeclcomeActivity.this);


    }


    private void skipLogin() {

//        dialog = new LevelDialog(WeclcomeActivity.this, new LevelDialog.OnLevelListener() {
//            @Override
//            public void onClick(Dialog dialog, boolean confirm) {
//                downapk("http://down-ww2.7down.com/soft/c/5c/com.qq.reader_a16be59a.apk");
//            }
//        });
//        dialog.show();
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        if (!TextUtils.isEmpty(data.getDesc())) {
//            dialog.setTipText(data.getDesc());
//        }
//        dialog.downBtn.setCurrentText("立即升级");

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(WeclcomeActivity.this, LoginActivity.class));
                WeclcomeActivity.this.finish();
            }
        }, 1000);
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
        if(handler != null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        super.onDestroy();
    }


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            //使用Handler制造一个200毫秒为周期的循环
            handler.sendEmptyMessageDelayed(1, 200);
            //计算下载进度
            int l = (int) ((float) info.getCompleteLen() / (float) info.getContentLen() * 100);
            //设置进度条进度
//            bar.setProgress(l);
            dialog.downBtn.setState( dialog.downBtn.STATE_DOWNLOADING);
            dialog.downBtn.setProgressText("",l);
            if (l>=100) {//当进度>=100时，取消Handler循环
                handler.removeCallbacksAndMessages(null);
                dialog.downBtn.setCurrentText("安装中...");
                installApk();

            }
            return true;
        }
    });


    private void installApk() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+"zyb.apk";
        File file = new File(filePath);
        Uri data;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(mContext, "com.lulian.Zaiyunbao.fileProvider", file);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /**
     * apk下载安装
     * @param url
     */
    private void downapk(String url) {
        verifyStoragePermissions(WeclcomeActivity.this);

//        long id = downloadManager.enqueue(request);
        info = new TaskInfo("zyb.apk", Environment.getExternalStorageDirectory().getAbsolutePath() + "/", url);
        dialog.downBtn.setClickable(false);
        start(dialog.downBtn);

    }

    public void start(View view) {
        //创建下载任务
        runnable = new DownloadRunnable(info);
        //开始下载任务
        new Thread(runnable).start();
        //开始Handler循环
        handler.sendEmptyMessageDelayed(1, 200);
    }
    /**
     * 停止下载按钮监听
     * @param view
     */
    public void stop(View view) {
        //调用DownloadRunnable中的stop方法，停止下载
        runnable.stop();
        runnable = null;//强迫症，不用的对象手动置空
    }


    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission1 = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.READ_PHONE_STATE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
        int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
        }
    }
}
