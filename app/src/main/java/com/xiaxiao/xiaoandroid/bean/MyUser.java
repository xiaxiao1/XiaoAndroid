package com.xiaxiao.xiaoandroid.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by xiaxiao on 2016/12/29.
 */
public class MyUser extends BmobUser {
    BmobFile headImage;

    public BmobFile getHeadImage() {
        return headImage;
    }

    public void setHeadImage(BmobFile headImage) {
        this.headImage = headImage;
    }

    public MyUser() {

    }
}
