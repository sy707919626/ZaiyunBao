package com.lulian.Zaiyunbao.ui.activity.bank;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lulian.Zaiyunbao.Bean.BankBean;
import com.lulian.Zaiyunbao.Bean.BankInfo;
import com.lulian.Zaiyunbao.Bean.SaleEntity;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2019/1/22.
 */

public class CashBankMoneyActivity extends BaseActivity {

    final List<String> banks = new ArrayList<>(); //银行类型
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.cash_money_balance)
    TextView cashMoneyBalance;
    @BindView(R.id.lease_my_modle_text)
    TextView leaseMyModleText;
    @BindView(R.id.cash_money_bank_select)
    TextView cashMoneyBankSelect;
    @BindView(R.id.cash_money_text)
    TextView cashMoneyText;
    @BindView(R.id.cash_money_sum)
    ClearEditText cashMoneySum;
    @BindView(R.id.cash_money_balance_text)
    TextView cashMoneyBalanceText;
    @BindView(R.id.cash_money_server_text)
    TextView cashMoneyServerText;
    @BindView(R.id.cash_money_btn_commit)
    Button cashMoneyBtnCommit;
    @BindView(R.id.dialog_bg)
    ImageView dialogBg;
    private Handler mHandler;
    private List<BankBean> mBankCardList = new ArrayList<>();
    private List<BankInfo> mBankInfoList = new ArrayList<>();
    private String Id;
    private String AccountNo; //银行卡号
    private String mBalance; //余额
    private String ApplyAccount; // 账户名称

    private float money = 0f;
    final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    @Override
    protected int setLayoutId() {
        return R.layout.activity_cash_money;
    }

    @SuppressLint("NewApi")
    @Override
    protected void init() {
        ImmersionBar.with(this)
                .titleBar(R.id.detail_bar_title)
                .titleBarMarginTop(R.id.detail_bar_title)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        getBankData();
        getData();

        textDetailContent.setText("提现");
        textDetailRight.setVisibility(View.GONE);
        cashMoneyBtnCommit.setEnabled(false);

        dialogBg.setImageAlpha(0);
        dialogBg.setVisibility(View.GONE);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    dialogBg.setVisibility(View.GONE);
                }
            }
        };

        cashMoneySum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(cashMoneySum.getText().toString().trim())
                        &&!TextUtils.isEmpty(cashMoneyBankSelect.getText().toString().trim())) {
                    cashMoneyBtnCommit.setEnabled(true);
                } else {
                    cashMoneyBtnCommit.setEnabled(false);
                }
            }
        });

        cashMoneyBankSelect.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(cashMoneySum.getText().toString().trim())
                        &&!TextUtils.isEmpty(cashMoneyBankSelect.getText().toString().trim())) {
                    cashMoneyBtnCommit.setEnabled(true);
                } else {
                    cashMoneyBtnCommit.setEnabled(false);
                }
            }
        });
        setupSearchView();

        cashMoneyBankSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBlur(dialogBg, mHandler);

                String[] list = banks.toArray(new String[banks.size()]);

                BaseDialog(CashBankMoneyActivity.this, dialogBg, list,
                        cashMoneyBankSelect.getText().toString(), "选择到账银行卡", mHandler, new OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                cashMoneyBankSelect.setText(data.get(position).getTitle());

                                for (BankInfo bankInfo : mBankInfoList) {
                                    if (bankInfo.getBank().equals(data.get(position).getTitle())) {
                                        Id = bankInfo.getId();
                                        AccountNo = bankInfo.getAccountNo();
                                    }
                                }
                            }
                        });
            }
        });

        cashMoneyBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Float.valueOf(mBalance) < Float.valueOf(cashMoneySum.getText().toString().trim()) + money) {
                    RxToast.warning("余额不足");
                } else {
                    String moneys = String.valueOf(Float.valueOf(cashMoneySum.getText().toString().trim()) + Float.valueOf(money));
                    //成功
                    JSONObject obj = new JSONObject();
                    obj.put("Number", AccountNo);
                    obj.put("ApplyAccount", ApplyAccount);
                    obj.put("Balance", moneys);
                    obj.put("UserId", GlobalParams.sUserId);
                    obj.put("Poundage", money);

                    String lease = obj.toString();
                    RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                            lease);

                    if (!isFastClick()) {
                        mApi.AddInFund(GlobalParams.sToken, body)
                                .compose(RxHttpResponseCompat.<String>compatResult())
                                .subscribe(new ErrorHandlerSubscriber<String>() {
                                    @Override
                                    public void onNext(String s) {
                                        RxToast.success("提现申请成功");
                                        finish();
                                    }
                                });
                    }
                }
            }
        });
    }

    // 获取余额
    private void getData() {
        mApi.myMoneyInfo(GlobalParams.sToken, GlobalParams.sUserId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        JSONObject jsonObject = JSONObject.parseObject(s);
                        mBalance = jsonObject.getString("Balance");
                        cashMoneyBalance.setText(jsonObject.getString("Balance") + "");
                        cashMoneyBalanceText.setText(jsonObject.getString("Balance") + "");

                        ApplyAccount = jsonObject.getString("AccountNo");//账户名称
                    }
                });
    }

    public void getBankData() {
        mApi.MyBankBindList(GlobalParams.sToken, GlobalParams.sUserId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        mBankCardList.addAll(JSONObject.parseArray(s, BankBean.class));
                        if (mBankCardList.size() <= 0){
                            RxToast.warning("请先添加银行卡，再进行提现");

                        } else {
                            for (BankBean bankBean : mBankCardList) {

                                //建设银行.龙卡通（1022）
                                String AuccountNo = bankBean.getAccountNo().substring(bankBean.getAccountNo().length() - 4, bankBean.getAccountNo().length());
                                String BankInfo = bankBean.getAccountBank() + " (" + AuccountNo + ") ";

                                BankInfo bankInfo = new BankInfo();
                                bankInfo.setId(bankBean.getId());
                                bankInfo.setAccountNo(bankBean.getAccountNo());
                                bankInfo.setBank(BankInfo);

                                banks.add(BankInfo);
                                mBankInfoList.add(bankInfo);
                            }
                        }
                    }

                });
    }

    private void setupSearchView() {
        RxTextView.textChanges(cashMoneySum)
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(@NonNull CharSequence charSequence) throws Exception {
                        return charSequence.toString().trim().length() > 0;
                    }
                }).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(@NonNull CharSequence charSequence) throws Exception {
                if (charSequence.length() > 0) {
                    cashMoneyServerText.setText(decimalFormat.format(Float.valueOf(cashMoneySum.getText().toString()) * 0.001)+ "");
                    money = Float.valueOf(cashMoneyServerText.getText().toString().trim());
                } else {
                    cashMoneyServerText.setText("0.00");
                }
            }
        });
    }
}
