package com.lulian.Zaiyunbao.common.rx.subscriber;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.reactivestreams.Subscription;

import io.reactivex.disposables.Disposable;

/**
 */
public abstract class ProgressDialogSubscriber<T> extends ErrorHandlerSubscriber<T> implements MaterialDialog.SingleButtonCallback {

    private ProgressDialogHandler mProgressDialog;
    private boolean isShow = true;

    private Subscription mSubscription;


    public ProgressDialogSubscriber(Context context, boolean isShow, boolean isShowCancel) {
        this.isShow = isShow;
        if (isShow) {
            mProgressDialog = new ProgressDialogHandler(context, isShowCancel, this);
        }
    }

    protected ProgressDialogSubscriber(Context context) {
        this(context, true, false);
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (isShow) {
            mProgressDialog.showProgressDialog();
        }
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
        super.onError(t);
        dismissDialog();
    }

    @Override
    public void onComplete() {
        super.onComplete();
        dismissDialog();
    }

    private void dismissDialog() {
        if (isShow) {
            mProgressDialog.dismissProgressDialog();
        }
    }


    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        if (mSubscription != null) {
            mSubscription.cancel();
        }
    }
}
