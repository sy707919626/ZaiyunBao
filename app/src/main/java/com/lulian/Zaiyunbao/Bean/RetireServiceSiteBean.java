package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/11/22.
 */

public class RetireServiceSiteBean implements Serializable {

    /**
     * total : 11
     * size : 10
     * index : 1
     * rows : [{"Id":"2CD2E805-BCCF-4671-A84D-626B61CD70F8","Number":"006","Name":"望月湖仓库","Area":"湖南省长沙市开福区新河路460号","Touch":"2","Manager":"2","BelongMember":"72423555-2415-4778-85f7-1c4cb509a4c2","BelongMemberName":"11(11)","AreaX":null,"AreaY":null,"ImgUrl":null,"Remark":"2","CreateId":null,"CreateUser":null,"CreateTime":null,"UpdateId":"2e5dbe3a-9cdd-4501-a1f5-7a5105d6b69c","UpdateUser":"Admin","UpdateTime":"2019-03-25 16:04:22","Deleted":null,"XPoint":113.042084,"YPoint":28.284666,"Url":"/upload/404.jpg"},{"Id":"CC6AD5CD-FF3E-471D-8595-43D738260564","Number":"005","Name":"望月湖仓库1","Area":"湖南省长沙市开福区","Touch":"0731-56895688","Manager":"张三疯","BelongMember":"869f15de-5b98-4a28-ba26-38e23d311b02","BelongMemberName":"12(12)","AreaX":null,"AreaY":null,"ImgUrl":null,"Remark":"托盘 保温箱","CreateId":null,"CreateUser":null,"CreateTime":null,"UpdateId":"2e5dbe3a-9cdd-4501-a1f5-7a5105d6b69c","UpdateUser":"Admin","UpdateTime":"2019-03-25 16:04:15","Deleted":null,"XPoint":112.897202,"YPoint":28.269905,"Url":"/upload/404.jpg"},{"Id":"F8D0488B-B305-42FC-A66E-0EF74F2FC955","Number":"004","Name":"望月湖仓库2","Area":"湖南省长沙市芙蓉区","Touch":"2","Manager":"2","BelongMember":"f3d85ebb-e017-4863-9d79-6e4df3efd448","BelongMemberName":"00(13344445555)","AreaX":null,"AreaY":null,"ImgUrl":null,"Remark":"2","CreateId":null,"CreateUser":null,"CreateTime":null,"UpdateId":"2e5dbe3a-9cdd-4501-a1f5-7a5105d6b69c","UpdateUser":"Admin","UpdateTime":"2019-03-22 16:27:53","Deleted":null,"XPoint":112.967918,"YPoint":28.046209,"Url":"/upload/404.jpg"},{"Id":"23BE80DD-D0E0-432D-A620-D780225AB86C","Number":"003","Name":"望月湖仓库3","Area":"湖南省长沙市芙蓉区古汉路","Touch":"2","Manager":"2","BelongMember":"f3d85ebb-e017-4863-9d79-6e4df3efd448","BelongMemberName":"00(13344445555)","AreaX":null,"AreaY":null,"ImgUrl":null,"Remark":"2","CreateId":null,"CreateUser":null,"CreateTime":null,"UpdateId":"2e5dbe3a-9cdd-4501-a1f5-7a5105d6b69c","UpdateUser":"Admin","UpdateTime":"2019-03-25 16:04:29","Deleted":null,"XPoint":113.080597,"YPoint":28.254635,"Url":"/upload/404.jpg"},{"Id":"4407bf0a-6eca-4121-b688-cf96ac1dff04","Number":"002","Name":"其他仓库","Area":"湖南省长沙市芙蓉区","Touch":"其他仓库","Manager":"其他","BelongMember":"4f27151d-0c72-4891-adec-82f5501eaabc","BelongMemberName":"TEST1(123456)","AreaX":null,"AreaY":null,"ImgUrl":null,"Remark":"2","CreateId":"24dedb73-9125-45da-882b-d49228e9ae2d","CreateUser":"1","CreateTime":"2018-12-19 09:51:30","UpdateId":"2e5dbe3a-9cdd-4501-a1f5-7a5105d6b69c","UpdateUser":"Admin","UpdateTime":"2019-03-04 17:54:18","Deleted":null,"XPoint":113.081463,"YPoint":28.188463,"Url":"/upload/404.jpg"},{"Id":"01b15e4c-45b6-4617-adb9-85c5109ba60f","Number":"C00006","Name":"橡树园物料仓","Area":"湖南省长沙市岳麓区麓天路8","Touch":"","Manager":"11","BelongMember":"4f27151d-0c72-4891-adec-82f5501eaabc","BelongMemberName":"TEST1(123456)","AreaX":null,"AreaY":null,"ImgUrl":null,"Remark":"","CreateId":"24dedb73-9125-45da-882b-d49228e9ae2d","CreateUser":"1","CreateTime":"2018-12-22 15:39:08","UpdateId":"2e5dbe3a-9cdd-4501-a1f5-7a5105d6b69c","UpdateUser":"Admin","UpdateTime":"2019-03-25 16:04:08","Deleted":null,"XPoint":113.125443,"YPoint":28.246489,"Url":"/upload/404.jpg"},{"Id":"aebba33a-74e7-432c-a609-4a05d173d5e7","Number":"444","Name":"麓谷仓","Area":"岳麓区","Touch":"18774846095","Manager":"admin","BelongMember":"72423555-2415-4778-85f7-1c4cb509a4c2","BelongMemberName":"11(11)","AreaX":null,"AreaY":null,"ImgUrl":null,"Remark":"","CreateId":"dcacf358-0b7e-48b6-89e0-31fd54dc97d3","CreateUser":"xl","CreateTime":"2019-01-09 10:05:20","UpdateId":"2e5dbe3a-9cdd-4501-a1f5-7a5105d6b69c","UpdateUser":"Admin","UpdateTime":"2019-03-25 16:03:56","Deleted":null,"XPoint":113.026558,"YPoint":28.185884,"Url":"/upload/404.jpg"},{"Id":"7bdebfa8-c220-400e-9ce9-56b74b69d6fc","Number":"009","Name":"东方红仓储基地","Area":"湖南省长沙市岳麓区枫林三路932号(涉外桃园对面)","Touch":"(0731)89700977","Manager":"张三丰","BelongMember":"72423555-2415-4778-85f7-1c4cb509a4c2","BelongMemberName":"11(11)","AreaX":null,"AreaY":null,"ImgUrl":null,"Remark":"","CreateId":"2e5dbe3a-9cdd-4501-a1f5-7a5105d6b69c","CreateUser":"Admin","CreateTime":"2019-02-15 14:07:33","UpdateId":"2e5dbe3a-9cdd-4501-a1f5-7a5105d6b69c","UpdateUser":"Admin","UpdateTime":"2019-03-22 16:28:00","Deleted":null,"XPoint":112.88858,"YPoint":28.214918,"Url":"/upload/404.jpg"},{"Id":"9796a55e-696a-4e80-940e-babcffc8bef9","Number":"010","Name":"麓谷保利仓","Area":"湖南省长沙市岳麓区桐梓坡路","Touch":"13812345678","Manager":"小李","BelongMember":"dcacf358-0b7e-48b6-89e0-31fd54dc97d3","BelongMemberName":"xl(13548678516)","AreaX":null,"AreaY":null,"ImgUrl":null,"Remark":"","CreateId":"c8afa142-b638-4ea0-abf2-46ecea156735","CreateUser":"cs","CreateTime":"2019-02-18 13:34:30","UpdateId":"2e5dbe3a-9cdd-4501-a1f5-7a5105d6b69c","UpdateUser":"Admin","UpdateTime":"2019-03-25 16:04:39","Deleted":null,"XPoint":112.85006,"YPoint":28.136969,"Url":"/upload/404.jpg"},{"Id":"0886d702-d09c-4f59-8993-de8b61012bf0","Number":"011","Name":"麓天仓","Area":"湖南省长沙市麓天路","Touch":"13812345679","Manager":"顾明","BelongMember":"d74295d7-cb25-4d89-897c-cfd88075e28f","BelongMemberName":"湖南货运公司(13912345678)","AreaX":null,"AreaY":null,"ImgUrl":null,"Remark":"","CreateId":"d74295d7-cb25-4d89-897c-cfd88075e28f","CreateUser":"hy","CreateTime":"2019-03-04 16:27:06","UpdateId":"2e5dbe3a-9cdd-4501-a1f5-7a5105d6b69c","UpdateUser":"Admin","UpdateTime":"2019-03-22 16:28:13","Deleted":null,"XPoint":112.920776,"YPoint":28.296881,"Url":"/upload/404.jpg"}]
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

    public static class RowsBean implements Serializable{
        /**
         * Id : 2CD2E805-BCCF-4671-A84D-626B61CD70F8
         * Number : 006
         * Name : 望月湖仓库
         * Area : 湖南省长沙市开福区新河路460号
         * Touch : 2
         * Manager : 2
         * BelongMember : 72423555-2415-4778-85f7-1c4cb509a4c2
         * BelongMemberName : 11(11)
         * AreaX : null
         * AreaY : null
         * ImgUrl : null
         * Remark : 2
         * CreateId : null
         * CreateUser : null
         * CreateTime : null
         * UpdateId : 2e5dbe3a-9cdd-4501-a1f5-7a5105d6b69c
         * UpdateUser : Admin
         * UpdateTime : 2019-03-25 16:04:22
         * Deleted : null
         * XPoint : 113.042084
         * YPoint : 28.284666
         * Url : /upload/404.jpg
         */

