package com.lulian.Zaiyunbao.ui.activity.myorder;

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
import com.lulian.Zaiyunbao.Bean.AreaBean;
import com.lulian.Zaiyunbao.Bean.DicItemBean;
import com.lulian.Zaiyunbao.Bean.MyOrderDetailsBean;
import com.lulian.Zaiyunbao.Bean.PersonalInfoBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.ui.activity.ReserveRetireDetailsActivity;
import com.lulian.Zaiyunbao.ui.activity.leaseorder.ReceiveLeaseInfoActivity;
import com.lulian.Zaiyunbao.ui.activity.pay.PayActivity;
import com.lulian.Zaiyunbao.ui.activity.subleaseorder.SubleaseOrderEntryActivity;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

import static com.lulian.Zaiyunbao.di.component.Constants.isAutoRefresh;

/**
 * Created by Administrator on 2018/11/8.
 */

public class MyOrderDetailsActivity extends BaseActivity {

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
    @BindView(R.id.my_order_take_time)
    TextView myOrderTakeTime;
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
    @BindView(R.id.my_order_lianxi_phone_text)
    TextView myOrderLianxiPhoneText;
    @BindView(R.id.my_order_lianxi_phone)
    TextView myOrderLianxiPhone;
    @BindView(R.id.songhuoren_text)
    TextView songhuorenText;
    @BindView(R.id.my_order_songhuo)
    TextView myOrderSonghuo;
    @BindView(R.id.songhuoren_relayout)
    RelativeLayout songhuorenRelayout;
    @BindView(R.id.songhuo_lianxi_phone_text)
    TextView songhuoLianxiPhoneText;
    @BindView(R.id.songhuo_lianxi_phone)
    TextView songhuoLianxiPhone;
    @BindView(R.id.songhuoren_lianxi_relayout)
    RelativeLayout songhuorenLianxiRelayout;
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

    @BindView(R.id.my_order_zulin_price_layout)
    RelativeLayout myOrderZulinPriceLayout;
    @BindView(R.id.my_order_Rent_free_layout)
    RelativeLayout myOrderRentFreeLayout;
    @BindView(R.id.my_order_rent_layout)
    RelativeLayout myOrderRentLayout;
    @BindView(R.id.my_order_yunfei_layout)
    RelativeLayout myOrderYunfeiLayout;

    private String OrdersId = "";
    private String OrderNo = "";
    private String Id = ""; //设备Id
    private int IsRendIn; //租入方
    private List<MyOrderDetailsBean> myOrderDetailsList = new ArrayList<>();
    private MyOrderDetailsBean myOrderDetailsBean;

    private String Area = ""; //地区
    private String Name = ""; //站点名
    private String ZZName = ""; //转租地址
    private String ZZContactName = ""; //转租联系人
    private String ZZContactPhone = ""; //转租联系电话
    private String ContactName = ""; //联系人
    private String ContactPhone = ""; //联系电话
    private List<AreaBean> areaBean = new ArrayList<>();
    private List<PersonalInfoBean> personalInfoBean = new ArrayList<>();

    private String ReceiveUserId;//租出方id
    private String StoreId;//仓库id

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

