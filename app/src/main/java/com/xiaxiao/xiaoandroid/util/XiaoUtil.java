package com.xiaxiao.xiaoandroid.util;

import android.app.Activity;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.xiaxiao.xiaoandroid.customview.BaseActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类，包含一些非系统性，框架性的工具方法，比如弹出toast，log，，
 * 像对网络操作的一些功能性方法，是相关联的一套的，就不在这个工具类中。
 */
public class XiaoUtil {

    public static int getHEIGHT(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        return height;
    }






    /**
     * 拨打电话
     *
     * @param number
     */
    public static void telPhone(Context context,String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        context.startActivity(intent);
    }



    /**
     * 自定义的TOAST
     */
    public static void customToast(Activity a,int viewId, String s) {
        View v = View.inflate(a, viewId, null);
        Toast toast = new Toast(a.getApplicationContext());
        toast.setView(v);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void toast(String s) {
        Toast.makeText(BaseActivity.mContext,s,Toast.LENGTH_SHORT).show();
    }

    /**
     * 自定义Log
     * @param info 打印信息
     */
    public static void l(String info) {
        Log.i("xx", info);
    }


    /**
     * 字符串转double类型
     * @param str
     * @return
     */
    public static Double string2Double(String str) {
        Double d = -1d;

        try {
            d = Double.valueOf(str);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return d;

    }

    public static int string2int(String str) {
        int i = -1;
        try {
            i = Integer.parseInt(str);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return i;
    }






    /**
     * 判断手机是否有SD卡。
     *
     * @return 有SD卡返回true，没有返回false。
     */
    public static boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }



    //要替换的文字
    private static String colorString = "<font color='#49a34b'>lishi</font>";

    /**
     * 根据搜索的内容， 替换返回回来信息相同文字颜色
     *
     * @param stext 关键字
     * @param info  内容
     * @return
     */
    public static String getSearchText(String stext, String info) {
        if (TextUtils.isEmpty(stext)) {
//			stext = SearchActivity.searchText;
            return info;
        }
        String result = info;
        String tempStr = colorString.replace("lishi", stext);
        result = info.replace(stext, tempStr);
        return result;
    }

    /**
     * 隐藏键盘
     *
     * @param a
     */
    public static void hideSoftInput(Activity a) {

        View view = a.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);

            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    //显示键盘
    public static void showSoftInput(Activity a, EditText view) {
        InputMethodManager inputmanger = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }




    /**
     * 判断当前应用是否是debug状态
     */

    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }



    public static String[] getTimeStamp(String timeS) {
        String[] result = new String[]{"", "",};
        if (TextUtils.isEmpty(timeS)) {
            return result;
        }
        //时间戳转化为Sting或Date
        SimpleDateFormat format = new SimpleDateFormat("MM,dd");
        Long time = new Long(timeS + "000");
        String d = format.format(time);

        result = d.split(",");

        return result;
    }


    /**
     * Url 格式化， 如果没有添加http:// 在头上添加
     *
     * @param url
     * @return
     */
    public static String httpUrlFomat(String url) {
        if (url == null) return url;
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        return url;
    }


    // 安装APK
    public static final void installAPK(Activity activity, String fileURL) {
        try {
            File file = new File(fileURL);
            l("apk path = " + file.getAbsolutePath());
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            String type = "application/vnd.android.package-archive";
            intent.setDataAndType(Uri.fromFile(file), type);
            activity.startActivity(intent);
        } catch (Exception e) {
            l("installAPK Exception = " + e.toString());
        }
    }

    /**
     * Unicode 转汉字
     *
     * @param utfString
     * @return
     */
    public static String convert(String utfString) {
        if (TextUtils.isEmpty(utfString)) {
            return utfString;
        }

        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }

        return sb.toString();
    }


    /**
     * 获取当前时间
     *
     * @return 格式化的当前时间字符串
     */
    public static String getFormatCurrentTime(String formatType) {
        SimpleDateFormat format;
        if (formatType.isEmpty()) {
            format= new SimpleDateFormat("yyyy.MM.dd HH:mm");
        } else {
            format = new SimpleDateFormat(formatType);
        }
        Date now = new Date();
        return format.format(now);


    }




    /**
     * 拷贝文字到剪切板
     *
     * @param a
     * @param str
     */
    public static void copyText(Activity a, String str) {
        if (TextUtils.isEmpty(str)) return;
        ClipData myClip = ClipData.newPlainText("text", str);
        ClipboardManager myClipBoard = (ClipboardManager) a.getSystemService(a.getApplicationContext().CLIPBOARD_SERVICE);
        myClipBoard.setPrimaryClip(myClip);
        XiaoUtil.toast("已复制：" + str);
    }


    /**
     * ImageView宽度固定，高度自适应时，根据和图片的比例计算出ImageView的实际高度
     *
     * @param view         自适应的view
     * @param sourceHieght 原图片的高度
     * @param sourceHieght 原图片的宽度
     * @return 自适应后的view的高度
     */
    public static void setAdaptedHeight(final ImageView view, final int sourceWidth, final int sourceHieght) {
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                int w = view.getMeasuredWidth();
                int h = w * sourceHieght / sourceWidth;
                ViewGroup.LayoutParams p = view.getLayoutParams();
                p.width = w;
                p.height = h;
                view.setLayoutParams(p);
                return true;
            }
        });
    }





    /**
     * 获取popupwindow在当前窗口中的最大高度
     * 防止popupwindow在设置了match_parent时底部被虚拟导航键遮挡，
     * 或者因高度太大 无法参照anchor显示
     *
     * @param anchor 参考View
     * @return
     */
    public static int getPopupwindowHeightInWindow(Activity a,View anchor) {
        int h = XiaoUtil.getHEIGHT(a);
        int[] location = new int[2];
        anchor.getLocationInWindow(location);
        h = h - location[1] - anchor.getHeight();
        return h;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)  。
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 。
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static void print(String s) {
        System.out.println(s);
    }

    public static boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }
}
