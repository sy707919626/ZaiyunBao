package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/12/4.
 */

public class BuyOrderInfoBean implements Serializable {

    /**
     * total : 1
     * size : 10
     * index : 1
     * rows : [{"ETypeName":"木质托盘234","ECode":"0000181128225","Price":1,"Norm":"3XS","Model":"X00001"}]
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
         * ETypeName : 木质托盘234
         * ECode : 0000181128225
         * Price : 1
         * Norm : 3XS
         * Model : X00001
         */

        private String ETypeName;
        private String ECode;
        private float Price;
        private String Norm;
        private String Model;

        public String getETypeName() {
            return ETypeName;
        }

        public void setETypeName(String ETypeName) {
            this.ETypeName = ETypeName;
        }

        public String getECode() {
            return ECode;
        }

        public void setECode(String ECode) {
            this.ECode = ECode;
        }

        public float getPrice() {
            return Price;
        }

        public void setPrice(float Price) {
            this.Price = Price;
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
    }
}
