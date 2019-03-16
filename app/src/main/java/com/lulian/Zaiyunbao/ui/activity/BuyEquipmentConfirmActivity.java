package com.lulian.Zaiyunbao.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.BuyDetailBean;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.event.BuyEvent;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/11/1.
 */

public class BuyEquipmentConfirmActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.buyDetails_img_photo)
    ImageView buyDetailsImgPhoto;
    @BindView(R.id.buyDetails_shebei_name)
    TextView buyDetailsShebeiName;
    @BindView(R.id.buyDetails_shebei_spec)
    TextView buyDetailsShebeiSpec;
    @BindView(R.id.buyDetails_shebei_load)
    TextView buyDetailsShebeiLoad;
    @BindView(R.id.buy_sum)
    TextView buySum;
    @BindView(R.id.buy_price_DJ)
    TextView buyPriceDJ;
    @BindView(R.id.buy_money)
    TextView buyMoney;
    @BindView(R.id.buy_payModle)
    TextView buyPayModle;
    @BindView(R.id.buy_zhekou_money)
    TextView buyZhekouMoney;
    @BindView(R.id.buy_shiji_pay)
    TextView buyShijiPay;
    @BindView(R.id.buy_SH)
    TextView buySH;
    @BindView(R.id.buy_SH_Address)
    TextView buySHAddress;
    @BindView(R.id.buy_lianxi_phone)
    TextView buyLianxiPhone;
    @BindView(R.id.buy_cancel)
    Button buyCancel;
    @BindView(R.id.buy_Sure)
    Button buySure;
    private String EquipmentName;
    private String Norm;
    private float Price;
    private int Quantity;
    private double StaticLoad;
    private double CarryingLoad;
    private double OnLoad;
    private double Volume;
    private double WarmLong;
    private double SpecifiedLoad;
    private String TypeName;
    private String Picture;
    private String Id; //设备ID
    private String SupplierContactName; //出售方名称
    private String SupplierContactPhone;//出售方电话

    private BuyDetailBean buyDetailBean;

    @Override
    protected int setLayoutId() {
        return R.layout.buy_equipment_confirm;
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

        textDetailContent.setText("订单确认");
        textDetailRight.setVisibility(View.GONE);
        EquipmentName = getIntent().getStringExtra("EquipmentName");
        Norm = getIntent().getStringExtra("Norm");
        Price = getIntent().getFloatExtra("Price", 0);
        Quantity = getIntent().getIntExtra("Quantity", 0);
        StaticLoad = getIntent().getDoubleExtra("StaticLoad", 0);
        CarryingLoad = getIntent().getDoubleExtra("CarryingLoad", 0);
        OnLoad = getIntent().getDoubleExtra("OnLoad", 0);
        Volume = getIntent().getDoubleExtra("Volume", 0);
        WarmLong = getIntent().getDoubleExtra("WarmLong", 0);
        SpecifiedLoad = getIntent().getDoubleExtra("SpecifiedLoad", 0);
        TypeName = getIntent().getStringExtra("TypeName");
//        Picture = getIntent().getStringExtra("Picture");
        Id = getIntent().getStringExtra("Id");
        SupplierContactName = getIntent().getStringExtra("SupplierContactName");
        SupplierContactPhone = getIntent().getStringExtra("SupplierContactPhone");

        getData();
    }

    //单纯获取图片
    private void getData() {
        mApi.EquipmentBuyItem(GlobalParams.sToken, Id)
                .compose(RxHttpResponseCompat.<String>compatResult())
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
            buyDetailsImgPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        buyDetailsShebeiName.setText(EquipmentName);
        buyDetailsShebeiSpec.setText(Norm);

        if (TypeName.equals("保温箱")) {
            buyDetailsShebeiLoad.setText("容积" + String.valueOf(Volume) + "升T；保温时长"
                    + String.valueOf(WarmLong) + "小时");
        } else if (TypeName.equals("托盘")) {
            buyDetailsShebeiLoad.setText("静载" + String.valueOf(StaticLoad) + "T；动载"
                    + String.valueOf(CarryingLoad) + "T；架载" + String.valueOf(OnLoad) + "T");
        } else if (TypeName.equals("周转篱")) {
            buyDetailsShebeiLoad.setText("容积" + String.valueOf(Volume) + "升T；载重"
                    + String.valueOf(SpecifiedLoad) + "公斤");
        }

        buySum.setText(Quantity + "个");
        buyPriceDJ.setText(Price + "/个/片");
        buyMoney.setText(getIntent().getFloatExtra("money", 0) + "元");
        buyPayModle.setText(getIntent().getStringExtra("payModel"));
        buyZhekouMoney.setText("0元");
        buyShijiPay.setText("￥" + getIntent().getFloatExtra("money", 0f) + "");
        buySH.setText(getIntent().getStringExtra("lianxiren"));
        buySHAddress.setText(getIntent().getStringExtra("address"));
//        buyLiaxiren.setText(getIntent().getStringExtra("lianxiren"));
        buyLianxiPhone.setText(getIntent().getStringExtra("liaxirenPhone"));
    }

    @OnClick({R.id.buy_cancel, R.id.buy_Sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buy_Sure:
                JSONObject obj = new JSONObject();
                obj.put("ETypeId", Id);

                obj.put("Price", Price); //单价
                obj.put("Count", Quantity); //数量
                obj.put("Amount", getIntent().getFloatExtra("money", 0f));//金额
                obj.put("CreateId", GlobalParams.sUserId); //创建用户ID
                obj.put("TransferWay", buyPayModle.getText().toString().trim()); //货款以及交付
                obj.put("ReceiveAddress", buySHAddress.getText().toString().trim()); //收货地址
                obj.put("ContactName", getIntent().getStringExtra("lianxiren")); //求购方联系人
                obj.put("ContactPhone", getIntent().getStringExtra("liaxirenPhone")); //求购方电话

                obj.put("SupplierContactName", SupplierContactName); //出售方联系人
                obj.put("SupplierContactPhone", SupplierContactPhone); //出售方联系电话

                obj.put("StoreId", getIntent().getStringExtra("StorehouseId")); //仓库ID
                obj.put("StoreName", ""); //仓库名
                String lease = obj.toString();
                RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                        lease);

                mApi.EquipmentBuyAdd(GlobalParams.sToken, body)
                        .compose(RxHttpResponseCompat.<String>compatResult())
                        .subscribe(new ErrorHandlerSubscriber<String>() {
                            @Override
                            public void onNext(String s) {
                                RxToast.success("购买订单发布成功");
                                EventBus.getDefault().post(new BuyEvent());

                                stepfinishAll();
                            }
                        });

                break;

            case R.id.buy_cancel:
                finish();
                break;
            default:
                break;
        }
    }
}
