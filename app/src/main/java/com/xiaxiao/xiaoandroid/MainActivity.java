package com.xiaxiao.xiaoandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import com.xiaxiao.xiaoandroid.Listener.OnDoubleClickListener;
import com.xiaxiao.xiaoandroid.bean.Article;
import com.xiaxiao.xiaoandroid.customview.BaseActivity;
import com.xiaxiao.xiaoandroid.customview.bannerview.XXBannerAdapter;
import com.xiaxiao.xiaoandroid.customview.bannerview.XXBannerView;
import com.xiaxiao.xiaoandroid.customview.veil.Veil;
import com.xiaxiao.xiaoandroid.roundcorner.RoundCornerImageView;
import com.xiaxiao.xiaoandroid.thirdframework.bmob.BmobListener;
import com.xiaxiao.xiaoandroid.thirdframework.bmob.BmobServer;
import com.xiaxiao.xiaoandroid.util.XiaoUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class MainActivity extends BaseActivity {

    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView tv6;
    XXBannerView bannerView;
    XXBannerAdapter<String> myBannerAdapter;
    List<String> datas;
    RoundCornerImageView roundCornerImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        bannerView = (XXBannerView) findViewById(R.id.banner);
        roundCornerImageView = (RoundCornerImageView) findViewById(R.id.roundImg);
        datas = new ArrayList<>();
        datas.add("11111111111111111111");
        datas.add("222222222222222222222");
        datas.add("33333333333333333333333");
        datas.add("44444444444444444444444");
        datas.add("5555555555555555555555");



       /* myBannerAdapter=new XXBannerAdapter<String>(this,datas) {
            TextView tv;

            @Override
            public int loadItemLayout() {
                return R.layout.item_img;
            }

            @Override
            public Holder createItemHolder(View view) {
                return null;
            }

            @Override
            public void afterItemInstantiated(Holder holder, String obj, int position) {

            }

            @Override
            public void onItemInstantiated(View item, String obj, int position) {
                tv = (TextView) item.findViewById(R.id.tv);
                tv.setText(obj);
            }
        };
        bannerView.setAdapter(myBannerAdapter);*/
       /* myBannerAdapter=new XXBannerAdapter(this,R.layout.item_img,datas) {
            @Override
            public void onItemInstantiated(View item, Object obj, int position) {

            }
        };*/

        tv2.setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
                XiaoUtil.l("单击。。。。");
            }

            @Override
            public void onDoubleClick(View view) {
                XiaoUtil.l("双双双击。。。。");
            }
        });
        SpanTest();


       /* BmobIniter.init(getApplicationContext());
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation().save();
        // 启动推送服务
        BmobPush.startWork(this);
//        getRuntimePermissionsManager().
        Map p = new HashMap(5);
        List l = new ArrayList(5);

        //一个下线程安全的map
        Map m = Collections.synchronizedMap(new HashMap());
        Context context1=getApplicationContext();
        Context context2 = getBaseContext();
        Context context3 = this;
//        AlertDialog.Builder()*/
    }

    @Override
    public void onRefreshing() {

    }

    public void toTestactivity(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }
    public void getArticles(View view) {
        BmobServer bmobServer = new BmobServer.Builder(this).enableDialog(false).build();
        bmobServer.getArticles(0, new BmobListener() {
            @Override
            public void onSuccess(Object object) {
                List<Article> articleList = (List<Article>) object;
                XiaoUtil.toast(articleList.get(10).getName());
            }

            @Override
            public void onError(BmobException e) {

            }
        });

       /* XXBannerAdapter<String> myPagerAdapter=new XXBannerAdapter<String>(this,new ArrayList<String>()) {

            @Override
            public int loadItemLayout() {
                return R.layout.bottom_tab_item;
            }

            @Override
            public void onItemInstantiated(View item, String obj, int position) {

            }
        };*/
    }


    public void SpanTest() {
        BulletSpan span = new BulletSpan(80, Color.RED);
        SpannableString spannableString1 = new SpannableString("今天天气好晴朗");
        SpannableString spannableString2 = new SpannableString("今天天气好晴朗");
        SpannableString spannableString3 = new SpannableString("今天天气好晴朗");
        SpannableString spannableString4 = new SpannableString("今天天气好晴朗");
        SpannableString spannableString5 = new SpannableString("今天天气好晴朗");
        SpannableString spannableString6 = new SpannableString("今天天气好晴朗");
        spannableString1.setSpan(span,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv1.setText(spannableString1);

        ImageSpan imageSpan = new ImageSpan(this, R.drawable.tab4, DynamicDrawableSpan.ALIGN_BOTTOM);
        spannableString2.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv2.setText(spannableString2);

        SpannableStringBuilder ssb2 = new SpannableStringBuilder("今天天气好晴朗");
        ssb2.setSpan(imageSpan, 1, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ssb2.setSpan(span,0,1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//bitmap.recycle();
        tv3.setText(ssb2);

    }


    int haha=0;
    public void setVeil(View view) {
        Veil veil = roundCornerImageView.getVeil();
        if (haha == 0) {
            veil.setVeil(0.4f, "#ff0000");
            haha++;
        } else {
            veil.clearVeil();

        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
