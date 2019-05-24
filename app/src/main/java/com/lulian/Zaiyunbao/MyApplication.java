package com.lulian.Zaiyunbao;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.bumptech.glide.Glide;
import com.lulian.Zaiyunbao.common.widget.GlideCacheUtil;
import com.lulian.Zaiyunbao.di.component.AppComponent;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.di.component.DaggerAppComponent;
import com.lulian.Zaiyunbao.di.module.AppModule;
import com.lulian.Zaiyunbao.di.module.HttpModule;
import com.lulian.Zaiyunbao.ui.activity.CrashHandler;
import com.lulian.Zaiyunbao.ui.base.BaiduMapBase;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class MyApplication extends Application {
    private static MyApplication mMyApplication;
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {

                return new ClassicsHeader(context)
                        .setDrawableArrowSize(17)
                        .setTextSizeTitle(14);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context)
                        .setDrawableSize(15)
                        .setAccentColorId(R.color.color_99)
                        .setTextSizeTitle(TypedValue.COMPLEX_UNIT_SP, 14);
            }
        });
    }

    AppComponent mAppComponent;

    public static MyApplication get() {
        return mMyApplication;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static BaiduMapBase mBaiduMap = null;
    @Override
    public void onCreate() {
        super.onCreate();
        mMyApplication = this;

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .httpModule(new HttpModule()).build();

        initActivityLifecycle();
        registerWxAppId();

        CrashHandler.getInstance().init(this, this);

        //****************************************百度地图***********************************
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        mBaiduMap = new BaiduMapBase(this);
        mBaiduMap.startLocate();
    }


    /**
     * 注册微信sdk的Appid
     */
    private void registerWxAppId() {
        IWXAPI msgApi = WXAPIFactory.createWXAPI(this.getApplicationContext(), null);
        boolean registerIsSucc = msgApi.registerApp(Constants.WX_APPID);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    private void initActivityLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(final Activity activity) {

                // ActivityLifecycleCallbacks 中所有方法的调用时机都是在 Activity 对应生命周期的 Super 方法中进行的
                // 所以在 Activity 的 onCreate 方法中使用 setContentView 必须在 super.onCreate(savedInstanceState); 之前
                // 已经有多个title 多次判断

                // 找到 Toolbar 的标题栏并设置标题名
                View title = activity.findViewById(R.id.title);
                if (title != null) {
                    String titleStr = (String) activity.getTitle();
                    if (!titleStr.equals(getString(R.string.app_name))) {
                        ((TextView) title).setText(titleStr);
                    }
                }

                //找到 Toolbar 的返回按钮,并且设置点击事件,点击关闭这个 Activity
                View titleBack = activity.findViewById(R.id.title);
                if (titleBack != null) {
                    if (!titleBack.hasOnClickListeners()) {
                        titleBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activity.finish();
                            }
                        });
                    }
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    /**
     * 用户点击了Home键或者Back键退出应用，所有UI界面被隐藏，这时候应该释放一些不可见的时候非必须的资源
     * 系统环境 非APP可用内存
     * Release memory when the UI becomes hidden or when system resources become low.
     *
     * @param level the memory-related event that was raised.
     */
    @Override
    public void onTrimMemory(int level) {
        // Determine which lifecycle or system event was raised.
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
        switch (level) {
            //app处于后台  清除内存缓存
            case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
            case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
            case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:
            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:
                /*
                   Release any UI objects that currently hold memory.
                   The user interface has moved to the background.
                */
                GlideCacheUtil.getInstance().clearImageMemoryCache(this);

                break;
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:
                /*
                   Release any memory that your app doesn't need to run.
                   The device is running low on memory while the app is running.
                   The event raised indicates the severity of the memory-related event.
                   If the event is TRIM_MEMORY_RUNNING_CRITICAL, then the system will
                   begin killing background processes.
                */
                break;

            default:
                /*
                  Release any non-critical data structures.
                  The app received an unrecognized memory level value
                  from the system. Treat this as a generic low-memory message.
                */
                break;
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //内存低的时候，清理Glide的缓存
        GlideCacheUtil.getInstance().clearImageMemoryCache(this);
    }


}
