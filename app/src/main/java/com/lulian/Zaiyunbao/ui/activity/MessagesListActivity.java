package com.lulian.Zaiyunbao.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.MessageListBean;
import com.lulian.Zaiyunbao.MyApplication;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.adapter.MessageListAdapter;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/10/26.
 */

public class MessagesListActivity extends BaseActivity implements View.OnClickListener, MessageListAdapter.OnItemClickListener {


    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_EDIT = 1;
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.message_recyclerview)
    RecyclerView messageRecyclerview;
    @BindView(R.id.message_smartRefresh_Layout)
    SmartRefreshLayout messageSmartRefreshLayout;
    @BindView(R.id.read_btn)
    TextView readBtn;
    @BindView(R.id.btn_delete)
    TextView btnDelete;
    @BindView(R.id.ll_mycollection_bottom_dialog)
    LinearLayout llMycollectionBottomDialog;


    private MessageListAdapter mAdapter;
    private ArrayList<MessageListBean.RowsBean> mMessageList = new ArrayList<>();
    private boolean isRefresh; //刷新

    private int pageSize = 10;
    private int page = 1;

    private int index = 0;

    private int mEditMode = MYLIVE_MODE_CHECK;
    private boolean editorStatus = false;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_messageslist;
    }

    @Override
    protected void init() {

        ImmersionBar.with(this)
                .titleBar(R.id.detail_bar_title)
                .titleBarMarginTop(R.id.detail_bar_title)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        textDetailContent.setText("消息");
        textDetailRight.setText("编辑");

        //初始化消息列表
        initView();

        messageSmartRefreshLayout.autoRefresh(); //触发自动刷新

        messageSmartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
                getMessageData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isRefresh = true;
                messageSmartRefreshLayout.setLoadmoreFinished(false); //再次触发加载更多事件
                getMessageData();
            }
        });
        initListener();
    }

    private void initView() {
        messageRecyclerview.setItemAnimator(new DefaultItemAnimator());
        messageRecyclerview.setLayoutManager(new LinearLayoutManager(this));
//        messageRecyclerview.addItemDecoration(new RecyclerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new MessageListAdapter(this, mMessageList);
        messageRecyclerview.setAdapter(mAdapter);
    }


    /**
     * 获取消息列表
     */
    private void getMessageData() {
        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }

        JSONObject obj = new JSONObject();
        obj.put("Page", page);
        obj.put("Rows", 10);

        String messages = obj.toString();
        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                messages);

        mApi.messagesList(GlobalParams.sToken, GlobalParams.sUserId, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        MessageListBean messageListBean = MyApplication.get().getAppComponent().getGson().fromJson(s, MessageListBean.class);

                        if (isRefresh) {
                            mMessageList.clear();
                        }

                        mMessageList.addAll(messageListBean.getRows());

                        if (mAdapter != null) {
                            mAdapter.notifyDataSetChanged();
                        }

                        if (messageListBean.getRows().size() < pageSize) {
                            messageSmartRefreshLayout.finishLoadmoreWithNoMoreData(); //将不会再次触发加载更多事件
                        }
                    }

                    @Override
                    protected void onAfter() {
                        super.onAfter();
                        messageSmartRefreshLayout.finishRefresh();
                        messageSmartRefreshLayout.finishLoadmore();
                    }
                });

    }

    private void initListener() {
        mAdapter.setOnItemClickListener(this);
        btnDelete.setOnClickListener(this);
        textDetailRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_delete:
                deleteVideo();
                break;

            case R.id.text_detail_right:
                updataEditMode();
                break;
            default:
                break;
        }
    }

    /**
     * 删除逻辑
     */
    private void deleteVideo() {
        if (index == 0) {
            RxToast.warning("请选择需要删除的消息");
            return;
        }
        final AlertDialog builder = new AlertDialog.Builder(this)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = builder.findViewById(R.id.tv_msg);
        Button cancle = builder.findViewById(R.id.btn_cancle);
        Button sure = builder.findViewById(R.id.btn_sure);
        msg.setGravity(Gravity.CENTER);

        if (msg == null || cancle == null || sure == null) return;

        if (index == 1) {
            msg.setText("删除后不可恢复，是否删除该条目？");
        } else {
            msg.setText("删除后不可恢复，是否删除这" + index + "个条目？");
        }
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = mAdapter.getMyLiveList().size(), j = 0; i > j; i--) {
                    MessageListBean.RowsBean myLive = mAdapter.getMyLiveList().get(i - 1);
                    if (myLive.isSelect()) {

                        mAdapter.getMyLiveList().remove(myLive);
                        index--;
                    }
                }
                index = 0;

                if (mAdapter.getMyLiveList().size() == 0) {
                    llMycollectionBottomDialog.setVisibility(View.GONE);
                }
                mAdapter.notifyDataSetChanged();
                builder.dismiss();
            }
        });
    }

    private void updataEditMode() {
        mEditMode = mEditMode == MYLIVE_MODE_CHECK ? MYLIVE_MODE_EDIT : MYLIVE_MODE_CHECK;
        if (mEditMode == MYLIVE_MODE_EDIT) {
            textDetailRight.setText("取消");
            llMycollectionBottomDialog.setVisibility(View.VISIBLE);
            editorStatus = true;
        } else {
            textDetailRight.setText("编辑");
            llMycollectionBottomDialog.setVisibility(View.GONE);
            editorStatus = false;
        }
        mAdapter.setEditMode(mEditMode);
    }


    @Override
    public void onItemClickListener(int pos, ArrayList<MessageListBean.RowsBean> messageList) {
        if (editorStatus) {
            MessageListBean.RowsBean myLive = messageList.get(pos);
            boolean isSelect = myLive.isSelect();
            if (!isSelect) {
                index++;
                myLive.setSelect(true);
            } else {
                myLive.setSelect(false);
                index--;
            }
            mAdapter.notifyDataSetChanged();
        }
    }

}
