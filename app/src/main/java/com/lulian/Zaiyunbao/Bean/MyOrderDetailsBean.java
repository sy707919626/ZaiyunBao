package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/11/8.
 */

public class MyOrderDetailsBean implements Serializable {


    /**
     * EquipmentName : 木质托盘
     * Norm : 3XS
     * Picture :
     * Count : 2
     * CreateTime : 2018-11-07 17:06:50
     * Status : 1
     * TargetDeliveryTime : 2018-11-07 00:00:00
     * TransferWay : 2
     * TakeAddress : 54564654
     * ContactName : null
     * ContactPhone : null
     * Recommend : 阿达
     * Price : 10
     * RentDates : 6
     * RentAmount : 120
     * Deposit : 60
     * HandRentWay : null
     * StoreName :
     * FreeDates : null
     * OrgName : null
     * ReceiveName : null
     * AlianceLinkPhone : null
     */

    private String EquipmentName;
    private String Norm;
    private String Picture;
    private int Count;
    private String CreateTime;
    private int Status;
    private String TargetDeliveryTime;
    private int TransferWay;
    private String TakeAddress;
    private String ContactName; //租出方
    private String ContactPhone; //租出方
    private String Recommend;
    private float Price;
    private int RentDates;
    private float RentAmount;
    private int Deposit;
    private int HandRentWay;
    private String StoreName;//仓库名
    private int FreeDates;
    private String OrgName;
    private String ReceiveName; //接单方联系人
    private String AlianceLinkPhone;//接单方电话
    private String StoreId; //仓库ID
    private String TypeName;//设备类型
    private String TypeId; //设备类型Id
    private int FormType; //订单类型1求租2租赁3转租
    private float OrderDeposit; //押金
    private float TrafficFee; //运费

    public float getTrafficFee() {
        return TrafficFee;
    }

    public void setTrafficFee(float trafficFee) {
        TrafficFee = trafficFee;
    }

    public float getOrderDeposit() {
        return OrderDeposit;
    }

    public void setOrderDeposit(float orderDeposit) {
        OrderDeposit = orderDeposit;
    }

    public int getFormType() {
        return FormType;
    }

    public void setFormType(int formType) {
        FormType = formType;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public String getTypeId() {
        return TypeId;
    }

    public void setTypeId(String typeId) {
        TypeId = typeId;
    }

    public String getEquipmentName() {
        return EquipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        EquipmentName = equipmentName;
    }

    public String getNorm() {
        return Norm;
    }

    public void setNorm(String norm) {
        Norm = norm;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getTargetDeliveryTime() {
        return TargetDeliveryTime;
    }

    public void setTargetDeliveryTime(String targetDeliveryTime) {
        TargetDeliveryTime = targetDeliveryTime;
    }

    public int getTransferWay() {
        return TransferWay;
    }

    public void setTransferWay(int transferWay) {
        TransferWay = transferWay;
    }

    public String getTakeAddress() {
        return TakeAddress;
    }

    public void setTakeAddress(String takeAddress) {
        TakeAddress = takeAddress;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactPhone() {
        return ContactPhone;
    }

    public void setContactPhone(String contactPhone) {
        ContactPhone = contactPhone;
    }

    public String getRecommend() {
        return Recommend;
    }

    public void setRecommend(String recommend) {
        Recommend = recommend;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getRentDates() {
        return RentDates;
    }

    public void setRentDates(int rentDates) {
        RentDates = rentDates;
    }

    public float getRentAmount() {
        return RentAmount;
    }

    public void setRentAmount(float rentAmount) {
        RentAmount = rentAmount;
    }

    public int getDeposit() {
        return Deposit;
    }

    public void setDeposit(int deposit) {
        Deposit = deposit;
    }

    public int getHandRentWay() {
        return HandRentWay;
    }

    public void setHandRentWay(int handRentWay) {
        HandRentWay = handRentWay;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public int getFreeDates() {
        return FreeDates;
    }

    public void setFreeDates(int freeDates) {
        FreeDates = freeDates;
    }

    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String orgName) {
        OrgName = orgName;
    }

    public String getReceiveName() {
        return ReceiveName;
    }

    public void setReceiveName(String receiveName) {
        ReceiveName = receiveName;
    }

    public String getAlianceLinkPhone() {
        return AlianceLinkPhone;
    }

    public void setAlianceLinkPhone(String alianceLinkPhone) {
        AlianceLinkPhone = alianceLinkPhone;
    }
}
