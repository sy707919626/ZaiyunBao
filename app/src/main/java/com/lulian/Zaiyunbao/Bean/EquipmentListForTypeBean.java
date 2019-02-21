package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/5.
 */

public class EquipmentListForTypeBean implements Serializable {

    /**
     * Id : 7fe8cff3-c798-4642-8ba5-b3cc60856c53
     * EquipmentName : TEST
     */

    private String Id;
    private String EquipmentName;
    private int Deposit;

    public int getDeposit() {
        return Deposit;
    }

    public void setDeposit(int deposit) {
        Deposit = deposit;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getEquipmentName() {
        return EquipmentName;
    }

    public void setEquipmentName(String EquipmentName) {
        this.EquipmentName = EquipmentName;
    }
}
