package com.xiaxiao.xiaoandroid.runtimepermission;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.xiaxiao.xiaoandroid.util.XiaoUtil;

import java.util.ArrayList;

/**
 * 运行时权限申请管理类，负责运行时对权限的单个或多个请求，并对请求结果做出响应，
 * 需要在调用的类中重载onRequestPermissionsResult方法：
 * @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
    grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        runtimePermissionsManager.handle(requestCode, permissions, grantResults);
    }

同时要在activity的回调中添加：
 @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
     hanleWriteSettings(int requestCode, int resultCode, Intent data);
 }
 * Created by xiaxiao on 2016/10/20.
 */
public class RuntimePermissionsManager {
    private Activity activity;
    private int requestCode=8888;
    private int REQUEST_CODE_WRITE_SETTINGS=7777;

    private RequestCallback requestCallback;
    //变量为true时， 刚进程序，申请的权限没有被全部通过时，则退出程序。
    private boolean exitWhileNotAllPermissionsGranted=false;
    //请求权限中是否有Write_settings权限
    private  boolean haveWriteSettingPermission=false;

    /**
     * 本程序中需要用到的权限
     */
    public final static String[] requestedPermissions= new String[]{
            Manifest.permission.ACCESS_NETWORK_STATE
            ,Manifest.permission.CALL_PHONE
            ,Manifest.permission.CAMERA
            ,Manifest.permission.ACCESS_COARSE_LOCATION
            ,Manifest.permission.WRITE_EXTERNAL_STORAGE
            ,Manifest.permission.RECORD_AUDIO
            ,Manifest.permission.READ_SMS
            ,Manifest.permission.WRITE_SETTINGS

    };

    /**
     * 初始化管理类
     * @param activity 当前activity
     * @param exitWhileNotAllPermissionsGranted 是否当未授权时直接退出 true:是
     */
    public RuntimePermissionsManager(Activity activity,boolean exitWhileNotAllPermissionsGranted) {
        this.exitWhileNotAllPermissionsGranted=exitWhileNotAllPermissionsGranted;
        this.activity=activity;
    }

    /**
     * 检查所给的权限是否已被授权
     * @param permission 要检查的权限名字
     * @return true:已被授权；false:未被授权
     */
    public boolean checkPermission(String permission) {
        return  permission!=null&&(ContextCompat.checkSelfPermission(activity,permission)== PackageManager.PERMISSION_GRANTED);
    }

    /**
     * 请求程序所有运行时需要申请的权限
     */
    public void requestPermissions(String[] permissions, RequestCallback requestCallback) {
        this.requestCallback=requestCallback;
        requestPermissions(permissions);
    }

