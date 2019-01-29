package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

public class EquipmentTypeBean implements Serializable {
    /**
     * Id : 07DB0D77-CC45-49B3-BF6C-754DA0DF1D61
     * TypeName : 托盘
     */
    private String Id;
    private String TypeName;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }
}
