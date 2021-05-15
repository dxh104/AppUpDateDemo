package com.example.app_baselibrary;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

//ActivityManager负责管理所有Activity
public class ActivityManager {
    private static ActivityManager manager;
    private final List<Activity> activityList = new ArrayList<>();

    public static ActivityManager getInstance() {
        if (manager == null) {
            manager = new ActivityManager();
        }
        return manager;
    }

    /**
     * 添加Activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 移除Activity
     */
    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    //移除所有Activity
    public void removeAllActivity() {
        activityList.clear();
    }

    public void finishActivity(Activity activity) {
        activity.finish();
        activityList.remove(activity);
    }

    public void finishAllActivity() {
        for (int i = 0; i < activityList.size(); i++) {
            activityList.get(i).finish();
        }
        activityList.clear();
    }


    public List<Activity> getActivityList() {
        return activityList;
    }
}
