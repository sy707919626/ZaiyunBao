package com.lulian.Zaiyunbao.ui.activity.myorder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lulian.Zaiyunbao.Bean.MyOrderLisetBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.FastBlur;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.data.http.ApiService;
import com.lulian.Zaiyunbao.ui.activity.ReserveRetireDetailsActivity;
import com.lulian.Zaiyunbao.ui.activity.leaseorder.ReceiveLeaseInfoActivity;
import com.lulian.Zaiyunbao.ui.activity.pay.PayActivity;
import com.lulian.Zaiyunbao.ui.activity.subleaseorder.SubleaseOrderEntryActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/26.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.EquipmentViewHolder> {

    private String state = "";
    private String stateBtn = "";
    private String ZulinModel = "";
    private boolean isAllFragment = false;
    private ApiService mApi;
    private OnItemClickListener mOnItemClickListener;

    private Context mContext;
    private Activity mActivity;
    private ArrayList<MyOrderLisetBean.RowsBean> mOrderListBean = new ArrayList<>();


    public MyOrderAdapter(Context context, ArrayList<MyOrderLisetBean.RowsBean> orderListBeans,
                          ApiService mApi, boolean isAll) {
        this.mContext = context;
        this.mActivity = (Activity) context;
        this.mOrderListBean = orderListBeans;
        this.mApi = mApi;
        this.isAllFragment = isAll;

    }

    @Override
    public EquipmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_myorder, parent, false);
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

        if (mOrderList.getFormType() == 3) {  //(订单类型1求租2租赁3转租)
            holder.leaseRentSumText.setText("转租数量：");
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

            }
        } else if (mOrderList.getFormType() == 2) { //租赁
            holder.leaseRentSumText.setText("租赁数量：");
            if (mOrderList.getStatus() == 0) {
                state = "未接单";
                holder.orderStateBtn.setVisibility(View.GONE);
            } else if (mOrderList.getStatus() == 1) {
                state = "待确认";
                holder.orderStateBtn.setVisibility(View.GONE);

            } else if (mOrderList.getStatus() == 2) {
                state = "待支付";
                stateBtn = "支付押金";
                holder.orderStateBtn.setVisibility(View.VISIBLE);

            } else if (mOrderList.getStatus() == 3) {
                state = "待发货";
                holder.orderStateBtn.setVisibility(View.GONE);

            } else if (mOrderList.getStatus() == 5) {
                state = "已发货";
                stateBtn = "确认收货";
                holder.orderStateBtn.setVisibility(View.VISIBLE);

            } else if (mOrderList.getStatus() == 6) {
                state = "已收货";
                stateBtn = "我要退租";
                holder.orderStateBtn.setVisibility(View.VISIBLE);
            }
        } else if (mOrderList.getFormType() == 1) { //求租
            holder.leaseRentSumText.setText("求租数量：");
            if (mOrderList.getStatus() == 0) {
                state = "未接单";
                holder.orderStateBtn.setVisibility(View.GONE);

            } else if (mOrderList.getStatus() == 2 || mOrderList.getStatus() == 1) {
                state = "待支付";
                stateBtn = "支付押金";
                holder.orderStateBtn.setVisibility(View.VISIBLE);

            } else if (mOrderList.getStatus() == 3) {
                state = "待发货";
                holder.orderStateBtn.setVisibility(View.GONE);

            } else if (mOrderList.getStatus() == 5) {
                state = "已发货";
                stateBtn = "确认收货";
                holder.orderStateBtn.setVisibility(View.VISIBLE);

            } else if (mOrderList.getStatus() == 6) {
                state = "已收货";
                stateBtn = "我要退租";
                holder.orderStateBtn.setVisibility(View.VISIBLE);
            }
        }

        if (mOrderList.getStatus() == 7) {
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

        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(mOrderList.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            holder.leaseImgPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        holder.leaseShebeiName.setText(mOrderList.getEquipmentName());
        holder.leaseShebeiSpec.setText(mOrderList.getNorm());
        if (mOrderList.getZulinModel() == 1) {
            //分时租赁
            ZulinModel = "分时租赁";
        } else {
            ZulinModel = "分次租赁";
        }
        holder.leaseLeaseModel.setText(ZulinModel);
        holder.leaseRentSum.setText(mOrderList.getCount() + "个");
        holder.leaseXiadanTime.setText(mOrderList.getOrderCreateTime());

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

    private void getData(final EquipmentViewHolder holder, String orderId, final int status, final int position) {
        mApi.updateOrderStatus(GlobalParams.sToken, 1, orderId, status)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        RxToast.success("订单状态修改成功");
                        if (status == 2) {
                            holder.orderStateBtn.setText("支付押金");
                            holder.orderState.setText("待支付");

                        } else if (status == 3) {
                            holder.orderStateBtn.setText("确认发货");
                            holder.orderState.setText("待发货");

                        } else if (status == 5) {
                            holder.orderStateBtn.setText("确认收货");
                            holder.orderState.setText("已发货");

                        } else if (status == 6) {
                            holder.orderStateBtn.setText("我要退租");
                            holder.orderState.setText("已收货");
                        }

                        if (!isAllFragment) {
                            mOrderListBean.remove(position);
                            notifyItemRemoved(position);//刷新被删除的地方
                            notifyItemRangeChanged(position, getItemCount()); //刷新被删除数据，以及其后面的数据
                        }
                    }
                });
    }

    private Bitmap getIerceptionScreen() {
        // View是你需要截图的View
        View view = mActivity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        // 获取屏幕长和高
        int width = mActivity.getWindowManager().getDefaultDisplay().getWidth();
        int height = mActivity.getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏
//         Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap bitmap = Bitmap.createBitmap(b, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        bitmap = FastBlur.fastBlur(bitmap, 5);

        if (bitmap != null) {
            return bitmap;
        } else {
            return null;
        }
    }

    @SuppressLint("NewApi")
    private void showDialog(String message, final int status, final EquipmentViewHolder equipmentViewHolder, final int position) {
        final AlertDialog builder = new AlertDialog.Builder(mContext)
                .create();
        builder.show();

        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        LinearLayout lp = builder.findViewById(R.id.pop_user_layout);
        TextView msg = builder.findViewById(R.id.tv_msg);
        Button cancle = builder.findViewById(R.id.btn_cancle);
        Button sure = builder.findViewById(R.id.btn_sure);
//        builder.getWindow().getDecorView().setBackground(new BitmapDrawable(getIerceptionScreen()));

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
                    if (mOrderListBean.get(position).getIsRendIn() == 0) { //是否为租出方
                        getData(equipmentViewHolder, mOrderListBean.get(position).getOrdersId(), 2, position);
                    } else {
                        getData(equipmentViewHolder, mOrderListBean.get(position).getOrdersId(), 10, position);
                    }

                } else if (status == 3) {
                    //待发货
                    //(订单类型1求租2租赁3转租)
                    if (mOrderListBean.get(position).getFormType() == 3) {
                        //转租发货逻辑
                        Intent intent = new Intent(mContext, SubleaseOrderEntryActivity.class);
                        intent.putExtra("EquipmentName", mOrderListBean.get(position).getEquipmentName());
                        intent.putExtra("EquipmentNorm", mOrderListBean.get(position).getNorm());
                        intent.putExtra("OrderId", mOrderListBean.get(position).getOrdersId());
                        intent.putExtra("Count", mOrderListBean.get(position).getCount());
                        intent.putExtra("EquipmentId", mOrderListBean.get(position).getId());

                        intent.putExtra("AdapterPage", "MyOrderAdapter");

                        mContext.startActivity(intent);
                    }

                } else if (status == 5) {
                    //待收货
                    Intent intent = new Intent(mContext, ReceiveLeaseInfoActivity.class);
                    intent.putExtra("OrdersId", mOrderListBean.get(position).getOrdersId());

                    intent.putExtra("AdapterPage", "MyOrderAdapter");
                    mContext.startActivity(intent);


                } else if (status == 6) {
                    //退租中
                    Intent intentReserve = new Intent(mContext, ReserveRetireDetailsActivity.class);
                    intentReserve.putExtra("OrdersId", mOrderListBean.get(position).getOrdersId());
                    intentReserve.putExtra("OrderNo", mOrderListBean.get(position).getOrderNo());
                    intentReserve.putExtra("Id", mOrderListBean.get(position).getId());

                    intentReserve.putExtra("AdapterPage", "MyOrderAdapter");
                    mContext.startActivity(intentReserve);
                }
                builder.dismiss();
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
        @BindView(R.id.lease_shebei_name)
        TextView leaseShebeiName;
        @BindView(R.id.lease_shebei_spec)
        TextView leaseShebeiSpec;
        @BindView(R.id.lease_Lease_Model)
        TextView leaseLeaseModel;
        @BindView(R.id.lease_Rent_sum_text)
        TextView leaseRentSumText;
        @BindView(R.id.lease_Rent_sum)
        TextView leaseRentSum;
        @BindView(R.id.lease_xiadan_time)
        TextView leaseXiadanTime;
        @BindView(R.id.order_state_btn)
        Button orderStateBtn;

        public EquipmentViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            orderStateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //未接单 = 0, 已接单 = 1,订单确认待支付 = 2,
                    // 已支付待发货 = 3,已发货 = 5,已收货 = 6,退租中 = 7,已退租 = 8,已结算 = 9,已撤销 = 10
                    int status = mOrderListBean.get(getAdapterPosition()).getStatus();
                    if (status == 1) {
                        if (mOrderListBean.get(getAdapterPosition()).getFormType() == 3) { //转租
                            //已接单待确认（转租单）
                            if (mOrderListBean.get(getAdapterPosition()).getIsRendIn() == 1) {
                                showDialog("确认是否接单", status, EquipmentViewHolder.this, getAdapterPosition());
                            } else {
                                showDialog("确认是否撤回订单", status, EquipmentViewHolder.this, getAdapterPosition());
                            }
                        }

                        //求租单状态1  也是支付
                        if (mOrderListBean.get(getAdapterPosition()).getFormType() == 1){
                            //支付押金
                            Intent intent = new Intent(mContext, PayActivity.class);
                            intent.putExtra("orderId", mOrderListBean.get(getAdapterPosition()).getOrdersId());
                            intent.putExtra("UserId", GlobalParams.sUserId);
                            intent.putExtra("money", mOrderListBean.get(getAdapterPosition()).getOrderDeposit());
                            intent.putExtra("OrderType", 1); //1租赁单2购买单

                            mContext.startActivity(intent);
                        }

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
                        showDialog("确认是否发货", status, EquipmentViewHolder.this, getAdapterPosition());

                    } else if (status == 5) {
                        //待收货
                        showDialog("确认是否收货", status, EquipmentViewHolder.this, getAdapterPosition());

                    } else if (status == 6) {
                        //退租中
                        showDialog("确认是否预约退租", status, EquipmentViewHolder.this, getAdapterPosition());
                    }
                }
            });
        }
    }
}
