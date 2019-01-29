package com.lulian.Zaiyunbao.ui.activity.repair;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lulian.Zaiyunbao.Bean.RepairBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.data.http.ApiService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/26.
 */

public class RepairOrderAdapter extends RecyclerView.Adapter<RepairOrderAdapter.LeaseViewHolder> {


    private OnItemClickListener mOnItemClickListener;
    private ApiService mApi;
    private Context mContext;
    private ArrayList<RepairBean.RowsBean> mRepairBeanList = new ArrayList<>();


    public RepairOrderAdapter(Context context, ArrayList<RepairBean.RowsBean> orderListBeans) {
        this.mContext = context;
        this.mRepairBeanList = orderListBeans;
    }

    @Override
    public LeaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_repair_order, parent, false);
        LeaseViewHolder viewHolder = new LeaseViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LeaseViewHolder holder, final int position) {
        final RepairBean.RowsBean mOrderList = mRepairBeanList.get(position);
        holder.repairOrderNo.setText(mOrderList.getFormNo());

        if (mOrderList.getStatus() == 1) {
            holder.repairOrderStatu.setText("处理中");
        } else if (mOrderList.getStatus() == 2) {
            holder.repairOrderStatu.setText("已完成");
        }

        holder.repairOrderTime.setText(mOrderList.getCreateTime());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClickListener(position, mRepairBeanList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRepairBeanList == null ? 0 : mRepairBeanList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClickListener(int position, ArrayList<RepairBean.RowsBean> orderListBean);
    }

    class LeaseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.repair_order_no)
        TextView repairOrderNo;
        @BindView(R.id.repair_report_Name_layout)
        LinearLayout repairReportNameLayout;
        @BindView(R.id.repair_order_statu)
        TextView repairOrderStatu;
        @BindView(R.id.repair_order_time_text)
        TextView repairOrderTimeText;
        @BindView(R.id.repair_order_time)
        TextView repairOrderTime;
        @BindView(R.id.repair_order_remart_text)
        TextView repairOrderRemartText;
        @BindView(R.id.repair_order_remart)
        TextView repairOrderRemart;

        public LeaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }
}
