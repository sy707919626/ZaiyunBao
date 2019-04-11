package com.lulian.Zaiyunbao.ui.activity.service;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.utils.DistanceUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.RetireServiceSiteBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/2/12.
 */

public class RetireServiceMapActivity extends BaseActivity {

    @BindView(R.id.image_back_map_bar)
    ImageView imageBackMapBar;
    @BindView(R.id.map_service_address)
    TextView mapServiceAddress;
    @BindView(R.id.map_detail_content)
    TextView mapDetailContent;
    @BindView(R.id.map_service_ditu)
    TextView mapServiceDitu;
    @BindView(R.id.comm_map_service_layout)
    RelativeLayout commMapServiceLayout;
    @BindView(R.id.retire_bar_search)
    TextView retireBarSearch;
    @BindView(R.id.retire_bar_empty)
    ImageView retireBarEmpty;
    @BindView(R.id.map_view)
    MapView mapView;


    private BaiduMap mBaiduMap;
    private RetireServiceSiteBean retireServiceSiteBean;
    private ArrayList<RetireServiceSiteBean.RowsBean> mRetireServiceSiteList = new ArrayList<>();
    @Override
    protected int setLayoutId() {
        return R.layout.activity_retire_service_map;
    }

    @Override
    protected void init() {
        ImmersionBar.with(this)
                .titleBar(R.id.comm_map_service_layout)
                .titleBarMarginTop(R.id.comm_map_service_layout)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        mapServiceAddress.setText(GlobalParams.district);
        mapDetailContent.setText("服务站点");
        mapServiceDitu.setVisibility(View.GONE);
        retireServiceSiteBean = (RetireServiceSiteBean) getIntent().getSerializableExtra("RetireServiceSiteListBean");
        mRetireServiceSiteList.addAll(retireServiceSiteBean.getRows());

        initView();

    }

    private void initView() {
        //获取地图控件引用
        mBaiduMap = mapView.getMap();
        //显示卫星图层
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);

        for (int i = 0; i< mRetireServiceSiteList.size(); i++){
            setMarker(mRetireServiceSiteList.get(i).getYPoint(),
                    mRetireServiceSiteList.get(i).getXPoint(),
                    mRetireServiceSiteList.get(i).getName(),
                    mRetireServiceSiteList.get(i).getArea(),
                    mRetireServiceSiteList.get(i).getTouch(),
                    mRetireServiceSiteList.get(i).getManager(),
                    mRetireServiceSiteList.get(i).getId());
        }

        setMarker(GlobalParams.latitude, GlobalParams.longitude, "当前位置","","","", "");

        setUserMapCenter();
    }



    /**
     * 设置中心点
     */
    private void setUserMapCenter() {
        LatLng cenpt = new LatLng(GlobalParams.latitude, GlobalParams.longitude);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                String name = String.valueOf(marker.getExtraInfo().get("deviceSN"));
                String address = String.valueOf(marker.getExtraInfo().get("address"));
                String phone = String.valueOf(marker.getExtraInfo().get("phone"));
                String userName = String.valueOf(marker.getExtraInfo().get("userName"));
                double juli = marker.getExtraInfo().getDouble("juli");
                boolean ismine = marker.getExtraInfo().getBoolean("isMine");
                String Id = String.valueOf(marker.getExtraInfo().get("userName"));
                if (!ismine) {
                    MapDialog(name, address, userName, phone, juli, Id);
                }
                return false;
            }
        });
    }

    public void MapDialog(final String siteName, final String address, final String userName, final String phone, final double juli, final String id) {

        final AlertDialog builder = new AlertDialog.Builder(mContext)
                .create();
        builder.show();

        Window dialogWindow = builder.getWindow();
        if (dialogWindow == null) return;
        dialogWindow.setContentView(R.layout.pop_map);//设置弹出框加载的布局

        TextView mapSite = builder.findViewById(R.id.tv_map_site);
        TextView mapAddress = builder.findViewById(R.id.tv_map_address);
        TextView maplxr = builder.findViewById(R.id.tv_map_lxr);
        TextView mapJuli = builder.findViewById(R.id.tv_map_juli);
        Button cancle = builder.findViewById(R.id.btn_cancle);
        Button sure = builder.findViewById(R.id.btn_sure);

        mapSite.setText(siteName);
        mapAddress.setText("地址："+ address);
        maplxr.setText("联系人："+ userName + "   联系电话："+phone);
        mapJuli.setText("距离我："+ String.format("%.2f", juli/1000) + "  KM");

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        final WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.5
        dialogWindow.setAttributes(p);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RetireCreateActivity.class);
                intent.putExtra("StoreId",id); //仓库ID
                intent.putExtra("Name", siteName);//仓库名字
                intent.putExtra("Area", address); //仓库地址

                for (int i = 0 ; i < retireServiceSiteBean.getRows().size(); i++){
                    if (retireServiceSiteBean.getRows().get(i).getId().equals(id)){
                        intent.putExtra("BelongMember", retireServiceSiteBean.getRows().get(i).getBelongMember());
                    }
                }

                mContext.startActivity(intent);
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RetireServiceLeaseActivity.class);
                intent.putExtra("StoreId",id); //仓库ID
                intent.putExtra("Name", siteName);//仓库名字
                mContext.startActivity(intent);
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface p1) {
                builder.dismiss();
            }
        });

    }

    /**
     * 添加marker
     */
    private void setMarker(double latitude, double longitude, String name, String address, String phone, String userName, String id) {
        Marker marker = null;
        //定义Maker坐标点
        LatLng point = new LatLng(latitude, longitude);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.map_bs);

        BitmapDescriptor bitmapMin = BitmapDescriptorFactory
                .fromResource(R.drawable.map_mine);

        double distance = DistanceUtil.getDistance( new LatLng(GlobalParams.latitude, GlobalParams.longitude), point);
        Bundle bundle = new Bundle();

        if (GlobalParams.latitude != latitude && GlobalParams.longitude != longitude){ //站点位置
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap)
                    .title(name);

            OverlayOptions textOption = new TextOptions()
                    .fontSize(36)
                    .fontColor(0xFFFF0000)
                    .text(name)
                    .position(point);

            //在地图上添加该文字对象并显示
            mBaiduMap.addOverlay(textOption);

            //在地图上添加Marker，并显示
            marker = (Marker) (mBaiduMap.addOverlay(option));
            // 设置额外的信息
            bundle.putBoolean("isMine", false);

        } else { //我的位置

            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmapMin)
                    .title(name);

            //在地图上添加Marker，并显示
            marker = (Marker) (mBaiduMap.addOverlay(option));

            bundle.putBoolean("isMine", true);

        }

        bundle.putString("deviceSN", name);
        bundle.putString("address", address);
        bundle.putString("phone", phone);
        bundle.putString("userName", userName);
        bundle.putDouble("juli", distance);
        marker.setExtraInfo(bundle);
    }

    @OnClick({R.id.map_service_address, R.id.map_service_ditu, R.id.retire_bar_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.map_service_address:
                //选择定位
                changeAddress(mapServiceAddress.getText().toString().trim());
                break;

            case R.id.map_service_ditu:
                //地图
                RxToast.warning("站点");
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
                mapServiceAddress.setText(data.getStringExtra("addressSelect"));
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时必须调用mMapView. onResume ()
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时必须调用mMapView. onPause ()
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时必须调用mMapView.onDestroy()
//        mapView.onDestroy();
    }
}
