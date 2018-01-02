package com.xiaxiao.xiaoandroid.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * 修改状态栏透明的
 */
public class StatusUtil {

	public static void setStatusBar ( Activity a )
	{
		View v = getRootView(a);
		if ( v != null )
//		v.setFitsSystemWindows(true);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(a, true);
		}
//
//		a.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//		SystemBarTintManager tintManager = new SystemBarTintManager(a);
//		tintManager.setStatusBarTintEnabled(true);
//		tintManager.setStatusBarTintResource(R.color.text_color_blue);
	}
	
	public static void setStatusBarDefult (Activity a)
	{
		View v = getRootView(a);
		if ( v != null )
		v.setFitsSystemWindows(true);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(a, true);
		}

//		SystemBarTintManager tintManager = new SystemBarTintManager(a);
//		tintManager.setStatusBarTintEnabled(true);
//		tintManager.setStatusBarTintResource(R.color.black);
	}
	
	private static View getRootView(Activity context)
	{
		return ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
	}
	
	@TargetApi(19)
	private static void setTranslucentStatus(Activity a, boolean on) {
		Window win = a.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
	
	
}
