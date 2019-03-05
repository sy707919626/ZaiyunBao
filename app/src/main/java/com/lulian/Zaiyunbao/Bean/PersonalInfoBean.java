package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/24.
 */

public class PersonalInfoBean implements Serializable {

    /**
     * Name : 谢丽
     * Phone : 13657352060
     * ContactAdress : null
     */

    private String Name;
    private String Phone;
    private String ContactAdress;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getContactAdress() {
        return ContactAdress;
    }

    public void setContactAdress(String ContactAdress) {
        this.ContactAdress = ContactAdress;
    }
}
