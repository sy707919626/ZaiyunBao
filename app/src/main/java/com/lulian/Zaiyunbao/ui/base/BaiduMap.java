package com.lulian.Zaiyunbao.ui.base;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.lulian.Zaiyunbao.common.GlobalParams;

/**
 * Created by Administrator on 2019/1/23.
 */

public class BaiduMap {
    public LocationClient mLocationClient;
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
            GlobalParams.setDistrict(bdLocation.getDistrict());   //获取区县
            String street = bdLocation.getStreet();    //获取街道信息

            double latitude = bdLocation.getLatitude();    //获取纬度信息
            double longitude = bdLocation.getLongitude();    //获取经度信息
            float radius = bdLocation.getRadius();    //获取定位精度，默认值为0.0f
            String coorType = bdLocation.getCoorType();//获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            int errorCode = bdLocation.getLocType();//获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明


            //计算距离
            DistanceUtil.getDistance(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()),
                    new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()));
        }

    };

    public BaiduMap(Context context) {
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
        LocationClientOption locOption = new LocationClientOption();

        //可选，设置定位模式，默认高精度
        locOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locOption.setCoorType("bd09ll");

        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        locOption.setScanSpan(0);

        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        locOption.setLocationNotify(true);

        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位
        locOption.setWifiCacheTimeOut(5 * 60 * 1000);
        return locOption;
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
