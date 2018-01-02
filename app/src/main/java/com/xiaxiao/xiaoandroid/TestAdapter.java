package com.xiaxiao.xiaoandroid;

import android.content.Context;
import android.view.View;

import com.xiaxiao.xiaoandroid.bean.Article;
import com.xiaxiao.xiaoandroid.customview.bannerview.XXBannerAdapter;

import java.util.List;

/**
 * Created by xiaxiao on 2017/9/18.
 */

public class TestAdapter extends XXBannerAdapter<Article> {
    public TestAdapter(Context context, List<Article> datas) {
        super(context, datas);
    }

    @Override
    public int loadItemLayout() {
        return 0;
    }

    @Override
    public Holder createItemHolder(View view) {
        return new TestHolder<>(view);
    }

    @Override
    public void afterItemInstantiated(Holder holder, Article obj, int position) {

    }


    class TestHolder<Article> extends XXBannerAdapter.Holder {
        public TestHolder(View view) {
            super(view);
        }

        @Override
        public void initDatas(Object obj) {

        }

        @Override
        public void initChildren(View view) {

        }
    }
}

