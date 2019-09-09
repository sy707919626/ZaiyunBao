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
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/10/22.
 */
public class DataUserTypeActivity extends BaseActivity {


    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.data_user_relative)
    RelativeLayout dataUserRelative;
    @BindView(R.id.data_company_relative)
    RelativeLayout dataCompanyRelative;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_data_usertype;
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

        textDetailContent.setText("我的资料");
        textDetailRight.setVisibility(View.GONE);

        if (GlobalParams.sUserClass == 1){
            dataUserRelative.setVisibility(View.VISIBLE);
            dataCompanyRelative.setVisibility(View.GONE);
        } else if (GlobalParams.sUserClass == 2){
            dataUserRelative.setVisibility(View.GONE);
            dataCompanyRelative.setVisibility(View.VISIBLE);
        } else if (GlobalParams.sUserClass == 0){
            dataUserRelative.setVisibility(View.VISIBLE);
            dataCompanyRelative.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.data_user_relative, R.id.data_company_relative})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, UploadDataActivity.class);

        switch (view.getId()) {
            case R.id.data_user_relative: //个人资料
                intent.putExtra("ActivityType", 2);
                break;

            case R.id.data_company_relative: //企业资料
                intent.putExtra("ActivityType", 3);
                break;
            default:
                break;
        }

        startActivity(intent);
    }

}
