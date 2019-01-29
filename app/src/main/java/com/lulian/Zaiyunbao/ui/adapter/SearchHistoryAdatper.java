package com.lulian.Zaiyunbao.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lulian.Zaiyunbao.R;

public class SearchHistoryAdatper extends BaseQuickAdapter<String, BaseViewHolder> {
    public SearchHistoryAdatper() {
        super(R.layout.template_search_history);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_txt_tag, item);
        helper.addOnClickListener(R.id.item_txt_tag);

    }
}
