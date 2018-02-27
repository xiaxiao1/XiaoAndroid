package com.xiaxiao.xiaoandroid.customview;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaxiao on 2018/2/26.
 * 水波纹View,可以设置多条水波曲线， 添加了退到后台时动画线程的暂停控制。
 * 具体的参数配置等没有封装，以后用到再具体封装吧
 */

public class WaveView extends View {
    ArrayList<ValueAnimator> animators = new ArrayList<>();
    private Paint mpaint;
    //画水波的
    private Bitmap dest;
    //画圆形的
    private Bitmap src;

    //参考系，x,y坐标和半径
    private int radiusX;
    private int radiusY;
    private int raduis;

    //以下四个是水波函数的几个参数
    private float t = 10f;
    private float A = 30;
    private float W = 0.005f;
    private float K = 300;

    //本view的长宽
    int w = 0, h = 0;

    Canvas c1;
    Paint p1;
    Canvas c2;
    Paint p2;

    float y;
    //水波的个数
    int waveSize = 3;
    List<Wave> waveList = new ArrayList<>();
    private int mcolor;
    //圆球形状时 是否展示边线
    boolean showBorder = false;
    //是否变成球形
    boolean asBall = true;
    //是否是水在底部
    boolean below = true;
    //边线颜色
    int borderColor;


    public void print(String s) {
        System.out.println(s);
    }

    private void init() {
        mcolor = Color.parseColor("#fd9d36");
        //初始化水波
        Wave wave1 = new Wave(mcolor, 55, 10f);
        Wave wave2 = new Wave(mcolor, 100, 6f);
        Wave wave3 = new Wave(mcolor, 240, 2f);
        waveList.add(wave1);
        waveList.add(wave2);
        waveList.add(wave3);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mpaint = new Paint();
        mpaint.setAntiAlias(true);
        ValueAnimator va = ValueAnimator.ofInt(100, 0);
        addAnimator(va);
        va.setDuration(8000);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                for (Wave wave : waveList) {
                    //根据改变初象t来实现运动
                    wave.t -= 0.01f;
                }
                invalidate();
            }
        });
        va.setRepeatMode(ValueAnimator.RESTART);
        va.setRepeatCount(-1);
        va.start();
    }

    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = canvas.save();
//        print("haha" + A);
        draw1(canvas);

        mpaint.setColor(Color.BLACK);
        mpaint.setTextSize(100);
        drawAsBall(canvas);
        drawBorder(canvas);
//        canvas.drawText("qwqwqwqw",100,h/2,mpaint);
//        canvas.drawOval(radiusX-raduis+40,radiusY-raduis+40,radiusX+raduis-40,radiusY+raduis-40,mpaint);
        canvas.restoreToCount(count);

    }


    /**
     * 作为球形
     * @param canvas
     */
    private void drawAsBall(Canvas canvas) {
        if (asBall) {
            canvas.drawBitmap(dest, 0, 0, mpaint);
            mpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(src, 0, 0, mpaint);
            mpaint.setXfermode(null);
        }
    }

    /**
     * 画水波纹
     * @param canvas
     */
    private void draw1(Canvas canvas) {
        if (w == 0) {
            w = getWidth();
            h = getHeight();
            radiusX = w / 2;
            radiusY = h / 2;
            raduis = radiusX > radiusY ? radiusY : radiusX;
            dest = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            c1 = new Canvas(dest);
            p1 = new Paint(Paint.ANTI_ALIAS_FLAG);
            p1.setAntiAlias(true);

            src = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            c2 = new Canvas(src);
            p2 = new Paint(Paint.ANTI_ALIAS_FLAG);
            p2.setAntiAlias(true);

            K = h / 3;
        }

        for (Wave wave : waveList) {

            p1.setColor(wave.color);
            p1.setAlpha(wave.alpha);

            wave.path.reset();
            if (below) {
                wave.path.moveTo(0, h);

                for (float x = 0; x <= w; x += 10) {
                    y = (float) (A * Math.sin(W * x + wave.t) + K);
                    wave.path.lineTo(x, h - y);

                }

                wave.path.lineTo(w, h);
                wave.path.lineTo(0, h);
            } else {
                wave.path.moveTo(0, 0);

                for (float x = 0; x <= w; x += 10) {
                    y = (float) (A * Math.sin(W * x + wave.t) + K);
                    wave.path.lineTo(x, h - y);

                }

                wave.path.lineTo(w, 0);
                wave.path.lineTo(0, 0);
            }
            wave.path.close();
            canvas.drawPath(wave.path, p1);
        }
        c2.drawCircle(radiusX, radiusY, raduis, p2);

    }

    /**
     * 实现水位上升，注意水位在下还是在上正好相反的，这里没处理在上时
     */
    public void flowUp() {
        if (K > h + A / 2) {
            print("--return---" + A);
            return;
        }
        print("-----" + A);
        K += 20;

    }


    class Wave {
        int color;
        int alpha;
        //波形的初象
        float t;
        Path path;

        public Wave(int color, int alpha, float t) {
            this.color = color;
            this.alpha = alpha;
            this.t = t;
            path = new Path();
        }
    }

    /**
     * 动态配置水波的类，待完成
     */
    class WaveParam{

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawBorder(Canvas canvas) {
        if (showBorder) {
            mpaint.setColor(mcolor);
            mpaint.setStrokeWidth(1);
            mpaint.setStyle(Paint.Style.STROKE);
            canvas.drawArc(radiusX - raduis, radiusY - raduis, radiusX + raduis, radiusY + raduis, 0, 360, false, mpaint);
        }
    }

    /**
     * 是否作为球形 外部调用
     * @param asBall
     * @param showBorder
     * @param borderColor
     */
    public void showAsBall(boolean asBall, boolean showBorder, int borderColor) {
        this.asBall = asBall;
        this.showBorder = showBorder;
        this.borderColor = borderColor;

    }


    /**
     * 每次创建一个ValueAnimator时都要调用，用来做线程处理
     * @param va
     */
    public void addAnimator(ValueAnimator va) {
        animators.add(va);
    }

    public void removeAnimator(ValueAnimator va) {
        animators.remove(va);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void stopAnimators() {
        for (ValueAnimator valueAnimator:animators) {
            if (valueAnimator!=null&&valueAnimator.isRunning()) {
                valueAnimator.pause();
            }
        }
    }
    public void startAnimators() {
        for (ValueAnimator valueAnimator:animators) {
            if (valueAnimator!=null&&!valueAnimator.isRunning()) {
                valueAnimator.start();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimators();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimators();
    }
}