package com.lulian.Zaiyunbao.ui.adapter;

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

import com.lulian.Zaiyunbao.Bean.BuyListBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.ui.activity.BuyMyEquipmentActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/26.
 */

public class BuyListAdapter extends RecyclerView.Adapter<BuyListAdapter.BuyViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    private Context mContext;
    private ArrayList<BuyListBean.RowsBean> mBuyListBean;

    public BuyListAdapter(Context context, ArrayList<BuyListBean.RowsBean> buyListBean) {
        this.mContext = context;
        this.mBuyListBean = buyListBean;

    }

    @Override
    public BuyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_buy, parent, false);
        BuyViewHolder viewHolder = new BuyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BuyViewHolder holder, final int position) {
        final BuyListBean.RowsBean mBuyList = mBuyListBean.get(position);

        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(mBuyList.getPicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            holder.mBuyImgPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

//        Glide.with(mContext).load(Constants.BASE_URL + mEquipmentList.getPicture()).into(holder.leaseImgPhoto);
        holder.mBuyShebeiName.setText(mBuyList.getEquipmentName());
        holder.mBuyShebeiSpec.setText(mBuyList.getNorm());
        if (mBuyList.getTypeName().equals("保温箱")) {
            holder.mBuyShebeiPriceDanwei.setText("元/个(不含税)");
            holder.mBuyShebeiNumDanwei.setText("个");
            holder.mBuyShebeiLoad.setText("容积" + String.valueOf(mBuyList.getVolume()) + "升T；保温时长"
                    + String.valueOf(mBuyList.getWarmLong()) + "小时");
        } else {
            holder.mBuyShebeiPriceDanwei.setText("元/片(不含税)");
            holder.mBuyShebeiNumDanwei.setText("片");
            holder.mBuyShebeiLoad.setText("静载" + String.valueOf(mBuyList.getStaticLoad()) + "T；动载"
                    + String.valueOf(mBuyList.getCarryingLoad()) + "T；架载" + String.valueOf(mBuyList.getOnLoad()) + "T");
        }
        holder.mBuyShebeiPrice.setText(String.valueOf(mBuyList.getPriceNow()) + "");
        holder.mBuyShebeiNum.setText(String.valueOf(mBuyList.getQuantity()) + "");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClickListener(position, mBuyList);
            }
        });

        holder.mBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMy = new Intent();
                intentMy.setClass(mContext, BuyMyEquipmentActivity.class);
                intentMy.putExtra("EquipmentName", mBuyList.getEquipmentName());
                intentMy.putExtra("Norm", mBuyList.getNorm());
                intentMy.putExtra("Price", mBuyList.getPriceNow());
                intentMy.putExtra("Quantity", mBuyList.getQuantity());
                intentMy.putExtra("StaticLoad", mBuyList.getStaticLoad());
                intentMy.putExtra("CarryingLoad", mBuyList.getCarryingLoad());
                intentMy.putExtra("OnLoad", mBuyList.getOnLoad());
                intentMy.putExtra("Volume", mBuyList.getVolume());
                intentMy.putExtra("WarmLong", mBuyList.getWarmLong());
                intentMy.putExtra("SpecifiedLoad", mBuyList.getSpecifiedLoad());
                intentMy.putExtra("TypeName", mBuyList.getTypeName());
//                intentMy.putExtra("Picture", mBuyList.getPicture());
                intentMy.putExtra("TypeId", mBuyList.getTypeId());
                intentMy.putExtra("SupplierContactName", mBuyList.getManager());//出售方名称
                intentMy.putExtra("SupplierContactPhone", mBuyList.getTouch()); //出售方电话
                intentMy.putExtra("Id", mBuyList.getId());//设备ID
                intentMy.putExtra("StorehouseId", mBuyList.getStorehouseId());//仓库ID
                mContext.startActivity(intentMy);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBuyListBean == null ? 0 : mBuyListBean.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, BuyListBean.RowsBean equipmentList);
    }

    class BuyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.buy_img_photo)
        ImageView mBuyImgPhoto;
        @BindView(R.id.buy_shebei_name)
        TextView mBuyShebeiName;
        @BindView(R.id.buy_shebei_spec)
        TextView mBuyShebeiSpec;
        @BindView(R.id.buy_shebei_load)
        TextView mBuyShebeiLoad;
        @BindView(R.id.buy_shebei_price)
        TextView mBuyShebeiPrice;
        @BindView(R.id.buy_shebei_price_danwei)
        TextView mBuyShebeiPriceDanwei;
        @BindView(R.id.buy_shebei_num)
        TextView mBuyShebeiNum;
        @BindView(R.id.buy_shebei_num_danwei)
        TextView mBuyShebeiNumDanwei;
        @BindView(R.id.buy_distance)
        TextView mBuyDistance;
        @BindView(R.id.buy_btn)
        Button mBuyBtn;
        public BuyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }

}
