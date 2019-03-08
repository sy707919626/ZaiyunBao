package com.lulian.Zaiyunbao.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import com.lulian.Zaiyunbao.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/12/22.
 */

public class PhotoDialog extends Dialog {
    @BindView(R.id.photo_shili_imageview)
    ImageView photoShiliImageview;
    @BindView(R.id.photo_paizhao_button)
    Button photoPaizhaoButton;
    @BindView(R.id.photo_xiangce_button)
    Button photoXiangceButton;
    @BindView(R.id.photo_cancal_button)
    Button photoCancalButton;
    private Context mContext;

    private int mPhotoType;

    public onPhotoListener listener;

    public PhotoDialog(@NonNull Context context, int photoType, onPhotoListener listener) {
        super(context);
        this.mContext = context;
        this.mPhotoType = photoType;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_layout);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this, this);

        if (mPhotoType == 1) {
            photoShiliImageview.setImageResource(R.drawable.photo_zm_bg);
        } else if (mPhotoType == 2) {
            photoShiliImageview.setImageResource(R.drawable.photo_fm_bg);
        } else if (mPhotoType == 3) {
            photoShiliImageview.setImageResource(R.drawable.business_license);
        }


        photoPaizhaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick("1");
                dismiss();
            }
        });

        photoXiangceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick("2");
                dismiss();
            }
        });

        photoCancalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    public interface onPhotoListener{
        void onClick(String photoType);
    }
}
