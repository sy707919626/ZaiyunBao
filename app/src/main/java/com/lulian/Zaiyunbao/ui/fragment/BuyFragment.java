package com.lulian.Zaiyunbao.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.BuyListBean;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.event.BuyEvent;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.activity.BuyDetailActivity;
import com.lulian.Zaiyunbao.ui.adapter.BuyListAdapter;
import com.lulian.Zaiyunbao.ui.base.BaseLazyFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/7/29.
 */

public class BuyFragment extends BaseLazyFragment {
    @BindView(R.id.image_back_title_bar)
    ImageView imageBackTitleBar;
    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.title_buy_toolbar)
    Toolbar titleBuyToolbar;
    @BindView(R.id.service_recycler_view)
    RecyclerView serviceRecyclerView;
    @BindView(R.id.smartRefresh_Layout)
    SmartRefreshLayout smartRefreshLayout;

    private boolean isRefresh; //刷新
    private int pageSize = 10;
    private int page = 1;
    private ArrayList<BuyListBean.RowsBean> mBuyList = new ArrayList<>();
    private BuyListAdapter mAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_buy;
    }

    @Override
    protected void init() {
        ImmersionBar.with(getActivity())
                .titleBar(R.id.title_buy_toolbar)
                .navigationBarColor(R.color.toolbarBG)
                .init();

        EventBus.getDefault().register(this);
        textContent.setText("购买");
        imageBackTitleBar.setVisibility(View.GONE);

        isRefresh = true;
        getData();
//        smartRefreshLayout.autoRefresh(); //触发自动刷新
        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
                getData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isRefresh = true;
                smartRefreshLayout.setLoadmoreFinished(false); //再次触发加载更多事件
                getData();
            }
        });

        serviceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        serviceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BuyListAdapter(getContext(), mBuyList);
        serviceRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BuyListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, BuyListBean.RowsBean buyListBean) {
                Intent intent = new Intent(getContext(), BuyDetailActivity.class);
                buyListBean.setPicture(""); //图片太大无法传递
                intent.putExtra("buyDetail", buyListBean);
                startActivity(intent);
                hashCode();
            }
        });
    }

    @Override
    public void getData() {
        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }

//        JSONObject FormType = new JSONObject();
//        FormType.put("name", "FormType");
//        FormType.put("type", "=");
//        FormType.put("value", "3");
//        JSONObject[] filters = {FormType};

        String[] beforePagingFilters = {GlobalParams.sUserId};
        JSONObject obj = new JSONObject();
        obj.put("Page", page);
        obj.put("Rows", 10);
        obj.put("BeforePagingFilters", beforePagingFilters);
//        obj.put("Filters", filters);

        String messages = obj.toString();
        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                messages);

        mApi.EquipmentBuyList(GlobalParams.sToken, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .compose(this.<String>bindUntilEvent(FragmentEvent.DESTROY))
                .compose(this.<String>bindUntilEvent(FragmentEvent.STOP))
                .compose(this.<String>bindUntilEvent(FragmentEvent.PAUSE))
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        BuyListBean buyListBean = MyApplication.get().getAppComponent().getGson().fromJson(s, BuyListBean.class);

                        if (isRefresh) {
                            mBuyList.clear();
                        }

                        mBuyList.addAll(buyListBean.getRows());

                        if (mAdapter != null) {
                            mAdapter.notifyDataSetChanged();
                        }

                        if (buyListBean.getRows().size() < pageSize) {
                            smartRefreshLayout.finishLoadmoreWithNoMoreData(); //将不会再次触发加载更多事件
                        }
                    }

                    @Override
                    protected void onAfter() {
                        super.onAfter();
                        smartRefreshLayout.finishRefresh();
                        smartRefreshLayout.finishLoadmore();
                    }
                });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BuyEvent event) {
//        smartRefreshLayout.autoRefresh(); //触发自动刷新
        isRefresh = true;
        getData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
