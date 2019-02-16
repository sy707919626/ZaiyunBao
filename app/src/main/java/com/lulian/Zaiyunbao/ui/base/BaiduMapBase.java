package com.lulian.Zaiyunbao.ui.base;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lulian.Zaiyunbao.common.GlobalParams;

/**
 * Created by Administrator on 2019/1/23.
 */

public class BaiduMapBase {
    public static LocationClient mLocationClient;
    private Context mContext;
    /**
     * 接收定位信息的监听器
     */
    private BDAbstractLocationListener mLocationListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            String addr = bdLocation.getAddrStr();    //获取详细地址信息
            String country = bdLocation.getCountry();    //获取国家
            String province = bdLocation.getProvince();    //获取省份

            String city = bdLocation.getCity();    //获取城市
            String district = bdLocation.getDistrict();
            GlobalParams.setDistrict(bdLocation.getDistrict());   //获取区县

            String street = bdLocation.getStreet();    //获取街道信息

            double latitude = bdLocation.getLatitude();    //获取纬度信息
            GlobalParams.setLatitude(bdLocation.getLatitude());   //获取纬度信息

            double longitude = bdLocation.getLongitude();    //获取经度信息
            GlobalParams.setLongitude(bdLocation.getLongitude());   //获取经度信息

            float radius = bdLocation.getRadius();    //获取定位精度，默认值为0.0f
            String coorType = bdLocation.getCoorType();//获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            int errorCode = bdLocation.getLocType();//获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

        }

    };

    public BaiduMapBase(Context context) {
        this.mContext = context;
        initLocationApi();
    }

    private void initLocationApi() {
        mLocationClient = new LocationClient(mContext);
        mLocationClient.setLocOption(buildLocationOption());
        mLocationClient.registerLocationListener(mLocationListener);

    }

    /**
     * 创建百度定位配置对象
     *
     * @return
     */
    private LocationClientOption buildLocationOption() {
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        return option;
    }

    /**
     * 开始上传定位
     */
    public void startLocate() {
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    /**
     * 停止上传定位
     */
    public void stopLocate() {
        mLocationClient.stop();
    }

}
