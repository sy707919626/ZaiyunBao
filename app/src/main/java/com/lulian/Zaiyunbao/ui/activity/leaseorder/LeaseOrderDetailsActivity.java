package com.lulian.Zaiyunbao.ui.activity.leaseorder;

import android.app.AlertDialog;
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
import com.lulian.Zaiyunbao.ui.activity.ReserveRetireDetailsActivity;
import com.lulian.Zaiyunbao.ui.activity.pay.PayActivity;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lulian.Zaiyunbao.di.component.Constants.isAutoRefresh;

/**
 * Created by Administrator on 2018/11/8.
 */

public class LeaseOrderDetailsActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.my_order_no)
    TextView myOrderNo;
    @BindView(R.id.my_order_img_photo)
    ImageView myOrderImgPhoto;
    @BindView(R.id.my_order_shebei_name)
    TextView myOrderShebeiName;
    @BindView(R.id.my_order_shebei_spec)
    TextView myOrderShebeiSpec;
    @BindView(R.id.my_order_shebei_price)
    TextView myOrderShebeiPrice;
    @BindView(R.id.my_order_shebei_num_text)
    TextView myOrderShebeiNumText;
    @BindView(R.id.my_order_shebei_num)
    TextView myOrderShebeiNum;
    @BindView(R.id.lease_status_text)
    TextView leaseStatusText;
    @BindView(R.id.my_order_take_time)
    TextView myOrderTakeTime;
    @BindView(R.id.my_order_company)
    TextView myOrderCompany;
    @BindView(R.id.my_order_contacts)
    TextView myOrderContacts;
    @BindView(R.id.my_order_phone)
    TextView myOrderPhone;
    @BindView(R.id.my_order_zulin_sum_text)
    TextView myOrderZulinSumText;
    @BindView(R.id.my_order_zulin_sum)
    TextView myOrderZulinSum;
    @BindView(R.id.my_order_zulin_price)
    TextView myOrderZulinPrice;
    @BindView(R.id.my_order_zulin_day)
    TextView myOrderZulinDay;
    @BindView(R.id.my_order_Rent_free)
    TextView myOrderRentFree;
    @BindView(R.id.my_order_rent)
    TextView myOrderRent;
    @BindView(R.id.my_order_Unpaid_deposit)
    TextView myOrderUnpaidDeposit;
    @BindView(R.id.my_order_yunfei)
    TextView myOrderYunfei;
    @BindView(R.id.my_order_jiesuan_method)
    TextView myOrderJiesuanMethod;
    @BindView(R.id.my_order_TransferWay)
    TextView myOrderTransferWay;
    @BindView(R.id.my_order_service_site)
    TextView myOrderServiceSite;
    @BindView(R.id.fuwu_zhandian)
    RelativeLayout fuwuZhandian;
    @BindView(R.id.shouhuoren_text)
    TextView shouhuorenText;
    @BindView(R.id.my_order_consignee)
    TextView myOrderConsignee;
    @BindView(R.id.shouhuo_dizhi_text)
    TextView shouhuoDizhiText;
    @BindView(R.id.my_order_address)
    TextView myOrderAddress;
    @BindView(R.id.my_order_lianxi_phone)
    TextView myOrderLianxiPhone;
    @BindView(R.id.lease_service_recommend)
    TextView leaseServiceRecommend;
    @BindView(R.id.my_order_submission_btn)
    Button myOrderSubmissionBtn;
    @BindView(R.id.order_text)
    TextView orderText;
    @BindView(R.id.my_order_submission_layout)
    LinearLayout myOrderSubmissionLayout;
    @BindView(R.id.my_order_cancel)
    Button myOrderCancel;
    @BindView(R.id.my_order_Sure)
    Button myOrderSure;
    @BindView(R.id.my_order_btn_layout)
    LinearLayout myOrderBtnLayout;


    private String OrdersId = "";
    private String OrderNo = "";
    private String Id = ""; //设备ID
    private boolean isShowDailog = false;
    private List<MyOrderDetailsBean> myOrderDetailsList = new ArrayList<>();
    private MyOrderDetailsBean myOrderDetailsBean;

    @Override
    protected int setLayoutId() {
        return R.layout.order_my_details;
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

        textDetailContent.setText("租赁订单详情");
        textDetailRight.setVisibility(View.GONE);
        OrdersId = getIntent().getStringExtra("OrdersId");
        OrderNo = getIntent().getStringExtra("OrderNo");
        Id = getIntent().getStringExtra("Id");

        myOrderZulinSumText.setText("租赁数量");
        myOrderShebeiNumText.setText("租赁数量:");
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
        myOrderNo.setText(OrderNo);
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(myOrderDetailsBean.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            myOrderImgPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        myOrderShebeiName.setText(myOrderDetailsBean.getEquipmentName());
        myOrderShebeiSpec.setText(myOrderDetailsBean.getNorm());
        myOrderShebeiPrice.setText(myOrderDetailsBean.getPrice() + "");
        myOrderShebeiNum.setText(myOrderDetailsBean.getCount() + "");

        myOrderTakeTime.setText(myOrderDetailsBean.getTargetDeliveryTime());//取托时间
        myOrderCompany.setText(myOrderDetailsBean.getOrgName()); //企业名称
        myOrderContacts.setText(myOrderDetailsBean.getReceiveName());//企业联系人
        myOrderUnpaidDeposit.setText(myOrderDetailsBean.getOrderDeposit() + "元");//押金
        myOrderPhone.setText(myOrderDetailsBean.getAlianceLinkPhone());//企业联系电话

        myOrderZulinSum.setText(myOrderDetailsBean.getCount() + "个");//租赁数量
        myOrderZulinPrice.setText(myOrderDetailsBean.getPrice() + "元/天/片");
        myOrderZulinDay.setText(myOrderDetailsBean.getRentDates() + "天");
        myOrderRentFree.setText(myOrderDetailsBean.getFreeDates() + "天");//免租
        myOrderRent.setText(myOrderDetailsBean.getRentAmount() + "元");//租金
        myOrderYunfei.setText(myOrderDetailsBean.getTrafficFee() + "元");//运费


        if (myOrderDetailsBean.getHandRentWay() == 1) {//租金结算方式
            myOrderJiesuanMethod.setText("按月预付");
        } else if (myOrderDetailsBean.getHandRentWay() == 2) {
            myOrderJiesuanMethod.setText("按季预付");
        } else if (myOrderDetailsBean.getHandRentWay() == 3) {
            myOrderJiesuanMethod.setText("按半年预付");
        } else if (myOrderDetailsBean.getHandRentWay() == 4) {
            myOrderJiesuanMethod.setText("按年预付");
        } else if (myOrderDetailsBean.getHandRentWay() == 5) {
            myOrderJiesuanMethod.setText("月结");
        } else if (myOrderDetailsBean.getHandRentWay() == 6) {
            myOrderJiesuanMethod.setText("季结");
        }

        if (myOrderDetailsBean.getTransferWay() == 1) {//送货方式
            myOrderTransferWay.setText("送货上门");
            fuwuZhandian.setVisibility(View.VISIBLE);
            shouhuorenText.setText("收货人");
            shouhuoDizhiText.setText("收货地址");

            myOrderServiceSite.setText(myOrderDetailsBean.getStoreName());
            myOrderConsignee.setText(myOrderDetailsBean.getContactName());//收货人
            myOrderAddress.setText(myOrderDetailsBean.getTakeAddress());//收货地址
            myOrderLianxiPhone.setText(myOrderDetailsBean.getContactPhone());//联系电话

        } else if (myOrderDetailsBean.getTransferWay() == 2) {
            myOrderTransferWay.setText("用户自提");
            fuwuZhandian.setVisibility(View.GONE);
            shouhuorenText.setText("提货人");
            shouhuoDizhiText.setText("提货地址");

            myOrderConsignee.setText(myOrderDetailsBean.getReceiveName());//联系人
            myOrderAddress.setText(myOrderDetailsBean.getTakeAddress());//提货地址
            myOrderLianxiPhone.setText(myOrderDetailsBean.getAlianceLinkPhone());//接单联系电话
        }

        leaseServiceRecommend.setText(myOrderDetailsBean.getRecommend());//推荐人

        if (myOrderDetailsBean.getStatus() == 2) {
            leaseStatusText.setBackgroundResource(R.drawable.order_background);
        } else {
            leaseStatusText.setBackgroundResource(R.drawable.status_bj);
        }

        myOrderSubmissionLayout.setVisibility(View.VISIBLE);
        //未接单 = 0, 已接单 = 1,订单确认待支付 = 2,
        // 已支付待发货 = 3,已发货 = 5,已收货 = 6,退租中 = 7,已退租 = 8,已结算 = 9,已撤销 = 10
        if (myOrderDetailsBean.getStatus() == 0) {
            //未接单
            myOrderSubmissionBtn.setText("完成");
            myOrderSubmissionBtn.setVisibility(View.VISIBLE);
            leaseStatusText.setText("未接单");

        } else if (myOrderDetailsBean.getStatus() == 1) {
            myOrderSubmissionBtn.setVisibility(View.GONE);
            leaseStatusText.setText("待确认");

        } else if (myOrderDetailsBean.getStatus() == 2) {
            myOrderSubmissionBtn.setVisibility(View.VISIBLE);
            myOrderSubmissionBtn.setText("支付押金");
            leaseStatusText.setText("待支付");

        } else if (myOrderDetailsBean.getStatus() == 3) {
            myOrderSubmissionBtn.setVisibility(View.GONE);
            leaseStatusText.setText("待发货");

        } else if (myOrderDetailsBean.getStatus() == 5) {
            myOrderSubmissionBtn.setVisibility(View.VISIBLE);
            myOrderSubmissionBtn.setText("确认收货");
            leaseStatusText.setText("已发货");

        } else if (myOrderDetailsBean.getStatus() == 6) {
            myOrderSubmissionBtn.setVisibility(View.VISIBLE);
            myOrderSubmissionBtn.setText("我要退租");
            leaseStatusText.setText("已收货");

        } else if (myOrderDetailsBean.getStatus() == 7) {
            myOrderSubmissionBtn.setVisibility(View.GONE);
            leaseStatusText.setText("退租中");

        } else if (myOrderDetailsBean.getStatus() == 8) {
            myOrderSubmissionBtn.setVisibility(View.GONE);
            leaseStatusText.setText("已退租");

        } else if (myOrderDetailsBean.getStatus() == 9) {
            myOrderSubmissionBtn.setVisibility(View.GONE);
            leaseStatusText.setText("已结算");

        } else if (myOrderDetailsBean.getStatus() == 10) {
            myOrderSubmissionBtn.setVisibility(View.GONE);
            leaseStatusText.setText("已撤销");
        }

        //未接单 = 0, 已接单 = 1,订单确认待支付 = 2,
        // 已支付待发货 = 3,已发货 = 5,已收货 = 6,退租中 = 7,已退租 = 8,已结算 = 9,已撤销 = 10
        myOrderSubmissionBtn.setOnClickListener(new View.OnClickListener() {
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
//                    finish();
                } else if (myOrderDetailsBean.getStatus() == 5) {
                    //确认收货
                    showDialog("确认是否收货");
                } else if (myOrderDetailsBean.getStatus() == 6) {
                    //我要退租
                    showDialog("确认是否预约退租");
                }
            }
        });
    }

    private void showDialog(String message) {
        final AlertDialog builder = new AlertDialog.Builder(mContext)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = builder.findViewById(R.id.tv_msg);
        Button cancle = builder.findViewById(R.id.btn_cancle);
        Button sure = builder.findViewById(R.id.btn_sure);

        if (msg == null || cancle == null || sure == null) return;

        msg.setText(message);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myOrderDetailsBean.getStatus() == 5) {
                    //确认收货
                    Intent intent = new Intent(LeaseOrderDetailsActivity.this, ReceiveLeaseInfoActivity.class);
                    intent.putExtra("OrdersId", OrdersId);

                    intent.putExtra("AdapterPage", "LeaseOrderAdapter");
                    startActivity(intent);
//                    finish();

                } else if (myOrderDetailsBean.getStatus() == 6) {
                    //我要退租
                    Intent intentReserve = new Intent(LeaseOrderDetailsActivity.this, ReserveRetireDetailsActivity.class);
                    intentReserve.putExtra("OrdersId", OrdersId); //订单ID
                    intentReserve.putExtra("OrderNo", OrderNo); //订单号
                    intentReserve.putExtra("Id", Id); //设备ID

                    intentReserve.putExtra("AdapterPage", "LeaseOrderAdapter");
                    startActivity(intentReserve);
//                    finish();
                }

                builder.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isAutoRefresh) {
//            if (myOrderDetailsBean.getStatus() == 2) {
//                myOrderSubmissionBtn.setVisibility(View.GONE);
//                leaseStatusText.setText("待发货");
//
//            } else if (myOrderDetailsBean.getStatus() == 5) {
//                myOrderSubmissionBtn.setVisibility(View.VISIBLE);
//                myOrderSubmissionBtn.setText("我要退租");
//                leaseStatusText.setText("已收货");
//
//            } else if (myOrderDetailsBean.getStatus() == 6) {
//                myOrderSubmissionBtn.setVisibility(View.GONE);
//                leaseStatusText.setText("退租中");
//            }

            if (isAutoRefresh) {
                getData();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
