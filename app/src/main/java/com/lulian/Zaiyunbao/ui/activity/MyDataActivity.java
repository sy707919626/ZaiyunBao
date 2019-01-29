package com.lulian.Zaiyunbao.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.widget.CircleImageView;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/10/22.
 */
public class MyDataActivity extends BaseActivity {


    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.my_cropimage_view)
    CircleImageView myCropimageView;
    @BindView(R.id.mydata_head_relative)
    RelativeLayout mydataHeadRelative;
    @BindView(R.id.mydata_name)
    TextView mydataName;
    @BindView(R.id.mydata_name_relative)
    RelativeLayout mydataNameRelative;
    @BindView(R.id.phone_numble)
    TextView phoneNumble;
    @BindView(R.id.mydata_phone_relative)
    RelativeLayout mydataPhoneRelative;
    private String user_name;
    private String phone;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mydata;
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

        textDetailContent.setText("用户信息");
        textDetailRight.setVisibility(View.GONE);
        user_name = getIntent().getStringExtra("myDataName");
        phone = GlobalParams.sUserPhone;
        initView();
    }

    private void initView() {
        mydataName.setText(user_name);
        phoneNumble.setText(phone);
    }


    @OnClick({R.id.mydata_head_relative, R.id.mydata_name_relative, R.id.mydata_phone_relative})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mydata_head_relative:

                break;

            case R.id.mydata_name_relative:

                break;

            case R.id.mydata_phone_relative:
                //手机号码修改
                Intent intent = new Intent(this, Update_Phone_One_Activity.class);
                intent.putExtra("tempMobile", phone);
                startActivity(intent);

                break;

            default:
                break;
        }
    }
}
