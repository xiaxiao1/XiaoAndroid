package com.xiaxiao.xiaoandroid.thirdframework.bmob;

import android.content.Context;

import cn.bmob.v3.Bmob;


/**
 * Created by xiaxi on 2016/10/10.
 */
public class BmobIniter {
    public  static void init(Context context) {
        //第一：默认初始化
        Bmob.initialize(context, "2f0b843f4fa2d170216ff309cc123300");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
    }
}
