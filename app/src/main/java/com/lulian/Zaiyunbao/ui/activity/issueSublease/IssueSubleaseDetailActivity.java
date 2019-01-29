package com.lulian.Zaiyunbao.ui.activity.issueSublease;

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
import com.lulian.Zaiyunbao.Bean.IssueDeatilsBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/11/1.
 */

public class IssueSubleaseDetailActivity extends BaseActivity {
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
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
    @BindView(R.id.lease_equipment_my_btn)
    Button leaseEquipmentMyBtn;

    private String ShebeiId;
    private IssueDeatilsBean issueDeatilsBean;

    @Override
    protected int setLayoutId() {
        return R.layout.lease_equipment_info;
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

        textDetailContent.setText("设备信息");
        textDetailRight.setVisibility(View.GONE);
        ShebeiId = getIntent().getStringExtra("ShebeiId");
        getData();
    }

    private void getData() {
        mApi.CanRentEquipmentDetailsForUser(GlobalParams.sToken, ShebeiId, GlobalParams.sUserId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        issueDeatilsBean = JSONObject.parseObject(s, IssueDeatilsBean.class);
                        initView();
                    }
                });
    }

    private void initView() {
        leaseEquipmentMyBtn.setVisibility(View.GONE);

        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(issueDeatilsBean.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            leaseEquipmentImageview.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        leaseEquipmentTypeName.setText(issueDeatilsBean.getTypeName());
        leaseEquipmentEquipmentName.setText(issueDeatilsBean.getEquipmentName());
        leaseEquipmentModel.setText(issueDeatilsBean.getModel());
        leaseEquipmentNorm.setText(issueDeatilsBean.getNorm());

        if (issueDeatilsBean.getTypeName().equals("托盘")) {
            carryingLoadText.setText("动载");
            staticLoadText.setText("静载");
            leaseEquipmentCarryingLoad.setText(issueDeatilsBean.getCarryingLoad() + "吨");
            leaseEquipmentStaticLoad.setText(issueDeatilsBean.getStaticLoad() + "吨");
        } else if (issueDeatilsBean.getTypeName().equals("保温箱")) {
            carryingLoadText.setText("容积");
            staticLoadText.setText("保温时长");
            leaseEquipmentCarryingLoad.setText(issueDeatilsBean.getVolume() + "升");
            leaseEquipmentStaticLoad.setText(issueDeatilsBean.getWarmLong() + "小时");
        } else if (issueDeatilsBean.getTypeName().equals("周转篱")) {
            carryingLoadText.setText("容积");
            staticLoadText.setText("载重");
            leaseEquipmentCarryingLoad.setText(issueDeatilsBean.getVolume() + "升");
            leaseEquipmentStaticLoad.setText(issueDeatilsBean.getSpecifiedLoad() + "公斤");
        }

        leaseEquipmentBaseMaterial.setText(issueDeatilsBean.getBaseMaterial());
        leaseEquipmentRemark.setText(issueDeatilsBean.getRemark());

        leaseEquipmentEquipmentBaseNo.setText(issueDeatilsBean.getEquipmentBaseNo());
        leaseEquipmentPValue.setText(issueDeatilsBean.getPrice() + "元/天");
    }

}
