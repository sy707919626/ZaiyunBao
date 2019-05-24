package com.lulian.Zaiyunbao.ui.activity;

import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.IDCard;
import com.lulian.Zaiyunbao.common.widget.LoginUtil;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/10/22.
 */

public class Setting_Password_Activity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.text_forget_code)
    TextView textForgetCode;
    @BindView(R.id.setting_parssword_new_text)
    TextView settingParsswordNewText;
    @BindView(R.id.setting_parssword_new)
    EditText settingParsswordNew;
    @BindView(R.id.setting_parssword_new_Password)
    CheckBox settingParsswordNewPassword;
    @BindView(R.id.setting_parssword_confirm_text)
    TextView settingParsswordConfirmText;
    @BindView(R.id.setting_parssword_confirm)
    EditText settingParsswordConfirm;
    @BindView(R.id.setting_parssword_confirm_Password)
    CheckBox settingParsswordConfirmPassword;
    @BindView(R.id.setting_pwd_complete)
    Button settingPwdComplete;
    private String forgetPhone = "";

    @Override
    protected int setLayoutId() {
        return R.layout.activity_setting_password;
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


        forgetPhone = getIntent().getStringExtra("forget_phone");

        textDetailContent.setText("设置密码");
        textDetailRight.setVisibility(View.GONE);


        settingPwdComplete.setEnabled(false);

        settingParsswordNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(settingParsswordNew.getText().toString().trim()) &&
                        !TextUtils.isEmpty(settingParsswordConfirm.getText().toString().trim())) {
                    settingPwdComplete.setEnabled(true);

                } else {
                    settingPwdComplete.setEnabled(false);
                }
            }
        });

        settingParsswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(settingParsswordNew.getText().toString().trim()) &&
                        !TextUtils.isEmpty(settingParsswordConfirm.getText().toString().trim())) {
                    settingPwdComplete.setEnabled(true);

                } else {
                    settingPwdComplete.setEnabled(false);
                }
            }
        });

        settingParsswordNewPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    settingParsswordNew.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    settingParsswordNew.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        settingParsswordConfirmPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    settingParsswordConfirm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    settingParsswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

    }

    @OnClick({R.id.setting_pwd_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_pwd_complete:
                //处理密码修改逻辑
                String pwd = settingParsswordNew.getText().toString().trim();
                String pwdAgain = settingParsswordConfirm.getText().toString().trim();
                if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdAgain)) {
                    RxToast.warning("密码不能为空!");
                } else if (pwd.length() < 8 || pwd.length() > 16) {
                    RxToast.warning("请输入8到16位的密码!");
                } else if (IDCard.isNumeric(pwd) || IDCard.isLetter(pwd)) {
                    RxToast.warning("密码不能为纯数字或纯字母!");
                } else if (pwd.equals(pwdAgain)) {
                    mApi.modifyPwd(GlobalParams.sToken, forgetPhone, settingParsswordNew.getText().toString().trim())
                            .compose(RxHttpResponseCompat.<String>compatResult())
                            .subscribe(new ErrorHandlerSubscriber<String>() {
                                @Override
                                public void onNext(String s) {
                                    RxToast.success("密码修改成功，请重新登录");

                                    stepfinishAll();
                                    LoginUtil.loginOut();
                                }
                            });
                } else {
                    RxToast.warning("密码输入不一致!");
                }

                break;
            default:
                break;
        }
    }

}
