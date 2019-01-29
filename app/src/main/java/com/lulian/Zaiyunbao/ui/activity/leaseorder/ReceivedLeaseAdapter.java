package com.lulian.Zaiyunbao.ui.activity.leaseorder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lulian.Zaiyunbao.Bean.EquipmentListBean;
import com.lulian.Zaiyunbao.Bean.ReceivedLeaseBean;
import com.lulian.Zaiyunbao.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/26.
 */

public class ReceivedLeaseAdapter extends RecyclerView.Adapter<ReceivedLeaseAdapter.ReceivedLeaseHolder> {

    private OnItemClickListener mOnItemClickListener;

    private Context mContext;
    private ArrayList<ReceivedLeaseBean.RowsBean> mReceivedLeaseBean;

    public ReceivedLeaseAdapter(Context context, ArrayList<ReceivedLeaseBean.RowsBean> receivedLeaseBean) {
        this.mContext = context;
        this.mReceivedLeaseBean = receivedLeaseBean;
    }

    @Override
    public ReceivedLeaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sublease_lease, parent, false);
        ReceivedLeaseHolder viewHolder = new ReceivedLeaseHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReceivedLeaseHolder holder, final int position) {
        final ReceivedLeaseBean.RowsBean receivedLease = mReceivedLeaseBean.get(position);

        holder.receiveLeaseName.setText(receivedLease.getEquipmentName());
        holder.receiveLeaseNorm.setText(receivedLease.getNorm());
        holder.receiveLeaseOrderNo.setText(receivedLease.getECode());
    }

    @Override
    public int getItemCount() {
        return mReceivedLeaseBean == null ? 0 : mReceivedLeaseBean.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, EquipmentListBean.RowsBean equipmentList);
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
