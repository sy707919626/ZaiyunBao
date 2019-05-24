package com.lulian.Zaiyunbao.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.LeaseEquipmentDetailBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.event.LeaseEvent;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/11/1.
 */

public class LeaseEquipmentDetailActivity extends BaseActivity {

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
    @BindView(R.id.lease_equipment_my_btn_cannot)
    Button leaseEquipmentMyBtnCannot;

    private String ShebeiId;
    private String OperatorId;
    private String UID;
    private List<LeaseEquipmentDetailBean> detailBeans = new ArrayList<>();
    private LeaseEquipmentDetailBean leaseEquipmentDetailBean;

    @Override
    protected int setLayoutId() {
        return R.layout.lease_equipment_info;
    }

    @Override
    protected void init() {
        stepActivities.add(this);
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
        OperatorId = getIntent().getStringExtra("OperatorId"); //供应商Id
        UID = getIntent().getStringExtra("UID"); //使用者ID

        if (UID != null) {
            if (UID.equals(GlobalParams.sUserId)) {
                leaseEquipmentMyBtn.setVisibility(View.GONE);
                leaseEquipmentMyBtnCannot.setVisibility(View.VISIBLE);
            } else {
                leaseEquipmentMyBtn.setVisibility(View.VISIBLE);
                leaseEquipmentMyBtnCannot.setVisibility(View.GONE);
            }
        } else {
            leaseEquipmentMyBtn.setVisibility(View.VISIBLE);
            leaseEquipmentMyBtnCannot.setVisibility(View.GONE);
        }

        getData();
    }


    private void getData() {
        mApi.equipmentDetails2(GlobalParams.sToken, ShebeiId, OperatorId, UID)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        detailBeans = JSONObject.parseArray(s, LeaseEquipmentDetailBean.class);
                        leaseEquipmentDetailBean = detailBeans.get(0);
                        initView();
                    }
                });
    }


    private void initView() {
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(leaseEquipmentDetailBean.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            leaseEquipmentImageview.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        leaseEquipmentTypeName.setText(leaseEquipmentDetailBean.getTypeName());
        leaseEquipmentEquipmentName.setText(leaseEquipmentDetailBean.getEquipmentName());
        leaseEquipmentModel.setText(leaseEquipmentDetailBean.getModel());
        leaseEquipmentNorm.setText(leaseEquipmentDetailBean.getNorm());

        if (leaseEquipmentDetailBean.getTypeName().equals("托盘")) {
            carryingLoadText.setText("动载");
            staticLoadText.setText("静载");
            leaseEquipmentCarryingLoad.setText(leaseEquipmentDetailBean.getCarryingLoad() + "吨");
            leaseEquipmentStaticLoad.setText(leaseEquipmentDetailBean.getStaticLoad() + "吨");
        } else if (leaseEquipmentDetailBean.getTypeName().equals("保温箱")) {
            carryingLoadText.setText("容积");
            staticLoadText.setText("保温时长");
            leaseEquipmentCarryingLoad.setText(leaseEquipmentDetailBean.getVolume() + "升");
            leaseEquipmentStaticLoad.setText(leaseEquipmentDetailBean.getWarmLong() + "小时");
        } else if (leaseEquipmentDetailBean.getTypeName().equals("周转篱")) {
            carryingLoadText.setText("容积");
            staticLoadText.setText("载重");
            leaseEquipmentCarryingLoad.setText(leaseEquipmentDetailBean.getVolume() + "升");
            leaseEquipmentStaticLoad.setText(leaseEquipmentDetailBean.getSpecifiedLoad() + "公斤");
        }

        leaseEquipmentBaseMaterial.setText(leaseEquipmentDetailBean.getBaseMaterial());
        leaseEquipmentRemark.setText(leaseEquipmentDetailBean.getRemark());

        leaseEquipmentEquipmentBaseNo.setText(leaseEquipmentDetailBean.getEquipmentBaseNo());
        leaseEquipmentPValue.setText(leaseEquipmentDetailBean.getPValue() + "元/天");
    }

    @OnClick({R.id.lease_equipment_my_btn, R.id.lease_equipment_my_btn_cannot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lease_equipment_my_btn:
                Intent intentMy = new Intent();
                intentMy.setClass(this, LeaseMyEquipmentActivity.class);
                intentMy.putExtra("EquipmentName", leaseEquipmentDetailBean.getEquipmentName());
                intentMy.putExtra("Norm", leaseEquipmentDetailBean.getNorm());
                intentMy.putExtra("Price", leaseEquipmentDetailBean.getPValue());
                intentMy.putExtra("Quantity", leaseEquipmentDetailBean.getQuantity());
                intentMy.putExtra("StaticLoad", leaseEquipmentDetailBean.getStaticLoad());
                intentMy.putExtra("CarryingLoad", leaseEquipmentDetailBean.getCarryingLoad());
                intentMy.putExtra("OnLoad", leaseEquipmentDetailBean.getOnLoad());
                intentMy.putExtra("Volume", leaseEquipmentDetailBean.getVolume());
                intentMy.putExtra("WarmLong", leaseEquipmentDetailBean.getWarmLong());
                intentMy.putExtra("SpecifiedLoad", leaseEquipmentDetailBean.getSpecifiedLoad());
                intentMy.putExtra("TypeName", leaseEquipmentDetailBean.getTypeName());
//                intentMy.putExtra("Picture", leaseEquipmentDetailBean.getPicture());
                intentMy.putExtra("TypeId", leaseEquipmentDetailBean.getTypeId()); //类型Id
                intentMy.putExtra("UserType", leaseEquipmentDetailBean.getUserType());
                intentMy.putExtra("CreateId", leaseEquipmentDetailBean.getCreateId());
                intentMy.putExtra("CreateUser", leaseEquipmentDetailBean.getCreateUser());
                intentMy.putExtra("OperatorId", OperatorId);  //供应商Id
                intentMy.putExtra("Id", leaseEquipmentDetailBean.getId());//设备ID
                intentMy.putExtra("StorehouseId", getIntent().getStringExtra("StorehouseId")); //仓库ID
                intentMy.putExtra("Deposit", leaseEquipmentDetailBean.getDeposit());//押金

//                if (TextUtils.isEmpty(leaseEquipmentDetailBean.getUID())){
//                    intentMy.putExtra("UID",""); //使用者ID
//                }else {
                    intentMy.putExtra("UID", leaseEquipmentDetailBean.getUID()); //使用者ID
//                }
//                intentMy.putExtra("UID", leaseEquipmentDetailBean.getUID());//使用者ID
                startActivity(intentMy);
                break;

            case R.id.lease_equipment_my_btn_cannot:
                //撤销上架
                mApi.ChangeGroundingQuantity(GlobalParams.sToken, GlobalParams.sUserId,
                        leaseEquipmentDetailBean.getId(),0)
                        .compose(RxHttpResponseCompat.<String>compatResult())
                        .subscribe(new ErrorHandlerSubscriber<String>() {
                            @Override
                            public void onNext(String s) {
                                RxToast.success("撤销上架成功");
                                EventBus.getDefault().post(new LeaseEvent());
                                stepfinishAll();
                            }
                        });
                break;

            default:
                break;
        }
    }

}
