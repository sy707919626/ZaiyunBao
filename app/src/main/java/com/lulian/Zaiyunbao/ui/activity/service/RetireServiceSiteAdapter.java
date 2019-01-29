package com.lulian.Zaiyunbao.ui.activity.service;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lulian.Zaiyunbao.Bean.RetireServiceSiteBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.di.component.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/11/22.
 */

public class RetireServiceSiteAdapter extends RecyclerView.Adapter<RetireServiceSiteAdapter.ServiceSiteViewHolder> {

    private Context mContext;
    private RetireServiceSiteAdapter.OnItemClickListener mOnItemClickListener;

    private ArrayList<RetireServiceSiteBean.RowsBean> mRetireServiceSiteBean;

    public RetireServiceSiteAdapter(Context context, ArrayList<RetireServiceSiteBean.RowsBean> retireServiceSiteBean) {
        this.mContext = context;
        this.mRetireServiceSiteBean = retireServiceSiteBean;
    }

    @Override
    public ServiceSiteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_retire_service_site, parent, false);
        ServiceSiteViewHolder viewHolder = new ServiceSiteViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceSiteViewHolder holder, final int position) {
        final RetireServiceSiteBean.RowsBean mRetireServiceSite = mRetireServiceSiteBean.get(position);
        holder.retireServiceSiteName.setText(mRetireServiceSite.getName());
        holder.retireServiceSiteAddress.setText(mRetireServiceSite.getArea());
        holder.retireServiceSiteJuli.setText(mRetireServiceSite.getTouch() + "KM");
        Glide.with(mContext).load(Constants.BASE_URL + mRetireServiceSite.getUrl()).into(holder.retireServiceSiteImgPhoto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(position, mRetireServiceSite);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRetireServiceSiteBean == null ? 0 : mRetireServiceSiteBean.size();
    }

    public void setOnItemClickListener(RetireServiceSiteAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, RetireServiceSiteBean.RowsBean retireServiceSite);
    }

    class ServiceSiteViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.retire_service_site_img_photo)
        ImageView retireServiceSiteImgPhoto;
        @BindView(R.id.retire_service_site_name)
        TextView retireServiceSiteName;
        @BindView(R.id.retire_service_site_address)
        TextView retireServiceSiteAddress;
        @BindView(R.id.retire_service_site_juli)
        TextView retireServiceSiteJuli;
        @BindView(R.id.retire_service_site_liji_btn)
        Button retireServiceSiteLijiBtn;
        @BindView(R.id.retire_service_site_back_btn)
        Button retireServiceSiteBackBtn;

        public ServiceSiteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            retireServiceSiteLijiBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RetireServiceLeaseActivity.class);
                    intent.putExtra("StoreId", mRetireServiceSiteBean.get(getAdapterPosition()).getId()); //仓库ID
                    intent.putExtra("Name", mRetireServiceSiteBean.get(getAdapterPosition()).getName());//仓库名字
                    mContext.startActivity(intent);
                }
            });

            retireServiceSiteBackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RetireCreateActivity.class);
                    intent.putExtra("StoreId", mRetireServiceSiteBean.get(getAdapterPosition()).getId()); //仓库ID
                    intent.putExtra("Name", mRetireServiceSiteBean.get(getAdapterPosition()).getName());//仓库名字
                    intent.putExtra("Area", mRetireServiceSiteBean.get(getAdapterPosition()).getArea()); //仓库地址

                    mContext.startActivity(intent);
                }
            });

        }

    }
}
