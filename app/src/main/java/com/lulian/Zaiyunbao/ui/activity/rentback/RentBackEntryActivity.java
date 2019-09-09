package com.lulian.Zaiyunbao.ui.activity.rentback;

import android.content.Intent;
import android.graphics.Color;
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
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxBus;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.EventMsg;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.activity.PermissionsActivity;
import com.lulian.Zaiyunbao.ui.activity.subleaseorder.SubleaseDeliveryActivity;
import com.lulian.Zaiyunbao.ui.activity.subleaseorder.SubleaseEntryOrderAdapter;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/11/15.
 */

public class RentBackEntryActivity extends BaseActivity {
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
    private String EquipmentName;
    private String EquipmentNorm;
    private String OrderId;
    private String RentOrderID;
    private int Count;
    private int TextSum = 0;

    private SubleaseEntryOrderAdapter mAdapter;
    private ArrayList<String> orderList = new ArrayList<>();

    private List<ECodeBean> mEcodeListBean = new ArrayList<>();
    private String EquipmentId; //设备ID
    private int RentWay;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_rentback_entry_order;
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

        textDetailContent.setText("录入设备");
        textDetailRight.setText("导出发货单");
        ecodeAddBtn.setEnabled(false);

        EquipmentName = getIntent().getStringExtra("EquipmentName");
        EquipmentNorm = getIntent().getStringExtra("EquipmentNorm");
        OrderId = getIntent().getStringExtra("OrderId");
        RentOrderID = getIntent().getStringExtra("RentOrderID");
        Count = getIntent().getIntExtra("Count", 0); //可租数量
        EquipmentId = getIntent().getStringExtra("Id");
        RentWay = getIntent().getIntExtra("RentWay", 0); //租赁模式 1分时， 2分次

        subleaseEntryRecycler.setItemAnimator(new DefaultItemAnimator());
        subleaseEntryRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SubleaseEntryOrderAdapter(this, orderList, EquipmentName, EquipmentNorm);
        subleaseEntryRecycler.setAdapter(mAdapter);

        getEquimentCode(Count);


