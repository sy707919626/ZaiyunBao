package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/11/22.
 */

public class RetireServiceSiteBean implements Serializable {
    /**
     * total : 4
     * size : 10
     * index : 1
     * rows : [{"Id":"2CD2E805-BCCF-4671-A84D-626B61CD70F8","Number":"1","Name":"望月湖仓库","Area":"长沙市岳麓区","Touch":"2","Manager":"2","BelongMember":"f3d85ebb-e017-4863-9d79-6e4df3efd448","AreaX":null,"AreaY":null,"ImgUrl":null,"Remark":"2","CreateId":null,"CreateUser":null,"CreateTime":null,"UpdateId":null,"UpdateUser":null,"UpdateTime":null,"Deleted":null,"Url":"/upload/404.jpg"},{"Id":"CC6AD5CD-FF3E-471D-8595-43D738260564","Number":"1","Name":"望月湖仓库1","Area":"长沙市岳麓区1","Touch":"2","Manager":"2","BelongMember":"f3d85ebb-e017-4863-9d79-6e4df3efd448","AreaX":null,"AreaY":null,"ImgUrl":null,"Remark":"2","CreateId":null,"CreateUser":null,"CreateTime":null,"UpdateId":null,"UpdateUser":null,"UpdateTime":null,"Deleted":null,"Url":"/upload/404.jpg"},{"Id":"F8D0488B-B305-42FC-A66E-0EF74F2FC955","Number":"1","Name":"望月湖仓库2","Area":"长沙市岳麓区2","Touch":"2","Manager":"2","BelongMember":"f3d85ebb-e017-4863-9d79-6e4df3efd448","AreaX":null,"AreaY":null,"ImgUrl":null,"Remark":"2","CreateId":null,"CreateUser":null,"CreateTime":null,"UpdateId":null,"UpdateUser":null,"UpdateTime":null,"Deleted":null,"Url":"/upload/404.jpg"},{"Id":"23BE80DD-D0E0-432D-A620-D780225AB86C","Number":"1","Name":"望月湖仓库3","Area":"长沙市岳麓区3","Touch":"2","Manager":"2","BelongMember":"f3d85ebb-e017-4863-9d79-6e4df3efd448","AreaX":null,"AreaY":null,"ImgUrl":null,"Remark":"2","CreateId":null,"CreateUser":null,"CreateTime":null,"UpdateId":null,"UpdateUser":null,"UpdateTime":null,"Deleted":null,"Url":"/upload/404.jpg"}]
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

    public static class RowsBean {
        /**
         * Id : 2CD2E805-BCCF-4671-A84D-626B61CD70F8
         * Number : 1
         * Name : 望月湖仓库
         * Area : 长沙市岳麓区
         * Touch : 2
         * Manager : 2
         * BelongMember : f3d85ebb-e017-4863-9d79-6e4df3efd448
         * AreaX : null
         * AreaY : null
         * ImgUrl : null
         * Remark : 2
         * CreateId : null
         * CreateUser : null
         * CreateTime : null
         * UpdateId : null
         * UpdateUser : null
         * UpdateTime : null
         * Deleted : null
         * Url : /upload/404.jpg
         */

        private String Id; //仓库ID
        private String Number;
        private String Name;
        private String Area;
        private String Touch;
        private String Manager;
        private String BelongMember;
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
        private String Url;

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

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getNumber() {
            return Number;
        }

        public void setNumber(String Number) {
            this.Number = Number;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getArea() {
            return Area;
        }

        public void setArea(String Area) {
            this.Area = Area;
        }

        public String getTouch() {
            return Touch;
        }

        public void setTouch(String Touch) {
            this.Touch = Touch;
        }

        public String getManager() {
            return Manager;
        }

        public void setManager(String Manager) {
            this.Manager = Manager;
        }

        public String getBelongMember() {
            return BelongMember;
        }

        public void setBelongMember(String BelongMember) {
            this.BelongMember = BelongMember;
        }

        public Object getAreaX() {
            return AreaX;
        }

        public void setAreaX(String areaX) {
            AreaX = areaX;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }
    }
}
