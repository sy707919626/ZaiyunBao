package com.lulian.Zaiyunbao.Bean;

/**
 * Created by Administrator on 2018/5/15 0015.
 */

public class TaskInfo {
    private String name ;//文件名
    private String path; //文件路径
    private String url ;//下载链接
    private long contentLen;//文件大小
    private volatile long completeLen;//已经下载的大小

    public TaskInfo(String name, String path, String url) {
        this.name = name;
        this.path = path;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getContentLen() {
        return contentLen;
    }

    public void setContentLen(long contentLen) {
        this.contentLen = contentLen;
    }

    public long getCompleteLen() {
        return completeLen;
    }

    public void setCompleteLen(long completeLen) {
        this.completeLen = completeLen;
    }
}
