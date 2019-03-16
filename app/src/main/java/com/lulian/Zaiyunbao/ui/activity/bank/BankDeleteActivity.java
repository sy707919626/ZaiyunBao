package com.lulian.Zaiyunbao.ui.activity.bank;

import android.graphics.Color;
import android.os.Bundle;
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
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.MD5Utils;
import com.lulian.Zaiyunbao.common.widget.MyCountDownTimer;
import com.lulian.Zaiyunbao.common.widget.ProjectUtil;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/1/21.
 */

public class BankDeleteActivity extends BaseActivity {


    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.bank_detele_test_name)
    TextView bankDeteleTestName;
    @BindView(R.id.bank_detele_test_cardCode)
    TextView bankDeteleTestCardCode;
    @BindView(R.id.user_name_text)
    TextView userNameText;
    @BindView(R.id.user_name_text2)
    TextView userNameText2;
    @BindView(R.id.bank_detele_test_phone)
    ClearEditText bankDeteleTestPhone;
    @BindView(R.id.update_hint_text)
    TextView updateHintText;
    @BindView(R.id.bank_detele_phone_text)
    TextView bankDetelePhoneText;
    @BindView(R.id.bank_detele_test_Code)
    EditText bankDeteleTestCode;
    @BindView(R.id.bank_detele_test_getCode)
    Button bankDeteleTestGetCode;
    @BindView(R.id.bank_detele_test_commit)
    Button bankDeteleTestCommit;
    private String Code = "";

    private String BankId;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_bank_detele;
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

        textDetailContent.setText("解绑银行卡");
        textDetailRight.setVisibility(View.GONE);

        bankDeteleTestName.setText(getIntent().getStringExtra("AccountBank"));//银行卡名
        bankDeteleTestCardCode.setText(getIntent().getStringExtra("BankCardNo"));//银行卡名
        BankId = getIntent().getStringExtra("BankId");
    }

    @OnClick({R.id.bank_detele_test_getCode, R.id.bank_detele_test_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bank_detele_test_getCode:
                //获取验证码
                if (bankDeteleTestPhone.getText().toString().trim().equals("")) {
                    RxToast.warning("请输入手机号码");
                } else if (ProjectUtil.isMobileNO(bankDeteleTestPhone.getText().toString().trim())) {

                    bankDetelePhoneText.setText(bankDeteleTestPhone.getText().toString().trim());

                    //处理获取手机验证码网络请求
                    mApi.sendVerifySms(GlobalParams.sToken, GlobalParams.sUserPhone, "4")
                            .compose(RxHttpResponseCompat.<String>compatResult())
                            .subscribe(new ErrorHandlerSubscriber<String>() {
                                @Override
                                public void onNext(String s) {
                                    final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,
                                            1000, bankDeteleTestGetCode);
                                    myCountDownTimer.start();

                                    JSONObject jsonObject = JSONObject.parseObject(s);
                                    Code = jsonObject.getString("VerifyCode");
                                }

                            });

                } else {
                    RxToast.warning("请输入正确的手机号码");
                }

                break;

            case R.id.bank_detele_test_commit:
                //完成
//                if (bankDeteleTestCode.getVerification().equals("")) {
//                    RxToast.warning("请输入验证码");
//                } else if (!MD5Utils.getPwd(bankDeteleTestCode.getVerification()).equals(Code)) {
//                    RxToast.error("验证码错误，请重新输入");
                if (bankDeteleTestCode.getText().toString().trim().equals("")) {
                    RxToast.warning("请输入验证码");
                } else if (!MD5Utils.getPwd(bankDeteleTestCode.getText().toString().trim()).equals(Code)) {
                    RxToast.error("验证码错误，请重新输入");
                } else {
                    //成功
                    mApi.UnBankBind(GlobalParams.sToken, BankId)
                            .compose(RxHttpResponseCompat.<String>compatResult())
                            .subscribe(new ErrorHandlerSubscriber<String>() {

                                @Override
                                public void onNext(String s) {
                                    RxToast.warning("解绑成功");
                                    stepfinishAll();
                                }
                            });
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
