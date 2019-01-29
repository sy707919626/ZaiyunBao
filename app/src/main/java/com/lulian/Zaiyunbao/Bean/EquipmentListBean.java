package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/10/31.
 */

public class EquipmentListBean implements Serializable {

    /**
     * total : 1
     * size : 10
     * index : 1
     * rows : [{"PValue":10,"Quantity":1,"TypeName":"托盘","Operator":"f3d85ebb-e017-4863-9d79-6e4df3efd448","Id":"824a45c1-c2c6-48df-9b0c-3415ca87668e","TypeId":"5868ADEF-67B2-4BCD-B166-BB8F5AD2EFC5","EquipmentBaseNo":"TEST","EquipmentName":"木质托盘","Unit":"m","Model":"X00001","Norm":"3XS","BaseMaterial":"木头","Size":"2.32","SpecifiedLoad":23.2,"StaticLoad":3322,"CarryingLoad":44.33,"Volume":null,"WarmLong":null,"OnLoad":null,"UserTimeLength":null,"Remark":"","PriceNow":120,"CreateId":null,"CreateTime":null,"UpdateId":null,"UpdateTime":null,"Deleted":null,"CreateUser":null,"UpdateUser":null,"LoadDate":null,"DaiGouDaiZu":null,"HuiGouFanZhu":null,"ZuLin":null,"Picture":"}]
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
         * PValue : 10
         * Quantity : 1
         * TypeName : 托盘
         * Operator : f3d85ebb-e017-4863-9d79-6e4df3efd448
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
         * UserType:null
         * Picture :
         */

        private float PValue;
        private String StorehouseId; //仓库ID
        private int Quantity;
        private String TypeName;
        private String Id;
        private String TypeId;
        private String EquipmentBaseNo;
        private String EquipmentName;
        private String Unit;
        private String Model;
        private String Norm;//规格
        private String BaseMaterial;//材质
        private String Size;
        private double SpecifiedLoad; //额定载荷
        private double StaticLoad;//静载
        private double CarryingLoad;//动载
        private double Volume; //容积
        private double WarmLong;//保温时长
        private double OnLoad;//上架载荷
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
        private int UserType; //发布人类型
        private String Structural;
        private int Deposit; //押金
        private String UID; //使用者Id
        private String Operator;  //加盟商id

        public String getUID() {
            return UID;
        }

        public void setUID(String UID) {
            this.UID = UID;
        }

        public String getStorehouseId() {
            return StorehouseId;
        }

        public void setStorehouseId(String storehouseId) {
            StorehouseId = storehouseId;
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

        public int getUserType() {
            return UserType;
        }

        public void setUserType(int userType) {
            UserType = userType;
        }

        public float getPValue() {
            return PValue;
        }

        public void setPValue(float PValue) {
            this.PValue = PValue;
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

        public String getOperator() {
            return Operator;
        }

        public void setOperator(String operator) {
            Operator = operator;
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
}
