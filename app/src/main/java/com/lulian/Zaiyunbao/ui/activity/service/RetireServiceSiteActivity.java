package com.lulian.Zaiyunbao.ui.activity.service;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lulian.Zaiyunbao.Bean.RetireServiceSiteBean;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/11/22.
 */

public class RetireServiceSiteActivity extends BaseActivity {

    @BindView(R.id.tv_service_address)
    TextView tvServiceAddress;
    @BindView(R.id.text_service_content)
    TextView textServiceContent;
    @BindView(R.id.iv_service_ditu)
    TextView ivServiceDitu;
    @BindView(R.id.title_searvice_toolbar)
    Toolbar titleSearviceToolbar;
    @BindView(R.id.retire_bar_search)
    TextView retireBarSearch;
    @BindView(R.id.retire_bar_empty)
    ImageView retireBarEmpty;
    @BindView(R.id.retire_recyclerview)
    RecyclerView retireRecyclerview;
    @BindView(R.id.retire_smartRefresh_Layout)
    SmartRefreshLayout retireSmartRefreshLayout;

    private RetireServiceSiteAdapter mAdapter;
    private ArrayList<RetireServiceSiteBean.RowsBean> mRetireServiceSiteList = new ArrayList<>();
    private boolean isRefresh; //刷新

    private int pageSize = 10;
    private int page = 1;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_retire_service_site;
    }

    @Override
    protected void init() {
        textServiceContent.setText("服务站点");

        retireRecyclerview.setItemAnimator(new DefaultItemAnimator());
        retireRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RetireServiceSiteAdapter(this, mRetireServiceSiteList);
        retireRecyclerview.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RetireServiceSiteAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, RetireServiceSiteBean.RowsBean retireServiceSiteList) {
                Intent intent = new Intent(RetireServiceSiteActivity.this, RetireServiceLeaseActivity.class);
                intent.putExtra("StoreId", retireServiceSiteList.getId());
                intent.putExtra("Name", retireServiceSiteList.getName());
                startActivity(intent);
            }
        });


        retireSmartRefreshLayout.autoRefresh(); //触发自动刷新
        retireSmartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
                getData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isRefresh = true;
                retireSmartRefreshLayout.setLoadmoreFinished(false); //再次触发加载更多事件
                getData();
            }
        });
    }


    @OnClick({R.id.tv_service_address, R.id.iv_service_ditu, R.id.retire_bar_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_service_address:
                //选择定位
                changeAddress(tvServiceAddress.getText().toString().trim());
                break;

            case R.id.iv_service_ditu:
                //地图
                RxToast.warning("地图");
                break;

            case R.id.retire_bar_search:
                //搜索
                startActivity(new Intent(this, RetireServiceSiteSearchActivity.class));
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS_LOCATION && resultCode == 11) { //选择地点返回值
            if (data != null) {
                tvServiceAddress.setText(data.getStringExtra("addressSelect"));
            }
        }
    }

    private void getData() {
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

        mApi.storeHouseList(GlobalParams.sToken, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        RetireServiceSiteBean retireServiceSiteListBean = MyApplication.get().getAppComponent().getGson().fromJson(s, RetireServiceSiteBean.class);

                        if (isRefresh) {
                            mRetireServiceSiteList.clear();
                        }

                        mRetireServiceSiteList.addAll(retireServiceSiteListBean.getRows());

                        if (mAdapter != null) {
                            mAdapter.notifyDataSetChanged();
                        }

                        if (retireServiceSiteListBean.getRows().size() < pageSize) {
                            retireSmartRefreshLayout.finishLoadmoreWithNoMoreData(); //将不会再次触发加载更多事件
                        }
                    }

                    @Override
                    protected void onAfter() {
                        super.onAfter();
                        retireSmartRefreshLayout.finishRefresh();
                        retireSmartRefreshLayout.finishLoadmore();
                    }
                });
    }

}
