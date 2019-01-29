package com.lulian.Zaiyunbao.ui.photoview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.lulian.Zaiyunbao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 展示图片大图的Dialog
 */
public class PhotoDisplayDialog extends Dialog {

    @BindView(R.id.pv)
    PhotoView photoView;
    @BindView(R.id.btn_shoot_again)
    Button btnShootAgain;

    private String imgUrl;
    private Context mContext;
    private boolean mShowBtn;

    private Callback mCallback;

    private boolean shootAgainBtnVisible = true;//是否显示 "重新选择/拍摄"图片按钮

    public PhotoDisplayDialog(@NonNull Context context, boolean showBtn) {
        super(context, R.style.common_dialog);
        mContext = context;
        mShowBtn = showBtn;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public PhotoDisplayDialog setShootAgainBtnVisible(boolean shootAgainBtnVisible) {
        this.shootAgainBtnVisible = shootAgainBtnVisible;
        return this;
    }

    public PhotoDisplayDialog setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_show_photo);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this, this);

        if (mShowBtn) {
            btnShootAgain.setText("重新拍摄/选择照片");
        } else {
            btnShootAgain.setText("返回");
        }

        if (shootAgainBtnVisible) {
            btnShootAgain.setVisibility(View.VISIBLE);
        } else {
            btnShootAgain.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Glide.with(mContext).load(imgUrl).into(photoView);
    }

    @OnClick(R.id.btn_shoot_again)
    public void onViewClicked(View v) {
        dismiss();
        if (mCallback != null) {
            mCallback.onShootAgainClicked();
        }
    }

    @Override
    public void show() {
        super.show();

    }


    public interface Callback {
        void onShootAgainClicked();
    }
}
