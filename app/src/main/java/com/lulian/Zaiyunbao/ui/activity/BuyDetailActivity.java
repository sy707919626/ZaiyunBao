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
import com.lulian.Zaiyunbao.Bean.BuyListBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/10/22.
 */
public class BuyDetailActivity extends BaseActivity {


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
    @BindView(R.id.buyDetails_shebei_price)
    TextView buyDetailsShebeiPrice;
    @BindView(R.id.buyDetails_shebei_num)
    TextView buyDetailsShebeiNum;
    @BindView(R.id.image_distance)
    ImageView imageDistance;
    @BindView(R.id.buyDetails_distance)
    TextView buyDetailsDistance;
    @BindView(R.id.buyDetails_read_details)
    TextView buyDetailsReadDetails;
    @BindView(R.id.buyDetails_service_site)
    TextView buyDetailsServiceSite;
    @BindView(R.id.buyDetails_contacts)
    TextView buyDetailsContacts;
    @BindView(R.id.buyDetails_phone)
    TextView buyDetailsPhone;
    @BindView(R.id.my_buyDetails_btn)
    Button myBuyDetailsBtn;
    private BuyListBean.RowsBean buyListBean;

    @Override
    protected int setLayoutId() {
        return R.layout.buy_order_details;
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
        buyListBean = (BuyListBean.RowsBean) getIntent().getSerializableExtra("buyDetail");
        initView();
    }

    private void initView() {
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(buyListBean.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            buyDetailsImgPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        buyDetailsShebeiName.setText(buyListBean.getEquipmentName());
        buyDetailsShebeiSpec.setText(buyListBean.getNorm());

        if (buyListBean.getTypeName().equals("托盘")) {
            buyDetailsShebeiLoad.setText("静载" + String.valueOf(buyListBean.getStaticLoad()) + "T；动载"
                    + String.valueOf(buyListBean.getCarryingLoad()) + "T；架载" + String.valueOf(buyListBean.getOnLoad()) + "T");
        } else if (buyListBean.getTypeName().equals("保温箱")) {
            buyDetailsShebeiLoad.setText("容积" + buyListBean.getVolume() + "升；保温时长"
                    + buyListBean.getWarmLong() + "小时");
        } else if (buyListBean.getTypeName().equals("周转篱")) {
            buyDetailsShebeiLoad.setText("容积" + buyListBean.getVolume() + "升；载重"
                    + buyListBean.getSpecifiedLoad() + "公斤");
        }

        buyDetailsShebeiPrice.setText(String.valueOf(buyListBean.getPriceNow()) + "元");
        buyDetailsShebeiNum.setText(String.valueOf(buyListBean.getQuantity()) + "个");

        buyDetailsServiceSite.setText(buyListBean.getStoreName());
        buyDetailsContacts.setText(buyListBean.getManager());
        buyDetailsPhone.setText(buyListBean.getTouch());
    }


    @OnClick({R.id.buyDetails_read_details, R.id.my_buyDetails_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buyDetails_read_details:
                Intent intent = new Intent(this, BuyEquipmentDetailActivity.class);
                intent.putExtra("EquipmentId", buyListBean.getId());
                startActivity(intent);

                break;

            case R.id.my_buyDetails_btn:
                Intent intentMy = new Intent(this, BuyMyEquipmentActivity.class);
                intentMy.setClass(mContext, BuyMyEquipmentActivity.class);
                intentMy.putExtra("EquipmentName", buyListBean.getEquipmentName());
                intentMy.putExtra("Norm", buyListBean.getNorm());
                intentMy.putExtra("Price", buyListBean.getPriceNow());
                intentMy.putExtra("Quantity", buyListBean.getQuantity());
                intentMy.putExtra("StaticLoad", buyListBean.getStaticLoad());
                intentMy.putExtra("CarryingLoad", buyListBean.getCarryingLoad());
                intentMy.putExtra("OnLoad", buyListBean.getOnLoad());
                intentMy.putExtra("Volume", buyListBean.getVolume());
                intentMy.putExtra("WarmLong", buyListBean.getWarmLong());
                intentMy.putExtra("SpecifiedLoad", buyListBean.getSpecifiedLoad());
                intentMy.putExtra("TypeName", buyListBean.getTypeName());
                intentMy.putExtra("Picture", buyListBean.getPicture());
                intentMy.putExtra("TypeId", buyListBean.getTypeId());
                intentMy.putExtra("SupplierContactName", buyListBean.getManager());//出售方名称
                intentMy.putExtra("SupplierContactPhone", buyListBean.getTouch());//出售方电话
                intentMy.putExtra("Id", buyListBean.getId());//设备ID
                intentMy.putExtra("StorehouseId", buyListBean.getStorehouseId());//仓库ID
                startActivity(intentMy);
                break;

            default:
                break;
        }
    }

}
