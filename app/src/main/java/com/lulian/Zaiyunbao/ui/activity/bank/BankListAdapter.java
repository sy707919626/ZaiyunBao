package com.lulian.Zaiyunbao.ui.activity.bank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lulian.Zaiyunbao.Bean.BankBean;
import com.lulian.Zaiyunbao.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/10/26.
 */

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.BankViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private List<BankBean> mBankListBean;

    public BankListAdapter(Context context, List<BankBean> bankListBean) {
        this.mContext = context;
        this.mBankListBean = bankListBean;

    }

    @Override
    public BankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bankcard_list, parent, false);
        BankViewHolder viewHolder = new BankViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BankViewHolder holder, final int position) {
        final BankBean mBankBean = mBankListBean.get(position);

        holder.bankCodeText.setText(showNumber(mBankBean.getAccountNo()));
        holder.bankNameText.setText(mBankBean.getAccountBank());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(mBankBean);
            }
        });

    }

    private String showNumber(String bankCode) {
        String bankCard = bankCode;
        int hideLength = 10;//替换位数，这里替代中间10位
        int startIndex = bankCard.length() / 2 - hideLength / 2;
        String replaceSymbol = "*";//替换符号
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bankCard.length(); i++) {
            char number = bankCard.charAt(i);
            if (i >= startIndex - 1 && i < startIndex + hideLength) {
                stringBuilder.append(replaceSymbol);
            } else {
                stringBuilder.append(number);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public int getItemCount() {
        return mBankListBean == null ? 0 : mBankListBean.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(BankBean mBankBean);
    }

    class BankViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bank_name_text)
        TextView bankNameText;
        @BindView(R.id.bank_code_text)
        TextView bankCodeText;

        public BankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }

}
