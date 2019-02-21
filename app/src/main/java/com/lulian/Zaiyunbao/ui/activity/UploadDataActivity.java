package com.lulian.Zaiyunbao.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.lulian.Zaiyunbao.Bean.SaleEntity;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.GlobalParams;
import com.lulian.Zaiyunbao.common.event.PayEvent;
import com.lulian.Zaiyunbao.common.rx.RxHttpResponseCompat;
import com.lulian.Zaiyunbao.common.rx.subscriber.ErrorHandlerSubscriber;
import com.lulian.Zaiyunbao.common.widget.DataCleanManager;
import com.lulian.Zaiyunbao.common.widget.IDCard;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.di.component.Constants;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;
import com.lulian.Zaiyunbao.ui.dialog.PhotoDialog;
import com.lulian.Zaiyunbao.ui.photoview.PhotoDisplayDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/9/19.
 */

public class UploadDataActivity extends BaseActivity implements InvokeListener, TakePhoto.TakeResultListener {
    @BindView(R.id.image_back_login_bar)
    ImageView imageBackLoginBar;
    @BindView(R.id.text_login_content)
    TextView textLoginContent;
    @BindView(R.id.agree_login)
    TextView agreeLogin;
    @BindView(R.id.login_bar_title)
    RelativeLayout loginBarTitle;
    @BindView(R.id.user_text)
    TextView userText;
    @BindView(R.id.userType_edit_name)
    TextView userTypeEditName;
    @BindView(R.id.user_data_into_image)
    ImageView userDataIntoImage;
    @BindView(R.id.upload_usertype_linear)
    RelativeLayout uploadUsertypeLinear;
    @BindView(R.id.user_name_text)
    TextView userNameText;
    @BindView(R.id.paperType_edit_username)
    EditText paperTypeEditUsername;
    @BindView(R.id.username_data_into_image)
    ImageView usernameDataIntoImage;
    @BindView(R.id.user_sex_text)
    TextView userSexText;
    @BindView(R.id.usersex_edit_name)
    TextView usersexEditName;
    @BindView(R.id.usersex_data_into_image)
    ImageView usersexDataIntoImage;
    @BindView(R.id.companyName_name_text)
    TextView companyNameNameText;
    @BindView(R.id.companyName_edit_name)
    EditText companyNameEditName;
    @BindView(R.id.companyName_data_into_image)
    ImageView companyNameDataIntoImage;
    @BindView(R.id.companyName_name_layout)
    RelativeLayout companyNameNameLayout;
    @BindView(R.id.user_zj_text)
    TextView userZjText;
    @BindView(R.id.paperType_edit_name)
    TextView paperTypeEditName;
    @BindView(R.id.userZJ_data_into_image)
    ImageView userZJDataIntoImage;
    @BindView(R.id.user_zj_layout)
    RelativeLayout userZjLayout;
    @BindView(R.id.user_ZJ_number_text)
    TextView userZJNumberText;
    @BindView(R.id.paper_edit_idcard)
    EditText paperEditIdcard;
    @BindView(R.id.user_ZJ_number_data_into_image)
    ImageView userZJNumberDataIntoImage;
    @BindView(R.id.paper_img_upload_idcard_image)
    ImageView paperImgUploadIdcardImage;
    @BindView(R.id.paper_img_upload_idcard)
    LinearLayout paperImgUploadIdcard;
    @BindView(R.id.paper_layout_upload_idcard)
    LinearLayout paperLayoutUploadIdcard;
    @BindView(R.id.paper_img_upload_idcard_back_image)
    ImageView paperImgUploadIdcardBackImage;
    @BindView(R.id.paper_img_upload_idcard_back)
    LinearLayout paperImgUploadIdcardBack;
    @BindView(R.id.paper_layout_upload_idcard_back)
    LinearLayout paperLayoutUploadIdcardBack;
    @BindView(R.id.register_btn)
    Button registerBtn;
    @BindView(R.id.iv_dialog_bg)
    ImageView ivDialogBg;
    @BindView(R.id.paper_img_upload_bizLicUrl_image)
    ImageView paperImgUploadBizLicUrlImage;
    @BindView(R.id.paper_img_upload_bizLicUrl)
    LinearLayout paperImgUploadBizLicUrl;
    @BindView(R.id.paper_layout_upload_bizLicUrl)
    LinearLayout paperLayoutUploadBizLicUrl;
    @BindView(R.id.paper_upload_bizLicUrl)
    LinearLayout paperUploadBizLicUrl;
    @BindView(R.id.photo_type_text)
    TextView photoTypeText;
    @BindView(R.id.paper_upload_idcard)
    LinearLayout paperUploadIdcard;


