package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/1/25.
 */

public class WalletDetail implements Serializable {
    /**
     * total : 25
     * size : 10
     * index : 1
     * rows : [{"Id":"00115e22-2794-4410-8ad6-0b3cd2cfae1d","CashOrPay":1,"TradSum":"手工入金","Monetary":2},{"Id":"049f0ecc-01f2-43a3-bbfb-0a10f0728358","CashOrPay":2,"TradSum":"手工出金","Monetary":33},{"Id":"08dcc2f0-0211-4b6b-b274-a0739ceb9e3b","CashOrPay":2,"TradSum":"手工出金","Monetary":1},{"Id":"0d6bfc5b-e874-42a0-b63f-ca730faa46af","CashOrPay":1,"TradSum":"手工入金","Monetary":1},{"Id":"2ab25a62-4801-41f6-af4e-e60e863c2a14","CashOrPay":2,"TradSum":"手工出金","Monetary":1},{"Id":"2fe39fcd-a34b-491f-98f7-c52b8e048fec","CashOrPay":2,"TradSum":"手工出金","Monetary":1},{"Id":"307e5b6d-3c8a-40e8-8ced-ccc127277f5f","CashOrPay":1,"TradSum":"手工入金","Monetary":5000},{"Id":"34cdd11a-e90b-4178-ad22-ffc9963797a0","CashOrPay":1,"TradSum":"手工入金","Monetary":1},{"Id":"4099144a-8aa6-4b87-91cb-1774d2b5ae5d","CashOrPay":2,"TradSum":"手工出金","Monetary":11},{"Id":"441c5980-a0ef-404a-8208-382d97f36bf2","CashOrPay":1,"TradSum":"手工入金","Monetary":100}]
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
         * Id : 00115e22-2794-4410-8ad6-0b3cd2cfae1d
         * CashOrPay : 1
         * TradSum : 手工入金
         * Monetary : 2
         */

        private String Id;
        private int CashOrPay;
        private String TradSum;
        private int Monetary;
        private String TradTime;

        public String getTradTime() {
            return TradTime;
        }

        public void setTradTime(String tradTime) {
            TradTime = tradTime;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public int getCashOrPay() {
            return CashOrPay;
        }

        public void setCashOrPay(int CashOrPay) {
            this.CashOrPay = CashOrPay;
        }

        public String getTradSum() {
            return TradSum;
        }

        public void setTradSum(String TradSum) {
            this.TradSum = TradSum;
        }

        public int getMonetary() {
            return Monetary;
        }

        public void setMonetary(int Monetary) {
            this.Monetary = Monetary;
        }
    }

    //Id(Id)CashOrPay(1收入2支出)TradSum(交易说明)Monetary(金额)


}
