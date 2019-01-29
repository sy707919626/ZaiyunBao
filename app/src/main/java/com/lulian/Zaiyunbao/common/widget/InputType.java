package com.lulian.Zaiyunbao.common.widget;

/**
 * Created by Administrator on 2015/12/9.
 */
public enum InputType {
    NONE(0), PHONE(1), BANK_CARD(2);
    int value;

    InputType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
