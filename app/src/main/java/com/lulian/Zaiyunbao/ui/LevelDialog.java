package com.lulian.Zaiyunbao.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.widget.DownloadProgressButton;


/**
 * Created by Labor on 2018/5/14.
 */

public class LevelDialog extends Dialog implements View.OnClickListener{
    public OnLevelListener listener;
    private Context mContext;
    public TextView tipsTv;//更新内容提示
    public DownloadProgressButton downBtn;//下载按钮
    public LevelDialog(Context context, OnLevelListener listener) {
        super(context);
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levelup_layout);
        setCanceledOnTouchOutside(false);
        initView();
    }
    private void initView() {
        tipsTv = (TextView)findViewById(R.id.levelup_des_tv);
        downBtn = (DownloadProgressButton)findViewById(R.id.levelup_tv);
        downBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.levelup_tv://下载按钮
                    //下载操作
                    listener.onClick(this,false);
                    break;
            }
    }
    public interface OnLevelListener{
        void onClick(Dialog dialog, boolean confirm);
    }

    public void setTipText(String text){
        tipsTv.setText(text);
    }
}