        textDetailContent.setText("订单详情");
        textDetailRight.setVisibility(View.GONE);
        OrdersId = getIntent().getStringExtra("OrdersId");
        OrderNo = getIntent().getStringExtra("OrderNo");
        Id = getIntent().getStringExtra("Id");
        IsRendIn = getIntent().getIntExtra("IsRendIn", 0);
        ReceiveUserId = getIntent().getStringExtra("ReceiveUserId");
        StoreId = getIntent().getStringExtra("StoreId");
        getData();
    }

    private void getDataInfo() {
        //根据仓库Id获取仓库与所属加盟商信息
//        if (myOrderDetailsBean.getFormType() != 3) {
//            mApi.getStorehouseInfo(GlobalParams.sToken, StoreId)
//                    .compose(RxHttpResponseCompat.<String>compatResult())
//                    .subscribe(new Consumer<String>() {
//                        @Override
//                        public void accept(String s) throws Exception {
//                            if (s.equals("[]")) {
////                                RxToast.warning("当前供应商资料不全");
//                            } else {
//                                areaBean = JSONObject.parseArray(s, AreaBean.class);
//                                Area = areaBean.get(0).getArea();
//                                Name = areaBean.get(0).getName();
//                                ContactName = areaBean.get(0).getContactName();
//                                ContactPhone = areaBean.get(0).getContactPhone();
//                            }
//                            initView();
//                        }
//                    });
//        } else {
//            mApi.GetPersonalInfo(GlobalParams.sToken, ReceiveUserId)
//                    .compose(RxHttpResponseCompat.<String>compatResult())
//                    .subscribe(new Consumer<String>() {
//                        @Override
//                        public void accept(String s) throws Exception {
//
//                            if (s.equals("[]")) {
////                                RxToast.warning("当前个人资料不全");
//                            } else {
//                                personalInfoBean = JSONObject.parseArray(s, PersonalInfoBean.class);
//                                ZZName = personalInfoBean.get(0).getContactAdress();
//                                ZZContactName = personalInfoBean.get(0).getName();
//                                ZZContactPhone = personalInfoBean.get(0).getPhone();
//                            }
//                            initView();
//                        }
//                    });

            ZZName = myOrderDetailsBean.getReceiveAddress();
            ZZContactName = myOrderDetailsBean.getRelease();
            ZZContactPhone = myOrderDetailsBean.getReleasePhone();
            initView();
//        }
    }

    private void getData() {
        mApi.myEquipmentRentOrderDetail(GlobalParams.sToken, OrdersId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        myOrderDetailsList = JSONObject.parseArray(s, MyOrderDetailsBean.class);
                        myOrderDetailsBean = myOrderDetailsList.get(0);
                        getDataInfo();
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
        myOrderContacts.setText(myOrderDetailsBean.getContactName());//企业联系人
        myOrderUnpaidDeposit.setText(myOrderDetailsBean.getOrderDeposit() + "元");//押金
        myOrderPhone.setText(myOrderDetailsBean.getContactPhone());//企业联系电话

        myOrderZulinSum.setText(myOrderDetailsBean.getCount() + "个");//租赁数量
        myOrderZulinPrice.setText(myOrderDetailsBean.getPrice() + "元/天/片");
        myOrderZulinDay.setText(myOrderDetailsBean.getRentDates() + "天");
        myOrderRentFree.setText(myOrderDetailsBean.getFreeDates() + "天");//免租
        myOrderRent.setText(myOrderDetailsBean.getRentAmount() + "元");//租金
        myOrderYunfei.setText(myOrderDetailsBean.getTrafficFee() + "元");//运费

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
            songhuorenRelayout.setVisibility(View.VISIBLE);
            songhuorenLianxiRelayout.setVisibility(View.VISIBLE);

            shouhuorenText.setText("收货人");
            shouhuoDizhiText.setText("收货地址");
            myOrderLianxiPhoneText.setText("收货人联系电话");

            if (myOrderDetailsBean.getFormType() == 3) { //转租单
                fuwuZhandian.setVisibility(View.GONE);
                myOrderServiceSite.setText("");//服务站点

                if (myOrderDetailsBean.getReceiveUserType() == 2){
                    myOrderSonghuo.setText(GlobalParams.sUserName);//联系人
                    songhuoLianxiPhone.setText(GlobalParams.sUserPhone);//联系电话
                } else {
                    myOrderSonghuo.setText(ZZContactName);//联系人
                    songhuoLianxiPhone.setText(ZZContactPhone);//联系电话
                }


            } else {
                fuwuZhandian.setVisibility(View.VISIBLE);
                myOrderServiceSite.setText(Name);//服务站点

                myOrderSonghuo.setText(ContactName);//联系人
                songhuoLianxiPhone.setText(ContactPhone);//联系电话
            }

//            myOrderSonghuo.setText(ZZContactName);//联系人
//            songhuoLianxiPhone.setText(ZZContactPhone);//联系电话

            myOrderConsignee.setText(myOrderDetailsBean.getContactName());//收货人
            myOrderAddress.setText(myOrderDetailsBean.getTakeAddress());//收货地址
            myOrderLianxiPhone.setText(myOrderDetailsBean.getContactPhone());//联系电话

        } else if (myOrderTransferWay.getText().toString().trim().equals("用户自提")) {
            songhuorenRelayout.setVisibility(View.GONE);
            songhuorenLianxiRelayout.setVisibility(View.GONE);
            fuwuZhandian.setVisibility(View.GONE);

            shouhuorenText.setText("取货联系人");
            shouhuoDizhiText.setText("提货地址");
            myOrderLianxiPhoneText.setText("取货联系电话");

            if (myOrderDetailsBean.getFormType() == 3) { //转租单

                if (myOrderDetailsBean.getReceiveUserType() == 1){ // 1、加盟商  2、用户
                    myOrderConsignee.setText(ZZContactName);//联系人
                    myOrderLianxiPhone.setText(ZZContactPhone);//联系电话
                } else {
                    myOrderConsignee.setText(GlobalParams.sUserName);//联系人
                    myOrderLianxiPhone.setText(GlobalParams.sUserPhone);//联系电话
                }

//                myOrderConsignee.setText(ZZContactName);//联系人
//                myOrderLianxiPhone.setText(ZZContactPhone);//联系电话
                myOrderAddress.setText(myOrderDetailsBean.getTakeAddress());//提货地址
            } else {
                myOrderConsignee.setText(ContactName);//联系人
                myOrderAddress.setText(myOrderDetailsBean.getTakeAddress());//提货地址
                myOrderLianxiPhone.setText(ContactPhone);//联系电话
            }

//            myOrderConsignee.setText(ZZContactName);//联系人
//            myOrderAddress.setText(myOrderDetailsBean.getTakeAddress());//提货地址
//            myOrderLianxiPhone.setText(ZZContactPhone);//联系电话
        }

        leaseServiceRecommend.setText(myOrderDetailsBean.getRecommend());//推荐人

        if (myOrderDetailsBean.getStatus() == 2) {
            leaseStatusText.setBackgroundResource(R.drawable.order_background);
        } else {
            leaseStatusText.setBackgroundResource(R.drawable.status_bj);
        }

        if (myOrderDetailsBean.getFormType() == 1) {
            if (myOrderDetailsBean.getStatus() == 0) {
                myOrderZulinPriceLayout.setVisibility(View.GONE);
                myOrderRentFreeLayout.setVisibility(View.GONE);
                myOrderRentLayout.setVisibility(View.GONE);
                myOrderYunfeiLayout.setVisibility(View.GONE);
            } else {
                myOrderZulinPriceLayout.setVisibility(View.VISIBLE);
                myOrderRentFreeLayout.setVisibility(View.VISIBLE);
                myOrderRentLayout.setVisibility(View.VISIBLE);
                myOrderYunfeiLayout.setVisibility(View.VISIBLE);
            }
        } else {
            myOrderZulinPriceLayout.setVisibility(View.VISIBLE);
            myOrderRentFreeLayout.setVisibility(View.VISIBLE);
            myOrderRentLayout.setVisibility(View.VISIBLE);
            myOrderYunfeiLayout.setVisibility(View.VISIBLE);
        }


        //未接单 = 0, 已接单 = 1,订单确认待支付 = 2,
        // 已支付待发货 = 3,已发货 = 5,已收货 = 6,退租中 = 7,已退租 = 8,已结算 = 9,已撤销 = 10
        if (myOrderDetailsBean.getFormType() == 3) { //转租
            myOrderShebeiNumText.setText("转租数量：");
            myOrderZulinSumText.setText("转租数量");
            if (myOrderDetailsBean.getStatus() == 1) {
                if (IsRendIn == 0) { //用户是否为租入方(IsRendIn 1是0否)
                    myOrderSubmissionLayout.setVisibility(View.GONE);
                    myOrderBtnLayout.setVisibility(View.VISIBLE);

//                    myOrderSubmissionBtn.setText("确认接单");
                    myOrderCancel.setText("撤销订单");
                    myOrderSure.setText("确认接单");


                    leaseStatusText.setText("待确认");
                } else {
                    myOrderSubmissionLayout.setVisibility(View.GONE);
                    myOrderBtnLayout.setVisibility(View.GONE);
                    leaseStatusText.setText("待确认");
                }

            } else if (myOrderDetailsBean.getStatus() == 2) {
                if (IsRendIn == 1) { //用户是否为租入方(IsRendIn 1是0否)
                    myOrderSubmissionLayout.setVisibility(View.GONE);
                    myOrderBtnLayout.setVisibility(View.VISIBLE);

                    myOrderCancel.setText("撤销订单");
                    myOrderSure.setText("支付押金");

//                    myOrderSubmissionBtn.setText("支付押金");
                    leaseStatusText.setText("待支付");
                } else {
                    myOrderSubmissionLayout.setVisibility(View.GONE);
                    myOrderBtnLayout.setVisibility(View.GONE);

                    leaseStatusText.setText("待支付");
                }

            } else if (myOrderDetailsBean.getStatus() == 3) {
                if (IsRendIn == 0) { //用户是否为租入方(IsRendIn 1是0否)
                    myOrderSubmissionLayout.setVisibility(View.VISIBLE);
                    myOrderBtnLayout.setVisibility(View.GONE);
                    myOrderSubmissionBtn.setText("确认发货");
                    leaseStatusText.setText("待发货");
                } else {
                    myOrderSubmissionLayout.setVisibility(View.GONE);
                    myOrderBtnLayout.setVisibility(View.GONE);
                    leaseStatusText.setText("待发货");
                }

            } else if (myOrderDetailsBean.getStatus() == 5) {
                if (IsRendIn == 1) {//用户是否为租入方(IsRendIn 1是0否)
                    myOrderSubmissionLayout.setVisibility(View.VISIBLE);
                    myOrderBtnLayout.setVisibility(View.GONE);
                    myOrderSubmissionBtn.setText("确认收货");
                    leaseStatusText.setText("已发货");
                } else {
                    myOrderSubmissionLayout.setVisibility(View.GONE);
                    myOrderBtnLayout.setVisibility(View.GONE);
                    leaseStatusText.setText("已发货");
                }

            } else if (myOrderDetailsBean.getStatus() == 6) {
                if (IsRendIn == 1) {//用户是否为租入方(IsRendIn 1是0否)
                    myOrderSubmissionLayout.setVisibility(View.VISIBLE);
                    myOrderBtnLayout.setVisibility(View.GONE);
                    myOrderSubmissionBtn.setText("我要退租");
                    leaseStatusText.setText("已收货");
                } else {
                    myOrderSubmissionLayout.setVisibility(View.GONE);
                    myOrderBtnLayout.setVisibility(View.GONE);
                    leaseStatusText.setText("已收货");
                }
            }

        } else if (myOrderDetailsBean.getFormType() == 2) { //租赁
            myOrderShebeiNumText.setText("租赁数量：");
            myOrderZulinSumText.setText("租赁数量");
            if (myOrderDetailsBean.getStatus() == 1) {
                myOrderBtnLayout.setVisibility(View.GONE);
                myOrderSubmissionLayout.setVisibility(View.GONE);
                leaseStatusText.setText("待确认");

            } else if (myOrderDetailsBean.getStatus() == 2) {
                myOrderSubmissionLayout.setVisibility(View.VISIBLE);
                myOrderBtnLayout.setVisibility(View.GONE);
                myOrderSubmissionBtn.setText("支付押金");
                leaseStatusText.setText("待支付");

            } else if (myOrderDetailsBean.getStatus() == 3) {
                myOrderSubmissionLayout.setVisibility(View.GONE);
                myOrderBtnLayout.setVisibility(View.GONE);
                leaseStatusText.setText("待发货");

            } else if (myOrderDetailsBean.getStatus() == 5) {
                myOrderSubmissionLayout.setVisibility(View.VISIBLE);
                myOrderBtnLayout.setVisibility(View.GONE);
                myOrderSubmissionBtn.setText("确认收货");
                leaseStatusText.setText("已发货");

            } else if (myOrderDetailsBean.getStatus() == 6) {
                myOrderSubmissionLayout.setVisibility(View.VISIBLE);
                myOrderBtnLayout.setVisibility(View.GONE);
                myOrderSubmissionBtn.setText("我要退租");
                leaseStatusText.setText("已收货");
            }

        } else if (myOrderDetailsBean.getFormType() == 1) { //求租
            myOrderShebeiNumText.setText("求租数量：");
            myOrderZulinSumText.setText("求租数量");


            if (myOrderDetailsBean.getStatus() == 0) {
                myOrderSubmissionLayout.setVisibility(View.GONE);
                myOrderBtnLayout.setVisibility(View.GONE);
                leaseStatusText.setText("待接单");

            } else if (myOrderDetailsBean.getStatus() == 2 || myOrderDetailsBean.getStatus() == 1) {
                myOrderSubmissionLayout.setVisibility(View.GONE);
                myOrderBtnLayout.setVisibility(View.VISIBLE);
                leaseStatusText.setText("待支付");
                myOrderCancel.setText("撤销订单");
                myOrderSure.setText("支付押金");

            } else if (myOrderDetailsBean.getStatus() == 3) {
                myOrderSubmissionLayout.setVisibility(View.GONE);
                myOrderBtnLayout.setVisibility(View.GONE);
                leaseStatusText.setText("待发货");

            } else if (myOrderDetailsBean.getStatus() == 5) {
                myOrderSubmissionLayout.setVisibility(View.VISIBLE);
                myOrderBtnLayout.setVisibility(View.GONE);
                myOrderSubmissionBtn.setText("确认收货");
                leaseStatusText.setText("已发货");

            } else if (myOrderDetailsBean.getStatus() == 6) {
                myOrderSubmissionLayout.setVisibility(View.VISIBLE);
                myOrderBtnLayout.setVisibility(View.GONE);
                myOrderSubmissionBtn.setText("我要退租");
                leaseStatusText.setText("已收货");
            }
        }

        if (myOrderDetailsBean.getStatus() == 7) {
            myOrderSubmissionLayout.setVisibility(View.GONE);
            myOrderBtnLayout.setVisibility(View.GONE);
            leaseStatusText.setText("退租中");

        } else if (myOrderDetailsBean.getStatus() == 8) {
            myOrderSubmissionLayout.setVisibility(View.GONE);
            myOrderBtnLayout.setVisibility(View.GONE);
            leaseStatusText.setText("已退租");

        } else if (myOrderDetailsBean.getStatus() == 9) {
            myOrderSubmissionLayout.setVisibility(View.GONE);
            myOrderBtnLayout.setVisibility(View.GONE);
            leaseStatusText.setText("已结算");

        } else if (myOrderDetailsBean.getStatus() == 10) {
            myOrderSubmissionLayout.setVisibility(View.GONE);
            myOrderBtnLayout.setVisibility(View.GONE);
            leaseStatusText.setText("已撤销");
        }

        myOrderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("是否撤销订单", "Cancel");
            }
        });

        myOrderSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myOrderDetailsBean.getFormType() == 3) { //转租
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

                            showDialog("是否确认接单", "Sure");

                        }
                    }

                } else if (myOrderDetailsBean.getFormType() == 1) { //求租
                    if (myOrderDetailsBean.getStatus() == 1 || myOrderDetailsBean.getStatus() == 2) {
                        //支付押金
                        Intent intent = new Intent(mContext, PayActivity.class);
                        intent.putExtra("orderId", OrdersId);
                        intent.putExtra("UserId", GlobalParams.sUserId);
                        intent.putExtra("money", myOrderDetailsBean.getOrderDeposit());
                        intent.putExtra("OrderType", 1); //1租赁单2购买单
                        mContext.startActivity(intent);
                    }
                }
            }
        });

        myOrderSubmissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myOrderDetailsBean.getStatus() == 1) {
                    //待接单
//                    if (myOrderDetailsBean.getFormType() == 3) {
//                        showDialog("确认是否接单");
//                    }

                } else if (myOrderDetailsBean.getStatus() == 2) {
                    //支付押金
                    Intent intent = new Intent(mContext, PayActivity.class);
                    intent.putExtra("orderId", OrdersId);
                    intent.putExtra("UserId", GlobalParams.sUserId);
                    intent.putExtra("money", myOrderDetailsBean.getOrderDeposit());
                    intent.putExtra("OrderType", 1); //1租赁单2购买单
                    mContext.startActivity(intent);

//                    finish();

                } else if (myOrderDetailsBean.getStatus() == 3) {
                    //确认发货
                    showDialog("确认是否发货", "FH");

                } else if (myOrderDetailsBean.getStatus() == 5) {
                    //确认收货
                    showDialog("确认是否收货", "SH");

                } else if (myOrderDetailsBean.getStatus() == 6) {
                    //我要退租
                    showDialog("确认是否预约退租", "TZ");
                }
            }
        });
    }

    private void showDialog(String message, final String Type) {
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
                if (Type.equals("Sure")) {
                    //确认接单
                    mApi.updateOrderStatus(GlobalParams.sToken, 1, OrdersId, 2)
                            .compose(RxHttpResponseCompat.<String>compatResult())
                            .subscribe(new ErrorHandlerSubscriber<String>() {
                                @Override
                                public void onNext(String s) {
                                    RxToast.success("订单接单成功");

                                    myOrderSubmissionLayout.setVisibility(View.GONE);
                                    myOrderBtnLayout.setVisibility(View.GONE);
                                    leaseStatusText.setText("待支付");
                                    leaseStatusText.setBackgroundResource(R.drawable.order_background);
                                    Constants.setIsAutoRefresh(true);
                                }
                            });
                } else if (Type.equals("Cancel")) {
                    if (myOrderDetailsBean.getFormType() == 3) {
                        if (myOrderDetailsBean.getStatus() == 2) {
                            if (IsRendIn == 1) {//用户是否为租入方(IsRendIn 1是0否)
                                //撤销订单
                                mApi.CancelOrder(GlobalParams.sToken, 1, OrdersId)
                                        .compose(RxHttpResponseCompat.<String>compatResult())
                                        .subscribe(new ErrorHandlerSubscriber<String>() {
                                            @Override
                                            public void onNext(String s) {
                                                RxToast.success("订单撤销成功");

                                                myOrderSubmissionLayout.setVisibility(View.GONE);
                                                myOrderBtnLayout.setVisibility(View.GONE);
                                                leaseStatusText.setText("已撤销");
                                                Constants.setIsAutoRefresh(true);
                                                leaseStatusText.setBackgroundResource(R.drawable.status_bj);
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

                                                myOrderSubmissionLayout.setVisibility(View.GONE);
                                                myOrderBtnLayout.setVisibility(View.GONE);
                                                leaseStatusText.setText("已撤销");
                                                Constants.setIsAutoRefresh(true);
                                            }
                                        });
                            }
                        }
                    } else if (myOrderDetailsBean.getFormType() == 1) {
                        if (myOrderDetailsBean.getStatus() == 1 || myOrderDetailsBean.getStatus() == 2) {
                            //撤销订单
                            mApi.CancelOrder(GlobalParams.sToken, 1, OrdersId)
                                    .compose(RxHttpResponseCompat.<String>compatResult())
                                    .subscribe(new ErrorHandlerSubscriber<String>() {
                                        @Override
                                        public void onNext(String s) {
                                            RxToast.success("订单撤销成功");

                                            myOrderSubmissionLayout.setVisibility(View.GONE);
                                            myOrderBtnLayout.setVisibility(View.GONE);
                                            leaseStatusText.setText("已撤销");
                                            Constants.setIsAutoRefresh(true);
                                        }
                                    });
                        }
                    }
                } else {

                    if (myOrderDetailsBean.getStatus() == 3) {
                        //确认发货
                        if (myOrderDetailsBean.getFormType() == 3) {
                            //转租发货逻辑
                            Intent intent = new Intent(mContext, SubleaseOrderEntryActivity.class);
                            intent.putExtra("EquipmentName", myOrderDetailsBean.getEquipmentName());
                            intent.putExtra("EquipmentNorm", myOrderDetailsBean.getNorm());
                            intent.putExtra("OrderId", OrdersId);
                            intent.putExtra("Count", myOrderDetailsBean.getCount());
                            intent.putExtra("EquipmentId", Id);

                            intent.putExtra("AdapterPage", "MyOrderAdapter");
                            mContext.startActivity(intent);
//                        finish();
                        }

                    } else if (myOrderDetailsBean.getStatus() == 5) {
                        //确认收货
                        Intent intent = new Intent(MyOrderDetailsActivity.this, ReceiveLeaseInfoActivity.class);
                        intent.putExtra("OrdersId", OrdersId);

                        intent.putExtra("AdapterPage", "MyOrderAdapter");
                        startActivity(intent);
//                    finish();

                    } else if (myOrderDetailsBean.getStatus() == 6) {
                        //我要退租
                        Intent intentReserve = new Intent(MyOrderDetailsActivity.this, ReserveRetireDetailsActivity.class);
                        intentReserve.putExtra("OrdersId", OrdersId);
                        intentReserve.putExtra("OrderNo", OrderNo);
                        intentReserve.putExtra("Id", Id); //设备Id
                        intentReserve.putExtra("AdapterPage", "MyOrderAdapter");
                        startActivity(intentReserve);

                    }
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
