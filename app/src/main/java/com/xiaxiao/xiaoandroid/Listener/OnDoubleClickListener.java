package com.xiaxiao.xiaoandroid.Listener;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.UiThread;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xiaxiao on 2017/3/24.
 * 双击和单击的事件监听
 */

public abstract class OnDoubleClickListener implements View.OnClickListener{
   boolean firstClick=false;
    boolean secondClick=false;
    Activity activity;

    @Override
    public void onClick(final View v) {
        if (firstClick) {
            secondClick = true;
        }
        firstClick = true;
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (!firstClick) {
                    return;
                }
                if (secondClick) {
                    //double
                    Log.i("xx", "double");
                    activity=(Activity)v.getContext();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onDoubleClick(v);
                        }
                    });

                } else {
                    //single
                    Log.i("xx", "single");
                    activity=(Activity)v.getContext();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onSingleClick(v);
                        }
                    });
                }
                firstClick = false;
                secondClick = false;
            }
        };
        timer.schedule(timerTask, 200);
    }

    public abstract void onSingleClick(View view);
    public abstract void onDoubleClick(View view);
}
