package com.lulian.Zaiyunbao.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.service.autofill.Dataset;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lulian.Zaiyunbao.Bean.LeasePriceFromBean;
import com.lulian.Zaiyunbao.Bean.SaleEntity;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static com.alibaba.fastjson.JSON.parseArray;

public class LeaseMyEquipmentActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.lease_my_EquipmentName)
    TextView leaseMyEquipmentName;
    @BindView(R.id.lease_my_Norm)
    TextView leaseMyNorm;
    @BindView(R.id.lease_my_kezu_sum)
    TextView leaseMyKezuSum;
    @BindView(R.id.lease_my_modle_text)
    TextView leaseMyModleText;
    @BindView(R.id.lease_my_modle_spinner)
    TextView leaseMyModleSpinner;
    @BindView(R.id.lease_my_BalanceMode_text)
    TextView leaseMyBalanceModeText;
    @BindView(R.id.lease_my_BalanceMode)
    TextView leaseMyBalanceMode;
    @BindView(R.id.lease_my_startTime)
    TextView leaseMyStartTime;
    @BindView(R.id.lease_my_endTime)
    TextView leaseMyEndTime;
    @BindView(R.id.lease_my_sum)
    ClearEditText leaseMySum;
    @BindView(R.id.lease_my_PValue_Text)
    TextView leaseMyPValueText;
    @BindView(R.id.lease_my_PValue)
    TextView leaseMyPValue;
    @BindView(R.id.lease_my_rentAll_text)
    TextView leaseMyRentAllText;
    @BindView(R.id.lease_my_rentAll)
    TextView leaseMyRentAll;
    @BindView(R.id.lease_my_freeDayAmount_text)
    TextView leaseMyFreeDayAmountText;
    @BindView(R.id.lease_my_freeDayAmount)
    TextView leaseMyFreeDayAmount;
    @BindView(R.id.lease_my_DiscountAmount_text)
    TextView leaseMyDiscountAmountText;
    @BindView(R.id.lease_my_DiscountAmount)
    TextView leaseMyDiscountAmount;
    @BindView(R.id.lease_my_rent_text)
    TextView leaseMyRentText;
    @BindView(R.id.lease_my_rent)
    TextView leaseMyRent;
    @BindView(R.id.lease_my_depositPrice_text)
    TextView leaseMyDepositPriceText;
    @BindView(R.id.lease_my_depositPrice)
    TextView leaseMyDepositPrice;
    @BindView(R.id.lease_my_Deposit_text)
    TextView leaseMyDepositText;
    @BindView(R.id.lease_my_Deposit)
    TextView leaseMyDeposit;
    @BindView(R.id.lease_my_mianzu_text)
    TextView leaseMyMianzuText;
    @BindView(R.id.lease_my_mianzu)
    TextView leaseMyMianzu;
    @BindView(R.id.lease_money_btn)
    Button leaseMoneyBtn;
    @BindView(R.id.lease_my_PriceList)
    RelativeLayout leaseMyPriceList;
    @BindView(R.id.lease_equipment_my_next)
    Button leaseEquipmentMyNext;
    @BindView(R.id.dialog_bg)
    ImageView dialogBg;
    @BindView(R.id.lease_my_line)
    ImageView mLeaseMyLine;
    @BindView(R.id.time_reLayout)
    RelativeLayout mTimeReLayout;
    @BindView(R.id.lease_my_freeDayAmount_view)
    View mLeaseMyFreeDayAmountView;
    @BindView(R.id.lease_my_freeDayAmount_reLayout)
    LinearLayout mLeaseMyFreeDayAmountReLayout;
    @BindView(R.id.lease_my_mianzu_reLayout)
    LinearLayout mLeaseMyMianzuReLayout;
    @BindView(R.id.lease_my_sum_day)
    TextView mLeaseMySumDay;

    private TimePickerView pvTime;
    private int leaseSum = 0;
    private int Datasums = 0;
    private int YaJin = 0;
    private float YongJin = 0f;
    private int MianZuQi = 0;
    private float ZuJin;

    private String EquipmentName;
    private String Norm;
    private float Price;
    private int Quantity;
    private double StaticLoad;
    private double CarryingLoad;
    private double OnLoad;
    private double Volume;
    private double WarmLong;
    private double SpecifiedLoad;
    private String TypeName;
    //    private String Picture;
    private String Id; //设备ID
    private int UserType; //发布人类型a
    private String CreateId; //创建人
    private String CreateUser;
    private String OperatorId;
    private String UID; //使用者ID

    private Handler mHandler;
    private boolean isFenci;

    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //不同年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else  //同年
        {
            if (day2 == day1){
                return 1;
            } else {
                return day2 - day1;
            }
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.lease_my_equipment;
    }

    @SuppressLint("NewApi")
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

        textDetailContent.setText("我要租赁");
        textDetailRight.setVisibility(View.GONE);

        leaseMoneyBtn.setEnabled(false);

        OperatorId = getIntent().getStringExtra("OperatorId");  //供应商Id
        EquipmentName = getIntent().getStringExtra("EquipmentName");
        Norm = getIntent().getStringExtra("Norm");
        Price = getIntent().getFloatExtra("Price", 0);
        Quantity = getIntent().getIntExtra("Quantity", 0);
        StaticLoad = getIntent().getDoubleExtra("StaticLoad", 0);
        CarryingLoad = getIntent().getDoubleExtra("CarryingLoad", 0);
        OnLoad = getIntent().getDoubleExtra("OnLoad", 0);
        Volume = getIntent().getDoubleExtra("Volume", 0);
        WarmLong = getIntent().getDoubleExtra("WarmLong", 0);
        SpecifiedLoad = getIntent().getDoubleExtra("SpecifiedLoad", 0);
        TypeName = getIntent().getStringExtra("TypeName");
