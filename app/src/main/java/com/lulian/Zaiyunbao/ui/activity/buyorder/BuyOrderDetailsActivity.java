package com.lulian.Zaiyunbao.ui.activity.buyorder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.BuyOrderDetailBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.activity.pay.PayActivity;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.lulian.Zaiyunbao.di.component.Constants.isAutoRefresh;

/**
 * Created by Administrator on 2018/11/8.
 */

public class BuyOrderDetailsActivity extends BaseActivity {

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
    @BindView(R.id.buy_order_no)
    TextView buyOrderNo;
    @BindView(R.id.buy_order_state)
    TextView buyOrderState;
    @BindView(R.id.buy_img_photo)
    ImageView buyImgPhoto;
    @BindView(R.id.buy_shebei_name)
    TextView buyShebeiName;
    @BindView(R.id.buy_shebei_spec)
    TextView buyShebeiSpec;
    @BindView(R.id.buy_order_price)
    TextView buyOrderPrice;
    @BindView(R.id.buy_order_sum)
    TextView buyOrderSum;
    @BindView(R.id.buy_order_qutuo_time)
    TextView buyOrderQutuoTime;
    @BindView(R.id.buy_order_ContactName)
    TextView buyOrderContactName;
    @BindView(R.id.buy_order_ContactPhone)
    TextView buyOrderContactPhone;
    @BindView(R.id.buy_order_Advance)
    TextView buyOrderAdvance;
    @BindView(R.id.buy_order_Count)
    TextView buyOrderCount;
    @BindView(R.id.buy_order_goumai_Price)
    TextView buyOrderGoumaiPrice;
    @BindView(R.id.buy_order_Amount)
    TextView buyOrderAmount;
    @BindView(R.id.buy_order_transferWay)
    TextView buyOrderTransferWay;
    @BindView(R.id.buy_order_SupplierContactName)
    TextView buyOrderSupplierContactName;
    @BindView(R.id.buy_order_SupplierContactPhone)
    TextView buyOrderSupplierContactPhone;
    @BindView(R.id.buy_order_address_text)
    TextView buyOrderAddressText;
    @BindView(R.id.buy_order_address)
    TextView buyOrderAddress;
    @BindView(R.id.buy_order_submission_btn)
    Button buyOrderSubmissionBtn;
    private String OrdersId = "";
    private String OrderNo = "";

    private List<BuyOrderDetailBean> myOrderDetailsList = new ArrayList<>();
    private BuyOrderDetailBean myOrderDetailsBean;

    @Override
    protected int setLayoutId() {
        return R.layout.buyorder_my_details;
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

        getData();
    }

