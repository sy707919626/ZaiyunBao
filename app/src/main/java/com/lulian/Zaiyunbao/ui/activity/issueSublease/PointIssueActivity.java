package com.lulian.Zaiyunbao.ui.activity.issueSublease;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.Bean.IssueListBean;
import com.lulian.Zaiyunbao.Bean.LeasePriceFromBean;
import com.lulian.Zaiyunbao.Bean.SaleEntity;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.ProjectUtil;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.alibaba.fastjson.JSON.parseArray;

/**
 * Created by Administrator on 2018/11/26.
 */

@SuppressLint("Registered")
public class PointIssueActivity extends BaseActivity {

    private static final int PICK_CONTACT = 1;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 2;
    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.point_issue_name_text)
    TextView pointIssueNameText;
    @BindView(R.id.point_issue_name)
    TextView pointIssueName;
    @BindView(R.id.point_can_sum)
    TextView pointCanSum;
    @BindView(R.id.point_issue_phone_text)
    TextView pointIssuePhoneText;
    @BindView(R.id.point_issue_phone)
    ClearEditText pointIssuePhone;
    @BindView(R.id.point_issue_mailList)
    ImageView pointIssueMailList;
    @BindView(R.id.point_sum)
    ClearEditText pointSum;
    @BindView(R.id.point_ZLmodle_text)
    TextView pointZLmodleText;
    @BindView(R.id.point_ZLmodle)
    TextView pointZLmodle;
    @BindView(R.id.point_SHmodle_text)
    TextView pointSHmodleText;
    @BindView(R.id.point_SHMode)
    TextView pointSHMode;
    @BindView(R.id.point_address_area_text)
    TextView pointAddressAreaText;
    @BindView(R.id.point_address_area)
    ClearEditText pointAddressArea;
    @BindView(R.id.point_address_name)
    ClearEditText pointAddressName;
    @BindView(R.id.point_issue_btn)
    Button pointIssueBtn;
    @BindView(R.id.issue_dialog_bg)
    ImageView issueDialogBg;
    DecimalFormat decimalFormat = new DecimalFormat(".00");
    private IssueListBean issueListBean;
    private Handler mHandler;
    private Intent mIntent;
    private String UserId;
    private String UserType;
    private String UserName;
    private float ZuJin;
    private float YaJin;
    private float JiaGe;
    private float YongJin;
    private List<LeasePriceFromBean> list = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.activity_point_issue;
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

        textDetailContent.setText("点对点转租");
        textDetailRight.setVisibility(View.GONE);
        issueListBean = (IssueListBean) getIntent().getSerializableExtra("issueListBean");
        pointCanSum.setText(issueListBean.getQuantity() + "个/片");
        getYajin();
        initView();
    }

    //获取押金
    private void getYajin() {
//        mApi.rentPriceListPoint(GlobalParams.sToken, issueListBean.getId(), issueListBean.getOperator(), 1,
//                issueListBean.getQuantity())
//                .compose(RxHttpResponseCompat.<String>compatResult())
//                .subscribe(new ErrorHandlerSubscriber<String>() {
//                    @Override
//                    public void onNext(String s) {
//                        list = parseArray(s, LeasePriceFromBean.class);
//                        if (list.size() > 0) {
//
//                            ZuJin = Float.valueOf(decimalFormat.format(list.get(0).getAllAmount() -
//                                    (list.get(0).getFreeDay() * list.get(0).getPrice()) - list.get(0).getDiscountAmount()));
//                            YaJin = list.get(0).getDeposit();
//                            YongJin = list.get(0).getCommisionValue();
//                            if (ZuJin <= 0) {
//                                ZuJin = 0;
//                            }
//                        }
//                    }
//                });


        mApi.rentPriceList(GlobalParams.sToken, issueListBean.getId(), issueListBean.getOperator(), 1,
                issueListBean.getQuantity(), 0)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .compose(this.<String>bindUntilEvent(ActivityEvent.STOP))
                .compose(this.<String>bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        List<LeasePriceFromBean> list = parseArray(s, LeasePriceFromBean.class);
                        if (list.size() > 0) {
                            DecimalFormat decimalFormat = new DecimalFormat("0.00");
                            float freeDayMoney = list.get(0).getPrice() * Float.valueOf(list.get(0).getFreeDay());
                            ZuJin = list.get(0).getAllAmount() - freeDayMoney - list.get(0).getDiscountAmount();

                            if (ZuJin <= 0f) {
                                ZuJin = 0;
                            }
                            YongJin = list.get(0).getCommisionValue();
                            YaJin = list.get(0).getDepositAmount();
                            JiaGe = list.get(0).getPrice();

                        } else {
                            YaJin = 0;
                        }
                    }
                });

    }


    private void initView() {

        pointIssueName.setText(issueListBean.getEquipmentName());

        pointZLmodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBlur(issueDialogBg, mHandler);

                String[] list = GlobalParams.ZLTypeList.toArray(new String[GlobalParams.ZLTypeList.size()]);

                BaseDialog(PointIssueActivity.this, issueDialogBg, list,
                        pointZLmodle.getText().toString(), "租赁模式", mHandler, new OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                pointZLmodle.setText(data.get(position).getTitle());
                            }
                        });

            }
        });

        pointSHMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            handleBlur(issueDialogBg, mHandler);
            String[] list = GlobalParams.FHTypeList.toArray(new String[GlobalParams.FHTypeList.size()]);
            BaseDialog(PointIssueActivity.this, issueDialogBg, list,
                    pointSHMode.getText().toString(), "送货方式", mHandler, new OnItemClickListener() {
                        @Override
                        public void onItemClickListener(int position, List<SaleEntity> data) {
                            pointSHMode.setText(data.get(position).getTitle());

                            if (data.get(position).getTitle().equals("送货上门")) {
                                pointAddressAreaText.setText("送货地址：");
                            } else if (data.get(position).getTitle().equals("用户自提")) {
                                pointAddressAreaText.setText("提货地址：");
                            }
                        }
                    });
            }
        });

        pointIssueMailList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });


        pointIssuePhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("") && s.toString().length() == 11) {
                    if (s.toString().trim().equals(GlobalParams.sUserPhone)) {
                        RxToast.warning("设备无法转租给当前用户");

                    } else if (ProjectUtil.isMobileNO(s.toString().trim())) {
                        isPhone(s.toString().trim());

                    } else {
                        RxToast.warning("请输入正确的手机号码");
                    }
                }

            }
        });

        pointAddressArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAddress(pointAddressArea.getText().toString().trim());
            }
        });

        pointIssueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(pointIssuePhone.getText().toString().trim())) {
                    RxToast.warning("请输入手机号码");
                } else if (TextUtils.isEmpty(pointSum.getText().toString().trim())) {
                    RxToast.warning("请输入转租数量");
                } else if (Integer.valueOf(pointSum.getText().toString().trim()) > issueListBean.getQuantity()) {
                    RxToast.warning("转租数量不能大于可转租数量");
                } else if (TextUtils.isEmpty(pointZLmodle.getText().toString().trim())) {
                    RxToast.warning("请选择租赁模式");
                } else if (TextUtils.isEmpty(pointSHMode.getText().toString().trim())) {
                    RxToast.warning("请选择送货方式");
                } else {

                    JSONObject obj = new JSONObject();
                    obj.put("ETypeId", issueListBean.getId());
                    obj.put("ETypeName", issueListBean.getEquipmentName()); //设备名称
                    obj.put("FormType", 3); //1,求租,2租赁,3转租

                    obj.put("Count", Integer.valueOf(pointSum.getText().toString().trim()));

                    obj.put("CreateUserType", Integer.valueOf(GlobalParams.sUserType)); //租出方用户类型
                    obj.put("CreateId", GlobalParams.sUserId); //租出方用户ID
                    obj.put("ReceiverName", GlobalParams.sUserName);//租出方名字
                    obj.put("ReceiverPhone", GlobalParams.sUserPhone);//租出方电话
                    obj.put("ReceiveUserId", GlobalParams.sUserId);//租出方ID


                    if (pointSHMode.getText().toString().trim().equals("送货上门")) {
                        obj.put("TransferWay", 1);
                        obj.put("ReceiveWay", 1);//送货方式
                    } else if (pointSHMode.getText().toString().trim().equals("租户自提")) {
                        obj.put("TransferWay", 2);
                        obj.put("ReceiveWay", 2);
                    }

                    obj.put("RentDates", "");
                    obj.put("Status", 2);
                    obj.put("Remark", "");


                    if (pointZLmodle.getText().toString().trim().equals("分时租赁")) {
                        obj.put("ZulinModel", 1);
                    } else if (pointZLmodle.getText().toString().trim().equals("分次租赁")) {
                        obj.put("ZulinModel", 2);
                    }

                    obj.put("TakeAddress", pointAddressArea.getText().toString() + pointAddressName.getText().toString());  //收货地点
                    obj.put("Price", JiaGe);
                    obj.put("RentAmount", ZuJin); //租金
                    obj.put("Deposit", YaJin); //押金

                    obj.put("ReceiveUserType", UserType); //租入方用户类型 1=加盟商 2=客户 3=经纪人
                    obj.put("ContactName", UserName); //租入方名字
                    obj.put("RendInUserId", UserId);//租入方Id
                    obj.put("ContactPhone", pointIssuePhone.getText().toString().trim());//租入方电话

                    obj.put("Release", ""); //发布人
                    obj.put("ReleasePhone", ""); //发布人电话

                    obj.put("Commision", YongJin); //佣金
                    String lease = obj.toString();
                    final RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                            lease);

                    if (!isFastClick()) {
                        mApi.PublishAttornRent(GlobalParams.sToken, GlobalParams.sUserId, issueListBean.getId(),
                                issueListBean.getQuantity(), Integer.valueOf(pointSum.getText().toString().trim()),
                                pointAddressArea.getText().toString() + pointAddressName.getText().toString())
                                .compose(RxHttpResponseCompat.<String>compatResult())

                                .subscribe(new ErrorHandlerSubscriber<String>() {
                                    @Override
                                    public void onNext(String s) {
                                        EquipmentRentSubmit(body);
                                    }
                                });
                    }
                }
            }
        });
    }

    //发布租赁
    private void EquipmentRentSubmit(RequestBody body){
        mApi.equipmentRentSubmit(GlobalParams.sToken, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        RxToast.success("点对点转租成功");
                        finish();
                    }
                });
    }

    //验证手机号码是否注册
    private void isPhone(final String Phone) {
        mApi.phoneIsExists(GlobalParams.sToken, Phone)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .compose(this.<String>bindUntilEvent(ActivityEvent.STOP))
                .compose(this.<String>bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        pointIssueBtn.setEnabled(false);

                        handleBlur(issueDialogBg, mHandler);
                        String content = "当前手机号码暂未注册账号，无法进行点对点转租，是否发送注册邀请？";

                        BaseDialogTitle(PointIssueActivity.this, issueDialogBg, content,
                                "提示", mHandler, new OnClickDialogListener() {
                                    @Override
                                    public void onClickLDialogistener() {
                                        RxToast.success("同意发送！");
                                    }
                                });

                    }

                    @Override
                    public void onError(Throwable t) {

                        pointIssueBtn.setEnabled(true);
                        //通过手机号码获取用户信息
                        mApi.getUserInfo(GlobalParams.sToken, Phone)
                                .compose(RxHttpResponseCompat.<String>compatResult())
                                .subscribe(new ErrorHandlerSubscriber<String>() {
                                    @Override
                                    public void onNext(String s) {
                                        UserId = JSONObject.parseObject(s).getString("UserId");
                                        UserName = JSONObject.parseObject(s).getString("Name");
                                        UserType = JSONObject.parseObject(s).getString("UserType");
                                    }
                                });
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_CONTACT:
                mIntent = data;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    //申请授权，第一个参数为要申请用户授权的权限；第二个参数为requestCode 必须大于等于0，主要用于回调的时候检测，匹配特定的onRequestPermissionsResult。
                    //可以从方法名requestPermissions以及第二个参数看出，是支持一次性申请多个权限的，系统会通过对话框逐一询问用户是否授权。
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);

                } else {
                    //如果该版本低于6.0，或者该权限已被授予，它则可以继续读取联系人。
                    getContacts(data);
                }
                break;

            case ADDRESS_LOCATION:
                if (resultCode == 11 && data != null) {
                    pointAddressArea.setText(data.getStringExtra("addressAll"));
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户成功授予权限
                getContacts(mIntent);
            } else {
                RxToast.warning("你拒绝了此应用对读取联系人权限的申请");
            }
        }
    }

    private void getContacts(Intent data) {
        if (data == null) {
            return;
        }

        Uri contactData = data.getData();
        if (contactData == null) {
            return;
        }
        String name = "";
        String phoneNumber = "";

        Uri contactUri = data.getData();
        Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
        if (cursor.moveToFirst()) {
            name = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String hasPhone = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            String id = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            if (hasPhone.equalsIgnoreCase("1")) {
                hasPhone = "true";
            } else {
                hasPhone = "false";
            }
            if (Boolean.parseBoolean(hasPhone)) {
                Cursor phones = getContentResolver()
                        .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + " = " + id, null, null);
                while (phones.moveToNext()) {
                    phoneNumber = phones
                            .getString(phones
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).trim();
                }
                phones.close();
            }
            cursor.close();

            pointIssuePhone.setText(phoneNumber.toString().replaceAll(" ", ""));

        }
    }
}
