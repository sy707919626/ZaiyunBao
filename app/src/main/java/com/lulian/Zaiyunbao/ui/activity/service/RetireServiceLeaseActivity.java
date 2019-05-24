package com.lulian.Zaiyunbao.ui.activity.service;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.EquipmentListBean;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.activity.LeaseDetailActivity;
import com.lulian.Zaiyunbao.ui.adapter.EquipmentListAdapter;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/11/22.
 */

public class RetireServiceLeaseActivity extends BaseActivity {


    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.retire_service_RecyclerView)
    RecyclerView retireServiceRecyclerView;
    @BindView(R.id.retire_service_Layout)
    SmartRefreshLayout retireServiceLayout;
    private EquipmentListAdapter mAdapter;
    private ArrayList<EquipmentListBean.RowsBean> mEquipmentList = new ArrayList<>();
    private boolean isRefresh; //刷新

    private int pageSize = 10;
    private int page = 1;
    private String Name;
    private String StoreId;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_retire_service_lease;
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

        Name = getIntent().getStringExtra("Name");
        StoreId = getIntent().getStringExtra("StoreId");

        textDetailContent.setText(Name + "服务站点");
        textDetailRight.setVisibility(View.GONE);

        retireServiceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        retireServiceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        retireServiceRecyclerView.addItemDecoration(new RecyclerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new EquipmentListAdapter(this, mEquipmentList);
        retireServiceRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new EquipmentListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, EquipmentListBean.RowsBean equipmentList) {
                Intent intent = new Intent(RetireServiceLeaseActivity.this, LeaseDetailActivity.class);
                intent.putExtra("ShebeiId", equipmentList.getId());
                intent.putExtra("OperatorId", equipmentList.getOperator());
                intent.putExtra("StorehouseId", equipmentList.getStorehouseId());
                intent.putExtra("UID", equipmentList.getUID());
                startActivity(intent);
            }
        });

        isRefresh = true;
        getData();
//        retireServiceLayout.autoRefresh(); //触发自动刷新
        retireServiceLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
                getData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isRefresh = true;
                retireServiceLayout.setLoadmoreFinished(false); //再次触发加载更多事件
                getData();
            }
        });
    }


    private void getData() {
        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }

        JSONObject StorehouseId = new JSONObject(); //仓库id
        StorehouseId.put("name", "StorehouseId");
        StorehouseId.put("type", "=");
        StorehouseId.put("value", StoreId);
        JSONObject[] filters = {StorehouseId};

        JSONObject obj = new JSONObject();
        obj.put("Page", page);
        obj.put("Rows", 10);
        obj.put("Filters", filters);

        String messages = obj.toString();
        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                messages);

        mApi.equipmentList(GlobalParams.sToken, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .compose(this.<String>bindUntilEvent(ActivityEvent.STOP))
                .compose(this.<String>bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        EquipmentListBean equipmentListBean = MyApplication.get().getAppComponent().getGson().fromJson(s, EquipmentListBean.class);

                        if (isRefresh) {
                            mEquipmentList.clear();
                        }

                        mEquipmentList.addAll(equipmentListBean.getRows());

                        if (mAdapter != null) {
                            mAdapter.notifyDataSetChanged();
                        }

                        if (equipmentListBean.getRows().size() < pageSize) {
                            retireServiceLayout.finishLoadmoreWithNoMoreData(); //将不会再次触发加载更多事件
                        }
                    }

                    @Override
                    protected void onAfter() {
                        super.onAfter();
                        retireServiceLayout.finishRefresh();
                        retireServiceLayout.finishLoadmore();
                    }
                });

    }

}
