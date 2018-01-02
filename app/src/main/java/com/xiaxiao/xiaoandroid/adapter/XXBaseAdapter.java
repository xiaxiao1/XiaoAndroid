package com.xiaxiao.xiaoandroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by xiaxiao on 2017.11.02.
 * f封装的一个Adapter,省去了view复用相关的代码重复书写。
 * getItemId方法返回加载的item布局文件id
 * createHolder方法直接返回新的holder对象。
 * 数据的操作转移到了holder的initviews和initdatas方法中。
 */
public abstract class XXBaseAdapter<T,VH extends XXBaseAdapter.XXHolder<T>> extends BaseAdapter {

    public Context mContext;

    protected List<T> listItem;

    public XXBaseAdapter(Context c)
    {
        this.mContext = c;
    }

    public XXBaseAdapter(Context c, List<T> listItem )
    {
        this.mContext = c;
        this.listItem = listItem;
    }

    public void setList (List<T> listItem)
    {
        if ( listItem == null ) return;
        this.listItem = listItem;
        notifyDataSetChanged();
    }

    public void addList ( List<T> listItem )
    {
        if (listItem == null) {
            this.listItem = listItem;
        } else {
            this.listItem.addAll(listItem);
            notifyDataSetChanged();
        }
    }

    public void clean ()
    {
        if ( listItem == null )
        return;
        listItem.clear();
//        listItem = null;
    }

    @Override
    public int getCount() {
        return  (listItem == null ) ? 0 : listItem.size();
    }

    @Override
    public T getItem(int i) {
        return (listItem == null || listItem.size() == 0 ) ? null : listItem.get(i);
    }

    public List<T> getList ()
    {
        return listItem;
    }

    public void removeAt ( int position )
    {
        if (listItem!=null&&position<listItem.size()) {
            listItem.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view==null) {
            view = View.inflate(mContext, getViewId(position), null);
        }
        VH h = (VH) view.getTag();
        if (h==null) {
            h = createHolder(position, view, viewGroup);
            view.setTag(h);
        }
        if (h!=null) {
            h.initDatas(position,listItem.get(position));
        }
        return h.itemView;
    }

    /**
     * 创建一个当前position位置的holder。三个参数position, view, viewGroup来自getView方法。
     * @param position 当前item position
     * @param viewInHolder 当前item的view
     * @param viewGroup
     * @return 返回一个新的holder对象既可。
     */
    public  abstract VH createHolder(int position, View viewInHolder, ViewGroup viewGroup);

    /**
     * 返回当前item加载的布局文件id
     * @param position
     * @return
     */
    public abstract int getViewId(int position);


    public abstract static class XXHolder<T>{
        public View itemView;

        public XXHolder(View view) {
            this.itemView = view;
            initViews(view);
        }

        /**
         * 初始化各view控件
         * @param view
         */
        public abstract  void initViews(View view);

        /**
         * 展示数据
         * @param position
         * @param object
         */
        public abstract void initDatas(int position, T object);
    }

}
