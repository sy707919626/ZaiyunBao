package com.lulian.Zaiyunbao.ui.activity.devicemanage;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
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
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseLazyFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.lulian.Zaiyunbao.di.component.Constants.isAutoRefresh;

/**
 * Created by Administrator on 2018/11/6.
 */

public class DeviceManageXZFragment extends BaseLazyFragment {


    private static final int MYLIVE_MODE_CHECK = 0;

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
    private int index = 0;
    private int mEditMode = MYLIVE_MODE_CHECK;
    private boolean isSelectAll = false;
    private boolean editorStatus = false;
    private List<String> IdList = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_device_order;
    }

    @Override
    protected void init() {
        OrderRecyclerView.setItemAnimator(new DefaultItemAnimator());
        OrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new DeviceManageAdapter(getContext(), mDeviceManageBean, false);
        OrderRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new DeviceManageAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, ArrayList<DeviceManageBean.RowsBean> DeviceManageListBean) {
                if (editorStatus) {
                    DeviceManageBean.RowsBean myLive = DeviceManageListBean.get(position);
                    boolean isSelect = myLive.isSelect();
                    if (!isSelect) {
                        index++;
                        myLive.setSelect(true);
                        if (index == DeviceManageListBean.size()) {
                            isSelectAll = true;
                            selectAll.setText("取消全选");
                            Drawable drawableLeft = getResources().getDrawable(
                                    R.drawable.un_all_select);

                            selectAll.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                                    null, null, null);
                        }

                    } else {
                        myLive.setSelect(false);
                        index--;
                        isSelectAll = false;
                        selectAll.setText("全选");

                        Drawable drawableLeft = getResources().getDrawable(
                                R.drawable.all_select);

                        selectAll.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                                null, null, null);
                    }
                    setBtnBackground(index);
                    tvSelectNum.setText(String.valueOf(index));
                    mAdapter.notifyDataSetChanged();
                }

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

        initView();
    }

    private void initView() {
        btnUpdate.setText("占用");

        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAllMain();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateVideo();
            }
        });

        llMycollectionBottomDialog.setVisibility(View.VISIBLE);
        editorStatus = true;
        clearAll();
    }

    @Override
    public void getData() {
        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }

        //使用状态6=闲置 3=载物
        JSONObject UseStatus = new JSONObject();
        UseStatus.put("name", "UseStatus");
        UseStatus.put("type", "=");
        UseStatus.put("value", "6");

        JSONObject[] filters = {UseStatus};

        String[] beforePagingFilters = {GlobalParams.sUserId};
        JSONObject obj = new JSONObject();
        obj.put("Page", page);
        obj.put("Rows", pageSize);
        obj.put("BeforePagingFilters", beforePagingFilters);
        obj.put("filters", filters);

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

    private void clearAll() {
        tvSelectNum.setText(String.valueOf(0));
        isSelectAll = false;
        selectAll.setText("全选");
        setBtnBackground(0);
    }

    /**
     * 根据选择的数量是否为0来判断按钮的是否可点击.
     *
     * @param size
     */
    private void setBtnBackground(int size) {
        btnUpdate.setBackgroundResource(R.drawable.button_shape);
        if (size != 0) {
            btnUpdate.setEnabled(true);
        } else {
            btnUpdate.setEnabled(false);
        }
    }


    /**
     * 修改逻辑
     */
    private void updateVideo() {
        IdList.clear();

        if (index == 0) {
            btnUpdate.setEnabled(false);
            return;
        }
        final AlertDialog builder = new AlertDialog.Builder(this.getContext())
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = builder.findViewById(R.id.tv_msg);
        Button cancle = builder.findViewById(R.id.btn_cancle);
        Button sure = builder.findViewById(R.id.btn_sure);
        msg.setGravity(Gravity.CENTER);

        if (msg == null || cancle == null || sure == null) return;

        if (index == 1) {
            msg.setText("更改为占用状态后不可恢复，是否修改该条目？");
        } else {
            msg.setText("更改为占用状态后不可恢复，是否修改这" + index + "个条目？");
        }


        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = mAdapter.getMyLiveList().size(), j = 0; i > j; i--) {
                    DeviceManageBean.RowsBean myLive = mAdapter.getMyLiveList().get(i - 1);
                    if (myLive.isSelect()) {
                        mAdapter.getMyLiveList().remove(myLive);
                        IdList.add(myLive.getId());
                        index--;
                    }
                }
                index = 0;
                tvSelectNum.setText(String.valueOf(0));
                setBtnBackground(index);
//                if (mAdapter.getMyLiveList().size() == 0) {
//                    llMycollectionBottomDialog.setVisibility(View.GONE);
//                    mEditMode = MYLIVE_MODE_CHECK;
//                    btnEdit.setText("编辑");
//                }
                mAdapter.notifyDataSetChanged();

                //修改状态
                MyEquipmentOpt(IdList);

                builder.dismiss();
            }
        });
    }

    //使用状态6=闲置 3=载物
    private void MyEquipmentOpt(List<String> Ids) {
        String[] arr = Ids.toArray(new String[Ids.size()]);
        mApi.MyEquipmentOpt(GlobalParams.sToken, arr, 3)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        RxToast.success("操作成功");
                    }
                });
    }

    /**
     * 全选和反选
     */
    private void selectAllMain() {
        if (mAdapter == null) return;
        if (!isSelectAll) {
            for (int i = 0, j = mAdapter.getMyLiveList().size(); i < j; i++) {
                mAdapter.getMyLiveList().get(i).setSelect(true);
            }
            index = mAdapter.getMyLiveList().size();
            btnUpdate.setEnabled(true);
            selectAll.setText("取消全选");

            Drawable drawableLeft = getResources().getDrawable(
                    R.drawable.un_all_select);

            selectAll.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);


            isSelectAll = true;
        } else {
            for (int i = 0, j = mAdapter.getMyLiveList().size(); i < j; i++) {
                mAdapter.getMyLiveList().get(i).setSelect(false);
            }
            index = 0;
            btnUpdate.setEnabled(false);
            selectAll.setText("全选");

            Drawable drawableLeft = getResources().getDrawable(
                    R.drawable.all_select);

            selectAll.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);

            isSelectAll = false;
        }
        mAdapter.notifyDataSetChanged();
        setBtnBackground(index);
        tvSelectNum.setText(String.valueOf(index));
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
