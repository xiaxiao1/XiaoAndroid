package com.xiaxiao.xiaoandroid.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaxiao.xiaoandroid.R;

/**
 * Created by xiaxi on 2017/4/30.
 * 具体实现的底部导航栏 喵呜
 */
public class MiaoWuBottomTab extends BottomTab {
    int[] tabOnDrawables={R.drawable.test1,R.drawable.tab2,R.drawable.tab3,R.drawable.tab4,R.drawable.test1};
    int[] tabOffDrawables={R.drawable.test2,R.drawable.tab2_off,R.drawable.tab3_off,R.drawable.tab_off,R.drawable.test2};
    String[] texts={"haha1","mama2","gaga3","baba4","kaka5"};
    int colorOn = Color.parseColor("#f56857");
    int colorOff = Color.parseColor("#8a8a8a");

    public MiaoWuBottomTab(Context context) {
        super(context);
    }

    public MiaoWuBottomTab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MiaoWuBottomTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MiaoWuBottomTab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void changeTabState(View tab, int selectedIndex) {
        for (int i=0;i<getTabCount();i++) {
            getImageView(getTabs()[i]).setImageResource(tabOffDrawables[i]);
            getTextView(getTabs()[i]).setTextColor(colorOff);
            getTextView(getTabs()[i]).setText(texts[i]);
        }
        getImageView(tab).setImageResource(tabOnDrawables[selectedIndex]);
        getTextView(tab).setTextColor(colorOn);
    }

    @Override
    public int initCount() {
        return 5;
    }

    @Override
    public int initItemLayoutId() {
        return R.layout.bottom_tab_item;
    }

    @Override
    public void withInits() {

    }

    public ImageView getImageView(View view) {
        return (ImageView) view.findViewById(R.id.bottom_tab_item_img);
    }

    public TextView getTextView(View view) {
        return (TextView) view.findViewById(R.id.bottom_tab_item_tv);
    }
}
