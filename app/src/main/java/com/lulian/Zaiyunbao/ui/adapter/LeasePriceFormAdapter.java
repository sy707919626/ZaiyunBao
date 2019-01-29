package com.lulian.Zaiyunbao.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lulian.Zaiyunbao.Bean.LeasePriceFromBean;
import com.lulian.Zaiyunbao.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/11/2.
 */

public class LeasePriceFormAdapter extends RecyclerView.Adapter {

    private final static int HEAD_COUNT = 1;
    private final static int TYPE_HEAD = 0;
    private final static int TYPE_CONTENT = 1;


    private Context mContext;
    private List<LeasePriceFromBean> mLeasePriceFromBean = new ArrayList<>();

    public LeasePriceFormAdapter(Context context, List<LeasePriceFromBean> leasePriceFromBeans) {
        this.mContext = context;
        this.mLeasePriceFromBean = leasePriceFromBeans;
    }

    @Override
    public int getItemViewType(int position) {
        if (HEAD_COUNT != 0 && position == 0) { // 头部
            return TYPE_HEAD;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.lease_priceform_head, parent, false);
            return new HeadHolder(itemView);

        } else {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.lease_priceform_content, parent, false);
            return new ContentHolder(itemView);
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadHolder) { // 头部

        } else if (holder instanceof ContentHolder) { // 内容
            ((ContentHolder) holder).EName.setText(mLeasePriceFromBean.get(position).getEName());
            ((ContentHolder) holder).FreeDays.setText(String.valueOf(mLeasePriceFromBean.get(position).getFreeDays()));
            ((ContentHolder) holder).Rent.setText(String.valueOf(mLeasePriceFromBean.get(position).getPrice()));

            ((ContentHolder) holder).TopLimit.setText(String.valueOf(mLeasePriceFromBean.get(position).getDowLimit() + " ≤ 天数 ≤" +
                    String.valueOf(mLeasePriceFromBean.get(position).getTopLimit())));
        }
    }

    @Override
    public int getItemCount() {
        return mLeasePriceFromBean.size();
    }

    // 头部
    class HeadHolder extends RecyclerView.ViewHolder {
        public HeadHolder(View itemView) {
            super(itemView);
        }
    }

    // 内容
    class ContentHolder extends RecyclerView.ViewHolder {
        private TextView EName;
        private TextView TopLimit;
        private TextView Rent;
        private TextView FreeDays;

        public ContentHolder(View itemView) {
            super(itemView);
            EName = itemView.findViewById(R.id.EName);
            TopLimit = itemView.findViewById(R.id.TopLimit);
            Rent = itemView.findViewById(R.id.Rent);
            FreeDays = itemView.findViewById(R.id.FreeDays);
        }
    }

}
