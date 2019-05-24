package com.lulian.Zaiyunbao.ui.activity.pay;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 钱包充值 结果显示界面
 */
public class WalletChargeResultActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.tv_remaining_sum)
    TextView tvRemainingSum;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_wallet_charge_result;
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

        textDetailRight.setVisibility(View.GONE);
        getData();
    }

    @OnClick({R.id.btn_confirm})
    public void onViewClicked(View v) {
        finish();
    }

    // 获取余额
    private void getData() {
        mApi.myMoneyInfo(GlobalParams.sToken, GlobalParams.sUserId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .compose(this.<String>bindUntilEvent(ActivityEvent.STOP))
                .compose(this.<String>bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        JSONObject jsonObject = JSONObject.parseObject(s);
                        tvRemainingSum.setText(jsonObject.getString("Balance") + "元");
                    }
                });
    }
}
