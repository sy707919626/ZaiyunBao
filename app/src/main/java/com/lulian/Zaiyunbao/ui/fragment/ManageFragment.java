package com.lulian.Zaiyunbao.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.data.http.ApiService;
import com.lulian.Zaiyunbao.ui.activity.App_Statistic_Activity;
import com.lulian.Zaiyunbao.ui.activity.MyOrderActivity;
import com.lulian.Zaiyunbao.ui.activity.PermissionsActivity;
import com.lulian.Zaiyunbao.ui.activity.devicemanage.DeviceManageActivity;
import com.lulian.Zaiyunbao.ui.activity.issueSublease.IssueSubleaseListActivity;
import com.lulian.Zaiyunbao.ui.activity.repair.RepairReportActivity;
import com.lulian.Zaiyunbao.ui.activity.service.RetireServiceSiteActivity;
import com.lulian.Zaiyunbao.ui.base.BaseLazyFragment;
import com.yzq.zxinglibrary.common.Constant;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2018/7/29.
 */

public class ManageFragment extends BaseLazyFragment {
    @BindView(R.id.text_manage_content)
    TextView textManageContent;
    @BindView(R.id.iv_manage_code)
    ImageView ivManageCode;
    @BindView(R.id.title_manage_toolbar)
    Toolbar titleManageToolbar;
    @BindView(R.id.tv_shebeiManage)
    LinearLayout tvShebeiManage;
    @BindView(R.id.tv_myOrder)
    LinearLayout tvMyOrder;
    @BindView(R.id.tv_release)
    LinearLayout tvRelease;
    @BindView(R.id.tv_service)
    LinearLayout tvService;
    @BindView(R.id.tv_repair_report)
    LinearLayout tvRepairReport;
    @BindView(R.id.tv_shebeiTJ)
    LinearLayout tvShebeiTJ;
    Unbinder unbinder;
    private ApiService mApiService = MyApplication.get().getAppComponent().getApiService();

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_manage;
    }

    @Override
    protected void init() {
        ImmersionBar.with(getActivity())
                .titleBar(R.id.title_manage_toolbar)
                .navigationBarColor(R.color.toolbarBG)
                .init();

        getData();
        textManageContent.setText("管理");
    }

    @Override
    public void getData() {

    }


    @OnClick({R.id.tv_shebeiManage, R.id.tv_myOrder, R.id.tv_release,
            R.id.tv_shebeiTJ, R.id.tv_service, R.id.iv_manage_code, R.id.tv_repair_report})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shebeiManage:
                //设备管理
                Intent intent = new Intent(getContext(), DeviceManageActivity.class);
                getContext().startActivity(intent);
                break;

            case R.id.tv_myOrder:
                //我的订单
                getContext().startActivity(new Intent(getContext(), MyOrderActivity.class));
                break;

            case R.id.tv_release:
                //发布转租
                getContext().startActivity(new Intent(getContext(), IssueSubleaseListActivity.class));
                break;

            case R.id.tv_shebeiTJ:
                //统计
                getContext().startActivity(new Intent(getContext(), App_Statistic_Activity.class));
                break;

            case R.id.tv_service:
                //服务
                getContext().startActivity(new Intent(getContext(), RetireServiceSiteActivity.class));
                break;

            case R.id.tv_repair_report:
                //维修上报
                getContext().startActivity(new Intent(getContext(), RepairReportActivity.class));
                break;

            case R.id.iv_manage_code:
                scanCode();
                break;

        }
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
        }
    }

}
