package com.lulian.Zaiyunbao.ui.activity.repair;

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
import com.lulian.Zaiyunbao.Bean.RepairItem;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/24.
 */

public class RepairOrderDetailActivity extends BaseActivity {
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.repair_detail_recyclerview)
    RecyclerView repairDetailRecyclerview;

    private String Id;
    private int Status;

    private ArrayList<RepairItem> mRepairItem = new ArrayList<>();
    private RepairOrderDetailAdapter mAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_repair_order_detail;
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

        textDetailContent.setText("维修单明细");
        textDetailRight.setVisibility(View.GONE);

        Id = getIntent().getStringExtra("RepairId");
        Status = getIntent().getIntExtra("Status", 0);

        repairDetailRecyclerview.setItemAnimator(new DefaultItemAnimator());
        repairDetailRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        getData();
    }

    public void getData() {
        mApi.RepairItem(GlobalParams.sToken, Id)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        mRepairItem.clear();
                        mRepairItem.addAll(JSONObject.parseArray(s, RepairItem.class));

                        mAdapter = new RepairOrderDetailAdapter(RepairOrderDetailActivity.this, mRepairItem);
                        repairDetailRecyclerview.setAdapter(mAdapter);

                        mAdapter.setOnItemClickListener(new RepairOrderDetailAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, ArrayList<RepairItem> repairItem) {

                                Intent intent = new Intent(RepairOrderDetailActivity.this, RepairItemDetailActivity.class);
                                intent.putExtra("DetailId", repairItem.get(position).getDetailId());
                                intent.putExtra("Status", Status);
                                startActivity(intent);
                            }
                        });
                    }
                });
    }
}
