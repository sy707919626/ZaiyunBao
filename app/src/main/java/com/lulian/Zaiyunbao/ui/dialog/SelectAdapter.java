package com.lulian.Zaiyunbao.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lulian.Zaiyunbao.Bean.SaleEntity;
import com.lulian.Zaiyunbao.R;

import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 */

public class SelectAdapter extends BaseAdapter {
    private Context context;
    private List<SaleEntity> list;
    private OnItemClickListener mOnItemClickListener;

    public SelectAdapter(Context context, List<SaleEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.dialog_item, null);
            vh.textview = convertView.findViewById(R.id.textview);
            vh.cb = convertView.findViewById(R.id.cb);
            vh.itemView = convertView.findViewById(R.id.dialog_item_view);
            vh.cb.setVisibility(View.GONE);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.textview.setText(list.get(position).getTitle());

        if (list.get(position).isChecked()) {//状态选中
            vh.textview.setTextColor(context.getResources().getColor(R.color.text_hint_bule));
        } else {
            vh.textview.setTextColor(context.getResources().getColor(R.color.text_black));
        }


        if (position == list.size() - 1) {
            vh.itemView.setVisibility(View.GONE);
        } else {
            vh.itemView.setVisibility(View.VISIBLE);
        }

        //点击事件
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentNun = -1;

                for (SaleEntity saleEntity : list) {  // 遍历list集合中的数据
                    saleEntity.setChecked(false);  // 全部设置为未选中
                }

                if (currentNun == -1) {  // 如果选中
                    list.get(position).setChecked(true);
                    currentNun = position;
                } else if (currentNun == position) {  // 同一个item选中变未选中
                    for (SaleEntity saleEntity : list) {
                        saleEntity.setChecked(false);
                    }
                    currentNun = -1;
                } else if (currentNun != position) {  // 不是同一个item选中当前的， 去除上一个选中的状态
                    for (SaleEntity saleEntity : list) {
                        saleEntity.setChecked(false);
                    }

                    list.get(position).setChecked(true);
                    currentNun = position;
                }

                notifyDataSetChanged();//刷新adapter

                mOnItemClickListener.onItemClickListener(position, list);
            }
        });

        return convertView;
    }

    public void setOnItemClickListener(SelectAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, List<SaleEntity> saleEntity);
    }

    static class ViewHolder {
        TextView textview;
        CheckBox cb;
        View itemView;
    }
}
