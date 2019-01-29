package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/10/31.
 */

public class EquipmentDetailBean implements Serializable {
    /**
     * Name : null
     * Phone : 1112
     * Price : 10
     * Area : 2
     * Quantity : 1
     * TypeName : 托盘
     * Id : 824a45c1-c2c6-48df-9b0c-3415ca87668e
     * TypeId : 5868ADEF-67B2-4BCD-B166-BB8F5AD2EFC5
     * EquipmentBaseNo : TEST
     * EquipmentName : 木质托盘
     * Unit : m
     * Model : X00001
     * Norm : 3XS
     * BaseMaterial : 木头
     * Size : 2.32
     * SpecifiedLoad : 23.2
     * StaticLoad : 3322
     * CarryingLoad : 44.33
     * Volume : null
     * WarmLong : null
     * OnLoad : null
     * UserTimeLength : null
     * Remark :
     * PriceNow : 120
     * CreateId : null
     * CreateTime : null
     * UpdateId : null
     * UpdateTime : null
     * Deleted : null
     * CreateUser : null
     * UpdateUser : null
     * LoadDate : null
     * DaiGouDaiZu : null
     * HuiGouFanZhu : null
     * ZuLin : null
     * Picture :
     */
    private String Name;
    private String Phone;
    private float Price; //租赁单价
    private String Area; //站点
    private int Quantity;//可租数量
    private String TypeName;//设备类型
    private String Id;//设备ID
    private String TypeId;//类型ID
    private String EquipmentBaseNo; //物资编号
    private String EquipmentName;//设备名
    private String Unit; //计量单位
    private String Model; //型号
    private String Norm;//规格
    private String BaseMaterial;//材质
    private String Size; //尺寸
    private double SpecifiedLoad; //额定载荷
    private double StaticLoad; //静载
    private double CarryingLoad; //动载
    private double Volume; //容积
    private double WarmLong; //保温时长
    private double OnLoad; //上架载荷
    private float UserTimeLength;//使用期限
    private String Remark;
    private float PriceNow;
    private String CreateId;
    private String CreateTime;
    private String UpdateId;
    private String UpdateTime;
    private int Deleted;
    private String CreateUser;
    private String UpdateUser;
    private String LoadDate;
    private int DaiGouDaiZu;
    private int HuiGouFanZhu;
    private int ZuLin;
    private String Picture;
    private int UserType;
    private int Deposit;
    private String UID;//使用者ID

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }


    public int getDeposit() {
        return Deposit;
    }

    public void setDeposit(int deposit) {
        Deposit = deposit;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTypeId() {
        return TypeId;
    }

    public void setTypeId(String typeId) {
        TypeId = typeId;
    }

    public String getEquipmentBaseNo() {
        return EquipmentBaseNo;
    }

    public void setEquipmentBaseNo(String equipmentBaseNo) {
        EquipmentBaseNo = equipmentBaseNo;
    }

    public String getEquipmentName() {
        return EquipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        EquipmentName = equipmentName;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getNorm() {
        return Norm;
    }

    public void setNorm(String norm) {
        Norm = norm;
    }

    public String getBaseMaterial() {
        return BaseMaterial;
    }

    public void setBaseMaterial(String baseMaterial) {
        BaseMaterial = baseMaterial;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public double getSpecifiedLoad() {
        return SpecifiedLoad;
    }

    public void setSpecifiedLoad(double specifiedLoad) {
        SpecifiedLoad = specifiedLoad;
    }

    public double getStaticLoad() {
        return StaticLoad;
    }

    public void setStaticLoad(double staticLoad) {
        StaticLoad = staticLoad;
    }

    public double getCarryingLoad() {
        return CarryingLoad;
    }

    public void setCarryingLoad(double carryingLoad) {
        CarryingLoad = carryingLoad;
    }

    public double getVolume() {
        return Volume;
    }

    public void setVolume(double volume) {
        Volume = volume;
    }

    public double getWarmLong() {
        return WarmLong;
    }

    public void setWarmLong(double warmLong) {
        WarmLong = warmLong;
    }

    public double getOnLoad() {
        return OnLoad;
    }

    public void setOnLoad(double onLoad) {
        OnLoad = onLoad;
    }

    public float getUserTimeLength() {
        return UserTimeLength;
    }

    public void setUserTimeLength(float userTimeLength) {
        UserTimeLength = userTimeLength;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public float getPriceNow() {
        return PriceNow;
    }

    public void setPriceNow(float priceNow) {
        PriceNow = priceNow;
    }

    public String getCreateId() {
        return CreateId;
    }

    public void setCreateId(String createId) {
        CreateId = createId;
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

    public String getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(String createUser) {
        CreateUser = createUser;
    }

    public String getUpdateUser() {
        return UpdateUser;
    }

    public void setUpdateUser(String updateUser) {
        UpdateUser = updateUser;
    }

    public String getLoadDate() {
        return LoadDate;
    }

    public void setLoadDate(String loadDate) {
        LoadDate = loadDate;
    }

    public int getDaiGouDaiZu() {
        return DaiGouDaiZu;
    }

    public void setDaiGouDaiZu(int daiGouDaiZu) {
        DaiGouDaiZu = daiGouDaiZu;
    }

    public int getHuiGouFanZhu() {
        return HuiGouFanZhu;
    }

    public void setHuiGouFanZhu(int huiGouFanZhu) {
        HuiGouFanZhu = huiGouFanZhu;
    }

    public int getZuLin() {
        return ZuLin;
    }

    public void setZuLin(int zuLin) {
        ZuLin = zuLin;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

}
