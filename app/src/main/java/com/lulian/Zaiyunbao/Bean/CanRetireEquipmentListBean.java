package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/11/22.
 */

public class CanRetireEquipmentListBean implements Serializable {

    /**
     * EDicId : 9a73a9bf-7a79-4917-b2d2-7447ec6aadc3
     * EquipmentName : 药用保温U型箱
     * StorehouseId : F8D0488B-B305-42FC-A66E-0EF74F2FC955
     * Quantity : 3
     */

    private String EDicId;
    private String EquipmentName;
    private String StorehouseId;
    private int Quantity;
    private String Norm;

    public String getNorm() {
        return Norm;
    }

    public void setNorm(String norm) {
        Norm = norm;
    }

    public String getEDicId() {
        return EDicId;
    }

    public void setEDicId(String EDicId) {
        this.EDicId = EDicId;
    }

    public String getEquipmentName() {
        return EquipmentName;
    }

    public void setEquipmentName(String EquipmentName) {
        this.EquipmentName = EquipmentName;
    }

    public String getStorehouseId() {
        return StorehouseId;
    }

    public void setStorehouseId(String StorehouseId) {
        this.StorehouseId = StorehouseId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }
}
