package com.xiaxiao.xiaoandroid.roundcorner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.xiaxiao.xiaoandroid.R;
import com.xiaxiao.xiaoandroid.customview.veil.Veil;
import com.xiaxiao.xiaoandroid.util.XiaoUtil;


/**
 * 实现图片的圆角，可以单独设置某几个角为多少弧度的圆角，
 *
 * 主要有两种方式实现，Xfermode方式和bitmapShader方式
 * Xfermode
 *
 * @author wbl
 */
public class RoundCornerImageView extends ImageView {

    private Paint mPaint;
    private Paint mPaint2;
    private boolean leftTop = false;
    private boolean rightTop = false;
    private boolean leftBottom = false;
    private boolean rightBottom = false;
    private int radius;
    private int leftTopRadius;
    private int rightTopRadius;
    private int leftBottomRadius;
    private int rightBottomRadius;
    private Veil mVeil;



    public RoundCornerImageView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
        mVeil = new Veil(this);
        mVeil.getAttributes(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView,
                defStyle, 0);
        radius = a.getDimensionPixelSize(R.styleable.RoundCornerImageView_cornerRadius, 0);
        if (radius > 0) {
            leftTopRadius = radius;
            rightTopRadius = radius;
            leftBottomRadius = radius;
            rightBottomRadius = radius;
            leftTop = isRoundCorner(radius);
            rightTop = isRoundCorner(radius);
            leftBottom = isRoundCorner(radius);
            rightBottom = isRoundCorner(radius);
        } else {
            leftTopRadius = a.getDimensionPixelSize(R.styleable
                    .RoundCornerImageView_cornerRadius_left_top, 0);
            rightTopRadius = a.getDimensionPixelSize(R.styleable
                    .RoundCornerImageView_cornerRadius_right_top, 0);
            leftBottomRadius = a.getDimensionPixelSize(R.styleable
                    .RoundCornerImageView_cornerRadius_left_bottom, 0);
            rightBottomRadius = a.getDimensionPixelSize(R.styleable
                    .RoundCornerImageView_cornerRadius_right_bottom, 0);
            leftTop = isRoundCorner(leftTopRadius);
            rightTop = isRoundCorner(rightTopRadius);
            leftBottom = isRoundCorner(leftBottomRadius);
            rightBottom = isRoundCorner(rightBottomRadius);
        }
        a.recycle();

        init();


    }

    public RoundCornerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public RoundCornerImageView(Context context) {
        this(context, null);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmap);
        super.onDraw(canvas2);
        if (mVeil.getAlpha()>0) {
            mVeil.drawVeil(canvas2);
        }
        if (leftTop) {
            drawLeftUp(canvas2, leftTopRadius);
        }
        if (rightTop) {
            drawRightUp(canvas2, rightTopRadius);
        }
        if (leftBottom) {
            drawLeftDown(canvas2, leftBottomRadius);
        }
        if (rightBottom) {
            drawRightDown(canvas2, rightBottomRadius);
        }
        canvas.drawBitmap(bitmap, 0, 0, mPaint2);
        bitmap.recycle();
    }

    public void setRoundCorners(int leftTopRadius, int rightTopRadius, int rightBottomRadius, int
            leftBottomRadius) {
        this.leftTopRadius = leftTopRadius;
        this.rightTopRadius = rightTopRadius;
        this.leftBottomRadius = leftBottomRadius;
        this.rightBottomRadius = rightBottomRadius;

        leftTop = isRoundCorner(leftTopRadius);
        rightTop = isRoundCorner(rightTopRadius);
        leftBottom = isRoundCorner(leftBottomRadius);
        rightBottom = isRoundCorner(rightBottomRadius);

        invalidate();
    }

    public void setRoundCorners(int radius) {
        setRoundCorners(radius,radius,radius,radius);
    }

    public int[] getRadius() {
        return new int[]{this.leftTopRadius,this.rightTopRadius,this.rightBottomRadius,this.leftBottomRadius};
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        //16种状态
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mPaint2 = new Paint();
        mPaint2.setXfermode(null);
    }

    private void drawLeftUp(Canvas canvas, int radius) {
        XiaoUtil.l("drawLeftUp radius:" + radius);
        Path path = new Path();
        path.moveTo(0, radius);
        path.lineTo(0, 0);
        path.lineTo(radius, 0);
        //arcTo的第二个参数是以多少度为开始点，第三个参数-90度表示逆时针画弧，正数表示顺时针
        path.arcTo(new RectF(0, 0, radius * 2, radius * 2), -90, -90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private void drawLeftDown(Canvas canvas, int radius) {
        Path path = new Path();
        path.moveTo(0, getHeight() - radius);
        path.lineTo(0, getHeight());
        path.lineTo(radius, getHeight());
        path.arcTo(new RectF(0, getHeight() - radius * 2, 0 + radius * 2, getHeight()), 90, 90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private void drawRightDown(Canvas canvas, int radius) {
        Path path = new Path();
        path.moveTo(getWidth() - radius, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - radius);
        path.arcTo(new RectF(getWidth() - radius * 2, getHeight() - radius * 2, getWidth(),
                getHeight()), 0, 90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private void drawRightUp(Canvas canvas, int radius) {
        Path path = new Path();
        path.moveTo(getWidth(), radius);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - radius, 0);
        path.arcTo(new RectF(getWidth() - radius * 2, 0, getWidth(), 0 + radius * 2), -90, 90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private boolean isRoundCorner(int radius) {
        if (radius > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Veil getVeil() {
        return this.mVeil;
    }
}
