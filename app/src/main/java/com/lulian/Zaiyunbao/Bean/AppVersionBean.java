package com.lulian.Zaiyunbao.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/4.
 */

public class AppVersionBean implements Serializable{
    /**
     * MinVersion : 1.0.1
     * MinVersionCode : 2
     * DownLoadUrl : api.zaiyunbao.com/zybapp.apk
     */

    private String MinVersion;
    private String MinVersionCode;
    private String DownLoadUrl;

    public String getMinVersion() {
        return MinVersion;
    }

    public void setMinVersion(String MinVersion) {
        this.MinVersion = MinVersion;
    }

    public String getMinVersionCode() {
        return MinVersionCode;
    }

    public void setMinVersionCode(String MinVersionCode) {
        this.MinVersionCode = MinVersionCode;
    }

    public String getDownLoadUrl() {
        return DownLoadUrl;
    }

    public void setDownLoadUrl(String DownLoadUrl) {
        this.DownLoadUrl = DownLoadUrl;
    }
}
