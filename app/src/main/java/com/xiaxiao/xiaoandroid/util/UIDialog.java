package com.xiaxiao.xiaoandroid.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.xiaxiao.xiaoandroid.R;


/**
 * Created by Administrator on 2016/11/4.
 */
public class UIDialog {
    private boolean isShow=false;
    private ProgressDialog progressDialog;
    AlertDialog kdialog;
    AlertDialog takePhotoDialog;
    private Context context;
    public UIDialog(Context context) {
        this.context = context;
    }
    public void showWaitDialog() {
        progressDialog = ProgressDialog.show(context, "", "等会哦", false, true, null);
        isShow=true;
    }
    public void showWaitDialog(String message) {
        progressDialog = ProgressDialog.show(context, message, "等会哦", false, true, null);
        isShow=true;
    }

    public void dismissWaitDialog() {
        if (isShow) {
            progressDialog.dismiss();
        }
    }
    public void dismissTakePhotoDialog() {
        if (takePhotoDialog!=null&&takePhotoDialog.isShowing()) {
            takePhotoDialog.dismiss();
        }
    }

    public void showTakePhotoDialog(final CustomDialogListener customDialogListener) {
        if (takePhotoDialog!=null) {
            takePhotoDialog.show();
            return;
        }
        TextView takePhoto_tv;
        TextView fromGallery_tv;
        TextView cancle_tv;
        View view = View.inflate(context, R.layout.dialog_view_choose_photo, null);
        takePhoto_tv = (TextView) view.findViewById(R.id.take_photo_tv);
        fromGallery_tv = (TextView) view.findViewById(R.id.from_gallery_tv);
        cancle_tv = (TextView) view.findViewById(R.id.cancle_tv);
        if (customDialogListener!=null) {


            cancle_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissTakePhotoDialog();
                    customDialogListener.onItemClick(2);
                }
            });
            takePhoto_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Util.toast(context,"take photo");
                    dismissTakePhotoDialog();
                    customDialogListener.onItemClick(0);
                }
            });
            fromGallery_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Util.toast(context,"from gallery");
                    dismissTakePhotoDialog();
                    customDialogListener.onItemClick(1);
                }
            });
        }
        takePhotoDialog=new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();

        takePhotoDialog.getWindow().setBackgroundDrawableResource(R.drawable.whrite_solid_bg);
        takePhotoDialog.show();
        //一定得在show完dialog后来set属性
        WindowManager.LayoutParams lp = takePhotoDialog.getWindow().getAttributes();
        lp.width = context.getResources().getDimensionPixelSize(R.dimen.dialog_width);
        takePhotoDialog.getWindow().setAttributes(lp);
    }

    public void showAddNoteDialog(final CustomDialogListener customDialogListener) {

        TextView addNote_tv;

        View view = View.inflate(context, R.layout.dialog_view_add_note, null);
        addNote_tv = (TextView) view.findViewById(R.id.dialog_add_note_tv);

        final AlertDialog mdialog=new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();

        if (customDialogListener!=null) {

            addNote_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialogListener.onItemClick(0);
                    mdialog.dismiss();
                }
            });

        }

        mdialog.getWindow().setBackgroundDrawableResource(R.drawable.whrite_solid_bg);
        mdialog.show();
        //一定得在show完dialog后来set属性
        WindowManager.LayoutParams lp = mdialog.getWindow().getAttributes();
        lp.width = context.getResources().getDimensionPixelSize(R.dimen.dialog_width);
        mdialog.getWindow().setAttributes(lp);
    }

    /*public void showChooseTypeDialog(String text1,String text2, final CustomDialogListener customDialogListener) {

        TextView t1;
        TextView t2;

        View view = View.inflate(context, R.layout.dialog_view_choose_type, null);
        t1 = (TextView) view.findViewById(R.id.t1);
        t2 = (TextView) view.findViewById(R.id.t2);
        t1.setText(text1);
        t2.setText(text2);

        final AlertDialog mdialog=new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();

        if (customDialogListener!=null) {

            t1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialogListener.onItemClick(0);
                    mdialog.dismiss();
                }
            });
            t2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialogListener.onItemClick(1);
                    mdialog.dismiss();
                }
            });

        }

        mdialog.getWindow().setBackgroundDrawableResource(R.drawable.view_white_bg);
        mdialog.show();
        //一定得在show完dialog后来set属性
        WindowManager.LayoutParams lp = mdialog.getWindow().getAttributes();
        lp.width = context.getResources().getDimensionPixelSize(R.dimen.dialog_width);
        mdialog.getWindow().setAttributes(lp);
    }*/



    public interface CustomDialogListener{
        public void onItemClick(int index);
    }
}
