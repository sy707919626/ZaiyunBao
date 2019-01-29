package com.lulian.Zaiyunbao.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lulian.Zaiyunbao.Bean.SaleEntity;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class BuyMyEquipmentActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.buy_imageview_photo)
    ImageView buyImageviewPhoto;
    @BindView(R.id.buy_my_EquipmentName)
    TextView buyMyEquipmentName;
    @BindView(R.id.buy_my_Norm)
    TextView buyMyNorm;
    @BindView(R.id.buy_my_load)
    TextView buyMyLoad;
    @BindView(R.id.buy_my_PayModle_text)
    TextView buyMyPayModleText;
    @BindView(R.id.buy_my_PayModle_spinner)
    TextView buyMyPayModleSpinner;
    @BindView(R.id.my_address_quxian_text)
    TextView myAddressQuxianText;
    @BindView(R.id.my_address_quxian)
    TextView myAddressQuxian;
    @BindView(R.id.buy_address_xiangxi)
    ClearEditText buyAddressXiangxi;
    @BindView(R.id.buy_my_lianxiren_text)
    TextView buyMyLianxirenText;
    @BindView(R.id.buy_my_lianxiren)
    ClearEditText buyMyLianxiren;
    @BindView(R.id.buy_my_lianxiPhone_Text)
    TextView buyMyLianxiPhoneText;
    @BindView(R.id.buy_my_lianxiren_Phone)
    ClearEditText buyMyLianxirenPhone;
    @BindView(R.id.buy_my_sum_text)
    TextView buyMySumText;
    @BindView(R.id.buy_my_sum)
    ClearEditText buyMySum;
    @BindView(R.id.buy_my_PValue_Text)
    TextView buyMyPValueText;
    @BindView(R.id.buy_my_PValue)
    TextView buyMyPValue;
    @BindView(R.id.buy_my_rent_text)
    TextView buyMyRentText;
    @BindView(R.id.buy_my_money)
    TextView buyMyMoney;
    @BindView(R.id.buy_my_shouhou)
    RelativeLayout buyMyShouhou;
    @BindView(R.id.buy_equipment_my_next)
    Button buyEquipmentMyNext;
    @BindView(R.id.dialog_bg)
    ImageView dialogBg;
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
    private String TypeId;
    private String StorehouseId; //仓库ID

    private Handler mHandler;

    @Override
    protected int setLayoutId() {
        return R.layout.buy_my_equipment;
    }

    @SuppressLint("NewApi")
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

        textDetailContent.setText("我要购买");
        textDetailRight.setVisibility(View.GONE);

        dialogBg.setImageAlpha(0);
        dialogBg.setVisibility(View.GONE);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    dialogBg.setVisibility(View.GONE);
                }
            }
        };

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
        Picture = getIntent().getStringExtra("Picture");
        Id = getIntent().getStringExtra("Id");
        SupplierContactName = getIntent().getStringExtra("SupplierContactName");
        SupplierContactPhone = getIntent().getStringExtra("SupplierContactPhone");
        TypeId = getIntent().getStringExtra("TypeId");
        StorehouseId = getIntent().getStringExtra("StorehouseId");

        initView();
    }


    private void initView() {

        buyMyPayModleSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBlur(dialogBg, mHandler);

                String[] list = GlobalParams.ZFTypeList.toArray(new String[GlobalParams.ZFTypeList.size()]);

                BaseDialog(BuyMyEquipmentActivity.this, dialogBg, list,
                        buyMyPayModleSpinner.getText().toString(), "货款以及交付方式", mHandler, new OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                buyMyPayModleSpinner.setText(data.get(position).getTitle());
                            }
                        });

            }
        });

        buyMyEquipmentName.setText(EquipmentName);
        buyMyNorm.setText(Norm);
        buyMyPValue.setText(Price + "");

        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(Picture, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            buyImageviewPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        if (TypeName.equals("保温箱")) {
            buyMyLoad.setText("容积" + String.valueOf(Volume) + "升T；保温时长"
                    + String.valueOf(WarmLong) + "小时");
        } else {
            buyMyLoad.setText("静载" + String.valueOf(StaticLoad) + "T；动载"
                    + String.valueOf(CarryingLoad) + "T；架载" + String.valueOf(OnLoad) + "T");
        }

        buyMyLianxiren.setText(GlobalParams.sUserName);
        buyMyLianxirenPhone.setText(GlobalParams.sUserPhone);

        buyMySum.setText(Quantity + "");
        buyMyMoney.setText(Quantity * Price + "");

        setupSearchView();
    }

    @OnClick({R.id.my_address_quxian, R.id.buy_equipment_my_next, R.id.buy_my_shouhou})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_address_quxian:
                changeAddress("");
                break;

            case R.id.buy_my_shouhou:
                startActivity(new Intent(this, BuyPolicyActivity.class));
                break;

            case R.id.buy_equipment_my_next:
                if (myAddressQuxian.getText().toString().trim().equals("")) {
                    RxToast.warning("请选择收货区县地址");
                } else if (buyAddressXiangxi.getText().toString().trim().equals("")) {
                    RxToast.warning("请填写收货详细地址");
                } else if (buyMyLianxiren.getText().toString().trim().equals("")) {
                    RxToast.warning("请填写联系人");
                } else if (buyMyLianxirenPhone.getText().toString().trim().equals("")) {
                    RxToast.warning("请填写联系电话");
                } else if (buyMySum.getText().toString().trim().equals("")) {
                    RxToast.warning("请输入购买数量");
                } else {
                    Intent intent = new Intent(BuyMyEquipmentActivity.this, BuyEquipmentConfirmActivity.class);
                    intent.putExtra("EquipmentName", EquipmentName);
                    intent.putExtra("Norm", Norm);
                    intent.putExtra("Price", Price);
                    intent.putExtra("Quantity", Quantity);
                    intent.putExtra("StaticLoad", StaticLoad);
                    intent.putExtra("CarryingLoad", CarryingLoad);
                    intent.putExtra("OnLoad", OnLoad);
                    intent.putExtra("Volume", Volume);
                    intent.putExtra("WarmLong", WarmLong);
                    intent.putExtra("SpecifiedLoad", SpecifiedLoad);
                    intent.putExtra("TypeName", TypeName);
                    intent.putExtra("Picture", Picture);
                    intent.putExtra("TypeId", TypeId);
                    intent.putExtra("SupplierContactName", SupplierContactName);//出售方名称
                    intent.putExtra("SupplierContactPhone", SupplierContactPhone);//出售方电话
                    intent.putExtra("Id", Id);//设备ID
                    intent.putExtra("StorehouseId", StorehouseId);//仓库ID
                    intent.putExtra("address", myAddressQuxian.getText().toString().trim() + buyAddressXiangxi.getText().toString().trim());//地址
                    intent.putExtra("payModel", buyMyPayModleSpinner.getText().toString().trim());//支付方式
                    intent.putExtra("lianxiren", buyMyLianxiren.getText().toString().trim());//联系人
                    intent.putExtra("liaxirenPhone", buyMyLianxirenPhone.getText().toString().trim());//联系电话
                    intent.putExtra("money", Float.valueOf(buyMyMoney.getText().toString().trim()));
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == ADDRESS_LOCATION && resultCode == 11) { //选择地点返回值
            if (data != null) {
                myAddressQuxian.setText(data.getStringExtra("addressAll"));
            }
        }
    }

    private void setupSearchView() {
        //etSeatc点击回车
        RxTextView.editorActions(buyMySum).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                if (Integer.valueOf(buyMySum.getText().toString().trim()) <= Quantity) {
                    buyMyMoney.setText(Float.valueOf(Integer.valueOf(buyMySum.getText().toString().trim()) * Price) + "");
                } else {
                    RxToast.warning("购买数量不能大于可购买数量");
                    buyMySum.setText("");
                    buyMyMoney.setText("");
                }
            }
        });

        RxTextView.textChanges(buyMySum)
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(@NonNull CharSequence charSequence) throws Exception {
                        return charSequence.toString().trim().length() > 0;
                    }
                }).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(@NonNull CharSequence charSequence) throws Exception {
                if (Integer.valueOf(buyMySum.getText().toString().trim()) <= Quantity) {
                    buyMyMoney.setText(Float.valueOf(Integer.valueOf(buyMySum.getText().toString().trim()) * Price) + "");
                } else {
                    RxToast.warning("购买数量不能大于可购买数量");
                    buyMySum.setText("");
                    buyMyMoney.setText("");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
