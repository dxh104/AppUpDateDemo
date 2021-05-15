package com.example.appupdate.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.appupdate.util.UpdateApkUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class UpdateApkIntentService extends IntentService {
    private final String apkDir = Environment.getExternalStorageDirectory() + "/ElecTerminal/download/apk";
    private String apkFileName = "default.apk";
    private DownloadListener downloadListener;
    //通过binder实现调用者client与Service之间的通信
    private UpdateApkIntentServiceBinder updateApkIntentServiceBinder = new UpdateApkIntentServiceBinder();
    private boolean isDownLoading = false;//是否正在下载
    private boolean isCancelDownload = false;//是否取消下载
    private boolean isSupportContinueDownload = false;//是否支持继续下载

    public UpdateApkIntentService() {
        super("UpdateApkIntentService");
    }

    public UpdateApkIntentService(String name) {
        super(name);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String url = intent.getStringExtra("url");
        String apkFileName = intent.getStringExtra("apkFileName");
        isSupportContinueDownload = intent.getBooleanExtra("isSupportContinueDownload", false);
        if (!TextUtils.isEmpty(apkFileName))
            setApkFileName(apkFileName);
//            if (isDownLoading) {
//                return;
//            }
        isDownLoading = true;
        //防止在activity退出后还在下载导致activity泄漏
        download(url, new DownloadListener() {
                    @Override
                    public void onDownloadSuccess(String path) {
                        isDownLoading = false;
                        UpdateApkUtil.installAPK(UpdateApkIntentService.this, new File(path));
                        if (downloadListener != null) {
                            downloadListener.onDownloadSuccess(path);
                        }
                    }

                    @Override
                    public void onDownloading(int progress) {
                        if (downloadListener != null) {
                            downloadListener.onDownloading(progress);
                        }
                    }

                    @Override
                    public void onDownloadRemain(double speed, double allBytes, double remainBytes, int remainSecond) {
                        int reMainKb = (int) (remainBytes / 1024);
                        if (downloadListener != null) {
                            downloadListener.onDownloadRemain(speed, allBytes, remainBytes, remainSecond);
                        }
                    }

                    @Override
                    public void onDownloadFailed(Exception e) {
                        isDownLoading = false;
                        isCancelDownload = false;
                        if (downloadListener != null) {
                            downloadListener.onDownloadFailed(e);
                        }
                    }

                    @Override
                    public void onCancelDownload() {
                        isDownLoading = false;
                        if (downloadListener != null) {
                            downloadListener.onCancelDownload();
                        }
                    }
                }
        );
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        if (isDownLoading) {//已有任务正在下载，不再添加下载任务
            stopSelf(startId);
            return;
        }
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return updateApkIntentServiceBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
//        setDownloadListener(null);
        return super.onUnbind(intent);
    }


    public void cancelDownload() {
        if (isDownLoading)
            isCancelDownload = true;
    }

    private void download(final String url, final DownloadListener downloadListener) {
        try {
            if (TextUtils.isEmpty(url)) {
                downloadListener.onDownloadFailed(new Exception("url为空"));
                return;
            }
            File apkDirFile = new File(apkDir);
            if (!apkDirFile.exists()) {
                apkDirFile.mkdirs();//创建目录
            }
            OkHttpClient okHttpClient = new OkHttpClient();
            long resApkSize = getContentLength(okHttpClient, url);//获取服务器资源文件大小 并 判断服务器是否支持断点续传
            if (apkDirFile.isDirectory()) {
                File[] files = apkDirFile.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {//是文件类型
                        if (isSupportContinueDownload) {//支持继续下载就保留文件
                            if (!files[i].getName().equals(apkFileName)) {
                                files[i].delete();//删除目录下其他非下载的文件
                            }
                        } else {
                            files[i].delete();
                        }
                    }
                }
            }
            final File apkFile = new File(apkDir + "/" + apkFileName);
            if (!apkFile.exists()) {
                apkFile.createNewFile();//创建文件
            }

            RandomAccessFile apkAccessFile = new RandomAccessFile(apkFile, "rw");
            // 断点续传：指定下载的位置：apkFile.length()
            String range = String.format(Locale.CHINESE, "bytes=%d-", apkFile.length());
            Request request = new Request.Builder().url(url).header("range", range).build();
            if (resApkSize == 0) {
                downloadListener.onDownloadFailed(new Exception("获取下载文件大小失败"));
                return;
            }
            if (resApkSize == apkFile.length()) {//已下载的文件和服务器资源文件一样大，说明已下载完成
                downloadListener.onDownloadSuccess(apkFile.getAbsolutePath());
                return;
            }
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            if (response == null) {
                downloadListener.onDownloadFailed(new Exception("response为空"));
                return;
            }
            InputStream is = null;
            byte[] buf = new byte[1024*10];//缓存区大小
            int len = 0;//每次写入文件的长度
            long downBytes = 0;//文件下载速度
            ResponseBody body = response.body();
            //未下载完成，执行下载
            try {
                is = body.byteStream();
                apkAccessFile.seek(apkFile.length()); //移动文件指针到断点续传的位置
                long lastTimeMillis = System.currentTimeMillis();
                long currentTimeMillis = System.currentTimeMillis();
                int progress;//进度
                while ((len = is.read(buf)) != -1) {
                    if (isCancelDownload) {
                        isCancelDownload = false;//取消下载时恢复默认
                        downloadListener.onCancelDownload();
                        return;
                    }
                    apkAccessFile.write(buf, 0, len);//文件写入
                    downBytes = downBytes + len;//文件下载速度
                    progress = (int) (apkFile.length() * 1.0f / resApkSize * 100);
                    // 下载中
                    downloadListener.onDownloading(progress);
                    currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - lastTimeMillis >= 1000) {//每隔1s 计算一次下载速度
                        double speed = downBytes;
                        double remainTime = (resApkSize - apkFile.length()) / speed;
                        downloadListener.onDownloadRemain(speed, resApkSize, resApkSize - apkFile.length(), (int) remainTime);//每间隔一秒回调一次
                        downBytes = 0;//重置计算下载速度
                        lastTimeMillis = currentTimeMillis;
                    }
                }
                // 下载完成
                downloadListener.onDownloadSuccess(apkFile.getAbsolutePath());
            } catch (Exception e) {
                downloadListener.onDownloadFailed(e);
            } finally {
                if (is != null)
                    is.close();//断开http连接
            }
        } catch (IOException e) {
            downloadListener.onDownloadFailed(e);
        }
    }


    //获取下载资源的大小
    private long getContentLength(OkHttpClient mClient, String downloadUrl) {
        String range = String.format(Locale.CHINESE, "bytes=%d-", 0);
        Request request = new Request.Builder()
                .url(downloadUrl)
                .header("range", range)
                .build();
        try {
            Response response = mClient.newCall(request).execute();
            if (TextUtils.isEmpty(response.header("Content-Range"))) {//没有这个字段就说明服务器不支持断点续传
                isSupportContinueDownload = false;
            }
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                response.close();//断开http连接
                //这里返回的，就是资源文件的大小
                return contentLength;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    public void setApkFileName(String apkFileName) {
        this.apkFileName = apkFileName;
    }

    public class UpdateApkIntentServiceBinder extends Binder {

        public UpdateApkIntentService getService() {
            return UpdateApkIntentService.this;
        }
    }

    public interface DownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(String path);

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * @param speed        当前下载速度 byte/s
         * @param allBytes     总字节数
         * @param remainBytes  剩余要下载字节数
         * @param remainSecond 大约剩余时间单位秒
         */
        void onDownloadRemain(double speed, double allBytes, double remainBytes, int remainSecond);

        /**
         * 下载失败
         */
        void onDownloadFailed(Exception e);

        /**
         * 取消下载
         */
        void onCancelDownload();
    }

}