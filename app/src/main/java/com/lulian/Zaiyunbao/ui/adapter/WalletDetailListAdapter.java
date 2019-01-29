package com.lulian.Zaiyunbao.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lulian.Zaiyunbao.Bean.WalletDetail;
import com.lulian.Zaiyunbao.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/26.
 */

public class WalletDetailListAdapter extends RecyclerView.Adapter<WalletDetailListAdapter.MessageViewHolder> {


    private Context mContext;
    private ArrayList<WalletDetail.RowsBean> mWallBean;

    public WalletDetailListAdapter(Context context, ArrayList<WalletDetail.RowsBean> wallBean) {
        this.mContext = context;
        this.mWallBean = wallBean;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_wallet_detail, parent, false);
        MessageViewHolder viewHolder = new MessageViewHolder(view);

        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MessageViewHolder holder, final int position) {

        holder.tvTradSum.setText(mWallBean.get(position).getTradSum());
        holder.tvTime.setText(mWallBean.get(position).getTradTime());

        if (mWallBean.get(position).getCashOrPay() == 1) {
            holder.tvRecordType.setText("收入");
            holder.tvAmount.setTextColor(mContext.getResources().getColor(R.color.text_sum));
            holder.tvAmount.setText("+  " + mWallBean.get(position).getMonetary());

        } else {
            holder.tvRecordType.setText("支出");
            holder.tvAmount.setTextColor(mContext.getResources().getColor(R.color.text_bule));
            holder.tvAmount.setText("-  " + mWallBean.get(position).getMonetary());
        }

    }

    @Override
    public int getItemCount() {
        return mWallBean == null ? 0 : mWallBean.size();
    }


    class MessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_record_type)
        TextView tvRecordType;
        @BindView(R.id.tv_amount)
        TextView tvAmount;
        @BindView(R.id.tv_TradSum)
        TextView tvTradSum;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }
}
