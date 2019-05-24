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

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.BuyDetailBean;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/11/1.
 */

public class BuyEquipmentDetailActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.buy_equipment_imageview)
    ImageView buyEquipmentImageview;
    @BindView(R.id.buy_equipment_TypeName)
    TextView buyEquipmentTypeName;
    @BindView(R.id.buy_equipment_EquipmentName)
    TextView buyEquipmentEquipmentName;
    @BindView(R.id.buy_equipment_Model)
    TextView buyEquipmentModel;
    @BindView(R.id.buy_equipment_Norm)
    TextView buyEquipmentNorm;
    @BindView(R.id.carryingLoad_text)
    TextView carryingLoadText;
    @BindView(R.id.buy_equipment_CarryingLoad)
    TextView buyEquipmentCarryingLoad;
    @BindView(R.id.staticLoad_text)
    TextView staticLoadText;
    @BindView(R.id.buy_equipment_StaticLoad)
    TextView buyEquipmentStaticLoad;
    @BindView(R.id.buy_equipment_BaseMaterial)
    TextView buyEquipmentBaseMaterial;
    @BindView(R.id.buy_equipment_Remark)
    TextView buyEquipmentRemark;
    @BindView(R.id.buy_equipment_EquipmentBaseNo)
    TextView buyEquipmentEquipmentBaseNo;
    @BindView(R.id.buy_equipment_PValue)
    TextView buyEquipmentPValue;
    @BindView(R.id.buy_equipment_Other)
    TextView buyEquipmentOther;
    @BindView(R.id.buy_equipment_my_btn)
    Button buyEquipmentMyBtn;

    private String EquipmentId;
    private BuyDetailBean buyDetailBean;

    @Override
    protected int setLayoutId() {
        return R.layout.buy_equipment_info;
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
        EquipmentId = getIntent().getStringExtra("EquipmentId");
        getData();
    }

    private void getData() {
        mApi.EquipmentBuyItem(GlobalParams.sToken, EquipmentId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .compose(this.<String>bindUntilEvent(ActivityEvent.STOP))
                .compose(this.<String>bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        buyDetailBean = MyApplication.get().getAppComponent().getGson().fromJson(s, BuyDetailBean.class);
                        initView();
                    }
                });
    }

    private void initView() {
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(buyDetailBean.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            buyEquipmentImageview.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        buyEquipmentTypeName.setText(buyDetailBean.getTypeName());
        buyEquipmentEquipmentName.setText(buyDetailBean.getEquipmentName());
        buyEquipmentModel.setText(buyDetailBean.getModel());
        buyEquipmentNorm.setText(buyDetailBean.getNorm());

        if (buyDetailBean.getTypeName().equals("托盘")) {
            carryingLoadText.setText("动载");
            staticLoadText.setText("静载");
            buyEquipmentCarryingLoad.setText(buyDetailBean.getCarryingLoad() + "吨");
            buyEquipmentStaticLoad.setText(buyDetailBean.getStaticLoad() + "吨");
        } else if (buyDetailBean.getTypeName().equals("保温箱")) {
            carryingLoadText.setText("容积");
            staticLoadText.setText("保温时长");
            buyEquipmentCarryingLoad.setText(buyDetailBean.getVolume() + "升");
            buyEquipmentStaticLoad.setText(buyDetailBean.getWarmLong() + "小时");
        } else if (buyDetailBean.getTypeName().equals("周转篱")) {
            carryingLoadText.setText("容积");
            staticLoadText.setText("载重");
            buyEquipmentCarryingLoad.setText(buyDetailBean.getVolume() + "升");
            buyEquipmentStaticLoad.setText(buyDetailBean.getSpecifiedLoad() + "公斤");
        }

        buyEquipmentBaseMaterial.setText(buyDetailBean.getBaseMaterial());
        buyEquipmentRemark.setText(buyDetailBean.getRemark());

        buyEquipmentEquipmentBaseNo.setText(buyDetailBean.getEquipmentBaseNo());
        buyEquipmentPValue.setText(buyDetailBean.getPriceNow() + "元");
        buyEquipmentOther.setText("");
    }

    @OnClick({R.id.buy_equipment_my_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buy_equipment_my_btn:
                Intent intentMy = new Intent();
                intentMy.setClass(mContext, BuyMyEquipmentActivity.class);
                intentMy.putExtra("EquipmentName", buyDetailBean.getEquipmentName());
                intentMy.putExtra("Norm", buyDetailBean.getNorm());
                intentMy.putExtra("Price", buyDetailBean.getPriceNow());
                intentMy.putExtra("Quantity", buyDetailBean.getQuantity());
                intentMy.putExtra("StaticLoad", buyDetailBean.getStaticLoad());
                intentMy.putExtra("CarryingLoad", buyDetailBean.getCarryingLoad());
                intentMy.putExtra("OnLoad", buyDetailBean.getOnLoad());
                intentMy.putExtra("Volume", buyDetailBean.getVolume());
                intentMy.putExtra("WarmLong", buyDetailBean.getWarmLong());
                intentMy.putExtra("SpecifiedLoad", buyDetailBean.getSpecifiedLoad());
                intentMy.putExtra("TypeName", buyDetailBean.getTypeName());
                intentMy.putExtra("TypeId", buyDetailBean.getTypeId());
//                intentMy.putExtra("Picture", buyDetailBean.getPicture());
                intentMy.putExtra("TypeId", buyDetailBean.getTypeId());
                intentMy.putExtra("SupplierContactName", buyDetailBean.getSupplierContactName());//出售方名称
                intentMy.putExtra("SupplierContactPhone", buyDetailBean.getSupplierContactPhone());//出售方电话
                intentMy.putExtra("Id", buyDetailBean.getId());//设备ID
                intentMy.putExtra("StorehouseId", buyDetailBean.getStorehouseId());//仓库ID
                startActivity(intentMy);
                break;

            default:
                break;
        }
    }
}
