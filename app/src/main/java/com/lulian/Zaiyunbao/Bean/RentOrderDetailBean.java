package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/7.
 */

public class RentOrderDetailBean implements Serializable {
    /**
     * ReceiverName : 顾明
     * RentTime : null
     * Id : 8e092fa5-9eb1-4f30-bffb-8ec041d27d38
     * EquipmentName : 车载保温箱
     * Norm :
     * Picture :
     * Count : 2
     * CreateTime : 2019-03-05 17:50:21
     * BackLink : 顾明
     * BackLinkPhone : 13812345679
     * Status : 0
     * TransferWay : null
     * ReceiveAddress : null
     * Price : null
     */

    private String ReceiverName;
    private String RentTime;
    private String Id;
    private String EquipmentName;
    private String Norm;
    private String Picture;
    private int Count;
    private String CreateTime;
    private String BackLink;
    private String BackLinkPhone;
    private int Status;
    private int TransferWay;
    private String ReceiveAddress;
    private float Price;
    private String RentOrderID;

    public String getReceiverPhone() {
        return ReceiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        ReceiverPhone = receiverPhone;
    }

    private String ReceiverPhone;

    public String getRentOrderID() {
        return RentOrderID;
    }

    public void setRentOrderID(String rentOrderID) {
        RentOrderID = rentOrderID;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }

    public String getRentTime() {
        return RentTime;
    }

    public void setRentTime(String rentTime) {
        RentTime = rentTime;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getBackLink() {
        return BackLink;
    }

    public void setBackLink(String backLink) {
        BackLink = backLink;
    }

    public String getBackLinkPhone() {
        return BackLinkPhone;
    }

    public void setBackLinkPhone(String backLinkPhone) {
        BackLinkPhone = backLinkPhone;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getTransferWay() {
        return TransferWay;
    }

    public void setTransferWay(int transferWay) {
        TransferWay = transferWay;
    }

    public String getReceiveAddress() {
        return ReceiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        ReceiveAddress = receiveAddress;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }
}
