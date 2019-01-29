package com.lulian.Zaiyunbao.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.event.LeaseEvent;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by Administrator on 2018/11/1.
 */

public class LeaseMyEquipmentSeeActivity extends BaseActivity {
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.leaseDetails_img_photo)
    ImageView leaseDetailsImgPhoto;
    @BindView(R.id.leaseDetails_shebei_name)
    TextView leaseDetailsShebeiName;
    @BindView(R.id.leaseDetails_shebei_spec)
    TextView leaseDetailsShebeiSpec;
    @BindView(R.id.leaseDetails_shebei_load)
    TextView leaseDetailsShebeiLoad;
    @BindView(R.id.lease_orderno_text)
    TextView leaseOrdernoText;
    @BindView(R.id.lease_orderno)
    TextView leaseOrderno;
    @BindView(R.id.lease_Rent_Modles)
    TextView leaseRentModles;
    @BindView(R.id.lease_sum)
    TextView leaseSum;
    @BindView(R.id.lease_price_DJ)
    TextView leasePriceDJ;
    @BindView(R.id.lease_Term)
    TextView leaseTerm;
    @BindView(R.id.lease_FreeDays)
    TextView leaseFreeDays;
    @BindView(R.id.lease_Rent)
    TextView leaseRent;
    @BindView(R.id.lease_Deposit)
    TextView leaseDeposit;
    @BindView(R.id.lease_Discount)
    TextView leaseDiscount;
    @BindView(R.id.lease_Delivery_Modle)
    TextView leaseDeliveryModle;
    @BindView(R.id.lease_delivery_time)
    TextView leaseDeliveryTime;
    @BindView(R.id.lease_Consignee)
    TextView leaseConsignee;
    @BindView(R.id.lease_Collect_Address)
    TextView leaseCollectAddress;
    @BindView(R.id.lease_Contacts)
    TextView leaseContacts;
    @BindView(R.id.lease_contact_phone)
    TextView leaseContactPhone;
    @BindView(R.id.lease_Recommend)
    TextView leaseRecommend;
    @BindView(R.id.lease_cancel)
    Button leaseCancel;
    @BindView(R.id.lease_Sure)
    Button leaseSure;

    private String Id = "";

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        TextView msg = new TextView(context);
        msg.setText(message);
        msg.setPadding(10, 40, 10, 0);
        msg.setGravity(Gravity.CENTER);
        msg.setTextSize(18);

        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);

        builder.setView(msg);
        return builder;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.lease_my_equipment_see;
    }

    @Override
    protected void init() {
        stepActivities.add(this);
        ImmersionBar.with(this)
                .titleBar(R.id.detail_bar_title)
                .titleBarMarginTop(R.id.detail_bar_title)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        Id = getIntent().getStringExtra("Id"); //设备ID
        textDetailContent.setText("确认订单");
        textDetailRight.setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        leaseDetailsShebeiName.setText(getIntent().getStringExtra("MyEquipmentName"));
        leaseDetailsShebeiSpec.setText(getIntent().getStringExtra("MyNorm"));
        leaseDetailsShebeiLoad.setText(getIntent().getStringExtra("myLoad"));

        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(getIntent().getStringExtra("MyEquipmentImage"), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
            leaseDetailsImgPhoto.setImageBitmap(bitmap);
        } catch (Exception e) {
        }

        leaseRentModles.setText(getIntent().getStringExtra("MyBalanceMode"));
        leaseSum.setText(String.valueOf(getIntent().getIntExtra("leaseSum", 0)) + "片"); //租赁数量
        leasePriceDJ.setText(getIntent().getStringExtra("MyPValue") + "元/天/片"); //单价
        leaseTerm.setText(String.valueOf(getIntent().getIntExtra("Datasums", 0)) + "天"); //租期

        leaseFreeDays.setText(getIntent().getIntExtra("MianzuQI", 0) + "天");

        leaseRent.setText(getIntent().getStringExtra("MyRent") + "元"); //租金
        leaseDeposit.setText(getIntent().getStringExtra("MyDeposit") + "元"); //押金
        leaseDiscount.setText(getIntent().getStringExtra("DiscountAmount") + "元"); //折扣减去
        leaseDeliveryModle.setText(getIntent().getStringExtra("deliveryModes")); //
        leaseDeliveryTime.setText(getIntent().getStringExtra("leaseMyDeliveryTime"));
        leaseConsignee.setText(getIntent().getStringExtra("leaseMyName"));
        leaseCollectAddress.setText(getIntent().getStringExtra("leaseMyAddress"));
        leaseContacts.setText(getIntent().getStringExtra("leaseMyName"));
        leaseContactPhone.setText(getIntent().getStringExtra("leaseMyPhone"));
        leaseRecommend.setText(getIntent().getStringExtra("leaseMyPhoneNumble")
                + getIntent().getStringExtra("leaseMyRefereeName"));

    }

    @OnClick({R.id.lease_cancel, R.id.lease_Sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lease_cancel:
                getConfirmDialog(this, "是否确认取消订单？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
                break;

            case R.id.lease_Sure:
                JSONObject obj = new JSONObject();
                obj.put("ETypeId", Id);
                obj.put("ETypeName", leaseDetailsShebeiName.getText().toString().trim()); //设备名称

                //加盟商ID与使用者ID相同  则此订单为转租单
                if (getIntent().getStringExtra("UID").length() > 0) {
                    obj.put("UID", getIntent().getStringExtra("UID"));

                    if (getIntent().getStringExtra("UID").equals(getIntent().getStringExtra("OperatorId"))) {
                        obj.put("FormType", 2); //1,求租,2租赁,3转租

                    } else {
                        obj.put("FormType", 3);
//                        if (getIntent().getStringExtra("UID").equals(GlobalParams.sUserId)) {
//                            obj.put("FormType", 3); //1,求租,2租赁,3转租
//                        } else {
//                            obj.put("FormType", 2); //1,求租,2租赁,3转租
//                        }

                    }
//                    obj.put("FormType", 3); //1,求租,2租赁,3转租

                } else {
                    obj.put("FormType", 2); //1,求租,2租赁,3转租
                }


                obj.put("TargetDeliveryTime", getIntent().getStringExtra("MyStartTime")); //租赁开始时间
                obj.put("ReturnBakcTime", getIntent().getStringExtra("MyEndTime")); //退租日期(归还时间)
                obj.put("ArrivalTime", "");

                obj.put("Price", Float.valueOf(getIntent().getStringExtra("MyPValue")));
                obj.put("Count", getIntent().getIntExtra("leaseSum", 0));
                obj.put("RentAmount", Float.valueOf(getIntent().getStringExtra("MyRent"))); //租金
                obj.put("CreateUserType", Integer.valueOf(GlobalParams.sUserType)); //下单用户类型 1=加盟商 2=客户 3=经纪人
                obj.put("CreateId", GlobalParams.sUserId); //创建用户ID
                obj.put("FreeDates", getIntent().getIntExtra("mainzuDay", 0)); //免租期

                obj.put("ReceiveUserType", getIntent().getIntExtra("UserType", 0)); //接单用户类型 1=加盟商 2=客户 3=经纪人
                obj.put("ReceiveUserId", getIntent().getStringExtra("OperatorId"));//接单用户ID

                if (getIntent().getStringExtra("deliveryModes").equals("送货上门")) {
                    obj.put("TransferWay", 1);
                } else if (getIntent().getStringExtra("deliveryModes").equals("租户自提")) {
                    obj.put("TransferWay", 2);
                } else {
                    obj.put("TransferWay", 3);
                }


                obj.put("RentDates", "");
                obj.put("Status", 1);
                obj.put("Remark", "");

                if (getIntent().getStringExtra("MyModle").equals("分时租赁")) {
                    obj.put("ZulinModel", 1);
                } else {
                    obj.put("ZulinModel", 2);
                }

                obj.put("TakeAddress", getIntent().getStringExtra("leaseMyAddress"));  //收货地点
//                obj.put("AlianceLink", "");
//                obj.put("AlianceLinkPhone", "");
                obj.put("Deposit", Float.valueOf(getIntent().getStringExtra("MyDeposit"))); //押金
                obj.put("ContactName", getIntent().getStringExtra("leaseMyName")); //租入方名字
                obj.put("ContactPhone", getIntent().getStringExtra("leaseMyPhone"));//租入方电话

                obj.put("Recommend", getIntent().getStringExtra("leaseMyRefereeName")); //推荐人
                obj.put("RecommendPhone", getIntent().getStringExtra("leaseMyPhoneNumble")); //推荐人电话

                obj.put("Release", ""); //发布人
                obj.put("ReleasePhone", ""); //发布人电话
                obj.put("StoreName", getIntent().getStringExtra("StoreName")); //仓库名
                obj.put("StoreId", getIntent().getStringExtra("StoreId")); //仓库Id\

                if (TextUtils.isEmpty(getIntent().getStringExtra("UID"))) {
                    obj.put("ReceiveUserId", getIntent().getStringExtra("OperatorId")); //供应商ID
                } else {
                    obj.put("ReceiveUserId", getIntent().getStringExtra("UID"));
                }

                obj.put("ReceiveWay", Integer.valueOf(getIntent().getStringExtra("deliveryModesCode")));//送货方式
                obj.put("HandRentWay", Integer.valueOf(getIntent().getStringExtra("MyBalanceModeCode")));//结算方式


                String lease = obj.toString();
                RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                        lease);

                if (!isFastClick()) {
                    mApi.equipmentRentSubmit(GlobalParams.sToken, body)
                            .compose(RxHttpResponseCompat.<String>compatResult())
                            .subscribe(new ErrorHandlerSubscriber<String>() {
                                @Override
                                public void onNext(String s) {
                                    RxToast.success("租赁订单发布成功");
                                    EventBus.getDefault().post(new LeaseEvent());
                                    stepfinishAll();
                                }
                            });
                }

                break;
            default:
                break;
        }
    }
}