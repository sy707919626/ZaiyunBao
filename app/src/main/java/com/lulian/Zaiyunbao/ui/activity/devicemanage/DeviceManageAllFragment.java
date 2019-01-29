package com.lulian.Zaiyunbao.ui.activity.devicemanage;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lulian.Zaiyunbao.Bean.DeviceManageBean;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.base.BaseLazyFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.lulian.Zaiyunbao.di.component.Constants.isAutoRefresh;

/**
 * Created by Administrator on 2018/11/6.
 */

public class DeviceManageAllFragment extends BaseLazyFragment {

    @BindView(R.id.Order_RecyclerView)
    RecyclerView OrderRecyclerView;
    @BindView(R.id.smartRefresh_Layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_select_num)
    TextView tvSelectNum;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.select_all)
    TextView selectAll;
    @BindView(R.id.ll_mycollection_bottom_dialog)
    LinearLayout llMycollectionBottomDialog;
    Unbinder unbinder;

    private boolean isRefresh; //刷新

    private int pageSize = 10;
    private int page = 1;

    private DeviceManageAdapter mAdapter;
    private ArrayList<DeviceManageBean.RowsBean> mDeviceManageBean = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_device_order;
    }

    @Override
    protected void init() {
        llMycollectionBottomDialog.setVisibility(View.GONE);

        OrderRecyclerView.setItemAnimator(new DefaultItemAnimator());
        OrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new DeviceManageAdapter(getContext(), mDeviceManageBean, true);
        OrderRecyclerView.setAdapter(mAdapter);

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

//        //设备编号
//        JSONObject EquipmentBaseNo = new JSONObject();
//        EquipmentBaseNo.put("name", "FormType");
//        EquipmentBaseNo.put("type", "=");
//        EquipmentBaseNo.put("value", "");
//
//        //使用状态
//        JSONObject UseStatus = new JSONObject();
//        UseStatus.put("name", "UseStatus");
//        UseStatus.put("type", "=");
//        UseStatus.put("value", "");

//        JSONObject[] filters = {EquipmentBaseNo, UseStatus};

        String[] beforePagingFilters = {GlobalParams.sUserId};
        JSONObject obj = new JSONObject();
        obj.put("Page", page);
        obj.put("Rows", pageSize);
        obj.put("BeforePagingFilters", beforePagingFilters);

        String messages = obj.toString();
        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                messages);


        mApi.MyEquipmentList(GlobalParams.sToken, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        DeviceManageBean DeviceManageList = MyApplication.get().getAppComponent().getGson().fromJson(s, DeviceManageBean.class);

                        if (isRefresh) {
                            mDeviceManageBean.clear();
                        }

                        mDeviceManageBean.addAll(DeviceManageList.getRows());

                        if (mAdapter != null) {
                            mAdapter.notifyDataSetChanged();
                        }

                        if (DeviceManageList.getRows().size() < pageSize) {
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
            isAutoRefresh = false;
        }
    }

}
