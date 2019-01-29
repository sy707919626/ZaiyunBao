package com.lulian.Zaiyunbao.ui.activity.buyorder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lulian.Zaiyunbao.Bean.BuyOrderInfoBean;
import com.lulian.Zaiyunbao.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/26.
 */

public class BuyOrderInfoAdapter extends RecyclerView.Adapter<BuyOrderInfoAdapter.ReceivedLeaseHolder> {

    private OnItemClickListener mOnItemClickListener;

    private Context mContext;
    private ArrayList<BuyOrderInfoBean.RowsBean> mBuyOrderInfoBean;

    public BuyOrderInfoAdapter(Context context, ArrayList<BuyOrderInfoBean.RowsBean> buyOrderInfoBean) {
        this.mContext = context;
        this.mBuyOrderInfoBean = buyOrderInfoBean;
    }

    @Override
    public ReceivedLeaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sublease_lease, parent, false);
        ReceivedLeaseHolder viewHolder = new ReceivedLeaseHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReceivedLeaseHolder holder, final int position) {
        final BuyOrderInfoBean.RowsBean buyOrderInfo = mBuyOrderInfoBean.get(position);

        holder.receiveLeaseName.setText(buyOrderInfo.getETypeName());
        holder.receiveLeaseNorm.setText(buyOrderInfo.getNorm());
        holder.receiveLeaseOrderNo.setText(buyOrderInfo.getECode());
    }

    @Override
    public int getItemCount() {
        return mBuyOrderInfoBean == null ? 0 : mBuyOrderInfoBean.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, BuyOrderInfoBean.RowsBean buyOrderInfo);
    }

    class ReceivedLeaseHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.receive_lease_Name)
        TextView receiveLeaseName;
        @BindView(R.id.receive_lease_norm)
        TextView receiveLeaseNorm;
        @BindView(R.id.receive_lease_orderNo)
        TextView receiveLeaseOrderNo;

        public ReceivedLeaseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }

}
