package com.lulian.Zaiyunbao.common.rx.subscriber;

import io.reactivex.Observer;

/**
 * @description：
 * @author：bux on 2018/4/18 16:24
 * @email: 471025316@qq.com
 */
public abstract class DefaultSubscriber<T> implements Observer<T> {

    @Override
    public void onError(Throwable e) {

    }
}
