package com.lulian.Zaiyunbao.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lulian.Zaiyunbao.R;
import com.lulian.Zaiyunbao.common.widget.DataCleanManager;
import com.lulian.Zaiyunbao.common.widget.LoginUtil;
import com.lulian.Zaiyunbao.common.widget.RxToast;
import com.lulian.Zaiyunbao.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/10/22.
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.image_back_detail_bar)
    ImageView imageBackDetailBar;
    @BindView(R.id.text_detail_content)
    TextView textDetailContent;
    @BindView(R.id.text_detail_right)
    TextView textDetailRight;
    @BindView(R.id.detail_bar_title)
    RelativeLayout detailBarTitle;
    @BindView(R.id.update_login_password)
    RelativeLayout updateLoginPassword;
    @BindView(R.id.clear_cache_textview)
    TextView clearCacheTextview;
    @BindView(R.id.image_view_clear)
    ImageView imageViewClear;
    @BindView(R.id.clear_cache)
    RelativeLayout clearCache;
    @BindView(R.id.inspect_update)
    RelativeLayout inspectUpdate;
    @BindView(R.id.exit_login)
    Button exitLogin;

    private float cacheSize;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_setting;
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

        textDetailContent.setText("设置");
        textDetailRight.setVisibility(View.GONE);
        showCacheSize();
    }

    private void showCacheSize() {
        try {
            cacheSize = DataCleanManager.getFolderSize(getCacheDir());
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                cacheSize += DataCleanManager.getFolderSize(getExternalCacheDir());
            }

            clearCacheTextview.setText(DataCleanManager.getFormatSize(cacheSize));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.update_login_password, R.id.clear_cache, R.id.inspect_update, R.id.exit_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.update_login_password:
                Intent intent = new Intent(this, Updae_pwd_Activity.class);
                intent.putExtra("isForget", false);
                startActivity(intent);

                break;

            case R.id.clear_cache:
                if (cacheSize > 0) {
                    final AlertDialog builder = new AlertDialog.Builder(this)
                            .create();
                    builder.show();
                    if (builder.getWindow() == null) return;
                    builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
                    TextView msg = builder.findViewById(R.id.tv_msg);
                    Button cancle = builder.findViewById(R.id.btn_cancle);
                    Button sure = builder.findViewById(R.id.btn_sure);

                    if (msg == null || cancle == null || sure == null) return;

                    msg.setText("是否清空缓存?");

                    cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.dismiss();
                        }
                    });
                    sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataCleanManager.clearAllCache(getApplicationContext());
                            showCacheSize();
                            builder.dismiss();
                        }
                    });

                } else {
                    RxToast.warning("当前应用没有缓存数据！");
                }

                break;

            case R.id.inspect_update:

                break;

            case R.id.exit_login:
                LoginUtil.loginOut();
                break;

            default:
                break;
        }
    }

//    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setMessage(message);
//        builder.setPositiveButton("确定", onClickListener);
//        builder.setNegativeButton("取消", null);
//        return builder;
//    }

}
