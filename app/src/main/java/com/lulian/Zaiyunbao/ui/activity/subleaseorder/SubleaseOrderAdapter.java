package com.lulian.Zaiyunbao.ui.activity.subleaseorder;

import android.app.AlertDialog;
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

import com.lulian.Zaiyunbao.Bean.MyOrderLisetBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.data.http.ApiService;
import com.lulian.Zaiyunbao.ui.activity.ReserveRetireDetailsActivity;
import com.lulian.Zaiyunbao.ui.activity.leaseorder.ReceiveLeaseInfoActivity;
import com.lulian.Zaiyunbao.ui.activity.pay.PayActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/26.
 */

public class SubleaseOrderAdapter extends RecyclerView.Adapter<SubleaseOrderAdapter.EquipmentViewHolder> {

    private String state = "";
    private String stateBtn = "";
    private ApiService mApi;
    private boolean isAllFragment = false;
    private OnItemClickListener mOnItemClickListener;

    private Context mContext;
    private ArrayList<MyOrderLisetBean.RowsBean> mOrderListBean = new ArrayList<>();

    public SubleaseOrderAdapter(Context context, ArrayList<MyOrderLisetBean.RowsBean> orderListBeans, ApiService mApi, boolean isAll) {
        this.mContext = context;
        this.mOrderListBean = orderListBeans;
        this.mApi = mApi;
        this.isAllFragment = isAll;
    }

