package com.lulian.Zaiyunbao.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.ui.activity.pay.WalletChargeResultActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信支付结果回调Activity
 * 这个Activity是无界面的,接受到回调后,立即finish,然后转到统一的支付结果界面去展示
 * 支付结果
 *
 * @author hxb
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Log.i("onReq", "");
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.i("onResp", resp.errCode + "");
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case WxPayManager.PAY_RESULT_SUCC:  //支付成功
                    jumpToChargeResult(true);
                    break;
                case WxPayManager.PAY_RESULT_ERROR: //错误
                    jumpToChargeResult(false);
                    break;
                case WxPayManager.PAY_RESULT_CANCEL:  //取消支付
                    RxToast.warning("取消支付");
                    break;
            }
            finish();
        }
    }

    /**
     * 跳转至充值结果界面
     */
    private void jumpToChargeResult(boolean isSuccess) {
        Intent it = new Intent(this, WalletChargeResultActivity.class);
        it.putExtra("isSuccess", isSuccess);
        it.putExtra("RemainingSum", "1000");
        startActivity(it);
    }
}