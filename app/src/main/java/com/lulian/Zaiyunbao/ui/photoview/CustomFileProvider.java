package com.lulian.Zaiyunbao.ui.photoview;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.OperationApplicationException;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;

import java.util.ArrayList;

/**
 *解决相机  和 apk 安装FileProvider冲突
 */

public class CustomFileProvider extends FileProvider {
    @Override
    public boolean onCreate() {
        return super.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        return super.applyBatch(operations);
    }

    @Nullable
    @Override
    public Uri canonicalize(@NonNull Uri url) {
        return super.canonicalize(url);
    }

    @Nullable
    @Override
    public Uri uncanonicalize(@NonNull Uri url) {
        return super.uncanonicalize(url);
    }
}
