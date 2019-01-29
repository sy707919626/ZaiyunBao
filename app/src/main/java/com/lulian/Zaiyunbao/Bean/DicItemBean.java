package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/21.
 */

public class DicItemBean implements Serializable {

    /**
     * DicTypeCode : DT001
     * ItemCode : 0
     * ItemName : 平台
     * ItemSort : 1
     */

    private String DicTypeCode;
    private String ItemCode;
    private String ItemName;
    private int ItemSort;

    public String getDicTypeCode() {
        return DicTypeCode;
    }

    public void setDicTypeCode(String DicTypeCode) {
        this.DicTypeCode = DicTypeCode;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String ItemCode) {
        this.ItemCode = ItemCode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    public int getItemSort() {
        return ItemSort;
    }

    public void setItemSort(int ItemSort) {
        this.ItemSort = ItemSort;
    }
}
