package com.lulian.Zaiyunbao.common.rx;

import com.lulian.Zaiyunbao.Bean.BaseBean;
import com.lulian.Zaiyunbao.common.exception.ApiException;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @description:统一封装结果预处理
 */
public class RxHttpResponseCompatTest {

    public static <T> ObservableTransformer<BaseBean<T>, T> compatResult() {
        return new ObservableTransformer<BaseBean<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseBean<T>> upstream) {
                return upstream.flatMap(new Function<BaseBean<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(final BaseBean<T> baseBean) throws Exception {

                        if (baseBean.getCode()== 0 ) {
                            return Observable.create(new ObservableOnSubscribe<T>() {
                                @Override
                                public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
                                    try {
                                        subscriber.onNext(baseBean.getData());
                                        subscriber.onComplete();

                                    } catch (Exception e) {
                                        subscriber.onError(e);
                                    }
                                }
                            });
                        } else {
                            String msg = baseBean.getMessage();
                            return Observable.error(new ApiException(Integer.valueOf(baseBean.getCode()), msg));
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }
        };
    }
}








