package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/11/23.
 */

public class DeviceManageBean implements Serializable {

    /**
     * total : 11
     * size : 10
     * index : 1
     * rows :
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
        public boolean isSelect;
        /**
         * Id : 048e1bd9-268e-4f50-a763-b41799ba854a
         * EquipmentBaseNo : EQ201809140201
         * EquipmentName : 金属托盘04
         * Norm :
         * CirculationState : 0
         * UseStatus : 1
         * Picture :
         */

        private String Id; //设备Id
        private String ECode; //设备唯一编码
        private String EquipmentName; //设备号
        private String Norm; //规格
        private int CirculationState; //流转状态0=在库 1=在途
        private int UseStatus; //使用状态1=闲置 2=载物 3=报修 4= 报废
        private String Picture;

        public String getECode() {
            return ECode;
        }

        public void setECode(String ECode) {
            this.ECode = ECode;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
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
}
