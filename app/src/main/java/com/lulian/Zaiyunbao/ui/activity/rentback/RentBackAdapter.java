package com.lulian.Zaiyunbao.ui.activity.rentback;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lulian.Zaiyunbao.Bean.RentBackBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.data.http.ApiService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/26.
 */

public class RentBackAdapter extends RecyclerView.Adapter<RentBackAdapter.LeaseViewHolder> {

    private String state = "";
    private String stateBtn = "";
    private OnItemClickListener mOnItemClickListener;
    private ApiService mApi;
    private Context mContext;
    private ArrayList<RentBackBean.RowsBean> mOrderListBean = new ArrayList<>();

    public RentBackAdapter(Context context, ArrayList<RentBackBean.RowsBean> orderListBeans, ApiService mApi) {
        this.mContext = context;
        this.mOrderListBean = orderListBeans;
        this.mApi = mApi;
    }

    @Override
    public LeaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rent_back, parent, false);
        LeaseViewHolder viewHolder = new LeaseViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LeaseViewHolder holder, final int position) {
        final RentBackBean.RowsBean mOrderList = mOrderListBean.get(position);

        holder.rentBackOrderId.setText(mOrderList.getOrderNo());
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(mOrderList.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            holder.rentBackImgPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        // 新建 = 0,已接单 = 1,已发货 = 3,已收货 = 4, 已完成 = 5

        if (mOrderList.getStatus() == 0) {
            state = "未接单";
            holder.rentBackBtn.setVisibility(View.GONE);

        } else if (mOrderList.getStatus() == 1) {
            state = "待发货";
            stateBtn = "确认发货";
            holder.rentBackBtn.setVisibility(View.VISIBLE);

        } else if (mOrderList.getStatus() == 3) {
            state = "已发货";
            holder.rentBackBtn.setVisibility(View.GONE);

        } else if (mOrderList.getStatus() == 4) {
            state = "已收货";
            holder.rentBackBtn.setVisibility(View.GONE);

        } else if (mOrderList.getStatus() == 5) {
            state = "已完成";
            holder.rentBackBtn.setVisibility(View.GONE);

        }

        holder.rentBackState.setText(state);
        holder.rentBackBtn.setText(stateBtn);


        holder.rentBackShebeiName.setText(mOrderList.getEquipmentName());
        holder.rentBackShebeiSpec.setText(mOrderList.getNorm());
        holder.rentBackShebeiNum.setText(mOrderList.getCount() + "个");

        if (mOrderList.getTypeName().equals("托盘")) {
            holder.rentBackLoad.setText("静载" + String.valueOf(mOrderList.getStaticLoad()) + "T；动载"
                    + String.valueOf(mOrderList.getCarryingLoad()) + "T；架载" + String.valueOf(mOrderList.getOnLoad()) + "T");
        } else if (mOrderList.getTypeName().equals("保温箱")) {
            holder.rentBackLoad.setText("容积" + mOrderList.getVolume() + "升；保温时长"
                    + mOrderList.getWarmLong() + "小时");
        } else if (mOrderList.getTypeName().equals("周转篱")) {
            holder.rentBackLoad.setText("容积" + mOrderList.getVolume() + "升；载重"
                    + mOrderList.getSpecifiedLoad() + "公斤");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClickListener(position, mOrderListBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrderListBean == null ? 0 : mOrderListBean.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, ArrayList<RentBackBean.RowsBean> orderListBean);
    }

    class LeaseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_text)
        TextView orderText;
        @BindView(R.id.rent_back_orderId)
        TextView rentBackOrderId;
        @BindView(R.id.rent_back_state)
        TextView rentBackState;
        @BindView(R.id.rent_back_img_photo)
        ImageView rentBackImgPhoto;
        @BindView(R.id.rent_back_shebei_name)
        TextView rentBackShebeiName;
        @BindView(R.id.rent_back_shebei_spec)
        TextView rentBackShebeiSpec;
        @BindView(R.id.rent_back_load)
        TextView rentBackLoad;
        @BindView(R.id.rent_back_type_name)
        TextView rentBackTypeName;
        @BindView(R.id.rent_back_shebei_num)
        TextView rentBackShebeiNum;
        @BindView(R.id.rent_back_btn)
        Button rentBackBtn;

        public LeaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            rentBackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int status = mOrderListBean.get(getAdapterPosition()).getStatus();
                    if (status == 1) {
                        //发货
                        Intent intent = new Intent(mContext, RentBackEntryActivity.class);
                        intent.putExtra("EquipmentName", mOrderListBean.get(getAdapterPosition()).getEquipmentName());
                        intent.putExtra("EquipmentNorm", mOrderListBean.get(getAdapterPosition()).getNorm());
                        intent.putExtra("OrderId", mOrderListBean.get(getAdapterPosition()).getOrdersId());
                        intent.putExtra("Count", mOrderListBean.get(getAdapterPosition()).getCount());
                        intent.putExtra("Id", mOrderListBean.get(getAdapterPosition()).getId());
                        mContext.startActivity(intent);
                    }

                }
            });
        }

    }
}
