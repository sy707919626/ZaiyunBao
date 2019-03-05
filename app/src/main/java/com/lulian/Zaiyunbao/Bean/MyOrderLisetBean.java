package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/11/8.
 */

public class MyOrderLisetBean implements Serializable {

    /**
     * total : 6
     * size : 10
     * index : 1
     * rows : [{"Count":2,"OrderCreateTime":"2018-11-07 17:06:50","Status":1,"TypeName":"托盘","Id":"824a45c1-c2c6-48df-9b0c-3415ca87668e","TypeId":"5868ADEF-67B2-4BCD-B166-BB8F5AD2EFC5","EquipmentBaseNo":"TEST","EquipmentName":"木质托盘","Unit":"m","Model":"X00001","Norm":"3XS","BaseMaterial":"木头","Size":"2.32","SpecifiedLoad":23.2,"StaticLoad":3322,"CarryingLoad":44.33,"Volume":null,"WarmLong":null,"OnLoad":null,"UserTimeLength":null,"Remark":"","PriceNow":120,"CreateId":null,"CreateTime":null,"UpdateId":null,"UpdateTime":null,"Deleted":null,"CreateUser":null,"UpdateUser":null,"LoadDate":null,"DaiGouDaiZu":null,"HuiGouFanZhu":null,"ZuLin":null,"Picture":"","Structural":null,"Deposit":null}]
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
         * Count : 2
         * OrderCreateTime : 2018-11-07 17:06:50
         * Status : 1
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
         * Structural : null
         * Deposit : null
         */

        private int Count; //租赁个数
        private int ZulinModel; //租赁模式
        private String OrderCreateTime; //订单生成时间
        private int Status; //订单状态
        private String TypeName;//设备类型
        private String Id; //设备ID
        private String TypeId; //设备类型Id
        private String EquipmentBaseNo; //物资编号
        private String EquipmentName; //物资名称
        private String Unit; //计量单位
        private String Model;//型号
        private String Norm;//规格
        private String BaseMaterial;//材质
        private String Size; //尺寸
        private double SpecifiedLoad;//额定载荷
        private double StaticLoad;//静载
        private double CarryingLoad; //动载
        private double Volume;//容积
        private double WarmLong; //保温时长
        private double OnLoad; //上架载荷
        private float UserTimeLength;//使用期限
        private String Remark;
        private float PriceNow;
        private String CreateId;
        private String CreateTime;
        private String UpdateId;
        private String UpdateTime;
        private String Deleted;
        private String CreateUser;
        private String UpdateUser;
        private Object LoadDate;
        private int DaiGouDaiZu;
        private int HuiGouFanZhu;
        private int ZuLin;
        private String Picture;
        private Object Structural;
        private float Deposit;
        private String OrderNo; //订单号码
        private String OrdersId; //订单ID
        private String StoreId; //仓库ID
        private String StoreName;//仓库名
        private int FormType;
        private int IsRendIn; //用户是否为租入方(IsRendIn 1是0否)
        private float OrderDeposit; //押金
        private float TrafficFee; //运费
        private float Price; //租赁单价
        private String ReceiveUserId; //租出方ID

        public String getReceiveUserId() {
            return ReceiveUserId;
        }

        public void setReceiveUserId(String receiveUserId) {
            ReceiveUserId = receiveUserId;
        }

        public float getPrice() {
            return Price;
        }

        public void setPrice(float price) {
            Price = price;
        }

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

        public int getIsRendIn() {
            return IsRendIn;
        }

        public void setIsRendIn(int isRendIn) {
            IsRendIn = isRendIn;
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

        public String getStoreName() {
            return StoreName;
        }

        public void setStoreName(String storeName) {
            StoreName = storeName;
        }

        public int getZulinModel() {
            return ZulinModel;
        }

        public void setZulinModel(int zulinModel) {
            ZulinModel = zulinModel;
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

        public String getDeleted() {
            return Deleted;
        }

        public void setDeleted(String deleted) {
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

        public Object getLoadDate() {
            return LoadDate;
        }

        public void setLoadDate(Object loadDate) {
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

        public Object getStructural() {
            return Structural;
        }

        public void setStructural(Object structural) {
            Structural = structural;
        }

        public float getDeposit() {
            return Deposit;
        }

        public void setDeposit(float deposit) {
            Deposit = deposit;
        }

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        public String getOrderCreateTime() {
            return OrderCreateTime;
        }

        public void setOrderCreateTime(String OrderCreateTime) {
            this.OrderCreateTime = OrderCreateTime;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public String getTypeName() {
            return TypeName;
        }

        public void setTypeName(String TypeName) {
            this.TypeName = TypeName;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getTypeId() {
            return TypeId;
        }

        public void setTypeId(String TypeId) {
            this.TypeId = TypeId;
        }

        public String getEquipmentBaseNo() {
            return EquipmentBaseNo;
        }

        public void setEquipmentBaseNo(String EquipmentBaseNo) {
            this.EquipmentBaseNo = EquipmentBaseNo;
        }

        public String getEquipmentName() {
            return EquipmentName;
        }

        public void setEquipmentName(String EquipmentName) {
            this.EquipmentName = EquipmentName;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        public String getModel() {
            return Model;
        }

        public void setModel(String Model) {
            this.Model = Model;
        }

        public String getNorm() {
            return Norm;
        }

        public void setNorm(String Norm) {
            this.Norm = Norm;
        }

        public String getBaseMaterial() {
            return BaseMaterial;
        }

        public void setBaseMaterial(String BaseMaterial) {
            this.BaseMaterial = BaseMaterial;
        }

        public String getSize() {
            return Size;
        }

        public void setSize(String Size) {
            this.Size = Size;
        }

        public double getSpecifiedLoad() {
            return SpecifiedLoad;
        }

        public void setSpecifiedLoad(double SpecifiedLoad) {
            this.SpecifiedLoad = SpecifiedLoad;
        }

        public double getStaticLoad() {
            return StaticLoad;
        }

        public void setStaticLoad(double StaticLoad) {
            this.StaticLoad = StaticLoad;
        }

        public double getCarryingLoad() {
            return CarryingLoad;
        }

        public void setCarryingLoad(double CarryingLoad) {
            this.CarryingLoad = CarryingLoad;
        }


    }
}
