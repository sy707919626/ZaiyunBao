package com.lulian.Zaiyunbao.common.exception;

/**
 * 业务接口返回的错误信息
 */
public class BaseException extends Exception {
    private int code;
    private String displayMessage;

    public BaseException(int code, String displayMessage) {
        super(displayMessage);
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public BaseException() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
