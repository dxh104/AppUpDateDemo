package com.example.app_baselibrary;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;

/**
 * 封装一些网络请求方法
 */
public class ApiUtil {
    public static void calendarDetails(LifecycleProvider<ActivityEvent> provider, String client, String timestamp, String token, DefaultObserver<CalendarInfo> observer) {
        DataManager.getInstance().calendarDetails(client, timestamp, token)
//                .subscribeOn(Schedulers.io()) // 在子线程进行http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .compose(provider.<CalendarInfo>bindUntilEvent(ActivityEvent.DESTROY))// onDestroy取消订阅
                .subscribe(observer);
    }
}
