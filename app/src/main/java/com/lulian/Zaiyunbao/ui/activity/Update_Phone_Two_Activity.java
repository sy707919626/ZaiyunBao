package com.lulian.Zaiyunbao.ui.activity;

import android.content.Intent;
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
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/19.
 */

public class Update_Phone_Two_Activity extends BaseActivity {


    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.updatePhone_two_password_text)
    TextView updatePhoneTwoPasswordText;
    @BindView(R.id.updatePhone_two_password)
    EditText updatePhoneTwoPassword;
    @BindView(R.id.updatePhone_two_password_Password)
    CheckBox updatePhoneTwoPasswordPassword;
    @BindView(R.id.updatePhone_two_next)
    Button updatePhoneTwoNext;
    private boolean isUpdatePhone;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_update_phone_two;
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

        textDetailContent.setText("登录密码验证");
        textDetailRight.setVisibility(View.GONE);
        updatePhoneTwoNext.setEnabled(false);

        isUpdatePhone = getIntent().getBooleanExtra("update_Phone", false);

        updatePhoneTwoPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(updatePhoneTwoPassword.getText().toString().trim())) {
                    updatePhoneTwoNext.setEnabled(true);
                } else {
                    updatePhoneTwoNext.setEnabled(false);
                }
            }
        });

        updatePhoneTwoPasswordPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    updatePhoneTwoPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    updatePhoneTwoPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    @OnClick({R.id.updatePhone_two_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.updatePhone_two_next: //下一步
                String passWord = updatePhoneTwoPassword.getText().toString().trim();

                if (passWord.equals("")) {
                    RxToast.warning("请输入登录密码");
                }

                //检验密码是否正确
                mApi.pwdIsRight(GlobalParams.sToken, GlobalParams.sUserId, passWord)
                        .compose(RxHttpResponseCompat.<String>compatResult())
                        .subscribe(new ErrorHandlerSubscriber<String>() {
                            @Override
                            public void onNext(String s) {
                                if (isUpdatePhone) {
                                    startActivity(new Intent(Update_Phone_Two_Activity.this, Update_Phone_Three_Activity.class));
                                } else {
                                    Intent intent = new Intent(Update_Phone_Two_Activity.this, Setting_Password_Activity.class);
                                    intent.putExtra("forget_phone", GlobalParams.sUserPhone);
                                    startActivity(intent);
                                }
                            }
                        });
                break;
            default:
                break;
        }
    }

}
