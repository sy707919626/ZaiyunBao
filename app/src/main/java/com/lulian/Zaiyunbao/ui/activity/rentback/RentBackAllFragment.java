package com.lulian.Zaiyunbao.ui.activity.rentback;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.lulian.Zaiyunbao.Bean.RentBackBean;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.base.BaseLazyFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/11/6.
 */

public class RentBackAllFragment extends BaseLazyFragment {
    @BindView(R.id.Order_RecyclerView)
    RecyclerView OrderRecyclerView;
    @BindView(R.id.smartRefresh_Layout)
    SmartRefreshLayout smartRefreshLayout;

    private boolean isRefresh; //刷新

    private int pageSize = 10;
    private int page = 1;

    private RentBackAdapter mAdapter;
    private ArrayList<RentBackBean.RowsBean> mOrderListBean = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void init() {
        OrderRecyclerView.setItemAnimator(new DefaultItemAnimator());
        OrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RentBackAdapter(getContext(), mOrderListBean, mApi);
        OrderRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RentBackAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, ArrayList<RentBackBean.RowsBean> orderListBean) {

            }
        });


        smartRefreshLayout.autoRefresh(); //触发自动刷新
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
    }

    @Override
    public void getData() {
        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }

        JSONObject FormType = new JSONObject();

        FormType.put("name", "Status");
        FormType.put("type", "=");
        FormType.put("value", "");
        JSONObject[] filters = {FormType};
        String[] beforePagingFilters = {GlobalParams.sUserId};

        JSONObject obj = new JSONObject();
        obj.put("Page", page);
        obj.put("Rows", pageSize);
        obj.put("BeforePagingFilters", beforePagingFilters);
//        obj.put("Filters", filters);

        String messages = obj.toString();
        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                messages);

        mApi.MyEquipmentBackRentOrderList(GlobalParams.sToken, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .compose(this.<String>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        RentBackBean myOrderLisetBean = MyApplication.get().getAppComponent().getGson().fromJson(s, RentBackBean.class);

                        if (isRefresh) {
                            mOrderListBean.clear();
                        }

                        mOrderListBean.addAll(myOrderLisetBean.getRows());

                        if (mAdapter != null) {
                            mAdapter.notifyDataSetChanged();
                        }

                        if (myOrderLisetBean.getRows().size() < pageSize) {
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

    @Override
    public void onResume() {
        super.onResume();
//        smartRefreshLayout.autoRefresh(); //触发自动刷新
    }

    @Override
    public void onPause() {
        super.onPause();
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadmore();
    }
}
