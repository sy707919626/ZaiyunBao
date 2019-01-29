package com.lulian.Zaiyunbao.ui.activity.issueSublease;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lulian.Zaiyunbao.Bean.IssueListBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


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
    TextView addressTextview;
    @BindView(R.id.listing_issue_location)
    RelativeLayout listingIssueLocation;
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

    @Override
    protected int setLayoutId() {
        return R.layout.activity_listing_issue;
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

        textDetailContent.setText("挂牌转租");
        textDetailRight.setVisibility(View.GONE);
        issueListBean = (IssueListBean) getIntent().getSerializableExtra("issueListBean");
        initView();

        listingIssueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listingIssueCount.getText().toString().trim().equals("")) {
                    RxToast.warning("请输入转租数量");
                } else if (Integer.valueOf(listingIssueCount.getText().toString().trim()) > issueListBean.getQuantity()) {
                    RxToast.warning("转租数量不能超过设备闲置库存数量");
                } else {
                    listingIssueYajin.setText(issueListBean.getDeposit() *
                            Integer.valueOf(listingIssueCount.getText().toString().trim()) + "元");

                    uploadData();
                }

            }
        });

        listingIssueLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAddress("");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS_LOCATION && resultCode == 11) { //选择地点返回值
            if (data != null) {
                addressTextview.setText(data.getStringExtra("addressAll"));
            }
        }
    }

    private void initView() {
        getDeposit();

        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(issueListBean.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            listingIssueImgPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

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


    }

    private void uploadData() {
        mApi.PublishAttornRent(GlobalParams.sToken, GlobalParams.sUserId, issueListBean.getId(),
                issueListBean.getQuantity(), Integer.valueOf(listingIssueCount.getText().toString().trim()))
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

                listingIssueYajin.setText(issueListBean.getDeposit() *
                        Integer.valueOf(listingIssueCount.getText().toString().trim()) + "元");

            }
        });
    }

}