    @Override
    public EquipmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_subleaseorder, parent, false);
        EquipmentViewHolder viewHolder = new EquipmentViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EquipmentViewHolder holder, final int position) {
        final MyOrderLisetBean.RowsBean mOrderList = mOrderListBean.get(position);

        holder.myOrderId.setText(mOrderList.getOrderNo());
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(mOrderList.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            holder.leaseImgPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        if (mOrderList.getStatus() == 2) {
            holder.orderState.setBackgroundResource(R.drawable.order_background);
        } else {
            holder.orderState.setBackgroundResource(R.drawable.status_bj);
        }

        //未接单 = 0, 已接单 = 1,订单确认待支付 = 2,
        // 已支付待发货 = 3,已发货 = 5,已收货 = 6,退租中 = 7,已退租 = 8,已结算 = 9,已撤销 = 10
        if (mOrderList.getStatus() == 1) {
            if (mOrderList.getIsRendIn() == 0) { //用户是否为租入方(IsRendIn 1是0否)
                state = "待确认";
                stateBtn = "确认接单";
                holder.orderStateBtn.setVisibility(View.VISIBLE);
            } else {
                state = "待确认";
                holder.orderStateBtn.setVisibility(View.GONE);
            }

        } else if (mOrderList.getStatus() == 2) {
            if (mOrderList.getIsRendIn() == 1) { //用户是否为租入方(IsRendIn 1是0否)
                state = "待支付";
                stateBtn = "支付押金";
                holder.orderStateBtn.setVisibility(View.VISIBLE);
            } else {
                state = "待支付";
                holder.orderStateBtn.setVisibility(View.GONE);
            }

        } else if (mOrderList.getStatus() == 3) {
            if (mOrderList.getIsRendIn() == 0) { //用户是否为租入方(IsRendIn 1是0否)
                state = "待发货";
                stateBtn = "确认发货";
                holder.orderStateBtn.setVisibility(View.VISIBLE);
            } else {
                state = "待发货";
                holder.orderStateBtn.setVisibility(View.GONE);
            }

        } else if (mOrderList.getStatus() == 5) {
            if (mOrderList.getIsRendIn() == 1) { //用户是否为租入方(IsRendIn 1是0否)
                state = "已发货";
                stateBtn = "确认收货";
                holder.orderStateBtn.setVisibility(View.VISIBLE);
            } else {
                state = "已发货";
                holder.orderStateBtn.setVisibility(View.GONE);
            }

        } else if (mOrderList.getStatus() == 6) {
            if (mOrderList.getIsRendIn() == 1) { //用户是否为租入方(IsRendIn 1是0否)
                state = "已收货";
                stateBtn = "我要退租";
                holder.orderStateBtn.setVisibility(View.VISIBLE);
            } else {
                state = "已收货";
                holder.orderStateBtn.setVisibility(View.GONE);
            }

        } else if (mOrderList.getStatus() == 7) {
            state = "退租中";
            holder.orderStateBtn.setVisibility(View.GONE);

        } else if (mOrderList.getStatus() == 8) {
            state = "已退租";
            holder.orderStateBtn.setVisibility(View.GONE);

        } else if (mOrderList.getStatus() == 9) {
            state = "已结算";
            holder.orderStateBtn.setVisibility(View.GONE);

        } else if (mOrderList.getStatus() == 10) {
            state = "已撤销";
            holder.orderStateBtn.setVisibility(View.GONE);
        }

        holder.orderState.setText(state);
        holder.orderStateBtn.setText(stateBtn);

        holder.subleaseLeasePrice.setText(mOrderList.getPrice() + "元");

        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(mOrderList.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            holder.leaseImgPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        holder.subleaseShebeiName.setText(mOrderList.getEquipmentName());
        holder.subleaseShebeiSpec.setText(mOrderList.getNorm());

        holder.subleaseRentSum.setText(mOrderList.getCount() + "个");

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

    private void showDialog(String message, final int status, final SubleaseOrderAdapter.EquipmentViewHolder equipmentViewHolder, final int position) {
        final AlertDialog builder = new AlertDialog.Builder(mContext)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = builder.findViewById(R.id.tv_msg);
        Button cancle = builder.findViewById(R.id.btn_cancle);
        Button sure = builder.findViewById(R.id.btn_sure);

        if (msg == null || cancle == null || sure == null) return;

        msg.setText(message);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == 1) {
                    getData(equipmentViewHolder, mOrderListBean.get(position).getOrdersId(), 2, position);

                } else if (status == 3) {
                    //待发货
                    Intent intent = new Intent(mContext, SubleaseOrderEntryActivity.class);
                    intent.putExtra("EquipmentName", mOrderListBean.get(position).getEquipmentName());
                    intent.putExtra("EquipmentNorm", mOrderListBean.get(position).getNorm());
                    intent.putExtra("OrderId", mOrderListBean.get(position).getOrdersId());
                    intent.putExtra("Count", mOrderListBean.get(position).getCount());
                    intent.putExtra("EquipmentId", mOrderListBean.get(position).getId());

                    intent.putExtra("AdapterPage", "SubleaseOrderAdapter");

                    mContext.startActivity(intent);

                } else if (status == 5) {
                    //待收货
                    Intent intent = new Intent(mContext, ReceiveLeaseInfoActivity.class);
                    intent.putExtra("OrdersId", mOrderListBean.get(position).getOrdersId());

                    intent.putExtra("AdapterPage", "SubleaseOrderAdapter");
                    mContext.startActivity(intent);

                } else if (status == 6) {
                    //退租中
                    Intent intentReserve = new Intent(mContext, ReserveRetireDetailsActivity.class);
                    intentReserve.putExtra("OrdersId", mOrderListBean.get(position).getOrdersId());
                    intentReserve.putExtra("OrderNo", mOrderListBean.get(position).getOrderNo());
                    intentReserve.putExtra("Id", mOrderListBean.get(position).getId());

                    intentReserve.putExtra("AdapterPage", "SubleaseOrderAdapter");
                    mContext.startActivity(intentReserve);
                }
                builder.dismiss();
            }
        });
    }

    private void getData(final SubleaseOrderAdapter.EquipmentViewHolder holder, String orderId, final int status, final int position) {
        mApi.updateOrderStatus(GlobalParams.sToken, 1, orderId, status)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        RxToast.success("订单状态修改成功");
                        if (status == 2) {
                            holder.orderStateBtn.setText("支付押金");
                            holder.orderState.setText("待支付");
                        }

                        if (!isAllFragment) {
                            mOrderListBean.remove(position);
                            notifyItemRemoved(position);//刷新被删除的地方
                            notifyItemRangeChanged(position, getItemCount()); //刷新被删除数据，以及其后面的数据
                        }
                    }
                });
    }


    public interface OnItemClickListener {
        void onItemClickListener(int position, ArrayList<MyOrderLisetBean.RowsBean> orderListBean);
    }

    class EquipmentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_text)
        TextView orderText;
        @BindView(R.id.my_orderId)
        TextView myOrderId;
        @BindView(R.id.order_state)
        TextView orderState;
        @BindView(R.id.lease_img_photo)
        ImageView leaseImgPhoto;
        @BindView(R.id.sublease_shebei_name)
        TextView subleaseShebeiName;
        @BindView(R.id.sublease_shebei_spec)
        TextView subleaseShebeiSpec;
        @BindView(R.id.sublease_Lease_price)
        TextView subleaseLeasePrice;
        @BindView(R.id.sublease_Rent_sum)
        TextView subleaseRentSum;
        @BindView(R.id.order_state_btn)
        Button orderStateBtn;

        public EquipmentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            orderStateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //未接单 = 0, 已接单 = 1,订单确认待支付 = 2,
                    // 已支付待发货 = 3,已发货 = 5,已收货 = 6,退租中 = 7,已退租 = 8,已结算 = 9,已撤销 = 10
                    int status = mOrderListBean.get(getAdapterPosition()).getStatus();
                    if (status == 1) {
                        showDialog("确认是否接单", status, SubleaseOrderAdapter.EquipmentViewHolder.this, getAdapterPosition());

                    } else if (status == 2) {
                        //支付押金
                        Intent intent = new Intent(mContext, PayActivity.class);
                        intent.putExtra("orderId", mOrderListBean.get(getAdapterPosition()).getOrdersId());
                        intent.putExtra("UserId", GlobalParams.sUserId);
                        intent.putExtra("money", mOrderListBean.get(getAdapterPosition()).getOrderDeposit());
                        intent.putExtra("OrderType", 1); //1租赁单2购买单
                        mContext.startActivity(intent);

                    } else if (status == 3) {
                        //待发货
                        showDialog("确认是否发货", status, SubleaseOrderAdapter.EquipmentViewHolder.this, getAdapterPosition());

                    } else if (status == 5) {
                        //待收货
                        showDialog("确认是否收货", status, SubleaseOrderAdapter.EquipmentViewHolder.this, getAdapterPosition());

                    } else if (status == 6) {
                        //退租中
                        showDialog("确认是否预约退租", status, SubleaseOrderAdapter.EquipmentViewHolder.this, getAdapterPosition());
                    }
                }
            });
        }

    }

}
