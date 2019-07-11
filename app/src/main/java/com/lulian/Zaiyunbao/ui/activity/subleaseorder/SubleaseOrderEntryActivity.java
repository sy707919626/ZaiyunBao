package com.lulian.Zaiyunbao.ui.activity.subleaseorder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.ECodeBean;
import com.lulian.Zaiyunbao.Bean.SaleEntity;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxBus;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.EventMsg;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.ui.activity.PermissionsActivity;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Administrator on 2018/11/15.
 */

public class SubleaseOrderEntryActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.sublease_entry_RFID)
    RelativeLayout subleaseEntryRFID;
    @BindView(R.id.sublease_entry_Scan)
    RelativeLayout subleaseEntryScan;
    @BindView(R.id.sublease_entry_sum)
    ClearEditText subleaseEntrySum;
    @BindView(R.id.ecode_add_btn)
    ImageView ecodeAddBtn;
    @BindView(R.id.sublease_entry_count)
    TextView subleaseEntryCount;
    @BindView(R.id.sublease_entry_recycler)
    RecyclerView subleaseEntryRecycler;
    @BindView(R.id.sublease_entry_btn)
    Button subleaseEntryBtn;
    @BindView(R.id.sublease_entry_zulin_mode_text)
    TextView mSubleaseEntryZulinModeText;
    @BindView(R.id.sublease_entry_zulin_mode)
    TextView mSubleaseEntryZulinMode;
    @BindView(R.id.dialog_bg)
    ImageView dialogBg;

    private String EquipmentName;
    private String EquipmentNorm;
    private String OrderId;
    private int Count;
    private int RFIDsum = 0;
    private int TextSum = 0;

    private SubleaseEntryOrderAdapter mAdapter;
    private ArrayList<String> orderList = new ArrayList<>();
    private List<ECodeBean> mEcodeListBean = new ArrayList<>();

    private String AdapterPage;//区分来源
    private String EquipmentId; //设备ID
    private int FormType;
    private Handler mHandler;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_sublease_entry_order;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void init() {
        ImmersionBar.with(this)
                .titleBar(R.id.detail_bar_title)
                .titleBarMarginTop(R.id.detail_bar_title)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        orderList.clear();
        textDetailContent.setText("录入设备");
        textDetailRight.setText("导出发货单");

        dialogBg.setImageAlpha(0);
        dialogBg.setVisibility(View.GONE);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    dialogBg.setVisibility(View.GONE);
                }
            }
        };


        EquipmentName = getIntent().getStringExtra("EquipmentName");
        EquipmentNorm = getIntent().getStringExtra("EquipmentNorm");
        OrderId = getIntent().getStringExtra("OrderId");
        Count = getIntent().getIntExtra("Count", 0); //可租数量
        EquipmentId = getIntent().getStringExtra("EquipmentId");

        FormType = getIntent().getIntExtra("FormType", 0);
        AdapterPage = getIntent().getStringExtra("AdapterPage");


        mSubleaseEntryZulinMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBlur(dialogBg, mHandler);
                String[] list = {"全部","分时租赁","分次租赁"};
                BaseDialog(SubleaseOrderEntryActivity.this, dialogBg, list,
                        mSubleaseEntryZulinMode.getText().toString(), "租赁方式", mHandler, new OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                mSubleaseEntryZulinMode.setText(data.get(position).getTitle());
                            }
                        });
            }
        });
        mSubleaseEntryZulinMode.setText("全部");

        subleaseEntryRecycler.setItemAnimator(new DefaultItemAnimator());
        subleaseEntryRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SubleaseEntryOrderAdapter(this, orderList, EquipmentName, EquipmentNorm);
        subleaseEntryRecycler.setAdapter(mAdapter);



        ecodeAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zulinModel = mSubleaseEntryZulinMode.getText().toString().trim();
                if (TextUtils.isEmpty(subleaseEntrySum.getText().toString().trim())) {
                    RxToast.warning("请输入设备数量");

                } else if (Integer.valueOf(subleaseEntrySum.getText().toString().trim()) + orderList.size() > Count) {
                    RxToast.warning("录入设备总数不能大于可转租数量");

                } else if (TextUtils.isEmpty(mSubleaseEntryZulinMode.getText().toString().trim())){
                    RxToast.warning("请选择租赁方式");

                } else{

                    if (zulinModel.equals("全部")){
                        getEquimentCode(Count, 0);

                    } else if (zulinModel.equals("分时租赁")){
                        getEquimentCode(Count, 1);

                    } else if (zulinModel.equals("分次租赁")){
                        getEquimentCode(Count, 2);
                    }

                }
            }
        });

        mSubleaseEntryZulinMode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                orderList.clear();
                mAdapter.notifyDataSetChanged();
            }
        });

        subleaseEntrySum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                orderList.clear();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.sublease_entry_RFID, R.id.sublease_entry_Scan, R.id.sublease_entry_btn, R.id.text_detail_right, R.id.ecode_add_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sublease_entry_RFID:
                if (orderList.size() > Count) {
                    RxToast.warning("录入设备数量不能大于可转租数量");
                } else {

                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mAdapter = new SubleaseEntryOrderAdapter(this, orderList, EquipmentName, EquipmentNorm);
                        subleaseEntryRecycler.setAdapter(mAdapter);
                    }
                    subleaseEntryCount.setText(orderList.size() + "");
                }
                break;

            case R.id.sublease_entry_Scan:
                if (orderList.size() > Count) {
                    RxToast.warning("设备录入数量不能大于可转租数量");
                } else {
                    scanCode();
                    subleaseEntryCount.setText(orderList.size() + "");
                }
                break;

            case R.id.sublease_entry_btn:
                if (orderList.size() <= 0) {
                    RxToast.warning("请导入转租设备");
                }
                 if (orderList.size() < Count) {
                    RxToast.warning("当前录入设备数量不足，无法进行发货");
                } else {
                    String[] strArrayTrue = orderList.toArray(new String[orderList.size()]);
                    mApi.SendGood_Rent(GlobalParams.sToken, OrderId, strArrayTrue, Count)
                            .compose(RxHttpResponseCompat.<String>compatResult())
                            .subscribe(new ErrorHandlerSubscriber<String>() {
                                @Override
                                public void onNext(String s) {
                                    Constants.setIsAutoRefresh(true);
                                    RxToast.success("发货成功");
                                    finish();
                                }
                            });
                }
                break;

            case R.id.text_detail_right:
                Intent intents = new Intent(this, SubleaseDeliveryActivity.class);
                startActivity(intents);
                break;
            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) { //二维码扫描
            Toast.makeText(this, "没有权限无法扫描呦", Toast.LENGTH_LONG).show();
        } else if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Toast.makeText(this, "扫描结果为：" + content, Toast.LENGTH_SHORT).show();
                //跳转页面
                Intent intent = new Intent(this, SubleaseOrderAddEntryActivity.class);
                intent.putExtra("scanOrder", content);
                intent.putExtra("scanCount", Count - Integer.valueOf(subleaseEntryCount.getText().toString().trim()));
                intent.putExtra("EquipmentName", EquipmentName);
                intent.putExtra("EquipmentNorm", EquipmentNorm);
                startActivity(intent);
            }
        }

    }

    private void getEquimentCode(int Sum, final int RentWay) {
        mApi.GetECodeForSend(GlobalParams.sToken, EquipmentId, GlobalParams.sUserId, Sum, RentWay)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .compose(this.<String>bindUntilEvent(ActivityEvent.STOP))
                .compose(this.<String>bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        mEcodeListBean = JSONObject.parseArray(s, ECodeBean.class);
//                        if (mEcodeListBean.size() <= 0) {
//                            RxToast.warning("获取无码设备ECode失败！找不到适用的设备！");
//                            ecodeAddBtn.setEnabled(false);
//                            subleaseEntrySum.setEnabled(false);
//                        } else {
//                            ecodeAddBtn.setEnabled(true);
//                            subleaseEntrySum.setEnabled(true);
//                        }
                        if (mEcodeListBean.size() > 0) {
                            for (int i = 0; i < Integer.valueOf(subleaseEntrySum.getText().toString().trim()); i++) {
                                orderList.add(mEcodeListBean.get(i).getECode());
                            }

                            if (mAdapter != null) {
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter = new SubleaseEntryOrderAdapter(SubleaseOrderEntryActivity.this, orderList, EquipmentName, EquipmentNorm);
                                subleaseEntryRecycler.setAdapter(mAdapter);
                            }

                            subleaseEntryCount.setText(orderList.size() + "");
                        } else {
                            if (RentWay == 1){
                                RxToast.warning("获取分时租赁的无码设备ECode失败！");
                            } else if (RentWay == 2){
                                RxToast.warning("获取分次租赁的无码设备ECode失败！");
                            } else if (RentWay == 0){
                                RxToast.warning("获取无码设备ECode失败！找不到适用的设备！");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
//                        ecodeAddBtn.setEnabled(false);
//                        subleaseEntrySum.setEnabled(false);
                        if (RentWay == 1){
                            RxToast.warning("获取分时租赁的无码设备ECode失败！");
                        } else if (RentWay == 2){
                            RxToast.warning("获取分次租赁的无码设备ECode失败！");
                        } else if (RentWay == 0){
                            RxToast.warning("获取无码设备ECode失败！找不到适用的设备！");
                        }

//                        super.onError(t);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RxBus.getInstance().toObservable().map(new Function<Object, EventMsg>() {
            @Override
            public EventMsg apply(Object o) throws Exception {
                return (EventMsg) o;
            }
        }).subscribe(new Consumer<EventMsg>() {
            @Override
            public void accept(EventMsg eventMsg) throws Exception {
                if (eventMsg != null) {
                    orderList.addAll(eventMsg.getMsg());

                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mAdapter = new SubleaseEntryOrderAdapter(SubleaseOrderEntryActivity.this, orderList, EquipmentName, EquipmentNorm);
                        subleaseEntryRecycler.setAdapter(mAdapter);
                    }

                    subleaseEntryCount.setText(orderList.size() + "");
                }
            }
        });
    }
}
