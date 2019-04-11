package com.lulian.Zaiyunbao.ui.activity.service;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
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
import com.lulian.Zaiyunbao.Bean.CanRetireEquipmentListBean;
import com.lulian.Zaiyunbao.Bean.SaleEntity;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/11/20.
 */

public class RetireCreateActivity extends BaseActivity {

    //退租方式
    private static final String[] TuiZuMode = {"物流运输", "送货上门"};
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.retire_create_see_map)
    ImageView retireCreateSeeMap;
    @BindView(R.id.rent_can_sum_text)
    TextView rentCanSumText;
    @BindView(R.id.rent_can_sum)
    TextView rentCanSum;
    @BindView(R.id.rent_can_sum_text2)
    TextView rentCanSumText2;
    @BindView(R.id.retire_create_service_site)
    TextView retireCreateServiceSite;
    @BindView(R.id.retire_create_site_address)
    TextView retireCreateSiteAddress;
    @BindView(R.id.retire_create_site_distance)
    TextView retireCreateSiteDistance;
    @BindView(R.id.retire_create_dateTime)
    TextView retireCreateDateTime;
    @BindView(R.id.retire_create_Mode_text)
    TextView retireCreateModeText;
    @BindView(R.id.retire_create_Mode)
    TextView retireCreateMode;
    @BindView(R.id.retire_create_typeName_text)
    TextView retireCreateTypeNameText;
    @BindView(R.id.retire_create_typeName)
    TextView retireCreateTypeName;
    @BindView(R.id.cansum_text)
    TextView cansumText;
    @BindView(R.id.retire_create_shebei_cansum)
    TextView retireCreateShebeiCansum;
    @BindView(R.id.cansum_text2)
    TextView cansumText2;
    @BindView(R.id.retire_create_shebei_sum)
    ClearEditText retireCreateShebeiSum;
    @BindView(R.id.retire_create_lixi_name)
    ClearEditText retireCreateLixiName;
    @BindView(R.id.retire_create_phone)
    ClearEditText retireCreatePhone;
    @BindView(R.id.retire_create_btn)
    Button retireCreateBtn;
    @BindView(R.id.dialog_bg)
    ImageView dialogBg;


    private List<CanRetireEquipmentListBean> EtypeList = new ArrayList<>();
    //设备类型
    private List<String> EType = new ArrayList<>();
    private String EtypeId = "";
    private int Quantity;
    private TimePickerView pvTime;

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
        return R.layout.activity_retire_create;
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

        textDetailContent.setText("退租预约");
        textDetailRight.setVisibility(View.GONE);
        getCanRent();

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


        initView();
    }

    private void initView() {
        retireCreateSiteAddress.setText(getIntent().getStringExtra("Area"));
        retireCreateServiceSite.setText(getIntent().getStringExtra("Name"));
        retireCreateSiteDistance.setText("距我约15KM");

        retireCreateTypeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBlur(dialogBg, mHandler);
                String[] list = EType.toArray(new String[EType.size()]);

                BaseDialog(RetireCreateActivity.this, dialogBg, list,
                        retireCreateTypeName.getText().toString(), "设备类型", mHandler, new OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                retireCreateTypeName.setText(data.get(position).getTitle());

                                for (int i = 0; i < EtypeList.size(); i++) {
                                    if (EtypeList.get(i).getEquipmentName().equals(data.get(position).getTitle())) {
                                        Quantity = EtypeList.get(i).getQuantity();
                                    }
                                }
                                retireCreateShebeiCansum.setText(Quantity + "");
                                rentCanSum.setText(Quantity + "");
                            }
                        });
            }
        });

        retireCreateMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBlur(dialogBg, mHandler);
                String[] list = TuiZuMode;

                BaseDialog(RetireCreateActivity.this, dialogBg, list,
                        retireCreateMode.getText().toString(), "退租方式", mHandler, new OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                retireCreateMode.setText(data.get(position).getTitle());

                            }
                        });
            }
        });

    }

    @OnClick({R.id.retire_create_btn, R.id.retire_create_see_map, R.id.retire_create_dateTime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.retire_create_btn:
                if (retireCreateTypeName.getText().toString().trim().equals("")) {
                    RxToast.warning("请选择设备！");
                } else if (retireCreateDateTime.getText().toString().trim().equals("")) {
                    RxToast.warning("请退租时间！");
                } else if (retireCreateLixiName.getText().toString().trim().equals("")) {
                    RxToast.warning("请输入联系人！");
                } else if (retireCreatePhone.getText().toString().trim().equals("")) {
                    RxToast.warning("请输入联系电话！");
                } else if (retireCreateShebeiSum.getText().toString().trim().equals("")) {
                    RxToast.warning("请输入退租数量！");
                } else {

                    for (int i = 0; i < EtypeList.size(); i++) {
                        if (EtypeList.get(i).getEquipmentName().equals(retireCreateTypeName.getText().toString().trim())) {
                            Quantity = EtypeList.get(i).getQuantity();
                            EtypeId = EtypeList.get(i).getEDicId();
                        }
                    }

                    if (Integer.valueOf(retireCreateShebeiSum.getText().toString().trim()) > Quantity) {
                        RxToast.warning("退租的数量不能超过库存数量！");
                        return;
                    }

                    //发起预约
                    Intent intent = new Intent(RetireCreateActivity.this, RetireConfirmActivity.class);
                    intent.putExtra("EquipmentName", retireCreateTypeName.getText().toString().trim());
                    intent.putExtra("TargetDeliveryTime", retireCreateDateTime.getText().toString().trim()); //退租时间
                    intent.putExtra("StoreId", getIntent().getStringExtra("StoreId")); //仓库ID
                    intent.putExtra("StoreName", retireCreateServiceSite.getText().toString().trim());
                    intent.putExtra("ETypeId", EtypeId); //设备Id
                    intent.putExtra("ETypeName", retireCreateTypeName.getText().toString().trim());//设备名称
                    intent.putExtra("Count", Integer.valueOf(retireCreateShebeiSum.getText().toString().trim())); //实际退租数量
                    intent.putExtra("Quantity", Quantity); //可退租数量
                    intent.putExtra("BackLink", retireCreateLixiName.getText().toString().trim());
                    intent.putExtra("BackLinkPhone", retireCreatePhone.getText().toString().trim());
                    intent.putExtra("TakeAddress", retireCreateSiteAddress.getText().toString().trim());
                    intent.putExtra("ReserveMode", retireCreateMode.getText().toString().trim());
                    intent.putExtra("BelongMember", getIntent().getStringExtra("BelongMember"));
                    startActivity(intent);
                    finish();
                }
                break;

            case R.id.retire_create_see_map:
                //查看地图
                break;

            case R.id.retire_create_dateTime:
                initTimePicker();
                break;
            default:
                break;
        }
    }

    //获取可退租设备
    private void getCanRent() {
        mApi.canRentEquipmentList(GlobalParams.sToken, GlobalParams.sUserId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        EtypeList = JSONObject.parseArray(s, CanRetireEquipmentListBean.class);

                        for (int i = 0; i < EtypeList.size(); i++) {
                            EType.add(EtypeList.get(i).getEquipmentName());
                        }
                    }
                });
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Date dateNew = new Date(System.currentTimeMillis());

                if (differentDays(dateNew, date) < 0) {
                    RxToast.warning("选择的时间不能小于当前时间");
                } else {
                    retireCreateDateTime.setText(getTime(date));
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

}
