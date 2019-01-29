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
import com.lulian.Zaiyunbao.common.exception.BaseException;
import com.lulian.Zaiyunbao.common.exception.RxErrorHandler;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.InvitationCode;
import com.lulian.Zaiyunbao.common.widget.ProjectUtil;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/19.
 */

public class Register_One_Activity extends BaseActivity {
    @BindView(R.id.image_back_login_bar)
    ImageView imageBackLoginBar;
    @BindView(R.id.text_login_content)
    TextView textLoginContent;
    @BindView(R.id.agree_login)
    TextView agreeLogin;
    @BindView(R.id.invitation_Code_register)
    InvitationCode invitationCodeRegister;
    @BindView(R.id.user_name_text)
    TextView userNameText;
    @BindView(R.id.user_name_text2)
    TextView userNameText2;
    @BindView(R.id.register_one_edit_name)
    ClearEditText registerOneEditName;
    @BindView(R.id.register_one_next)
    Button registerOneNext;
    @BindView(R.id.register_one_error_text)
    TextView registerOneErrorText;
    @BindView(R.id.login_bar_title)
    RelativeLayout loginBarTitle;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_register_one;
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

        registerOneNext.setEnabled(false);

        registerOneEditName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(registerOneEditName.getText().toString().trim())) {
                    registerOneNext.setEnabled(true);
                    registerOneEditName.setTextSize(18);

                } else {
                    registerOneNext.setEnabled(false);
                    registerOneEditName.setTextSize(16);
                }
            }
        });

    }

    @OnClick({R.id.agree_login, R.id.register_one_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.agree_login:
                //帮助
                startActivity(new Intent(this, HelpActivity.class));
                break;

            case R.id.register_one_next: //下一步
                if (registerOneEditName.getText().toString().trim().equals("")) {
                    RxToast.warning("请输入手机号码");
                } else if (ProjectUtil.isMobileNO(registerOneEditName.getText().toString().trim())) {
                    //验证手机号码是否占用
                    mApi.phoneIsExists(GlobalParams.sToken, registerOneEditName.getText().toString().trim())
                            .compose(RxHttpResponseCompat.<String>compatResult())
                            .subscribe(new ErrorHandlerSubscriber<String>() {
                                @Override
                                public void onNext(String s) {
                                    //处理获取手机验证码网络请求
                                    mApi.sendVerifySms(GlobalParams.sToken, registerOneEditName.getText().toString().trim(), "1")
                                            .compose(RxHttpResponseCompat.<String>compatResult())
                                            .subscribe(new ErrorHandlerSubscriber<String>() {
                                                @Override
                                                public void onNext(String s) {
                                                    JSONObject jsonObject = JSONObject.parseObject(s);

//                                                    Intent intent = new Intent(Register_One_Activity.this,
//                                                            Register_Two_Activity.class);
//
//                                                    intent.putExtra("register_phone", tempMobile);
//                                                    intent.putExtra("invitation_Code", invitationCodeRegister.getInvitation()); //邀请码
//                                                    intent.putExtra("Code", jsonObject.getString("VerifyCode"));
//                                                    startActivity(intent);
//                                                    finish();
                                                }
                                            });

                                    Intent intent = new Intent(Register_One_Activity.this,
                                            Register_Two_Activity.class);

                                    intent.putExtra("register_phone", registerOneEditName.getText().toString().trim());
                                    intent.putExtra("invitation_Code", invitationCodeRegister.getInvitation()); //邀请码
                                    intent.putExtra("Code", "123456");
                                    startActivity(intent);
                                }

                                @Override
                                public void onError(Throwable t) {
                                    RxErrorHandler mRxErrorHandler = new RxErrorHandler();
                                    BaseException exception = mRxErrorHandler.handlerError(t);
                                    registerOneErrorText.setText(exception.getDisplayMessage());
                                }
                            });

                } else {
                    RxToast.warning("请输入正确的手机号码");
                }
                break;
        }
    }

}
