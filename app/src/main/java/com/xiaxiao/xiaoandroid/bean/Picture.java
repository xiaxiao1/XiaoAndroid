package com.xiaxiao.xiaoandroid.bean;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by xiaxi on 2017/4/23.
 */
public class Picture {
    public static final int PICTURE_TYPE_GIF=0;
    public static final int PICTURE_TYPE_PNG=1;
    int index;
    int type;
    File gifFile;
    Bitmap pngBitmap;

    public File getGifFile() {
        return gifFile;
    }

    public void setGifFile(File gifFile) {
        this.gifFile = gifFile;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Bitmap getPngBitmap() {
        return pngBitmap;
    }

    public void setPngBitmap(Bitmap pngBitmap) {
        this.pngBitmap = pngBitmap;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
