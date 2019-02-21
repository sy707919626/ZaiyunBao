package com.lulian.Zaiyunbao.ui.activity.seekrentorder;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.DicItemBean;
import com.lulian.Zaiyunbao.Bean.MyOrderDetailsBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.ui.activity.ReserveRetireDetailsActivity;
import com.lulian.Zaiyunbao.ui.activity.pay.PayActivity;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.lulian.Zaiyunbao.di.component.Constants.isAutoRefresh;

/**
 * Created by Administrator on 2018/11/8.
 */

public class SeekRentOrderDetailsActivity extends BaseActivity {

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
    @BindView(R.id.my_order_no)
    TextView myOrderNo;
    @BindView(R.id.lease_status_text)
    TextView leaseStatusText;
    @BindView(R.id.my_order_img_photo)
    ImageView myOrderImgPhoto;
    @BindView(R.id.myOrder_Shebei_Name)
    TextView myOrderShebeiName;
    @BindView(R.id.my_order_shebei_spec)
    TextView myOrderShebeiSpec;
    @BindView(R.id.my_order_shebei_price)
    TextView myOrderShebeiPrice;
    @BindView(R.id.my_order_shebei_num_text)
    TextView myOrderShebeiNumText;
    @BindView(R.id.my_order_shebei_num)
    TextView myOrderShebeiNum;
    @BindView(R.id.my_order_take_time)
    TextView myOrderTakeTime;
    @BindView(R.id.my_order_company)
    TextView myOrderCompany;
    @BindView(R.id.my_order_contacts)
    TextView myOrderContacts;
    @BindView(R.id.my_order_phone)
    TextView myOrderPhone;
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
    @BindView(R.id.lease_info_linearLayout)
    LinearLayout leaseInfoLinearLayout;
    @BindView(R.id.my_order_TransferWay)
    TextView myOrderTransferWay;
    @BindView(R.id.shouhuo_dizhi_text)
    TextView shouhuoDizhiText;
    @BindView(R.id.my_order_address)
    TextView myOrderAddress;
    @BindView(R.id.my_order_contact_peisong)
    TextView myOrderContactPeisong;
    @BindView(R.id.my_order_lianxi_phone)
    TextView myOrderLianxiPhone;
    @BindView(R.id.lease_service_recommend)
    TextView leaseServiceRecommend;
    @BindView(R.id.my_order_submission_btn)
    Button myOrderSubmissionBtn;
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
    private List<MyOrderDetailsBean> myOrderDetailsList = new ArrayList<>();
    private MyOrderDetailsBean myOrderDetailsBean;

