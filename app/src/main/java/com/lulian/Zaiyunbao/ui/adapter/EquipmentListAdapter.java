package com.lulian.Zaiyunbao.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lulian.Zaiyunbao.Bean.EquipmentListBean;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.event.LeaseEvent;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.data.http.ApiService;
import com.lulian.Zaiyunbao.ui.activity.LeaseMyEquipmentActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/26.
 */

public class EquipmentListAdapter extends RecyclerView.Adapter<EquipmentListAdapter.EquipmentViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    private Context mContext;
    private ArrayList<EquipmentListBean.RowsBean> mEquipmentListBean;
    private ApiService mApi;

    public EquipmentListAdapter(Context context, ArrayList<EquipmentListBean.RowsBean> equipmentListBean) {
        this.mContext = context;
        this.mEquipmentListBean = equipmentListBean;
    }

    public EquipmentListAdapter(Context context, ArrayList<EquipmentListBean.RowsBean> equipmentListBean, ApiService Api) {
        this.mContext = context;
        this.mEquipmentListBean = equipmentListBean;
        this.mApi = Api;
    }

    @Override
    public EquipmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_lease, parent, false);
        EquipmentViewHolder viewHolder = new EquipmentViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EquipmentViewHolder holder, final int position) {
        final EquipmentListBean.RowsBean mEquipmentList = mEquipmentListBean.get(position);

        if (mEquipmentList.getUID() != null) {
            //使用者ID等于当前用户ID  不能进行租赁订单发布
            if (mEquipmentList.getUID().equals(GlobalParams.sUserId)) {
                holder.leaseBtn.setVisibility(View.GONE);
                holder.leaseBtnCannot.setVisibility(View.VISIBLE);
            } else {
                holder.leaseBtn.setVisibility(View.VISIBLE);
                holder.leaseBtnCannot.setVisibility(View.GONE);
            }
        } else {
            holder.leaseBtn.setVisibility(View.VISIBLE);
            holder.leaseBtnCannot.setVisibility(View.GONE);
        }

        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(mEquipmentList.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            holder.leaseImgPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

//        Glide.with(mContext).load(Constants.BASE_URL + mEquipmentList.getPicture()).into(holder.leaseImgPhoto);
        holder.leaseShebeiName.setText(mEquipmentList.getEquipmentName());
        holder.leaseShebeiSpec.setText(mEquipmentList.getNorm());
        if (mEquipmentList.getTypeName().equals("保温箱")) {
            holder.leaseShebeiLoad.setText("容积" + String.valueOf(mEquipmentList.getVolume()) + "升；保温时长"
                     + String.valueOf(mEquipmentList.getWarmLong()) + "小时");
        } else {
            holder.leaseShebeiLoad.setText("静载" + String.valueOf(mEquipmentList.getStaticLoad()) + "T；动载"
                    + String.valueOf(mEquipmentList.getCarryingLoad()) + "T；架载" + String.valueOf(mEquipmentList.getOnLoad()) + "T");
        }
        holder.leaseShebeiPrice.setText(String.valueOf(mEquipmentList.getPValue()) + "");
        holder.leaseShebeiNum.setText(String.valueOf(mEquipmentList.getQuantity()) + "");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClickListener(position, mEquipmentList);
            }
        });

        holder.leaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMy = new Intent();
                intentMy.setClass(mContext, LeaseMyEquipmentActivity.class);
                intentMy.putExtra("EquipmentName", mEquipmentList.getEquipmentName());
                intentMy.putExtra("Norm", mEquipmentList.getNorm());
                intentMy.putExtra("Price", mEquipmentList.getPValue());
                intentMy.putExtra("Quantity", mEquipmentList.getQuantity());
                intentMy.putExtra("StaticLoad", mEquipmentList.getStaticLoad());
                intentMy.putExtra("CarryingLoad", mEquipmentList.getCarryingLoad());
                intentMy.putExtra("OnLoad", mEquipmentList.getOnLoad());
                intentMy.putExtra("Volume", mEquipmentList.getVolume());
                intentMy.putExtra("WarmLong", mEquipmentList.getWarmLong());
                intentMy.putExtra("SpecifiedLoad", mEquipmentList.getSpecifiedLoad());
                intentMy.putExtra("TypeName", mEquipmentList.getTypeName());
//                intentMy.putExtra("Picture", mEquipmentList.getPicture());
                intentMy.putExtra("TypeId", mEquipmentList.getTypeId());
                intentMy.putExtra("UserType", mEquipmentList.getUserType());
                intentMy.putExtra("CreateId", mEquipmentList.getCreateId());
                intentMy.putExtra("CreateUser", mEquipmentList.getCreateUser());
                intentMy.putExtra("OperatorId", mEquipmentList.getOperator()); //加盟商id
                intentMy.putExtra("Id", mEquipmentList.getId());//设备ID
                intentMy.putExtra("StorehouseId", mEquipmentList.getStorehouseId());//仓库ID

//                if (TextUtils.isEmpty(mEquipmentList.getUID())){
//                    intentMy.putExtra("UID", "");//使用者Id
//                } else {
                    intentMy.putExtra("UID", mEquipmentList.getUID());//使用者Id
//                }


                intentMy.putExtra("Deposit", mEquipmentList.getDeposit());//押金
                mContext.startActivity(intentMy);
            }
        });

        holder.leaseBtnCannot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApi.ChangeGroundingQuantity(GlobalParams.sToken, GlobalParams.sUserId,
                        mEquipmentList.getId(),0)
                        .compose(RxHttpResponseCompat.<String>compatResult())
                        .subscribe(new ErrorHandlerSubscriber<String>() {
                            @Override
                            public void onNext(String s) {
                                RxToast.success("撤销上架成功");
                                EventBus.getDefault().post(new LeaseEvent());
                            }
                        });

            }
        });

    }

    @Override
    public int getItemCount() {
        return mEquipmentListBean == null ? 0 : mEquipmentListBean.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, EquipmentListBean.RowsBean equipmentList);
    }

    class EquipmentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lease_img_photo)
        ImageView leaseImgPhoto;
        @BindView(R.id.lease_shebei_name)
        TextView leaseShebeiName;
        @BindView(R.id.lease_shebei_spec)
        TextView leaseShebeiSpec;
        @BindView(R.id.lease_shebei_load)
        TextView leaseShebeiLoad;
        @BindView(R.id.lease_shebei_price)
        TextView leaseShebeiPrice;
        @BindView(R.id.lease_shebei_num)
        TextView leaseShebeiNum;
        @BindView(R.id.detail_layout_userinfo)
        LinearLayout detailLayoutUserinfo;
        @BindView(R.id.lease_distance)
        TextView leaseDistance;
        @BindView(R.id.lease_btn)
        Button leaseBtn;
        @BindView(R.id.lease_btn_cannot)
        Button leaseBtnCannot;

        public EquipmentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }

}
