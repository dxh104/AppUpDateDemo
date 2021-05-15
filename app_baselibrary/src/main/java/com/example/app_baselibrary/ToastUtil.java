package com.example.app_baselibrary;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 */
public class ToastUtil {

    private static Toast mToast;

    private ToastUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static void makeToast(Context context, CharSequence message, int duration) {
        mToast = Toast.makeText(context.getApplicationContext(), message, duration);
//        LinearLayout layout = (LinearLayout) mToast.getView();
        //layout.setBackgroundColor(Color.parseColor("#FFFFFF11"));
        //layout.setBackgroundResource(R.drawable.shape_corner);
//        TextView tv = (TextView) layout.getChildAt(0);
        //tv.setTextColor(Color.RED);
//        tv.setTextSize(30);
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (mToast == null) {
            makeToast(context, message, Toast.LENGTH_SHORT);
//            mToast.setGravity(Gravity.CENTER, 0, 220);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (mToast == null) {
            makeToast(context, message, Toast.LENGTH_LONG);
//            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (mToast == null) {
            makeToast(context, message, duration);
//            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }


    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

}