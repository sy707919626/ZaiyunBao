package com.lulian.Zaiyunbao.ui.activity.seekrentorder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.lulian.Zaiyunbao.Bean.MyOrderLisetBean;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.ui.base.BaseLazyFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.lulian.Zaiyunbao.di.component.Constants.isAutoRefresh;

/**
 * Created by Administrator on 2018/11/6.
 */

@SuppressLint("ValidFragment")
public class SeekRentOrderFragment extends BaseLazyFragment {
    @BindView(R.id.Order_RecyclerView)
    RecyclerView OrderRecyclerView;
    @BindView(R.id.smartRefresh_Layout)
    SmartRefreshLayout smartRefreshLayout;

    private boolean isRefresh; //刷新

    private int pageSize = 10;
    private int page = 1;

    private SeekRentOrderAdapter mAdapter;
    private ArrayList<MyOrderLisetBean.RowsBean> mOrderListBean = new ArrayList<>();
    private String Status;

    public SeekRentOrderFragment(String status) {
        Status = status;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void init() {
        OrderRecyclerView.setItemAnimator(new DefaultItemAnimator());
        OrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        OrderRecyclerView.addItemDecoration(new RecyclerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new SeekRentOrderAdapter(getContext(), mOrderListBean, mApi, false);
        OrderRecyclerView.setAdapter(mAdapter);
        mOrderListBean.clear();

        mAdapter.setOnItemClickListener(new SeekRentOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, ArrayList<MyOrderLisetBean.RowsBean> orderListBean) {
                Intent intent = new Intent(getContext(), SeekRentOrderDetailsActivity.class);
                intent.putExtra("OrderNo", orderListBean.get(position).getOrderNo());
                intent.putExtra("OrdersId", orderListBean.get(position).getOrdersId());
                intent.putExtra("Id", orderListBean.get(position).getId()); //设备ID
                getContext().startActivity(intent);
            }
        });

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
    }

    @Override
    public void getData() {
        JSONObject[] filters;
        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }

        //未接单 = 0, 已接单 = 1,订单确认待支付 = 2,
        // 已支付待发货 = 3,已发货 = 5,已收货 = 6,退租中 = 7,已退租 = 8,已结算 = 9,已撤销 = 10


        JSONObject FormType = new JSONObject();
        FormType.put("name", "FormType");//订单类型1求租2租赁3转租
        FormType.put("type", "=");
        FormType.put("value", "1");

        if (!TextUtils.isEmpty(Status)) {
            JSONObject mStatus = new JSONObject();
            mStatus.put("name", "Status");
            mStatus.put("type", "=");
            mStatus.put("value", Status);

            filters = new JSONObject[]{mStatus, FormType};
        } else {

            filters = new JSONObject[]{FormType};
        }

        String[] beforePagingFilters = {GlobalParams.sUserId};

        JSONObject obj = new JSONObject();
        obj.put("Page", page);
        obj.put("Rows", pageSize);
        obj.put("BeforePagingFilters", beforePagingFilters);
        obj.put("Filters", filters);

        String messages = obj.toString();
        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                messages);


        mApi.myEquipmentRentOrderList(GlobalParams.sToken, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .compose(this.<String>bindUntilEvent(FragmentEvent.DESTROY))
                .compose(this.<String>bindUntilEvent(FragmentEvent.STOP))
                .compose(this.<String>bindUntilEvent(FragmentEvent.PAUSE))
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        MyOrderLisetBean myOrderLisetBean = MyApplication.get().getAppComponent().getGson().fromJson(s, MyOrderLisetBean.class);

                        if (isRefresh) {
                            mOrderListBean.clear();
                        }

                        mOrderListBean.addAll(myOrderLisetBean.getRows());
                        sort();

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
        if (isAutoRefresh) {
            isRefresh = true;
            getData();
//            smartRefreshLayout.autoRefresh();
            Constants.setIsAutoRefresh(false);
        }
    }


    private void sort() {
        //根据下单时间降序排列
        Collections.sort(mOrderListBean, new Comparator<MyOrderLisetBean.RowsBean>() {
            @Override
            public int compare(MyOrderLisetBean.RowsBean lhs, MyOrderLisetBean.RowsBean rhs) {
                Date date1 = stringToDate(lhs.getOrderCreateTime());
                Date date2 = stringToDate(rhs.getOrderCreateTime());
                // 对日期字段进行升序，如果欲降序可采用after方法
                if (date1.before(date2)) {
                    return 1;
                }
                return -1;
            }
        });
    }
}
