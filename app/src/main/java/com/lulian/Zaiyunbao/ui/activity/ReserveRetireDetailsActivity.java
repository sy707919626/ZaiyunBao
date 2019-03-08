package com.lulian.Zaiyunbao.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
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
import com.lulian.Zaiyunbao.Bean.MyOrderDetailsBean;
import com.lulian.Zaiyunbao.Bean.SaleEntity;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/11/8.
 */

public class ReserveRetireDetailsActivity extends BaseActivity {

    //退租方式
    private static final String[] ReserveMode = {"物流运输", "送货上门", "上门自提"};
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.order_text)
    TextView orderText;
    @BindView(R.id.reserve_retire_order_no)
    TextView reserveRetireOrderNo;
    @BindView(R.id.reserve_retire_status_text)
    TextView reserveRetireStatusText;
    @BindView(R.id.my_order_img_photo)
    ImageView myOrderImgPhoto;
    @BindView(R.id.reserve_retire_shebei_name)
    TextView reserveRetireShebeiName;
    @BindView(R.id.reserve_retire_shebei_spec)
    TextView reserveRetireShebeiSpec;
    @BindView(R.id.reserve_retire_shebei_price)
    TextView reserveRetireShebeiPrice;
    @BindView(R.id.reserve_retire_shebei_num)
    TextView reserveRetireShebeiNum;
    @BindView(R.id.reserve_retire_company)
    TextView reserveRetireCompany;
    @BindView(R.id.reserve_retire_contacts)
    TextView reserveRetireContacts;
    @BindView(R.id.reserve_retire_deposit)
    TextView reserveRetireDeposit;
    @BindView(R.id.reserve_retire_phone)
    TextView reserveRetirePhone;
    @BindView(R.id.reserve_retire_service_site)
    TextView reserveRetireServiceSite;
    @BindView(R.id.fuwu_zhandian)
    RelativeLayout fuwuZhandian;
    @BindView(R.id.reserve_retire_address)
    TextView reserveRetireAddress;
    @BindView(R.id.reserve_retire_lianxiren)
    TextView reserveRetireLianxiren;
    @BindView(R.id.reserve_retire_lianxi_phone)
    TextView reserveRetireLianxiPhone;
    @BindView(R.id.reserve_retire_mode_text)
    TextView reserveRetireModeText;
    @BindView(R.id.reserve_retire_mode)
    TextView reserveRetireMode;
    @BindView(R.id.reserve_retire_data_text)
    TextView reserveRetireDataText;
    @BindView(R.id.reserve_retire_data)
    TextView reserveRetireData;
    @BindView(R.id.error_text)
    TextView errorText;
    @BindView(R.id.reserve_retire_submission_btn)
    Button reserveRetireSubmissionBtn;
    @BindView(R.id.dialog_bg)
    ImageView dialogBg;


    private String OrdersId = "";
    private String OrderNo = "";
    private String Id; //设备Id
    private TimePickerView pvTime;
    private MyOrderDetailsBean myOrderDetailsBean;

    private String FragmentPage;//区分页面
    private String AdapterPage;//区分适配器来源

    private int StockSum; //库存数量

    private Handler mHandler;

    @Override
    protected int setLayoutId() {
        return R.layout.reserve_retire_details;
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

        textDetailContent.setText("预约退租");
        textDetailRight.setVisibility(View.GONE);

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

        OrdersId = getIntent().getStringExtra("OrdersId");
        OrderNo = getIntent().getStringExtra("OrderNo");
        Id = getIntent().getStringExtra("Id");

        FragmentPage = getIntent().getStringExtra("FragmentPage");
        AdapterPage = getIntent().getStringExtra("AdapterPage");

        getOrderDatails();

    }

    private void getStockSum() {
        mApi.GetEquipmentCount(GlobalParams.sToken, Id, GlobalParams.sUserId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        StockSum = Integer.valueOf(s);
                        if (StockSum < myOrderDetailsBean.getCount()) {
                            errorText.setText("当前设备库存不足，库存数量为" + StockSum + "个，只能以库存数量进行退租处理");
                        } else {
                            errorText.setText("");
                        }
                    }
                });
    }

    private void initView() {
        getStockSum();
        //未接单 = 0, 已接单 = 1, 订单确认 = 2, 待支付 = 3,
        // 待发货 = 4, 已发货 = 5, 已收货 = 6, 退租中 = 7,已退租 = 8,已结算=9,已撤销 = 10
        if (myOrderDetailsBean.getStatus() == 6) {
            reserveRetireSubmissionBtn.setText("预约退租");
        } else if (myOrderDetailsBean.getStatus() == 7) {
            reserveRetireSubmissionBtn.setText("退租中");
        } else if (myOrderDetailsBean.getStatus() == 8) {
            reserveRetireSubmissionBtn.setText("已退租");
        }
        reserveRetireOrderNo.setText(OrderNo);

        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(myOrderDetailsBean.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            myOrderImgPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        reserveRetireStatusText.setText("已收货");
        reserveRetireShebeiName.setText(myOrderDetailsBean.getEquipmentName());
        reserveRetireShebeiSpec.setText(myOrderDetailsBean.getNorm());
        reserveRetireShebeiPrice.setText(myOrderDetailsBean.getPrice() + "");
        reserveRetireShebeiNum.setText(myOrderDetailsBean.getCount() + "");

        reserveRetireCompany.setText(myOrderDetailsBean.getOrgName()); //企业名称
        reserveRetireContacts.setText(myOrderDetailsBean.getContactName());//企业联系人
        reserveRetireDeposit.setText(myOrderDetailsBean.getOrderDeposit() + "元");//押金
        reserveRetirePhone.setText(myOrderDetailsBean.getContactPhone());//企业联系电话
        reserveRetireServiceSite.setText(myOrderDetailsBean.getStoreName()); //服务站点

        reserveRetireLianxiren.setText(myOrderDetailsBean.getReceiveName());//联系人
        reserveRetireLianxiPhone.setText(myOrderDetailsBean.getAlianceLinkPhone());//联系电话
//        reserveRetireShouhuoren.setText(myOrderDetailsBean.getReceiveName());//联系人
        reserveRetireAddress.setText(myOrderDetailsBean.getTakeAddress());//提货地址


        reserveRetireMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBlur(dialogBg, mHandler);

                String[] list = GlobalParams.FHTypeList.toArray(new String[GlobalParams.FHTypeList.size()]);

                BaseDialog(ReserveRetireDetailsActivity.this, dialogBg, list,
                        reserveRetireMode.getText().toString(), "退租方式", mHandler, new OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                reserveRetireMode.setText(data.get(position).getTitle());
                            }
                        });
            }
        });
    }

    @OnClick({R.id.reserve_retire_data, R.id.reserve_retire_submission_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reserve_retire_data:
                initTimePicker();
                break;

            case R.id.reserve_retire_submission_btn:
                if (reserveRetireData.getText().toString().trim().equals("")) {
                    RxToast.warning("请选择退租时间");
                } else if (reserveRetireMode.getText().toString().trim().equals("")) {
                    RxToast.warning("请选择退租方式");
                } else {
                    //我要退租
                    if (myOrderDetailsBean.getStatus() == 6) {
                        upadteData();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void upadteData() {

        JSONObject obj = new JSONObject();
        obj.put("RentOrderID", OrdersId);
        obj.put("TargetDeliveryTime", reserveRetireData.getText().toString().trim());
        obj.put("StoreId", myOrderDetailsBean.getStoreId());
        obj.put("ETypeId", Id);//设备ID
        obj.put("ETypeName", myOrderDetailsBean.getEquipmentName()); //设备名称

        if (myOrderDetailsBean.getCount() <= StockSum) {
            obj.put("Count", myOrderDetailsBean.getCount()); //实际退租数量
            obj.put("CanRentCount", myOrderDetailsBean.getCount()); //可退租数量
        } else {
            obj.put("Count", StockSum); //实际库存数量
            obj.put("CanRentCount", StockSum);
        }

        obj.put("BackLink", reserveRetireContacts.getText().toString().trim());
        obj.put("BackLinkPhone", reserveRetirePhone.getText().toString().trim());
        obj.put("CreateUserId", GlobalParams.sUserId);

        if (reserveRetireMode.getText().toString().equals("送货上门")){
            obj.put("TransferWay", 1);
        } else {
            obj.put("TransferWay", 2);
        }

        String args = obj.toString();
        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                args);
        mApi.equipmentBackRent(GlobalParams.sToken, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        Constants.setIsAutoRefresh(true);
                        RxToast.success("预约退租成功");
                        reserveRetireSubmissionBtn.setVisibility(View.GONE);
                        reserveRetireStatusText.setText("退租中");
                        finish();
                    }
                });
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                reserveRetireData.setText(getTime(date));
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


    private void getOrderDatails() {
        mApi.myEquipmentRentOrderDetail(GlobalParams.sToken, OrdersId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        myOrderDetailsBean = JSONObject.parseArray(s, MyOrderDetailsBean.class).get(0);
                        initView();
                    }
                });
    }

}
