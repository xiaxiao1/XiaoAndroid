package com.xiaxiao.xiaoandroid.customview.bannerview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xiaxiao.xiaoandroid.R;
import com.xiaxiao.xiaoandroid.customview.BaseActivity;
import com.xiaxiao.xiaoandroid.util.XiaoUtil;

import java.util.List;

/**
 * Created by xiaxiao on 2017/5/4.
 * 自定义的顶部banner view.可以循环滑动，可以在对应的adapter中更换不同的item view文件
 * 可以更换下标图片，实现自动轮播，
 * 可以切换正常模式和多图显示模式
 */

public class XXBannerView extends RelativeLayout {
    //bannerview 根view
    View mRootView;
    Context mContext;
    //具体操作的viewPager
    ViewPager mViewpager;
    //viewpager装载的View列表，为了实现循环滑动，其size比对应的数据列表大2
    static List<View> itemViews;
    //下标区域
    LinearLayout dots_ll;
    //下标数组
    ImageView[] dotImgs;
    int[] dotDrawables;
    //用来解决viewpager的滑动和当前activity的下拉刷新冲突时，标记一次完整的当前触摸事件的
    boolean theMession=false;
    //item之间的间距
    int pageMargin=0;
    //item的宽度占屏幕宽度的百分比
    float pageWidthPercentage=1f;
    //是否显示多图，模式
    boolean multiple=false;
    //自动轮播线程
    Thread autoScrollThread;
    //是否在轮播
    boolean autoScroll=false;
    //用于解决viewpager和当前页面的滑动冲突问题。即左右滑动的时候可能同时出发上下滑动
    boolean fixSlideDisturb=false;
    //设置item点击事件
    OnItemPagerClickListener onItemPagerClickListener;
//    List<Object> mDatas;
//    int itemViewLayoutId;

    public XXBannerView(Context context) {
        super(context);
        initBanner(context);
    }

