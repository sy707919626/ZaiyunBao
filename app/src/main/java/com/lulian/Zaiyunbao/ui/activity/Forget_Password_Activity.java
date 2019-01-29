package com.lulian.Zaiyunbao.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/10/23.
 */

public class Forget_Password_Activity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.forget_pwd_phone)
    TextView forgetPwdPhone;
    @BindView(R.id.forget_pwdclearEdit_code)
    ClearEditText forgetPwdclearEditCode;
    @BindView(R.id.text_forget_code)
    TextView textForgetCode;
    @BindView(R.id.forget_pwd_next)
    Button forgetPwdNext;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_forget_password;
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

        textDetailContent.setText("忘记密码");
        textDetailRight.setVisibility(View.GONE);
        forgetPwdPhone.setText("13657352060");
    }

    @OnClick({R.id.forget_pwdclearEdit_code, R.id.text_forget_code, R.id.forget_pwd_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forget_pwdclearEdit_code:

                break;

            case R.id.text_forget_code:
                MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);
                myCountDownTimer.start();

                break;


            case R.id.forget_pwd_next:
                Intent intent = new Intent(this, Setting_Password_Activity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    public class MyCountDownTimer extends CountDownTimer {

        /**
         * 重写计时器
         *
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        @Override
        public void onTick(long millisUntilFinished) {
            if (textForgetCode != null) {
                textForgetCode.setClickable(false);
                textForgetCode.setText("验证码已发送到你的手机，请查看 \n(" +
                        millisUntilFinished / 1000 + "s)");
                textForgetCode.setTextColor(getResources().getColor(R.color.text_hint_bule));
                textForgetCode.setBackgroundResource(R.drawable.button_bg_xuxin);
            }
        }

        @Override
        public void onFinish() {
            if (textForgetCode != null) {
                textForgetCode.setClickable(true);
                textForgetCode.setText("没收到?重新发送验证码");
                textForgetCode.setBackgroundColor(0);
                textForgetCode.setTextColor(getResources().getColor(R.color.text_hint_bule));
                textForgetCode.setBackgroundResource(0);
            }
        }
    }

}
