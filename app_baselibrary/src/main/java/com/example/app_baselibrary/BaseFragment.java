package com.example.app_baselibrary;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.navi2.component.support.NaviFragment;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.navi.NaviLifecycle;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by XHD on 2020/08/12
 */
public abstract class BaseFragment extends NaviFragment implements IBaseView {
    //管理BaseFragment集合
    public FragmentManager mFragmentManager = FragmentManager.getInstance();
    public Context mContext;
    private View mContentView;
    private Unbinder bind;
    public LifecycleProvider<ActivityEvent> mProvider;
    private ProgressDialog loadingDialog;

    private boolean isViewInitialized;//View是否初始化完毕
    private boolean isDataInitialized;//数据是否初始化完毕
    private boolean islazyLoadMode = setLazyLoadMode();//是否启用懒加载模式

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager.addFragment(this);//添加Fragment
    }

    @SuppressLint({"NewApi", "MissingSuperCall"})
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResourceID(), container, false);//填充布局
        bind = ButterKnife.bind(this, mContentView);//绑定View
        mContext = getContext();
        mProvider = NaviLifecycle.createActivityLifecycleProvider(this);
        loadingDialog = LoadingDialogUtil.createLoadingDialog(getActivity(), getLoadingDialogTitle());//创建加载框
        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpView();//设置View
        isViewInitialized = true;//View初始化完毕
        lazyLoad();//启动懒加载
        if (!islazyLoadMode)//非赖加载模式
            setUpData();
    }


    /**
     * 此方法会在onCreateView(）之前执行
     * 当viewPager中fragment改变可见状态时也会调用
     * 当fragment 从可见到不见，或者从不可见切换到可见，都会调用此方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();//启动懒加载
    }


    private void lazyLoad() {
        if (getUserVisibleHint() && isViewInitialized && !isDataInitialized && islazyLoadMode) {
            setUpData();//设置数据
            isDataInitialized = true;//数据初始化完毕
        }
    }


    @Override
    public void onDestroy() {
        if (bind != null) {
            bind.unbind();//取消绑定
        }
        hideLoadingDialog();
        ToastUtil.cancelToast();
        mFragmentManager.removeFragment(this);//移除Fragment
        super.onDestroy();//防空
    }

    /**
     * 此方法用于返回Fragment设置ContentView的布局文件资源ID * * @return 布局文件资源ID
     */
    protected abstract int setLayoutResourceID();


    /**
     * 是否设置懒加载模式
     *
     * @return
     */
    protected abstract boolean setLazyLoadMode();

    /**
     * 一些View的相关操作
     */
    protected abstract void setUpView();

    /**
     * 一些Data的相关操作
     */
    protected abstract void setUpData();


    protected View getContentView() {
        return mContentView;
    }


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
    }

    //设置加载框标题
    protected String getLoadingDialogTitle() {
        return "加载中";
    }

}
