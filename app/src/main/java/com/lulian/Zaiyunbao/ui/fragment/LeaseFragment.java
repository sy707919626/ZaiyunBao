package com.lulian.Zaiyunbao.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.EquipmentListBean;
import com.lulian.Zaiyunbao.Bean.EquipmentTypeBean;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.event.LeaseEvent;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.SelectPopupWindow;
import com.lulian.Zaiyunbao.ui.activity.LeaseDetailActivity;
import com.lulian.Zaiyunbao.ui.activity.PermissionsActivity;
import com.lulian.Zaiyunbao.ui.activity.SearchActivity;
import com.lulian.Zaiyunbao.ui.adapter.EquipmentListAdapter;
import com.lulian.Zaiyunbao.ui.base.BaseLazyFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.yzq.zxinglibrary.common.Constant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

/**
 * 租赁
 */
public class LeaseFragment extends BaseLazyFragment {

    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.bar_search)
    TextView barSearch;
    @BindView(R.id.bar_empty)
    ImageView barEmpty;
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.title_search_toolbar)
    Toolbar titleSearchToolbar;
    @BindView(R.id.zulin_All)
    TextView zulinAll;
    @BindView(R.id.zulin_user_type)
    TextView zulinUserType;
    @BindView(R.id.zulin_user_imageView)
    ImageView zulinUserImageView;
    @BindView(R.id.zulin_user_layout)
    RelativeLayout zulinUserLayout;
    @BindView(R.id.zulin_shebei_type)
    TextView zulinShebeiType;
    @BindView(R.id.zulin_shebei_imageView)
    ImageView zulinShebeiImageView;
    @BindView(R.id.factor_ll_endspinner)
    RelativeLayout factorLlEndspinner;
    @BindView(R.id.title_bar)
    LinearLayout titleBar;
    @BindView(R.id.fragment_recyclerview)
    RecyclerView fragmentRecyclerview;
    @BindView(R.id.smartRefresh_Layout)
    SmartRefreshLayout smartRefreshLayout;

    private EquipmentListAdapter mAdapter;
    private ArrayList<EquipmentListBean.RowsBean> mEquipmentList = new ArrayList<>();
    private boolean isRefresh; //刷新

    private int pageSize = 10;
    private int page = 1;

    private boolean isshowAll = false;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_lease;
    }

    @Override
    protected void init() {
        ImmersionBar.with(getActivity())
                .titleBar(R.id.title_search_toolbar)
                .navigationBarColor(R.color.toolbarBG)
                .init();

        EventBus.getDefault().register(this);
        initView();

        tvAddress.setText(GlobalParams.district);

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

    private void initView() {
        zulinUserType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> userTypeList = new ArrayList<>();
                userTypeList.add("信息类型");
                userTypeList.add("转租信息");
                userTypeList.add("出租信息");
                userTypeList.add("我的发布");

                SelectPopupWindow popup = new SelectPopupWindow(getContext(), userTypeList, zulinUserType.getText().toString());
                popup.showAsDropDown(titleBar);
                zulinUserType.setTextColor(getResources().getColor(R.color.text_hint_bule));
                zulinUserImageView.setImageDrawable(getResources().getDrawable(R.drawable.unxiala));

                //起始地选择监听
                popup.setCallback(new SelectPopupWindow.Callback() {
                    @Override
                    public void onCallback(String title) {
                        zulinUserType.setText(title);
                        getData();
                    }
                });

                popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        zulinUserType.setTextColor(getResources().getColor(R.color.text_black));
                        zulinUserImageView.setImageDrawable(getResources().getDrawable(R.drawable.xiala));
                    }
                });
            }
        });

        zulinShebeiType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> shebeiTypeList = new ArrayList<>();
                shebeiTypeList.add("设备类型");
                shebeiTypeList.addAll(GlobalParams.SBTypeList);

                SelectPopupWindow popup2 = new SelectPopupWindow(getContext(), shebeiTypeList, zulinShebeiType.getText().toString());
                popup2.showAsDropDown(titleBar);
                zulinShebeiType.setTextColor(getResources().getColor(R.color.text_hint_bule));
                zulinShebeiImageView.setImageDrawable(getResources().getDrawable(R.drawable.unxiala));

                //选择监听
                popup2.setCallback(new SelectPopupWindow.Callback() {
                    @Override
                    public void onCallback(String title) {
                        zulinShebeiType.setText(title);
                        getData();
                    }
                });

                popup2.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        zulinShebeiType.setTextColor(getResources().getColor(R.color.text_black));
                        zulinShebeiImageView.setImageDrawable(getResources().getDrawable(R.drawable.xiala));
                    }
                });
            }
        });

        zulinAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isshowAll = true;
                getData();
            }
        });

        fragmentRecyclerview.setItemAnimator(new DefaultItemAnimator());
        fragmentRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new EquipmentListAdapter(getContext(), mEquipmentList, mApi);
        fragmentRecyclerview.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new EquipmentListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, EquipmentListBean.RowsBean equipmentList) {
                Intent intent = new Intent(getContext(), LeaseDetailActivity.class);
                intent.putExtra("ShebeiId", equipmentList.getId());
                intent.putExtra("OperatorId", equipmentList.getOperator()); //供应商ID
                intent.putExtra("StorehouseId", equipmentList.getStorehouseId());

                if (TextUtils.isEmpty(equipmentList.getUID())){
                    intent.putExtra("UID",""); //使用者ID
                }else {
                    intent.putExtra("UID", equipmentList.getUID()); //使用者ID
                }
                getContext().startActivity(intent);
            }
        });
    }

    private void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = alpha;
        getActivity().getWindow().setAttributes(lp);

    }

    @Override
    public void getData() {
        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }

        JSONObject TypeId = new JSONObject();//设备类型Id
        JSONObject useTypeId = new JSONObject(); //用户类型
        JSONObject obj = new JSONObject();
        obj.put("Page", page);
        obj.put("Rows", 10);

        List<JSONObject> list = new ArrayList<JSONObject>();

        if (zulinUserType.getText().toString().trim().equals("转租信息")) {
            useTypeId.put("name", "len(UID)");
            useTypeId.put("type", ">");
            useTypeId.put("value", 0);
            list.add(useTypeId);

        } else if (zulinUserType.getText().toString().trim().equals("出租信息")) {
            useTypeId.put("name", "len(UID)");
            useTypeId.put("type", "!>");
            useTypeId.put("value", 0);
            list.add(useTypeId);
        } else if (zulinUserType.getText().toString().trim().equals("我的发布")) {
            useTypeId.put("name", "UID");
            useTypeId.put("type", "=");
            useTypeId.put("value", GlobalParams.sUserId);
            list.add(useTypeId);
        }

        if (!zulinShebeiType.getText().toString().trim().equals("设备类型")) {
            TypeId.put("name", "equipment.TypeId");
            TypeId.put("type", "=");

            for (EquipmentTypeBean equipmentTypeBean : GlobalParams.sEquipmentTypeBean) {
                if (equipmentTypeBean.getTypeName().equals(zulinShebeiType.getText().toString().trim())) {
                    TypeId.put("value", equipmentTypeBean.getId());
                }
            }

            list.add(TypeId);
        }

        if (!isshowAll) {
            JSONObject[] filters = list.toArray(new JSONObject[list.size()]);
            obj.put("Filters", filters);
        }

        String messages = obj.toString();
        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                messages);

        mApi.equipmentList(GlobalParams.sToken, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .compose(this.<String>bindUntilEvent(FragmentEvent.DESTROY))
                .compose(this.<String>bindUntilEvent(FragmentEvent.STOP))
                .compose(this.<String>bindUntilEvent(FragmentEvent.PAUSE))
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        EquipmentListBean equipmentListBean = MyApplication.get().getAppComponent().getGson().fromJson(s, EquipmentListBean.class);

                        if (isRefresh) {
                            mEquipmentList.clear();
                        }

                        mEquipmentList.addAll(equipmentListBean.getRows());
//                        sort();
                        if (mAdapter != null) {
                            mAdapter.notifyDataSetChanged();
                        }

                        if (equipmentListBean.getRows().size() < pageSize) {
                            smartRefreshLayout.finishLoadmoreWithNoMoreData(); //将不会再次触发加载更多事件
                        }

                        isshowAll = false;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) { //二维码扫描
            Toast.makeText(getActivity(), "没有权限无法扫描呦", Toast.LENGTH_LONG).show();
        } else if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Toast.makeText(getActivity(), "扫描结果为：" + content, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == ADDRESS_LOCATION && resultCode == 11) { //选择地点返回值
            if (data != null) {
                tvAddress.setText(data.getStringExtra("addressSelect"));
            }
        }
