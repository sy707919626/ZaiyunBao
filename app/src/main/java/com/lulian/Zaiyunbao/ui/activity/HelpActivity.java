package com.lulian.Zaiyunbao.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.widget.CircleImageView;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/1/17.
 */

public class HelpActivity extends BaseActivity {


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
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.mydata_relative)
    RelativeLayout mydataRelative;
    @BindView(R.id.forget_pwd_text)
    TextView forgetPwdText;
    @BindView(R.id.cannot_code)
    TextView cannotCode;
    @BindView(R.id.untying_phone)
    TextView untyingPhone;
    @BindView(R.id.update_phone)
    TextView updatePhone;
    @BindView(R.id.other_problem)
    TextView otherProblem;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_help;
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

    }

    @OnClick({R.id.forget_pwd_text, R.id.cannot_code, R.id.untying_phone,
            R.id.update_phone, R.id.other_problem})

    public void onViewClicked(View view) {
        Intent intent = new Intent(this, HelpDetailActivity.class);

        switch (view.getId()) {
            case R.id.forget_pwd_text:
                //忘记密码
                intent.putExtra("problemTitle", forgetPwdText.getText().toString().trim());
                intent.putExtra("type", 1);
                startActivity(intent);
                break;

            case R.id.cannot_code:
                //收不到短信
                intent.putExtra("problemTitle", cannotCode.getText().toString().trim());
                intent.putExtra("type", 2);
                startActivity(intent);
                break;

            case R.id.untying_phone:
                //解绑手机
                intent.putExtra("problemTitle", untyingPhone.getText().toString().trim());
                intent.putExtra("type", 3);
                startActivity(intent);
                break;

            case R.id.update_phone:
                //修改手机号码
                intent.putExtra("problemTitle", updatePhone.getText().toString().trim());
                intent.putExtra("type", 4);
                startActivity(intent);
                break;

            case R.id.other_problem:
                //其他问题
                intent.putExtra("problemTitle", otherProblem.getText().toString().trim());
                intent.putExtra("type", 5);
                startActivity(intent);
                break;

        }
    }
}
