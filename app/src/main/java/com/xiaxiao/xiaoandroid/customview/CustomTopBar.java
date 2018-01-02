package com.xiaxiao.xiaoandroid.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaxiao.xiaoandroid.R;

/**
 * Created by xiaxiao on 2017/4/27.
 * 自定义的activity顶部栏
 * 一定包含的是左右两个图标，一个中间titile
 * 可以动态添加左右两个textview ,中间一个图标位
 */

public class CustomTopBar extends LinearLayout {

    View root;
    LinearLayout left_ll;
    LinearLayout right_ll;
    LinearLayout middle_ll;
    ImageView left_img;
    ImageView middle_img;
    ImageView right_img;
    TextView left_tv;
    TextView right_tv;
    TextView middle_tv;
    public CustomTopBar(Context context) {
        super(context);
        // 加载布局
       root= LayoutInflater.from(context).inflate(R.layout.customview_top_bar, this);
//        root = View.inflate(context, R.layout.customview_top_bar, null);
        left_ll = (LinearLayout) root.findViewById(R.id.left_ll);
        right_ll = (LinearLayout) root.findViewById(R.id.right_ll);
        middle_ll = (LinearLayout) root.findViewById(R.id.middle_ll);
        left_img = (ImageView) root.findViewById(R.id.left_img);
        right_img = (ImageView) root.findViewById(R.id.right_img);
        middle_tv = (TextView) root.findViewById(R.id.middle_tv);
    }

    public void setTitle(String title) {
        middle_tv.setText(title);
    }

    public void setLeftImg(int drawableId) {
        left_img.setImageResource(drawableId);

    }
    public void setRightImg(int drawableId) {
        left_img.setImageResource(drawableId);

    }

    public ImageView getLeftImageView() {
        return this.left_img;
    }
    public ImageView getRightImageView() {
        return this.right_img;
    }
    public ImageView getMiddleImageView() {
        return this.middle_img;
    }
}
