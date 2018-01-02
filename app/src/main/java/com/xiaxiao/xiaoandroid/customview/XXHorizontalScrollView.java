package com.xiaxiao.xiaoandroid.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by xiaxiao on 2017/9/22.
 *
 * 实现监听水平滑动的滑动状态的
 */

public class XXHorizontalScrollView extends HorizontalScrollView {
    XXScrollListener xxScrollListener;
    public XXHorizontalScrollView(Context context) {
        super(context);
    }

    public XXHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XXHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (xxScrollListener!=null) {
            xxScrollListener.onScrollChanged(this,l,t,oldl,oldt);
        }
    }

    /**
     * 添加滑动监听，原生的HOrizontalScrollView没有提供滑动监听的方法，要自己实现
     * @param xxScrollListener
     */
    public void setXxScrollListener(XXScrollListener xxScrollListener) {
        this.xxScrollListener = xxScrollListener;
    }

    public interface XXScrollListener{
        public void onScrollChanged(XXHorizontalScrollView scrollView, int x, int y, int oldx,
                                    int oldy);


    }
}
