package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/7/27.
 */

public class BaseBean<T> implements Serializable {

    /**
     * Code : 0
     * Message : 支付详情成功！
     * Data : [{"CashOrPay":1,"TradSum":"收入：","Monetary":12,"TradTime":"2019-02-26 16:18:23","SerialNo":"RP2019022616182318231","accountNo":null,"CompanysName":"个人客户"}]
     */

    private int Code;
    private String Message;
    private T Data;

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
}
