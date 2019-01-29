package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/11.
 */

public class EquipmentInfoBean implements Serializable {

    /**
     * equipmentId : 9a73a9bf-7a79-4917-b2d2-7447ec6aadc3
     * TypeId : e4a02ecb-abbe-436c-ac80-d7fa873d2eeb
     * TypeName : 保温箱
     * EquipmentBaseNo : BXYY
     * EquipmentName : 药用保温U型箱
     * ECode : ET038111503000001
     * StorehouseId : F8D0488B-B305-42FC-A66E-0EF74F2FC955
     * MemberID : 674db0be-70af-4ede-9afc-c9e525a6e5bf
     */

    private String equipmentId;
    private String TypeId;
    private String TypeName;
    private String EquipmentBaseNo;
    private String EquipmentName;
    private String ECode;
    private String StorehouseId;
    private String MemberID;

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getTypeId() {
        return TypeId;
    }

    public void setTypeId(String TypeId) {
        this.TypeId = TypeId;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
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

    public String getECode() {
        return ECode;
    }

    public void setECode(String ECode) {
        this.ECode = ECode;
    }

    public String getStorehouseId() {
        return StorehouseId;
    }

    public void setStorehouseId(String StorehouseId) {
        this.StorehouseId = StorehouseId;
    }

    public String getMemberID() {
        return MemberID;
    }

    public void setMemberID(String MemberID) {
        this.MemberID = MemberID;
    }
}