//        else if (requestCode == REQUEST_LOCATION && data != null) {
//            LocationListBean locationListBean = (LocationListBean) data.getSerializableExtra("LocationListBean");
//            tv_address.setText(locationListBean.getCity());
//            Toast.makeText(getActivity(), locationListBean.getCity(), Toast.LENGTH_LONG).show();
//        }


    }

    @OnClick({R.id.iv_qr_code, R.id.tv_address, R.id.bar_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_qr_code:
                scanCode();
                break;

            case R.id.tv_address:
                changeAddress(tvAddress.getText().toString().trim());
                break;


            case R.id.bar_search:
                getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        smartRefreshLayout.autoRefresh(); //触发自动刷新

    }
//    private void sort(){
//        //根据下单时间降序排列
//        Collections.sort(mEquipmentList, new Comparator<EquipmentListBean.RowsBean>() {
//            @Override
//            public int compare(EquipmentListBean.RowsBean lhs, EquipmentListBean.RowsBean rhs) {
//                Date date1 = stringToDate(lhs.getCreateTime());
//                Date date2 = stringToDate(rhs.getCreateTime());
//                // 对日期字段进行升序，如果欲降序可采用after方法
//                if (date1.before(date2)) {
//                    return 1;
//                }
//                return -1;
//            }
//        });
//    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LeaseEvent event) {
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
