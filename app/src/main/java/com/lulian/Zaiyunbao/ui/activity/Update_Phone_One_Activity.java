package com.lulian.Zaiyunbao.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class Update_Phone_One_Activity extends BaseActivity {


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
    @BindView(R.id.updae_new_phone)
    TextView updaeNewPhone;
    @BindView(R.id.update_hint_text)
    TextView updateHintText;
    @BindView(R.id.update_hint_phone_text)
    TextView updateHintPhoneText;
    @BindView(R.id.verification_Code_update)
    EditText verificationCodeUpdate;
    @BindView(R.id.update_getCode)
    Button updateGetCode;
    @BindView(R.id.update_commit)
    Button updateCommit;
    @BindView(R.id.update_login_password)
    LinearLayout updateLoginPassword;
    private String Code = "";

    @Override
    protected int setLayoutId() {
        return R.layout.activity_update_phone_one;
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

        textDetailContent.setText("更换手机号码");
        textDetailRight.setVisibility(View.GONE);

        updaeNewPhone.setText(GlobalParams.sUserPhone);
        updateGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateHintPhoneText.setText(GlobalParams.sUserPhone);

                //处理获取手机验证码网络请求
                mApi.sendVerifySms(GlobalParams.sToken, GlobalParams.sUserPhone, "4")
                        .compose(RxHttpResponseCompat.<String>compatResult())
                        .subscribe(new ErrorHandlerSubscriber<String>() {
                            @Override
                            public void onNext(String s) {
                                final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000, updateGetCode);
                                myCountDownTimer.start();

                                JSONObject jsonObject = JSONObject.parseObject(s);
                                Code = jsonObject.getString("VerifyCode");
                            }

                        });
            }
        });

    }

    @OnClick({R.id.update_commit, R.id.update_login_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.update_login_password:
                //点击这里
                Intent intent = new Intent(Update_Phone_One_Activity.this, Update_Phone_Two_Activity.class);
                intent.putExtra("update_Phone", true);
                startActivity(intent);

                break;

            case R.id.update_commit: //下一步
                String cCode = MD5Utils.getPwd(verificationCodeUpdate.getText().toString().trim());
                if (cCode.equals("")) {
                    RxToast.warning("请输入验证码");
                } else if (!cCode.equals(Code)) {
                    RxToast.error("验证码错误，请重新输入");
                } else {
                    startActivity(new Intent(Update_Phone_One_Activity.this, Update_Phone_Three_Activity.class));
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
