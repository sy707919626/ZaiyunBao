package com.lulian.Zaiyunbao.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.widget.ProjectUtil;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/1/17.
 */

public class HelpDetailActivity extends BaseActivity {


    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.problem_title)
    TextView problemTitle;
    @BindView(R.id.problem_content)
    TextView problemContent;
    @BindView(R.id.contact_service)
    Button contactService;
    @BindView(R.id.know_sure)
    Button knowSure;

    private int type;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_help_detail;
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

        textDetailContent.setText("在线客服");
        textDetailRight.setVisibility(View.GONE);

        problemTitle.setText(getIntent().getStringExtra("problemTitle"));
        type = getIntent().getIntExtra("type", 0);


        if (type == 1) {
            problemContent.setText(R.string.forger_pwd);
        } else if (type == 2) {
            problemContent.setText(R.string.cannot_code);
        } else if (type == 3) {
            problemContent.setText(R.string.untying_phone);
        } else if (type == 4) {
            problemContent.setText(R.string.update_phone);
        } else if (type == 5) {
            problemContent.setText(R.string.other_problem);
        }
    }

    @OnClick({R.id.contact_service, R.id.know_sure})

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contact_service:
                //联系客服
                ProjectUtil.checkCallPhone(this, "400-564654654");
                break;

            case R.id.know_sure:
                //知道了
                finish();
                break;

        }
    }


}
