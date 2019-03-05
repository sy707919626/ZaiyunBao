package com.lulian.Zaiyunbao.ui.activity.bank;

import android.graphics.Color;
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
import com.lulian.Zaiyunbao.common.widget.BankCode;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.MD5Utils;
import com.lulian.Zaiyunbao.common.widget.MyCountDownTimer;
import com.lulian.Zaiyunbao.common.widget.ProjectUtil;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.common.widget.VerificationCode;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2019/1/21.
 */

public class BankAddTestActivity extends BaseActivity {
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.bank_add_test_name)
    TextView bankAddTestName;
    @BindView(R.id.bank_add_test_cardCode)
    ClearEditText bankAddTestCardCode;
    @BindView(R.id.user_name_text)
    TextView userNameText;
    @BindView(R.id.user_name_text2)
    TextView userNameText2;
    @BindView(R.id.bank_add_test_phone)
    ClearEditText bankAddTestPhone;
    @BindView(R.id.update_hint_text)
    TextView updateHintText;
    @BindView(R.id.update_hint_phone_text)
    TextView updateHintPhoneText;
    @BindView(R.id.bank_add_test_Code)
    VerificationCode bankAddTestCode;
    @BindView(R.id.bank_add_test_getCode)
    Button bankAddTestGetCode;
    @BindView(R.id.bank_add_test_commit)
    Button bankAddTestCommit;
    private String BankCardNo;
    private String AccountName;
    private String Code = "";

    @Override
    protected int setLayoutId() {
        return R.layout.activity_bank_add_test;
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

        BankCardNo = getIntent().getStringExtra("BankCardNo");
        AccountName = getIntent().getStringExtra("AccountName");

        bankAddTestCardCode.setText(BankCardNo);

        textDetailContent.setText("绑定银行卡");
        textDetailRight.setVisibility(View.GONE);
        bankAddTestName.setText(BankCode.getDetailNameOfBank(BankCardNo).toString());
        bankAddTestPhone.setText(GlobalParams.sUserPhone);
    }

    @OnClick({R.id.bank_add_test_commit, R.id.bank_add_test_getCode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bank_add_test_getCode:
                //获取验证码
                if (bankAddTestPhone.getText().toString().trim().equals("")) {
                    RxToast.warning("请输入手机号码");
                } else if (ProjectUtil.isMobileNO(bankAddTestPhone.getText().toString().trim())) {
                    updateHintPhoneText.setText(bankAddTestPhone.getText().toString().trim());

                    //处理获取手机验证码网络请求
                    mApi.sendVerifySms(GlobalParams.sToken, GlobalParams.sUserPhone, "4")
                            .compose(RxHttpResponseCompat.<String>compatResult())
                            .subscribe(new ErrorHandlerSubscriber<String>() {
                                @Override
                                public void onNext(String s) {
                                    final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,
                                            1000, bankAddTestGetCode);
                                    myCountDownTimer.start();

                                    JSONObject jsonObject = JSONObject.parseObject(s);
                                    Code = jsonObject.getString("VerifyCode");
                                }

                            });

                } else {
                    RxToast.warning("请输入正确的手机号码");
                }

                break;

            case R.id.bank_add_test_commit:
                //完成
                if (bankAddTestCode.getVerification().equals("")) {
                    RxToast.warning("请输入验证码");
                } else if (!MD5Utils.getPwd(bankAddTestCode.getVerification()).equals(Code)) {
                    RxToast.error("验证码错误，请重新输入");

                } else {
                    //成功
                    JSONObject obj = new JSONObject();
                    obj.put("AccountNo", BankCardNo);
                    obj.put("Phone", GlobalParams.sUserPhone);
                    obj.put("AccountName", AccountName);
                    obj.put("AccountBank", bankAddTestName.getText().toString().trim());
                    obj.put("UserId", GlobalParams.sUserId);

                    String lease = obj.toString();
                    RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                            lease);

                    mApi.BankBind(GlobalParams.sToken, body)
                            .compose(RxHttpResponseCompat.<String>compatResult())
                            .subscribe(new ErrorHandlerSubscriber<String>() {
                                @Override
                                public void onNext(String s) {
                                    RxToast.success("银行卡绑定成功");
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
