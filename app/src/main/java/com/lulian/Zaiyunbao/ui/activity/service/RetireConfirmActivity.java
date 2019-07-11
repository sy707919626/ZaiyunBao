package com.lulian.Zaiyunbao.ui.activity.service;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/11/20.
 */

public class RetireConfirmActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.retire_confirm_see_map)
    Button retireConfirmSeeMap;
    @BindView(R.id.retire_service_site)
    TextView retireServiceSite;
    @BindView(R.id.retire_confirm_site_address)
    TextView retireConfirmSiteAddress;
    @BindView(R.id.retire_confirm_site_distance)
    TextView retireConfirmSiteDistance;
    @BindView(R.id.retire_confirm_dateTime)
    TextView retireConfirmDateTime;
    @BindView(R.id.retire_confirm_Mode)
    TextView retireConfirmMode;
    @BindView(R.id.retire_confirm_typeName)
    TextView retireConfirmTypeName;
    @BindView(R.id.retire_confirm_shebei_sum)
    TextView retireConfirmShebeiSum;
    @BindView(R.id.retire_confirm_lixi_name)
    TextView retireConfirmLixiName;
    @BindView(R.id.retire_confirm_phone)
    TextView retireConfirmPhone;
    @BindView(R.id.retire_btn)
    Button retireBtn;
    @BindView(R.id.retire_confirm_modle_spinner)
    TextView mRetireConfirmModleSpinner;
    private int Quantity;
    private int Count;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_retire_confirm;
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

        textDetailContent.setText("退租确定");
        textDetailRight.setText("完成");

        Quantity = getIntent().getIntExtra("Quantity", 0);
        Count = getIntent().getIntExtra("Count", 0);
        retireConfirmSeeMap.setVisibility(View.GONE);

        initView();
    }

    private void initView() {
        retireConfirmSiteAddress.setText(getIntent().getStringExtra("TakeAddress"));
        retireServiceSite.setText(getIntent().getStringExtra("StoreName"));
        retireConfirmSiteDistance.setText("距我约15KM");
        retireConfirmDateTime.setText(getIntent().getStringExtra("TargetDeliveryTime"));
        retireConfirmMode.setText(getIntent().getStringExtra("ReserveMode"));
        retireConfirmTypeName.setText(getIntent().getStringExtra("ETypeName"));
        retireConfirmShebeiSum.setText(Count + "个");
        retireConfirmLixiName.setText(getIntent().getStringExtra("BackLink"));
        retireConfirmPhone.setText(getIntent().getStringExtra("BackLinkPhone"));

        mRetireConfirmModleSpinner.setText(getIntent().getStringExtra("ModleSpinner"));
    }

    @OnClick({R.id.retire_btn, R.id.text_detail_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.retire_btn:
                //取消预约
                finish();
                break;

            case R.id.text_detail_right:
                //完成
                upadteData();
                finish();
                break;

            case R.id.retire_confirm_see_map:
                //查看地图
                break;

            default:
                break;
        }
    }

    private void upadteData() {
        JSONObject obj = new JSONObject();
//        obj.put("RentOrderID", getIntent().getStringExtra("OrdersId"));
        obj.put("TargetDeliveryTime", getIntent().getStringExtra("TargetDeliveryTime"));
        obj.put("StoreId", getIntent().getStringExtra("StoreId"));
        obj.put("ETypeId", getIntent().getStringExtra("ETypeId"));
        obj.put("ETypeName", getIntent().getStringExtra("ETypeName"));
        obj.put("Count", Count); //实际退租数量
        obj.put("BackLink", getIntent().getStringExtra("BackLink"));
        obj.put("BackLinkPhone", getIntent().getStringExtra("BackLinkPhone"));

        obj.put("BelongMember", getIntent().getStringExtra("BelongMember"));
        obj.put("CreateUserId", GlobalParams.sUserId);
        obj.put("CanRentCount", Quantity); //可退租数量

        if (mRetireConfirmModleSpinner.getText().toString().equals("分次租赁")) {
            obj.put("RentWay", 2);
        } else if (mRetireConfirmModleSpinner.getText().toString().equals("分时租赁")) {
            obj.put("RentWay", 1);
        }

        String args = obj.toString();
        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                args);
        mApi.equipmentBackRent(GlobalParams.sToken, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        RxToast.success("预约退租成功");
                    }
                });
    }
}
