package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/10.
 */

public class SaleEntity implements Serializable {

    private String title;
    private boolean checked = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
