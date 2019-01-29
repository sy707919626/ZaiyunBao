package com.lulian.Zaiyunbao.ui.activity.seekrentorder;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.ReceivedLeaseBean;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/11/14.
 */

public class ReceiveSeekRentInfoActivity extends BaseActivity {
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.receive_sum)
    TextView receiveSum;
    @BindView(R.id.receive_lease_RecyclerView)
    RecyclerView receiveLeaseRecyclerView;
    @BindView(R.id.smart_Layout)
    SmartRefreshLayout smartLayout;
    @BindView(R.id.receive_lease_btn)
    Button receiveLeaseBtn;

    private ReceivedSeekRentAdapter mAdapter;
    private ArrayList<ReceivedLeaseBean.RowsBean> mReceivedLeaseList = new ArrayList<>();
    private int pageSize = 10;
    private int page = 1;
    private boolean isRefresh; //刷新

    private String OrdersId = "";

    @Override
    protected int setLayoutId() {
        return R.layout.activity_receive_lease;
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

        textDetailContent.setText("接收设备");
        textDetailRight.setVisibility(View.GONE);

        OrdersId = getIntent().getStringExtra("OrdersId");

        smartLayout.autoRefresh(); //触发自动刷新
        smartLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
                getData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isRefresh = true;
                smartLayout.setLoadmoreFinished(false); //再次触发加载更多事件
                getData();
            }
        });


        receiveLeaseRecyclerView.setItemAnimator(new DefaultItemAnimator());
        receiveLeaseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ReceivedSeekRentAdapter(this, mReceivedLeaseList);
        receiveLeaseRecyclerView.setAdapter(mAdapter);

        receiveLeaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus(OrdersId);
            }
        });

    }


    private void updateStatus(final String orderId) {
        mApi.ReceiveGood(GlobalParams.sToken, 1, orderId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        RxToast.success("订单收货成功");
                        Constants.setIsAutoRefresh(true);
                        finish();
                    }
                });
    }

    public void getData() {

        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }

        JSONObject obj = new JSONObject();
        obj.put("Page", page);
        obj.put("Rows", 10);
        String[] BeforePagingFilters = {OrdersId};
        obj.put("BeforePagingFilters", BeforePagingFilters);

        String args = obj.toString();
        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                args);

        mApi.myEquipmentRentOrderItem(GlobalParams.sToken, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        ReceivedLeaseBean receivedLeaseBean = MyApplication.get().getAppComponent().getGson().fromJson(s, ReceivedLeaseBean.class);


                        receiveSum.setText(receivedLeaseBean.getTotal() + "片");

                        if (isRefresh) {
                            mReceivedLeaseList.clear();
                        }

                        mReceivedLeaseList.addAll(receivedLeaseBean.getRows());

                        if (mAdapter != null) {
                            mAdapter.notifyDataSetChanged();
                        }

                        if (receivedLeaseBean.getRows().size() < pageSize) {
                            smartLayout.finishLoadmoreWithNoMoreData(); //将不会再次触发加载更多事件
                        }
                    }

                    @Override
                    protected void onAfter() {
                        super.onAfter();
                        smartLayout.finishRefresh();
                        smartLayout.finishLoadmore();
                    }
                });

    }

}
