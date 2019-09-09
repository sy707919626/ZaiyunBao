package com.lulian.Zaiyunbao.ui.activity.issueSublease;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lulian.Zaiyunbao.Bean.IssueListBean;
import com.lulian.Zaiyunbao.Bean.LeasePriceFromBean;
import com.lulian.Zaiyunbao.Bean.SaleEntity;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static com.alibaba.fastjson.JSON.parseArray;


/**
 * Created by Administrator on 2018/11/26.
 */

public class ListingIssueActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.listing_issue_img_photo)
    ImageView listingIssueImgPhoto;
    @BindView(R.id.listing_issue_name)
    TextView listingIssueName;
    @BindView(R.id.listing_issue_spec)
    TextView listingIssueSpec;
    @BindView(R.id.listing_issue_load)
    TextView listingIssueLoad;
    @BindView(R.id.listing_issue_sum)
    TextView listingIssueSum;
    @BindView(R.id.address_linearLayout)
    TextView addressLinearLayout;
    @BindView(R.id.address_textview)
    ClearEditText addressTextview;
    @BindView(R.id.liearLayout_sum)
    TextView liearLayoutSum;
    @BindView(R.id.listing_issue_count)
    ClearEditText listingIssueCount;
    @BindView(R.id.time_linearLayout)
    TextView timeLinearLayout;
    @BindView(R.id.listing_issue_time)
    TextView listingIssueTime;
    @BindView(R.id.yajin_linearLayout)
    TextView yajinLinearLayout;
    @BindView(R.id.listing_issue_yajin)
    TextView listingIssueYajin;
    @BindView(R.id.listing_issue_btn)
    Button listingIssueBtn;


    private IssueListBean issueListBean;
    private int YaJin = 0;
    private Handler mHandler;

    public IssueListBean getIssueListBean() {
        return issueListBean;
    }

    public void setIssueListBean(IssueListBean issueListBean) {
        this.issueListBean = issueListBean;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_listing_issue;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void init() {
        ImmersionBar.with(this)
                .titleBar(R.id.detail_bar_title)
                .titleBarMarginTop(R.id.detail_bar_title)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        textDetailContent.setText("挂牌转租");
        textDetailRight.setVisibility(View.GONE);
        issueListBean = (IssueListBean) getIntent().getSerializableExtra("issueListBean");

        initView();


        listingIssueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(addressTextview.getText().toString().trim())) {
                    RxToast.warning("请输入设备所在地");
                } else if (TextUtils.isEmpty(listingIssueCount.getText().toString().trim())) {
                    RxToast.warning("请输入转租数量");
                } else if (Integer.valueOf(listingIssueCount.getText().toString().trim()) > issueListBean.getQuantity()) {
                    RxToast.warning("转租数量不能超过设备闲置库存数量");
                } else if (TextUtils.isEmpty(listingIssueYajin.getText().toString().trim())) {
                    RxToast.warning("请输入转租数量");
                } else {
                    uploadData();
                }

            }
        });
    }

    //获取押金
    private void getYajin() {
//        mApi.rentPriceListPoint(GlobalParams.sToken, issueListBean.getId(), issueListBean.getOperator(), 1,
//                Integer.valueOf(listingIssueCount.getText().toString().trim()))
//                .compose(RxHttpResponseCompat.<String>compatResult())
//                .subscribe(new ErrorHandlerSubscriber<String>() {
//                    @Override
//                    public void onNext(String s) {
//                        List<LeasePriceFromBean> list = parseArray(s, LeasePriceFromBean.class);
//                        if (list.size() > 0) {
//                            DecimalFormat decimalFormat = new DecimalFormat("0.00");
//                            float freeDayMoney = list.get(0).getPrice() * Float.valueOf(list.get(0).getFreeDay());
//                            YaJin = list.get(0).getDeposit();
//
//                            listingIssueYajin.setText(YaJin *
//                                    Integer.valueOf(listingIssueCount.getText().toString().trim()) + "元");
//                        } else {
//                            YaJin = 0;
//                            listingIssueYajin.setText("0");
//                        }
//                    }
//                });

      if (Integer.valueOf(listingIssueCount.getText().toString().trim()) <= 0){
            RxToast.warning("转租数量不能小于或等于0");
        } else {
            mApi.rentPriceList(GlobalParams.sToken, issueListBean.getId(), issueListBean.getOperator(), 1,
                    Integer.valueOf(listingIssueCount.getText().toString().trim()), 0)
                    .compose(RxHttpResponseCompat.<String>compatResult())
                    .subscribe(new ErrorHandlerSubscriber<String>() {
                        @Override
                        public void onNext(String s) {
                            List<LeasePriceFromBean> list = parseArray(s, LeasePriceFromBean.class);
                            if (list.size() > 0) {
                                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                                float freeDayMoney = list.get(0).getPrice() * Float.valueOf(list.get(0).getFreeDay());
                                YaJin = list.get(0).getDeposit();

                                listingIssueYajin.setText(YaJin *
                                        Integer.valueOf(listingIssueCount.getText().toString().trim()) + "元");
                            } else {
                                YaJin = 0;
                                listingIssueYajin.setText("0");
                            }
                        }
                    });
        }
    }

    private void initView() {
        listingIssueCount.setText(issueListBean.getQuantity()+"");

//        try {
//            byte[] bitmapArray;
//            bitmapArray = Base64.decode(issueListBean.getPicture(), Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
//                    bitmapArray.length);
//            listingIssueImgPhoto.setImageBitmap(bitmap);
//        } catch (Exception e) {
//        }

        Glide.with(mContext).load(Constants.BASE_URL +"/" + issueListBean.getPicture()).into(listingIssueImgPhoto);

        listingIssueName.setText(issueListBean.getEquipmentName());
        listingIssueSpec.setText(issueListBean.getNorm());

        if (issueListBean.getTypeName().equals("保温箱")) {
            listingIssueLoad.setText("容积" + String.valueOf(issueListBean.getVolume()) + "升；保温时长"
                    + String.valueOf(issueListBean.getWarmLong()) + "小时");
        } else {
            listingIssueLoad.setText("静载" + String.valueOf(issueListBean.getStaticLoad()) + "T；动载"
                    + String.valueOf(issueListBean.getCarryingLoad()) + "T；架载" + String.valueOf(issueListBean.getOnLoad()) + "T");
        }

        listingIssueSum.setText(String.valueOf(issueListBean.getQuantity()) + "");

//        listingIssueCount.setText(String.valueOf(issueListBean.getQuantity()) + "个");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        listingIssueTime.setText(formatter.format(curDate));

        getDeposit();
    }

    private void uploadData() {
        mApi.PublishAttornRent(GlobalParams.sToken, GlobalParams.sUserId, issueListBean.getId(),
                issueListBean.getQuantity(),
                Integer.valueOf(listingIssueCount.getText().toString().trim()), addressTextview.getText().toString().trim())
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        RxToast.success("转租订单发布成功");
                        finish();
                    }
                });
    }

    private void getDeposit() {
        RxTextView.textChanges(listingIssueCount)
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(@NonNull CharSequence charSequence) throws Exception {
                        return charSequence.toString().trim().length() > 0;
                    }
                }).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(@NonNull CharSequence charSequence) throws Exception {

                getYajin();

            }
        });
    }
}