        subleaseEntrySum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Count <= mEcodeListBean.size()) {
                    if (!TextUtils.isEmpty(subleaseEntrySum.getText().toString().trim())) {
                        ecodeAddBtn.setEnabled(true);
                    } else {
                        subleaseEntryCount.setText("0");
                        orderList.clear();
                        mAdapter.notifyDataSetChanged();
                        ecodeAddBtn.setEnabled(false);

                    }
                } else {
                    getEquimentCode(Integer.valueOf(subleaseEntrySum.getText().toString().trim()));
                }
            }
        });

        ecodeAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(subleaseEntrySum.getText().toString().trim())) {
                    RxToast.warning("请输入设备数量");

                } else if (Integer.valueOf(subleaseEntrySum.getText().toString().trim()) <= 0) {
                    RxToast.warning("录入设备总数不能小于或等于0");

                } else if (Integer.valueOf(subleaseEntrySum.getText().toString().trim()) + orderList.size() > Count) {
                    RxToast.warning("录入设备总数不能大于可退租数量");

                } else if (mEcodeListBean.size() > 0){
                    for (int i = 0; i < Integer.valueOf(subleaseEntrySum.getText().toString().trim()); i++) {
                        orderList.add(mEcodeListBean.get(i).getECode());
                    }

                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mAdapter = new SubleaseEntryOrderAdapter(RentBackEntryActivity.this, orderList, EquipmentName, EquipmentNorm);
                        subleaseEntryRecycler.setAdapter(mAdapter);
                    }

                    subleaseEntryCount.setText(orderList.size() + "");

                } else {
                    RxToast.warning("获取无码设备ECode失败!");
                }
            }
        });
    }

    private void getEquimentCode(int Sum) {
        Observable<String> observable;

        if (TextUtils.isEmpty(RentOrderID)){
            observable =  mApi.GetECodeForSend(GlobalParams.sToken, EquipmentId, GlobalParams.sUserId, Sum, RentWay);
        } else {
            observable =  mApi.GetECodeForSend(GlobalParams.sToken, EquipmentId, GlobalParams.sUserId, Sum, RentOrderID, RentWay);
        }

        observable.compose(RxHttpResponseCompat.<String>compatResult())
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .compose(this.<String>bindUntilEvent(ActivityEvent.STOP))
                .compose(this.<String>bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        mEcodeListBean = JSONObject.parseArray(s, ECodeBean.class);

                        if (mEcodeListBean.size() > 0 ) {
                            if (Count > mEcodeListBean.size()) {
                                RxToast.warning("源订单设备不足，无法按订单退租，请于我的-服务 进行退租操作");
                                subleaseEntryBtn.setEnabled(false);
                                subleaseEntryRFID.setEnabled(false);
                                subleaseEntryScan.setEnabled(false);
                                subleaseEntrySum.setEnabled(false);
                            } else {
                                subleaseEntryBtn.setEnabled(true);
                                subleaseEntryRFID.setEnabled(true);
                                subleaseEntryScan.setEnabled(true);
                                subleaseEntrySum.setEnabled(true);
                            }

                            ecodeAddBtn.setEnabled(true);
                            subleaseEntrySum.setEnabled(true);
                        } else {
                            RxToast.warning("获取无码设备ECode失败!");
                            ecodeAddBtn.setEnabled(false);
                            subleaseEntrySum.setEnabled(false);
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        RxToast.warning("获取无码设备ECode失败!");
                        ecodeAddBtn.setEnabled(false);
                        subleaseEntrySum.setEnabled(false);
//                        super.onError(t);
                    }
                });

    }

    @OnClick({R.id.sublease_entry_RFID, R.id.sublease_entry_Scan, R.id.sublease_entry_btn, R.id.text_detail_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sublease_entry_RFID:
                if (orderList.size() > Count) {
                    RxToast.warning("录入设备数量不能大于可退租数量");
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
                    RxToast.warning("设备录入数量不能大于可退租数量");
                } else {
                    scanCode();
                    subleaseEntryCount.setText(orderList.size() + "");
                }

                break;

            case R.id.sublease_entry_btn:
                if (orderList.size() <= 0) {
                    RxToast.warning("请导入退租设备");
                } else if (orderList.size() < Count) {
                    RxToast.warning("当前录入设备数量不足，无法进行发货");
                } else {
                    String[] strArrayTrue = orderList.toArray(new String[orderList.size()]);
                    JSONObject obj = new JSONObject();
                    obj.put("OrderId", OrderId);
                    obj.put("EquipmentBaseNo", strArrayTrue);
                    obj.put("Count", Count);
                    String user = obj.toString();
                    RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                            user);

                    mApi.SendGood_BackRent(GlobalParams.sToken, body)
                            .compose(RxHttpResponseCompat.<String>compatResult())
                            .subscribe(new ErrorHandlerSubscriber<String>() {
                                @Override
                                public void onNext(String s) {
                                    RxToast.success("退租发货成功");
                                    subleaseEntryBtn.setVisibility(View.GONE);
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
                Intent intent = new Intent(this, RentBackAddEntryActivity.class);
                intent.putExtra("scanOrder", content);
                intent.putExtra("scanCount", Count - Integer.valueOf(subleaseEntryCount.getText().toString().trim()));
                intent.putExtra("EquipmentName", EquipmentName);
                intent.putExtra("EquipmentNorm", EquipmentNorm);
                startActivity(intent);
            }
        }
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
                        mAdapter = new SubleaseEntryOrderAdapter(RentBackEntryActivity.this, orderList, EquipmentName, EquipmentNorm);
                        subleaseEntryRecycler.setAdapter(mAdapter);
                    }

                    subleaseEntryCount.setText(orderList.size() + "");
                }
            }
        });
    }

}
