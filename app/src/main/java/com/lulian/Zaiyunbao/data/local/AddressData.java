package com.lulian.Zaiyunbao.data.local;

import com.contrarywind.interfaces.IPickerViewData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.common.widget.GetJsonDataUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 地址选择器（包括省级、地级、县级）。
 * 地址数据见示例项目的“assets/city.json”，来源于国家统计局官网（http://www.stats.gov.cn/tjsj/tjbz/xzqhdm）
 *
 * @author 李玉江[QQ:1032694760]
 * @since 2015/12/15
 */
public class AddressData {

    List<Province> provinceList;

    ArrayList<ArrayList<String>> secondList = new ArrayList();
    ArrayList<ArrayList<ArrayList<String>>> thirdList = new ArrayList();


    public void parseData() {
        /**
         * 省市县数据
         */

        String jsonData = new GetJsonDataUtil().getJson(MyApplication.get(), "province.json");//获取assets目录下的json文件数据

        Gson gson = MyApplication.get().getAppComponent().getGson();
        provinceList = gson.fromJson(jsonData, new TypeToken<List<Province>>() {
        }.getType());

        for (Province province : provinceList) {


            ArrayList<City> cities = province.getCities();
            ArrayList<String> xCities = new ArrayList();
            ArrayList<ArrayList<String>> xCounties = new ArrayList();
            int citySize = cities.size();
            //添加地市
            for (int y = 0; y < citySize; y++) {
                City cit = cities.get(y);
                xCities.add(cit.getAreaName());
                ArrayList<County> counties = cit.getCounties();
                ArrayList<String> yCounties = new ArrayList();
                int countySize = counties.size();
                //添加区县
                if (countySize == 0) {
                    yCounties.add(cit.getAreaName());
                } else {
                    for (int z = 0; z < countySize; z++) {
                        yCounties.add(counties.get(z).getAreaName());
                    }
                }
                xCounties.add(yCounties);
            }
            secondList.add(xCities);
            thirdList.add(xCounties);
        }

    }

    public List<Province> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<Province> provinceList) {
        this.provinceList = provinceList;
    }

    public ArrayList<ArrayList<String>> getSecondList() {
        return secondList;
    }

    public void setSecondList(ArrayList<ArrayList<String>> secondList) {
        this.secondList = secondList;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getThirdList() {
        return thirdList;
    }

    public void setThirdList(ArrayList<ArrayList<ArrayList<String>>> thirdList) {
        this.thirdList = thirdList;
    }

    /**
     * 省市县抽象，本类及其子类不可混淆
     */
    public abstract static class Area {
        private String areaId;
        private String areaName;

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        @Override
        public String toString() {
            return "areaId=" + areaId + ",areaName=" + areaName;
        }

    }

    /**
     * 省份
     */
    public static class Province extends Area implements IPickerViewData {
        private ArrayList<City> cities = new ArrayList<City>();

        public ArrayList<City> getCities() {
            return cities;
        }

        public void setCities(ArrayList<City> cities) {
            this.cities = cities;
        }

        @Override
        public String getPickerViewText() {
            return getAreaName();
        }
    }

    /**
     * 地市
     */
    public static class City extends Area {
        private ArrayList<County> counties = new ArrayList<County>();

        public ArrayList<County> getCounties() {
            return counties;
        }

        public void setCounties(ArrayList<County> counties) {
            this.counties = counties;
        }

    }

    /**
     * 区县
     */
    public static class County extends Area {
    }
}