//        Picture = getIntent().getStringExtra("Picture");
        Id = getIntent().getStringExtra("Id");
        UserType = getIntent().getIntExtra("UserType", 0);
        CreateId = getIntent().getStringExtra("CreateId");
        CreateUser = getIntent().getStringExtra("CreateUser");
//        YaJin = getIntent().getIntExtra("Deposit", 0);

        UID = getIntent().getStringExtra("UID");

        initView();

    }

    //获取押金
    private void getYajin() {
        int RentWay = 0;
        if (leaseMyModleSpinner.getText().toString().trim().equals("分时租赁")) {
            RentWay = 1;
        } else {
            RentWay = 2;
        }
        mApi.rentPriceList(GlobalParams.sToken, Id, OperatorId, RentWay,
                Integer.valueOf(leaseMySum.getText().toString().trim()), Datasums)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        List<LeasePriceFromBean> list = parseArray(s, LeasePriceFromBean.class);
                        if (list.size() > 0) {
                            DecimalFormat decimalFormat = new DecimalFormat("0.00");

                            float freeDayMoney = list.get(0).getPrice() * Float.valueOf(list.get(0).getFreeDay());
                            MianZuQi = list.get(0).getFreeDays();

                            if (isFenci) {
                                ZuJin = list.get(0).getAllAmount() - list.get(0).getDiscountAmount();
                            } else {
                                ZuJin = list.get(0).getAllAmount() - freeDayMoney - list.get(0).getDiscountAmount();
                            }

                            if (ZuJin <= 0f) {
                                ZuJin = 0;
                            }
                            YongJin = list.get(0).getCommisionValue();
                            YaJin = list.get(0).getDepositAmount();

                            leaseMyPValue.setText(list.get(0).getPrice() + "");  //租金单价
                            leaseMyRent.setText(decimalFormat.format(ZuJin) + "");//租金
                            leaseMyMianzu.setText(MianZuQi + ""); //免租天数
                            leaseMyRentAll.setText(list.get(0).getAllAmount() + "");//总金额
                            leaseMyFreeDayAmount.setText(freeDayMoney + "");//免租金额
                            leaseMyDiscountAmount.setText(list.get(0).getDiscountAmount() + "");//折扣金额
                            leaseMyDeposit.setText(YaJin * Integer.valueOf(leaseMySum.getText().toString().trim()) + ""); //押金
                            leaseMyDepositPrice.setText(YaJin + ""); //押金单价
                        } else {
//                            leaseMyDepositPrice.setText("0");
//                            leaseMyDeposit.setText("0");
                            YaJin = 0;
                            leaseMyPValue.setText("0");
                            leaseMyRent.setText("0");
                            leaseMyMianzu.setText("0");
                            leaseMyRentAll.setText("0");
                            leaseMyFreeDayAmount.setText("0");
                            leaseMyDiscountAmount.setText("0");
                            leaseMyDepositPrice.setText("0");
                            leaseMyDeposit.setText("0");
                        }

//                        leaseMyRent.setText(Integer.valueOf(leaseMySum.getText().toString().trim()) *
//                                Float.valueOf(leaseMyPValue.getText().toString().trim()) * Datasums + ""); //租金

                    }
                });
    }

    @SuppressLint("NewApi")
    private void initView() {


        leaseMyKezuSum.setText(Quantity + "个/片");

        leaseMyModleSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBlur(dialogBg, mHandler);

                String[] list = {"分时租赁", "分次租赁"};
                BaseDialog(LeaseMyEquipmentActivity.this, dialogBg, list,
                        leaseMyModleSpinner.getText().toString(), "租赁模式", mHandler, new OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                leaseMyModleSpinner.setText(data.get(position).getTitle());
                            }
                        });
