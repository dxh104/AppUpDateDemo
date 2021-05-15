package com.example.app_baselibrary;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


/**
 * Dialog工具类封装
 */
public class DialogUtil {
    //创建Dialog底部弹出菜单案例，中部对话框
    public static Dialog createBaseDialog(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.base_dialog, null);//填充对话框
        final TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        final Dialog dialog = new Dialog(context, R.style.MyBlackDialog);//设置透明样式
        dialog.setContentView(view);//对话框填充布局
        dialog.getWindow().setGravity(Gravity.BOTTOM);//窗口靠底
        dialog.getWindow().setWindowAnimations(R.style.DialogBottomAnimation);//对话框底部弹出动画样式
        //方案1:此处设置的窗口宽高对应view的根layout宽高(推荐)
        dialog.getWindow().setLayout(context.getResources().getDimensionPixelSize(R.dimen.dp_100), context.getResources().getDimensionPixelSize(R.dimen.dp_100));
        //方案2:设置窗口宽高,由于view的root为null，所以view根布局长宽无效，可以直接用子布局实现对话框，此时窗体应设置WRAP_CONTENT
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(context, tvTitle.getText().toString());
            }
        });
        return dialog;
    }


}
