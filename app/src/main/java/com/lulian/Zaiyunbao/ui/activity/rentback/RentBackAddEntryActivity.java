package com.lulian.Zaiyunbao.ui.activity.rentback;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.rx.RxBus;
import com.lulian.Zaiyunbao.common.widget.EventMsg;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.activity.PermissionsActivity;
import com.lulian.Zaiyunbao.ui.activity.subleaseorder.SubleaseEntryOrderAdapter;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/11/15.
 */

public class RentBackAddEntryActivity extends BaseActivity {
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.text_success)
    TextView textSuccess;
    @BindView(R.id.add_order_btn)
    Button addOrderBtn;
    @BindView(R.id.add_order_sum)
    TextView addOrderSum;
    @BindView(R.id.sublease_add_entry_recycler)
    RecyclerView subleaseAddEntryRecycler;
    @BindView(R.id.sublease_add_entry_btn)
    Button subleaseAddEntryBtn;

    private ArrayList<String> orderList = new ArrayList<String>();
    private SubleaseEntryOrderAdapter mAdapter;
    private int OrderSum = 0;
    private String ScanOrder;
    private String EquipmentName;
    private String EquipmentNorm;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_sublease_entry_add_order;
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

        ScanOrder = getIntent().getStringExtra("scanOrder");
        OrderSum = getIntent().getIntExtra("sumOrder", 0);
        EquipmentName = getIntent().getStringExtra("EquipmentName");
        EquipmentNorm = getIntent().getStringExtra("EquipmentNorm");

        orderList.add(ScanOrder);
        textDetailContent.setText("添加设备");
        textDetailRight.setVisibility(View.GONE);

        subleaseAddEntryRecycler.setItemAnimator(new DefaultItemAnimator());
        subleaseAddEntryRecycler.setLayoutManager(new LinearLayoutManager(this));
//        subleaseAddEntryRecycler.addItemDecoration(new RecyclerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new SubleaseEntryOrderAdapter(this, orderList, EquipmentName, EquipmentNorm);
        subleaseAddEntryRecycler.setAdapter(mAdapter);

        addOrderSum.setText(orderList.size() + "");
    }

    @OnClick({R.id.add_order_btn, R.id.sublease_add_entry_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_order_btn:
                //继续添加
                if (orderList.size() > OrderSum) {
                    RxToast.warning("扫描的设备数量不能超过可转租数量");
                } else {
                    scanCode();
                }
                break;

            case R.id.sublease_add_entry_btn:
                //添加完成
                EventMsg eventMsg = new EventMsg();
                eventMsg.setMsg(orderList);
                RxBus.getInstance().post(eventMsg);
                finish();
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
                //动态添加
                orderList.add(content);
                mAdapter.notifyDataSetChanged();


                addOrderSum.setText(orderList.size() + "");
            }
        }
    }


}
