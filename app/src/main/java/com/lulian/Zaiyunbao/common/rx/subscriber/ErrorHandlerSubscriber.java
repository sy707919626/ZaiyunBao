package com.lulian.Zaiyunbao.common.rx.subscriber;

import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.exception.BaseException;
import com.lulian.Zaiyunbao.common.exception.RxErrorHandler;
import com.lulian.Zaiyunbao.common.widget.LoginUtil;

import io.reactivex.disposables.Disposable;


/**
 * 网络错误预处理
 */
public abstract class ErrorHandlerSubscriber<T> extends DefaultSubscriber<T> {

    private static final String TAG = "ErrorHandlerSubscriber";
    private RxErrorHandler mRxErrorHandler;

    protected ErrorHandlerSubscriber() {
        mRxErrorHandler = new RxErrorHandler();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
        BaseException exception = mRxErrorHandler.handlerError(t);
        showErrorMsg(exception);

        if (exception.getDisplayMessage().contains("无授权，服务器已阻止访问！")) {
            if (GlobalParams.sToken.isEmpty()) {
                GlobalParams.setToken("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1Njc4NDY2ODYsInVzZXIiOnsiVXNlcklEIjoiMSIsIlVzZXJOYW1lIjoi6LaF57qn566h55CG5ZGYIn19.U4mnFIJpioXChcKIBD29MLx1UL-NfPiK6hM1XVrw2U8");
            }
        }

        onFail(exception);
        onAfter();
    }

    protected void showErrorMsg(BaseException exception) {
        mRxErrorHandler.showErrorMessage(exception);
    }

    protected void onFail(BaseException e) {

    }

    @Override
    public void onComplete() {
        onAfter();
    }

    protected void onAfter() {

    }


}
