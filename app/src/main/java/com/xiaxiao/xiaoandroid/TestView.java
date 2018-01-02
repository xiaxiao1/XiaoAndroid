package com.xiaxiao.xiaoandroid;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.xiaxiao.xiaoandroid.customview.veil.Veil;

/**
 * Created by xiaxiao on 2017/10/13.
 */

public class TestView extends ImageView {
    Veil veil = new Veil(this);
    public TestView(Context context) {
        super(context);
        veil.getAttributes(context, null, 0);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        veil.getAttributes(context, attrs, 0);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        veil.getAttributes(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TestView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        veil.getAttributes(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        veil.drawVeil(canvas);
    }


    public void setVeil(float alpha,int color) {
        veil.setVeil(alpha, color);
        invalidate();
    }

    public void clearVeil() {
        veil.clearVeil();
        invalidate();
    }
}
