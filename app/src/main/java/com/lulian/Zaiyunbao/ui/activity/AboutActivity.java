package com.lulian.Zaiyunbao.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/10/22.
 */
@SuppressLint("Registered")
public class AboutActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.buy_policy_text)
    TextView buyPolicyText;
    @BindView(R.id.policy_bt)
    TextView policyBt;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_buy_policy;
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

        policyBt.setText("");
        textDetailContent.setText("关于我们");
        textDetailRight.setVisibility(View.GONE);
        String AA = "适用于载运保在售全部型号的“托盘”，“保温箱”，“周转筐”等产品\n" +
                "\n" +
                "本商品严格依据《中华人民共和国消费者权益保护法》、《中华人民共和国产品质量法》实行售后服务，服务内容如下：\n" +
                "\n" +
                "一、退换货政策\n" +
                "\n" +
                "1、   凡通过载运保商城或线下服务网点购买本产品的用户，可在自签收次日起七日内享受无理由退货服务。退货时用户须出示有效购买凭证，并退回发票。用户须保证退货商品保持原有品质和功能、外观完好、商品本身及配件的商标和各种标识完整齐全，如有赠品需一并退回。如果商品出现人为损坏、包装箱缺失、零配件缺失的情况，不予办理退货。退货时产生的物流费用由用户承担。\n" +
                "\n" +
                "自收到退货商品之日起七日内向用户返还已支付的货款。退款方式与付款方式相同。具体到账日期可能会受银行、支付机构等因素影响。\n" +
                "\n" +
                "2、   自用户签收次日起七日内，发生非人为损坏性能故障，经由载运保售后服务中心检测确认后，为用户办理退货业务，退货时用户须出示有效购买凭证，并退回发票。如有赠品需一并退回。\n" +
                "\n" +
                "3、   自用户签收次日起十五日内，发生非人为损坏性能故障，经由载运保售后服务中心检测确认后，为用户办理换货业务，更换整套商品。换货后，商品本身三包期重新计算。\n" +
                "\n" +
                "二、保修政策\n" +
                "\n" +
                "自用户签收次日起十五日后，发生非人为损坏性能故障，经由载运保售后服务中心检测确认后，为用户办理维修业务。\n" +
                "\n" +
                "保修期限 “托盘”，“保温箱”，“周转筐” 1年\n" +
                "\n" +
                "三、非保修条款\n" +
                "\n" +
                "以下情况不属于保修范围：\n" +
                "\n" +
                "1、  超过三包有效期限；\n" +
                "\n" +
                "2、   无保修卡、发票或保修卡与发票信息不符；\n" +
                "\n" +
                "3、   人为原因造成的损坏；\n" +
                "\n" +
                "4、   不可抗拒因素造成的损坏；\n" +
                "\n" +
                "5、   未按《用户手册》的要求使用、保养及调整造成的任何损坏；\n" +
                "\n" +
                "6、   超出正常使用条件，强行使用本产品造成的故障或损伤；\n" +
                "\n" +
                "7、   未经载运保公司授权的人员私自拆动或修理；\n" +
                "\n" +
                "8、   恶意损坏保修卡内容、产品信息，包括模糊破坏、自行撕毁、篡改等；\n" +
                "\n" +
                "9、   其他非产品本身设计、制造、质量等问题而导致的故障和损坏；\n" +
                "\n" +
                "四、售后服务收费标准\n" +
                "\n" +
                "物流费自行承担\n";

        buyPolicyText.setText(AA);
    }
}
