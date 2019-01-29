package com.lulian.Zaiyunbao.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.MyOrderDetailsBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.ui.activity.leaseorder.ReceiveLeaseInfoActivity;
import com.lulian.Zaiyunbao.ui.activity.pay.PayActivity;
import com.lulian.Zaiyunbao.ui.activity.subleaseorder.SubleaseOrderEntryActivity;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lulian.Zaiyunbao.di.component.Constants.isAutoRefresh;

/**
 * Created by Administrator on 2018/11/8.
 */

public class SubleaseOrderDetailsActivity extends BaseActivity {


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
    @BindView(R.id.sublease_order_no)
    TextView subleaseOrderNo;
    @BindView(R.id.sublease_order_state)
    TextView subleaseOrderState;
    @BindView(R.id.order_img_photo)
    ImageView orderImgPhoto;
    @BindView(R.id.order_shebei_name)
    TextView orderShebeiName;
    @BindView(R.id.order_shebei_spec)
    TextView orderShebeiSpec;
    @BindView(R.id.order_price)
    TextView orderPrice;
    @BindView(R.id.order_zuru_time)
    TextView orderZuruTime;
    @BindView(R.id.sublease_order_fabu_time)
    TextView subleaseOrderFabuTime;
    @BindView(R.id.sublease_order_shebeiname)
    TextView subleaseOrderShebeiname;
    @BindView(R.id.sublease_order_sum)
    TextView subleaseOrderSum;
    @BindView(R.id.sublease_order_deposit)
    TextView subleaseOrderDeposit;
    @BindView(R.id.sublease_order_transferWay)
    TextView subleaseOrderTransferWay;
    @BindView(R.id.sublease_order_orgName)
    TextView subleaseOrderOrgName;
    @BindView(R.id.sublease_order_name)
    TextView subleaseOrderName;
    @BindView(R.id.sublease_order_address_text)
    TextView subleaseOrderAddressText;
    @BindView(R.id.sublease_order_address)
    TextView subleaseOrderAddress;
    @BindView(R.id.sublease_order_zhifu_yajin_text)
    TextView subleaseOrderZhifuYajinText;
    @BindView(R.id.sublease_order_zhifu_yajin)
    TextView subleaseOrderZhifuYajin;
    @BindView(R.id.jiedan_info_linearlayout)
    LinearLayout jiedanInfoLinearlayout;
    @BindView(R.id.sublease_order_submission_btn)
    Button subleaseOrderSubmissionBtn;
    @BindView(R.id.sublease_order_submission_layout)
    LinearLayout subleaseOrderSubmissionLayout;
    @BindView(R.id.sublease_order_cancel)
    Button subleaseOrderCancel;
    @BindView(R.id.sublease_order_Sure)
    Button subleaseOrderSure;
    @BindView(R.id.sublease_order_btn_layout)
    LinearLayout subleaseOrderBtnLayout;

    private String OrdersId = "";
    private String OrderNo = "";
    private String Id = "";
    private int IsRendIn; //租入方

    private List<MyOrderDetailsBean> myOrderDetailsList = new ArrayList<>();
    private MyOrderDetailsBean myOrderDetailsBean;

    @Override
    protected int setLayoutId() {
        return R.layout.subleaseorder_my_details;
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

        textDetailContent.setText("订单详情");
        textDetailRight.setVisibility(View.GONE);
        OrdersId = getIntent().getStringExtra("OrdersId"); //订单ID
        OrderNo = getIntent().getStringExtra("OrderNo"); //订单号码
        Id = getIntent().getStringExtra("Id"); //设备id
        IsRendIn = getIntent().getIntExtra("IsRendIn", 0);

        getData();
    }

