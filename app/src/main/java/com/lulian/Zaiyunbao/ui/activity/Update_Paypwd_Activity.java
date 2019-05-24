package com.lulian.Zaiyunbao.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.lulian.Zaiyunbao.common.widget.MyCountDownTimer;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/19.
 */

public class Update_Paypwd_Activity extends BaseActivity {
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.user_name_text)
    TextView userNameText;
    @BindView(R.id.user_name_text2)
    TextView userNameText2;
    @BindView(R.id.update_paypwd_edit_name)
    TextView updatePaypwdEditName;
    @BindView(R.id.update_hint_text)
    TextView updateHintText;
    @BindView(R.id.update_paypwd_hint_phone_text)
    TextView updatePaypwdHintPhoneText;
    @BindView(R.id.update_paypwd_edit_code)
    EditText updatePaypwdEditCode;
    @BindView(R.id.update_paypwd_getcode)
    Button updatePaypwdGetcode;
    @BindView(R.id.update_paypwd_commit)
    Button updatePaypwdCommit;
    private String Code;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_update_paypwd;
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

        textDetailContent.setText("手机验证");
        textDetailRight.setVisibility(View.GONE);
        updatePaypwdEditName.setText(GlobalParams.sUserPhone);

        updatePaypwdGetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempMobile = updatePaypwdEditName.getText().toString().trim();
                updatePaypwdHintPhoneText.setText(tempMobile);

                final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000, updatePaypwdGetcode);

                //处理获取手机验证码网络请求
                mApi.sendVerifySms(GlobalParams.sToken, tempMobile, "3")
                        .compose(RxHttpResponseCompat.<String>compatResult())

                        .subscribe(new ErrorHandlerSubscriber<String>() {
                            @Override
                            public void onNext(String s) {
                                JSONObject jsonObject = JSONObject.parseObject(s);
                                Code = jsonObject.getString("VerifyCode");
                                myCountDownTimer.start();
                            }
                        });
            }
        });
    }

    @OnClick({R.id.update_paypwd_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.update_paypwd_commit: //下一步
                final String Phone = updatePaypwdEditName.getText().toString().trim();
                String cCode = MD5Utils.getPwd(updatePaypwdEditCode.getText().toString().trim());

                if (TextUtils.isEmpty(Phone)) {
                    RxToast.warning("请输入手机号码");
                } else if (TextUtils.isEmpty(cCode)) {
                    RxToast.warning("请输入验证码");
                } else if (!cCode.equals(Code)) {
                    RxToast.error("验证码错误，请重新输入");
                } else {
                    startActivity(new Intent(this, ResetPaypwdActivity.class));
                    RxToast.success("手机验证成功");
                }
                break;

            default:
                break;
        }
    }
}
