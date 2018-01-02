package com.xiaxiao.xiaoandroid.customview.veil;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.xiaxiao.xiaoandroid.R;

/**
 * Created by xiaxiao on 2017/10/13.
 * 实现View的蒙层效果，可以设置颜色和透明度
 *
 * 需要实现蒙层效果的View只要添加一个Veil对象既可，
 * 此时自定义View既可获得veilColor和veilAlpha两个属性。
 *
 * 在构造函数中使用veil.getAttributes获取在xml文件中设置的颜色和透明度值，
 * 然后在重写的onDraw方法中使用veil.drawVeil来绘制蒙层
 *
 * 对于代码中动态设置蒙层效果的实现  ，可以简单的在自定义View中创建一个getVeil方法返回veil对象既可。
 */

public class Veil {

    //蒙层的颜色
    private int color=0x00ffffff;
    //蒙层的透明度，可以用来判断是否要绘制蒙层 alpha>0则绘制
    private float alpha=0f;

    //事项蒙层效果的View
    public View mView;

    public Veil(View view) {
        this.mView = view;
    }

    /**
     * 从xml文件中获取设置的属性
     * @param context      来自实现效果的View
     * @param attrs        来自实现效果的View
     * @param defStyleAttr 来自实现效果的View
     */
    public void getAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        /**
         * 获得我们所定义的自定义样式属性
         */
//        mContext = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable
                .XXViewWithVeil, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.XXViewWithVeil_veilColor:
                    this.color = a.getColor(attr, 0x00ffffff);
                    break;
                case R.styleable.XXViewWithVeil_veilAlpha:
                    this.alpha = a.getFloat(attr, 0f);
                    break;

            }
        }
        a.recycle();
    }

    /**
     * 绘制蒙层效果
     * @param canvas 来自实现效果的View
     */
    public void drawVeil(Canvas canvas) {
        if (alpha<0f) {
            alpha=0f;
        }
        if (alpha>1f) {
            alpha=1f;
        }
        canvas.drawARGB((int)(255*this.alpha+0.5f),Color.red(color),Color.green(color),Color.blue(color));
    }


    /**
     * 设置蒙层效果属性
     * @param alpha 透明度
     * @param color 蒙层颜色
     */
    public void setVeil(float alpha,int color) {
        this.color = color;
        this.alpha = alpha;
        mView.invalidate();
    }
    public void setVeil(float alpha,String color) {
        this.color = Color.parseColor(color);
        this.alpha = alpha;
        mView.invalidate();
    }

    /**
     * 获取透明度
     * @return
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * 清除蒙层效果
     */
    public void clearVeil() {
        alpha=0;
        mView.invalidate();

    }

    /**
     * 是否已绘制蒙层，通过透明度判断
     * @return
     */
    public boolean isVeiled() {

        return alpha==0f;
    }
}
