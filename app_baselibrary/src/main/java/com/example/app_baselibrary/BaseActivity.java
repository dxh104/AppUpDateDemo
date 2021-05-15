package com.example.app_baselibrary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by XHD on 2020/11/16
 */
public abstract class BaseActivity extends RxAppCompatActivity implements IBaseView {
    //管理所有Activity
    public ActivityManager mActivityManager = ActivityManager.getInstance();
    public Context mContext;
    public LifecycleProvider<ActivityEvent> mProvider;
    private Unbinder bind;
    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isVerticalScreen())
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 强制竖屏
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 强制横屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);//Activity去标题栏
        setContentView(setContentLayoutRes());//设置布局
        hideActionBar();//AppCompatActivity去标题栏
        getRootLayoutView().setBackgroundColor(getResources().getColor(R.color.layoutRootColor));//根布局设置颜色(如果子类想修改，直接再后面重设两种颜色)
        setStatusBarColor(getResources().getColor(R.color.layoutRootColor), true);//状态栏设置为根布局颜色
        mActivityManager.addActivity(this);//添加Activity
        mContext = this;
        mProvider = this;
        loadingDialog = LoadingDialogUtil.createLoadingDialog(this, getLoadingDialogTitle());//创建加载框
        bind = ButterKnife.bind(this);//ButterKnife 不需要就注释掉

        //开始进行初始化
        initView();
        initData();

    }

    @Override
    protected void onDestroy() {
        if (bind != null) {
            bind.unbind();//解除绑定
        }
        hideLoadingDialog();
        ToastUtil.cancelToast();
        mActivityManager.removeActivity(this);//移除Activity
        super.onDestroy();
    }

    /**
     * 设置布局资源
     *
     * @return
     */
    protected abstract @LayoutRes
    int setContentLayoutRes();

    /**
     * 初始化控件，交给子类实现
     */
    protected abstract void initView();

    /**
     * 初始化数据，交给子类实现
     */
    protected abstract void initData();

    /**
     * 是否强制竖屏
     *
     * @return
     */
    protected abstract boolean isVerticalScreen();

    //显示加载框
    @Override
    public void showLoadingDialog() {
        LoadingDialogUtil.show(loadingDialog);
    }


    //隐藏加载框
    @Override
    public void hideLoadingDialog() {
        LoadingDialogUtil.dismiss(loadingDialog);
    }


    //通用请求失败
    @Override
    public void requestOnError(Throwable e) {
        ToastUtil.showShort(mContext, e.getMessage());
        hideLoadingDialog();
    }

    //设置默认通用ActionBar(如果使用时想修改,可以利用返回view重新设置内部属性)
    protected View setActionBar(String title) {
        View actionbarRootLayoutView = LayoutInflater.from(this).inflate(R.layout.actionbar_layout, ((ViewGroup) getRootLayoutView()));
        TextView tvTitle = actionbarRootLayoutView.findViewById(R.id.tv_title);
        ImageView ivBack = actionbarRootLayoutView.findViewById(R.id.iv_back);
        tvTitle.setText(title);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTitleSize());
        ivBack.getLayoutParams().width = (int) getTitleSize();
        ivBack.getLayoutParams().height = (int) getTitleSize();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return (View) tvTitle.getParent();
    }

    //设置加载框标题(子类需要修改的话可以重写)
    protected String getLoadingDialogTitle() {
        return "加载中";
    }

    //状态栏高度
    protected int getStatusBarHeight() {
        return AppUtil.getStatusBarHeight(this);
    }

    //活动栏高度
    protected int getActionBarHeight() {
        return AppUtil.getActionBarHeight(this);
    }

    //获取ContentView内部根layout
    protected View getRootLayoutView() {
        return ((ViewGroup) (getWindow().getDecorView().findViewById(android.R.id.content))).getChildAt(0);
    }

    //获取默认标题字体大小 单位/像素
    protected float getTitleSize() {
        Toolbar toolbar = getWindow().getDecorView().findViewById(R.id.action_bar);
        TextView titleTv = (TextView) toolbar.getChildAt(0);
        return titleTv.getTextSize();
    }

    //隐藏活动栏
    protected void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();//AppCompatActivity去标题栏
        }
    }

    //隐藏状态栏---->设置全屏和状态栏透明(含日期时间)
    protected void hideStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {//5.0有效
            View decorView = getWindow().getDecorView();
            //设置全屏(布局全屏)和状态栏透明
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置状态栏透明
        }
    }


    /**
     * 设置状态栏颜色
     *
     * @param color       状态栏颜色
     * @param isLightMode true 亮色模式(黑)
     */
    protected void setStatusBarColor(int color, boolean isLightMode) {
        if (Build.VERSION.SDK_INT >= 21) {//5.0有效
            getWindow().setStatusBarColor(color);//设置状态栏颜色
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isLightMode) {//6.0可以设置为亮色模式
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    /*
     *设置全屏(不含标题栏，状态栏，不含日期时间，全屏)
     */
    protected void setFullScreen() {
        //去掉顶部标题
        getSupportActionBar().hide();
        //去掉最上面时间、电量等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getWindow().setAttributes(lp);
    }

    //设置全屏并隐藏底部导航栏
    private void hideNavigationBar() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN; // hide status bar

        if (Build.VERSION.SDK_INT >= 19) {
            uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE;//0x00001000; // SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide
        } else {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
