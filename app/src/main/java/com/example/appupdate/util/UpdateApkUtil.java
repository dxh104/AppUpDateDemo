package com.example.appupdate.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;

public class UpdateApkUtil {
    /**
     * 7.0兼容
     */
    public static void installAPK(Context mContext, File apkFile) {
//        File apkFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), apkName);
//        File apkFile = new File(apkDir, apkName);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(mContext, "com.example.appupdate.fileprovider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        mContext.startActivity(intent);
    }

}
