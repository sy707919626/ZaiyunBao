package com.lulian.Zaiyunbao.Bean;

/**
 * Created by Administrator on 2018/11/2.
 */

public class LeasePriceFromBean {


    /**
     * AllAmount : 0
     * FreeDayAmount : 0
     * DiscountAmount : 0
     * Id : null
     * EDicId : cc193de9-3f8a-4b9c-99b7-fa4316837ea3
     * EName : 保温箱AA
     * TopLimit : 10
     * DowLimit : 5
     * Rent : 100
     * Unit : 元/个/天
     * FreeDays : 2
     * Discount : null
     * CustomerType : 1
     * RentWay : 1
     * Deposit : null
     */

    private float AllAmount; //总金额
    private int FreeDay; //免租期天数
    private float DiscountAmount; // 折扣金额
    private String Id;
    private String EDicId; //设备ID
    private String EName;
    private int TopLimit;
    private int DowLimit;
    private float Price; //租金
    private String Unit;
    private int FreeDays;//免租天数
    private float Discount;
    private int CustomerType;
    private int RentWay;
    private int Deposit; //押金

    public int getFreeDay() {
        return FreeDay;
    }

    public void setFreeDay(int freeDay) {
        FreeDay = freeDay;
    }

    public float getAllAmount() {
        return AllAmount;
    }

    public void setAllAmount(float allAmount) {
        AllAmount = allAmount;
    }


    public float getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(float discountAmount) {
        DiscountAmount = discountAmount;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEDicId() {
        return EDicId;
    }

    public void setEDicId(String EDicId) {
        this.EDicId = EDicId;
    }

    public String getEName() {
        return EName;
    }

    public void setEName(String EName) {
        this.EName = EName;
    }

    public int getTopLimit() {
        return TopLimit;
    }

    public void setTopLimit(int topLimit) {
        TopLimit = topLimit;
    }

    public int getDowLimit() {
        return DowLimit;
    }

    public void setDowLimit(int dowLimit) {
        DowLimit = dowLimit;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public int getFreeDays() {
        return FreeDays;
    }

    public void setFreeDays(int freeDays) {
        FreeDays = freeDays;
    }

    public float getDiscount() {
        return Discount;
    }

    public void setDiscount(float discount) {
        Discount = discount;
    }

    public int getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(int customerType) {
        CustomerType = customerType;
    }

    public int getRentWay() {
        return RentWay;
    }

    public void setRentWay(int rentWay) {
        RentWay = rentWay;
    }

    public int getDeposit() {
        return Deposit;
    }

    public void setDeposit(int deposit) {
        Deposit = deposit;
    }
}
