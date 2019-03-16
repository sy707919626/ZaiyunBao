package com.lulian.Zaiyunbao;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 *
 * @author 22064
 * @date 2018/1/6
 */


public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";

    // 此处SophixEntry应指定真正的Application，也就是你的应用中原有的主Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(MyApplication.class)
    static class RealApplicationStub {
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //如果需要使用MultiDex，需要在此处调用。
        MultiDex.install(this);
        initSophix();
    }

    private void initSophix() {
        String appVersion = "1.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                //三个参数分别对应AndroidManifest里面的
                //AppId、AppSecret、RSA密钥，可以不在AndroidManifest设置而是用此函数来设置
                //Secret。放到代码里面进行设置可以自定义混淆代码，更加安全，此函数的设置会覆盖
                //AndroidManifest里面的设置，如果对应的值设为null，默认会在使用AndroidManifest里面的。
                .setSecretMetaData("25812434", "c9e08805812ac05cf9049d485068e017",
                        "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCIzWT/vNZHPiCWznBEnheJ4lR65AREtfiHUWCSRfUyutycuCdgXQQmVR77UT0i6ssMGrON+FBXsJfUNMwZ9+mzYZJeJuMGctsUGa/kllD8Nx4PlnfBeDPONkZq0C8SetF5H/tbAdewNeR3X939xQ311hQNI8B3SzTfpM4kJe3CAFdqDQbyb4Mgj+Tak2J9HTGEGRqIb7CrfHCCpr5d3QrTLVYr410VPnHXS3lFM73bmcl3w9aLtlFwXcFe8EWD3dQmqteNY7d4j5IxxmOwpmoBHqBwA+fikU6NQ+CiDJ8AakFD5PYQHrHJcR4oOVR+WFeSXmmAu+h9blAitBPWLnQvAgMBAAECggEAUm6BR2BANdmHxZdLKu2f/WO1x/8RW13TDabJbBpfb1wNhxmoEHa8A2UTMBh5oEqeYwLL4uYXc19jfWj6+0LZPU+z5KvF7KLUw73esPyqe++yW24tZxTJEp69u6sKUjZTdosA6IuprfIrYosPttRAXzvKBUjftoJIr+IcnMeGBnlP/tMMm1JByFLVqWRC2rLEGmCiUBwWaUTruOqkSyH3tn1eFsm/3cVj+SxQ8bR0pqQ51S/o+fbN1uyTjDzjhE+Z2Uq7OYVgPiXkx18yJqKoyPR2t9Gb6sZFsK18rr7mTPnIdjp8/kYyW6wBZb22YU9F+5UnCMdapoxNJ3biIKZtkQKBgQDKLec71B2xFpzpJ4njbM7yc1rIz6AJsDNzXLhgx7eEMOEu8yHYJKna1AaPCmMaotovOwoo9qH0tBGIiSFYicdDO5yg8d/8+XezVyAXB3U/1af7UzlodbETgUWA1ob/a/PNAKXzDbosi67NhwhkCqO6GMdz8ms257mwsVwr0/CJ5QKBgQCtOC+6genbg0L0DGGr+3W7xXL5LVCWwNDfL7eyLHOi69sKROfNNiTlOpPLjb63m+bEggbLbeZZgknLYHTbd0lw4Af/5Goi8rQ542a/Ifi7Afq2MuuwzsNDXUqdRmWa8GzTF0L784bV5jcFj+oH/YdsM4SOs9w3kaQop/dKnOoUgwKBgQCNWM5Cm6cueXq13nieUSHViyQ7JDzkyRUPYBFfSAFbH7ku07gjnm5JmNYFBuQGaThUsNgHj3Xm2EUxyX1QovJ2ZKS6OJ+6AsbW0OCUeRhVyo+oO6T1DOMBmez3iAPVaqmF9yAN7tjr77QD6JXLK00P++blfuPJ/PNrPnQMCB4wqQKBgQCMUYSQYCbx9F3rVpBUgin4RtWBNo0e4ekRsxTql4nBA7B3nCPun5q5un75GMTS9c61huM8LbW48osW3fcrxXDPqBuy1fSIhPUsI6Ma18kmrE7NA2jb3FJfIpaL2vf50CNHyIMxmC+03H3b2p3phR4Lr5wFAXw145wIdzAi2APjQwKBgFu0ilWuIGT050Z20XtxYG4m437X5cr3TeQvThbbnynbWBhpnhZEEb2a5UD9mGIV7BpQ1ts7Q3EcV0TErtWieGWWpbclV7QHkfg5ed5uCQbJVy9iHAtgDqS9mvdMx77rqvHjjG2cZEywRXRwKWPmTgb1E5FUN+KkeuR82PEAvJXS")
                //默认为false，设为true即调试模式下会输出日志以及不进行
                //补丁签名校验. 线下调试此参数可以设置为true, 它会强制不对补丁进行签名校验, 所有就算
                //补丁未签名或者签名失败也发现可以加载成功. 但是正式发布该参数必须为false, false会
                //对补丁做签名校验, 否则就可能存在安全漏洞风险。
                .setEnableDebug(com.lulian.Zaiyunbao.BuildConfig.DEBUG)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info,
                                       final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                            //不可以直接Process.killProcess(Process.myPid())来杀进程，这样会扰乱Sophix的内部状态。
                            //因此如果需要杀死进程，建议使用这个方法，它在内部做一些适当处理后才杀死本进程。

                            instance.killProcessSafely();
                        }
                    }
                }).initialize();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在      //后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();
        //补丁在后台发布之后, 并不会主动下行推送到客户端, 客户端通过调用queryAndLoadNewPatch方法查询后台补丁是否可用

    }
}
