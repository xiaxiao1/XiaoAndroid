package com.xiaxiao.xiaoandroid.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xiaxiao.xiaoandroid.R;

/**
 * Created by xiaxi on 2017/4/29.
 * 底部导航栏，可以通过集成该类，实现动态的增减item，在子类中定义具体显示的UI图标等，本类已经实现了基本的底部导航栏效果
 * 对提供了点击响应，即返回当前点击item的index  从0开始
 */
public abstract  class BottomTab extends RelativeLayout {
    //item个数
   private int tabCount;
    //装载tab  item的直接父控件
   private LinearLayout tabBar_ll;
    //tab item 的点击监听事件
   private TabItemClickListener mTabItemClickListener;
    //当前的tab 下标
   private int currentTabIndex;
    //tab item的布局文件
   private int mLayoutId;
    //tab数组
   private View tabs[];
   private boolean alwaysResponse=true;

    public BottomTab(Context context) {
        super(context);
//        inits();
    }

    public BottomTab(Context context, AttributeSet attrs) {
        super(context, attrs);
//        inits();
    }

    public BottomTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        inits();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BottomTab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
//        inits();
    }

    public void inits() {
        this.tabCount=initCount();
        this.mLayoutId=initItemLayoutId();
        this.removeAllViews();
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tabBar_ll = new LinearLayout(getContext());
        tabBar_ll.setOrientation(LinearLayout.HORIZONTAL);
        tabBar_ll.setLayoutParams(params);
        this.addView(tabBar_ll);
        tabBar_ll.setWeightSum(tabCount);
        tabs = new View[tabCount];
        for (int i=0;i<tabCount;i++) {
            View v = inflate(getContext(), mLayoutId, null);
//            LinearLayout.LayoutParams
            tabBar_ll.addView(v);
            tabs[i]=v;
            LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams)v.getLayoutParams();
            params1.weight = 1;
            v.setLayoutParams(params1);
            final int finalI = i;
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentTabIndex = finalI;
                    changeTabState(tabs[finalI],finalI);
                    if (mTabItemClickListener!=null) {
                        mTabItemClickListener.onItemClick(finalI);
                    }
                }
            });

        }
        changeTabState(tabs[0],0);
        withInits();
    }

    public int getItemLayoutId() {
        return mLayoutId;
    }

    public LinearLayout getTabBar() {
        return tabBar_ll;
    }

    public int getTabCount() {
        return tabCount;
    }

    public View[] getTabs() {
        return tabs;
    }

    public int getCurrentTabIndex() {
        return currentTabIndex;
    }

    public void setTabItemClickListener(TabItemClickListener tabItemClickListener) {
        mTabItemClickListener = tabItemClickListener;
    }

    /**
     * 当tabs状态改变时的基本操作，主要是UI
     * @param tab 当前点击的tab
     * @param selectedIndex 当前tab的index
     */
    public abstract void changeTabState(View tab,int selectedIndex) ;

    /**
     * 指定tab个数
     * @return
     */
    public abstract int initCount();

    /**
     * 指定tab item的布局文件
     * @return
     */
    public abstract int initItemLayoutId();

    /**
     * 基本UI初始化完后的操作
     */
    public abstract void withInits() ;

    public interface TabItemClickListener {
        public void onItemClick(int index);
    }


}
