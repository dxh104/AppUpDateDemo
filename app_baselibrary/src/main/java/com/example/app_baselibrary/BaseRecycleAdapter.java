package com.example.app_baselibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XHD on 2020/01/09
 * base适配器优化版本
 */
public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    public List<T> mDatas;//数据集合
    protected Context mContext;//上下文
    protected OnItemClickListner onItemClickListner;//条目单击事件

    public BaseRecycleAdapter(List<T> mDatas) {
        this.mDatas = new ArrayList<>();
        if (mDatas != null)
            this.mDatas.addAll(mDatas);//非null，追加初始化数据
        setHasStableIds(true);//配合重写getItemId,避免刷新时图片闪烁,相当于我们平时给ImageView和图片做了一个tag绑定，检测到是url没变时，不再重新加载图片
    }

    @Override
    public int getItemViewType(int position) {
        int itemViewType = setItemViewType(mDatas, position);//------设置条目布局类型------
        if (itemViewType != 0) {
            return itemViewType;
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(getLayoutId(viewType), parent, false);//------设置条目布局------
        final BaseViewHolder baseViewHolder = new BaseViewHolder(mContext, view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListner != null)
                    onItemClickListner.onItemClickListner(v, baseViewHolder.getLayoutPosition());
            }
        });
        initView(baseViewHolder);//------初始化控件------
        setListener(baseViewHolder, mDatas);//------设置监听器------
        return baseViewHolder;
    }

    //尽量只做绑定数据,少创建对象 由于复用关系如果有if else 两种结果必须都重设背景/值/资源 状态(防止复用控件状态丢失,是之前控件状态，而不重新赋值)
    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        T t = mDatas.get(position);
        initView(holder);//------初始化控件------
        bindData(holder, position, t);//------绑定数据------
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //可以做一些回收之前可以做一些释放资源,取消加载等功能
    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
    }

    /**
     * 刷新数据
     *
     * @param datas
     */
    public void refresh(List<T> datas) {
        this.mDatas.clear();
        if (datas != null)
            this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param datas
     */
    public void addData(List<T> datas) {
        if (datas != null)
            this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param datas
     */
    public void addData(T datas) {
        this.mDatas.add(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据(局部刷新，局部刷新时必须重写getItemId方法，同时setHasStableIds(true))
     *
     * @param datas
     */
    public void addDataWithoutAnim(List<T> datas) {
        if (datas == null)
            return;
        int size = mDatas.size();
        this.mDatas.addAll(datas);
        notifyItemRangeChanged(size, datas.size());
    }

    /**
     * 返回ItemView布局类型---子类实现可以在外部用个变量保存返回值，初始化不同布局控件和点击事件可以直接根据这个返回值判断
     *
     * @return
     */
    protected abstract int setItemViewType(List<T> mDatas, int position);

    /**
     * 初始化item布局，获取item
     *
     * @return
     */
    protected abstract int getLayoutId(int viewType);

    /**
     * 初始化控件
     *
     * @param holder
     */
    protected abstract void initView(BaseViewHolder holder);

    /**
     * 设置监听事件
     */
    protected abstract void setListener(BaseViewHolder holder, List<T> mDatas);

    /**
     * 绑定数据
     *
     * @param position 对应的索引
     * @param t
     */
    protected abstract void bindData(BaseViewHolder holder, int position, T t);


    /**
     * 设置文本属性
     *
     * @param view
     * @param text
     */
    public void setItemText(View view, String text) {
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    /**
     * 设置图片属性
     *
     * @param view
     * @param url
     */
    public void setItemImage(View view, Object url) {
        if (view instanceof ImageView && mContext != null) {
            ImageView imageView = (ImageView) view;
            Glide.with(mContext).asDrawable().load(url).into(imageView);
        }
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface OnItemClickListner {
        void onItemClickListner(View v, int position);
    }
}
