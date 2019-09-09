package com.lulian.Zaiyunbao.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.AppVersionBean;
import com.lulian.Zaiyunbao.Bean.TaskInfo;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.ProjectUtil;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.jg.ExampleUtil;
import com.lulian.Zaiyunbao.ui.DownloadRunnable;
import com.lulian.Zaiyunbao.ui.LevelDialog;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.lulian.Zaiyunbao.ui.fragment.BuyFragment;
import com.lulian.Zaiyunbao.ui.fragment.LeaseFragment;
import com.lulian.Zaiyunbao.ui.fragment.ManageFragment;
import com.lulian.Zaiyunbao.ui.fragment.MeFragment;
import com.lulian.Zaiyunbao.ui.fragment.ServiceFragment;
import java.io.File;
import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    public static final int INDEX_HOME = 0;
    public static final int INDEX_GUANLI = 1;
    public static final int INDEX_GOUMAI = 2;
    public static final int INDEX_FUWU = 3;
    public static final int INDEX_ME = 4;
    @BindView(R.id.main_content)
    FrameLayout mainContent;
    @BindView(R.id.tab_separate_imgv)
    ImageView tabSeparateImgv;
    @BindView(R.id.rbtn_home)
    RadioButton rbtnHome;
    @BindView(R.id.rbtn_guanli)
    RadioButton rbtnGuanli;
    @BindView(R.id.rbtn_fuwu)
    RadioButton rbtnFuwu;
    @BindView(R.id.rbtn_mine)
    RadioButton rbtnMine;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.rbtn_goumai)
    RadioButton rbtnGoumai;
    FragmentManager mFragmentManager;
    LeaseFragment mLeaseFragment;
    ManageFragment mManageFragment;
    ServiceFragment mServiceFragment;
    BuyFragment mBuyFragment;
    MeFragment mMeFragment;
    private Fragment currentFragment = new Fragment();
    /**
     * 主页监听按两次返回键退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    private long mExitTime;

    public static boolean isForeground = false;

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.lulian.Zaiyunbao.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";


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
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void init() {
        ImmersionBar.with(this).init();
        initView();

        registerMessageReceiver();
        JPushInterface.init(getApplicationContext());

        verifyStoragePermissions(this);
        checkUpdate();
    }

    private void initView() {
        mFragmentManager = getSupportFragmentManager();
        mLeaseFragment = new LeaseFragment();
        mManageFragment = new ManageFragment();
        mBuyFragment = new BuyFragment();
        mServiceFragment = new ServiceFragment();
        mMeFragment = new MeFragment();

        radioGroup.setOnCheckedChangeListener(this);
        setSelectIndex(INDEX_HOME);

    }

    /**
     * 展示Fragment
     */
    public void showFragment(Fragment fragment) {
        if (currentFragment != fragment) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            // 设置Fragment的切换动画
            transaction.setCustomAnimations(R.anim.cu_push_right_in, R.anim.cu_push_left_out);

            transaction.hide(currentFragment);
            currentFragment = fragment;
            if (!fragment.isAdded()) {
                transaction.add(R.id.main_content, fragment).show(fragment).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.rbtn_home:
                setSelectIndex(INDEX_HOME);
                break;

            case R.id.rbtn_guanli:
                setSelectIndex(INDEX_GUANLI);
                break;

            case R.id.rbtn_goumai:
                setSelectIndex(INDEX_GOUMAI);
                break;

            case R.id.rbtn_fuwu:
                setSelectIndex(INDEX_FUWU);
                break;

            case R.id.rbtn_mine:
                setSelectIndex(INDEX_ME);
                break;
        }
    }

    public void setSelectIndex(int index) {
        switch (index) {
            case INDEX_HOME:
                showFragment(mLeaseFragment);
                rbtnHome.setTextColor(getResources().getColor(R.color.radio_color_select));
                rbtnGuanli.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnGoumai.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnFuwu.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnMine.setTextColor(getResources().getColor(R.color.radio_color));

                break;
            case INDEX_GUANLI:
                showFragment(mManageFragment);
                rbtnHome.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnGuanli.setTextColor(getResources().getColor(R.color.radio_color_select));
                rbtnGoumai.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnFuwu.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnMine.setTextColor(getResources().getColor(R.color.radio_color));
                break;

            case INDEX_GOUMAI:
                showFragment(mBuyFragment);
                rbtnHome.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnGuanli.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnGoumai.setTextColor(getResources().getColor(R.color.radio_color_select));
                rbtnFuwu.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnMine.setTextColor(getResources().getColor(R.color.radio_color));
                break;

            case INDEX_FUWU:
                showFragment(mServiceFragment);
                rbtnHome.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnGuanli.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnGoumai.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnFuwu.setTextColor(getResources().getColor(R.color.radio_color_select));
                rbtnMine.setTextColor(getResources().getColor(R.color.radio_color));
                break;
            case INDEX_ME:
                showFragment(mMeFragment);
                rbtnHome.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnGuanli.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnGoumai.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnFuwu.setTextColor(getResources().getColor(R.color.radio_color));
                rbtnMine.setTextColor(getResources().getColor(R.color.radio_color_select));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finishAll();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    String title = intent.getStringExtra(KEY_TITLE);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }


                    JSONObject obj = new JSONObject();
                    obj.put("CreateId", GlobalParams.sUserId);
                    obj.put("Content", messge);
                    obj.put("Title", title);

                    RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                            obj.toString());
                    mApi.AddSystem_Messages(GlobalParams.sToken, body)
                            .compose(RxHttpResponseCompat.<String>compatResult())
                            .subscribe(new ErrorHandlerSubscriber<String>() {
                                @Override
                                public void onNext(String s) {
                                    RxToast.success("您有新的消息！");
                                }
                            });
                }
            } catch (Exception e){
            }
        }
    }

    private void setCostomMsg(String msg){
//        RxToast.showToast(msg);
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(mContext, "com.lulian.Zaiyunbao.fileProvider", file);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(file);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /**
     * apk下载安装
     * @param url
     */
    private void downapk(String url) {
        verifyStoragePermissions(MainActivity.this);

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
        //6.0系统以上动态申请读写权限
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }


    /**
     * 上传app资料和  检测app更新
     */
    private AppVersionBean mAppVersionBean;
    private void checkUpdate() {
        mApi.GetAppVersion(GlobalParams.sToken)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {

                        mAppVersionBean = MyApplication.get().getAppComponent().getGson().fromJson(s, AppVersionBean.class);

                        if (Integer.valueOf(mAppVersionBean.getMinVersionCode()) >
                                ProjectUtil.getVersionCode(mContext)) {

                            dialog = new LevelDialog(MainActivity.this, new LevelDialog.OnLevelListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm) {
                                    downapk("http://"+ mAppVersionBean.getDownLoadUrl());
                                }
                            });
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.show();
                            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            dialog.downBtn.setCurrentText("立即升级");
                        }

                    }
                });

    }
}
