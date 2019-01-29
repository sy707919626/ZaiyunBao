package com.lulian.Zaiyunbao.ui.activity.repair;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lulian.Zaiyunbao.Bean.RepairItem;
import com.lulian.Zaiyunbao.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/26.
 */

public class RepairOrderDetailAdapter extends RecyclerView.Adapter<RepairOrderDetailAdapter.LeaseViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private ArrayList<RepairItem> mRepairItemList = new ArrayList<>();


    public RepairOrderDetailAdapter(Context context, ArrayList<RepairItem> orderListBeans) {
        this.mContext = context;
        this.mRepairItemList = orderListBeans;
    }

    @Override
    public LeaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_repair_order_detail, parent, false);
        LeaseViewHolder viewHolder = new LeaseViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LeaseViewHolder holder, final int position) {
        final RepairItem mOrderList = mRepairItemList.get(position);
        holder.repairOrderDetailName.setText(mOrderList.getETypeName());

        if (mOrderList.getRepairType() == 1) {
            holder.repairOrderDetailStatu.setText("磨损");
        } else if (mOrderList.getRepairType() == 2) {
            holder.repairOrderDetailStatu.setText("破裂");
        } else if (mOrderList.getRepairType() == 3) {
            holder.repairOrderDetailStatu.setText("损毁");
        }

        holder.repairOrderDetailNo.setText(mOrderList.getECode());
        holder.repairOrderDetailRemart.setText(mOrderList.getRemark());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClickListener(position, mRepairItemList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRepairItemList == null ? 0 : mRepairItemList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClickListener(int position, ArrayList<RepairItem> orderListBean);
    }

    class LeaseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.repair_order_detail_name)
        TextView repairOrderDetailName;
        @BindView(R.id.repair_order_detail_statu)
        TextView repairOrderDetailStatu;
        @BindView(R.id.repair_order_detail_no_text)
        TextView repairOrderDetailNoText;
        @BindView(R.id.repair_order_detail_no)
        TextView repairOrderDetailNo;
        @BindView(R.id.repair_order_detail_remart_text)
        TextView repairOrderDetailRemartText;
        @BindView(R.id.repair_order_detail_remart)
        TextView repairOrderDetailRemart;

        public LeaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }
}