    @Override
    protected int setLayoutId() {
        return R.layout.seek_order_details;
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

        textDetailContent.setText("求租订单详情");
        textDetailRight.setVisibility(View.GONE);
        OrdersId = getIntent().getStringExtra("OrdersId");
        OrderNo = getIntent().getStringExtra("OrderNo");
        Id = getIntent().getStringExtra("Id");

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
//        myOrderContacts.setText(myOrderDetailsBean.getReceiveName());//企业联系人
//        myOrderPhone.setText(myOrderDetailsBean.getAlianceLinkPhone());//企业联系电话

        myOrderContacts.setText(myOrderDetailsBean.getContactName());
        myOrderPhone.setText(myOrderDetailsBean.getContactPhone());

        myOrderUnpaidDeposit.setText(myOrderDetailsBean.getOrderDeposit() + "元");//押金
        myOrderZulinSum.setText(myOrderDetailsBean.getCount() + "个");//租赁数量
        myOrderZulinPrice.setText(myOrderDetailsBean.getPrice() + "元/天/片");
        myOrderZulinDay.setText(myOrderDetailsBean.getRentDates() + "天");
        myOrderRentFree.setText(myOrderDetailsBean.getFreeDates() + "天");//免租
        myOrderRent.setText(myOrderDetailsBean.getRentAmount() + "元");//租金
        myOrderYunfei.setText(myOrderDetailsBean.getTrafficFee() + "元"); //运费

        //结算方式
        for (DicItemBean dicItemBean : GlobalParams.sDicItemBean) {
            if (dicItemBean.getDicTypeCode().equals("DT003") &&
                    Integer.valueOf(dicItemBean.getItemCode()) == myOrderDetailsBean.getHandRentWay()) {

                myOrderJiesuanMethod.setText(dicItemBean.getItemName());
            }
        }

//        if (myOrderDetailsBean.getHandRentWay() == 1) {//租金结算方式
//            myOrderJiesuanMethod.setText("按月预付");
//        } else if (myOrderDetailsBean.getHandRentWay() == 2) {
//            myOrderJiesuanMethod.setText("按季预付");
//        } else if (myOrderDetailsBean.getHandRentWay() == 3) {
//            myOrderJiesuanMethod.setText("按半年预付");
//        } else if (myOrderDetailsBean.getHandRentWay() == 4) {
//            myOrderJiesuanMethod.setText("按年预付");
//        } else if (myOrderDetailsBean.getHandRentWay() == 5) {
//            myOrderJiesuanMethod.setText("月结");
//        } else if (myOrderDetailsBean.getHandRentWay() == 6) {
//            myOrderJiesuanMethod.setText("季结");
//        }
        //送货方式
        for (DicItemBean dicItemBean : GlobalParams.sDicItemBean) {
            if (dicItemBean.getDicTypeCode().equals("DT002") &&
                    Integer.valueOf(dicItemBean.getItemCode()) == myOrderDetailsBean.getTransferWay()) {
                myOrderTransferWay.setText(dicItemBean.getItemName());
            }
        }

        if (myOrderTransferWay.getText().toString().trim().equals("送货上门")) {//送货方式
            shouhuoDizhiText.setText("收货地址");

            myOrderAddress.setText(myOrderDetailsBean.getTakeAddress());//收货地址
            myOrderContactPeisong.setText(myOrderDetailsBean.getContactName());//联系人
            myOrderLianxiPhone.setText(myOrderDetailsBean.getContactPhone());//联系电话

        } else if (myOrderTransferWay.getText().toString().trim().equals("用户自提")) {
            shouhuoDizhiText.setText("提货地址");
            myOrderAddress.setText(myOrderDetailsBean.getTakeAddress());//提货地址
            myOrderContactPeisong.setText(myOrderDetailsBean.getReceiveName());//接单联系人
            myOrderLianxiPhone.setText(myOrderDetailsBean.getAlianceLinkPhone());//接单联系电话
        }

        leaseServiceRecommend.setText(myOrderDetailsBean.getRecommend());//推荐人

        if (myOrderDetailsBean.getStatus() == 2) {
            leaseStatusText.setBackgroundResource(R.drawable.order_background);
        } else {
            leaseStatusText.setBackgroundResource(R.drawable.status_bj);
        }

        //未接单 = 0, 已接单 = 1,订单确认待支付 = 2,
        // 已支付待发货 = 3,已发货 = 5,已收货 = 6,退租中 = 7,已退租 = 8,已结算 = 9,已撤销 = 10
        if (myOrderDetailsBean.getStatus() == 0) {
            //未接单
//            myOrderSubmissionBtn.setVisibility(View.GONE);

            myOrderSubmissionLayout.setVisibility(View.GONE);
            myOrderBtnLayout.setVisibility(View.GONE);

//            leaseInfoLinearLayout.setVisibility(View.GONE);
            leaseStatusText.setText("未接单");

//        } else if (myOrderDetailsBean.getStatus() == 1) {
//            myOrderSubmissionLayout.setVisibility(View.GONE);
//            myOrderBtnLayout.setVisibility(View.VISIBLE);
//
//            leaseInfoLinearLayout.setVisibility(View.VISIBLE);
//            leaseStatusText.setText("待确认");

        } else if (myOrderDetailsBean.getStatus() == 2 || myOrderDetailsBean.getStatus() == 1) {
//            myOrderSubmissionBtn.setVisibility(View.VISIBLE);

            myOrderSubmissionLayout.setVisibility(View.GONE);
            myOrderBtnLayout.setVisibility(View.VISIBLE);
            myOrderSure.setText("支付押金");
            myOrderCancel.setText("撤销订单");

            leaseInfoLinearLayout.setVisibility(View.VISIBLE);
//            myOrderSubmissionBtn.setText("支付押金");
            leaseStatusText.setText("待支付");

        } else if (myOrderDetailsBean.getStatus() == 3) {
//            myOrderSubmissionBtn.setVisibility(View.GONE);
            myOrderSubmissionLayout.setVisibility(View.GONE);
            myOrderBtnLayout.setVisibility(View.GONE);
            leaseStatusText.setText("待发货");

        } else if (myOrderDetailsBean.getStatus() == 5) {
//            myOrderSubmissionBtn.setVisibility(View.VISIBLE);

            myOrderSubmissionLayout.setVisibility(View.VISIBLE);
            myOrderBtnLayout.setVisibility(View.GONE);

            leaseInfoLinearLayout.setVisibility(View.VISIBLE);
            myOrderSubmissionBtn.setText("确认收货");
            leaseStatusText.setText("已发货");

        } else if (myOrderDetailsBean.getStatus() == 6) {
//            myOrderSubmissionBtn.setVisibility(View.VISIBLE);
            myOrderSubmissionLayout.setVisibility(View.VISIBLE);
            myOrderBtnLayout.setVisibility(View.GONE);

            leaseInfoLinearLayout.setVisibility(View.VISIBLE);
            myOrderSubmissionBtn.setText("我要退租");
            leaseStatusText.setText("已收货");

        } else if (myOrderDetailsBean.getStatus() == 7) {
//            myOrderSubmissionBtn.setVisibility(View.GONE);
            myOrderSubmissionLayout.setVisibility(View.GONE);
            myOrderBtnLayout.setVisibility(View.GONE);

            leaseInfoLinearLayout.setVisibility(View.VISIBLE);
            leaseStatusText.setText("退租中");

        } else if (myOrderDetailsBean.getStatus() == 8) {
//            myOrderSubmissionBtn.setVisibility(View.GONE);
            myOrderSubmissionLayout.setVisibility(View.GONE);
            myOrderBtnLayout.setVisibility(View.GONE);

            leaseInfoLinearLayout.setVisibility(View.VISIBLE);
            leaseStatusText.setText("已退租");

        } else if (myOrderDetailsBean.getStatus() == 9) {
//            myOrderSubmissionBtn.setVisibility(View.GONE);

            myOrderSubmissionLayout.setVisibility(View.GONE);
            myOrderBtnLayout.setVisibility(View.GONE);
            leaseInfoLinearLayout.setVisibility(View.VISIBLE);
            leaseStatusText.setText("已结算");

        } else if (myOrderDetailsBean.getStatus() == 10) {
//            myOrderSubmissionBtn.setVisibility(View.GONE);

            myOrderSubmissionLayout.setVisibility(View.GONE);
            myOrderBtnLayout.setVisibility(View.GONE);

            leaseInfoLinearLayout.setVisibility(View.VISIBLE);
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
                    showDialog("确认是否收货", "SH");
                } else if (myOrderDetailsBean.getStatus() == 6) {
                    //我要退租
                    showDialog("确认是否预约退租", "TZ");
                }
            }
        });


        myOrderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("确认是否撤销订单", "Cancel");
            }
        });

        myOrderSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //支付押金
                Intent intent = new Intent(mContext, PayActivity.class);
                intent.putExtra("orderId", OrdersId);
                intent.putExtra("UserId", GlobalParams.sUserId);
                intent.putExtra("money", myOrderDetailsBean.getOrderDeposit());
                intent.putExtra("OrderType", 1); //1租赁单2购买单
                mContext.startActivity(intent);
            }
        });
    }

    private void showDialog(String message, final String type) {
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
                if (myOrderDetailsBean.getStatus() == 1) {
                    if (type.equals("Cancel")) {

                        //撤销订单
                        mApi.CancelOrder(GlobalParams.sToken, 1, OrdersId)
                                .compose(RxHttpResponseCompat.<String>compatResult())
                                .subscribe(new ErrorHandlerSubscriber<String>() {
                                    @Override
                                    public void onNext(String s) {
                                        RxToast.success("订单撤销成功");

                                        myOrderSubmissionLayout.setVisibility(View.GONE);
                                        myOrderBtnLayout.setVisibility(View.GONE);
                                        leaseInfoLinearLayout.setVisibility(View.VISIBLE);
                                        leaseStatusText.setText("已撤销");
                                        Constants.setIsAutoRefresh(true);
                                    }
                                });

                    }
                }

                if (myOrderDetailsBean.getStatus() == 5) {
                    //确认收货
                    Intent intent = new Intent(SeekRentOrderDetailsActivity.this, ReceiveSeekRentInfoActivity.class);
                    intent.putExtra("OrdersId", OrdersId);
                    startActivity(intent);
//                    finish();

                } else if (myOrderDetailsBean.getStatus() == 6) {
                    //我要退租
                    Intent intentReserve = new Intent(SeekRentOrderDetailsActivity.this, ReserveRetireDetailsActivity.class);
                    intentReserve.putExtra("OrdersId", OrdersId); //订单ID
                    intentReserve.putExtra("OrderNo", OrderNo); //订单号
                    intentReserve.putExtra("Id", Id); //设备ID

                    intentReserve.putExtra("AdapterPage", "SeekRentOrderAdapter");
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
            getData();
        }
    }
}
