package com.xiaxiao.xiaoandroid.bean;

import java.io.File;
import java.net.URI;

/**
 * Created by xiaxi on 2017/4/23.
 */
public class PicFile extends File {
    int index;
    public PicFile(String dirPath, String name) {
        super(dirPath, name);
    }

    public PicFile(File dir, String name) {
        super(dir, name);
    }

    public PicFile(String path) {
        super(path);
    }

    public PicFile(URI uri) {
        super(uri);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
