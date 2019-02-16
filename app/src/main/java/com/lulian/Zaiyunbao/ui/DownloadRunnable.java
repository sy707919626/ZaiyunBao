package com.lulian.Zaiyunbao.ui;

import android.util.Log;

import com.lulian.Zaiyunbao.Bean.TaskInfo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Administrator on 2018/5/15 0015.
 */

public class DownloadRunnable implements Runnable {
    private TaskInfo info;
    private boolean isStop;//是否暂停
    public DownloadRunnable(TaskInfo info) {
        this.info = info;
    }

    /**
     * 设置是否暂停下载
     */
    public void stop(){
        isStop = true;
    }
    @Override
    public void run() {
        HttpURLConnection conn;//http连接对象
        BufferedInputStream bis;//缓冲输入流，从服务器获取
        RandomAccessFile raf;//随机读写器，用于写入文件，实现断点续传
        int len = 0;//每次读取的数组长度
        byte[] buffer = new byte[1024 * 8];//流读写的缓冲区
        try {
            //通过文件路径和文件名实例化File
            File file = new File(info.getPath() + info.getName());
            //实例化RandomAccessFile，rwd模式
            raf = new RandomAccessFile(file, "rwd");
            conn = (HttpURLConnection) new URL(info.getUrl()).openConnection();
            conn.setConnectTimeout(120000);//连接超时时间
            conn.setReadTimeout(120000);//读取超时时间
            conn.setRequestMethod("GET");//请求类型为GET
            if (info.getContentLen() == 0) {//如果文件长度为0，说明是新任务需要从头下载
                //获取文件长度
                info.setContentLen(Long.parseLong(conn.getHeaderField("content-length")));
            } else {//否则设置请求属性，请求制定范围的文件流
                conn.setRequestProperty("Range", "bytes=" + info.getCompleteLen() + "-" + info.getContentLen());
            }
            raf.seek(info.getCompleteLen());//移动RandomAccessFile写入位置，从上次完成的位置开始
            conn.connect();//连接
            bis = new BufferedInputStream(conn.getInputStream());//获取输入流并且包装为缓冲流
            //从流读取字节数组到缓冲区
            while (!isStop && -1 != (len = bis.read(buffer))) {
                //把字节数组写入到文件
                raf.write(buffer, 0, len);
                //更新任务信息中的完成的文件长度属性
                info.setCompleteLen(info.getCompleteLen() + len);
            }
            if (len == -1) {//如果读取到文件末尾则下载完成
                Log.i("tag", "下载完了");
            } else {//否则下载系手动停止
                Log.i("tag", "下载停止了");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
