package com.lulian.Zaiyunbao.ui.activity;


import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MARK on 2018/6/8.
 */

public class ProtocolActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_protocol;
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

        textDetailContent.setText("风险阅读");
    }

    @OnClick({R.id.agree_login, R.id.image_back_login_bar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.agree_login:
                backActivity();
                break;
            case R.id.image_back_login_bar:
                backActivity();
                break;
        }
    }

    private void backActivity() {
        Intent intent = new Intent();
        intent.putExtra("agreeLogin", true);
        setResult(22, intent);
        finish();
    }

}
