package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/10.
 */

public class loginBean implements Serializable {
    /**
     * UserId : aafaafd2-995a-44bf-8ba6-f26a1b4986b9
     * Name :
     * Phone :
     * UserType : 2
     */
    private String UserId;
    private String Name;
    private String Phone;
    private int UserType;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

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

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int UserType) {
        this.UserType = UserType;
    }
}
