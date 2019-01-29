package com.lulian.Zaiyunbao.ui.activity.repair;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.RepairItemDetail;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.lulian.Zaiyunbao.ui.photoview.PhotoDisplayDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/1/24.
 */

public class RepairItemDetailActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.repair_order_statu)
    TextView repairOrderStatu;
    @BindView(R.id.repair_item_RepairType)
    TextView repairItemRepairType;
    @BindView(R.id.repair_item_remark)
    TextView repairItemRemark;
    @BindView(R.id.repair_item_RepairType_image1)
    ImageView repairItemRepairTypeImage1;
    @BindView(R.id.repair_item_RepairType_image2)
    ImageView repairItemRepairTypeImage2;
    @BindView(R.id.repair_item_RepairType_image3)
    ImageView repairItemRepairTypeImage3;
    @BindView(R.id.repair_item_image_btn)
    Button repairItemImageBtn;
    @BindView(R.id.lease_equipment_imageview)
    ImageView leaseEquipmentImageview;
    @BindView(R.id.lease_equipment_TypeName)
    TextView leaseEquipmentTypeName;
    @BindView(R.id.lease_equipment_EquipmentName)
    TextView leaseEquipmentEquipmentName;
    @BindView(R.id.lease_equipment_Model)
    TextView leaseEquipmentModel;
    @BindView(R.id.lease_equipment_Norm)
    TextView leaseEquipmentNorm;
    @BindView(R.id.carryingLoad_text)
    TextView carryingLoadText;
    @BindView(R.id.lease_equipment_CarryingLoad)
    TextView leaseEquipmentCarryingLoad;
    @BindView(R.id.staticLoad_text)
    TextView staticLoadText;
    @BindView(R.id.lease_equipment_StaticLoad)
    TextView leaseEquipmentStaticLoad;
    @BindView(R.id.lease_equipment_BaseMaterial)
    TextView leaseEquipmentBaseMaterial;
    @BindView(R.id.lease_equipment_EquipmentBaseNo)
    TextView leaseEquipmentEquipmentBaseNo;
    @BindView(R.id.lease_equipment_PValue)
    TextView leaseEquipmentPValue;
    @BindView(R.id.lease_equipment_Remark)
    TextView leaseEquipmentRemark;
    private String Id;
    private int Status;
    private RepairItemDetail mRepairItemDetail;

    private PhotoDisplayDialog repairDialog1;
    private PhotoDisplayDialog repairDialog2;
    private PhotoDisplayDialog repairDialog3;

    @Override
    protected int setLayoutId() {
        return R.layout.repair_item_info;
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

        textDetailContent.setText("报修详情");
        textDetailRight.setVisibility(View.GONE);
        Id = getIntent().getStringExtra("DetailId");
        Status = getIntent().getIntExtra("Status", 0);

        getData();

        repairDialog1 = new PhotoDisplayDialog(this, false);
        repairDialog2 = new PhotoDisplayDialog(this, false);
        repairDialog3 = new PhotoDisplayDialog(this, false);

        repairItemImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repairItemRepairTypeImage2.setVisibility(View.VISIBLE);
                repairItemRepairTypeImage3.setVisibility(View.VISIBLE);
            }
        });
    }

    @SuppressLint("NewApi")
    @OnClick({R.id.repair_item_RepairType_image1, R.id.repair_item_RepairType_image2, R.id.repair_item_RepairType_image3})
    public void onViewClicked(View view) {
        String[] result = mRepairItemDetail.getUrls().split(",");

        switch (view.getId()) {
            case R.id.repair_item_RepairType_image1:
                repairDialog1.setImgUrl(Constants.BASE_URL + result[0]).show();
                break;

            case R.id.repair_item_RepairType_image2:
                repairDialog2.setImgUrl(Constants.BASE_URL + result[1]).show();
                break;

            case R.id.repair_item_RepairType_image3:
                repairDialog3.setImgUrl(Constants.BASE_URL + result[2]).show();
                break;
        }
    }

    public void getData() {
        mApi.RepairItemDetail(GlobalParams.sToken, Id)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        mRepairItemDetail = MyApplication.get().getAppComponent().getGson().fromJson(s, RepairItemDetail.class);
                        initView();
                    }
                });
    }

    private void initView() {
        if (Status == 1) {
            repairOrderStatu.setText("处理中");
        } else if (Status == 2) {
            repairOrderStatu.setText("已完成");
        }

        if (mRepairItemDetail.getRepairType() == 1) {
            repairItemRepairType.setText("磨损");
        } else if (mRepairItemDetail.getRepairType() == 2) {
            repairItemRepairType.setText("破裂");
        } else if (mRepairItemDetail.getRepairType() == 3) {
            repairItemRepairType.setText("损毁");
        }

        repairItemRemark.setText(mRepairItemDetail.getDetailRemark());

        String[] result = mRepairItemDetail.getUrls().split(",");

        Glide.with(RepairItemDetailActivity.this).load(Constants.BASE_URL + result[0]).into(repairItemRepairTypeImage1);
        Glide.with(RepairItemDetailActivity.this).load(Constants.BASE_URL + result[1]).into(repairItemRepairTypeImage2);
        Glide.with(RepairItemDetailActivity.this).load(Constants.BASE_URL + result[2]).into(repairItemRepairTypeImage3);

        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(mRepairItemDetail.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            leaseEquipmentImageview.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        leaseEquipmentTypeName.setText(mRepairItemDetail.getETypeName());
        leaseEquipmentEquipmentName.setText(mRepairItemDetail.getEquipmentName());
        leaseEquipmentModel.setText(mRepairItemDetail.getModel());
        leaseEquipmentNorm.setText(mRepairItemDetail.getNorm());

        if (mRepairItemDetail.getETypeName().equals("托盘")) {
            carryingLoadText.setText("动载");
            staticLoadText.setText("静载");
            leaseEquipmentCarryingLoad.setText(mRepairItemDetail.getCarryingLoad() + "吨");
            leaseEquipmentStaticLoad.setText(mRepairItemDetail.getStaticLoad() + "吨");
        } else if (mRepairItemDetail.getETypeName().equals("保温箱")) {
            carryingLoadText.setText("容积");
            staticLoadText.setText("保温时长");
            leaseEquipmentCarryingLoad.setText(mRepairItemDetail.getVolume() + "升");
            leaseEquipmentStaticLoad.setText(mRepairItemDetail.getWarmLong() + "小时");
        } else if (mRepairItemDetail.getETypeName().equals("周转篱")) {
            carryingLoadText.setText("容积");
            staticLoadText.setText("载重");
            leaseEquipmentCarryingLoad.setText(mRepairItemDetail.getVolume() + "升");
            leaseEquipmentStaticLoad.setText(mRepairItemDetail.getSpecifiedLoad() + "公斤");
        }

        leaseEquipmentBaseMaterial.setText(mRepairItemDetail.getBaseMaterial());
        leaseEquipmentRemark.setText(mRepairItemDetail.getRemark());

        leaseEquipmentEquipmentBaseNo.setText(mRepairItemDetail.getEquipmentBaseNo());
        leaseEquipmentPValue.setText(mRepairItemDetail.getPrice() + "元/天");
    }

}
