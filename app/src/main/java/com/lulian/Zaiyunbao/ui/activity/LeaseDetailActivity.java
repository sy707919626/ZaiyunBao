package com.lulian.Zaiyunbao.ui.activity;

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
import com.lulian.Zaiyunbao.Bean.EquipmentDetailBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/10/22.
 */
public class LeaseDetailActivity extends BaseActivity {
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.leaseDetails_img_photo)
    ImageView leaseDetailsImgPhoto;
    @BindView(R.id.leaseDetails_shebei_name)
    TextView leaseDetailsShebeiName;
    @BindView(R.id.leaseDetails_shebei_spec)
    TextView leaseDetailsShebeiSpec;
    @BindView(R.id.leaseDetails_shebei_load)
    TextView leaseDetailsShebeiLoad;
    @BindView(R.id.leaseDetails_shebei_price)
    TextView leaseDetailsShebeiPrice;
    @BindView(R.id.leaseDetails_shebei_num)
    TextView leaseDetailsShebeiNum;
    @BindView(R.id.image_distance)
    ImageView imageDistance;
    @BindView(R.id.leaseDetailsdistance)
    TextView leaseDetailsdistance;
    @BindView(R.id.leaseDetails_read_details)
    Button leaseDetailsReadDetails;
    @BindView(R.id.leaseDetails_service_site)
    TextView leaseDetailsServiceSite;
    @BindView(R.id.leaseDetails_contacts)
    TextView leaseDetailsContacts;
    @BindView(R.id.leaseDetails_phone)
    TextView leaseDetailsPhone;
    @BindView(R.id.my_leaseDetails_btn)
    Button myLeaseDetailsBtn;

    private String ShebeiId;
    private String OperatorId;
    private String UID;
    private List<EquipmentDetailBean> equipmentDetailBean = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.lease_order_details;
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

        ShebeiId = getIntent().getStringExtra("ShebeiId");
        OperatorId = getIntent().getStringExtra("OperatorId"); //供应商Id
        UID = getIntent().getStringExtra("UID"); //使用者Id
        textDetailContent.setText("设备信息");
        textDetailRight.setVisibility(View.GONE);

        if (UID != null) {
            if (UID.equals(GlobalParams.sUserId)) {
                myLeaseDetailsBtn.setVisibility(View.GONE);
            } else {
                myLeaseDetailsBtn.setVisibility(View.VISIBLE);
            }
        } else {
            myLeaseDetailsBtn.setVisibility(View.VISIBLE);
        }

        getData();
    }

    private void initView() {
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(equipmentDetailBean.get(0).getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            leaseDetailsImgPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        leaseDetailsShebeiName.setText(equipmentDetailBean.get(0).getEquipmentName());
        leaseDetailsShebeiSpec.setText(equipmentDetailBean.get(0).getNorm());

        if (equipmentDetailBean.get(0).getTypeName().equals("托盘")) {
            leaseDetailsShebeiLoad.setText("静载" + String.valueOf(equipmentDetailBean.get(0).getStaticLoad()) + "T；动载"
                    + String.valueOf(equipmentDetailBean.get(0).getCarryingLoad()) + "T；架载" + String.valueOf(equipmentDetailBean.get(0).getOnLoad()) + "T");
        } else if (equipmentDetailBean.get(0).getTypeName().equals("保温箱")) {
            leaseDetailsShebeiLoad.setText("容积" + equipmentDetailBean.get(0).getVolume() + "升；保温时长"
                    + equipmentDetailBean.get(0).getWarmLong() + "小时");
        } else if (equipmentDetailBean.get(0).getTypeName().equals("周转篱")) {
            leaseDetailsShebeiLoad.setText("容积" + equipmentDetailBean.get(0).getVolume() + "升；载重"
                    + equipmentDetailBean.get(0).getSpecifiedLoad() + "公斤");
        }

        leaseDetailsShebeiPrice.setText(String.valueOf(equipmentDetailBean.get(0).getPrice()) + "");
        leaseDetailsShebeiNum.setText(String.valueOf(equipmentDetailBean.get(0).getQuantity()) + "");

        leaseDetailsServiceSite.setText(equipmentDetailBean.get(0).getArea());
        leaseDetailsContacts.setText(equipmentDetailBean.get(0).getName());
        leaseDetailsPhone.setText(equipmentDetailBean.get(0).getPhone());
    }


    private void getData() {
        mApi.equipmentDetails1(GlobalParams.sToken, ShebeiId, OperatorId, UID)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        equipmentDetailBean = JSONObject.parseArray(s, EquipmentDetailBean.class);
                        initView();
                    }
                });
    }


    @OnClick({R.id.leaseDetails_read_details, R.id.my_leaseDetails_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.leaseDetails_read_details:
                Intent intent = new Intent(this, LeaseEquipmentDetailActivity.class);
                intent.putExtra("ShebeiId", ShebeiId);
                intent.putExtra("OperatorId", OperatorId); //供应商Id
                intent.putExtra("StorehouseId", getIntent().getStringExtra("StorehouseId")); //仓库ID
                intent.putExtra("UID", UID); //使用者Id
                startActivity(intent);

                break;

            case R.id.my_leaseDetails_btn:
                Intent intentMy = new Intent(this, LeaseMyEquipmentActivity.class);
                intentMy.putExtra("EquipmentName", equipmentDetailBean.get(0).getEquipmentName());
                intentMy.putExtra("Norm", equipmentDetailBean.get(0).getNorm());
                intentMy.putExtra("Price", equipmentDetailBean.get(0).getPrice());
                intentMy.putExtra("Quantity", equipmentDetailBean.get(0).getQuantity());
                intentMy.putExtra("StaticLoad", equipmentDetailBean.get(0).getStaticLoad());
                intentMy.putExtra("CarryingLoad", equipmentDetailBean.get(0).getCarryingLoad());
                intentMy.putExtra("OnLoad", equipmentDetailBean.get(0).getOnLoad());
                intentMy.putExtra("Volume", equipmentDetailBean.get(0).getVolume());
                intentMy.putExtra("WarmLong", equipmentDetailBean.get(0).getWarmLong());
                intentMy.putExtra("SpecifiedLoad", equipmentDetailBean.get(0).getSpecifiedLoad());
                intentMy.putExtra("TypeName", equipmentDetailBean.get(0).getTypeName());
                intentMy.putExtra("Picture", equipmentDetailBean.get(0).getPicture());
                intentMy.putExtra("TypeId", equipmentDetailBean.get(0).getTypeId());
                intentMy.putExtra("UserType", equipmentDetailBean.get(0).getUserType());
                intentMy.putExtra("CreateId", equipmentDetailBean.get(0).getCreateId());
                intentMy.putExtra("CreateUser", equipmentDetailBean.get(0).getCreateUser());
                intentMy.putExtra("OperatorId", OperatorId);  //供应商Id
                intentMy.putExtra("Id", equipmentDetailBean.get(0).getId());//设备ID
                intentMy.putExtra("StorehouseId", getIntent().getStringExtra("StorehouseId")); //仓库ID
                intentMy.putExtra("Deposit", equipmentDetailBean.get(0).getDeposit());//押金
                intentMy.putExtra("UID", equipmentDetailBean.get(0).getUID());//使用者ID
                startActivity(intentMy);
                break;

            default:
                break;
        }
    }
}
