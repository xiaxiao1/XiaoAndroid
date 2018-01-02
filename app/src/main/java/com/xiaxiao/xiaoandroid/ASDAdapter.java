package com.xiaxiao.xiaoandroid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaxiao.xiaoandroid.adapter.XXBaseAdapter;
import com.xiaxiao.xiaoandroid.bean.Article;

import java.util.List;

/**
 * Created by xiaxiao on 2017/11/7.
 */

public class ASDAdapter extends XXBaseAdapter<Article,ASDAdapter.ASDHolder> {
    public ASDAdapter(Context c, List<Article> listItem) {
        super(c, listItem);
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (listItem.get(position).getUrl().equals("0")) {
            return 0;
        } else if (listItem.get(position).getUrl().equals("1")) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public ASDHolder createHolder(int position, View viewInHolder, ViewGroup
            viewGroup) {
        int i = getItemViewType(position);
        if (i==0) {
            return new ASDHolder(viewInHolder);
        } else if (i == 1) {
            return new ASDHolder2(viewInHolder);
        } else {
            return new ASDHolder3(viewInHolder);
        }
    }

    @Override
    public int getViewId(int position) {
        int i = getItemViewType(position);
        if (i==0) {
            return R.layout.asd_item1;
        } else if (i == 1) {
            return R.layout.asd_item2;
        } else {
            return R.layout.asd_item3;
        }
    }

    public class ASDHolder extends XXBaseAdapter.XXHolder<Article> {
        public ASDHolder(View view) {
            super(view);
        }

        @Override
        public void initViews(View view) {
        }

        @Override
        public void initDatas(int position, Article object) {
        }
    }
    public class ASDHolder1 extends ASDHolder {
        TextView tv;
        public ASDHolder1(View view) {
            super(view);
        }

        @Override
        public void initViews(View view) {
            tv = (TextView) view.findViewById(R.id.tv);
        }

        @Override
        public void initDatas(int position, Article object) {
            tv.setText(object.getName());
        }
    }
    public class ASDHolder2 extends ASDHolder {
        TextView tv;
        public ASDHolder2(View view) {
            super(view);
        }

        @Override
        public void initViews(View view) {
            tv = (TextView) view.findViewById(R.id.tv);
        }

        @Override
        public void initDatas(int position, Article object) {
            tv.setText(object.getName());
        }
    }
    public class ASDHolder3 extends ASDHolder {
        TextView tv;
        public ASDHolder3(View view) {
            super(view);
        }

        @Override
        public void initViews(View view) {
            tv = (TextView) view.findViewById(R.id.tv);
        }

        @Override
        public void initDatas(int position, Article object) {
            tv.setText(object.getName());
        }
    }
}
