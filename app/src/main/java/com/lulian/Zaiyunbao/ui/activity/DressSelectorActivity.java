package com.lulian.Zaiyunbao.ui.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.addressBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.ywp.addresspickerlib.AddressPickerView;

import butterknife.BindView;

import static com.lulian.Zaiyunbao.ui.base.BaiduMapBase.mLocationClient;

/**
 * Created by Administrator on 2018/9/15.
 */

public class DressSelectorActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.image_back_title_bar)
    ImageView imageBackTitleBar;
    @BindView(R.id.address_selector)
    AddressPickerView addressSelector;
    @BindView(R.id.current_location)
    TextView currentLocation;
    @BindView(R.id.again_Location)
    TextView againLocation;

    private String current_address;
    private addressBean addressBean;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_dress_selector;
    }

    @Override
    protected void init() {
        ImmersionBar.with(this)
                .titleBar(R.id.title_address_toolbar)
                .titleBarMarginTop(R.id.title_address_toolbar)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        currentLocation.setText(GlobalParams.district);
        current_address = getIntent().getStringExtra("currentlocation");

        currentLocation.setText(current_address);

        textContent.setText("位置选择");
        againLocation.setOnClickListener(this); //定位


        addressBean = new addressBean();
        addressSelector.setOnAddressPickerSures(new AddressPickerView.OnAddressPickerSureListeners() {
            @Override
            public void onSureClick(String address, String shengfen, String city, String provinceCode, String cityCode, String districtCode) {
                currentLocation.setText(address);

                addressBean.setAddress(address);
                addressBean.setCode(cityCode);

                Bundle bundle = new Bundle();
                bundle.putString("addressSelect", address);
                bundle.putString("addressAll", shengfen + city + address);
                setResult(11, getIntent().putExtras(bundle));

                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.again_Location:
                //定位
                mLocationClient.start();
                currentLocation.setText(GlobalParams.district);
                break;
        }
    }
}
