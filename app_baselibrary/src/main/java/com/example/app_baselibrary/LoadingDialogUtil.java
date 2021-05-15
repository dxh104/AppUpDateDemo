package com.example.app_baselibrary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

/**
 * 加载框工具类
 */
public class LoadingDialogUtil {

    /**
     * 创建loading框
     */
    public static ProgressDialog createLoadingDialog(Activity activity, String title) {
        ProgressDialog loadingDialog = new ProgressDialog(activity);
        loadingDialog.setMessage(title + "...");
        loadingDialog.setCanceledOnTouchOutside(false);
        return loadingDialog;
    }


    /**
     * 显示等待框
     */
    public static void show(ProgressDialog loadingDialog) {
        if (loadingDialog != null && !loadingDialog.isShowing())
            loadingDialog.show();
    }

    /**
     * 隐藏等待框
     */
    public static void dismiss(ProgressDialog loadingDialog) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 设置点击等待框区域外是否能够dismiss 如果设置false，返回键也取消不了，需要自己实现OnKeyListener处理返回键事件
     * b为false 点击对话框外不能取消,b为true 点击对话框外可以取消
     */
    public static void setCancelable(final ProgressDialog loadingDialog, boolean b) {
        //setCanceledOnTouchOutside这个方法可以更容易实现只控制点击对话框外不能取消
        if (loadingDialog == null) return;
        if (!b) {
            loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {//监听ProgressDialog返回键事件
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
                        }
                    }
                    return false;
                }
            });
        }
        loadingDialog.setCancelable(b);
    }

}
