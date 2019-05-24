package com.lulian.Zaiyunbao.ui.activity;

import android.app.AlertDialog;
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

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.IDCard;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/19.
 */

public class Register_Tthree_Activity extends BaseActivity {

    @BindView(R.id.image_back_login_bar)
    ImageView imageBackLoginBar;
    @BindView(R.id.text_login_content)
    TextView textLoginContent;
    @BindView(R.id.agree_login)
    TextView agreeLogin;
    @BindView(R.id.register_three_edit_pwd)
    EditText registerThreeEditPwd;
    @BindView(R.id.play_Password)
    CheckBox playPassword;
    @BindView(R.id.register_three_edit_pwd2)
    EditText registerThreeEditPwd2;
    @BindView(R.id.play_Password2)
    CheckBox playPassword2;
    @BindView(R.id.register_three_complete)
    Button registerThreeComplete;
    @BindView(R.id.register_three_bottom_text)
    TextView registerThreeBottomText;
    @BindView(R.id.register_three_login)
    TextView registerThreeLogin;
    @BindView(R.id.login_bar_title)
    RelativeLayout loginBarTitle;

    private String tempMobile = "";
    private String Invitation_Code = "";

    @Override
    protected int setLayoutId() {
        return R.layout.activity_register_three;
    }

    @Override
    protected void init() {
        stepActivities.add(this);
        ImmersionBar.with(this)
                .titleBar(R.id.login_bar_title)
                .titleBarMarginTop(R.id.login_bar_title)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        tempMobile = getIntent().getStringExtra("register_phone");
        Invitation_Code = getIntent().getStringExtra("invitation_Code"); //邀请码

        textLoginContent.setText("设置密码");
        registerThreeComplete.setEnabled(false);

        playPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    registerThreeEditPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    registerThreeEditPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        playPassword2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    registerThreeEditPwd2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    registerThreeEditPwd2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        registerThreeEditPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(registerThreeEditPwd.getText().toString().trim())
                        && !TextUtils.isEmpty(registerThreeEditPwd2.getText().toString().trim())) {
                    registerThreeComplete.setEnabled(true);
                    registerThreeEditPwd.setTextSize(18);
                    registerThreeEditPwd2.setTextSize(18);
                } else {
                    registerThreeComplete.setEnabled(false);
                    registerThreeEditPwd.setTextSize(16);
                    registerThreeEditPwd2.setTextSize(16);
                }
            }
        });

        registerThreeEditPwd2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(registerThreeEditPwd.getText().toString().trim())
                        && !TextUtils.isEmpty(registerThreeEditPwd2.getText().toString().trim())) {
                    registerThreeComplete.setEnabled(true);
                    registerThreeEditPwd.setTextSize(18);
                    registerThreeEditPwd2.setTextSize(18);
                } else {
                    registerThreeComplete.setEnabled(false);
                    registerThreeEditPwd.setTextSize(16);
                    registerThreeEditPwd2.setTextSize(16);
                }
            }
        });
    }

    @OnClick({R.id.register_three_complete, R.id.register_three_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_three_complete:
                String pwd = registerThreeEditPwd.getText().toString().trim();
                String pwdAgain = registerThreeEditPwd2.getText().toString().trim();

                if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdAgain)) {
                    RxToast.warning("密码不能为空!");
                } else if (pwd.length() < 8 || pwd.length() > 16) {
                    RxToast.warning("请输入8到16位的密码!");
                } else if (IDCard.isNumeric(pwd) || IDCard.isLetter(pwd)) {
                    RxToast.warning("密码不能为纯数字或纯字母!");
                } else if (pwd.equals(pwdAgain)) {

                    mApi.register(GlobalParams.sToken, tempMobile, pwd, "2")
                            .compose(RxHttpResponseCompat.<String>compatResult())
                            .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                            .compose(this.<String>bindUntilEvent(ActivityEvent.STOP))
                            .compose(this.<String>bindUntilEvent(ActivityEvent.PAUSE))
                            .subscribe(new ErrorHandlerSubscriber<String>() {
                                @Override
                                public void onNext(String s) {
                                    JSONObject jsonObject = JSONObject.parseObject(s);
                                    GlobalParams.setuserId(jsonObject.getString("UserId"));
                                    GlobalParams.setuserName(jsonObject.getString("Phone"));
                                    GlobalParams.setuserPhone(jsonObject.getString("UserName"));

                                    RxToast.success("注册成功");

                                    Intent intent = new Intent(Register_Tthree_Activity.this, UploadDataActivity.class);
                                    intent.putExtra("ActivityType", 1);
                                    startActivity(intent);

                                }
                            });

                } else {
                    RxToast.warning("两次输入的密码不一致!");
                }
                break;


            case R.id.register_three_login: //已有账号登录
                final AlertDialog builder = new AlertDialog.Builder(this)
                        .create();
                builder.show();
                if (builder.getWindow() == null) return;
                builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
                TextView msg = builder.findViewById(R.id.tv_msg);
                Button cancle = builder.findViewById(R.id.btn_cancle);
                Button sure = builder.findViewById(R.id.btn_sure);

                if (msg == null || cancle == null || sure == null) return;

                msg.setText("返回后注册将中断，是否\n" +
                        "确认返回？");

                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Register_Tthree_Activity.this, LoginActivity.class));
                        builder.dismiss();
                    }
                });
                break;
        }
    }
}
