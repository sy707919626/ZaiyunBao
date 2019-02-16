package com.lulian.Zaiyunbao.common.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.lulian.Zaiyunbao.Bean.SaleEntity;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.ui.dialog.SelectAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/1/18.
 */

public class SelectPopupWindow extends PopupWindow {
    private Context mContext;
    private List<String> mList;
    private String mTitle;

    private Callback mCallback;

    public SelectPopupWindow(Context context, List<String> mList, String title) {
        super(context);
        this.mContext = context;
        this.mList = mList;
        this.mTitle = title;
        initView();
    }


    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    private void initView() {
        //获取布局文件
        View mContentView = LayoutInflater.from(mContext).inflate(R.layout.pop_layout, null);
        setContentView(mContentView);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setOutsideTouchable(false);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        setBackgroundDrawable(dw);


        ListView listView = mContentView.findViewById(R.id.listview);
        LinearLayout layout = mContentView.findViewById(R.id.dialog_title_layout);
        View v_outside = mContentView.findViewById(R.id.v_outside);

        layout.setVisibility(View.GONE);
        v_outside.setVisibility(View.VISIBLE);


        List<SaleEntity> list = new ArrayList();

        for (String a : mList) {
            SaleEntity saleEntity = new SaleEntity();
            saleEntity.setTitle(a);

            if (!mTitle.equals("")) {
                if (a.equals(mTitle)) {
                    saleEntity.setChecked(true);
                } else {
                    saleEntity.setChecked(false);
                }
            } else {
                saleEntity.setChecked(false);
            }

            list.add(saleEntity);
        }

        final SelectAdapter adapter = new SelectAdapter(mContext, list);
        listView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, List<SaleEntity> saleEntity) {
                mCallback.onCallback(saleEntity.get(position).getTitle());
                dismiss();
            }
        });

        v_outside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public interface Callback {
        void onCallback(String title);
    }
}

