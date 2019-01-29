package com.lulian.Zaiyunbao.common.rx.subscriber;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lulian.Zaiyunbao.R;


/**
 * @description：
 * @author：bux on 2018/4/19 15:47
 * @email: 471025316@qq.com
 */
public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 0;

    private MaterialDialog pDialog;
    private Context mContext;
    private boolean cancelable;
    private MaterialDialog.SingleButtonCallback mSingleButtonCallback;


    public ProgressDialogHandler(Context context) {
        this(context, false, null);
    }

    public ProgressDialogHandler(Context context, boolean cancelable, MaterialDialog.SingleButtonCallback onProgressCancelListener) {
        mContext = context;
        this.cancelable = cancelable;
        mSingleButtonCallback = onProgressCancelListener;
        initProgressDialog();
    }


    private void initProgressDialog() {

        if (pDialog == null) {

            MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext)
                    .content(R.string.loading)
                    .progress(true, 0);

            if (cancelable) {
                builder.positiveText(R.string.close_string)
                        .onPositive(mSingleButtonCallback);
            }

            pDialog = builder.build();
            pDialog.getWindow().setDimAmount(0f);
//            pDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_backgroup_transt);
            pDialog.setCanceledOnTouchOutside(false);
        }
    }

    public void showProgressDialog() {

        if (pDialog != null && !pDialog.isShowing()) {
            pDialog.show();

            WindowManager.LayoutParams layoutParams = pDialog.getWindow().getAttributes();

            layoutParams.gravity = Gravity.CENTER;
//            layoutParams.width= DensityUtil.dp2px(200);
//            layoutParams.height = DensityUtil.dp2px(70);
            pDialog.getWindow().setAttributes(layoutParams);
        }
    }

    public void dismissProgressDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                showProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
            default:
        }
    }


}
