package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/12/4.
 */

public class BuyOrderListBean implements Serializable {


    /**
     * total : 1
     * size : 10
     * index : 1
     * rows :
     */

    private int total;
    private int size;
    private int index;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * OrdersId : 7f205c43-fb72-49f4-aebd-231e363554de
         * OrderNo : 20181205093257103
         * Count : 1
         * OrderCreateTime : 2018-12-05 09:32:57
         * orderStatus : 0
         * TypeName : 保温箱
         * Id : c8057b0b-c785-4bae-820a-5cfa90411b4a
         * TypeId : e4a02ecb-abbe-436c-ac80-d7fa873d2eeb
         * EquipmentBaseNo : ET0301
         * EquipmentName : A类保湿箱
         * Unit : 个
         * Model : A
         * Norm : CC
         * BaseMaterial : 所料1
         * Size : 30*40
         * SpecifiedLoad : null
         * StaticLoad : null
         * CarryingLoad : null
         * Volume : null
         * WarmLong : null
         * OnLoad : null
         * UserTimeLength : null
         * Remark :
         * PriceNow : 250
         * CreateId : 24dedb73-9125-45da-882b-d49228e9ae2d
         * CreateTime : 2018-09-29 10:46:49
         * UpdateId : null
         * UpdateTime : null
         * Deleted : 0
         * CreateUser : 1
         * UpdateUser : null
         * LoadDate : null
         * DaiGouDaiZu : 20
         * HuiGouFanZhu : 30
         * ZuLin : 30
         * Picture :
         * Structural : null
         * Deposit : null
         */

        private String OrdersId;
        private String OrderNo;
        private int Count;
        private String OrderCreateTime;
        private int orderStatus;
        private String TypeName;
        private String Id;
        private String TypeId;
        private String EquipmentBaseNo;
        private String EquipmentName;
        private String Unit;
        private String Model;
        private String Norm;
        private String BaseMaterial;
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
        private int Advance; //订金
        private float Amount;//总金额

        public float getAmount() {
            return Amount;
        }

        public void setAmount(float amount) {
            Amount = amount;
        }

        public int getAdvance() {
            return Advance;
        }

        public void setAdvance(int advance) {
            Advance = advance;
        }

        public String getOrdersId() {
            return OrdersId;
        }

        public void setOrdersId(String ordersId) {
            OrdersId = ordersId;
        }

        public String getOrderNo() {
            return OrderNo;
        }

        public void setOrderNo(String orderNo) {
            OrderNo = orderNo;
        }

        public int getCount() {
            return Count;
        }

        public void setCount(int count) {
            Count = count;
        }

        public String getOrderCreateTime() {
            return OrderCreateTime;
        }

        public void setOrderCreateTime(String orderCreateTime) {
            OrderCreateTime = orderCreateTime;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
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
}
