package com.lulian.Zaiyunbao.ui.activity.buyorder;

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

import com.bumptech.glide.Glide;
import com.lulian.Zaiyunbao.Bean.BuyOrderListBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.ui.activity.pay.PayActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/26.
 */

public class BuyOrderAdapter extends RecyclerView.Adapter<BuyOrderAdapter.BuyViewHolder> {

    private String state = "";
    private OnItemClickListener mOnItemClickListener;

    private Context mContext;
    private ArrayList<BuyOrderListBean.RowsBean> mOrderListBean = new ArrayList<>();

    public BuyOrderAdapter(Context context, ArrayList<BuyOrderListBean.RowsBean> orderListBeans) {
        this.mContext = context;
        this.mOrderListBean = orderListBeans;
    }

    @Override
    public BuyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_buyorder, parent, false);
        BuyViewHolder viewHolder = new BuyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BuyViewHolder holder, final int position) {
        final BuyOrderListBean.RowsBean mOrderList = mOrderListBean.get(position);

        holder.buyOrderId.setText(mOrderList.getOrderNo());
//        try {
//            byte[] bitmapArray;
//            bitmapArray = Base64.decode(mOrderList.getPicture(), Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
//                    bitmapArray.length);
//            holder.buyImgPhoto.setImageBitmap(bitmap);
//        } catch (Exception e) {
//        }

        Glide.with(mContext).load(Constants.BASE_URL +"/" +  mOrderList.getPicture()).into(holder.buyImgPhoto);

        if (mOrderList.getOrderStatus() == 2) {
            holder.buyOrderState.setBackgroundResource(R.drawable.order_background);
        } else {
            holder.buyOrderState.setBackgroundResource(R.drawable.status_bj);
        }

        //未接单 = 0, 已接单 = 1,订单确认待支付 = 2,
        // 已支付待发货 = 3,已发货 = 5,已收货 = 6,退租中 = 7,已退租 = 8,已结算 = 9,已撤销 = 10
        if (mOrderList.getOrderStatus() == 0) {
            state = "未接单";
            holder.orderStateBtn.setVisibility(View.GONE);

        } else if (mOrderList.getOrderStatus() == 1) {
            state = "待确认";
            holder.orderStateBtn.setVisibility(View.GONE);

        } else if (mOrderList.getOrderStatus() == 2) {
            state = "待支付";
            holder.orderStateBtn.setVisibility(View.VISIBLE);
            holder.orderStateBtn.setText("确认支付");

        } else if (mOrderList.getOrderStatus() == 3) {
            state = "待发货";
            holder.orderStateBtn.setVisibility(View.GONE);

        } else if (mOrderList.getOrderStatus() == 5) {
            state = "已发货";
            holder.orderStateBtn.setVisibility(View.VISIBLE);
            holder.orderStateBtn.setText("确认收货");

        } else if (mOrderList.getOrderStatus() == 6) {
            state = "已收货";
            holder.orderStateBtn.setVisibility(View.GONE);

        } else if (mOrderList.getOrderStatus() == 7) {
            state = "已完成";
            holder.orderStateBtn.setVisibility(View.GONE);

        } else if (mOrderList.getOrderStatus() == 8) {
            state = "已撤回";
            holder.orderStateBtn.setVisibility(View.GONE);
        }
        holder.buyOrderState.setText(state);

        holder.buyShebeiName.setText(mOrderList.getEquipmentName());
        holder.buyShebeiSpec.setText(mOrderList.getNorm());

        holder.buyRentSum.setText(mOrderList.getCount() + "");

        if (mOrderList.getTypeName().equals("保温箱")) {
            holder.buyLeaseLoad.setText("容积" + String.valueOf(mOrderList.getVolume()) + "升T；保温时长"
                    + String.valueOf(mOrderList.getWarmLong()) + "小时");
        } else if (mOrderList.getTypeName().equals("托盘")) {
            holder.buyLeaseLoad.setText("静载" + String.valueOf(mOrderList.getStaticLoad()) + "T；动载"
                    + String.valueOf(mOrderList.getCarryingLoad()) + "T；架载" + String.valueOf(mOrderList.getOnLoad()) + "T");
        } else if (mOrderList.getTypeName().equals("周转篱")) {
            holder.buyLeaseLoad.setText("容积" + String.valueOf(mOrderList.getVolume()) + "升T；载重"
                    + String.valueOf(mOrderList.getSpecifiedLoad()) + "公斤");
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
        void onItemClickListener(int position, ArrayList<BuyOrderListBean.RowsBean> orderListBean);
    }

    class BuyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_text)
        TextView orderText;
        @BindView(R.id.buy_orderId)
        TextView buyOrderId;
        @BindView(R.id.buy_order_state)
        TextView buyOrderState;
        @BindView(R.id.buy_img_photo)
        ImageView buyImgPhoto;
        @BindView(R.id.buy_shebei_name)
        TextView buyShebeiName;
        @BindView(R.id.buy_shebei_spec)
        TextView buyShebeiSpec;
        @BindView(R.id.buy_lease_load)
        TextView buyLeaseLoad;
        @BindView(R.id.buy_rent_sum)
        TextView buyRentSum;
        @BindView(R.id.order_state_btn)
        Button orderStateBtn;

        public BuyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            orderStateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOrderListBean.get(getAdapterPosition()).getOrderStatus() == 2) {

                        //待支付
                        Intent intent = new Intent(mContext, PayActivity.class);
                        intent.putExtra("orderId", mOrderListBean.get(getAdapterPosition()).getOrdersId());
                        intent.putExtra("UserId", GlobalParams.sUserId);

                        if (mOrderListBean.get(getAdapterPosition()).getAdvance() == 0) { //订金
                            intent.putExtra("money", mOrderListBean.get(getAdapterPosition()).getAmount());
                        } else {
                            intent.putExtra("money", Float.valueOf(mOrderListBean.get(getAdapterPosition()).getAdvance()));
                        }

                        intent.putExtra("OrderType", 2); //1租赁单2购买单
                        mContext.startActivity(intent);
                    }

                    if (mOrderListBean.get(getAdapterPosition()).getOrderStatus() == 5) {
                        //确认收货
                        Intent intent = new Intent(mContext, BuyOrderInfoActivity.class);
                        intent.putExtra("OrdersId", mOrderListBean.get(getAdapterPosition()).getOrdersId());
                        mContext.startActivity(intent);
                    }
                }
            });
        }

    }

}
