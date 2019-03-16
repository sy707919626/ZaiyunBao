package com.lulian.Zaiyunbao.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.common.widget.VerificationCode;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MARK on 2018/7/7.
 */

public class ResetPaypwdActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.pay_one_password)
    VerificationCode payOnePassword;
    @BindView(R.id.pay_two_password)
    VerificationCode payTwoPassword;
    @BindView(R.id.paypwd_btn_commit)
    Button paypwdBtnCommit;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_reset_paypwd;
    }

    @Override
    protected void init() {
        stepActivities.add(this);
        ImmersionBar.with(this)
                .titleBar(R.id.detail_bar_title)
                .titleBarMarginTop(R.id.detail_bar_title)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        if (GlobalParams.isPayPwd) {
            textDetailContent.setText("重置支付密码");
        } else {
            textDetailContent.setText("设置支付密码");
        }

        textDetailRight.setVisibility(View.GONE);
    }

    @OnClick({R.id.paypwd_btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.paypwd_btn_commit:

                String PayPwdOne = payOnePassword.getVerification().toString();
                String PayPwdTwo = payTwoPassword.getVerification().toString();

                if (PayPwdOne.length()<6 && PayPwdTwo.length()<6){
                    RxToast.warning("支付密码必须设置6位数字");
                } else if (PayPwdOne.equals("") || PayPwdTwo.equals("")) {
                    RxToast.warning("请输入支付密码");
                } else if (!PayPwdOne.equals(PayPwdTwo)) {
                    RxToast.warning("两次输入的支付密码不一致");
                } else {

                    mApi.modifyPayPwd(GlobalParams.sToken, GlobalParams.sUserId, PayPwdOne)
                            .compose(RxHttpResponseCompat.<String>compatResult())
                            .subscribe(new ErrorHandlerSubscriber<String>() {
                                @Override
                                public void onNext(String s) {
                                    RxToast.success("支付密码设置成功");
                                    GlobalParams.setIsPayPwd(true);
                                    stepfinishAll();
                                }
                            });
                }

                break;

            default:
                break;
        }
    }

}
