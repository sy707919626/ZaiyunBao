package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/21.
 */

public class EquimentTypeBean implements Serializable {
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
