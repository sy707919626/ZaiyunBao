package com.lulian.Zaiyunbao.ui.activity.wallet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
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
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.BankCode;
import com.lulian.Zaiyunbao.common.widget.ClearEditText;
import com.lulian.Zaiyunbao.common.widget.ProjectUtil;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import java.io.File;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2019/2/28.
 */

public class CashRechargeActivity extends BaseActivity implements InvokeListener, AdapterView.OnItemClickListener, TakePhoto.TakeResultListener{

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.cash_recharge_money)
    TextView cashRechargeMoney;
    @BindView(R.id.cash_money_sum)
    TextView cashMoneySum;
    @BindView(R.id.cash_recharge_bank_name)
    ClearEditText cashRechargeBankName;
    @BindView(R.id.cash_recharge_bank_no)
    ClearEditText cashRechargeBankNo;
    @BindView(R.id.cash_recharge_hz_image)
    ImageView cashRechargeHzImage;
    @BindView(R.id.cash_recharge_hz_photo)
    LinearLayout cashRechargeHzPhoto;
    @BindView(R.id.cash_recharge_btn_commit)
    Button cashRechargeBtnCommit;

    private float cashMoney;

    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    private String iconPath;
    private String ImageUrl = "";
    private String ImageId = "";
    @Override
    protected int setLayoutId() {
        return R.layout.activity_cash_recharge;
    }

    @Override
    protected void init() {
        textDetailContent.setText("转账支付");
        textDetailRight.setVisibility(View.GONE);

        cashMoney = getIntent().getFloatExtra("cashMoney", 0f);
        cashMoneySum.setText(String.valueOf(cashMoney));

    }


    @OnClick({R.id.cash_recharge_btn_commit,R.id.cash_recharge_hz_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cash_recharge_btn_commit:
                //下一步
                if (cashRechargeBankName.getText().toString().equals("")) {
                    RxToast.warning("请填写银行卡开户支行名称");
                } else if (cashRechargeBankNo.getText().toString().equals("")) {
                    RxToast.warning("请填写银行卡号");
                } else if (!BankCode.checkBankCard(cashRechargeBankNo.getText().toString())) {
                    RxToast.warning("请填写正确的银行卡号");
                } else if (ImageUrl.equals("")) {
                    RxToast.warning("请上传打款回执单照片");
                } else {
                    uploadData();
                }
                break;

            case R.id.cash_recharge_hz_photo:
                ProjectUtil.showUploadFileDialog(this, this);
                break;
            default:
                break;
        }
    }


    private void uploadData(){
        JSONObject obj = new JSONObject();
        obj.put("UserId", GlobalParams.sUserId);
        obj.put("AccountNo", cashRechargeBankNo.getText().toString().trim());
        obj.put("Number", "");
        obj.put("Balance", cashMoneySum.getText().toString().trim());
        obj.put("Remark", ImageUrl);
        obj.put("UserType", GlobalParams.sUserType);
        obj.put("objectid", ImageId);

        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                obj.toString());
        mApi.MyOutInFund(GlobalParams.sToken, body)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        startActivity(new Intent(CashRechargeActivity.this, CashRechargeResultActivity.class));
                        finish();
                    }
                });
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
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
        CropOptions cropOptions = new CropOptions.Builder().create();
        //设置压缩参数
        CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
        takePhoto.onEnableCompress(compressConfig, true);  //设置为需要压缩
        if (istake) {
            takePhoto.onPickFromCapture(getImageCropUri());
//            takePhoto.onPickFromCaptureWithCrop(getImageCropUri(), cropOptions);

        } else {
            takePhoto.onPickFromGallery();
//            takePhoto.onPickFromGalleryWithCrop(getImageCropUri(), cropOptions);
        }
    }

    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/temp/" + System.currentTimeMillis() + ".jpg");
//        ProjectUtil.show(this, file.getPath());

        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
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

        mApi.Uploadproof(GlobalParams.sToken, GlobalParams.sUserId, multipartBody, "MyInfo_OutInFund")
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        RxToast.success("成功");
                        JSONObject jsonObject = JSONObject.parseObject(s);
                        ImageUrl = jsonObject.getString("Path");
                        ImageId = jsonObject.getString("objectid");
                        cashRechargeHzImage.setVisibility(View.VISIBLE);
                        Glide.with(CashRechargeActivity.this).load(Constants.BASE_URL + ImageUrl).into(cashRechargeHzImage);
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
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

}


