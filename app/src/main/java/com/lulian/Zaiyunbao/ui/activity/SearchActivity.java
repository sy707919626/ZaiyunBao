package com.lulian.Zaiyunbao.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lulian.Zaiyunbao.Bean.EquipmentListBean;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RecyclerItemDecoration;
import com.lulian.Zaiyunbao.common.widget.SPUtils;
import com.lulian.Zaiyunbao.ui.adapter.EquipmentListAdapter;
import com.lulian.Zaiyunbao.ui.adapter.SearchHistoryAdatper;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/*****************************
 * @Description：搜索页面
 * @Version:v1.0.0
 *****************************/

public class SearchActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.rl_layout)
    RelativeLayout rlLayout;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.history_delete)
    TextView historyDelete;
    @BindView(R.id.recycler_view_history)
    RecyclerView recyclerViewHistory;
    @BindView(R.id.layout_history)
    LinearLayout layoutHistory;
    @BindView(R.id.recycler_view_result_no)
    TextView recyclerViewResultNo;
    @BindView(R.id.recycler_view_result)
    RecyclerView recyclerViewResult;
    @BindView(R.id.recycler_view_result_layout)
    LinearLayout recyclerViewResultLayout;
    private String[] historyList;
    private List<String> historyLists = new ArrayList<>();
    private SearchHistoryAdatper mHistoryAdatper;

    private ArrayList<EquipmentListBean.RowsBean> mEquipmentList = new ArrayList<>();
    private EquipmentListAdapter mAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_search;
    }


    public void showSearchHistory(List<String> list) {
        initHistoryRecycleView(list);
        recyclerViewResultLayout.setVisibility(View.GONE);
        layoutHistory.setVisibility(View.VISIBLE);
        recyclerViewHistory.setVisibility(View.VISIBLE);
        recyclerViewResult.setVisibility(View.GONE);
    }

    public void showSearchResult(ArrayList<EquipmentListBean.RowsBean> mEquipmentList) {
        layoutHistory.setVisibility(View.GONE);

        recyclerViewResultLayout.setVisibility(View.VISIBLE);

        if (mEquipmentList.size() <= 0) {
            recyclerViewResultNo.setVisibility(View.VISIBLE);
            recyclerViewResult.setVisibility(View.GONE);
        } else {
            recyclerViewResultNo.setVisibility(View.GONE);
            recyclerViewResult.setVisibility(View.VISIBLE);
        }
    }


    private void initHistoryRecycleView(List<String> list) {
        mHistoryAdatper = new SearchHistoryAdatper();
        mHistoryAdatper.addData(list);
        GridLayoutManager lm = new GridLayoutManager(this, 4);
//        SpaceItemDecoration itemd = new SpaceItemDecoration(20);
//        recyclerViewHistory.addItemDecoration(itemd);

        recyclerViewHistory.setLayoutManager(lm);
        recyclerViewHistory.setAdapter(mHistoryAdatper);

        recyclerViewHistory.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                search(mHistoryAdatper.getItem(position));
                etSearch.setText(mHistoryAdatper.getItem(position));
                search(mHistoryAdatper.getItem(position));

            }
        });

    }

    private void initData() {
        String history = SPUtils.getInstance(this).getString("HISTORY");

        if (!TextUtils.isEmpty(history)) {
            if (history.contains(",")) {
                historyList = (history.split(","));
            } else {
                historyList = new String[]{history};
            }
        }

        if (!history.equals("")) {
            historyLists = Arrays.asList(historyList);
            showSearchHistory(historyLists);
        }
    }

    private void postHistory(String textView) {
        String searchTv = textView;
        if (TextUtils.isEmpty(SPUtils.getInstance(this).getString("HISTORY"))) {
            SPUtils.getInstance(this).put("HISTORY", searchTv);
        } else {
            String history1 = SPUtils.getInstance(this).getString("HISTORY");
            if (!history1.contains(searchTv)) {
                SPUtils.getInstance(this).put("HISTORY", history1 + "," + searchTv);
            }
        }
    }

    protected void init() {
        ImmersionBar.with(this)
                .titleBar(R.id.detail_bar_title)
                .titleBarMarginTop(R.id.detail_bar_title)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        textDetailContent.setText("搜索");
        textDetailRight.setVisibility(View.GONE);

        //监听键盘搜索按键
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    // 先隐藏键盘
                    ((InputMethodManager) etSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchActivity.this
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    // TODO 跳转搜索结果页面
                    if (!etSearch.getText().toString().trim().equals("")) {

                    } else {
                        Toast.makeText(SearchActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        initData();
        setupSearchView();
    }


    private void search(String keyword) {
        //搜索请求
        getData(keyword);
        //成功后保存
        postHistory(keyword);
    }


    @OnClick({R.id.iv_empty, R.id.image_back_detail_bar, R.id.tv_cancel, R.id.history_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_empty:
                //清空et_search
                etSearch.setText("");
                initData();
                break;

            case R.id.image_back_detail_bar:
                finish();
                SearchActivity.this.overridePendingTransition(0, R.anim.activity_close);
                break;

            case R.id.tv_cancel:
                finish();
                SearchActivity.this.overridePendingTransition(0, R.anim.activity_close);
                break;
            case R.id.history_delete:
                //清空历史记录
                SPUtils.getInstance(this).remove("HISTORY");
                recyclerViewHistory.setVisibility(View.GONE);

                break;
            default:
                break;
        }
    }

    private void setupSearchView() {
        //etSeatc点击回车
        RxTextView.editorActions(etSearch).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                search(etSearch.getText().toString().trim());
                initData();
            }
        });

        RxTextView.textChanges(etSearch)
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(@NonNull CharSequence charSequence) throws Exception {
                        return charSequence.toString().trim().length() > 0;
                    }
                }).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(@NonNull CharSequence charSequence) throws Exception {
                if (charSequence.length() > 0) {
                    ivEmpty.setVisibility(View.VISIBLE);
                } else {
                    ivEmpty.setVisibility(View.GONE);
                }

                search(etSearch.getText().toString().trim());
                initData();
            }
        });
    }

    private void getData(String keyword) {
        if (mEquipmentList != null) {
            mEquipmentList.clear();
        }

        recyclerViewResult.setItemAnimator(new DefaultItemAnimator());
        recyclerViewResult.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewResult.addItemDecoration(new RecyclerItemDecoration(this, DividerItemDecoration.VERTICAL));

        JSONObject TypeId = new JSONObject();//设备类型Id
        TypeId.put("name", "equipment.EquipmentName");
        TypeId.put("type", "like");
        TypeId.put("value", keyword);
        JSONObject[] filters = {TypeId};

        JSONObject obj = new JSONObject();
        obj.put("Page", 1);
        obj.put("Rows", 10);
        obj.put("Filters", filters);

        String messages = obj.toString();
        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                messages);

        mApi.equipmentList(GlobalParams.sToken, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        EquipmentListBean equipmentListBean = MyApplication.get().getAppComponent().getGson().fromJson(s, EquipmentListBean.class);
                        mEquipmentList.addAll(equipmentListBean.getRows());
                        mAdapter = new EquipmentListAdapter(SearchActivity.this, mEquipmentList);
                        recyclerViewResult.setAdapter(mAdapter);

                        showSearchResult(mEquipmentList); //展现数据
                    }
                });

    }
}
