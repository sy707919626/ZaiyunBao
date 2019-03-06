package com.lulian.Zaiyunbao.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.WalletListDetails;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompatTest;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/10/26.
 */

public class Wallet_Details_Activity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.wallet_details_money)
    TextView walletDetailsMoney;
    @BindView(R.id.wallet_details_zffs)
    TextView walletDetailsZffs;
    @BindView(R.id.wallet_details_zh_text)
    TextView walletDetailsZhText;
    @BindView(R.id.wallet_details_zh)
    TextView walletDetailsZh;
    @BindView(R.id.wallet_details_jylx)
    TextView walletDetailsJylx;
    @BindView(R.id.wallet_details_jyzt)
    TextView walletDetailsJyzt;
    @BindView(R.id.wallet_details_jy_time)
    TextView walletDetailsJyTime;
    @BindView(R.id.wallet_details_number)
    TextView walletDetailsNumber;

    private List<WalletListDetails> mLists = new ArrayList<>();
    private WalletListDetails mWalletListDetails;

    private String FlowId;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_walle_detils;
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

        textDetailContent.setText("交易详情");
        textDetailRight.setVisibility(View.GONE);
        FlowId = getIntent().getStringExtra("FlowId");
        getData();

    }

    private void getData(){
//        mApi.TradeFlowItem(GlobalParams.sToken, FlowId, GlobalParams.sUserId)
//                .compose(RxHttpResponseCompatTest.<String>compatResult())
//                .subscribe(new ErrorHandlerSubscriber<String>() {
//                    @Override
//                    public void onNext(String s) {
//                        mLists = JSONObject.parseArray(s, WalletListDetails.class);
//                        mWalletListDetails = mLists.get(0);
//                        initView();
//                    }
//                });

        mApi.TradeFlowItem(GlobalParams.sToken, FlowId, GlobalParams.sUserId)
                .compose(RxHttpResponseCompatTest.<List<WalletListDetails>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<WalletListDetails>>() {
                    @Override
                    public void onNext(List<WalletListDetails> walletListDetails) {
                        mWalletListDetails = walletListDetails.get(0);
                        initView();
                    }
                });

//        mApi.TradeFlowItem(GlobalParams.sToken, FlowId, GlobalParams.sUserId)
//                .subscribe(new Test<List<WalletListDetails>>() {
//                    @Override
//                    public void onSucess(BaseBean<List<WalletListDetails>> baseBean) {
//                        mWalletListDetails = baseBean.getData().get(0);
//                        initView();
//                    }
//                });
    }

    private void initView() {
        //支出
        if (mWalletListDetails.getCashOrPay() == 2){
            walletDetailsMoney.setText("-" + mWalletListDetails.getMonetary());
            walletDetailsZhText.setText("支出账户");
        } else {
            walletDetailsMoney.setText("+" + mWalletListDetails.getMonetary());
            walletDetailsZhText.setText("收入账户");
        }
        walletDetailsJylx.setText(mWalletListDetails.getTradSum());
        walletDetailsJyTime.setText(mWalletListDetails.getTradTime());
        walletDetailsNumber.setText(mWalletListDetails.getSerialNo());
        walletDetailsZh.setText(mWalletListDetails.getAccountNo());
    }
}
