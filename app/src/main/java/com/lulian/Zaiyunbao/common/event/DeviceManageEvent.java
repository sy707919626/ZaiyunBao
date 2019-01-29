package com.lulian.Zaiyunbao.common.event;

/**
 * Created by Administrator on 2018/12/27.
 */

public class DeviceManageEvent {
    private String[] OrderID;
    private int Status;

    public DeviceManageEvent(String[] orderID, int status) {
        OrderID = orderID;
        Status = status;
    }

    public String[] getOrderID() {
        return OrderID;
    }

    public void setOrderID(String[] orderID) {
        OrderID = orderID;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
