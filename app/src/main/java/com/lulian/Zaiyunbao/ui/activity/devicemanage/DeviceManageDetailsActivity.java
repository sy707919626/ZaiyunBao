package com.lulian.Zaiyunbao.ui.activity.devicemanage;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.DeviceDetailsBean;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/11/24.
 */

public class DeviceManageDetailsActivity extends BaseActivity {
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.order_text)
    TextView orderText;
    @BindView(R.id.device_details_no)
    TextView deviceDetailsNo;
    @BindView(R.id.device_details_state)
    TextView deviceDetailsState;
    @BindView(R.id.device_details_img_photo)
    ImageView deviceDetailsImgPhoto;
    @BindView(R.id.device_details_name)
    TextView deviceDetailsName;
    @BindView(R.id.device_details_spec)
    TextView deviceDetailsSpec;
    @BindView(R.id.device_details_price)
    TextView deviceDetailsPrice;
    @BindView(R.id.device_details_remark)
    TextView deviceDetailsRemark;
    @BindView(R.id.device_details_update_state)
    Button deviceDetailsUpdateState;

    private String deviceId;
    private DeviceDetailsBean deviceDetailsBean;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_device_manage_details;
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

        textDetailContent.setText("设备详情");

        textDetailRight.setVisibility(View.GONE);
        deviceId = getIntent().getStringExtra("ECode");
        getData();
    }

    private void getData() {
        mApi.MyEquipmentDetail(GlobalParams.sToken, deviceId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
//                        deviceDetailsBean = MyApplication.get().getAppComponent().getGson().fromJson(s, DeviceDetailsBean.class);
                        deviceDetailsBean = JSONObject.parseObject(s, DeviceDetailsBean.class);
                        initView();
                    }
                });
    }

    private void initView() {
        deviceDetailsNo.setText(deviceDetailsBean.getECode());

        //使用状态：6=闲置 3=占用  报修 = 4 报废 = 5
        if (deviceDetailsBean.getUseStatus() == 6) {
            deviceDetailsState.setText("闲置");
            deviceDetailsUpdateState.setVisibility(View.VISIBLE);
            deviceDetailsUpdateState.setText("占用");

        } else if (deviceDetailsBean.getUseStatus() == 3) {
            deviceDetailsState.setText("占用");
            deviceDetailsUpdateState.setVisibility(View.VISIBLE);
            deviceDetailsUpdateState.setText("闲置");

        } else if (deviceDetailsBean.getUseStatus() == 4) {
            deviceDetailsState.setText("报修");
            deviceDetailsUpdateState.setVisibility(View.GONE);

        } else if (deviceDetailsBean.getUseStatus() == 5) {
            deviceDetailsState.setText("报废");
            deviceDetailsUpdateState.setVisibility(View.GONE);
        }


//        try {
//            byte[] bitmapArray;
//            bitmapArray = Base64.decode(deviceDetailsBean.getPicture(), Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
//                    bitmapArray.length);
//            deviceDetailsImgPhoto.setImageBitmap(bitmap);
//        } catch (Exception e) {
//        }

        Glide.with(mContext).load(Constants.BASE_URL +"/" + deviceDetailsBean.getPicture()).into(deviceDetailsImgPhoto);

        deviceDetailsName.setText(deviceDetailsBean.getEquipmentName());
        deviceDetailsSpec.setText(deviceDetailsBean.getNorm());
        deviceDetailsUpdateState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用状态：6=闲置 3=载物
                if (deviceDetailsBean.getUseStatus() == 6) {
                    showDialog("确认是否占用？", 6, deviceDetailsBean.getId());

                } else if (deviceDetailsBean.getUseStatus() == 3) {
                    showDialog("确认是否闲置？", 3, deviceDetailsBean.getId());
                }
            }
        });
    }

    //使用状态6=闲置 3=载物
    private void MyEquipmentOpt(final String Ids, final int state) {
        final String[] arr = {Ids};

        mApi.MyEquipmentOpt(GlobalParams.sToken, arr, state)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        RxToast.success("操作成功");

                        Constants.isAutoRefresh = true;
                        finish();
                    }
                });
    }


    private void showDialog(String message, final int state, final String Ids) {
        final AlertDialog builder = new AlertDialog.Builder(mContext)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = builder.findViewById(R.id.tv_msg);
        Button cancle = builder.findViewById(R.id.btn_cancle);
        Button sure = builder.findViewById(R.id.btn_sure);

        if (msg == null || cancle == null || sure == null) return;

        msg.setText(message);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == 6) {
                    MyEquipmentOpt(Ids, 3);

                } else if (state == 3) {
                    MyEquipmentOpt(Ids, 6);
                }
                builder.dismiss();
            }
        });
    }
}
