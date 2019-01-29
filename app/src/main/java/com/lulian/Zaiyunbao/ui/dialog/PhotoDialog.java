package com.lulian.Zaiyunbao.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/12/22.
 */

public class PhotoDialog extends BaseActivity {
    @BindView(R.id.photo_shili_imageview)
    ImageView photoShiliImageview;
    @BindView(R.id.photo_paizhao_button)
    Button photoPaizhaoButton;
    @BindView(R.id.photo_xiangce_button)
    Button photoXiangceButton;
    @BindView(R.id.photo_cancal_button)
    Button photoCancalButton;

    private int photoType;

    @Override
    protected int setLayoutId() {
        return R.layout.photo_layout;
    }

    @SuppressLint("NewApi")
    @Override
    protected void init() {
        photoType = getIntent().getIntExtra("photoType", 0);

        if (photoType == 1) {
            photoShiliImageview.setImageResource(R.drawable.photo_zm_bg);
        } else if (photoType == 2) {
            photoShiliImageview.setImageResource(R.drawable.photo_fm_bg);
        } else {
            photoShiliImageview.setImageResource(R.drawable.business_license);
        }

        photoPaizhaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("data_return", "1");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        photoXiangceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("data_return", "2");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        photoCancalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
