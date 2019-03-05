package com.lulian.Zaiyunbao.ui.activity.repair;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.lulian.Zaiyunbao.Bean.DeteilListBean;
import com.lulian.Zaiyunbao.Bean.EquipmentInfoBean;
import com.lulian.Zaiyunbao.Bean.SaleEntity;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.ProjectUtil;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.ui.activity.PermissionsActivity;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/12/7.
 */

public class RepairReportActivity extends BaseActivity implements InvokeListener, AdapterView.OnItemClickListener, TakePhoto.TakeResultListener {


    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.clear_device_no)
    ClearEditText clearDeviceNo;
    @BindView(R.id.image_device_scan)
    ImageView imageDeviceScan;
    @BindView(R.id.lease_my_PriceList)
    RelativeLayout leaseMyPriceList;
    @BindView(R.id.device_repair_statu_text)
    TextView deviceRepairStatuText;
    @BindView(R.id.device_repair_statu)
    TextView deviceRepairStatu;
    @BindView(R.id.device_upload_image1)
    ImageView deviceUploadImage1;
    @BindView(R.id.device_layout_upload_image1)
    LinearLayout deviceLayoutUploadImage1;
    @BindView(R.id.device_upload_image2)
    ImageView deviceUploadImage2;
    @BindView(R.id.device_layout_upload_image2)
    LinearLayout deviceLayoutUploadImage2;
    @BindView(R.id.device_upload_image3)
    ImageView deviceUploadImage3;
    @BindView(R.id.device_layout_upload_image3)
    LinearLayout deviceLayoutUploadImage3;
    @BindView(R.id.paper_upload_idcard)
    LinearLayout paperUploadIdcard;
    @BindView(R.id.device_edit_remark)
    ClearEditText deviceEditRemark;
    @BindView(R.id.device_add_btn)
    ImageView deviceAddBtn;
    @BindView(R.id.device_entry_count)
    TextView deviceEntryCount;
    @BindView(R.id.device_entry_recycler)
    RecyclerView deviceEntryRecycler;
    @BindView(R.id.device_upload_btn)
    Button deviceUploadBtn;
    @BindView(R.id.dialog_bg)
    ImageView dialogBg;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private String iconPath;

    private int upLoadType;//1:图片1； 2:图片2； 3:图片3；

    private int repair;//1磨损2破裂3损毁

    private String Image1 = "";
    private String Image2 = "";
    private String Image3 = "";
    private int Count = 0;

    private RepairReportAdapter mAdapter;
    private EquipmentInfoBean equipmentInfoBean;

    private List<DeteilListBean> deteilListBeanList = new ArrayList<>();

    private Handler mHandler;
    private String StorehouseId; //仓库ID  取第一个设备
    private String MemberID; //加盟商ID  取第一个设备
    private String ETypeId;
    private String ETypeName;
    private String EquipmentId;

    private boolean isFirst;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_device_repair_upload;
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

        textDetailContent.setText("维修上报");
        textDetailRight.setText("查看列表");

        dialogBg.setImageAlpha(0);
        dialogBg.setVisibility(View.GONE);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    dialogBg.setVisibility(View.GONE);
                }
            }
        };

        mAdapter = new RepairReportAdapter(RepairReportActivity.this, deteilListBeanList);
        deviceEntryRecycler.setItemAnimator(new DefaultItemAnimator());
        deviceEntryRecycler.setLayoutManager(new LinearLayoutManager(this));


        deviceRepairStatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBlur(dialogBg, mHandler);
                String[] list = {"磨损", "破裂", "损毁"};

                BaseDialog(RepairReportActivity.this, dialogBg, list,
                        deviceRepairStatu.getText().toString(), "设备状况", mHandler, new OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                deviceRepairStatu.setText(data.get(position).getTitle());
                                if (data.get(position).getTitle().equals("磨损")) {
                                    repair = 1;
                                } else if (data.get(position).getTitle().equals("破裂")) {
                                    repair = 2;
                                } else if (data.get(position).getTitle().equals("损毁")) {
                                    repair = 3;
                                }
                            }
                        });

            }
        });

        deviceAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //继续添加
                if (clearDeviceNo.getText().toString().trim().equals("")) {
                    RxToast.warning("请输入设备编号");
                } else if (Image1.equals("") || Image2.equals("") || Image3.equals("")) {
                    RxToast.warning("请上传设备实景图片");
                } else {
                    List<String> Images = new ArrayList<>();
                    Images.add(Image1);
                    Images.add(Image2);
                    Images.add(Image3);

                    DeteilListBean deteilListBean = new DeteilListBean();

                    getDeviceInfo(clearDeviceNo.getText().toString().trim(), Images, deteilListBean);

                }
            }
        });
    }

    @OnClick({R.id.text_detail_right, R.id.image_device_scan, R.id.device_layout_upload_image1,
            R.id.device_layout_upload_image2, R.id.device_layout_upload_image3, R.id.device_upload_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_detail_right:
                //查看列表
                startActivity(new Intent(this, RepairOrderActivity.class));
                break;

            case R.id.image_device_scan:
                //二维码
                scanCode();
                break;

            case R.id.device_layout_upload_image1:
                upLoadType = 1;
                ProjectUtil.showUploadFileDialog(this, this);
                break;

            case R.id.device_layout_upload_image2:
                upLoadType = 2;
                ProjectUtil.showUploadFileDialog(this, this);
                break;

            case R.id.device_layout_upload_image3:
                upLoadType = 3;
                ProjectUtil.showUploadFileDialog(this, this);
                break;

            case R.id.device_upload_btn:
                 if (deviceEntryCount.getText().toString().equals("0") ){
                    RxToast.warning("请先添加设备");
                 } else {
                    //上传
                    JSONObject obj = new JSONObject();
                    obj.put("Count", deteilListBeanList.size());
                    obj.put("UserId", GlobalParams.sUserId);
                    obj.put("deteilList", deteilListBeanList);
                    obj.put("MemberID", MemberID);
                    obj.put("StorehouseId", StorehouseId);
                    obj.put("Name", GlobalParams.sUserName);
                    obj.put("ETypeId", ETypeId);
                    obj.put("ETypeName", ETypeName);
                    obj.put("EquipmentId", EquipmentId);
                    obj.put("Remark", deviceEditRemark.getText().toString().trim());

                    String messages = obj.toString();
                    RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                            messages);

                    mApi.AddObsoleteForm(GlobalParams.sToken, body)
                            .compose(RxHttpResponseCompat.<String>compatResult())
                            .subscribe(new ErrorHandlerSubscriber<String>() {

                                @Override
                                public void onNext(String s) {
                                    RxToast.success("上传成功");
                                    deteilListBeanList.clear();
                                    mAdapter.notifyDataSetChanged();
                                }
                            });
                }
                break;

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getTakePhoto().onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        getTakePhoto().onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) { //二维码扫描
            Toast.makeText(this, "没有权限无法扫描呦", Toast.LENGTH_LONG).show();
        } else if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                clearDeviceNo.setText(content);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                initPhotoData(true);
                break;
            case 1:
                initPhotoData(false);
                break;
        }
    }

    private void initPhotoData(boolean istake) {
        ////获取TakePhoto实例
        takePhoto = getTakePhoto();
        //设置裁剪参数
        CropOptions cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
        //设置压缩参数
        CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
        takePhoto.onEnableCompress(compressConfig, true);  //设置为需要压缩
        if (istake) {
            takePhoto.onPickFromCaptureWithCrop(getImageCropUri(), cropOptions);
        } else {
            takePhoto.onPickFromGalleryWithCrop(getImageCropUri(), cropOptions);
        }
    }

    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/temp/" + System.currentTimeMillis() + ".jpg");

        ProjectUtil.show(this, file.getPath());

        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    /**
     * 获取TakePhoto实例
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void takeSuccess(TResult result) {
        iconPath = result.getImage().getOriginalPath();

        File imgFile = new File(iconPath);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imgFile);
        builder.addFormDataPart("file", imgFile.getName(), requestBody);
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();

        mApi.UploadFile(GlobalParams.sToken, multipartBody, "Bussiness_RepairFormDetail")
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        RxToast.success("成功");
                        JSONObject jsonObject = JSONObject.parseObject(s);
                        if (upLoadType == 1) {
                            Image1 = jsonObject.getString("Path");
                            deviceUploadImage1.setVisibility(View.VISIBLE);
                            Glide.with(RepairReportActivity.this).load(Constants.BASE_URL + Image1).into(deviceUploadImage1);

                        } else if (upLoadType == 2) {
                            Image2 = jsonObject.getString("Path");
                            deviceUploadImage2.setVisibility(View.VISIBLE);
                            Glide.with(RepairReportActivity.this).load(Constants.BASE_URL + Image2).into(deviceUploadImage2);

                        } else if (upLoadType == 3) {
                            Image3 = jsonObject.getString("Path");
                            deviceUploadImage3.setVisibility(View.VISIBLE);
                            Glide.with(RepairReportActivity.this).load(Constants.BASE_URL + Image3).into(deviceUploadImage3);
                        }
                    }
                });
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void getDeviceInfo(final String ECodeNo, final List<String> Images, final DeteilListBean deteilListBean) {
        mApi.EquipmentInfoForNo(GlobalParams.sToken, ECodeNo)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                            equipmentInfoBean = JSONObject.parseObject(s, EquipmentInfoBean.class);
                            deteilListBean.setETypeId(equipmentInfoBean.getEquipmentId());
                            deteilListBean.setECode(ECodeNo);
                            deteilListBean.setETypeName(equipmentInfoBean.getEquipmentName());

                            deteilListBean.setRepairType(repair);
                            deteilListBean.setImgs(Images);

                            deteilListBeanList.add(deteilListBean);

                            if (!isFirst) {
                                StorehouseId = equipmentInfoBean.getStorehouseId();
                                MemberID = equipmentInfoBean.getMemberID();
                                ETypeId = equipmentInfoBean.getTypeId();
                                ETypeName = equipmentInfoBean.getTypeName();
                                EquipmentId = equipmentInfoBean.getEquipmentId();
                            }

                            isFirst = true;

                            if (mAdapter != null) {
                                mAdapter.notifyDataSetChanged();
                            }
                            deviceEntryRecycler.setAdapter(mAdapter);

                            //清空设备信息
                            clearDeviceNo.setText("");
                            deviceEditRemark.setText("");

                            deviceEntryCount.setText(deteilListBeanList.size() + "");

                            Image1 = "";
                            Image2 = "";
                            Image3 = "";
                            deviceUploadImage1.setVisibility(View.GONE);
                            deviceUploadImage2.setVisibility(View.GONE);
                            deviceUploadImage3.setVisibility(View.GONE);
                            clearDeviceNo.setFocusable(true);
                    }

                    @Override
                    public void onError(Throwable t) {
                        RxToast.warning("请输入正确的设备编号");
                    }
                });
    }
}
