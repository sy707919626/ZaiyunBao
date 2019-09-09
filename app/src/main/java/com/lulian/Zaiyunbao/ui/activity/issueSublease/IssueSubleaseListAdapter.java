package com.lulian.Zaiyunbao.ui.activity.issueSublease;

import android.content.Context;
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
import com.lulian.Zaiyunbao.Bean.IssueListBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.di.component.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/26.
 */

public class IssueSubleaseListAdapter extends RecyclerView.Adapter<IssueSubleaseListAdapter.IssueViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private OnItemButtonClickListener mOnItemButtonClickListener;

    private Context mContext;
    private ArrayList<IssueListBean> mIssueListBean = new ArrayList<>();
    private IssueListBean issueListBean;

    public IssueSubleaseListAdapter(Context context, ArrayList<IssueListBean> issueListBeans) {
        this.mContext = context;
        this.mIssueListBean = issueListBeans;
    }

    @Override
    public IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_issue_sublease, parent, false);
        IssueViewHolder viewHolder = new IssueViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IssueViewHolder holder, final int position) {
//        try {
//            byte[] bitmapArray;
//            bitmapArray = Base64.decode(mIssueListBean.get(position).getPicture(), Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
//                    bitmapArray.length);
//            holder.issueSubleaseImgPhoto.setImageBitmap(bitmap);
//        } catch (Exception e) {
//        }

        Glide.with(mContext).load(Constants.BASE_URL +"/" + mIssueListBean.get(position).getPicture()).into(holder.issueSubleaseImgPhoto);

        holder.issueSubleaseName.setText(mIssueListBean.get(position).getEquipmentName());
        holder.issueSubleaseSpec.setText(mIssueListBean.get(position).getNorm());

        if (mIssueListBean.get(position).getTypeName().equals("保温箱")) {
            holder.issueSubleaseLoad.setText("容积" + String.valueOf(mIssueListBean.get(position).getVolume()) + "升；保温时长"
                    + String.valueOf(mIssueListBean.get(position).getWarmLong()) + "小时");
        } else {
            holder.issueSubleaseLoad.setText("静载" + String.valueOf(mIssueListBean.get(position).getStaticLoad()) + "T；动载"
                    + String.valueOf(mIssueListBean.get(position).getCarryingLoad()) + "T；架载" + String.valueOf(mIssueListBean.get(position).getOnLoad()) + "T");
        }

        holder.issueSubleaseSum.setText(String.valueOf(mIssueListBean.get(position).getQuantity()) + "");
        holder.issueSubleaseKzsum.setText(String.valueOf(mIssueListBean.get(position).getQuantity()) + "");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClickListener(position, mIssueListBean);
            }
        });

        holder.issueSubleaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemButtonClickListener.onItemButtonClickListener(position, mIssueListBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mIssueListBean == null ? 0 : mIssueListBean.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemButtonClickListener(OnItemButtonClickListener onItemButtonClickListener) {
        this.mOnItemButtonClickListener = onItemButtonClickListener;
    }

//    private void dialogChoice(final int postion) {
//        final AlertDialog builder = new AlertDialog.Builder(mContext)
//                .create();
//        builder.show();
//        if (builder.getWindow() == null) return;
//        builder.getWindow().setContentView(R.layout.choice_issue);//设置弹出框加载的布局
//        RadioGroup choice_issue = builder.findViewById(R.id.IssueWay_choose);
//        final RadioButton ddd = builder.findViewById(R.id.choose_ddd);
//        final RadioButton gp = builder.findViewById(R.id.choose_gp);
//
//        if (choice_issue == null || ddd == null || gp == null) return;
//
//        choice_issue.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (ddd.getId() == checkedId) {
//
//
//
//                } else if (gp.getId() == checkedId) {
//                    Intent intent = new Intent(mContext, ListingIssueActivity.class);
//                    intent.putExtra("issueListBean", mIssueListBean.get(postion));
//                    mContext.startActivity(intent);
//                }
//
//                builder.dismiss();
//            }
//        });
//
//    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, ArrayList<IssueListBean> issueListBeans);
    }

    public interface OnItemButtonClickListener {
        void onItemButtonClickListener(int position, ArrayList<IssueListBean> issueListBeans);
    }

    class IssueViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.issue_sublease_img_photo)
        ImageView issueSubleaseImgPhoto;
        @BindView(R.id.issue_sublease_name)
        TextView issueSubleaseName;
        @BindView(R.id.issue_sublease_spec)
        TextView issueSubleaseSpec;
        @BindView(R.id.issue_sublease_load)
        TextView issueSubleaseLoad;
        @BindView(R.id.issue_sublease_sum)
        TextView issueSubleaseSum;
        @BindView(R.id.issue_sublease_kzsum)
        TextView issueSubleaseKzsum;
        @BindView(R.id.issue_sublease_btn)
        Button issueSubleaseBtn;

        public IssueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

//            issueSubleaseBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialogChoice(getAdapterPosition());
//                }
//            });
        }

    }
}
