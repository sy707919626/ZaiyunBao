package com.lulian.Zaiyunbao.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lulian.Zaiyunbao.Bean.MessageListBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.widget.RxToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/26.
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {
    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;

    private Context mContext;
    private ArrayList<MessageListBean.RowsBean> mMessageListBean;
    private OnItemClickListener mOnItemClickListener;

    public MessageListAdapter(Context context, ArrayList<MessageListBean.RowsBean> messageListBean) {
        this.mContext = context;
        this.mMessageListBean = messageListBean;
    }

    public List<MessageListBean.RowsBean> getMyLiveList() {
        if (mMessageListBean == null) {
            mMessageListBean = new ArrayList<>();
        }
        return mMessageListBean;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message_list, parent, false);
        MessageViewHolder viewHolder = new MessageViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, final int position) {
        final MessageListBean.RowsBean mMessageList = mMessageListBean.get(position);

        holder.messageDatetime.setText(mMessageList.getCreateTime());
        holder.messageStart.setText(mMessageList.getContent());

        if (mEditMode == MYLIVE_MODE_CHECK) {
            holder.messageCheckBox.setVisibility(View.GONE);
        } else {
            holder.messageCheckBox.setVisibility(View.VISIBLE);

            if (mMessageList.isSelect()) {
                holder.messageCheckBox.setImageResource(R.drawable.ic_news_checked);
            } else {
                holder.messageCheckBox.setImageResource(R.drawable.ic_uncheck);
            }
        }

        holder.messageCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClickListener(position, mMessageListBean);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxToast.showToast("点击了" + mMessageList.getContent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMessageListBean == null ? 0 : mMessageListBean.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos, ArrayList<MessageListBean.RowsBean> messageList);
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.message_check_box)
        ImageView messageCheckBox;
        @BindView(R.id.message_type)
        TextView messageType;
        @BindView(R.id.message_datetime)
        TextView messageDatetime;
        @BindView(R.id.message_start)
        TextView messageStart;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }
}
