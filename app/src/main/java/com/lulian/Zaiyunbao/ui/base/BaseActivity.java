package com.lulian.Zaiyunbao.ui.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lulian.Zaiyunbao.Bean.SaleEntity;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.http.util.PermissionsChecker;
import com.lulian.Zaiyunbao.data.http.ApiService;
import com.lulian.Zaiyunbao.ui.activity.DressSelectorActivity;
import com.lulian.Zaiyunbao.ui.activity.PermissionsActivity;
import com.lulian.Zaiyunbao.ui.dialog.MyAdapter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends RxAppCompatActivity {
    /**
     * 请求码
     */
    public static final int REQUEST_CODE = 0;
    /**
     * 所需的全部权限
     */
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    public static final int REQUEST_CODE_SCAN = 1; //扫一扫
    public static final int REQUEST_LOCATION = 2;  //定位
    public static final int ADDRESS_LOCATION = 3; //获取切换的地址
    private static final String TAG = "BaseActivity";
    //防止按钮多次点击问题
    private static final int MIN_DELAY_TIME = 1000;  // 两次点击间隔不能少于1000ms
    public static List<BaseActivity> stepActivities = new LinkedList<BaseActivity>();
    /**
     * 获取到前台进程的Activity  就是当前在运行的activity
     */
    private static Activity mForegroundActivity = null;
    /**
     * 用户保存所有用户操作过的activity
     */
    private static List<BaseActivity> mActivities = new LinkedList<BaseActivity>();
    private static long lastClickTime;
    public SaleEntity mSaleEntity;
    public ApiService mApi;
    public Context mContext;
    Unbinder mUnbinder;
    MyApplication mApplication;
    private Handler mHandler;
    private int originalW;
    private int originalH;
    private boolean isShowHide;
    /**
     * 权限检测器
     */
    private PermissionsChecker mPermissionsChecker;

    /**
     * 获得当前Activity
     *
     * @return
     */
    public static Activity getForegroundActivity() {
        return mForegroundActivity;
    }

    /**
     * 关闭所有步骤Activity
     */
    public static void stepfinishAll() {
        List<BaseActivity> copy;
        synchronized (stepActivities) {
            copy = new ArrayList<BaseActivity>(stepActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }

        stepActivities.clear();
    }

    /**
     * 关闭所有Activity
     */
    public static void finishAll() {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
    }

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(setLayoutId());
        mApplication = (MyApplication) getApplication();
        mApi = mApplication.getAppComponent().getApiService();

        mUnbinder = ButterKnife.bind(this);

        synchronized (mActivities) {
            mActivities.add(this);
        }

        mPermissionsChecker = new PermissionsChecker(this);

        init();

    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
        this.overridePendingTransition(R.anim.activity_open, 0);
    }

    public void scanCode() {
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        } else {
            Intent intent = new Intent(this, CaptureActivity.class);
            /**
             * ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
             * 也可以不传这个参数
             * 不传的话  默认都为默认不震动  其他都为true
             **/
            ZxingConfig config = new ZxingConfig();
            config.setPlayBeep(true);
            config.setShake(true);
            intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
            startActivityForResult(intent, REQUEST_CODE_SCAN);
        }
    }

    public void changeAddress(String address) {
        Intent intent = new Intent(this, DressSelectorActivity.class);
        intent.putExtra("currentlocation", address);
        startActivityForResult(intent, ADDRESS_LOCATION);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mForegroundActivity = null;
    }

    /**
     * 设置布局ID
     *
     * @return
     */
    protected abstract int setLayoutId();

    /**
     * 初始化操作
     */
    protected abstract void init();

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        RxBus.get().unregister(this);

        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        mActivities.remove(this);
    }

    /**
     * Activity的返回操作调用此方法
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }

    //处理dialog 模糊背景
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Bitmap captureScreen(Activity activity) {
        activity.getWindow().getDecorView().destroyDrawingCache();  //先清理屏幕绘制缓存(重要)
        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);
        Bitmap bmp = activity.getWindow().getDecorView().getDrawingCache();
        //获取原图尺寸
        originalW = bmp.getWidth();
        originalH = bmp.getHeight();
        //对原图进行缩小，提高下一步高斯模糊的效率
        bmp = Bitmap.createScaledBitmap(bmp, originalW / 4, originalH / 4, false);
        return bmp;
    }

    public void setScreenBgLight(Dialog dialog) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp;
        if (window != null) {
            lp = window.getAttributes();
            lp.dimAmount = 0.2f;
            window.setAttributes(lp);
        }
    }

    public void handleBlur(ImageView dialogBg, Handler mHandler) {
        Bitmap bp = captureScreen(this);
        bp = blur(bp);                      //对屏幕截图模糊处理
        //将模糊处理后的图恢复到原图尺寸并显示出来
        bp = Bitmap.createScaledBitmap(bp, originalW, originalH, false);
        dialogBg.setImageBitmap(bp);
        dialogBg.setVisibility(View.VISIBLE);
        //防止UI线程阻塞，在子线程中让背景实现淡入效果
        asyncRefresh(true, dialogBg, mHandler);
    }

    public void asyncRefresh(boolean in, final ImageView dialogBg, final Handler mHandler) {
        //淡出淡入效果的实现
        if (in) {    //淡入效果
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 256; i += 5) {
                        refreshUI(i, dialogBg);//在UI线程刷新视图
                        try {
                            Thread.sleep(4);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } else {    //淡出效果
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 255; i >= 0; i -= 5) {
                        refreshUI(i, dialogBg);//在UI线程刷新视图
                        try {
                            Thread.sleep(4);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //当淡出效果完毕后发送消息给mHandler把对话框背景设为不可见
                    mHandler.sendEmptyMessage(0);
                }
            }).start();
        }
    }

    private void refreshUI(final int i, final ImageView dialogBg) {
        runOnUiThread(new Runnable() {
            @SuppressLint("NewApi")
            @Override
            public void run() {
                dialogBg.setImageAlpha(i);
            }
        });
    }

    public void hideBlur(ImageView dialogBg, Handler mHandler) {
        //把对话框背景隐藏
        asyncRefresh(false, dialogBg, mHandler);
        System.gc();
    }

    @SuppressLint("NewApi")
    private Bitmap blur(Bitmap bitmap) {
        //使用RenderScript对图片进行高斯模糊处理
        Bitmap output = Bitmap.createBitmap(bitmap); // 创建输出图片
        RenderScript rs = RenderScript.create(this); // 构建一个RenderScript对象
        ScriptIntrinsicBlur gaussianBlue = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs)); //
        // 创建高斯模糊脚本
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap); // 开辟输入内存
        Allocation allOut = Allocation.createFromBitmap(rs, output); // 开辟输出内存
        float radius = 10f;     //设置模糊半径
        gaussianBlue.setRadius(radius); // 设置模糊半径，范围0f<radius<=25f
        gaussianBlue.setInput(allIn); // 设置输入内存
        gaussianBlue.forEach(allOut); // 模糊编码，并将内存填入输出内存
        allOut.copyTo(output); // 将输出内存编码为Bitmap，图片大小必须注意
        rs.destroy();
        //rs.releaseAllContexts(); // 关闭RenderScript对象，API>=23则使用rs.releaseAllContexts()
        return output;
    }

    public void BaseDialog(Context context, final ImageView dialogBg,
                           final String[] list2, String type, final String title, final Handler mHandler,
                           final OnItemClickListener onItemClickListener) {

        List<SaleEntity> list = new ArrayList();

        for (String a : list2) {
            SaleEntity saleEntity = new SaleEntity();
            saleEntity.setTitle(a);

            if (!type.equals("")) {
                if (a.equals(type)) {
                    saleEntity.setChecked(true);
                } else {
                    saleEntity.setChecked(false);
                }
            } else {
                saleEntity.setChecked(false);
            }

            list.add(saleEntity);
        }

        final AlertDialog builder = new AlertDialog.Builder(mContext)
                .create();
        builder.show();

        Window dialogWindow = builder.getWindow();
        if (dialogWindow == null) return;
        dialogWindow.setContentView(R.layout.pop_layout);//设置弹出框加载的布局

        TextView DialogTitleText = builder.findViewById(R.id.dialog_title_text);
        ListView listView = builder.findViewById(R.id.listview);
        LinearLayout layout = builder.findViewById(R.id.dialog_title_layout);
        DialogTitleText.setText(title);

        if (title.equals("")) {
            layout.setVisibility(View.GONE);
        } else {
            layout.setVisibility(View.VISIBLE);
        }

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        final WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.5
        dialogWindow.setAttributes(p);

        final MyAdapter adapter = new MyAdapter(context, list);
        listView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, List<SaleEntity> saleEntity) {
                onItemClickListener.onItemClickListener(position, saleEntity);
                hideBlur(dialogBg, mHandler);     //背景淡出
                builder.dismiss();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface p1) {
                //点击对话框外侧区域取消弹窗的监听
                hideBlur(dialogBg, mHandler);     //背景淡出
            }
        });

    }

    public void BaseDialogTitle(Context context, final ImageView dialogBg,
                                final String content, final String title, final Handler mHandler,
                                final OnClickDialogListener onClickDialogListener) {


        final AlertDialog builder = new AlertDialog.Builder(mContext)
                .create();
        builder.show();

        Window dialogWindow = builder.getWindow();
        if (dialogWindow == null) return;
        dialogWindow.setContentView(R.layout.pop_user);//设置弹出框加载的布局

        TextView titles = builder.findViewById(R.id.tv_name);
        TextView msg = builder.findViewById(R.id.tv_msg);
        Button cancle = builder.findViewById(R.id.btn_cancle);
        Button sure = builder.findViewById(R.id.btn_sure);
        msg.setText(content);
        titles.setText(title);

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        final WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.5
        dialogWindow.setAttributes(p);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideBlur(dialogBg, mHandler);     //背景淡出
                builder.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideBlur(dialogBg, mHandler);     //背景淡出
                onClickDialogListener.onClickLDialogistener();
                builder.dismiss();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface p1) {
                //点击对话框外侧区域取消弹窗的监听
                hideBlur(dialogBg, mHandler);     //背景淡出
            }
        });

    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, List<SaleEntity> data);
    }

    public interface OnClickDialogListener {
        void onClickLDialogistener();
    }
}