    private boolean isCompany = false;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private String iconPath;

    private int upLoadType;//0:身份证正面;1:身份证反面;2:营业执照

    private String IdFrontUrl = ""; //身份证正面
    private String IdBackUrl = ""; //身份证反面
    private String bizLicUrl = "";//营业执照

    private String userType;
    private String companyType;

    private String sex = "1";
    private int ActivityType = 0;

    private Handler mHandler;
    private PhotoDisplayDialog IdFrontUrlDialog;
    private PhotoDisplayDialog IdBackUrlDialog;
    private PhotoDisplayDialog bizLicUrlDialog;

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("是", onClickListener);
        builder.setNegativeButton("否", null);
        return builder;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_uploaddata;
    }

    @SuppressLint("NewApi")
    @Override
    protected void init() {
        stepActivities.add(this);
        ImmersionBar.with(this)
                .titleBar(R.id.login_bar_title)
                .titleBarMarginTop(R.id.login_bar_title)
                .navigationBarColorInt(Color.WHITE)
                .statusBarDarkFont(true, 0.5f)
                .navigationBarDarkIcon(true, 0.5f)
                .init();

        ivDialogBg.setImageAlpha(0);
        ivDialogBg.setVisibility(View.GONE);
        agreeLogin.setText("跳过");

        showPersonal();//初始布局为个人用户

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    ivDialogBg.setVisibility(View.GONE);
                }
            }
        };

        ActivityType = getIntent().getIntExtra("ActivityType", 0);

        IdFrontUrlDialog = new PhotoDisplayDialog(this, true);
        IdBackUrlDialog = new PhotoDisplayDialog(this, true);
        bizLicUrlDialog = new PhotoDisplayDialog(this, true);

        usernameDataIntoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paperTypeEditUsername.setText("");
            }
        });

        userZJNumberDataIntoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paperEditIdcard.setText("");
            }
        });

        companyNameDataIntoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyNameEditName.setText("");
            }
        });

        //注册完善资料
        if (ActivityType == 1) {
            textLoginContent.setText("填补资料");

            //个人中心完善资料
        } else if (ActivityType == 2) {
            textLoginContent.setText("个人资料");
            agreeLogin.setVisibility(View.GONE);
            uploadUsertypeLinear.setVisibility(View.GONE);

            isCompany = false;
            userType = "1";
            showPersonal();
            getuserInfo();

            //企业资料完善资料
        } else if (ActivityType == 3) {
            textLoginContent.setText("企业资料");
            agreeLogin.setVisibility(View.GONE);
            uploadUsertypeLinear.setVisibility(View.GONE);

            showCompany();
            isCompany = true;
            userType = "2";
        }

        agreeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getConfirmDialog(UploadDataActivity.this, "是否跳过完善资料？", new DialogInterface.OnClickListener
                        () {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        DataCleanManager.clearAllCache(getApplicationContext());
                        startActivity(new Intent(UploadDataActivity.this, MainActivity.class));
                        stepfinishAll();
                    }
                }).show();
            }
        });


        IdFrontUrlDialog.setCallback(new PhotoDisplayDialog.Callback() {
            @Override
            public void onShootAgainClicked() {
                upLoadType = 0;
                Intent intent = new Intent(UploadDataActivity.this, PhotoDialog.class);
                intent.putExtra("photoType", 1);
                startActivityForResult(intent, 1);
            }
        });

        IdBackUrlDialog.setCallback(new PhotoDisplayDialog.Callback() {
            @Override
            public void onShootAgainClicked() {
                upLoadType = 1;
                Intent intent2 = new Intent(UploadDataActivity.this, PhotoDialog.class);
                intent2.putExtra("photoType", 2);
                startActivityForResult(intent2, 1);
            }
        });

        bizLicUrlDialog.setCallback(new PhotoDisplayDialog.Callback() {
            @Override
            public void onShootAgainClicked() {
                upLoadType = 2;
                Intent intent3 = new Intent(UploadDataActivity.this, PhotoDialog.class);
                intent3.putExtra("photoType", 3);
                startActivityForResult(intent3, 1);
            }
        });
    }

    @SuppressLint("NewApi")
    @OnClick({R.id.register_btn, R.id.paper_layout_upload_idcard, R.id.paper_img_upload_idcard_image,
            R.id.paper_layout_upload_idcard_back, R.id.paper_layout_upload_bizLicUrl,
            R.id.paper_img_upload_idcard_back_image, R.id.paper_img_upload_bizLicUrl_image
            , R.id.upload_usertype_linear, R.id.user_zj_layout,
            R.id.usersex_edit_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.usersex_edit_name:
                handleBlur(ivDialogBg, mHandler);//处理背景图

                String[] list2 = {"男", "女"};
                BaseDialog(UploadDataActivity.this, ivDialogBg, list2,
                        usersexEditName.getText().toString(), "性别", mHandler, new OnItemClickListener() {

                            @Override
                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                usersexEditName.setText(data.get(position).getTitle());
                            }
                        });

                break;

            case R.id.upload_usertype_linear:
                handleBlur(ivDialogBg, mHandler);//处理背景图

                String[] list3 = {"个人用户", "企业用户"};
                BaseDialog(UploadDataActivity.this, ivDialogBg, list3,
                        userTypeEditName.getText().toString(), "用户类型", mHandler, new OnItemClickListener() {
                            @Override
                            public void onItemClickListener(int position, List<SaleEntity> data) {
                                userTypeEditName.setText(data.get(position).getTitle());
                                if (data.get(position).getTitle().equals("个人用户")) {
                                    showPersonal();
                                    userType = "1";
                                    isCompany = false;
                                } else {
                                    showCompany();
                                    userType = "2";
                                    isCompany = true;
                                }
                            }

                        });
                break;


            case R.id.user_zj_layout:
                handleBlur(ivDialogBg, mHandler);//处理背景图
                String title = "";

                if (ActivityType == 1) {
                    if (userTypeEditName.getText().toString().trim().equals("")) {
                        userType = "1";
                    }
                }
                if (userType.equals("1")) {
                    title = "证件类型";
                    String[] list4 = new String[]{"身份证", "驾驶证", "军官证"};

                    BaseDialog(UploadDataActivity.this, ivDialogBg, list4,
                            paperTypeEditName.getText().toString(), title, mHandler, new OnItemClickListener() {
                                @Override
                                public void onItemClickListener(int position, List<SaleEntity> data) {
                                    paperTypeEditName.setText(data.get(position).getTitle());
                                }
                            });

                } else if (userType.equals("2")) {

                    title = "企业类型";
                    String[] list5 = new String[]{"合资", "独资", "国有",
                            "私营", "全民所有制", "集体所有制", "股份制", "有限责任"};

                    BaseDialog(UploadDataActivity.this, ivDialogBg, list5,
                            paperTypeEditName.getText().toString(), title, mHandler, new OnItemClickListener() {
                                @Override
                                public void onItemClickListener(int position, List<SaleEntity> data) {
                                    paperTypeEditName.setText(data.get(position).getTitle());
                                }
                            });
                }
                break;
            case R.id.paper_layout_upload_idcard:
                //身份证照片上传（正面）
                upLoadType = 0;
                Intent intent = new Intent(UploadDataActivity.this, PhotoDialog.class);
                intent.putExtra("photoType", 1);
                startActivityForResult(intent, 1);
                break;

            case R.id.paper_img_upload_idcard_image:
                IdFrontUrlDialog.setImgUrl(Constants.BASE_URL + IdFrontUrl).show();
                break;

            case R.id.paper_layout_upload_idcard_back:
                //身份证照片上传（反面）
                upLoadType = 1;
                Intent intent2 = new Intent(UploadDataActivity.this, PhotoDialog.class);
                intent2.putExtra("photoType", 2);
                startActivityForResult(intent2, 1);
                break;

            case R.id.paper_img_upload_idcard_back_image:
                IdBackUrlDialog.setImgUrl(Constants.BASE_URL + IdBackUrl).show();
                break;

            case R.id.paper_layout_upload_bizLicUrl:
                //营业执照
                upLoadType = 2;
                Intent intent3 = new Intent(UploadDataActivity.this, PhotoDialog.class);
                intent3.putExtra("photoType", 3);
                startActivityForResult(intent3, 1);
                break;

            case R.id.paper_img_upload_bizLicUrl_image:
                bizLicUrlDialog.setImgUrl(Constants.BASE_URL + bizLicUrl).show();
                break;

            case R.id.register_btn: //注册
                if (isCompany) {
                    //公司类型
                    if (ActivityType == 3) {
                        userTypeEditName.setText("企业用户");
                    }

                    if (userTypeEditName.getText().toString().trim().equals("")) {
                        RxToast.warning("请选择用户类型");
                    } else if (paperTypeEditUsername.getText().toString().trim().equals("")) {
                        RxToast.warning("请输入姓名");
                    } else if (usersexEditName.getText().toString().trim().equals("")) {
                        RxToast.warning("请选择性别");
                    } else if (paperTypeEditName.getText().toString().trim().equals("")) {
                        RxToast.warning("请选择企业类型");
                    } else if (companyNameEditName.getText().toString().trim().equals("")) {
                        RxToast.warning("请输入企业名称");
                    } else if (paperEditIdcard.getText().toString().trim().equals("")) {
                        RxToast.warning("请输入机构代码");
                    } else if (bizLicUrl.equals("")) {
                        RxToast.warning("请上传营业执照");

                    } else {
                        //企业传入参数UserClass,UserId, OrgName,OrgType,OrgCode,LicUrl,Remark

                        JSONObject obj = new JSONObject();
                        obj.put("UserClass", userType);
                        obj.put("UserId", GlobalParams.sUserId);
                        obj.put("OrgName", companyNameEditName.getText().toString().trim());
                        obj.put("OrgType", companyType);
                        obj.put("OrgCode", paperEditIdcard.getText().toString().trim());
                        obj.put("LicUrl", bizLicUrl);
                        obj.put("Remark", "");
                        String user = obj.toString();
                        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                                user);
                        //上传接口
                        mApi.saveUserInfo(GlobalParams.sToken, body)
                                .compose(RxHttpResponseCompat.<String>compatResult())
                                .subscribe(new ErrorHandlerSubscriber<String>() {
                                    @Override
                                    public void onNext(String s) {
                                        RxToast.success("企业资料完善成功");
                                        GlobalParams.setuserName(companyNameEditName.getText().toString().trim());

                                        if (ActivityType == 1) {
                                            startActivity(new Intent(UploadDataActivity.this, MainActivity.class));
                                            stepfinishAll();

                                        } else if (ActivityType == 2) {
                                            finish();
                                        } else if (ActivityType == 3) {
                                            finish();
                                        }
                                    }
                                });
                    }

                } else {
                    //个人类型
                    String idCard = paperEditIdcard.getText().toString().trim();

                    if (ActivityType == 2) {
                        userTypeEditName.setText("个人用户");
                    }

                    if (userTypeEditName.getText().toString().trim().equals("")) {
                        RxToast.warning("请选择用户类型");
                    } else if (paperTypeEditUsername.getText().toString().trim().equals("")) {
                        RxToast.warning("请输入姓名");
                    } else if (usersexEditName.getText().toString().trim().equals("")) {
                        RxToast.warning("请选择性别");
                    } else if (paperTypeEditName.getText().toString().trim().equals("")) {
                        RxToast.warning("请选择证件类型");
                    } else if (idCard.equals("") || !IDCard.validate_effective(idCard, false)) {
                        RxToast.warning("请输入正确的证件号码");
                    } else if (IdFrontUrl.equals("")) {
                        RxToast.warning("请上传身份证正面照");
                    } else if (IdBackUrl.equals("")) {
                        RxToast.warning("请上传身份证反面照");
                    } else {

                        //上传接口
                        //个人传入参数UserClass,UserId, Name, Sex, IdCard, IdFrontUrl, IdBackUrl
                        JSONObject obj = new JSONObject();
                        obj.put("UserClass", userType);
                        obj.put("UserId", GlobalParams.sUserId);
                        obj.put("Name", paperTypeEditUsername.getText().toString().trim());
                        obj.put("Sex", sex);
                        obj.put("IdCard", paperEditIdcard.getText().toString().trim());
                        obj.put("IdFrontUrl", IdFrontUrl);
                        obj.put("IdBackUrl", IdBackUrl);
                        String user = obj.toString();
                        RequestBody body = RequestBody.create(MediaType.parse("text/json; charset=utf-8"),
                                user);

                        //上传接口
                        mApi.saveUserInfo(GlobalParams.sToken, body)
                                .compose(RxHttpResponseCompat.<String>compatResult())
                                .subscribe(new ErrorHandlerSubscriber<String>() {
                                    @Override
                                    public void onNext(String s) {
                                        RxToast.success("个人资料完善成功");
                                        GlobalParams.setuserName(paperTypeEditUsername.getText().toString().trim());

                                        if (ActivityType == 1) {
                                            startActivity(new Intent(UploadDataActivity.this, MainActivity.class));
                                            stepfinishAll();

                                        } else if (ActivityType == 2) {
                                            finish();

                                        } else if (ActivityType == 3) {
                                            finish();
                                        }


                                    }
                                });
                    }
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

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String returnedData = data.getStringExtra("data_return");
                if (returnedData.equals("1")) {
                    initPhotoData(true);
                } else {
                    initPhotoData(false);
                }
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

        String fileType = "";
        File imgFile = new File(iconPath);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imgFile);
        builder.addFormDataPart("file", imgFile.getName(), requestBody);
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();

        if (upLoadType == 0) {
            fileType = "1";
        } else if (upLoadType == 1) {
            fileType = "2";
        } else if (upLoadType == 2) {
            fileType = "3";
        }

        mApi.uploadImage(GlobalParams.sToken, GlobalParams.sUserId, multipartBody, fileType)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        RxToast.success("成功");
                        JSONObject jsonObject = JSONObject.parseObject(s);
                        if (upLoadType == 0) {

                            IdFrontUrl = jsonObject.getString("Path");
                            paperImgUploadIdcardImage.setVisibility(View.VISIBLE);
                            paperImgUploadIdcard.setVisibility(View.GONE);
                            Glide.with(UploadDataActivity.this).load(Constants.BASE_URL + IdFrontUrl).into(paperImgUploadIdcardImage);
                        } else if (upLoadType == 1) {

                            IdBackUrl = jsonObject.getString("Path");
                            paperImgUploadIdcardBackImage.setVisibility(View.VISIBLE);
                            paperImgUploadIdcardBack.setVisibility(View.GONE);
                            Glide.with(UploadDataActivity.this).load(Constants.BASE_URL + IdBackUrl).into(paperImgUploadIdcardBackImage);

                        } else if (upLoadType == 2) {

                            bizLicUrl = jsonObject.getString("Path");
                            paperImgUploadBizLicUrlImage.setVisibility(View.VISIBLE);
                            paperImgUploadBizLicUrl.setVisibility(View.GONE);

                            Glide.with(UploadDataActivity.this).load(Constants.BASE_URL + bizLicUrl).into(paperImgUploadBizLicUrlImage);
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

    private void getuserInfo() {
        mApi.GetUserInfo(GlobalParams.sToken, GlobalParams.sUserId)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        if (s != null) {
                            paperTypeEditUsername.setText(JSONObject.parseObject(s).getString("Name"));
                            paperEditIdcard.setText(JSONObject.parseObject(s).getString("IdCard"));

                            IdFrontUrl = JSONObject.parseObject(s).getString("IdFrontUrl");
                            IdBackUrl = JSONObject.parseObject(s).getString("IdBackUrl");

                            if (JSONObject.parseObject(s).getInteger("Sex") == 1) {
                                usersexEditName.setText("男");
                            } else {
                                usersexEditName.setText("女");
                            }

                            paperImgUploadIdcardImage.setVisibility(View.VISIBLE);
                            paperImgUploadIdcard.setVisibility(View.GONE);

                            paperImgUploadIdcardBackImage.setVisibility(View.VISIBLE);
                            paperImgUploadIdcardBack.setVisibility(View.GONE);

                            Glide.with(UploadDataActivity.this).load(Constants.BASE_URL + IdFrontUrl).into(paperImgUploadIdcardImage);

                            Glide.with(UploadDataActivity.this).load(Constants.BASE_URL + IdBackUrl).into(paperImgUploadIdcardBackImage);

                            registerBtn.setText("修改资料");
                        } else {
                            registerBtn.setText("完善资料");
                        }
                    }
                });
    }

    private void showCompany() {
        companyNameNameLayout.setVisibility(View.VISIBLE);
        userZjText.setText("企业类型");
        userZJNumberText.setText("机构代码");
        paperTypeEditName.setHint("请选择企业类型（必选项）");
        paperEditIdcard.setHint("请输入机构代码（必填项）");
        paperTypeEditName.setText("");
        paperEditIdcard.setText("");
        photoTypeText.setText("营业执照");
        paperUploadIdcard.setVisibility(View.GONE);
        paperUploadBizLicUrl.setVisibility(View.VISIBLE);
    }

    private void showPersonal() {
        companyNameNameLayout.setVisibility(View.GONE);
        userZjText.setText("证件类型");
        userZJNumberText.setText("证件号码");
        paperTypeEditName.setHint("请选择证件类型（必选项）");
        paperEditIdcard.setHint("请输入证件号码（必填项）");
        paperTypeEditName.setText("");
        paperEditIdcard.setText("");
        photoTypeText.setText("证件照片");
        paperUploadIdcard.setVisibility(View.VISIBLE);
        paperUploadBizLicUrl.setVisibility(View.GONE);
    }


}
