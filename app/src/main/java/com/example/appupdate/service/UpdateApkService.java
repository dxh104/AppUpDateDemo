package com.example.appupdate.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.appupdate.MainActivity;
import com.example.appupdate.R;

/**
 * 更新apk服务
 */
public class UpdateApkService extends Service {

    private static final String TAG = UpdateApkService.class.getSimpleName() + "-------------->";
    private NotificationCompat.Builder notificationBuilder; //创建服务对象
    private String channelId = "com.example.appupdate.AppUpdate";
    private String channelName = "版本更新";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        //        Intent mIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//适配8.0 通知渠道
            createNotificationChannel(
                    false, false,//震动/声音
                    channelId,//1.渠道id保证全局唯一
                    channelName,//2.渠道名称是给用户看的，需要能够表达清楚这个渠道的用途
                    NotificationManager.IMPORTANCE_MAX); // 3.重要等级的不同则会决定通知的不同行为，当然这里只是初始状态下的重要等级，用户可以随时手动更改某个渠道的重要等级，App是无法干预的
            notificationBuilder = new NotificationCompat.Builder(UpdateApkService.this).setChannelId(channelId);
        } else {
            notificationBuilder = new NotificationCompat.Builder(UpdateApkService.this);
        }
//        RemoteViews views = new RemoteViews(getPackageName(), R.layout.layout_update_notification);//自定义的布局视图
        //按钮点击事件：
//        PendingIntent homeIntent = PendingIntent.getBroadcast(this, 1, new Intent("action"), PendingIntent.FLAG_UPDATE_CURRENT);
//        views.setOnClickPendingIntent(R.id.tv_data, homeIntent);//点击的id，点击事件
    }

    int process = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");


        notificationBuilder
                .setContentTitle("版本更新")//设置通知栏标题
                .setContentText(++process + "%")//设置通知栏内容
//                .setContent(views)//设置布局
//                .setTicker("提示消息！")//状态栏提示文本
//                .setAutoCancel(true)//是否允许自动清除
//                .setWhen(System.currentTimeMillis())//推送时间
                .setSmallIcon(R.mipmap.update)//最顶部的小图标
                .setChannelId(channelId)//设置渠道Id
                .setProgress(100, process, false)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.update))// 设置下拉列表中的图标(大图标)
//                .setOngoing(true)//设置是否常驻,true为常驻(普通通知有效)
//                .setPriority(Notification.PRIORITY_MAX)//设置优先级
//                .setCustomContentView(views)
//                .setCustomBigContentView(views)
//                .setContentIntent(pendingIntent)// 设置PendingIntent
                .build();
        Notification notification = notificationBuilder.build();

//        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        // 参数一：唯一的通知标识不能为0；参数二：通知消息。
        startForeground(1000, notification);// 开始前台服务
//        NotificationManager notificationManager = (NotificationManager) getSystemService(
//                NOTIFICATION_SERVICE);
//        notificationManager.notify(1000,notification);//普通通知
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy()");
        stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
//        NotificationManager notificationManager = (NotificationManager) getSystemService(
//                NOTIFICATION_SERVICE);
//        notificationManager.cancel(1000);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(boolean isVibrate,
                                           boolean hasSound,
                                           String channelId,
                                           String channelName,
                                           int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        channel.enableVibration(isVibrate);//设置是否应振动发送到此频道的通知
        channel.enableLights(false);//设置桌面图标右上角红点
        channel.setShowBadge(false);//设置投递到此频道的通知是否可以显示为应用程序图标徽章在发射器里。
//        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);//设置发布到此频道的通知是否显示在锁屏上
        if (!hasSound)
            channel.setSound(null, null);
        if (notificationManager != null)
            notificationManager.createNotificationChannel(channel);
    }

}
