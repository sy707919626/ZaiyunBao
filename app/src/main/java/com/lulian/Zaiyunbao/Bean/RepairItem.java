package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/1/24.
 */

public class RepairItem implements Serializable {

    /**
     * detailId : 5c6b9270-d897-4084-b2d2-8e8b4eadc5a7
     * FormId : 5ce2ec09-929c-4abb-bab2-d6f9bccc2662
     * ECode : ET038111503000002
     * ETypeId : 9a73a9bf-7a79-4917-b2d2-7447ec6aadc3
     * ETypeName : 药用保温U型箱
     * Urls : /upload/190124095319244.jpg
     * RepairType : 2
     * Remark : null
     */

    private String detailId;
    private String FormId;
    private String ECode;
    private String ETypeId;
    private String ETypeName;
    private String Urls;
    private int RepairType;
    private String Remark;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getFormId() {
        return FormId;
    }

    public void setFormId(String FormId) {
        this.FormId = FormId;
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

    public String getUrls() {
        return Urls;
    }

    public void setUrls(String Urls) {
        this.Urls = Urls;
    }

    public int getRepairType() {
        return RepairType;
    }

    public void setRepairType(int RepairType) {
        this.RepairType = RepairType;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }
}
