package com.lulian.Zaiyunbao.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.MD5Utils;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/19.
 */

public class Register_Two_Activity extends BaseActivity {


    @BindView(R.id.image_back_login_bar)
    ImageView imageBackLoginBar;
    @BindView(R.id.text_login_content)
    TextView textLoginContent;
    @BindView(R.id.agree_login)
    TextView agreeLogin;
    @BindView(R.id.login_bar_title)
    RelativeLayout loginBarTitle;
    @BindView(R.id.register_two_hint_text)
    TextView registerTwoHintText;
    @BindView(R.id.register_two_hint_phone_text)
    TextView registerTwoHintPhoneText;
    @BindView(R.id.verification_Code_register)
    EditText verificationCodeRegister;
    @BindView(R.id.register_two_getCode)
    Button registerTwoGetCode;
    @BindView(R.id.register_two_next)
    Button registerTwoNext;
    @BindView(R.id.register_two_bottom_text)
    TextView registerTwoBottomText;
    @BindView(R.id.register_two_yonghu_text)
    TextView registerTwoYonghuText;

    private String tempMobile = "";
    private String Code; //验证码
    private String Invitation_Code = ""; //邀请码

    @Override
    protected int setLayoutId() {
        return R.layout.activity_register_two;
    }

    @Override
    protected void init() {
        stepActivities.add(this);
        ImmersionBar.with(this)
                .titleBar(R.id.login_bar_title)
                .titleBarMarginTop(R.id.login_bar_title)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        textLoginContent.setVisibility(View.GONE);
        agreeLogin.setVisibility(View.GONE);

//        Code = getIntent().getStringExtra("Code");
        Invitation_Code = getIntent().getStringExtra("invitation_Code");
        tempMobile = getIntent().getStringExtra("register_phone");

        registerTwoHintPhoneText.setText(tempMobile);


    }

    @OnClick({R.id.register_two_getCode, R.id.register_two_next, R.id.register_two_yonghu_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.register_two_getCode:
                final MyCountDownTimerCode myCountDownTimer =
                        new MyCountDownTimerCode(60000, 1000, registerTwoGetCode);
                myCountDownTimer.start();
                //处理获取手机验证码网络请求
                mApi.sendVerifySms(GlobalParams.sToken, tempMobile, "1")
                        .compose(RxHttpResponseCompat.<String>compatResult())
                        .subscribe(new ErrorHandlerSubscriber<String>() {
                            @Override
                            public void onNext(String s) {
                                JSONObject jsonObject = JSONObject.parseObject(s);
                                Code = jsonObject.getString("VerifyCode");
                                myCountDownTimer.start();
                            }
                        });
                break;

            case R.id.register_two_next: //下一步

                if (verificationCodeRegister.getText().toString().trim().equals("")) {
                    RxToast.warning("验证码不能为空");

                } else if (!Code.equals(MD5Utils.getPwd(verificationCodeRegister.getText().toString().trim()))) {
                    RxToast.warning("验证码输入错误，请重新填写");

                } else {
                    Intent intent = new Intent(Register_Two_Activity.this, Register_Tthree_Activity.class);
                    intent.putExtra("register_phone", tempMobile);
                    intent.putExtra("invitation_Code", Invitation_Code); //邀请码
                    startActivity(intent);
                }

                break;

            case R.id.register_two_yonghu_text:
                //用户协议
                startActivity(new Intent(this, UserAgreementActivity.class));
                break;
        }
    }


    class MyCountDownTimerCode extends CountDownTimer {

        public Button btn_djs;

        public MyCountDownTimerCode(long millisInFuture, long countDownInterval, Button btn_djs) {
            super(millisInFuture, countDownInterval);
            this.btn_djs = btn_djs;
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            btn_djs.setBackgroundResource(R.drawable.button_bg_xuxin);
            btn_djs.setTextColor(getResources().getColor(R.color.text_hint_black2));
            btn_djs.setClickable(false);
            btn_djs.setText(l / 1000 + "s后重新获取");
        }

        //计时完毕的方法

        @Override
        public void onFinish() {
            //重新给Button设置文字
            btn_djs.setBackgroundResource(R.drawable.button_bg_xuxin);
            btn_djs.setTextColor(getResources().getColor(R.color.text_hint_bule));
            btn_djs.setText("重新发送");
            //设置可点击
            btn_djs.setClickable(true);
        }
    }

}
