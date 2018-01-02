package com.xiaxiao.xiaoandroid.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.xiaxiao.xiaoandroid.R;


/**
 * Created by xiaxiao on 2017/9/13.
 *
 * 用来解决文字和图片组合时造成的view层级过多的问题。
 * 比如上图标下文字，下图标上文字，尤其是在实现一组tab均匀平铺的效果时出现的大量view层级
 * 比如各app的底部栏，本类只要一层view既可。
 *
 * 注意：必须设置drawable的宽高度。
 *
 */

public class XXDrawableTextView extends TextView {

    public final static int  POSITION_LEFT=0;
    public final static int  POSITION_TOP=1;
    public final static int  POSITION_RIGHT=2;
    public final static int  POSITION_BOTTOM=3;

    public final static int DEFAULT_SIZE=0;

    int leftDrawableWidth=DEFAULT_SIZE;
    int leftDrawableHeight=  DEFAULT_SIZE;
    int topDrawableWidth=    DEFAULT_SIZE;
    int topDrawableHeight=   DEFAULT_SIZE;
    int rightDrawableWidth=  DEFAULT_SIZE;
    int rightDrawableHeight= DEFAULT_SIZE;
    int bottomDrawableWidth= DEFAULT_SIZE;
    int bottomDrawableHeight=DEFAULT_SIZE;
    Paint mPaint;
    Paint mPaint2;
    Rect mBound;
    Drawable left;
    Drawable top;
    Drawable right;
    Drawable bottom;
    Context mContext;
    public XXDrawableTextView(Context context) {
        this(context,null,0);
    }

    public XXDrawableTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public XXDrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs, defStyleAttr);
    }

    private void getAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        /**
         * 获得我们所定义的自定义样式属性
         */
        mContext = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.XXDrawableTextView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.XXDrawableTextView_drawableWidth_left:
                    leftDrawableWidth = a.getDimensionPixelSize(attr,DEFAULT_SIZE);
                    break;
                case R.styleable.XXDrawableTextView_drawableHeight_left:
                    leftDrawableHeight = a.getDimensionPixelSize(attr, DEFAULT_SIZE);
                    break;

                case R.styleable.XXDrawableTextView_drawableWidth_top:
                    topDrawableWidth = a.getDimensionPixelSize(attr,DEFAULT_SIZE);
                    break;
                case R.styleable.XXDrawableTextView_drawableHeight_top:
                    topDrawableHeight = a.getDimensionPixelSize(attr, DEFAULT_SIZE);
                    break;

                case R.styleable.XXDrawableTextView_drawableWidth_right:
                    rightDrawableWidth = a.getDimensionPixelSize(attr,DEFAULT_SIZE);
                    break;
                case R.styleable.XXDrawableTextView_drawableHeight_right:
                    rightDrawableHeight = a.getDimensionPixelSize(attr, DEFAULT_SIZE);
                    break;
                case R.styleable.XXDrawableTextView_drawableWidth_bottom:
                    bottomDrawableWidth = a.getDimensionPixelSize(attr,DEFAULT_SIZE);
                    break;
                case R.styleable.XXDrawableTextView_drawableHeight_bottom:
                    bottomDrawableHeight = a.getDimensionPixelSize(attr, DEFAULT_SIZE);
                    break;
            }
        }
        a.recycle();

        /*
        * setCompoundDrawablesWithIntrinsicBounds方法会首先在父类的构造方法中执行，
        * 彼时执行时drawable的大小还都没有开始获取，都是0,
        * 这里获取完自定义的宽高属性后再次调用这个方法，插入drawable的大小
        * */
        setCompoundDrawablesWithIntrinsicBounds(
                left,top,right,bottom);


    }


    /**
     * Sets the Drawables (if any) to appear to the left of, above, to the
     * right of, and below the text. Use {@code null} if you do not want a
     * Drawable there. The Drawables' bounds will be set to their intrinsic
     * bounds.
     * <p>
     * Calling this method will overwrite any Drawables previously set using
     * {@link #setCompoundDrawablesRelative} or related methods.
     * 这里重写这个方法，来设置上下左右的drawable的大小
     *
     * @attr ref android.R.styleable#TextView_drawableLeft
     * @attr ref android.R.styleable#TextView_drawableTop
     * @attr ref android.R.styleable#TextView_drawableRight
     * @attr ref android.R.styleable#TextView_drawableBottom
     */
    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(@Nullable Drawable left,
                                                        @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;

        System.out.println("啦啦啦啦啦啦啦");

        if (left != null) {
            left.setBounds(0, 0, leftDrawableWidth,leftDrawableHeight);
        }
        if (right != null) {
            right.setBounds(0, 0, rightDrawableWidth,rightDrawableHeight);
        }
        if (top != null) {
            top.setBounds(0, 0, topDrawableWidth,topDrawableHeight);
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, bottomDrawableWidth,bottomDrawableHeight);
        }

        setCompoundDrawables(left, top, right, bottom);
    }

    /*
    * 代码中动态设置drawable的宽高度
    * */
    public void setDrawableSize(int width, int height,int position) {
        if (position==this.POSITION_LEFT) {
            leftDrawableWidth = width;
            leftDrawableHeight = height;
        }
        if (position==this.POSITION_TOP) {
            topDrawableWidth = width;
            topDrawableHeight = height;
        }
        if (position==this.POSITION_RIGHT) {
            rightDrawableWidth = width;
            rightDrawableHeight = height;
        }
        if (position==this.POSITION_BOTTOM) {
            bottomDrawableWidth = width;
            bottomDrawableHeight = height;
        }

        setCompoundDrawablesWithIntrinsicBounds(
                left,top,right,bottom);
    }

    /**
     * 代码中动态设置textview的上下左右的drawable
     * @param drawableId
     * @param position 上下左右
     * @return true:设置成功
     */
    public boolean setDrawableInSide(int drawableId, int position) {
        Drawable d=mContext.getResources().getDrawable(drawableId);
        if (d==null) {
            return false;
        }
        if (position==this.POSITION_LEFT) {
            left=d;
        }
        if (position==this.POSITION_TOP) {
            top=d;
        }
        if (position==this.POSITION_RIGHT) {
            right=d;
        }
        if (position==this.POSITION_BOTTOM) {
            bottom=d;
        }

        setCompoundDrawablesWithIntrinsicBounds(
                left,top,right,bottom);

        return true;
    }

    /**
     * 获得上下左右的图片的宽高度
     * @param position 上下左右位置
     * @return 宽高数组
     */
    public int[] getDrawableSize(int position) {
        int[] size = new int[2];
        if (position==this.POSITION_LEFT) {
            size[0]=leftDrawableWidth;
            size[1]=leftDrawableHeight;
        }
        if (position==this.POSITION_TOP) {
            size[0]=topDrawableWidth;
            size[1]=topDrawableHeight;
        }
        if (position==this.POSITION_RIGHT) {
            size[0]=rightDrawableWidth;
            size[1]=rightDrawableHeight;
        }
        if (position==this.POSITION_BOTTOM) {
            size[0]=bottomDrawableWidth;
            size[1]=bottomDrawableHeight;
        }
        return size;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the background for this view
        super.onDraw(canvas);

    }
}