    private void getData() {
        mApi.myEquipmentRentOrderDetail(GlobalParams.sToken, OrdersId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        myOrderDetailsList = JSONObject.parseArray(s, MyOrderDetailsBean.class);
                        myOrderDetailsBean = myOrderDetailsList.get(0);
                        initView();
                    }
                });
    }

    private void initView() {
        subleaseOrderNo.setText(OrderNo);
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(myOrderDetailsBean.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            orderImgPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        orderShebeiName.setText(myOrderDetailsBean.getEquipmentName());
        orderShebeiSpec.setText(myOrderDetailsBean.getNorm());
        orderPrice.setText(myOrderDetailsBean.getPrice() + "");
        orderZuruTime.setText(myOrderDetailsBean.getCount() + "");
        subleaseOrderFabuTime.setText(myOrderDetailsBean.getCreateTime());//发布时间
        subleaseOrderShebeiname.setText(myOrderDetailsBean.getEquipmentName()); //设备名称
        subleaseOrderSum.setText(myOrderDetailsBean.getCount() + "个");//租赁数量
        subleaseOrderDeposit.setText(myOrderDetailsBean.getOrderDeposit() + "元");//押金


        subleaseOrderZhifuYajin.setText(myOrderDetailsBean.getOrderDeposit() + "元");
        if (myOrderDetailsBean.getTransferWay() == 1) {
            subleaseOrderTransferWay.setText("送货上门");
            subleaseOrderAddressText.setText("送货地址");

            subleaseOrderOrgName.setText(myOrderDetailsBean.getStoreName()); //企业名称
            subleaseOrderAddress.setText(myOrderDetailsBean.getTakeAddress());//收货地址
            subleaseOrderName.setText(myOrderDetailsBean.getContactName());//联系人

        } else {
            subleaseOrderTransferWay.setText("用户自提");
            subleaseOrderAddressText.setText("提货地址");

            subleaseOrderOrgName.setText(myOrderDetailsBean.getOrgName());//企业名称
            subleaseOrderAddress.setText(myOrderDetailsBean.getTakeAddress());//收货地址
            subleaseOrderName.setText(myOrderDetailsBean.getReceiveName());//联系人
        }

        if (myOrderDetailsBean.getStatus() == 2) {
            subleaseOrderState.setBackgroundResource(R.drawable.order_background);
        } else {
            subleaseOrderState.setBackgroundResource(R.drawable.status_bj);
        }
        //未接单 = 0, 已接单 = 1,订单确认待支付 = 2,
        // 已支付待发货 = 3,已发货 = 5,已收货 = 6,退租中 = 7,已退租 = 8,已结算 = 9,已撤销 = 10
        if (myOrderDetailsBean.getStatus() == 1) {
            subleaseOrderZhifuYajinText.setText("待支付押金");
            //未接单
            subleaseOrderState.setText("待确认");
//            subleaseOrderSubmissionBtn.setVisibility(View.VISIBLE);

            if (IsRendIn == 0) {//用户是否为租入方(IsRendIn 1是0否)
                subleaseOrderSubmissionLayout.setVisibility(View.GONE);
                subleaseOrderBtnLayout.setVisibility(View.VISIBLE);
                subleaseOrderCancel.setText("撤销订单");
                subleaseOrderSure.setText("确认接单");
//                subleaseOrderSubmissionBtn.setText("确认接单");
            } else {
//                subleaseOrderSubmissionBtn.setText("撤回订单");

                subleaseOrderSubmissionLayout.setVisibility(View.GONE);
                subleaseOrderBtnLayout.setVisibility(View.GONE);
            }

        } else if (myOrderDetailsBean.getStatus() == 2) {
            subleaseOrderZhifuYajinText.setText("待支付押金");
            if (IsRendIn == 1) {//用户是否为租入方(IsRendIn 1是0否)
                subleaseOrderState.setText("待支付");
                jiedanInfoLinearlayout.setVisibility(View.VISIBLE);
//                subleaseOrderSubmissionBtn.setVisibility(View.VISIBLE);
//                subleaseOrderSubmissionBtn.setText("支付押金");

                subleaseOrderSubmissionLayout.setVisibility(View.GONE);
                subleaseOrderBtnLayout.setVisibility(View.VISIBLE);
                subleaseOrderCancel.setText("撤销订单");
                subleaseOrderSure.setText("支付押金");
            } else {
                subleaseOrderState.setText("待支付");
                jiedanInfoLinearlayout.setVisibility(View.VISIBLE);
//                subleaseOrderSubmissionBtn.setVisibility(View.GONE);
                subleaseOrderSubmissionLayout.setVisibility(View.GONE);
                subleaseOrderBtnLayout.setVisibility(View.GONE);
            }

        } else if (myOrderDetailsBean.getStatus() == 3) {
            subleaseOrderZhifuYajinText.setText("已支付押金");
            if (IsRendIn == 0) {//用户是否为租入方(IsRendIn 1是0否)
                subleaseOrderState.setText("待发货");
                jiedanInfoLinearlayout.setVisibility(View.VISIBLE);
//                subleaseOrderSubmissionBtn.setVisibility(View.VISIBLE);
                subleaseOrderSubmissionLayout.setVisibility(View.VISIBLE);
                subleaseOrderBtnLayout.setVisibility(View.GONE);
                subleaseOrderSubmissionBtn.setText("确认发货");
            } else {
                subleaseOrderState.setText("待发货");
                jiedanInfoLinearlayout.setVisibility(View.VISIBLE);
//                subleaseOrderSubmissionBtn.setVisibility(View.GONE);
                subleaseOrderSubmissionLayout.setVisibility(View.GONE);
                subleaseOrderBtnLayout.setVisibility(View.GONE);
            }

        } else if (myOrderDetailsBean.getStatus() == 5) {
            subleaseOrderZhifuYajinText.setText("已支付押金");
            if (IsRendIn == 1) {//用户是否为租入方(IsRendIn 1是0否)
                subleaseOrderState.setText("待收货");
                jiedanInfoLinearlayout.setVisibility(View.VISIBLE);
//                subleaseOrderSubmissionBtn.setVisibility(View.VISIBLE);
                subleaseOrderSubmissionLayout.setVisibility(View.VISIBLE);
                subleaseOrderBtnLayout.setVisibility(View.GONE);

                subleaseOrderSubmissionBtn.setText("确认收货");
            } else {
                subleaseOrderState.setText("待收货");
                jiedanInfoLinearlayout.setVisibility(View.VISIBLE);
//                subleaseOrderSubmissionBtn.setVisibility(View.GONE);
                subleaseOrderSubmissionLayout.setVisibility(View.GONE);
                subleaseOrderBtnLayout.setVisibility(View.GONE);
            }

        } else if (myOrderDetailsBean.getStatus() == 6) {
            subleaseOrderZhifuYajinText.setText("已支付押金");
            if (IsRendIn == 1) {//用户是否为租入方(IsRendIn 1是0否)
                subleaseOrderState.setText("已收货");
                jiedanInfoLinearlayout.setVisibility(View.VISIBLE);
//                subleaseOrderSubmissionBtn.setVisibility(View.VISIBLE);
                subleaseOrderSubmissionLayout.setVisibility(View.VISIBLE);
                subleaseOrderBtnLayout.setVisibility(View.GONE);

                subleaseOrderSubmissionBtn.setText("我要退租");
            } else {
                subleaseOrderState.setText("已收货");
                jiedanInfoLinearlayout.setVisibility(View.VISIBLE);
//                subleaseOrderSubmissionBtn.setVisibility(View.GONE);
                subleaseOrderSubmissionLayout.setVisibility(View.GONE);
                subleaseOrderBtnLayout.setVisibility(View.GONE);
            }

        } else if (myOrderDetailsBean.getStatus() == 7) {
            subleaseOrderZhifuYajinText.setText("已支付押金");
            subleaseOrderState.setText("退租中");
            jiedanInfoLinearlayout.setVisibility(View.VISIBLE);
//            subleaseOrderSubmissionBtn.setVisibility(View.GONE);
            subleaseOrderSubmissionLayout.setVisibility(View.GONE);
            subleaseOrderBtnLayout.setVisibility(View.GONE);

        } else if (myOrderDetailsBean.getStatus() == 8) {
            subleaseOrderZhifuYajinText.setText("已支付押金");
            subleaseOrderState.setText("已退租");
            jiedanInfoLinearlayout.setVisibility(View.VISIBLE);
//            subleaseOrderSubmissionBtn.setVisibility(View.GONE);

            subleaseOrderSubmissionLayout.setVisibility(View.GONE);
            subleaseOrderBtnLayout.setVisibility(View.GONE);

        } else if (myOrderDetailsBean.getStatus() == 9) {
            subleaseOrderZhifuYajinText.setText("已支付押金");
            subleaseOrderState.setText("已结算");
            jiedanInfoLinearlayout.setVisibility(View.VISIBLE);
//            subleaseOrderSubmissionBtn.setVisibility(View.GONE);

            subleaseOrderSubmissionLayout.setVisibility(View.GONE);
            subleaseOrderBtnLayout.setVisibility(View.GONE);

        } else if (myOrderDetailsBean.getStatus() == 10) {
            subleaseOrderZhifuYajinText.setText("未支付押金");
            subleaseOrderState.setText("已撤销");
            jiedanInfoLinearlayout.setVisibility(View.VISIBLE);
//            subleaseOrderSubmissionBtn.setVisibility(View.GONE);
            subleaseOrderSubmissionLayout.setVisibility(View.GONE);
            subleaseOrderBtnLayout.setVisibility(View.GONE);
        }

        subleaseOrderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myOrderDetailsBean.getStatus() == 2) {
                    if (IsRendIn == 1) {//用户是否为租入方(IsRendIn 1是0否)
                        //撤销订单
                        mApi.CancelOrder(GlobalParams.sToken, 1, OrdersId)
                                .compose(RxHttpResponseCompat.<String>compatResult())
                                .subscribe(new ErrorHandlerSubscriber<String>() {
                                    @Override
                                    public void onNext(String s) {
                                        RxToast.success("订单撤销成功");

                                        subleaseOrderState.setText("已撤销");
                                        jiedanInfoLinearlayout.setVisibility(View.VISIBLE);
                                        subleaseOrderSubmissionLayout.setVisibility(View.GONE);
                                        subleaseOrderBtnLayout.setVisibility(View.GONE);
                                        Constants.setIsAutoRefresh(true);
                                        subleaseOrderState.setBackgroundResource(R.drawable.status_bj);
                                    }
                                });
                    }
                } else if (myOrderDetailsBean.getStatus() == 1) {
                    if (IsRendIn == 0) {//用户是否为租入方(IsRendIn 1是0否)
                        //撤销订单
                        mApi.CancelOrder(GlobalParams.sToken, 1, OrdersId)
                                .compose(RxHttpResponseCompat.<String>compatResult())
                                .subscribe(new ErrorHandlerSubscriber<String>() {
                                    @Override
                                    public void onNext(String s) {
                                        RxToast.success("订单撤销成功");

                                        subleaseOrderState.setText("已撤销");
                                        jiedanInfoLinearlayout.setVisibility(View.GONE);
                                        subleaseOrderSubmissionLayout.setVisibility(View.GONE);
                                        subleaseOrderBtnLayout.setVisibility(View.GONE);
                                        Constants.setIsAutoRefresh(true);
                                    }
                                });
                    }
                }
            }
        });

        subleaseOrderSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myOrderDetailsBean.getStatus() == 2) {
                    //支付押金
                    Intent intent = new Intent(mContext, PayActivity.class);
                    intent.putExtra("orderId", OrdersId);
                    intent.putExtra("UserId", GlobalParams.sUserId);
                    intent.putExtra("money", myOrderDetailsBean.getOrderDeposit());
                    intent.putExtra("OrderType", 1); //1租赁单2购买单
                    mContext.startActivity(intent);

                } else if (myOrderDetailsBean.getStatus() == 1) {
                    if (IsRendIn == 0) { //用户是否为租入方(IsRendIn 1是0否)
                        //确认接单
                        mApi.updateOrderStatus(GlobalParams.sToken, 1, OrdersId, 2)
                                .compose(RxHttpResponseCompat.<String>compatResult())
                                .subscribe(new ErrorHandlerSubscriber<String>() {
                                    @Override
                                    public void onNext(String s) {
                                        RxToast.success("订单接单成功");

                                        subleaseOrderState.setText("待支付");
                                        jiedanInfoLinearlayout.setVisibility(View.VISIBLE);
                                        subleaseOrderSubmissionLayout.setVisibility(View.GONE);
                                        subleaseOrderBtnLayout.setVisibility(View.GONE);
                                        Constants.setIsAutoRefresh(true);
                                    }
                                });
                    }

                }

            }
        });


        //未接单 = 0, 已接单 = 1,订单确认待支付 = 2,
        // 已支付待发货 = 3,已发货 = 5,已收货 = 6,退租中 = 7,已退租 = 8,已结算 = 9,已撤销 = 10
        subleaseOrderSubmissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (myOrderDetailsBean.getStatus() == 1) {
//                    if (IsRendIn == 1) {
//                        getData(OrdersId, 2);
//                    } else {
//                        getData(OrdersId, 10);
//                    }

//                } else if (myOrderDetailsBean.getStatus() == 2) {
//                    //支付押金
//                    Intent intent = new Intent(mContext, PayActivity.class);
//                    intent.putExtra("orderId", OrdersId);
//                    intent.putExtra("UserId", GlobalParams.sUserId);
//                    intent.putExtra("money", myOrderDetailsBean.getOrderDeposit());
//                    intent.putExtra("OrderType", 1); //1租赁单2购买单
//                    mContext.startActivity(intent);

//                } else
                if (myOrderDetailsBean.getStatus() == 3) {
                    //发货
                    Intent intent2 = new Intent(SubleaseOrderDetailsActivity.this, SubleaseOrderEntryActivity.class);
                    intent2.putExtra("EquipmentName", myOrderDetailsBean.getEquipmentName());
                    intent2.putExtra("EquipmentNorm", myOrderDetailsBean.getNorm());
                    intent2.putExtra("OrderId", OrdersId);
                    intent2.putExtra("Count", myOrderDetailsBean.getCount());
                    intent2.putExtra("EquipmentId", Id);

                    intent2.putExtra("AdapterPage", "SubleaseOrderAdapter");
                    startActivity(intent2);

//                    finish();
                } else if (myOrderDetailsBean.getStatus() == 5) {
                    Intent intent = new Intent(mContext, ReceiveLeaseInfoActivity.class);
                    intent.putExtra("OrdersId", OrdersId);

                    intent.putExtra("AdapterPage", "SubleaseOrderAdapter");
                    mContext.startActivity(intent);


                } else if (myOrderDetailsBean.getStatus() == 6) {
                    //退租
                    Intent intentReserve = new Intent(mContext, ReserveRetireDetailsActivity.class);
                    intentReserve.putExtra("OrdersId", OrdersId);
                    intentReserve.putExtra("OrderNo", OrderNo);
                    intentReserve.putExtra("Id", Id); //设备ID

                    intentReserve.putExtra("AdapterPage", "SubleaseOrderAdapter");
                    mContext.startActivity(intentReserve);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isAutoRefresh) {
            getData();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