//                }


            }
        });
        leaseMyModleSpinner.setText("分时租赁");

        leaseMyBalanceMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBlur(dialogBg, mHandler);//处理背景图
                String[] list2 = {"周结", "月结", "季结"};

                BaseDialog(LeaseMyEquipmentActivity.this, dialogBg, list2,
                        leaseMyBalanceMode.getText().toString(), "结算方式", mHandler, new OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                leaseMyBalanceMode.setText(data.get(position).getTitle());
                            }
                        });
            }
        });

        leaseMyEquipmentName.setText(EquipmentName);
        leaseMyNorm.setText(Norm);

        leaseMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFenci) {
                    if (TextUtils.isEmpty(leaseMyStartTime.getText().toString().trim())) {
                        RxToast.warning("请选择起租时间");
                        return;
                    } else if (TextUtils.isEmpty(leaseMyEndTime.getText().toString().trim())) {
                        RxToast.warning("请选择退租时间");
                        return;
                    }

                    if (Datasum() < 90){
                        RxToast.warning("当前选择天数小于90天，请选择分次租赁模式");
                        return;
                    }
                }

                if (TextUtils.isEmpty(leaseMySum.getText().toString().trim())) {
                    RxToast.warning("请输入租赁数量");
                    return;
                } else {
                    if (Integer.valueOf(leaseMySum.getText().toString().trim()) > Quantity) {
                        RxToast.warning("租赁数量不能大于可租数量");
                    } else if(Integer.valueOf(leaseMySum.getText().toString().trim()) < 200){
                        RxToast.warning("租赁数量不能小于200个");
                    } else {

                        if (leaseMyStartTime.getText().toString().trim().equals(leaseMyEndTime.getText().toString().trim())) {
                            Datasums = 1;
                        } else {
                            Datasums = Datasum();
                        }

                        getYajin(); //押金
                    }
                }
            }
        });


        leaseMyStartTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(leaseMyStartTime.getText().toString().trim())
                        && !TextUtils.isEmpty(leaseMyEndTime.getText().toString().trim())
                        && !TextUtils.isEmpty(leaseMySum.getText().toString().trim())) {
                    mLeaseMySumDay.setText(Datasum()+"天");
                    if (Datasum() < 90){
                        RxToast.warning("当前选择天数小于90天，请选择分次租赁模式");
                        leaseMoneyBtn.setEnabled(false);
                    } else {
                        leaseMoneyBtn.setEnabled(true);
                    }

                } else if (!TextUtils.isEmpty(leaseMyStartTime.getText().toString().trim())
                        && !TextUtils.isEmpty(leaseMyEndTime.getText().toString().trim())) {

                    if (Datasum() < 90){
                        RxToast.warning("当前选择天数小于90天，请选择分次租赁模式");
                    }

                    mLeaseMySumDay.setText(Datasum()+"天");
                    leaseMoneyBtn.setEnabled(false);
                } else {
                    leaseMoneyBtn.setEnabled(false);
                }


            }
        });

        leaseMyEndTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(leaseMyStartTime.getText().toString().trim())
                        && !TextUtils.isEmpty(leaseMyEndTime.getText().toString().trim())
                        && !TextUtils.isEmpty(leaseMySum.getText().toString().trim())) {
                    mLeaseMySumDay.setText(Datasum()+"天");
                    if (Datasum() < 90){
                        RxToast.warning("当前选择天数小于90天，请选择分次租赁模式");
                        leaseMoneyBtn.setEnabled(false);
                    } else {
                        leaseMoneyBtn.setEnabled(true);
                    }

                } else if (!TextUtils.isEmpty(leaseMyStartTime.getText().toString().trim())
                        && !TextUtils.isEmpty(leaseMyEndTime.getText().toString().trim())) {

                    if (Datasum() < 90){
                        RxToast.warning("当前选择天数小于90天，请选择分次租赁模式");
                    }
                    mLeaseMySumDay.setText(Datasum()+"天");
                    leaseMoneyBtn.setEnabled(false);
                }  else {
                    leaseMoneyBtn.setEnabled(false);
                }

            }
        });


        leaseMySum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearPriceView();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isFenci) {
                    if (!TextUtils.isEmpty(leaseMyStartTime.getText().toString().trim())
                            && !TextUtils.isEmpty(leaseMyEndTime.getText().toString().trim())
                            && !TextUtils.isEmpty(leaseMySum.getText().toString().trim())) {
                        leaseMoneyBtn.setEnabled(true);
                    } else {
                        leaseMoneyBtn.setEnabled(false);
                    }
                } else {
                    if (!TextUtils.isEmpty(leaseMySum.getText().toString().trim())) {
                        leaseMoneyBtn.setEnabled(true);
                    } else {
                        leaseMoneyBtn.setEnabled(false);
                    }
                }
            }

        });


        getDeposit();
    }

    @OnClick({R.id.lease_my_startTime, R.id.lease_my_endTime, R.id.lease_equipment_my_next, R.id.lease_my_PriceList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lease_my_startTime:
                initTimePicker(R.id.lease_my_startTime);
                break;

            case R.id.lease_my_endTime:
                initTimePicker(R.id.lease_my_endTime);
                break;

            case R.id.lease_my_PriceList:
                startActivity(new Intent(this, LeasePriceFormActivity.class));
                break;

            case R.id.lease_equipment_my_next:
                if (!isFenci) {
                    if (TextUtils.isEmpty(leaseMyStartTime.getText().toString().trim())) {
                        RxToast.warning("请选择起租时间");
                        return;
                    } else if (TextUtils.isEmpty(leaseMyEndTime.getText().toString().trim())) {
                        RxToast.warning("请选择退租时间");
                        return;
                    }

                    if (Datasum() < 90){
                        RxToast.warning("当前选择天数小于90天，请选择分次租赁模式");
                        return;
                    }
                }

                if (TextUtils.isEmpty(leaseMySum.getText().toString().trim())) {
                    RxToast.warning("请输入租赁数量");
                    return;
                } else if (TextUtils.isEmpty(leaseMyRent.getText().toString().trim())) {
                    RxToast.warning("请先计算费用");
                    return;
                }

                String load = "";

                Intent intent = new Intent(this, LeaseMyEquipmentAddressActivity.class);
                intent.putExtra("MyEquipmentName", leaseMyEquipmentName.getText().toString().trim());
//                    intent.putExtra("MyEquipmentImage", Picture);

                if (TypeName.equals("托盘")) {
                    load = "静载" + StaticLoad + "T；动载" + CarryingLoad + "T；架载" + OnLoad + "T";

                } else if (TypeName.equals("保温箱")) {
                    load = "容积" + Volume + "升；保温时长" + WarmLong + "小时";

                } else if (TypeName.equals("周转篱")) {
                    load = "容积" + Volume + "升；载重" + SpecifiedLoad + "公斤";
                }

                intent.putExtra("myLoad", load);
                intent.putExtra("MyNorm", leaseMyNorm.getText().toString().trim());
                intent.putExtra("MyModle", leaseMyModleSpinner.getText().toString().trim());
                if (isFenci) {
                    intent.putExtra("MyStartTime", "");
                    intent.putExtra("MyEndTime", "");
                    intent.putExtra("mainzuDay", 0);
                } else {
                    intent.putExtra("MyStartTime", leaseMyStartTime.getText().toString().trim());
                    intent.putExtra("MyEndTime", leaseMyEndTime.getText().toString().trim());
                    intent.putExtra("mainzuDay", MianZuQi);
                }

                intent.putExtra("Datasums", Datasums);
                intent.putExtra("leaseSum", Integer.valueOf(leaseMySum.getText().toString().trim()));
                intent.putExtra("MyBalanceMode", leaseMyBalanceMode.getText().toString().trim());
                intent.putExtra("MyPValue", leaseMyPValue.getText().toString().trim());
                intent.putExtra("MyRent", leaseMyRent.getText().toString().trim());
                intent.putExtra("MyDepositPrice", leaseMyDepositPrice.getText().toString().trim());
                intent.putExtra("MyDeposit", leaseMyDeposit.getText().toString().trim());
                intent.putExtra("Id", Id);
                intent.putExtra("UserType", UserType);
                intent.putExtra("CreateId", CreateId);
                intent.putExtra("CreateUser", CreateUser);

                intent.putExtra("StorehouseId", getIntent().getStringExtra("StorehouseId")); //仓库ID

                intent.putExtra("OperatorId", OperatorId);//供应商ID
//                    intent.putExtra("UID", getIntent().getStringExtra("UID"));//使用者ID

                intent.putExtra("TypeId", getIntent().getStringExtra("TypeId"));
                intent.putExtra("TypeName", TypeName);

                intent.putExtra("DiscountAmount", leaseMyDiscountAmount.getText().toString().trim()); //折扣金额

                intent.putExtra("UID", UID); //使用者ID
                intent.putExtra("YongJin", YongJin); //佣金


                startActivity(intent);
//                    finish();

                break;
            default:
                break;
        }
    }

    private void initTimePicker(final int Id) {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Date dateNew = new Date(System.currentTimeMillis());

                if (differentDays(dateNew, date) < 0) {
                    RxToast.warning("选择的时间不能小于当前时间");

                } else {
                    if (Id == R.id.lease_my_startTime) {
                        String dateEnd = leaseMyEndTime.getText().toString().trim();

                        if (!TextUtils.isEmpty(dateEnd)) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date endDate = format.parse(dateEnd);

                                if (differentDays(date, endDate) < 0) {
                                    RxToast.warning("起租时间不能大于退租时间");
                                } else {
                                    leaseMyStartTime.setText(getTime(date));
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            leaseMyStartTime.setText(getTime(date));
                        }
                    }

                    if (Id == R.id.lease_my_endTime) {
                        String dateStr = leaseMyStartTime.getText().toString().trim();

                        if (!TextUtils.isEmpty(dateStr)) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date startDate = format.parse(dateStr);

                                if (differentDays(startDate, date) < 0) {
                                    RxToast.warning("退租时间不能小于起租时间");
                                } else {
                                    leaseMyEndTime.setText(getTime(date));
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            leaseMyEndTime.setText(getTime(date));
                        }

                    }
                }

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {

                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }

        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private int Datasum() {
        String dateStr = leaseMyStartTime.getText().toString().trim();
        String dateStr2 = leaseMyEndTime.getText().toString().trim();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date2 = format.parse(dateStr2);
            Date date = format.parse(dateStr);

            return differentDays(date, date2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private void clearPriceView() {
        leaseMyPValue.setText("");
        leaseMyDepositPrice.setText("");
        leaseMyRentAll.setText("");
        leaseMyFreeDayAmount.setText("");
        leaseMyDiscountAmount.setText("");
        leaseMyRent.setText("");
        leaseMyDeposit.setText("");
        leaseMyMianzu.setText("");

    }


    private void getDeposit() {
        RxTextView.textChanges(leaseMyModleSpinner)
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
                if (charSequence.toString().trim().equals("分次租赁")) {
                    isFenci = true;
                    mTimeReLayout.setVisibility(View.GONE);
                    mLeaseMyFreeDayAmountReLayout.setVisibility(View.GONE);
                    mLeaseMyMianzuReLayout.setVisibility(View.GONE);
                    mLeaseMyFreeDayAmountView.setVisibility(View.GONE);
                    mLeaseMySumDay.setText("90");

                } else {
                    isFenci = false;
                    mLeaseMySumDay.setText("");
                    mTimeReLayout.setVisibility(View.VISIBLE);
                    mLeaseMyFreeDayAmountReLayout.setVisibility(View.VISIBLE);
                    mLeaseMyMianzuReLayout.setVisibility(View.VISIBLE);
                    mLeaseMyFreeDayAmountView.setVisibility(View.VISIBLE);
                    leaseMyStartTime.setText("");
                    leaseMyEndTime.setText("");
                }
                clearPriceView();
            }
        });
    }

}
