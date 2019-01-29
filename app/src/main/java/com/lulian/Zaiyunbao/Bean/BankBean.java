package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/1/21.
 */

public class BankBean implements Serializable {
    /**
     * Id : 817d98b3-9833-4983-8f14-a63285ce3e0c
     * UserId : 674db0be-70af-4ede-9afc-c9e525a6e5bf
     * AccountNo : 6217002920147311022
     * AccountName : 谢丽
     * AccountBank : 建设银行·龙卡通
     * Mobile : 13657352060
     * AccountType : null
     * Address : null
     * CreateId : null
     * CreateUser : null
     * CreateTime : null
     * UpdateId : null
     * UpdateUser : null
     * UpdateTime : null
     * Deleted : 0
     * IsBind : 1
     */

    private String Id;
    private String UserId;
    private String AccountNo;
    private String AccountName;
    private String AccountBank;
    private String Mobile;
    private Object AccountType;
    private Object Address;
    private Object CreateId;
    private Object CreateUser;
    private Object CreateTime;
    private Object UpdateId;
    private Object UpdateUser;
    private Object UpdateTime;
    private int Deleted;
    private int IsBind;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String AccountNo) {
        this.AccountNo = AccountNo;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String AccountName) {
        this.AccountName = AccountName;
    }

    public String getAccountBank() {
        return AccountBank;
    }

    public void setAccountBank(String AccountBank) {
        this.AccountBank = AccountBank;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public Object getAccountType() {
        return AccountType;
    }

    public void setAccountType(Object AccountType) {
        this.AccountType = AccountType;
    }

    public Object getAddress() {
        return Address;
    }

    public void setAddress(Object Address) {
        this.Address = Address;
    }

    public Object getCreateId() {
        return CreateId;
    }

    public void setCreateId(Object CreateId) {
        this.CreateId = CreateId;
    }

    public Object getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(Object CreateUser) {
        this.CreateUser = CreateUser;
    }

    public Object getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Object CreateTime) {
        this.CreateTime = CreateTime;
    }

    public Object getUpdateId() {
        return UpdateId;
    }

    public void setUpdateId(Object UpdateId) {
        this.UpdateId = UpdateId;
    }

    public Object getUpdateUser() {
        return UpdateUser;
    }

    public void setUpdateUser(Object UpdateUser) {
        this.UpdateUser = UpdateUser;
    }

    public Object getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Object UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    public int getDeleted() {
        return Deleted;
    }

    public void setDeleted(int Deleted) {
        this.Deleted = Deleted;
    }

    public int getIsBind() {
        return IsBind;
    }

    public void setIsBind(int IsBind) {
        this.IsBind = IsBind;
    }
}