    /**
     * 请求授权,限内部调用
     * @param permissions
     * 当不为null时，表示请求传入的权限，
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissions(String[] permissions) {
        if (permissions==null) {
            return;
        }
        //过滤出目前还未被授权的权限
        String[] noGrantedPermissions;
        ArrayList<String> l=new ArrayList<String>();
        for (String s:permissions) {
            if (!checkPermission(s)) {
                l.add(s);
            }
        }

        //如果请求的权限里面有Write_Settings,捡出来，单独处理
        pickUpWriteSetting(l);

        if (l.size()==0) {
            //这种情况是在进入app之前其他的权限已经都被授权了，只有write_settings待请求
            if (haveWriteSettingPermission&&!Settings.System.canWrite(activity)) {
                requestWriteSettingsPermission();
            }
            else {
                if (requestCallback!=null) {
                    requestCallback.requestSuccess();
                }
            }
            return;
        }
        //一次性请求所有未被授权的权限
        noGrantedPermissions=(String[])l.toArray(new String[l.size()]);
        ActivityCompat.requestPermissions(activity,
                noGrantedPermissions,
                requestCode);
    }

    /**
     * 请求指定的单个权限
     * @param permission 给定的权限
     */
    private void requestPermission(String permission) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            showDialog();
        } else {
            requestPermissions(new String[]{permission});
        }
    }

    /**
     * 处理权限申请的回调方法
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public final void handle(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCallback==null) {
            return;
        }
        //对请求结果进行过滤
        if (requestCode == this.requestCode) {
            int grantedNum=permissions.length;
            Log.i("xx","permissions size:"+permissions.length+"  grantResult size:"+grantResults.length);
            for (int i=0;i<grantResults.length;i++) {
                int result=grantResults[i];
                if (result != PackageManager.PERMISSION_GRANTED) {
                    grantedNum--;
                } else {
                    Log.i("xx","permissions :"+permissions[i]);
                }
            }

            //如果有未被授权的请求
            if (grantedNum < permissions.length) {

                //根据开关决定是提示还是直接退出
                if (exitWhileNotAllPermissionsGranted) {
                    showDialog();
                } else {
                    requestCallback.requestFailed();
                }
            } else {//全部通过
                //如果有write_setting权限，则单独处理，此时requestCallback.requestSuccess()方法在write_setting对应的回调方法里调用。
                //保持永远在一处处理请求结果
                if (haveWriteSettingPermission) {
                    requestWriteSettingsPermission();
                } else {
                    requestCallback.requestSuccess();
                }
            }


        } else {
            Log.i("xx","error:requestCode not match");
        }

    }

    /**
     * 检测权限，如未被授权则申请，并继续处理业务
     * @param permission 需要使用的权限
     * @param requestCallback 回调类，负责处理具体业务，包含权限已被授权和申请后的情况
     *                        ！！！！！！！这个方法没有对修改系统权限过滤处理，以后注意完善！！！！！！！！！！！
     */
    public void workwithPermission(String permission, RequestCallback requestCallback) {
        if (permission==null||requestCallback==null) {
            return;
        }
        this.requestCallback=requestCallback;
        if (checkPermission(permission)) {
            requestCallback.requestSuccess();
        } else {
            requestPermission(permission);

        }

    }

    /*
    * Manifest.permission.WRITE_SETTINGS权限需要单独处理
    * */
    private void pickUpWriteSetting(ArrayList<String> permissions) {
        for (String p:permissions) {
            if (Manifest.permission.WRITE_SETTINGS.equals(p)) {
                haveWriteSettingPermission=true;
                permissions.remove(p);
            }
        }
    }

    /*
    * 请求write_setting权限
    * */
    private void requestWriteSettingsPermission() {
        XiaoUtil.toast("请允许修改系统设置");
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS );
    }
    /*
    * 处理请求write_settings权限的回调方法，需要在对应的activity的onActivityResult方法中添加此方法
    * */
    @TargetApi(Build.VERSION_CODES.M)
    public void handleWriteSettings(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_WRITE_SETTINGS) {
            if (Settings.System.canWrite(activity)) {
                requestCallback.requestSuccess();
            } else {
                XiaoUtil.toast("未获得修改系统设置的权限，退出应用");
                activity.finish();
            }
        }
    }
    private void showDialog(){
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setMessage("下一步操作需要开启相应的权限，不开启将无法正常工作！")
                .setCancelable(false)
                .setPositiveButton("开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i=new Intent();
                        i.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        i.setData(Uri.fromParts("package", activity.getPackageName(), null));
                        activity.startActivity(i);
                        activity.finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (exitWhileNotAllPermissionsGranted) {
                            activity.finish();
                        }
                    }
                }).create();
        dialog.show();
    }

    public interface RequestCallback {
        public void requestSuccess();
        public void requestFailed();
    }

    /**
     *以下是运行时请求的权限分组
     */
    /*public final static String[] group_CONTACTS={
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.GET_ACCOUNTS,
        Manifest.permission.READ_CONTACTS};

    public final static String[] group_PHONE={
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.WRITE_CALL_LOG,
        Manifest.permission.USE_SIP,
        Manifest.permission.PROCESS_OUTGOING_CALLS,
        Manifest.permission.ADD_VOICEMAIL};

    public final static String[] group_CALENDAR={
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.WRITE_CALENDAR};

    public final static String[] group_CAMERA={
        Manifest.permission.CAMERA};

    public final
    static String[] group_SENSORS={
        Manifest.permission.BODY_SENSORS};

    public final static String[] group_LOCATION={
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};

    public final static String[] group_STORAGE={
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public final static String[] group_MICROPHONE={
        Manifest.permission.RECORD_AUDIO};

    public final static String[] group_SMS={
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_WAP_PUSH,
        Manifest.permission.RECEIVE_MMS,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.SEND_SMS,
        //Manifest.permission.READ_CELL_BROADCASTS
        };
*/
}

























































