package com.xiaxiao.xiaoandroid.Listener;

import android.view.View;
import android.widget.AbsListView;

import com.xiaxiao.xiaoandroid.util.XiaoUtil;

/**
 * Created by xiaxiao on 2017/8/11.
 * 主要是监听listview等的滑动，根据滑动的距离来操作

 * 实例： 标题栏动态透明度变化
 */

public abstract class ScrollingWatcher implements AbsListView.OnScrollListener {
    //参考的距离值
    private  int referenceHeight;
    //目标view
    private View targetView;
    //目标view滑动前时的顶部在window的位置，主要用来做计算参考
    private int initTop;
    //装载x,y轴位置信息
    private int[] location=new int[2];

    public ScrollingWatcher(View targetView, int referenceHeight) {
        this.referenceHeight = referenceHeight;
        this.targetView = targetView;
        targetView.getLocationInWindow(location);
        initTop = location[1];
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int
            totalItemCount) {
        //正在用手指滑动
        targetView.getLocationInWindow(location);
        XiaoUtil.l("hua a hua a 1   ");
        //交给用户处理
        this.handleScroll(targetView,location[1]-initTop,referenceHeight);
    }

    /**
     * 针对特定的View,根据滑动的距离，处理滑动
     * @param targetView 目标View
     * @param distance 基于view的初始化时的位置，向上或向下滑动了多远，<0:向上； >=0:向下
     * @param refrerence 滑动距离监听的最大间距
     */
    public abstract void handleScroll(View targetView, int distance, int refrerence);
}
