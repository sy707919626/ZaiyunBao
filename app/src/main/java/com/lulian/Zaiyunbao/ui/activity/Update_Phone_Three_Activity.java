package com.lulian.Zaiyunbao.ui.activity;

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
 * Created by Administrator on 2018/9/19.
 */

public class Update_Phone_Three_Activity extends BaseActivity {


    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.updatePhone_three_old_phone)
    TextView updatePhoneThreeOldPhone;
    @BindView(R.id.user_name_text)
    TextView userNameText;
    @BindView(R.id.user_name_text2)
    TextView userNameText2;
    @BindView(R.id.updatePhone_three_edit_name)
    ClearEditText updatePhoneThreeEditName;
    @BindView(R.id.update_hint_text)
    TextView updateHintText;
    @BindView(R.id.updatePhone_three_hint_phone_text)
    TextView updatePhoneThreeHintPhoneText;
    @BindView(R.id.updatePhone_three_edit_code)
    VerificationCode updatePhoneThreeEditCode;
    @BindView(R.id.updatePhone_three_getcode)
    Button updatePhoneThreeGetcode;
    @BindView(R.id.updatePhone_two_commit)
    Button updatePhoneTwoCommit;
    private String Code = "";

    @Override
    protected int setLayoutId() {
        return R.layout.activity_update_phone_three;
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

        textDetailContent.setText("新号码验证");
        textDetailRight.setVisibility(View.GONE);
        updatePhoneTwoCommit.setEnabled(false);

        updatePhoneThreeOldPhone.setText(GlobalParams.sUserPhone);

        updatePhoneThreeEditName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(updatePhoneThreeEditName.getText().toString().trim())) {
                    updatePhoneTwoCommit.setEnabled(true);
                } else {
                    updatePhoneTwoCommit.setEnabled(false);
                }
            }
        });


        updatePhoneThreeGetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempMobile = updatePhoneThreeEditName.getText().toString().trim();

                if (tempMobile.equals("")) {
                    RxToast.warning("请输入手机号码");

                } else if (ProjectUtil.isMobileNO(tempMobile)) {

                    updatePhoneThreeHintPhoneText.setText(tempMobile);
                    final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000, updatePhoneThreeGetcode);

                    //处理获取手机验证码网络请求
                    mApi.sendVerifySms(GlobalParams.sToken, tempMobile, "4")
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
    }

    @OnClick({R.id.updatePhone_two_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.updatePhone_two_commit: //下一步
                final String Phone = updatePhoneThreeEditName.getText().toString().trim();
                String cCode = MD5Utils.getPwd(updatePhoneThreeEditCode.getVerification().trim());

                if (Phone.equals("")) {
                    RxToast.warning("请输入手机号码");
                } else if (cCode.equals("")) {
                    RxToast.warning("请输入验证码");
                } else if (!cCode.equals(Code)) {
                    RxToast.error("验证码错误，请重新输入");
                } else {
                    mApi.modifyPhone(GlobalParams.sToken, GlobalParams.sUserId, Phone)
                            .compose(RxHttpResponseCompat.<String>compatResult())
                            .subscribe(new ErrorHandlerSubscriber<String>() {
                                @Override
                                public void onNext(String s) {
                                    RxToast.success("手机号码更换成功");
                                    GlobalParams.setuserPhone(Phone);
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
