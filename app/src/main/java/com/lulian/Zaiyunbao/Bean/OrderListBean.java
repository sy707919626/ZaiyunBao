package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/11/6.
 */

public class OrderListBean implements Serializable {

    /**
     * ETypeId : 824a45c1-c2c6-48df-9b0c-3415ca87668e
     * TargetDeliveryTime : 2018-11-07 00:00:00
     * ReturnBakcTime : 2018-11-13 00:00:00
     * Price : 10
     * Count : 2
     * RentAmount : 120
     * CreateUserType : 2
     * CreateId : 674db0be-70af-4ede-9afc-c9e525a6e5bf
     * ReceiveUserType : 1
     * TransferWay : 2
     * RentDates : 6
     * Status : 1
     * Remark :
     * ZulinModel : 1
     * TakeAddress : 54564654
     * Deposit : 60
     * Recommend : 阿达
     * RecommendPhone : 13657352000
     * Release :
     * ReleasePhone :
     * StoreName :
     * EquipmentName : 木质托盘
     * TypeName : 托盘
     * OrderId : 34da4db5-f596-49b6-b0b7-1b0a27774977
     * Id : 34da4db5-f596-49b6-b0b7-1b0a27774977
     * OrderNo : 20181107170650885
     * OrderName : 租赁单
     * FormType : 2
     * ETypeName : null
     * TrafficFee : 0
     * ReceiveUserId : null
     * ArrivalTime : null
     * CreateUser : null
     * CreateTime : 2018-11-07 17:06:50
     * UpdateId : null
     * UpdateUser : null
     * UpdateTime : null
     * Deleted : 0
     * AlianceLink : null
     * AlianceLinkPhone : null
     * FreeDates : null
     * ContactName : null
     * ContactPhone : null
     * ReceiveAddress : null
     * HandRentWay : null
     * RentWay : null
     * ReceiveWay : null
     */

    private String ETypeId;
    private String TargetDeliveryTime; //起租时间
    private String ReturnBakcTime;  //退组时间
    private float Price;  //租金单价
    private int Count;  //租赁数量
    private float RentAmount; //租金
    private int CreateUserType; //当前用户类型  UserType
    private String CreateId; //当前用户ID   userID
    private int ReceiveUserType; //接单用户类型 1=加盟商 2=客户 3=经纪人
    private int TransferWay; //送货方式 1送货上门 2用户自取 3带托运输
    private int RentDates; //租赁天数
    private int Status;  //0求租,1租赁
    private String Remark;
    private int ZulinModel; //1分时租赁 2 分次租赁
    private String TakeAddress; //送货地址
    private int Deposit; //押金
    private String Recommend; //推荐人
    private String RecommendPhone; //推荐人电话
    private String Release; //发布人
    private String ReleasePhone; //发布人电话
    private String StoreName; //仓库名
    private String Picture; //图片实体
    private String EquipmentName; //设备名称
    private String TypeName;
    private String OrderId; //订单ID
    private String Id; //订单ID
    private String OrderNo;//订单号码
    private String OrderName; //订单名称
    private int FormType;
    private String ETypeName; //设备类型
    private int TrafficFee;
    private String ReceiveUserId;
    private String ArrivalTime;
    private String CreateUser;
    private String CreateTime;
    private String UpdateId;
    private String UpdateUser;
    private String UpdateTime;
    private int Deleted;
    private String AlianceLink;
    private String AlianceLinkPhone;
    private String FreeDates;
    private String ContactName;
    private String ContactPhone;
    private String ReceiveAddress;
    private String HandRentWay;
    private String RentWay;
    private String ReceiveWay;

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public int getTrafficFee() {
        return TrafficFee;
    }

    public void setTrafficFee(int trafficFee) {
        TrafficFee = trafficFee;
    }

