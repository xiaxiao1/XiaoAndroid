package com.xiaxiao.xiaoandroid.customview.bannerview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaxiao.xiaoandroid.util.XiaoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaxiao on 2017/5/4.
 * 为自定义XXBannerView设置的adapter类
 */

public abstract class XXBannerAdapter<T> extends PagerAdapter {
    Context mContext;
    List<T> mDatas;
    //viewpager的items
    List<View> itemViews;
    XXBannerView xxBannerView;
    //为item加载的布局文件Id
    int mItemViewLayoutId;
    //item的宽度占viewPager的百分比
    float pageWidth=1f;
    //是否是多图显示模式
    boolean isMultiple=false;
    public XXBannerAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewLayoutId = loadItemLayout();
        initItemsView();
    }

    public void setXxBannerView(XXBannerView xxBannerView) {
        this.xxBannerView = xxBannerView;
    }

    /**
     * 设置viewpager要加载的布局文件
     * @return item 加载的布局文件id
     */
    public abstract int loadItemLayout();

    /**
     * 初始化items
     */
    private void initItemsView() {
        if (mDatas==null||mDatas.size()==0) {
            return;
        }
        itemViews = new ArrayList<>();
        for (int i=0;i<mDatas.size();i++) {
            itemViews.add(getOneItemView());
        }
        //用于循环滑动
        itemViews.add(getOneItemView());
        itemViews.add(getOneItemView());

    }


    private View getOneItemView() {
        return LayoutInflater.from(mContext).inflate(mItemViewLayoutId,null);
    }

    public List<View> getItemViews() {
        return itemViews;
    }


    /**
     * 设置item占当前viewpager的宽度的百分比，用于实现第二种多图模式
     * 因为这是和自定义的bannerView搭配的，自定义bannerView做了循环处理，所以这里的百分比不能小于0.5,
     * 否则会导致循环无效，
     * @param position
     * @return
     */
    @Override
    public float getPageWidth(int position) {

        return  1.0f;
    }
    @Override
    public int getCount() {
        return mDatas.size()+2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        XiaoUtil.l("isViewFromObject");
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(itemViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        XiaoUtil.l("instantiateItem");
        View item=itemViews.get(position);
        container.addView(item);
        Holder holder = (Holder) item.getTag();
        if (holder==null) {
            XiaoUtil.l("createItemHolder");
            holder=createItemHolder(item);
            if (xxBannerView.onItemPagerClickListener!=null) {
                item.setOnClickListener(xxBannerView.getOnItemPagerClickListener());
            }
            item.setTag(holder);
        }

        if (position==0) {
            onItemInstantiated(holder, mDatas.get(mDatas.size()-1),position);
        } else if (position == itemViews.size() - 1) {
            onItemInstantiated(holder, mDatas.get(0), position);
        } else {
            onItemInstantiated(holder, mDatas.get(position-1), position);
        }
        return itemViews.get(position);
    }

    /**
     * 和listview中的holder一个意思，避免重复加载，
     * @param view 当前item的view
     * @return 返回一个Holder的实例，需要user在其中自定义各种控件引用
     */
    public abstract Holder createItemHolder(View view);

    /**
     * onItemInstantiated()方法中执行的，对每一个item的设置操作在这里进行，
     * @param holder 装载当前item View
     * @param obj 当前item要加载的数据
     * @param position 当前item的index
     */
    public  abstract void afterItemInstantiated(Holder holder, T obj,int position);

    /**
     * 在instantiateItem()方法中执行的，对每一个item的设置操作在这里进行，
     * @param holder 装载当前item View
     * @param obj 当前item要加载的数据
     * @param position 当前item的index
     */
    public   void onItemInstantiated(Holder holder, T obj,int position){
        holder.initDatas(obj);
        afterItemInstantiated(holder, obj, position);

    }


    public abstract class Holder<T>{

        View item;
        public Holder(View view) {
            item = view;
            initChildren(view);
        }

        public View getItemView() {
            return this.item;
        }

        /**
         * 在这里做数据的逻辑处理
         * @param obj
         */
        public abstract void initDatas(T obj);

        /**
         * 在这里初始化item view中的各个控件
         * @param view
         */
        public abstract void initChildren(View view);
    }

}
