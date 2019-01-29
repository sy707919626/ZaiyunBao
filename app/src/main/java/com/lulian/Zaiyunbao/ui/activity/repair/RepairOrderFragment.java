package com.lulian.Zaiyunbao.ui.activity.repair;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.lulian.Zaiyunbao.Bean.RepairBean;
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
public class RepairOrderFragment extends BaseLazyFragment {
    @BindView(R.id.Order_RecyclerView)
    RecyclerView OrderRecyclerView;
    @BindView(R.id.smartRefresh_Layout)
    SmartRefreshLayout smartRefreshLayout;

    private boolean isRefresh; //刷新
    private int pageSize = 10;
    private int page = 1;

    private String Status;
    private RepairOrderAdapter mAdapter;
    private ArrayList<RepairBean.RowsBean> mRepairBeanList = new ArrayList<>();

    public RepairOrderFragment(String status) {
        this.Status = status;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void init() {
        OrderRecyclerView.setItemAnimator(new DefaultItemAnimator());
        OrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RepairOrderAdapter(getContext(), mRepairBeanList);
        OrderRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RepairOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, ArrayList<RepairBean.RowsBean> orderListBean) {
                Intent intent = new Intent(getContext(), RepairOrderDetailActivity.class);
                intent.putExtra("RepairId", orderListBean.get(position).getId());
                intent.putExtra("Status", orderListBean.get(position).getStatus());
                getContext().startActivity(intent);
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
        JSONObject[] filters;
        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }


        JSONObject mStatus = new JSONObject();
        mStatus.put("name", "Status");
        mStatus.put("type", "=");
        mStatus.put("value", Status);
        filters = new JSONObject[]{mStatus};

        String[] beforePagingFilters = {GlobalParams.sUserId};

        JSONObject obj = new JSONObject();
        obj.put("Page", page);
        obj.put("Rows", pageSize);
        obj.put("BeforePagingFilters", beforePagingFilters);
        obj.put("Filters", filters);

        String messages = obj.toString();
        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                messages);


        mApi.RepairList(GlobalParams.sToken, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        RepairBean mRepairBean = MyApplication.get().getAppComponent().getGson().fromJson(s, RepairBean.class);

                        if (isRefresh) {
                            mRepairBeanList.clear();
                        }

                        mRepairBeanList.addAll(mRepairBean.getRows());
                        sort();
                        if (mAdapter != null) {
                            mAdapter.notifyDataSetChanged();
                        }

                        if (mRepairBean.getRows().size() < pageSize) {
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
            smartRefreshLayout.autoRefresh();

            Constants.setIsAutoRefresh(false);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadmore();
    }

    private void sort() {
        //根据下单时间降序排列
        Collections.sort(mRepairBeanList, new Comparator<RepairBean.RowsBean>() {
            @Override
            public int compare(RepairBean.RowsBean lhs, RepairBean.RowsBean rhs) {
                Date date1 = stringToDate(lhs.getCreateTime());
                Date date2 = stringToDate(rhs.getCreateTime());
                // 对日期字段进行升序，如果欲降序可采用after方法
                if (date1.before(date2)) {
                    return 1;
                }
                return -1;
            }
        });
    }

}
