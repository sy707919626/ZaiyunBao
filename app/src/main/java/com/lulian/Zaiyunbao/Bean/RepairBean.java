package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/1/23.
 */

public class RepairBean implements Serializable {
    /**
     * total : 10
     * size : 10
     * index : 1
     * rows : [{"FormNo":"20190103174516009","CreateTime":"2019-01-03 17:45:16","Status":null},{"FormNo":"20190103174514993","CreateTime":"2019-01-03 17:45:14","Status":null},{"FormNo":"20190103174517868","CreateTime":"2019-01-03 17:45:17","Status":null},{"FormNo":"20190103174518071","CreateTime":"2019-01-03 17:45:18","Status":null},{"FormNo":"20190103174516415","CreateTime":"2019-01-03 17:45:16","Status":null},{"FormNo":"20181211112425466","CreateTime":"2018-12-11 11:24:25","Status":null},{"FormNo":"20190103174516196","CreateTime":"2019-01-03 17:45:16","Status":null},{"FormNo":"20190103174517650","CreateTime":"2019-01-03 17:45:17","Status":null},{"FormNo":"20190123165453697","CreateTime":"2019-01-23 16:54:53","Status":null},{"FormNo":"20190103174513259","CreateTime":"2019-01-03 17:45:13","Status":null}]
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
         * FormNo : 20190103174516009
         * CreateTime : 2019-01-03 17:45:16
         * Status : null
         */
        private String Id;
        private String FormNo;
        private String CreateTime;
        private int Status;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getFormNo() {
            return FormNo;
        }

        public void setFormNo(String FormNo) {
            this.FormNo = FormNo;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }
    }
}
