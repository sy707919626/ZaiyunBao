package com.lulian.Zaiyunbao.ui.activity.bank;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/1/21.
 */

public class BankDetailActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.bank_AccountBank_textView)
    TextView bankAccountBankTextView;
    @BindView(R.id.bank_detail_textView)
    TextView bankDetailTextView;
    @BindView(R.id.bank_AccountNo_textView)
    TextView bankAccountNoTextView;
    @BindView(R.id.bank_unbind_commit)
    Button bankUnbindCommit;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_bank_detail;
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

        textDetailContent.setText("银行卡详情");
        textDetailRight.setVisibility(View.GONE);

        bankAccountBankTextView.setText(getIntent().getStringExtra("AccountBank"));
        bankDetailTextView.setText(getIntent().getStringExtra("AccountName"));
        bankAccountNoTextView.setText(getIntent().getStringExtra("BankCardNo"));
    }

    @OnClick({R.id.bank_unbind_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.bank_unbind_commit:
                //完成
                Intent intents = new Intent(this, BankDeleteActivity.class);
                intents.putExtra("BankCardNo", bankAccountNoTextView.getText().toString().trim()); //卡号
                intents.putExtra("AccountBank", bankAccountBankTextView.getText().toString().trim()); //卡名
                intents.putExtra("BankId", getIntent().getStringExtra("BankId"));
                startActivity(intents);

                break;
            default:
                break;
        }
    }

}