        private String Id;
        private String Number;
        private String Name;
        private String Area;
        private String Touch;
        private String Manager;
        private String BelongMember;
        private String BelongMemberName;
        private String AreaX;
        private String AreaY;
        private String ImgUrl;
        private String Remark;
        private String CreateId;
        private String CreateUser;
        private String CreateTime;
        private String UpdateId;
        private String UpdateUser;
        private String UpdateTime;
        private boolean Deleted;
        private double XPoint;
        private double YPoint;
        private String Url;
        private int IsManager; //1.平台 2.加盟商

        public int getIsManager() {
            return IsManager;
        }

        public void setIsManager(int isManager) {
            IsManager = isManager;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getNumber() {
            return Number;
        }

        public void setNumber(String number) {
            Number = number;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getArea() {
            return Area;
        }

        public void setArea(String area) {
            Area = area;
        }

        public String getTouch() {
            return Touch;
        }

        public void setTouch(String touch) {
            Touch = touch;
        }

        public String getManager() {
            return Manager;
        }

        public void setManager(String manager) {
            Manager = manager;
        }

        public String getBelongMember() {
            return BelongMember;
        }

        public void setBelongMember(String belongMember) {
            BelongMember = belongMember;
        }

        public String getBelongMemberName() {
            return BelongMemberName;
        }

        public void setBelongMemberName(String belongMemberName) {
            BelongMemberName = belongMemberName;
        }

        public String getAreaX() {
            return AreaX;
        }

        public void setAreaX(String areaX) {
            AreaX = areaX;
        }

        public String getAreaY() {
            return AreaY;
        }

        public void setAreaY(String areaY) {
            AreaY = areaY;
        }

        public String getImgUrl() {
            return ImgUrl;
        }

        public void setImgUrl(String imgUrl) {
            ImgUrl = imgUrl;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public String getCreateId() {
            return CreateId;
        }

        public void setCreateId(String createId) {
            CreateId = createId;
        }

        public String getCreateUser() {
            return CreateUser;
        }

        public void setCreateUser(String createUser) {
            CreateUser = createUser;
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

        public String getUpdateUser() {
            return UpdateUser;
        }

        public void setUpdateUser(String updateUser) {
            UpdateUser = updateUser;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String updateTime) {
            UpdateTime = updateTime;
        }

        public boolean isDeleted() {
            return Deleted;
        }

        public void setDeleted(boolean deleted) {
            Deleted = deleted;
        }

        public double getXPoint() {
            return XPoint;
        }

        public void setXPoint(double XPoint) {
            this.XPoint = XPoint;
        }

        public double getYPoint() {
            return YPoint;
        }

        public void setYPoint(double YPoint) {
            this.YPoint = YPoint;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String url) {
            Url = url;
        }
    }
}
