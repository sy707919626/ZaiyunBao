package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;
import java.util.List;


public class DeteilListBean implements Serializable {

    /**
     * ECode : 1
     * ETypeId : 1
     * ETypeName : 1
     * RepairType : 1
     * imgs : ["/img/a.jpg","/img/a.jpg"]
     */

    private String ECode;
    private String ETypeId;
    private String ETypeName;
    private int RepairType;
    private String Remark;
    private List<String> imgs;

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getECode() {
        return ECode;
    }

    public void setECode(String ECode) {
        this.ECode = ECode;
    }

    public String getETypeId() {
        return ETypeId;
    }

    public void setETypeId(String ETypeId) {
        this.ETypeId = ETypeId;
    }

    public String getETypeName() {
        return ETypeName;
    }

    public void setETypeName(String ETypeName) {
        this.ETypeName = ETypeName;
    }

    public int getRepairType() {
        return RepairType;
    }

    public void setRepairType(int RepairType) {
        this.RepairType = RepairType;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}
