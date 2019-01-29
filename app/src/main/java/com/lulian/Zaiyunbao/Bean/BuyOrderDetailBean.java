package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/5.
 */

public class BuyOrderDetailBean implements Serializable {
    /**
     * Id : 7f205c43-fb72-49f4-aebd-231e363554de
     * EquipmentName : A类保湿箱
     * Norm : CC
     * Picture :
     * Count : 1
     * CreateTime : 2018-12-05 09:32:57
     * OrderStatus : 0
     * TargetDeliveryTime : null
     * TransferWay : 全款送货
     * ReceiveAddress : 湖南省长沙市开福区啊啊啊啊啊啊啊啊啊啊
     * ContactName : 大老板
     * ContactPhone : 13657352060
     * SupplierContactName : 大老板
     * SupplierContactPhone : 13333333333
     * Recommend :
     * Price : 250
     * Amount : 250
     * Advance : null
     * StoreName :
     * TypeId : e4a02ecb-abbe-436c-ac80-d7fa873d2eeb
     * TypeName : 保温箱
     */

    private String Id;  //订单Id
    private String EquipmentName;//设备名称
    private String Norm;
    private String Picture;
    private int Count; //数量
    private String CreateTime; //订单创建时间
    private int OrderStatus; //订单状态:0未接单,1已接单,2订单确认,3待发货,5已发货,6已收货，7退租中,8已退租,9已结算,10已撤销
    private String TargetDeliveryTime; //取托时间
    private String TransferWay; //货款以及交付
    private String ReceiveAddress; //收货地址
    private String ContactName; //购买方联系人
    private String ContactPhone; //购买方联系电话
    private String SupplierContactName; //出售方联系人
    private String SupplierContactPhone; //出售方联系人电话
    private String Recommend; //推荐人
    private float Price; //单价
    private float Amount; //总金额
    private int Advance; //订金
    private String TypeId;
    private String TypeName; //设备类型名称

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getEquipmentName() {
        return EquipmentName;
    }

    public void setEquipmentName(String EquipmentName) {
        this.EquipmentName = EquipmentName;
    }

    public String getNorm() {
        return Norm;
    }

    public void setNorm(String Norm) {
        this.Norm = Norm;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String Picture) {
        this.Picture = Picture;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int OrderStatus) {
        this.OrderStatus = OrderStatus;
    }

    public String getTargetDeliveryTime() {
        return TargetDeliveryTime;
    }

    public void setTargetDeliveryTime(String TargetDeliveryTime) {
        this.TargetDeliveryTime = TargetDeliveryTime;
    }

    public String getTransferWay() {
        return TransferWay;
    }

    public void setTransferWay(String TransferWay) {
        this.TransferWay = TransferWay;
    }

    public String getReceiveAddress() {
        return ReceiveAddress;
    }

    public void setReceiveAddress(String ReceiveAddress) {
        this.ReceiveAddress = ReceiveAddress;
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

    public String getSupplierContactName() {
        return SupplierContactName;
    }

    public void setSupplierContactName(String SupplierContactName) {
        this.SupplierContactName = SupplierContactName;
    }

    public String getSupplierContactPhone() {
        return SupplierContactPhone;
    }

    public void setSupplierContactPhone(String SupplierContactPhone) {
        this.SupplierContactPhone = SupplierContactPhone;
    }

    public String getRecommend() {
        return Recommend;
    }

    public void setRecommend(String Recommend) {
        this.Recommend = Recommend;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float Price) {
        this.Price = Price;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float Amount) {
        this.Amount = Amount;
    }

    public int getAdvance() {
        return Advance;
    }

    public void setAdvance(int Advance) {
        this.Advance = Advance;
    }

    public String getTypeId() {
        return TypeId;
    }

    public void setTypeId(String TypeId) {
        this.TypeId = TypeId;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }
}
