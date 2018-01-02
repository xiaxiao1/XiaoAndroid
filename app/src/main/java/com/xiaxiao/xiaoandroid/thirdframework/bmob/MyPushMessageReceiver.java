package com.xiaxiao.xiaoandroid.thirdframework.bmob;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xiaxiao.xiaoandroid.util.XiaoUtil;

import cn.bmob.push.PushConstants;

/**
 * Created by xiaxiao on 2017/5/4.
 */

public class MyPushMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            XiaoUtil.l("客户端收到推送内容："+intent.getStringExtra("msg"));
        }
    }

}
