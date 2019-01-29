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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.exception.BaseException;
import com.lulian.Zaiyunbao.common.exception.RxErrorHandler;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.rx.subscriber.ProgressDialogHandler;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.ProjectUtil;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.common.widget.SPUtils;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/9/18.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_image_close)
    ImageView loginImageClose;
    @BindView(R.id.login_text_help)
    TextView loginTextHelp;
    @BindView(R.id.login_relativeLayout_top)
    RelativeLayout loginRelativeLayoutTop;
    @BindView(R.id.login_linearLayout_welcome)
    LinearLayout loginLinearLayoutWelcome;
    @BindView(R.id.user_name_text)
    TextView userNameText;
    @BindView(R.id.user_name_text2)
    TextView userNameText2;
    @BindView(R.id.edit_name)
    ClearEditText editName;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.login_play_Password)
    CheckBox loginPlayPassword;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.text_forgetpwd)
    TextView textForgetpwd;
    @BindView(R.id.text_register)
    TextView textRegister;
    @BindView(R.id.login_all)
    LinearLayout loginAll;
    @BindView(R.id.login_bottom_text)
    TextView loginBottomText;
    @BindView(R.id.login_error_text)
    TextView loginErrorText;
    @BindView(R.id.login_bottom_yonghu_text)
    TextView loginBottomYonghuText;
    @BindView(R.id.login_toor)
    RelativeLayout loginToor;

    private ProgressDialogHandler dialogHandler;
    private SPUtils sp;

    @Override
    protected int setLayoutId() {
        return R.layout.login_activity_layout;
    }

    @Override
    protected void init() {
        ImmersionBar.with(this)
                .titleBar(R.id.login_toor)
                .titleBarMarginTop(R.id.login_toor)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        dialogHandler = new ProgressDialogHandler(this);
        sp = SPUtils.getInstance(this);
        initView();
    }

    private void initView() {
        String name = sp.getString("name");
        String pwd = sp.getString("pwd");

        if (name != null && pwd != null) {
            btnSubmit.setEnabled(true);
            editName.setTextSize(18);
            editPwd.setTextSize(18);
        } else {
            btnSubmit.setEnabled(false);
            editName.setTextSize(16);
            editPwd.setTextSize(16);
        }

        editName.setText(name);
        editPwd.setText(pwd);

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!editName.getText().toString().trim().equals(s)) {
                    editPwd.setText("");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(editName.getText().toString().trim())
                        && !TextUtils.isEmpty(editPwd.getText().toString().trim())) {
                    btnSubmit.setEnabled(true);
                    editName.setTextSize(18);
                    editPwd.setTextSize(18);
                } else {
                    btnSubmit.setEnabled(false);
                    editName.setTextSize(18);
                    editPwd.setTextSize(18);
                }
            }
        });

        editPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(editName.getText().toString().trim())
                        && !TextUtils.isEmpty(editPwd.getText().toString().trim())) {
                    btnSubmit.setEnabled(true);
                    editName.setTextSize(18);
                    editPwd.setTextSize(18);
                } else {
                    btnSubmit.setEnabled(false);
                    editName.setTextSize(16);
                    editPwd.setTextSize(16);
                }
            }
        });

        loginPlayPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    editPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    editPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }


    @OnClick({R.id.text_forgetpwd, R.id.text_register, R.id.btn_submit, R.id.login_image_close, R.id.login_text_help,
            R.id.login_bottom_text, R.id.login_bottom_yonghu_text})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_image_close:
                finishAll();
                break;

            case R.id.login_text_help:
                //帮助
                RxToast.warning("点击了？");
                startActivity(new Intent(this, HelpActivity.class));
                break;

            case R.id.login_bottom_yonghu_text:
                //用户协议
                break;

            case R.id.login_bottom_text:
                startActivityForResult(new Intent(this, ProtocolActivity.class), 11);
                break;

            case R.id.text_forgetpwd:
                Intent intent = new Intent(this, Updae_pwd_Activity.class);
                intent.putExtra("isForget", true);
                startActivity(intent);

                break;
            case R.id.text_register:
                startActivity(new Intent(this, Register_One_Activity.class));

//                Intent intent2 = new Intent(this, UploadDataActivity.class);
//                intent2.putExtra("ActivityType", 1);
//                startActivity(intent2);


                break;
            case R.id.btn_submit:
                if (editName.getText().toString().trim().equals("")) {
                    RxToast.warning("手机号码不能为空");
                } else if (editPwd.getText().toString().trim().equals("")) {
                    RxToast.warning("密码不能为空");
                } else if (ProjectUtil.isMobileNO(editName.getText().toString().trim())) {
                    login();
                } else {
                    RxToast.warning("请输入正确的手机号码");
                }
                break;
            default:
                break;
        }
    }

    private void login() {

        dialogHandler.showProgressDialog();

        final String userId = editName.getText().toString();
        final String pwd = editPwd.getText().toString();

        JSONObject obj = new JSONObject();
        obj.put("Phone", userId);
        obj.put("Password", pwd);
        obj.put("UserType", 2);

        String user = obj.toString();
        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                user);

        mApi.login(GlobalParams.sToken, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        RxToast.success("登录成功");

                        JSONObject jsonObject = JSONObject.parseObject(s);
                        GlobalParams.setuserId(jsonObject.getString("UserId"));
                        GlobalParams.setuserName(jsonObject.getString("Name"));
                        GlobalParams.setuserType(jsonObject.getString("UserType"));
                        GlobalParams.setuserPhone(jsonObject.getString("Phone"));
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        loginErrorText.setText("");
                        sp.put("name", userId);
                        sp.put("pwd", pwd);

                        //验证是否有支付密码
                        PayPwdIsSet(jsonObject.getString("UserId"));

                        finish();
                    }

                    @Override
                    public void onError(Throwable t) {
                        RxErrorHandler mRxErrorHandler = new RxErrorHandler();
                        BaseException exception = mRxErrorHandler.handlerError(t);
                        loginErrorText.setText(exception.getDisplayMessage());

                        dialogHandler.dismissProgressDialog();
                    }
                });

    }


    private void PayPwdIsSet(String userId) {
        mApi.PayPwdIsSet(GlobalParams.sToken, userId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        GlobalParams.setIsPayPwd(true);
                    }

                    @Override
                    public void onError(Throwable t) {
                        GlobalParams.setIsPayPwd(false);
                    }
                });
    }
}
