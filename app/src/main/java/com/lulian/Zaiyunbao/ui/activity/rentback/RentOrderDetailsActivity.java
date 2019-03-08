package com.lulian.Zaiyunbao.ui.activity.rentback;

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

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.RentOrderDetailBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompatTest;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lulian.Zaiyunbao.di.component.Constants.isAutoRefresh;

/**
 * Created by Administrator on 2018/11/8.
 */

public class RentOrderDetailsActivity extends BaseActivity {


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
    @BindView(R.id.my_order_TransferWay)
    TextView myOrderTransferWay;
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
    @BindView(R.id.my_order_submission_btn)
    Button myOrderSubmissionBtn;
    @BindView(R.id.my_order_submission_layout)
    LinearLayout myOrderSubmissionLayout;
    private String OrdersId = "";
    private String OrderNo = "";
    private List<RentOrderDetailBean> mOrderDetailsList = new ArrayList<>();
    private RentOrderDetailBean mRentOrderDetailBean;

    @Override
    protected int setLayoutId() {
        return R.layout.rent_order_my_details;
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

        textDetailContent.setText("退租订单详情");
        textDetailRight.setVisibility(View.GONE);
        OrdersId = getIntent().getStringExtra("OrdersId");
        OrderNo = getIntent().getStringExtra("OrderNo");

        getData();
    }

    private void getData() {
        mApi.EquipmentBackRentOrderDetail(GlobalParams.sToken, OrdersId)
                .compose(RxHttpResponseCompatTest.<List<RentOrderDetailBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<RentOrderDetailBean>>() {
                    @Override
                    public void onNext(List<RentOrderDetailBean> s) {
                        mOrderDetailsList = s;
                        mRentOrderDetailBean = s.get(0);
                        initView();
                    }
                });
    }

    private void initView() {
        myOrderNo.setText(OrderNo);
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(mRentOrderDetailBean.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            myOrderImgPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        if (mRentOrderDetailBean.getTransferWay() == 1){
            myOrderTransferWay.setText("送货上门");
        } else {
            myOrderTransferWay.setText("物流运输");
        }

        myOrderShebeiName.setText(mRentOrderDetailBean.getEquipmentName());
        myOrderShebeiSpec.setText(mRentOrderDetailBean.getNorm());
        myOrderShebeiPrice.setText(mRentOrderDetailBean.getPrice() + "");
        myOrderShebeiNum.setText(mRentOrderDetailBean.getCount() + "");

        myOrderTakeTime.setText(mRentOrderDetailBean.getRentTime());//退租时间

        myOrderContacts.setText(GlobalParams.sUserName);
        myOrderPhone.setText(GlobalParams.sUserPhone);

        myOrderConsignee.setText(mRentOrderDetailBean.getBackLink());

        myOrderAddress.setText(mRentOrderDetailBean.getReceiveAddress());
        myOrderLianxiPhone.setText(mRentOrderDetailBean.getBackLinkPhone());


        // 新建 = 0,已接单 = 1,已发货 = 3,已收货 = 4, 已完成 = 5
        if (mRentOrderDetailBean.getStatus() == 0) {
            myOrderSubmissionLayout.setVisibility(View.GONE);
            leaseStatusText.setText("未接单");

        } else if (mRentOrderDetailBean.getStatus() == 2 || mRentOrderDetailBean.getStatus() == 1) {

            myOrderSubmissionLayout.setVisibility(View.VISIBLE);
            myOrderSubmissionBtn.setText("确认发货");
            leaseStatusText.setText("待发货");

        } else if (mRentOrderDetailBean.getStatus() == 3) {
            myOrderSubmissionLayout.setVisibility(View.GONE);
            leaseStatusText.setText("已发货");

        } else if (mRentOrderDetailBean.getStatus() == 4) {
            myOrderSubmissionLayout.setVisibility(View.GONE);
            leaseStatusText.setText("已收货");

        } else if (mRentOrderDetailBean.getStatus() == 5) {
            myOrderSubmissionLayout.setVisibility(View.GONE);
            leaseStatusText.setText("已完成");
        }

        myOrderSubmissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发货
                Intent intent = new Intent(mContext, RentBackEntryActivity.class);
                intent.putExtra("EquipmentName", mRentOrderDetailBean.getEquipmentName());
                intent.putExtra("EquipmentNorm", mRentOrderDetailBean.getNorm());
                intent.putExtra("OrderId", OrdersId);
                intent.putExtra("Count", mRentOrderDetailBean.getCount());
                intent.putExtra("Id", mRentOrderDetailBean.getId());
                mContext.startActivity(intent);
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
