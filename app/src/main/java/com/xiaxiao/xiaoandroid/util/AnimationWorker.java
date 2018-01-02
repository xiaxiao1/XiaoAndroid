package com.xiaxiao.xiaoandroid.util;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

/**
 * Created by xiaxiao on 2017/1/12.
 */

public class AnimationWorker {
    private final long DEFAULT_DURATION=200;
    View targetView;

    public static AnimationWorker getInstance(){
        return new AnimationWorker();
    }
    public AnimationWorker with(View targetView) {
        this.targetView = targetView;
        return this;
    }
    public void scale(final int fromW, final int fromH, int toW, int toH) {
        final float radio=(float)(toH-fromH)/(toW-fromW);
        final ViewGroup.LayoutParams params = targetView.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(fromW, toW);
        valueAnimator.setDuration(DEFAULT_DURATION);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int t = (int) animation.getAnimatedValue();
                params.width=t;
                params.height=(int)(fromH+(t-fromW)*radio);
//                params.height=t;
                targetView.setLayoutParams(params);
            }
        });
        valueAnimator.start();
/*
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "alpha", 0.1f, 1.0f);
        animator.setDuration(1000);
        animator.start();*/

    }

    public void test() {

        Animation animation = new ScaleAnimation(200, 20, 400, 40);
        animation.setDuration(1000);
//        Util.L("fafafdafafafafa");
        targetView.startAnimation(animation);
    }
}
