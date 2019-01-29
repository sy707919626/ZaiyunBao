package com.lulian.Zaiyunbao.ui.activity.repair;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lulian.Zaiyunbao.Bean.DeteilListBean;
import com.lulian.Zaiyunbao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/12/10.
 */

public class RepairReportAdapter extends RecyclerView.Adapter<RepairReportAdapter.RepairReportViewHolder> {
    private Context mContext;
    private List<DeteilListBean> mDeteilListBean = new ArrayList<>();


    public RepairReportAdapter(Context context, List<DeteilListBean> deteilListBean) {
        this.mContext = context;
        this.mDeteilListBean = deteilListBean;
    }

    @Override
    public RepairReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_repair_report, parent, false);
        RepairReportViewHolder viewHolder = new RepairReportViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RepairReportViewHolder holder, int position) {
        holder.repairReportName.setText(mDeteilListBean.get(position).getETypeName());
        holder.repairReportOrderNo.setText(mDeteilListBean.get(position).getECode());

        if (mDeteilListBean.get(position).getRepairType() == 1) {
            holder.repairReportType.setText("磨损");
        } else if (mDeteilListBean.get(position).getRepairType() == 2) {
            holder.repairReportType.setText("破裂");
        } else if (mDeteilListBean.get(position).getRepairType() == 3) {
            holder.repairReportType.setText("损毁");
        }
    }

    @Override
    public int getItemCount() {
        return mDeteilListBean == null ? 0 : mDeteilListBean.size();
    }

    class RepairReportViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.repair_report_Name)
        TextView repairReportName;
        @BindView(R.id.repair_report_type)
        TextView repairReportType;
        @BindView(R.id.repair_report_orderNo)
        TextView repairReportOrderNo;
        @BindView(R.id.repair_report_delete_btn)
        Button repairReportDeleteBtn;

        public RepairReportViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            repairReportDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mDeteilListBean.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());//刷新被删除的地方
                    notifyItemRangeChanged(getAdapterPosition(), getItemCount()); //刷新被删除数据，以及其后面的数据

                }
            });
        }

    }
}
