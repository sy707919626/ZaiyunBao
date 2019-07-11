package com.lulian.Zaiyunbao.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lulian.Zaiyunbao.Bean.EquipmentListForTypeBean;
import com.lulian.Zaiyunbao.Bean.LeasePriceFromBean;
import com.lulian.Zaiyunbao.Bean.SaleEntity;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.ProjectUtil;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.lulian.Zaiyunbao.ui.base.BaseFragment;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.lulian.Zaiyunbao.ui.base.BaseActivity.isFastClick;

/**
 * Created by Administrator on 2018/7/29.
 */

public class ServiceFragment extends BaseFragment {

    @BindView(R.id.tv_service_address)
    TextView tvServiceAddress;
    @BindView(R.id.text_service_content)
    TextView textServiceContent;
    @BindView(R.id.iv_service_ditu)
    TextView ivServiceDitu;
    @BindView(R.id.title_searvice_toolbar)
    Toolbar titleSearviceToolbar;
    @BindView(R.id.comm_title_service_layout)
    LinearLayout commTitleServiceLayout;
    @BindView(R.id.seekRent_quhuo_time)
    TextView seekRentQuhuoTime;
    @BindView(R.id.seekRent_startTime)
    TextView seekRentStartTime;
    @BindView(R.id.seekRent_line)
    ImageView seekRentLine;
    @BindView(R.id.seekRent_endTime)
    TextView seekRentEndTime;
    @BindView(R.id.seekRent_modle_text)
    TextView seekRentModleText;
    @BindView(R.id.seekRent_modle_spinner)
    TextView seekRentModleSpinner;
    @BindView(R.id.seekRent_shebei_name_text)
    TextView seekRentShebeiNameText;
    @BindView(R.id.seekRent_shebei_name)
    TextView seekRentShebeiName;
    @BindView(R.id.seekRent_jiesuan_model_text)
    TextView seekRentJiesuanModelText;
    @BindView(R.id.seekRent_jiesuan_model)
    TextView seekRentJiesuanModel;
    @BindView(R.id.seekRent_shebei_sum)
    ClearEditText seekRentShebeiSum;
    @BindView(R.id.seekRent_lease_count)
    ClearEditText seekRentLeaseCount;
    @BindView(R.id.seekRent_lease_layout)
    LinearLayout seekRentLeaseLayout;
    @BindView(R.id.seekRent_depositPrice_text)
    TextView seekRentDepositPriceText;
    @BindView(R.id.seekRent_depositPrice)
    ClearEditText seekRentDepositPrice;
    @BindView(R.id.seekRent_depositPrice_text2)
    TextView seekRentDepositPriceText2;
    @BindView(R.id.seekRent_Deposit_text)
    TextView seekRentDepositText;
    @BindView(R.id.seekRent_Deposit)
    TextView seekRentDeposit;
    @BindView(R.id.seekRent_money_btn)
    Button seekRentMoneyBtn;
    @BindView(R.id.seekRent_quhuo_model_text)
    TextView seekRentQuhuoModelText;
    @BindView(R.id.seekRent_quhuo_model)
    TextView seekRentQuhuoModel;
    @BindView(R.id.seekRent_lianxiren)
    ClearEditText seekRentLianxiren;
    @BindView(R.id.seekRent_lianxi_phone)
    ClearEditText seekRentLianxiPhone;
    @BindView(R.id.seekRent_address_quxian_text)
    TextView seekRentAddressQuxianText;
    @BindView(R.id.seekRent_address_quxian)
    TextView seekRentAddressQuxian;
    @BindView(R.id.seekRent_address_xiangxi)
    ClearEditText seekRentAddressXiangxi;
    @BindView(R.id.seekRent_songhuo_layout)
    LinearLayout seekRentSonghuoLayout;
    @BindView(R.id.seekRent_remark)
    ClearEditText seekRentRemark;
    @BindView(R.id.seekRent_Sure)
    Button seekRentSure;
    @BindView(R.id.service_dialog_bg)
    ImageView serviceDialogBg;
    Unbinder unbinder;
    @BindView(R.id.seekRent_time_layout)
    RelativeLayout mSeekRentTimeLayout;
    Unbinder unbinder1;
    private int Datasums;//租赁天数
    private int leaseModle;//租赁模式
    private boolean isListNull = false;
    private String EtypeId = "";
    private int mDeposit;
    private float YongJin;
    private TimePickerView pvTime;
    private boolean isFenci;
    private List<EquipmentListForTypeBean> EquipmentList = new ArrayList<>();
    //设备名称
    private List<String> ShebeiList = new ArrayList<>();

