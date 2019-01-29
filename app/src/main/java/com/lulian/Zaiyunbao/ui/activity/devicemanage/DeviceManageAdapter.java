package com.lulian.Zaiyunbao.ui.activity.devicemanage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lulian.Zaiyunbao.Bean.DeviceManageBean;
import com.lulian.Zaiyunbao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/26.
 */

public class DeviceManageAdapter extends RecyclerView.Adapter<DeviceManageAdapter.DeviceManageViewHolder> {


    private String state = "";
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private ArrayList<DeviceManageBean.RowsBean> mDeviceManageListBean = new ArrayList<>();
    private boolean isAllFragment = false;

    public DeviceManageAdapter(Context context, ArrayList<DeviceManageBean.RowsBean> deviceManageListBean, boolean isAll) {
        this.mContext = context;
        this.mDeviceManageListBean = deviceManageListBean;
        this.isAllFragment = isAll;
    }

    public List<DeviceManageBean.RowsBean> getMyLiveList() {
        if (mDeviceManageListBean == null) {
            mDeviceManageListBean = new ArrayList<>();
        }
        return mDeviceManageListBean;
    }

    @Override
    public DeviceManageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_device_manage, parent, false);
        DeviceManageViewHolder viewHolder = new DeviceManageViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DeviceManageViewHolder holder, final int position) {
        final DeviceManageBean.RowsBean mDeviceManage = mDeviceManageListBean.get(position);

        holder.deviceManageNo.setText(mDeviceManage.getECode());
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(mDeviceManage.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            holder.deviceManageImgPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        holder.deviceManageSpec.setText(mDeviceManage.getNorm());
        holder.deviceManageName.setText(mDeviceManage.getEquipmentName());

        //使用状态：6=闲置 3=占用
        if (mDeviceManage.getUseStatus() == 6) {
            state = "闲置";

        } else if (mDeviceManage.getUseStatus() == 3) {
            state = "占用";
        }

        holder.deviceManageState.setText(state);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DeviceManageDetailsActivity.class);
                intent.putExtra("ECode", mDeviceManage.getECode());
                mContext.startActivity(intent);
            }
        });


        holder.messageCheckBox.setVisibility(View.VISIBLE);

        if (!isAllFragment) {
            if (mDeviceManage.isSelect()) {
                holder.messageCheckBox.setImageResource(R.drawable.ic_manage_checked);
            } else {
                holder.messageCheckBox.setImageResource(R.drawable.ic_uncheck);
            }
        } else {
            holder.messageCheckBox.setVisibility(View.GONE);
        }

        holder.deviceManageCheckLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClickListener(position, mDeviceManageListBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDeviceManageListBean == null ? 0 : mDeviceManageListBean.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, ArrayList<DeviceManageBean.RowsBean> deviceManageListBean);
    }

    class DeviceManageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_text)
        TextView orderText;
        @BindView(R.id.device_manage_no)
        TextView deviceManageNo;
        @BindView(R.id.device_manage_state)
        TextView deviceManageState;
        @BindView(R.id.device_manage_img_photo)
        ImageView deviceManageImgPhoto;
        @BindView(R.id.device_manage_name)
        TextView deviceManageName;
        @BindView(R.id.device_manage_spec)
        TextView deviceManageSpec;
        @BindView(R.id.device_manage_layout)
        LinearLayout deviceManageLayout;
        @BindView(R.id.message_check_box)
        ImageView messageCheckBox;
        @BindView(R.id.device_manage_check_layout)
        LinearLayout deviceManageCheckLayout;

        public DeviceManageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
