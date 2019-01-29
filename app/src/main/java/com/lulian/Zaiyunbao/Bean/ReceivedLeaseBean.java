package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/11/8.
 */

public class ReceivedLeaseBean implements Serializable {

    /**
     * total : 2
     * size : 10
     * index : 1
     * rows : [{"EquipmentName":"金属托盘04","Norm":"","Model":"","ECode":"0000000000000000"},{"EquipmentName":"金属托盘04","Norm":"","Model":"","ECode":"0000000000000000"}]
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
         * EquipmentName : 金属托盘04
         * Norm :
         * Model :
         * ECode : 0000000000000000
         */

        private String EquipmentName;
        private String Norm;
        private String Model;
        private String ECode;

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

        public String getModel() {
            return Model;
        }

        public void setModel(String Model) {
            this.Model = Model;
        }

        public String getECode() {
            return ECode;
        }

        public void setECode(String ECode) {
            this.ECode = ECode;
        }
    }
}
