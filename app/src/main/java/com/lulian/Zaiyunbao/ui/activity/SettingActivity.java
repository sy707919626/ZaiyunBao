package com.lulian.Zaiyunbao.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.AppVersionBean;
import com.lulian.Zaiyunbao.Bean.TaskInfo;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.DataCleanManager;
import com.lulian.Zaiyunbao.common.widget.LoginUtil;
import com.lulian.Zaiyunbao.common.widget.ProjectUtil;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.DownloadRunnable;
import com.lulian.Zaiyunbao.ui.LevelDialog;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/10/22.
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.update_login_password)
    RelativeLayout updateLoginPassword;
    @BindView(R.id.clear_cache_textview)
    TextView clearCacheTextview;
    @BindView(R.id.image_view_clear)
    ImageView imageViewClear;
    @BindView(R.id.clear_cache)
    RelativeLayout clearCache;
    @BindView(R.id.inspect_update)
    RelativeLayout inspectUpdate;
    @BindView(R.id.exit_login)
    Button exitLogin;

    private float cacheSize;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_setting;
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

        textDetailContent.setText("设置");
        textDetailRight.setVisibility(View.GONE);
        showCacheSize();
    }

    private void showCacheSize() {
        try {
            cacheSize = DataCleanManager.getFolderSize(getCacheDir());
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                cacheSize += DataCleanManager.getFolderSize(getExternalCacheDir());
            }

            clearCacheTextview.setText(DataCleanManager.getFormatSize(cacheSize));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.update_login_password, R.id.clear_cache, R.id.inspect_update, R.id.exit_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.update_login_password:
                Intent intent = new Intent(this, Updae_pwd_Activity.class);
                intent.putExtra("isForget", false);
                startActivity(intent);

                break;

            case R.id.clear_cache:
                if (cacheSize > 0) {
                    final AlertDialog builder = new AlertDialog.Builder(this)
                            .create();
                    builder.show();
                    if (builder.getWindow() == null) return;
                    builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
                    TextView msg = builder.findViewById(R.id.tv_msg);
                    Button cancle = builder.findViewById(R.id.btn_cancle);
                    Button sure = builder.findViewById(R.id.btn_sure);

                    if (msg == null || cancle == null || sure == null) return;

                    msg.setText("是否清空缓存?");

                    cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.dismiss();
                        }
                    });
                    sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataCleanManager.clearAllCache(getApplicationContext());
                            showCacheSize();
                            builder.dismiss();
                        }
                    });

                } else {
                    RxToast.warning("当前应用没有缓存数据！");
                }

                break;

            case R.id.inspect_update:
                checkUpdate();
                break;

            case R.id.exit_login:
                LoginUtil.loginOut();
                break;

            default:
                break;
        }
    }

//    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setMessage(message);
//        builder.setPositiveButton("确定", onClickListener);
//        builder.setNegativeButton("取消", null);
//        return builder;
//    }

    LevelDialog dialog;
    private TaskInfo info;//任务信息
    private DownloadRunnable runnable;//下载任务
    public final static int REQUEST_READ_PHONE_STATE = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};

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
        verifyStoragePermissions(SettingActivity.this);

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


    /**
     * 上传app资料和  检测app更新
     */
    private AppVersionBean mAppVersionBean;
    private void checkUpdate() {
        mApi.GetAppVersion(GlobalParams.sToken)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .compose(this.<String>bindUntilEvent(ActivityEvent.STOP))
                .compose(this.<String>bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {

                        mAppVersionBean = MyApplication.get().getAppComponent().getGson().fromJson(s, AppVersionBean.class);

                        if (Integer.valueOf(mAppVersionBean.getMinVersionCode()) >
                                ProjectUtil.getVersionCode(mContext)) {

                            dialog = new LevelDialog(SettingActivity.this, new LevelDialog.OnLevelListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm) {
                                    downapk("http://"+ mAppVersionBean.getDownLoadUrl());
                                }
                            });
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.show();
                            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            dialog.downBtn.setCurrentText("立即升级");
                        } else {
                            RxToast.success("当前版本是最新版本！");
                        }

                    }
                });

    }

}
