package com.lulian.Zaiyunbao.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.WalletDetail;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.adapter.WalletDetailListAdapter;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/10/26.
 */

@SuppressLint("Registered")
public class Wallet_Detail_ListActivity extends BaseActivity {
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.wallet_recyclerview)
    RecyclerView walletRecyclerview;
    @BindView(R.id.wallet_smartRefresh_Layout)
    SmartRefreshLayout walletSmartRefreshLayout;

    private WalletDetailListAdapter mAdapter;
    private ArrayList<WalletDetail.RowsBean> mWalletDetailList = new ArrayList<>();
    private boolean isRefresh; //刷新

    private int pageSize = 10;
    private int page = 1;

    public static Date stringToDate(String dateString) {
        if (TextUtils.isEmpty(dateString)) {
            dateString = "1900-01-01 00:00:00";
        }

        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateValue = simpleDateFormat.parse(dateString, position);
        return dateValue;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_walle_detil_list;
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

        textDetailContent.setText("钱包收支明细");
        textDetailRight.setVisibility(View.GONE);

        initView();
        getWalletData();
        walletSmartRefreshLayout.autoRefresh(); //触发自动刷新

        walletSmartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
                getWalletData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isRefresh = true;
                walletSmartRefreshLayout.setLoadmoreFinished(false); //再次触发加载更多事件
                getWalletData();
            }
        });
    }

    private void initView() {
        walletRecyclerview.setItemAnimator(new DefaultItemAnimator());
        walletRecyclerview.setLayoutManager(new LinearLayoutManager(this));
//        messageRecyclerview.addItemDecoration(new RecyclerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new WalletDetailListAdapter(this, mWalletDetailList);
        walletRecyclerview.setAdapter(mAdapter);
    }

    /**
     * 获取消息列表
     */
    private void getWalletData() {
        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }

        String[] beforePagingFilters = {GlobalParams.sUserId};

        JSONObject obj = new JSONObject();
        obj.put("Page", page);
        obj.put("Rows", 10);
        obj.put("BeforePagingFilters", beforePagingFilters);
        String messages = obj.toString();
        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                messages);

        mApi.MyTradeFlowList(GlobalParams.sToken, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        WalletDetail walletDetailBean = MyApplication.get().getAppComponent().getGson().fromJson(s, WalletDetail.class);

                        if (isRefresh) {
                            mWalletDetailList.clear();
                        }

                        mWalletDetailList.addAll(walletDetailBean.getRows());
                        sort();

                        if (mAdapter != null) {
                            mAdapter.notifyDataSetChanged();
                        }

                        if (walletDetailBean.getRows().size() < pageSize) {
                            walletSmartRefreshLayout.finishLoadmoreWithNoMoreData(); //将不会再次触发加载更多事件
                        }
                    }

                    @Override
                    protected void onAfter() {
                        super.onAfter();
                        walletSmartRefreshLayout.finishRefresh();
                        walletSmartRefreshLayout.finishLoadmore();
                    }
                });
    }

    private void sort() {
        //根据下单时间降序排列
        Collections.sort(mWalletDetailList, new Comparator<WalletDetail.RowsBean>() {
            @Override
            public int compare(WalletDetail.RowsBean lhs, WalletDetail.RowsBean rhs) {
                Date date1 = stringToDate(lhs.getTradTime());
                Date date2 = stringToDate(rhs.getTradTime());
                // 对日期字段进行升序，如果欲降序可采用after方法
                if (date1.before(date2)) {
                    return 1;
                }
                return -1;
            }
        });
    }
}