    public String getReceiveUserId() {
        return ReceiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        ReceiveUserId = receiveUserId;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public String getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(String createUser) {
        CreateUser = createUser;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getUpdateId() {
        return UpdateId;
    }

    public void setUpdateId(String updateId) {
        UpdateId = updateId;
    }

    public String getUpdateUser() {
        return UpdateUser;
    }

    public void setUpdateUser(String updateUser) {
        UpdateUser = updateUser;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public int getDeleted() {
        return Deleted;
    }

    public void setDeleted(int deleted) {
        Deleted = deleted;
    }

    public String getAlianceLink() {
        return AlianceLink;
    }

    public void setAlianceLink(String alianceLink) {
        AlianceLink = alianceLink;
    }

    public String getAlianceLinkPhone() {
        return AlianceLinkPhone;
    }

    public void setAlianceLinkPhone(String alianceLinkPhone) {
        AlianceLinkPhone = alianceLinkPhone;
    }

    public String getFreeDates() {
        return FreeDates;
    }

    public void setFreeDates(String freeDates) {
        FreeDates = freeDates;
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

    public String getReceiveAddress() {
        return ReceiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        ReceiveAddress = receiveAddress;
    }

    public String getHandRentWay() {
        return HandRentWay;
    }

    public void setHandRentWay(String handRentWay) {
        HandRentWay = handRentWay;
    }

    public String getRentWay() {
        return RentWay;
    }

    public void setRentWay(String rentWay) {
        RentWay = rentWay;
    }

    public String getReceiveWay() {
        return ReceiveWay;
    }

    public void setReceiveWay(String receiveWay) {
        ReceiveWay = receiveWay;
    }

    public String getETypeId() {
        return ETypeId;
    }

    public void setETypeId(String ETypeId) {
        this.ETypeId = ETypeId;
    }

    public String getTargetDeliveryTime() {
        return TargetDeliveryTime;
    }

    public void setTargetDeliveryTime(String TargetDeliveryTime) {
        this.TargetDeliveryTime = TargetDeliveryTime;
    }

    public String getReturnBakcTime() {
        return ReturnBakcTime;
    }

    public void setReturnBakcTime(String ReturnBakcTime) {
        this.ReturnBakcTime = ReturnBakcTime;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float Price) {
        this.Price = Price;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public float getRentAmount() {
        return RentAmount;
    }

    public void setRentAmount(float RentAmount) {
        this.RentAmount = RentAmount;
    }

    public int getCreateUserType() {
        return CreateUserType;
    }

    public void setCreateUserType(int CreateUserType) {
        this.CreateUserType = CreateUserType;
    }

    public String getCreateId() {
        return CreateId;
    }

    public void setCreateId(String CreateId) {
        this.CreateId = CreateId;
    }

    public int getReceiveUserType() {
        return ReceiveUserType;
    }

    public void setReceiveUserType(int ReceiveUserType) {
        this.ReceiveUserType = ReceiveUserType;
    }

    public int getTransferWay() {
        return TransferWay;
    }

    public void setTransferWay(int TransferWay) {
        this.TransferWay = TransferWay;
    }

    public int getRentDates() {
        return RentDates;
    }

    public void setRentDates(int RentDates) {
        this.RentDates = RentDates;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public int getZulinModel() {
        return ZulinModel;
    }

    public void setZulinModel(int ZulinModel) {
        this.ZulinModel = ZulinModel;
    }

    public String getTakeAddress() {
        return TakeAddress;
    }

    public void setTakeAddress(String TakeAddress) {
        this.TakeAddress = TakeAddress;
    }

    public int getDeposit() {
        return Deposit;
    }

    public void setDeposit(int Deposit) {
        this.Deposit = Deposit;
    }

    public String getRecommend() {
        return Recommend;
    }

    public void setRecommend(String Recommend) {
        this.Recommend = Recommend;
    }

    public String getRecommendPhone() {
        return RecommendPhone;
    }

    public void setRecommendPhone(String RecommendPhone) {
        this.RecommendPhone = RecommendPhone;
    }

    public String getRelease() {
        return Release;
    }

    public void setRelease(String Release) {
        this.Release = Release;
    }

    public String getReleasePhone() {
        return ReleasePhone;
    }

    public void setReleasePhone(String ReleasePhone) {
        this.ReleasePhone = ReleasePhone;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String StoreName) {
        this.StoreName = StoreName;
    }

    public String getEquipmentName() {
        return EquipmentName;
    }

    public void setEquipmentName(String EquipmentName) {
        this.EquipmentName = EquipmentName;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String OrderId) {
        this.OrderId = OrderId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String OrderNo) {
        this.OrderNo = OrderNo;
    }

    public String getOrderName() {
        return OrderName;
    }

    public void setOrderName(String OrderName) {
        this.OrderName = OrderName;
    }

    public int getFormType() {
        return FormType;
    }

    public void setFormType(int FormType) {
        this.FormType = FormType;
    }

    public Object getETypeName() {
        return ETypeName;
    }

    public void setETypeName(String ETypeName) {
        this.ETypeName = ETypeName;
    }


}
