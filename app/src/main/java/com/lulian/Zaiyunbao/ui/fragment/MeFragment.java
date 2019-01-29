package com.lulian.Zaiyunbao.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.CircleImageView;
import com.lulian.Zaiyunbao.common.widget.item_view;
import com.lulian.Zaiyunbao.ui.activity.AboutActivity;
import com.lulian.Zaiyunbao.ui.activity.DataUserTypeActivity;
import com.lulian.Zaiyunbao.ui.activity.HelpFeedbackActivity;
import com.lulian.Zaiyunbao.ui.activity.MessagesListActivity;
import com.lulian.Zaiyunbao.ui.activity.MyDataActivity;
import com.lulian.Zaiyunbao.ui.activity.SettingActivity;
import com.lulian.Zaiyunbao.ui.activity.wallet.MyWalletActivity;
import com.lulian.Zaiyunbao.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by Administrator on 2018/7/29.
 */

public class MeFragment extends BaseFragment {
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.image_back_title_bar)
    ImageView imageBackTitleBar;
    @BindView(R.id.item_view_msg)
    ImageView itemViewMsg;
    @BindView(R.id.title_address_toolbar)
    Toolbar titleAddressToolbar;
    @BindView(R.id.my_cropimage_view)
    CircleImageView myCropimageView;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.mydata_relative)
    RelativeLayout mydataRelative;
    @BindView(R.id.me_txt_yuer)
    TextView meTxtYuer;
    @BindView(R.id.me_txt_yajin)
    TextView meTxtYajin;
    @BindView(R.id.my_qianbao_layout)
    LinearLayout myQianbaoLayout;
    @BindView(R.id.item_view_mydata)
    item_view itemViewMydata;
    @BindView(R.id.item_view_aboutme)
    item_view itemViewAboutme;
    @BindView(R.id.item_view_feedback)
    item_view itemViewFeedback;
    @BindView(R.id.item_view_install)
    item_view itemViewInstall;
    Unbinder unbinder;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_me;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void init() {
        ImmersionBar.with(this).statusBarView(R.id.top_view)
                .navigationBarColor(R.color.white)
                .fullScreen(false)
                .init();
        getData();

        //消息数目提示
        QBadgeView qbadge = new QBadgeView(getActivity());
        qbadge.setBadgeTextSize(12, true);
        qbadge.bindTarget(itemViewMsg).setBadgeGravity(Gravity.TOP | Gravity.END).setBadgeNumber(100);

        userName.setText(GlobalParams.sUserName);
        userPhone.setText(GlobalParams.sUserPhone);
    }

    @OnClick({R.id.mydata_relative,
            R.id.item_view_mydata, R.id.my_qianbao_layout, R.id.item_view_msg,
            R.id.item_view_aboutme, R.id.item_view_feedback, R.id.item_view_install})

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mydata_relative:
                Intent intentMydata = new Intent(getContext(), MyDataActivity.class);
                intentMydata.putExtra("myDataName", userName.getText().toString().trim());
                getContext().startActivity(intentMydata);
                break;

            case R.id.my_qianbao_layout:
                //钱包明细
                startActivity(new Intent(getContext(), MyWalletActivity.class));
                break;

            case R.id.item_view_msg:
                //我的消息
                getContext().startActivity(new Intent(getContext(), MessagesListActivity.class));
                break;

            case R.id.item_view_mydata:
                //我的资料
                getContext().startActivity(new Intent(getContext(), DataUserTypeActivity.class));
                break;

            case R.id.item_view_aboutme:
                getContext().startActivity(new Intent(getContext(), AboutActivity.class));
                break;

            case R.id.item_view_feedback:
                //反馈与帮助
                startActivity(new Intent(getContext(), HelpFeedbackActivity.class));
                break;

            case R.id.item_view_install:
                //设置
                Intent intent2 = new Intent(getContext(), SettingActivity.class);
                getActivity().startActivity(intent2);
                break;

        }
    }

    private void getData() {
        mApi.myMoneyInfo(GlobalParams.sToken, GlobalParams.sUserId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        JSONObject jsonObject = JSONObject.parseObject(s);
                        meTxtYuer.setText(jsonObject.getString("Balance") + "元");
                        meTxtYajin.setText(jsonObject.getString("Deposit") + "元");

                    }
                });
    }

}
