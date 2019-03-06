package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/5.
 */

public class WalletListDetails implements Serializable {
    /**
     * CashOrPay : 1
     * TradSum : 收入：
     * Monetary : 12.0
     * TradTime : 2019-02-26 16:18:23
     * SerialNo : RP2019022616182318231
     * accountNo : null
     * CompanysName : 个人客户
     */

    private int CashOrPay;
    private String TradSum;
    private double Monetary;
    private String TradTime;
    private String SerialNo;
    private String accountNo;
    private String CompanysName;

    public int getCashOrPay() {
        return CashOrPay;
    }

    public void setCashOrPay(int CashOrPay) {
        this.CashOrPay = CashOrPay;
    }

    public String getTradSum() {
        return TradSum;
    }

    public void setTradSum(String TradSum) {
        this.TradSum = TradSum;
    }

    public double getMonetary() {
        return Monetary;
    }

    public void setMonetary(double Monetary) {
        this.Monetary = Monetary;
    }

    public String getTradTime() {
        return TradTime;
    }

    public void setTradTime(String TradTime) {
        this.TradTime = TradTime;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String SerialNo) {
        this.SerialNo = SerialNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCompanysName() {
        return CompanysName;
    }

    public void setCompanysName(String CompanysName) {
        this.CompanysName = CompanysName;
    }

}
