package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/11/24.
 */

public class DeviceDetailsBean implements Serializable {

    /**
     * Id : f1c93057-5740-4191-bbb3-490b91f43e91
     * ECode : ET038111503000003
     * EquipmentName : 药用保温U型箱
     * Norm : 个
     * CirculationState : 1
     * UseStatus : 1
     * Picture : null
     */

    private String Id;
    private String ECode;
    private String EquipmentName;
    private String Norm;
    private int CirculationState;
    private int UseStatus;
    private String Picture;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getECode() {
        return ECode;
    }

    public void setECode(String ECode) {
        this.ECode = ECode;
    }

    public String getEquipmentName() {
        return EquipmentName;
    }

    public void setEquipmentName(String EquipmentName) {
        this.EquipmentName = EquipmentName;
    }

    public String getNorm() {
        return Norm;
    }

    public void setNorm(String Norm) {
        this.Norm = Norm;
    }

    public int getCirculationState() {
        return CirculationState;
    }

    public void setCirculationState(int CirculationState) {
        this.CirculationState = CirculationState;
    }

    public int getUseStatus() {
        return UseStatus;
    }

    public void setUseStatus(int UseStatus) {
        this.UseStatus = UseStatus;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String Picture) {
        this.Picture = Picture;
    }
}
