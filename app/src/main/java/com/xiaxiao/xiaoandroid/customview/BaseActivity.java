package com.xiaxiao.xiaoandroid.customview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.xiaxiao.xiaoandroid.R;
import com.xiaxiao.xiaoandroid.runtimepermission.RuntimePermissionsManager;
import com.xiaxiao.xiaoandroid.util.XiaoUtil;

public abstract  class BaseActivity extends AppCompatActivity {
    private LinearLayout parentLinearLayout;//把父类activity和子类activity的view都add到这里
    private CustomTopBar mCustomTopBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RuntimePermissionsManager runtimePermissionsManager;
    public static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initContentView();
    }

    /**
     * 初始化contentview,为所有的activity提供顶部栏和下拉刷新功能
     */
    private void initContentView() {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        mCustomTopBar = new CustomTopBar(this);
        linearLayout.addView(mCustomTopBar);
        View v=LayoutInflater.from(this).inflate(R.layout.activity_base, linearLayout, true);
        viewGroup.addView(linearLayout);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_base);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshing();
            }
        });
        parentLinearLayout=(LinearLayout)v.findViewById(R.id.base_content_root_ll);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);

    }

    @Override
    public void setContentView(View view) {

        parentLinearLayout.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {

        parentLinearLayout.addView(view, params);

    }


    /**
     * 设置顶部栏显示
     * @param visibility
     */
    public void setmCustomTopBarVisibility(int visibility) {
        mCustomTopBar.setVisibility(visibility);
    }

    public void setTitleLeftAction(View.OnClickListener onClickListener) {
        mCustomTopBar.getLeftImageView().setOnClickListener(onClickListener);

    }
    public void setTitleRightAction(View.OnClickListener onClickListener) {
        mCustomTopBar.getRightImageView().setOnClickListener(onClickListener);
    }

    public void setBarTitle(String title) {
        mCustomTopBar.setTitle(title);
    }

    public void startRefresh() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
//            mSwipeRefreshLayout.se
        }
    }

    public void setLeftImage(int drawableId) {
        mCustomTopBar.setLeftImg(drawableId);
    }
    public void stopRefresh() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
//            mSwipeRefreshLayout.se
        }
    }

    public void setRefreshEnable(boolean refreshEnable) {
        mSwipeRefreshLayout.setEnabled(refreshEnable);
    }

    public abstract void onRefreshing();

    public RuntimePermissionsManager getRuntimePermissionsManager() {
        if (runtimePermissionsManager==null) {
            runtimePermissionsManager = new RuntimePermissionsManager(this, false);
        }
        return runtimePermissionsManager;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        runtimePermissionsManager.handle(requestCode, permissions, grantResults);
    }
}