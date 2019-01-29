package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/10/26.
 */

public class MessageListBean implements Serializable {
    /**
     * total : 4
     * size : 1
     * index : 1
     * rows : [{"Content":"x","CreateTime":"2018-09-29 17:44:02"}]
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
         * Content : x
         * CreateTime : 2018-09-29 17:44:02
         */

        private String Content;
        private String CreateTime;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean isSelect) {
            this.isSelect = isSelect;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}
