package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/24.
 */

public class AreaBean implements Serializable {

    /**
     * Area : 长沙市岳麓区
     * Name : 望月湖仓库
     * ContactName : null
     * ContactPhone : 2222
     */

    private String Area;
    private String Name;
    private String ContactName;
    private String ContactPhone;

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String ContactName) {
        this.ContactName = ContactName;
    }

    public String getContactPhone() {
        return ContactPhone;
    }

    public void setContactPhone(String ContactPhone) {
        this.ContactPhone = ContactPhone;
    }
}
