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
}
