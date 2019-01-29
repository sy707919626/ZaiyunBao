package com.lulian.Zaiyunbao.common.exception;

/**
 * @description：
 * @author：bux on 2018/4/18 11:23
 * @email: 471025316@qq.com
 */
public class ApiException extends BaseException {

    public ApiException(int code, String displayMessage) {
        super(code, displayMessage);

    }
}
