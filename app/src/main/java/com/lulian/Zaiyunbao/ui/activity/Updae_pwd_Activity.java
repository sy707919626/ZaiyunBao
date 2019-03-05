package com.lulian.Zaiyunbao.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.MD5Utils;
import com.lulian.Zaiyunbao.common.widget.MyCountDownTimer;
import com.lulian.Zaiyunbao.common.widget.ProjectUtil;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.common.widget.VerificationCode;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MARK on 2018/6/8.
 */

public class Updae_pwd_Activity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.forgetpwd_edit_name_text)
    TextView forgetpwdEditNameText;
    @BindView(R.id.forgetpwd_edit_name_text2)
    TextView forgetpwdEditNameText2;
    @BindView(R.id.forgetpwd_edit_name)
    ClearEditText forgetpwdEditName;
    @BindView(R.id.forgetpwd_hint_text)
    TextView forgetpwdHintText;
    @BindView(R.id.forgetpwd_hint_phone_text)
    TextView forgetpwdHintPhoneText;
    @BindView(R.id.forgetpwd_edit_code)
    VerificationCode forgetpwdEditCode;
    @BindView(R.id.forgetpwd_btn_getcode)
    Button forgetpwdBtnGetcode;
    @BindView(R.id.forgetpwd_btn_submit)
    Button forgetpwdBtnSubmit;
    @BindView(R.id.forgetpwd_login_type)
    TextView forgetpwdLoginType;

    private String tempMobile;
    private String Code = "";
    private boolean isForget;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_forgetpwd;
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


        textDetailContent.setText("修改密码");
        textDetailRight.setVisibility(View.GONE);

        isForget = getIntent().getBooleanExtra("isForget", false);

        if (isForget) {
            forgetpwdLoginType.setVisibility(View.GONE);
        } else {
            forgetpwdLoginType.setVisibility(View.VISIBLE);
        }

        forgetpwdBtnSubmit.setEnabled(false);

        forgetpwdEditName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(forgetpwdEditName.getText().toString().trim())) {
                    forgetpwdBtnSubmit.setEnabled(true);
                    forgetpwdEditName.setTextSize(18);

                } else {
                    forgetpwdBtnSubmit.setEnabled(false);
                    forgetpwdEditName.setTextSize(18);
                }
            }
        });

        forgetpwdBtnGetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000, forgetpwdBtnGetcode);
                tempMobile = forgetpwdEditName.getText().toString().trim();

                forgetpwdHintPhoneText.setText(tempMobile);

                if (tempMobile.equals("")) {
                    RxToast.warning("请输入手机号码");

                } else if (ProjectUtil.isMobileNO(tempMobile)) {
                    //处理手机验证码获取
                    mApi.sendVerifySms(GlobalParams.sToken, tempMobile, "2")
                            .compose(RxHttpResponseCompat.<String>compatResult())
                            .subscribe(new ErrorHandlerSubscriber<String>() {
                                @Override
                                public void onNext(String s) {
                                    JSONObject jsonObject = JSONObject.parseObject(s);
                                    Code = jsonObject.getString("VerifyCode");
                                    myCountDownTimer.start();
                                }
                            });
                } else {
                    RxToast.warning("请输入正确的手机号码");
                }
            }
        });

        forgetpwdLoginType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Updae_pwd_Activity.this, Update_Phone_Two_Activity.class);
                intent.putExtra("update_Phone", false);
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.forgetpwd_btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forgetpwd_btn_submit:
                if (forgetpwdEditName.getText().toString().trim().equals("")) {
                    RxToast.warning("请输入手机号");
                } else if (forgetpwdEditCode.getVerification().trim().equals("")) {
                    RxToast.warning("请输入验证码");
                } else if (MD5Utils.getPwd(forgetpwdEditCode.getVerification().trim()).equals(Code)) {

                    Intent intent = new Intent(Updae_pwd_Activity.this, Setting_Password_Activity.class);
                    intent.putExtra("forget_phone", forgetpwdEditName.getText().toString().trim());
                    startActivity(intent);


                } else {
                    RxToast.error("验证码有误，请重新输入");
                }
                break;
        }
    }

}
