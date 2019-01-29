package com.lulian.Zaiyunbao.common.rx;

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
public class RxHttpResponseCompat {

    public static <T> ObservableTransformer<String, T> compatResult() {

        return new ObservableTransformer<String, T>() {
            @Override
            public ObservableSource<T> apply(Observable<String> baseBeanObservable) {
                return baseBeanObservable.flatMap(new Function<String, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(final String tBaseBean) throws Exception {

                        JSONObject jsonObject = new JSONObject(tBaseBean);
                        String state = jsonObject.getString("Code");

                        if (state.equals("0")) {
                            final String data = jsonObject.getString("Data");

                            return Observable.create(new ObservableOnSubscribe<T>() {
                                @Override
                                public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
                                    try {
                                        subscriber.onNext((T) data);
                                        subscriber.onComplete();

                                    } catch (Exception e) {
                                        subscriber.onError(e);
                                    }
                                }
                            });

                        } else {
                            String msg = jsonObject.getString("Message");
                            return Observable.error(new ApiException(Integer.valueOf(state), msg));
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }
        };
    }


}






