package com.xiaxiao.xiaoandroid.thirdframework.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Environment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xiaxiao.xiaoandroid.R;
import com.xiaxiao.xiaoandroid.util.XiaoUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by xiaxiao on 2016/12/28.
 */
public class GlideHelper {

    SimpleTarget<Bitmap> bitmapSimpleTarget=new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

        }
    };

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.tab2)
                .into(imageView);

    }
    public static void loadGifImage(final Context context, String url, final ImageView imageview, final OnGlideGIFListener onGlideGIFListener) {
        Glide.with(context)
                .load(url)
//                .crossFade()
                .asGif()
                .toBytes()
                .into(new SimpleTarget<byte[]>() {
                    @Override
                    public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {
                        if (resource==null||resource.length<1) {
                            onGlideGIFListener.failed();
                            return;
                        }
                        File f = new File(Environment.getExternalStorageDirectory()+"/miaowu", System.currentTimeMillis()+"sss.gif");
                        if (!f.exists()) {
                            try {
                                f.createNewFile();
                                OutputStream out= null;
                                out = new FileOutputStream(f);
                                out.write(resource);
                                out.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                XiaoUtil.l("save file error:"+e.getMessage());
                            }
                        }
                        onGlideGIFListener.success(f);

                        Glide.with(context)
                                .load(f)
                                .asGif()
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .error(R.drawable.tab2)
                                .into(imageview);

                    }
                });

    }
    public static void loadImageAsBitmap(Context context, String url, final ImageView img, final OnGlidePNGListener onGlidePNGListener) {
        Glide.with(context)
                .load(url)
                .asBitmap()
//.diskCacheStrategy(DiskCacheStrategy.SOURCE)

                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (resource==null) {
                            onGlidePNGListener.failed();
                            img.setImageResource(R.drawable.tab2);
                            return;
                        }
                        img.setImageBitmap(resource);
                        onGlidePNGListener.success(resource);
                    }
                });

    }
    public static void loadImage(Context context, String url, ImageView imageView,int defaultResId) {
        Glide.with(context)
                .load(url)
//                .placeholder(defaultResId)
                .error(defaultResId)
//                .crossFade()
                .into(imageView);

    }
    public static void loadImageWithFitHeight(Context context, String url,  int defaultResId, final OnGlideListener onGlideListener) {

        SimpleTarget simpleTarget=new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                if (onGlideListener!=null) {
                    Bitmap result =Bitmap.createBitmap(resource.getWidth(), resource.getHeight(), Bitmap.Config.ARGB_8888);

                    Canvas canvas = new Canvas(result);
                    Paint paint = new Paint();
                    paint.setShader(new BitmapShader(resource, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
                    paint.setAntiAlias(true);
                    RectF rectF = new RectF(0f, 0f, resource.getWidth()-10, resource.getHeight()-10);
                    canvas.drawRoundRect(rectF, 6, 6, paint);
//                    canvas.drawRect(rectF,paint);
                    resource.recycle();
//                    onGlideListener.onResourceReady(result,glideAnimation);
                }
                XiaoUtil.l(resource.getHeight()+"");
            }
        };
        Glide.with(context)
                .load(url)
                .asBitmap()
//                .placeholder(defaultResId)
//                .error(defaultResId)
//                .crossFade()
//                .transform(new GlideRoundTransform(context,10))
                .into(simpleTarget);



    }
}

/*
*
* 在Glide文档中找了半天没发现加载Gif的方式.然后通过基本的用法去加载:
[java] view plain copy
Glide.with(MainActivity.this).load(url).asGif().into(imageView);
    发现网络也没有获取,翻了一连接才找到:https://groups.google.com/forum/#!msg/glidelibrary/fZnIK2IW7cQ/lnBWNMsklI4J.然后给Github上面提交了issue后,大家都有这样的问题.
    解决方式:

[java] view plain copy
Glide.with(MainActivity.this).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    为其添加缓存策略,其中缓存策略可以为:Source及None,None及为不缓存,Source缓存原型.如果为ALL和Result就不行.然后几个issue的连接:
https://github.com/bumptech/glide/issues/513
https://github.com/bumptech/glide/issues/281
https://github.com/bumptech/glide/issues/600
    然后就可以愉快的加载Gif了,具体原因还在分析,不得不说Glide很不错的加载,绑定了各个生命周期,使其尽量避免内存泄露.
* */
