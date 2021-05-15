package com.example.app_baselibrary;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;


/**
 * Created by XHD on 2020/08/13
 */
public class BasePopWindow extends PopupWindow {
    private View popView;
    private int popViewWidth;
    private int popViewHeight;

    public BasePopWindow(View popView) {
        this(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public BasePopWindow(View popView, int popViewWidth, int popViewHeight) {
        this.popView = popView;
        this.popViewWidth = popViewWidth;
        this.popViewHeight = popViewHeight;
        setWidth(popViewWidth);
        setHeight(popViewHeight);
        //设置可以获得焦点
        setFocusable(true);
        //设置弹窗内可点击
        setTouchable(true);
        //设置弹窗外可点击
        setOutsideTouchable(true);
        //解决点击外部和返回键不消失问题
        setBackgroundDrawable(new BitmapDrawable());
        setContentView(popView);
//        setAnimationStyle(R.style.MenuPopWindowAnimalStyle);
    }

    final int location[] = new int[2];
    public final int top = 1;
    public final int right = 2;
    public final int bottom = 3;
    public final int left = 4;
    public final int top_left = 5;
    public final int top_right = 6;
    public final int bottom_left = 7;
    public final int bottom_right = 8;
    private int x, y;

    /**
     * @param anchor         参照控件
     * @param animationStyle 显示动画样式,可自己实现
     * @param orientation    方向
     */
    public void show(View anchor, int animationStyle, int orientation) {//设置显示在目标正方向
        int anchorWidth = anchor.getWidth();//关联view的宽度
        int anchorHeight = anchor.getHeight();//关联view的高度
//        anchor.getLocationOnScreen(location);//左上顶点在屏幕中的绝对位置
//        x = location[0];
//        y = location[1];
        if (animationStyle != 0)
            setAnimationStyle(animationStyle);//设置弹出动画样式
        switch (orientation) {//处理popwindow出现位置,无偏移，默认关联view下方左对齐
            case top://目标正上方
                showAsDropDown(anchor, -(popViewWidth - anchorWidth) / 2, -anchorHeight - popViewHeight);//可以设置偏移或无偏移
                break;
            case right://目标正右方
                showAsDropDown(anchor, anchorWidth, -(popViewHeight + anchorHeight) / 2);//可以设置偏移或无偏移
                break;
            case bottom://目标正下方
                showAsDropDown(anchor, -(popViewWidth - anchorWidth) / 2, 0);//可以设置偏移或无偏移
                break;
            case left://目标正左方
                showAsDropDown(anchor, -popViewWidth, -(popViewHeight + anchorHeight) / 2);//可以设置偏移或无偏移
                break;
            case top_left://目标左上方
                showAsDropDown(anchor, -popViewWidth, -anchorHeight - popViewHeight);//可以设置偏移或无偏移
                break;
            case top_right://目标右上方
                showAsDropDown(anchor, anchorWidth, -anchorHeight - popViewHeight);//可以设置偏移或无偏移
                break;
            case bottom_left://目标左下方
                showAsDropDown(anchor, -popViewWidth, 0);//可以设置偏移或无偏移
                break;
            case bottom_right://目标右下方
                showAsDropDown(anchor, anchorWidth, -(popViewWidth - anchorWidth) / 2);//可以设置偏移或无偏移
                break;
        }
    }
}
