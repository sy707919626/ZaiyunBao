package com.lulian.Zaiyunbao.ui.activity.subleaseorder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lulian.Zaiyunbao.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/26.
 */

public class SubleaseEntryOrderAdapter extends RecyclerView.Adapter<SubleaseEntryOrderAdapter.EntryViewHolder> {


    private Context mContext;
    private String leaseName;
    private String leaseNorm;

    private ArrayList<String> mOrderList = new ArrayList<>();

    public SubleaseEntryOrderAdapter(Context context, ArrayList<String> orderLists, String LeaseName, String LeaseNorm) {
        this.mContext = context;
        this.mOrderList = orderLists;
        this.leaseName = LeaseName;
        this.leaseNorm = LeaseNorm;
    }

    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sublease_lease, parent, false);
        EntryViewHolder viewHolder = new EntryViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EntryViewHolder holder, final int position) {
        holder.receiveLeaseName.setText(leaseName);
        holder.receiveLeaseNorm.setText(leaseNorm);
        holder.receiveLeaseOrderNo.setText(mOrderList.get(position));
    }

    @Override
    public int getItemCount() {
        return mOrderList == null ? 0 : mOrderList.size();
    }

    class EntryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.receive_lease_Name)
        TextView receiveLeaseName;
        @BindView(R.id.receive_lease_norm)
        TextView receiveLeaseNorm;
        @BindView(R.id.receive_lease_orderNo)
        TextView receiveLeaseOrderNo;

        public EntryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
