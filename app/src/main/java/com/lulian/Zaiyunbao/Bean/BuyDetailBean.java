package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/4.
 */

public class BuyDetailBean implements Serializable {

    /**
     * Quantity : 5
     * TypeName : 保温箱
     * StorehouseId :
     * storeName : null
     * SupplierContactName : 测试
     * SupplierContactPhone : 13333333333
     * Id : 176c4cc1-9350-469b-8241-73d99b2ca4c8
     * TypeId : e4a02ecb-abbe-436c-ac80-d7fa873d2eeb
     * EquipmentBaseNo : BX
     * EquipmentName : 保温箱02
     * Unit : 个
     * Model : B
     * Norm : B
     * BaseMaterial : 金属
     * Size :
     * SpecifiedLoad : null
     * StaticLoad : null
     * CarryingLoad : null
     * Volume : null
     * WarmLong : null
     * OnLoad : null
     * UserTimeLength : null
     * Remark :
     * PriceNow : 200
     * CreateId : 24dedb73-9125-45da-882b-d49228e9ae2d
     * CreateTime : 2018-10-15 10:19:12
     * UpdateId : null
     * UpdateTime : null
     * Deleted : 0
     * CreateUser : 1
     * UpdateUser : null
     * LoadDate : null
     * DaiGouDaiZu : null
     * HuiGouFanZhu : null
     * ZuLin : null
     * Picture :
     * Structural :
     * Deposit : 250
     */

    private int Quantity;
    private String TypeName;
    private String StorehouseId;
    private String storeName;
    private String SupplierContactName; //卖方名字
    private String SupplierContactPhone; //卖方电话
    private String Id; //设备ID
    private String TypeId;
    private String EquipmentBaseNo; //设备编号
    private String EquipmentName; //设备名称
    private String Unit;
    private String Model;
    private String Norm;
    private String BaseMaterial; //材质
    private String Size;
    private float SpecifiedLoad;
    private float StaticLoad;
    private float CarryingLoad;
    private float Volume;
    private float WarmLong;
    private float OnLoad;
    private float UserTimeLength;
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
    private String Structural;
    private int Deposit;

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

    public String getStorehouseId() {
        return StorehouseId;
    }

    public void setStorehouseId(String storehouseId) {
        StorehouseId = storehouseId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSupplierContactName() {
        return SupplierContactName;
    }

    public void setSupplierContactName(String supplierContactName) {
        SupplierContactName = supplierContactName;
    }

    public String getSupplierContactPhone() {
        return SupplierContactPhone;
    }

    public void setSupplierContactPhone(String supplierContactPhone) {
        SupplierContactPhone = supplierContactPhone;
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

    public float getSpecifiedLoad() {
        return SpecifiedLoad;
    }

    public void setSpecifiedLoad(float specifiedLoad) {
        SpecifiedLoad = specifiedLoad;
    }

    public float getStaticLoad() {
        return StaticLoad;
    }

    public void setStaticLoad(float staticLoad) {
        StaticLoad = staticLoad;
    }

    public float getCarryingLoad() {
        return CarryingLoad;
    }

    public void setCarryingLoad(float carryingLoad) {
        CarryingLoad = carryingLoad;
    }

    public float getVolume() {
        return Volume;
    }

    public void setVolume(float volume) {
        Volume = volume;
    }

    public float getWarmLong() {
        return WarmLong;
    }

    public void setWarmLong(float warmLong) {
        WarmLong = warmLong;
    }

    public float getOnLoad() {
        return OnLoad;
    }

    public void setOnLoad(float onLoad) {
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

    public String getStructural() {
        return Structural;
    }

    public void setStructural(String structural) {
        Structural = structural;
    }

    public int getDeposit() {
        return Deposit;
    }

    public void setDeposit(int deposit) {
        Deposit = deposit;
    }
}
