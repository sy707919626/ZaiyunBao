package com.lulian.Zaiyunbao.ui.activity.bank;

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

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.widget.BankCode;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/1/21.
 */

public class BankAddActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.bank_add_userName)
    TextView bankAddUserName;
    @BindView(R.id.bank_add_cardCode)
    ClearEditText bankAddCardCode;
    @BindView(R.id.bank_add_next_btn)
    Button bankAddNextBtn;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_bank_add;
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

        textDetailContent.setText("绑定银行卡");
        textDetailRight.setVisibility(View.GONE);

        bankAddNextBtn.setEnabled(false);
        bankAddUserName.setText(GlobalParams.sUserName);

        bankAddCardCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(bankAddCardCode.getText().toString().trim())) {
                    bankAddNextBtn.setEnabled(true);
                } else {
                    bankAddNextBtn.setEnabled(false);
                }
            }
        });
    }

    @OnClick({R.id.bank_add_next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bank_add_next_btn:
                //下一步
                if (bankAddCardCode.getText().toString().equals("")) {
                    RxToast.warning("请填写银行卡号");
                } else if (!BankCode.checkBankCard(bankAddCardCode.getText().toString())) {
                    RxToast.warning("请填写正确的银行卡号");
                } else {
                    Intent intent = new Intent(this, BankAddTestActivity.class);
                    intent.putExtra("BankCardNo", bankAddCardCode.getText().toString().trim());
                    intent.putExtra("AccountName", bankAddUserName.getText().toString().trim());
                    startActivity(intent);
                }
                break;

            default:
                break;
        }
    }

}
