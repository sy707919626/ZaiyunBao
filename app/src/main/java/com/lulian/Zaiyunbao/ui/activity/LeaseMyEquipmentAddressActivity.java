package com.lulian.Zaiyunbao.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.AreaBean;
import com.lulian.Zaiyunbao.Bean.DicItemBean;
import com.lulian.Zaiyunbao.Bean.PersonalInfoBean;
import com.lulian.Zaiyunbao.Bean.SaleEntity;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/11/1.
 */

public class LeaseMyEquipmentAddressActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.lease_my_modle_text)
    TextView leaseMyModleText;
    @BindView(R.id.lease_my_DeliveryMode)
    TextView leaseMyDeliveryMode;
    @BindView(R.id.lease_my_DeliveryTime_text)
    TextView leaseMyDeliveryTimeText;
    @BindView(R.id.lease_my_DeliveryTime)
    TextView leaseMyDeliveryTime;
    @BindView(R.id.lease_my_address_name_text)
    TextView leaseMyAddressNameText;
    @BindView(R.id.lease_my_address_name)
    ClearEditText leaseMyAddressName;
    @BindView(R.id.lease_my_name_text)
    TextView leaseMyNameText;
    @BindView(R.id.lease_my_name)
    ClearEditText leaseMyName;
    @BindView(R.id.lease_my_phone_text)
    TextView leaseMyPhoneText;
    @BindView(R.id.lease_my_phone)
    ClearEditText leaseMyPhone;
    @BindView(R.id.lease_my_RefereeName_text)
    TextView leaseMyRefereeNameText;
    @BindView(R.id.lease_my_RefereeName)
    ClearEditText leaseMyRefereeName;
    @BindView(R.id.lease_my_phoneNumble_text)
    TextView leaseMyPhoneNumbleText;
    @BindView(R.id.lease_my_phoneNumble)
    ClearEditText leaseMyPhoneNumble;
    @BindView(R.id.lease_equipment_my_btn)
    Button leaseEquipmentMyBtn;
    @BindView(R.id.dialog_bg)
    ImageView dialogBg;
    private TimePickerView pvTime;
    private String MyEquipmentName;
    private String MyNorm;
    private String MyModle;
    private String MyStartTime;
    private String MyEndTime;
    private int MianzuQI;
    private int Datasums;
    private int leaseSum;
    private String MyBalanceMode;
    private String MyPValue;
    private String MyRent;
    private String MyDepositPrice;
    private String MyDeposit;
    private String Load;
    private String MyEquipmentImage;
    private String Id; //设备ID
    private int UserType; //发布人类型
    private String CreateId; //创建人
    private String CreateUser;
    private String StorehouseId; //仓库id

    private String Area = ""; //地区
    private String Name = ""; //站点名
    private String ZZName = ""; //转租地址
    private String ZZContactName = ""; //转租联系人
    private String ZZContactPhone = ""; //转租联系电话
    private String ContactName = ""; //联系人
    private String ContactPhone = ""; //联系电话
    private String OperatorId;
    private String UID;
    private List<AreaBean> areaBean = new ArrayList<>();
    private List<PersonalInfoBean> personalInfoBean = new ArrayList<>();


    private Handler mHandler;

    @Override
    protected int setLayoutId() {
        return R.layout.lease_my_equipment_address;
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
        MyEquipmentName = getIntent().getStringExtra("MyEquipmentName");
        MyNorm = getIntent().getStringExtra("MyNorm");
        MyModle = getIntent().getStringExtra("MyModle");
        MyStartTime = getIntent().getStringExtra("MyStartTime");
        MyEndTime = getIntent().getStringExtra("MyEndTime");
        Datasums = getIntent().getIntExtra("Datasums", 0);
        leaseSum = getIntent().getIntExtra("leaseSum", 0);
        MyBalanceMode = getIntent().getStringExtra("MyBalanceMode");
        MyPValue = getIntent().getStringExtra("MyPValue");
        MyRent = getIntent().getStringExtra("MyRent"); //租金
        MyDepositPrice = getIntent().getStringExtra("MyDepositPrice");
        MyDeposit = getIntent().getStringExtra("MyDeposit"); //押金
        Load = getIntent().getStringExtra("myLoad");
//        MyEquipmentImage = getIntent().getStringExtra("MyEquipmentImage");
        Id = getIntent().getStringExtra("Id");
        UserType = getIntent().getIntExtra("UserType", 0);
        CreateId = getIntent().getStringExtra("CreateId");
        CreateUser = getIntent().getStringExtra("CreateUser");
        MianzuQI = getIntent().getIntExtra("mainzuDay", 0);
        StorehouseId = getIntent().getStringExtra("StorehouseId");
        OperatorId = getIntent().getStringExtra("OperatorId"); //供应商Id
        UID = getIntent().getStringExtra("UID"); //使用者ID

        textDetailContent.setText("我要租赁");
        textDetailRight.setVisibility(View.GONE);
//        initView();
        getData();
    }

    private void getData() {
        //根据仓库Id获取仓库与所属加盟商信息
//        mApi.getStorehouseInfo(GlobalParams.sToken, StorehouseId)
//                .compose(RxHttpResponseCompat.<String>compatResult())
//                .subscribe(new ErrorHandlerSubscriber<String>() {
//                    @Override
//                    public void onNext(String s) {
//                        if (s.equals("")) {
//                            RxToast.warning("当前供应商资料不全，请选择送货上门方式");
//                        } else {
//                            areaBean = MyApplication.get().getAppComponent().getGson().fromJson(s, AreaBean.class);
//
//                            Area = areaBean.getArea();
//                            Name = areaBean.getName();
//                            ContactName = areaBean.getContactName();
//                            ContactPhone = areaBean.getContactPhone();
//                        }
//
//                        initView();
//                    }
//                });
        if (UID.isEmpty()||UID == null) {
            mApi.getStorehouseInfo(GlobalParams.sToken, StorehouseId)
                    .compose(RxHttpResponseCompat.<String>compatResult())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            if (s.equals("[]")) {
//                                RxToast.warning("当前供应商资料不全，请选择送货上门方式");
                            } else {
                                areaBean = JSONObject.parseArray(s, AreaBean.class);
                                Area = areaBean.get(0).getArea();
                                Name = areaBean.get(0).getName();
                                ContactName = areaBean.get(0).getContactName();
                                ContactPhone = areaBean.get(0).getContactPhone();
                            }
                            initView();
                        }
                    });
        } else {
            mApi.GetPersonalInfo(GlobalParams.sToken, UID)
                    .compose(RxHttpResponseCompat.<String>compatResult())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {

                            if (s.equals("[]")) {
//                                RxToast.warning("当前个人资料不全，请选择送货上门方式");
                            } else {
                                personalInfoBean = JSONObject.parseArray(s, PersonalInfoBean.class);
                                ZZName = personalInfoBean.get(0).getContactAdress();
                                ZZContactName = personalInfoBean.get(0).getName();
                                ZZContactPhone = personalInfoBean.get(0).getPhone();
                            }
                            initView();
                        }
                    });
        }
    }

    private void initView() {
        leaseMyDeliveryMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBlur(dialogBg, mHandler);

                String[] list = GlobalParams.FHTypeList.toArray(new String[GlobalParams.FHTypeList.size()]);

                BaseDialog(LeaseMyEquipmentAddressActivity.this, dialogBg, list,
                        leaseMyDeliveryMode.getText().toString(), "送货方式", mHandler, new OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                leaseMyDeliveryMode.setText(data.get(position).getTitle());

                                if (data.get(position).getTitle().equals("送货上门")) {
                                    leaseMyDeliveryTimeText.setText("到货时间：");
                                    leaseMyAddressNameText.setText("收货地址：");
                                    leaseMyAddressName.setText("");
                                    leaseMyName.setText(GlobalParams.sUserName);
                                    leaseMyPhone.setText(GlobalParams.sUserPhone);


                                } else {
                                    leaseMyDeliveryTimeText.setText("提货时间：");
                                    leaseMyAddressNameText.setText("提货地点：");

                                    if (UID.equals("")) {
                                        if (!Area.equals("") || !Name.equals("")) {
                                            leaseMyAddressName.setText(Area + Name);

                                            leaseMyName.setText(ContactName);
                                            leaseMyPhone.setText(ContactPhone);
                                        }
                                    } else {
                                        leaseMyAddressName.setText(ZZName);
                                        leaseMyName.setText(ZZContactName);
                                        leaseMyPhone.setText(ZZContactPhone);
                                    }
                                }
                            }
                        });
            }
        });

    }


    @OnClick({R.id.lease_my_DeliveryTime, R.id.lease_equipment_my_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lease_my_DeliveryTime:
                initTimePicker();
                break;

            case R.id.lease_equipment_my_btn:
                if (leaseMyDeliveryTime.getText().toString().trim().equals("")) {
                    RxToast.warning("请选择提货时间");
                } else if (leaseMyAddressName.getText().toString().trim().equals("")) {
                    RxToast.warning("请输入送/提货地址");
                } else if (leaseMyName.getText().toString().trim().equals("")) {
                    RxToast.warning("请输入联系人");
                } else if (leaseMyPhone.getText().toString().trim().equals("")) {
                    RxToast.warning("请输入联系号码");
                } else {
                    Intent intent = new Intent(this, LeaseMyEquipmentSeeActivity.class);
                    intent.putExtra("MyEquipmentName", MyEquipmentName);
//                    intent.putExtra("MyEquipmentImage", MyEquipmentImage);
                    intent.putExtra("myLoad", Load);
                    intent.putExtra("MyNorm", MyNorm);
                    intent.putExtra("MyModle", MyModle);
                    intent.putExtra("MyStartTime", MyStartTime);
                    intent.putExtra("MyEndTime", MyEndTime);
                    intent.putExtra("Datasums", Datasums);
                    intent.putExtra("leaseSum", leaseSum);

                    //结算方式
                    for (DicItemBean dicItemBean : GlobalParams.sDicItemBean) {
                        if (dicItemBean.getDicTypeCode().equals("DT003") &&
                                dicItemBean.getItemName().equals(MyBalanceMode)) {

                            intent.putExtra("MyBalanceModeCode", dicItemBean.getItemCode());
                        }
                    }


                    intent.putExtra("MyBalanceMode", MyBalanceMode);

                    intent.putExtra("MyPValue", MyPValue);
                    intent.putExtra("MyRent", MyRent);
                    intent.putExtra("MyDepositPrice", MyDepositPrice);
                    intent.putExtra("MyDeposit", MyDeposit); //押金

                    //送货方式
                    for (DicItemBean dicItemBean : GlobalParams.sDicItemBean) {
                        if (dicItemBean.getDicTypeCode().equals("DT002") &&
                                dicItemBean.getItemName().equals(leaseMyDeliveryMode.getText().toString().trim())) {
                            intent.putExtra("deliveryModesCode", dicItemBean.getItemCode());
                        }
                    }

                    intent.putExtra("deliveryModes", leaseMyDeliveryMode.getText().toString().trim());
                    intent.putExtra("leaseMyDeliveryTime", leaseMyDeliveryTime.getText().toString().trim());


                    intent.putExtra("leaseMyAddress", leaseMyAddressName.getText().toString().trim());

                    intent.putExtra("leaseMyName", leaseMyName.getText().toString().trim());
                    intent.putExtra("leaseMyPhone", leaseMyPhone.getText().toString().trim());
                    intent.putExtra("leaseMyRefereeName", leaseMyRefereeName.getText().toString().trim());
                    intent.putExtra("leaseMyPhoneNumble", leaseMyPhoneNumble.getText().toString().trim());
                    intent.putExtra("Id", Id);
                    intent.putExtra("UserType", UserType);
                    intent.putExtra("CreateId", CreateId);
                    intent.putExtra("CreateUser", CreateUser);
                    intent.putExtra("MianzuQI", MianzuQI);
                    intent.putExtra("StoreId", StorehouseId);//仓库Id
                    intent.putExtra("StoreName", Name);//仓库名
                    intent.putExtra("OperatorId", OperatorId);//供应商ID
                    intent.putExtra("UID", UID);//使用者ID

                    intent.putExtra("ZDContactName", OperatorId);//供应商ID
                    intent.putExtra("ZDContactPhone", UID);//使用者ID

                    if (UID.isEmpty() || UID == null) {
                        intent.putExtra("Release", ContactName);//发布人
                        intent.putExtra("ReleasePhone", ContactPhone);//发布电话
                    } else {
                        intent.putExtra("Release", ZZContactName);//发布人
                        intent.putExtra("ReleasePhone", ZZContactPhone);//发布电话
                    }
                    intent.putExtra("TypeId", getIntent().getStringExtra("TypeId"));
                    intent.putExtra("TypeName", getIntent().getStringExtra("TypeName"));
                    intent.putExtra("DiscountAmount", getIntent().getStringExtra("DiscountAmount"));//折扣金额
                    startActivity(intent);
//                    finish();
                }
                break;

            default:
                break;
        }
    }


    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                leaseMyDeliveryTime.setText(getTime(date));
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
}