    public XXBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBanner(context);
    }

    public XXBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBanner(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public XXBannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initBanner(context);
    }

    private void initBanner(Context context) {
        mContext = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.banner_view, this);
        mViewpager = (ViewPager) mRootView.findViewById(R.id.viewpager);
        dots_ll = (LinearLayout) mRootView.findViewById(R.id.index_dot_ll);
        dotDrawables=new int[]{R.drawable.tab2,R.drawable.tab2_off};

        //循环滑动的原理在这个监听事件中实现的
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                XiaoUtil.l("onPageScrolled "+position);
            }

            @Override
            public void onPageSelected(int position) {
//                XiaoUtil.l("onPageSelected "+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                XiaoUtil.l("onPageScrollStateChanged  "+mViewpager.getCurrentItem());
                int currentItemIndex=mViewpager.getCurrentItem();
                if (state==ViewPager.SCROLL_STATE_IDLE&&currentItemIndex+1==itemViews.size()) {
                    mViewpager.setCurrentItem(1, false);

                }
                if (state==ViewPager.SCROLL_STATE_IDLE&&currentItemIndex==0) {
                    mViewpager.setCurrentItem(itemViews.size()-2, false);

                }
                if (state==ViewPager.SCROLL_STATE_IDLE){
                    if (currentItemIndex==0) {
                        changeDots(dotImgs.length-1);
                    } else if (currentItemIndex + 1 == itemViews.size()) {
                        changeDots(0);
                    } else {
                        changeDots(currentItemIndex-1);
                    }
                }
            }
        });

        //用于处理当手动操作viewpager时禁止轮播,和处理上下左右滑动冲突
        mViewpager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN) {
                    stopAutoScroll();
                }
                if (event.getAction()==MotionEvent.ACTION_MOVE) {
                    stopAutoScroll();
                }
                if (event.getAction()==MotionEvent.ACTION_UP) {
                    startAutoScroll();
                }


                //和处理上下左右滑动冲突,仅针对自定义的BaseActivity
                if (fixSlideDisturb) {
                    if (!theMession) {
                        float x=0;
                        float y=0;
                        if (event.getAction()==MotionEvent.ACTION_DOWN) {
                            x=event.getX();
                            y=event.getY();
                        }
                        if (event.getAction()==MotionEvent.ACTION_MOVE) {
                            if (Math.abs(event.getX()-x) >= Math.abs(event.getY()-y)) {
                                theMession=true;
                                ((BaseActivity)mContext).setRefreshEnable(false);
                            }
                        }


                    }

                    if (event.getAction()==MotionEvent.ACTION_UP) {
                        ((BaseActivity)mContext).setRefreshEnable(true);
                        theMession=false;
                    }
                }

                return false;
            }
        });




    }

    public void setAdapter(XXBannerAdapter pagerAdapter) {
        mViewpager.setAdapter(pagerAdapter);
        pagerAdapter.setXxBannerView(this);
        itemViews = pagerAdapter.getItemViews();
        //处理多图模式下，两侧的item 的点击事件
        //注释掉的原因是adapter中可以设置第二种多图模式
//        if (multiple) {
            handleMultipleClick();
//        }
        initDots();
        //设置viewpager初始化时显示的那个item，因为头尾是用来实现循环的，所以真正的第一个是index=1.
        mViewpager.setCurrentItem(1);
    }

    public OnItemPagerClickListener getOnItemPagerClickListener() {
        return onItemPagerClickListener;
    }

    /**
     * 解决和BaseActivity滑动干扰，比如viewpager左右滑动时还有上下滑的动作时，会触发activity的下拉刷新动作。
     * @param activity
     */
    public void fixSlideDisturbWith(final BaseActivity activity) {
        fixSlideDisturb=true;
    }

    private void initDots() {
        int size = itemViews.size()-2;
        dotImgs = new ImageView[size];
        for (int i = 0; i < size; i++) {
            ImageView dotImg = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(14,0,14,0);
            params.height=mContext.getResources().getDimensionPixelOffset(R.dimen.bannerview_dot_size);
            params.width=mContext.getResources().getDimensionPixelOffset(R.dimen.bannerview_dot_size);
            dotImg.setLayoutParams(params);
            dotImg.setImageResource(dotDrawables[0]);
            dotImgs[i] = dotImg;
            dots_ll.addView(dotImg);
        }
        changeDots(0);
    }

    private void changeDots(int currentIndex) {
        for (int i=0;i<dotImgs.length;i++) {
            if (i == currentIndex) {
                dotImgs[i].setImageResource(dotDrawables[0]);
            } else {
                dotImgs[i].setImageResource(dotDrawables[1]);
            }
        }
    }
    public void setDotDrawables(int onDrawableId, int offDrawableId) {
        dotDrawables[0] = onDrawableId;
        dotDrawables[1] = offDrawableId;
    }


    /*
    * 设置item之间的间距
    * */
    public void setPageMargin(int margin) {
        pageMargin = margin;
    }

    /**
     * 设置Viewpager宽度占屏幕百分比。。用于实现多图模式
     * @param percentage
     */
    public void setViewPagerWidth(float percentage ){
        if (percentage>0&&percentage<=1) {
            pageWidthPercentage = percentage;
        }
    }

    /**
     * 设置viewpager是否是多图显示模式
     * @param multiple
     */
    public void setMultiplePage(boolean multiple) {
        this.multiple = multiple;
        if (!multiple) {
//            setPageMargin(0);
            setViewPagerWidth(1f);
        }
        ViewGroup.LayoutParams params = mViewpager.getLayoutParams();
        params.width=(int)(mContext.getResources().getDisplayMetrics().widthPixels * pageWidthPercentage);
        mViewpager.setLayoutParams(params);

        mViewpager.setPageMargin(pageMargin);
        mViewpager.setClipChildren(!multiple);
        ((ViewGroup)mViewpager.getParent()).setClipChildren(!multiple);
        // 将父节点Layout事件分发给viewpager，否则只能滑动中间的一个view对象
        if (multiple) {
            mViewpager.setOffscreenPageLimit(4);
            mRootView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return mViewpager.dispatchTouchEvent(event);
                }
            });

        }
    }

    /**
     * 处理多图模式下侧边item和当前item的不同点击响应
     */
    private void handleMultipleClick() {

        for (int i=0;i<itemViews.size();i++) {

            final int finalI = i;

            itemViews.get(i).setOnTouchListener(new OnTouchListener() {
                boolean isClick=false;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction()==MotionEvent.ACTION_DOWN) {
                        isClick = true;
                    }
                    if (event.getAction()==MotionEvent.ACTION_MOVE) {
                        isClick=false;
                    }
                    if (event.getAction()==MotionEvent.ACTION_UP) {
                        if (isClick) {
                            if (finalI == mViewpager.getCurrentItem()) {
                                return false;
                            } else {
                                //如果是两边的，则开始滑动到当前
                                mViewpager.setCurrentItem(finalI,true);
                                return true;
                            }
                        }
                        isClick=false;
                    }

                    return false;
                }
            });
        }
    }

    /**
     * 开启自动轮播
     * 开启后，需要在activity退出时调用clearAutoScroll方法清除轮播线程
     */
    public void startAutoScroll() {
        autoScroll = true;
        XiaoUtil.l(" start autoscroll:"+autoScroll);
        if (autoScrollThread==null) {
            final Runnable uiRunnable=new Runnable() {
                @Override
                public void run() {
                    mViewpager.setCurrentItem((mViewpager.getCurrentItem()+1)%itemViews.size(),true);
                }
            };
            autoScrollThread=new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (autoScroll) {

                            XiaoUtil.l(" Thread--autoscroll:"+autoScroll);
                            ((Activity)mContext).runOnUiThread(uiRunnable);

                            try {
                                Thread.sleep(4000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
            autoScrollThread.start();
        }

    }

    /**
     * 暂停自动轮播
     */
    public void stopAutoScroll() {
        autoScroll = false;
        XiaoUtil.l("autoscroll:"+autoScroll);
    }

    /**
     * 清除自动化轮播线程
     */
    public void clearAutoScroll() {
        autoScroll=false;
        autoScrollThread=null;
    }


    public void setOnItemPagerClickListener(OnItemPagerClickListener onItemPagerClickListener) {
        this.onItemPagerClickListener = onItemPagerClickListener;
    }




     public static abstract class OnItemPagerClickListener implements OnClickListener {
        int position=0;

        /*public void setPosition(int position) {
            this.position = position;
        }*/
        public abstract void onItemPagerClick(View item, int position);
        public  void onClick(View v){
            position = itemViews.indexOf(v)-1;
            onItemPagerClick(v,position);
        }


     }



}