    private void getData() {
        mApi.MyEquipmentBuyOrderDetail(GlobalParams.sToken, OrdersId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        myOrderDetailsList = JSONObject.parseArray(s, BuyOrderDetailBean.class);
                        myOrderDetailsBean = myOrderDetailsList.get(0);
                        initView();
                    }
                });
    }

    private void initView() {
        buyOrderNo.setText(OrderNo);
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(myOrderDetailsBean.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            buyImgPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        buyShebeiName.setText(myOrderDetailsBean.getEquipmentName());
        buyShebeiSpec.setText(myOrderDetailsBean.getNorm());
        buyOrderPrice.setText(myOrderDetailsBean.getPrice() + "");
        buyOrderSum.setText(myOrderDetailsBean.getCount() + "");
        buyOrderQutuoTime.setText(myOrderDetailsBean.getTargetDeliveryTime());//取托时间
        buyOrderContactName.setText(myOrderDetailsBean.getContactName()); //联系人
        buyOrderContactPhone.setText(myOrderDetailsBean.getContactPhone());//联系电话
        buyOrderAdvance.setText(myOrderDetailsBean.getAdvance() + "元");//押金
        buyOrderCount.setText(myOrderDetailsBean.getCount() + "个"); //购买数量
        buyOrderAmount.setText(myOrderDetailsBean.getAmount() + "元");//总金额

        buyOrderTransferWay.setText(myOrderDetailsBean.getTransferWay());
        buyOrderSupplierContactName.setText(myOrderDetailsBean.getSupplierContactName());
        buyOrderSupplierContactPhone.setText(myOrderDetailsBean.getSupplierContactPhone());

        buyOrderAddress.setText(myOrderDetailsBean.getReceiveAddress());
        buyOrderGoumaiPrice.setText(myOrderDetailsBean.getPrice() + "元/个/片");


        if (myOrderDetailsBean.getOrderStatus() == 2) {
            buyOrderState.setBackgroundResource(R.drawable.order_background);
        } else {
            buyOrderState.setBackgroundResource(R.drawable.status_bj);
        }

        //未接单 = 0, 已接单 = 1,订单确认待支付 = 2,
        // 已支付待发货 = 3,已发货 = 5,已收货 = 6,退租中 = 7,已退租 = 8,已结算 = 9,已撤销 = 10

        if (myOrderDetailsBean.getOrderStatus() == 0) {
            //未接单
            buyOrderState.setText("待接单");
            buyOrderSubmissionBtn.setVisibility(View.GONE);

        } else if (myOrderDetailsBean.getOrderStatus() == 1) {
            //未接单
            buyOrderState.setText("待确认");
            buyOrderSubmissionBtn.setVisibility(View.GONE);

        } else if (myOrderDetailsBean.getOrderStatus() == 2) {
            buyOrderState.setText("待支付");
            buyOrderSubmissionBtn.setVisibility(View.VISIBLE);
            buyOrderSubmissionBtn.setText("确认支付");

        } else if (myOrderDetailsBean.getOrderStatus() == 3) {
            buyOrderState.setText("待发货");
            buyOrderSubmissionBtn.setVisibility(View.GONE);

        } else if (myOrderDetailsBean.getOrderStatus() == 5) {
            buyOrderState.setText("待收货");
            buyOrderSubmissionBtn.setVisibility(View.VISIBLE);
            buyOrderSubmissionBtn.setText("确认收货");

        } else if (myOrderDetailsBean.getOrderStatus() == 6) {
            buyOrderState.setText("已收货");
            buyOrderSubmissionBtn.setVisibility(View.GONE);
//            buyOrderSubmissionBtn.setText("回购返租");


        } else if (myOrderDetailsBean.getOrderStatus() == 7) {
            buyOrderState.setText("退租中");
            buyOrderSubmissionBtn.setVisibility(View.GONE);

        } else if (myOrderDetailsBean.getOrderStatus() == 8) {
            buyOrderState.setText("已退租");
            buyOrderSubmissionBtn.setVisibility(View.GONE);


        } else if (myOrderDetailsBean.getOrderStatus() == 9) {
            buyOrderState.setText("已结算");
            buyOrderSubmissionBtn.setVisibility(View.GONE);

        } else if (myOrderDetailsBean.getOrderStatus() == 10) {
            buyOrderState.setText("已撤销");
            buyOrderSubmissionBtn.setVisibility(View.GONE);
        }

        //未接单 = 0, 已接单 = 1,订单确认待支付 = 2,
        // 已支付待发货 = 3,已发货 = 5,已收货 = 6,退租中 = 7,已退租 = 8,已结算 = 9,已撤销 = 10
        buyOrderSubmissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myOrderDetailsBean.getOrderStatus() == 2) {
                    //支付押金
                    Intent intent = new Intent(BuyOrderDetailsActivity.this, PayActivity.class);
                    intent.putExtra("orderId", OrdersId);
                    intent.putExtra("UserId", GlobalParams.sUserId);

                    if (myOrderDetailsBean.getAdvance() == 0) {
                        intent.putExtra("money", myOrderDetailsBean.getAmount());
                    } else {
                        intent.putExtra("money", Float.valueOf(myOrderDetailsBean.getAdvance()));
                    }

                    intent.putExtra("OrderType", 2); //1租赁单2购买单
                    startActivity(intent);

                } else if (myOrderDetailsBean.getOrderStatus() == 5) {
                    Intent intent = new Intent(BuyOrderDetailsActivity.this, BuyOrderInfoActivity.class);
                    intent.putExtra("OrdersId", OrdersId);
                    startActivity(intent);

                } else if (myOrderDetailsBean.getOrderStatus() == 6) {


                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isAutoRefresh) {
//            if (myOrderDetailsBean.getOrderStatus() == 2) {
//                buyOrderState.setText("待发货");
//                buyOrderSubmissionBtn.setVisibility(View.GONE);
//
//            } else if (myOrderDetailsBean.getOrderStatus() == 5) {
//
//                buyOrderState.setText("已收货");
//                buyOrderSubmissionBtn.setVisibility(View.VISIBLE);
//                buyOrderSubmissionBtn.setText("回购返祖");
//
//            } else if (myOrderDetailsBean.getOrderStatus() == 6) {
//                buyOrderState.setText("退租中");
//                buyOrderSubmissionBtn.setVisibility(View.GONE);
//            }
            getData();
        }
    }
}
