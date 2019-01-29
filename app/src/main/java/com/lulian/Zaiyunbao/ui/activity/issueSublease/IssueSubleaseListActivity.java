package com.lulian.Zaiyunbao.ui.activity.issueSublease;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.IssueListBean;
import com.lulian.Zaiyunbao.Bean.SaleEntity;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/11/26.
 */

public class IssueSubleaseListActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.issue_sublease_recyclerview)
    RecyclerView issueSubleaseRecyclerview;
    @BindView(R.id.issue_dialog_bg)
    ImageView issueDialogBg;
    private IssueSubleaseListAdapter mAdapter;
    private ArrayList<IssueListBean> issueListBeans = new ArrayList<>();

    private Handler mHandler;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_issue_sublease_list;
    }

    @SuppressLint("NewApi")
    @Override
    protected void init() {
        ImmersionBar.with(this)
                .titleBar(R.id.detail_bar_title)
                .titleBarMarginTop(R.id.detail_bar_title)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        issueDialogBg.setImageAlpha(0);
        issueDialogBg.setVisibility(View.GONE);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    issueDialogBg.setVisibility(View.GONE);
                }
            }
        };

        textDetailContent.setText("设备转租");
        textDetailRight.setVisibility(View.GONE);
        issueSubleaseRecyclerview.setItemAnimator(new DefaultItemAnimator());
        issueSubleaseRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }


    public void getData() {
        mApi.CanRentEquipmentListForUser(GlobalParams.sToken, GlobalParams.sUserId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        issueListBeans.clear();
                        issueListBeans.addAll(JSONObject.parseArray(s, IssueListBean.class));
                        mAdapter = new IssueSubleaseListAdapter(IssueSubleaseListActivity.this, issueListBeans);
                        issueSubleaseRecyclerview.setAdapter(mAdapter);

                        mAdapter.setOnItemClickListener(new IssueSubleaseListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, ArrayList<IssueListBean> issueListBeans) {

                                Intent intent = new Intent(IssueSubleaseListActivity.this, IssueSubleaseDetailActivity.class);
                                intent.putExtra("ShebeiId", issueListBeans.get(position).getId());
                                startActivity(intent);
                            }
                        });

                        mAdapter.setOnItemButtonClickListener(new IssueSubleaseListAdapter.OnItemButtonClickListener() {
                            @Override
                            public void onItemButtonClickListener(final int positions, final ArrayList<IssueListBean> issueListBeans) {
                                handleBlur(issueDialogBg, mHandler);//处理背景图

                                String[] list = {"挂牌转租", "点对点转租"};
                                BaseDialog(IssueSubleaseListActivity.this, issueDialogBg, list,
                                        "", "", mHandler, new OnItemClickListener() {
                                            @Override
                                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                                if (data.get(position).getTitle().equals("挂牌转租")) {
                                                    Intent intent = new Intent(mContext, ListingIssueActivity.class);
                                                    intent.putExtra("issueListBean", issueListBeans.get(positions));
                                                    mContext.startActivity(intent);
                                                } else if (data.get(position).getTitle().equals("点对点转租")) {
                                                    Intent intent = new Intent(mContext, PointIssueActivity.class);
                                                    intent.putExtra("issueListBean", issueListBeans.get(positions));
                                                    mContext.startActivity(intent);
                                                }
                                            }
                                        });
                            }
                        });
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

}
