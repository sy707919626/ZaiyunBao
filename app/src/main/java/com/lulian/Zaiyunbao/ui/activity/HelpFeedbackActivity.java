package com.lulian.Zaiyunbao.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.ProjectUtil;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/10/29.
 */

public class HelpFeedbackActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.help_feeback_text)
    EditText helpFeebackText;
    @BindView(R.id.help_feeback_text_sum)
    TextView helpFeebackTextSum;
    @BindView(R.id.rl_comment_content)
    RelativeLayout rlCommentContent;
    @BindView(R.id.update_feedback_phone_text)
    TextView updateFeedbackPhoneText;
    @BindView(R.id.update_feedback_phone)
    ClearEditText updateFeedbackPhone;
    @BindView(R.id.btn_Submission)
    Button btnSubmission;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_help_feedback;
    }

    @Override
    protected void init() {
        ImmersionBar.with(this)
                .titleBar(R.id.detail_bar_title)
                .titleBarMarginTop(R.id.detail_bar_title)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        textDetailContent.setText("帮助与反馈");
        textDetailRight.setText("联系客服");

        final int maxNum = 140;
        helpFeebackText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                helpFeebackTextSum.setText((maxNum - editable.length()) + "");
            }
        });
    }

    @OnClick({R.id.btn_Submission, R.id.text_detail_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_Submission: //提交

                if (TextUtils.isEmpty(helpFeebackText.getText().toString().trim())) {
                    RxToast.warning("填写您的评价和建议");
                } else if (TextUtils.isEmpty(updateFeedbackPhone.getText().toString().trim())) {
                    RxToast.warning("请留下您的联系方式，方便我们联系您");
                } else {
                    if (!isFastClick()) {
                        mApi.addHelpMsg(GlobalParams.sToken, helpFeebackText.getText().toString().trim(),
                                updateFeedbackPhone.getText().toString().trim(), GlobalParams.sUserId)
                                .compose(RxHttpResponseCompat.<String>compatResult())
                                .subscribe(new ErrorHandlerSubscriber<String>() {
                                    @Override
                                    public void onNext(String s) {
                                        RxToast.success("提交成功");
                                        finish();
                                    }
                                });
                    }
                }

                break;

            case R.id.text_detail_right: //联系客服
                ProjectUtil.checkCallPhone(this, "0731-88395758");
                break;
            default:
                break;
        }
    }
}
