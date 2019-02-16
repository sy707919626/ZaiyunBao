package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/2/13.
 */

public class StaticsBean implements Serializable{

    /**
     * Deposits : 29400.0
     * ZDeposits : 3600.0
     * ZQuantity : 12
     * Quantity : 153
     * baofei : 150
     * xianzhi : 10
     * mianfei : 0
     * chaishi : 4
     */

    private double Deposits;
    private double ZDeposits;
    private int ZQuantity;
    private int Quantity;
    private int baofei;
    private int xianzhi;
    private int mianfei;
    private int chaishi;

    public double getDeposits() {
        return Deposits;
    }

    public void setDeposits(double Deposits) {
        this.Deposits = Deposits;
    }

    public double getZDeposits() {
        return ZDeposits;
    }

    public void setZDeposits(double ZDeposits) {
        this.ZDeposits = ZDeposits;
    }

    public int getZQuantity() {
        return ZQuantity;
    }

    public void setZQuantity(int ZQuantity) {
        this.ZQuantity = ZQuantity;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public int getBaofei() {
        return baofei;
    }

    public void setBaofei(int baofei) {
        this.baofei = baofei;
    }

    public int getXianzhi() {
        return xianzhi;
    }

    public void setXianzhi(int xianzhi) {
        this.xianzhi = xianzhi;
    }

    public int getMianfei() {
        return mianfei;
    }

    public void setMianfei(int mianfei) {
        this.mianfei = mianfei;
    }

    public int getChaishi() {
        return chaishi;
    }

    public void setChaishi(int chaishi) {
        this.chaishi = chaishi;
    }
}
