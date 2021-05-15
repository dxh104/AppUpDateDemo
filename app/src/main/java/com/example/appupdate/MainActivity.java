package com.example.appupdate;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.app_baselibrary.AppInfoUtil;
import com.example.app_baselibrary.BaseActivity;
import com.example.app_baselibrary.CheckPermissionUtils;
import com.example.appupdate.service.UpdateApkIntentService;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements UpdateApkIntentService.DownloadListener, ServiceConnection {
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.btn_startService)
    Button btnStartService;
    @BindView(R.id.btn_stopService)
    Button btnStopService;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tvData.setText("进度:" + msg.what);
        }
    };
    private String TAG = "MainActivity--------->";
    private UpdateApkIntentService updateApkIntentService;
    private boolean isBound = false;
    private Intent serviceIntent;

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setActionBar("主页");
        CheckPermissionUtils.checkPermission(this);//检查权限
        String appVersionCode = AppInfoUtil.getAppVersionCode(this);
        String appVersionName = AppInfoUtil.getAppVersionName(this);
    }


    @Override
    protected void initData() {
        String url = "http://47.114.59.47:8080/ipserver/uploadFiles/app-debug.apk";
        //todo--------必填1
        serviceIntent = new Intent(MainActivity.this, UpdateApkIntentService.class);
        serviceIntent.putExtra("url", url);
        serviceIntent.putExtra("apkFileName", "Wsgw.apk");
        serviceIntent.putExtra("isSupportContinueDownload", false);

    }

    @Override
    protected boolean isVerticalScreen() {
        return true;
    }


    @OnClick({R.id.tv_data, R.id.btn_startService, R.id.btn_stopService, R.id.btn_bindService, R.id.btn_unBindService, R.id.btn_cancelDownload, R.id.btn_Download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_data:
                break;
            case R.id.btn_bindService:
                bindUpdateService();
                break;
            case R.id.btn_unBindService:
                unBindUpdateService();
                break;
            case R.id.btn_startService:
                startUpdateService();
                break;
            case R.id.btn_stopService:
                stopUpdateService();
                break;
            case R.id.btn_cancelDownload:
                cancelDownload();        //todo--------必填2
                break;
            case R.id.btn_Download:
                download();        //todo--------必填3
                break;
        }
    }

    @Override
    protected void onDestroy() {
        //todo--------必填4
        unBindUpdateService();
        super.onDestroy();
    }

    //下载
    private void download() {
        bindUpdateService();
        startUpdateService();
    }

    //取消下载
    private void cancelDownload() {
        if (updateApkIntentService != null)
            updateApkIntentService.cancelDownload();
    }

    //开始下载服务
    private void startUpdateService() {
        startService(serviceIntent);
    }

    //停止下载服务
    private void stopUpdateService() {
        stopService(serviceIntent);
    }

    //绑定服务
    private void bindUpdateService() {
        // 标志位BIND_AUTO_CREATE使得服务中onCreate得到执行,onStartCommand不会执行
        bindService(serviceIntent, MainActivity.this, Context.BIND_AUTO_CREATE);
    }

    //解绑服务
    private void unBindUpdateService() {
        if (isBound) {
            if (updateApkIntentService != null)
                updateApkIntentService.setDownloadListener(null);//防止activity泄漏
            isBound = false;
            unbindService(MainActivity.this);
        }
    }


    @Override
    public void onDownloadSuccess(String path) {

    }

    @Override
    public void onDownloading(final int progress) {
        mHandler.sendEmptyMessage(progress);
    }

    @Override
    public void onDownloadRemain(double speed, double allBytes, double remainBytes, int remainSecond) {
        Log.i(TAG, "onDownloadRemain: speed=" + speed / 1024 + "kb/s");
    }

    @Override
    public void onDownloadFailed(Exception e) {
        Log.i(TAG, "onDownloadFailed: " + e.getMessage());
    }

    @Override
    public void onCancelDownload() {
        Log.i(TAG, "onCancelDownload: ");
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        //绑定成功，回调这个方法
        isBound = true;
        updateApkIntentService = ((UpdateApkIntentService.UpdateApkIntentServiceBinder) service).getService();
        updateApkIntentService.setDownloadListener(MainActivity.this);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        //如果Service中途挂掉，client端也能通过onServiceDisconnected感知到（通过Binder的linkToDeath实现）
        isBound = false;
    }
}
