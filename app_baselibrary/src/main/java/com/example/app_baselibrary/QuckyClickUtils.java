package com.example.app_baselibrary;

public class QuckyClickUtils {
    private static final int MIN_DELAY_TIME= 500;  // 两次点击间隔不能少于500ms
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}