    private Handler mHandler;

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
            return day2 - day1;

        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_service;
    }

    @SuppressLint("NewApi")
    @Override
    protected void init() {
        ImmersionBar.with(getActivity())
                .titleBar(R.id.title_searvice_toolbar)
                .navigationBarColor(R.color.toolbarBG)
                .init();

        ivServiceDitu.setVisibility(View.GONE);
        tvServiceAddress.setVisibility(View.GONE);
        textServiceContent.setText("发布求租");
        getDeposit();
        seekRentMoneyBtn.setEnabled(false);

        serviceDialogBg.setImageAlpha(0);
        serviceDialogBg.setVisibility(View.GONE);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    serviceDialogBg.setVisibility(View.GONE);
                }
            }
        };


        getShebeiName();

        seekRentModleSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBlur(serviceDialogBg, mHandler);

                String[] list = {"分时租赁", "分次租赁"};
//                String[] list = {"分时租赁"};
                BaseDialog(getContext(), serviceDialogBg, list,
                        seekRentModleSpinner.getText().toString(), "租赁方式", mHandler, new BaseActivity.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                seekRentModleSpinner.setText(data.get(position).getTitle());
                                showView();
                            }
                        });
            }
        });

        seekRentShebeiName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showView();
                handleBlur(serviceDialogBg, mHandler);

                String[] list = ShebeiList.toArray(new String[ShebeiList.size()]);

                BaseDialog(getContext(), serviceDialogBg, list,
                        seekRentShebeiName.getText().toString(), "设备类型", mHandler, new BaseActivity.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                seekRentShebeiName.setText(data.get(position).getTitle());
                            }
                        });
            }
        });

        seekRentQuhuoModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBlur(serviceDialogBg, mHandler);
                String[] list = {"送货上门", "用户自提"};
                BaseDialog(getContext(), serviceDialogBg, list,
                        seekRentQuhuoModel.getText().toString(), "取货方式", mHandler, new BaseActivity.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                seekRentQuhuoModel.setText(data.get(position).getTitle());

                                if (data.get(position).getTitle().equals("送货上门")) {
                                    seekRentSonghuoLayout.setVisibility(View.VISIBLE);
                                    seekRentAddressQuxian.setText("");
                                    seekRentAddressXiangxi.setText("");
                                } else if (data.get(position).getTitle().equals("用户自提")) {
                                    seekRentSonghuoLayout.setVisibility(View.GONE);
                                    seekRentAddressQuxian.setText("用户自提");
                                    seekRentAddressXiangxi.setText("用户自提");
                                }

                                seekRentLianxiren.setText(GlobalParams.sUserName);
                                seekRentLianxiPhone.setText(GlobalParams.sUserPhone);
                            }
                        });
            }
        });


        seekRentJiesuanModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBlur(serviceDialogBg, mHandler);

                String[] list = {"周结", "月结", "季结"};
                BaseDialog(getContext(), serviceDialogBg, list,
                        seekRentJiesuanModel.getText().toString(), "租赁结算方式", mHandler, new BaseActivity.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                seekRentJiesuanModel.setText(data.get(position).getTitle());
                            }
                        });
            }
        });

        seekRentModleSpinner.setText("分时租赁");
        seekRentEndTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(seekRentEndTime.getText().toString().trim())
                        && !TextUtils.isEmpty(seekRentStartTime.getText().toString().trim())
                        && !TextUtils.isEmpty(seekRentShebeiSum.getText().toString().trim())) {
                    seekRentMoneyBtn.setEnabled(true);
                } else {
                    seekRentMoneyBtn.setEnabled(false);
                }
            }
        });

        seekRentStartTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(seekRentStartTime.getText().toString().trim())
                        && !TextUtils.isEmpty(seekRentEndTime.getText().toString().trim())
                        && !TextUtils.isEmpty(seekRentShebeiSum.getText().toString().trim())) {
                    seekRentMoneyBtn.setEnabled(true);
                } else {
                    seekRentMoneyBtn.setEnabled(false);
                }
            }
        });

        seekRentShebeiSum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showView();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isFenci) {
                    if (!TextUtils.isEmpty(seekRentStartTime.getText().toString().trim())
                            && !TextUtils.isEmpty(seekRentEndTime.getText().toString().trim())
                            && !TextUtils.isEmpty(seekRentShebeiSum.getText().toString().trim())) {
                        seekRentMoneyBtn.setEnabled(true);
                    } else {
                        seekRentMoneyBtn.setEnabled(false);
                    }
                } else {
                    if (!TextUtils.isEmpty(seekRentShebeiSum.getText().toString().trim())) {
                        seekRentMoneyBtn.setEnabled(true);
                    } else {
                        seekRentMoneyBtn.setEnabled(false);
                    }
                }
            }
        });

    }

    private void getShebeiName() {
        mApi.EquipmentListForType(GlobalParams.sToken, "")
                .compose(RxHttpResponseCompat.<String>compatResult())
                .compose(this.<String>bindUntilEvent(FragmentEvent.DESTROY))
                .compose(this.<String>bindUntilEvent(FragmentEvent.STOP))
                .compose(this.<String>bindUntilEvent(FragmentEvent.PAUSE))
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        EquipmentList = JSONObject.parseArray(s, EquipmentListForTypeBean.class);
                        for (int i = 0; i < EquipmentList.size(); i++) {
                            ShebeiList.add(EquipmentList.get(i).getEquipmentName());
                        }
                    }
                });
    }

    //初始化费用控件
    private void showView() {
        seekRentDepositPrice.setText("");
        seekRentDeposit.setText("");
    }

    @OnClick({R.id.seekRent_quhuo_time, R.id.seekRent_startTime, R.id.seekRent_endTime,
            R.id.seekRent_Sure, R.id.seekRent_address_quxian, R.id.seekRent_money_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.seekRent_quhuo_time:
                initTimePicker(R.id.seekRent_quhuo_time);
                break;

            case R.id.seekRent_startTime:
                initTimePicker(R.id.seekRent_startTime);
                break;

            case R.id.seekRent_endTime:
                initTimePicker(R.id.seekRent_endTime);
                break;

            case R.id.seekRent_Sure:
                uploadNext();
                break;

            case R.id.seekRent_address_quxian:
                changeAddress("");
                break;

            case R.id.seekRent_money_btn:
                //计算费用
                if (!isFenci) {
                    if (TextUtils.isEmpty(seekRentStartTime.getText().toString().trim())) {
                        RxToast.warning("请选择起租时间");
                        return;
                    } else if (TextUtils.isEmpty(seekRentEndTime.getText().toString().trim())) {
                        RxToast.warning("请选择退租时间");
                        return;
                    }
                }

                if (TextUtils.isEmpty(seekRentShebeiName.getText().toString().trim())) {
                    RxToast.warning("请选择设备类型");
                    return;
                } else if (TextUtils.isEmpty(seekRentShebeiSum.getText().toString().trim())) {
                    RxToast.warning("请输入求租数量");
                    return;
                }

                if (isFenci){
                    Datasums = 1;
                } else {
                    if (seekRentStartTime.getText().toString().trim().equals(seekRentEndTime.getText().toString().trim())) {
                        Datasums = 1;
                    } else {
                        Datasums = Datasum();
                    }
                }

                if (seekRentModleSpinner.getText().toString().trim().equals("分时租赁")) {
                    leaseModle = 1;
                } else {
                    leaseModle = 2;
                }

                for (int i = 0; i < EquipmentList.size(); i++) {
                    if (EquipmentList.get(i).getEquipmentName().equals(seekRentShebeiName.getText().toString().trim())) {
                        EtypeId = EquipmentList.get(i).getId();

                    }
                }

                mApi.rentPriceList(GlobalParams.sToken, EtypeId, "", leaseModle,
                        Integer.valueOf(seekRentShebeiSum.getText().toString().trim()), Datasums)
                        .compose(RxHttpResponseCompat.<String>compatResult())
                        .compose(this.<String>bindUntilEvent(FragmentEvent.DESTROY))
                        .compose(this.<String>bindUntilEvent(FragmentEvent.STOP))
                        .compose(this.<String>bindUntilEvent(FragmentEvent.PAUSE))
                        .subscribe(new ErrorHandlerSubscriber<String>() {
                            @Override
                            public void onNext(String s) {
                                List<LeasePriceFromBean> list = JSONObject.parseArray(s, LeasePriceFromBean.class);
                                if (list.size() > 0) {
                                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                                    YongJin = list.get(0).getCommisionValue();
                                    seekRentDepositPrice.setEnabled(false);
                                    mDeposit = list.get(0).getDepositAmount();
                                    seekRentDepositPrice.setText(mDeposit + "");
                                    seekRentDeposit.setText(mDeposit * Integer.valueOf(seekRentShebeiSum.getText().toString().trim()) + "");
                                } else {
                                    seekRentDepositPrice.setEnabled(true);
                                    seekRentDeposit.setText(Float.valueOf(seekRentDepositPrice.getText().toString().trim()) *
                                            Integer.valueOf(seekRentShebeiSum.getText().toString().trim()) + "");
                                    YongJin = 0f;
                                }
                            }
                        });

                break;
        }

    }

    private void uploadNext() {
        if (!isFenci) {
            if (TextUtils.isEmpty(seekRentStartTime.getText().toString().trim())) {
                RxToast.warning("请选择起租时间");
                return;
            } else if (TextUtils.isEmpty(seekRentEndTime.getText().toString().trim())) {
                RxToast.warning("请选择退租时间");
                return;
            }
        }

        if (TextUtils.isEmpty(seekRentQuhuoTime.getText().toString().trim())) {
            RxToast.warning("请选择取货时间");
            return;
        } else if (TextUtils.isEmpty(seekRentModleSpinner.getText().toString().trim())) {
            RxToast.warning("请选择租赁模式");
            return;
        } else if (TextUtils.isEmpty(seekRentShebeiName.getText().toString().trim())) {
            RxToast.warning("请选择设备类型");
            return;
        } else if (TextUtils.isEmpty(seekRentJiesuanModel.getText().toString().trim())) {
            RxToast.warning("请选择结算模式");
            return;
        } else if (TextUtils.isEmpty(seekRentQuhuoModel.getText().toString().trim())) {
            RxToast.warning("请选择取货方式");
            return;
        } else if (TextUtils.isEmpty(seekRentShebeiSum.getText().toString().trim())) {
            RxToast.warning("请输入求租数量");
            return;
        } else if (TextUtils.isEmpty(seekRentDeposit.getText().toString().trim())) {
            RxToast.warning("请先计算费用后，再进行发布求租订单");
            return;
        } else if (TextUtils.isEmpty(seekRentLianxiPhone.getText().toString().trim())) {
            RxToast.warning("请输入联系电话");
            return;
        } else if (!ProjectUtil.isMobileNO(seekRentLianxiPhone.getText().toString().trim())) {
            RxToast.warning("请输入正确的手机号码");
            return;
        } else if (TextUtils.isEmpty(seekRentLianxiren.getText().toString().trim())) {
            RxToast.warning("请输入联系人");
            return;
        } else if (TextUtils.isEmpty(seekRentAddressQuxian.getText().toString().trim())) {
            RxToast.warning("请选择区县");
            return;
        } else if (TextUtils.isEmpty(seekRentAddressXiangxi.getText().toString().trim())) {
            RxToast.warning("请填写详细的地址");
            return;
        }


        for (int i = 0; i < EquipmentList.size(); i++) {
            if (EquipmentList.get(i).getEquipmentName().equals(seekRentShebeiName.getText().toString().trim())) {
                EtypeId = EquipmentList.get(i).getId();
            }
        }

        JSONObject obj = new JSONObject();
        obj.put("ETypeId", EtypeId);
        obj.put("ETypeName", seekRentShebeiName.getText().toString().trim()); //设备名称
        obj.put("FormType", 1); //1,求租,2租赁,3转租

        if (!isFenci) {
            obj.put("TargetDeliveryTime", seekRentStartTime.getText().toString().trim()); //租赁开始时间
            obj.put("ReturnBakcTime", seekRentEndTime.getText().toString().trim()); //退租日期(归还时间)
            obj.put("ZulinModel", 1);
        } else {
            obj.put("ZulinModel", 2);
        }

        obj.put("ArrivalTime", "");
        obj.put("Count", Integer.valueOf(seekRentShebeiSum.getText().toString().trim()));
        obj.put("CreateUserType", Integer.valueOf(GlobalParams.sUserType)); //下单用户类型 1=加盟商 2=客户 3=经纪人
        obj.put("CreateId", GlobalParams.sUserId); //创建用户ID

        if (seekRentJiesuanModel.getText().toString().trim().equals("周结")) {
            obj.put("HandRentWay", 1);
        } else if (seekRentJiesuanModel.getText().toString().trim().equals("月结")) {
            obj.put("HandRentWay", 2);
        } else if (seekRentJiesuanModel.getText().toString().trim().equals("季结")) {
            obj.put("HandRentWay", 3);
        }

        if (seekRentQuhuoModel.getText().toString().trim().equals("送货上门")) {
            obj.put("TransferWay", 1);
            obj.put("ReceiveWay", 1);
        } else if (seekRentQuhuoModel.getText().toString().trim().equals("用户自提")) {
            obj.put("TransferWay", 2);
            obj.put("ReceiveWay", 2);
        }

//        if (seekRentModleSpinner.getText().toString().trim().equals("分时租赁")) {
//            obj.put("ZulinModel", 1);
//        } else if (seekRentQuhuoModel.getText().toString().trim().equals("分次租赁")) {
//            obj.put("ZulinModel", 2);
//        }
//

        obj.put("Status", 0);
        obj.put("Remark", seekRentRemark.getText().toString().trim());

        if (seekRentQuhuoModel.getText().toString().trim().equals("送货上门")) {
            obj.put("TakeAddress", seekRentAddressQuxian.getText().toString().trim() +
                    seekRentAddressXiangxi.getText().toString().trim());  //收货地点
        } else {
            obj.put("TakeAddress", "");  //收货地点
        }

        obj.put("ContactName", seekRentLianxiren.getText().toString().trim()); //租入方名字
        obj.put("ContactPhone", seekRentLianxiPhone.getText().toString().trim());//租入方电话

        obj.put("Recommend", ""); //推荐人
        obj.put("RecommendPhone", ""); //推荐人电话

        obj.put("Release", seekRentLianxiren.getText().toString().trim()); //发布人
        obj.put("ReleasePhone", seekRentLianxiPhone.getText().toString().trim()); //发布人电话
        obj.put("StoreName", ""); //仓库名
        obj.put("StoreId", ""); //仓库Id
        obj.put("ReceiveUserId", ""); //供应商ID
        obj.put("Deposit", Integer.valueOf(seekRentDeposit.getText().toString().trim())); //押金
        obj.put("Commision", YongJin); //佣金
        String lease = obj.toString();
        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                lease);
        if (!isFastClick()) {
            mApi.equipmentRentSubmit(GlobalParams.sToken, body)
                    .compose(RxHttpResponseCompat.<String>compatResult())
                    .compose(this.<String>bindUntilEvent(FragmentEvent.DESTROY))
                    .compose(this.<String>bindUntilEvent(FragmentEvent.STOP))
                    .compose(this.<String>bindUntilEvent(FragmentEvent.PAUSE))
                    .subscribe(new ErrorHandlerSubscriber<String>() {
                        @Override
                        public void onNext(String s) {
                            RxToast.success("求租订单发布成功");

                            seekRentQuhuoTime.setText("");
                            seekRentStartTime.setText("");
                            seekRentEndTime.setText("");
                            seekRentAddressQuxian.setText("");
                            seekRentAddressXiangxi.setText("");
                            seekRentShebeiSum.setText("");
                            seekRentLianxiPhone.setText("");
                            seekRentLianxiren.setText("");
                            seekRentRemark.setText("");
                            seekRentDepositPrice.setText("");
                            seekRentDeposit.setText("");
                            seekRentShebeiName.setText("");

                        }
                    });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS_LOCATION && resultCode == 11) { //选择地点返回值
            if (data != null) {
                seekRentAddressQuxian.setText(data.getStringExtra("addressAll"));
            }
        }
    }

    private void initTimePicker(final int Id) {//Dialog 模式下，在底部弹出

        pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Date dateNew = new Date(System.currentTimeMillis());

                if (differentDays(dateNew, date) < 0) {
                    RxToast.warning("选择的时间不能小于当前时间");
                } else {
                    if (Id == R.id.seekRent_quhuo_time) {
                        seekRentQuhuoTime.setText(getTime(date));
                    }

                    if (Id == R.id.seekRent_startTime) {
                        String dateEnd = seekRentEndTime.getText().toString().trim();

                        if (!TextUtils.isEmpty(dateEnd)) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date endDate = format.parse(dateEnd);

                                if (differentDays(date, endDate) < 0) {
                                    RxToast.warning("起租时间不能大于退租时间");
                                } else {
                                    seekRentStartTime.setText(getTime(date));
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        } else {
                            seekRentStartTime.setText(getTime(date));
                        }
                    }

                    if (Id == R.id.seekRent_endTime) {
                        String dateStr = seekRentStartTime.getText().toString().trim();

                        if (!TextUtils.isEmpty(dateStr)) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date startDate = format.parse(dateStr);

                                if (differentDays(startDate, date) < 0) {
                                    RxToast.warning("退租时间不能小于起租时间");
                                } else {
                                    seekRentEndTime.setText(getTime(date));
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        } else {
                            seekRentEndTime.setText(getTime(date));
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
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
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
        String dateStr = seekRentStartTime.getText().toString().trim();
        String dateStr2 = seekRentEndTime.getText().toString().trim();
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

    private void getDeposit() {
        RxTextView.textChanges(seekRentModleSpinner)
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
                    mSeekRentTimeLayout.setVisibility(View.GONE);
                    seekRentDepositPrice.setText("");
                    seekRentDeposit.setText("");
                } else {
                    isFenci = false;
                    mSeekRentTimeLayout.setVisibility(View.VISIBLE);
                    seekRentDepositPrice.setText("");
                    seekRentDeposit.setText("");
                }

            }
        });
    }
}
