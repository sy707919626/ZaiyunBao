package com.lulian.Zaiyunbao.ui.activity.devicemanage;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.ui.activity.PermissionsActivity;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.yzq.zxinglibrary.common.Constant;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/11/6.
 */

public class DeviceManageActivity extends BaseActivity {
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.image_detail_right)
    ImageView imageDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.tab_device_manage_layout)
    TabLayout tabDeviceManageLayout;
    @BindView(R.id.view_device_manage_pager)
    ViewPager viewDeviceManagePager;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_device_manage;
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

        textDetailContent.setText("设备管理");

        PagerAdapter adapter = new DeviceManageViewPagerAdapter(this.getSupportFragmentManager());
        viewDeviceManagePager.setAdapter(adapter);
        tabDeviceManageLayout.setupWithViewPager(viewDeviceManagePager);
        tabDeviceManageLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        imageDetailRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) { //二维码扫描
            Toast.makeText(this, "没有权限无法扫描呦", Toast.LENGTH_LONG).show();
        } else if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);

                //跳转到详情界面
                Intent intent = new Intent(mContext, DeviceManageDetailsActivity.class);
                intent.putExtra("ECode", content);
                mContext.startActivity(intent);
            }
        }
    }

}
