package com.xiaxiao.xiaoandroid;

import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaxiao.xiaoandroid.customview.XXDrawableTextView;
import com.xiaxiao.xiaoandroid.customview.bannerview.XXBannerAdapter;
import com.xiaxiao.xiaoandroid.customview.bannerview.XXBannerView;
import com.xiaxiao.xiaoandroid.customview.BaseActivity;
import com.xiaxiao.xiaoandroid.customview.BottomTab;
import com.xiaxiao.xiaoandroid.customview.CommentView;
import com.xiaxiao.xiaoandroid.customview.MiaoWuBottomTab;
import com.xiaxiao.xiaoandroid.thirdframework.glide.GlideHelper;
import com.xiaxiao.xiaoandroid.util.XiaoUtil;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends BaseActivity {
CommentView commentView;
    MiaoWuBottomTab bottomTab;
    MiaoWuBottomTab bottomTab2;
    XXBannerView viewPager;
    List<View> views;
    List<String> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test);
        setRefreshEnable(false);
        XXDrawableTextView xxDrawableTextView;
//        bottomTab = (MiaoWuBottomTab) findViewById(R.id.bottom_tab);
//        bottomTab2 = (MiaoWuBottomTab) findViewById(R.id.bottom_tab2);
//        viewPager = (XXBannerView) findViewById(R.id.viewpager_kl);
        viewPager.setPageMargin(-60);
        viewPager.setViewPagerWidth(0.6f);
        viewPager.setMultiplePage(true);
        bottomTab2.inits();
        bottomTab.setTabItemClickListener(new BottomTab.TabItemClickListener() {
            @Override
            public void onItemClick(int index) {
                XiaoUtil.toast("tab :"+index);
            }
        });

        views = new ArrayList<>();
        datas = new ArrayList<>();
       /* for (int i=0;i<4;i++) {
            datas.add(i + " hahaha");
        }*/
        datas.add("http://img2.imgtn.bdimg.com/it/u=780939278,953464086&fm=11&gp=0.jpg");
        datas.add("http://img0.imgtn.bdimg.com/it/u=194570714,2632603990&fm=11&gp=0.jpg");
        datas.add("http://img5.imgtn.bdimg.com/it/u=2299609415,2213226454&fm=11&gp=0.jpg");
        datas.add("http://img0.imgtn.bdimg.com/it/u=2060780763,2734283137&fm=11&gp=0.jpg");
        datas.add("http://www.wfnews.com.cn/fashion/site1/20100812/1281573380203-3952258161937151863.jpg");
        XXBannerAdapter<String> myPagerAdapter = new XXBannerAdapter<String>(this,datas) {


            @Override
            public int loadItemLayout() {
                return R.layout.item_img;
            }

            @Override
            public Holder createItemHolder(View view) {
                return new Holder<String>(view) {
                    TextView tvv;
                    ImageView imgg;
                    Button btn;
                    @Override
                    public void initDatas(final String obj) {

                        tvv.setText(obj+"   position: ");
                        GlideHelper.loadImage(TestActivity.this,obj,imgg);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                XiaoUtil.toast("还有谁？？？？");
                            }
                        });
                        /*getItemView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                XiaoUtil.toast(obj+"这是重构的代码哦");
                            }
                        });*/
                    }

                    @Override
                    public void initChildren(View view) {
                        tvv= (TextView) view.findViewById(R.id.tv);
                        imgg= (ImageView) view.findViewById(R.id.img);
                        btn = (Button) view.findViewById(R.id.btn);
                        XiaoUtil.l("holder:initChildren");
                    }


                };
            }

            @Override
            public void afterItemInstantiated(Holder holder, final String obj, int position) {


            }


        };
        viewPager.setOnItemPagerClickListener(new  XXBannerView.OnItemPagerClickListener(){
            @Override
            public void onItemPagerClick(View item, int position) {
                XiaoUtil.toast(datas.get(position)+"这是第"+position);
            }
        });
        viewPager.setAdapter(myPagerAdapter);
//        viewPager.fixSlideDisturbWith(this);
        viewPager.startAutoScroll();


//
    }

    boolean theMession=false;
    @Override
    public void onRefreshing() {

        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                    // TODO Auto-generated method stub
                                   XiaoUtil.toast("hahahahaha ok");
                                    stopRefresh();
                                }
                        }, 2000);

    }

    public void stopRefresh(View view) {
        stopRefresh();

    }


    @Override
    protected void onDestroy() {
        viewPager.clearAutoScroll();
        super.onDestroy();
    }
}

