package com.xiaxiao.xiaoandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaxiao.xiaoandroid.customview.pinnedsectionlistview.PinnedSectionListView;

import java.util.ArrayList;
import java.util.List;


public class PinnedSectionListviewTestActivity extends AppCompatActivity {

    PinnedSectionListView pinnedSectionListView;
    List<Object> datas;
    TestAdapter testAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pinned);
        pinnedSectionListView = (PinnedSectionListView) findViewById(R.id.list);
        datas = new ArrayList<>();
        for (int i=0;i<60;i++) {
            datas.add("dssda");
        }
        datas.add(4,2);
        datas.add(24,2);
        datas.add(34,2);
        testAdapter = new TestAdapter(this, datas);
        Log.i("xx" , "xsssssssssssssssssssssssss");
        pinnedSectionListView.setAdapter(testAdapter);
    }



    class TestAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter{

        List<Object> lists;

        public TestAdapter(Context context, List list) {
            this.lists = list;
        }
        @Override
        public boolean isItemViewTypePinned(int viewType) {
            if (viewType==1) {
                return true;
            }
            return false;
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            if (lists.get(position) instanceof String) {
                return 0;
            }

            return 1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder h=null;
            Log.i("xx" , "xsssssssssssssssssssssssss  getView");
           /* if (convertView == null) {
                convertView = View.inflate(TestActivity.this, R.layout.item, null);
                h = new Holder(convertView);
                convertView.setTag(h);
            } else {
                h = (Holder) convertView.getTag();
            }
*/
            if (getItemViewType(position) == 0) {
                if (convertView == null) {
                    convertView = View.inflate(PinnedSectionListviewTestActivity.this, R.layout.item, null);
                    h = new Holder(convertView);
                    convertView.setTag(h);
                } else {
                    h = (Holder) convertView.getTag();
                }
                h.tv.setText("我是布局1：  " + position);
            } else {
                if (convertView == null) {
                    convertView = View.inflate(PinnedSectionListviewTestActivity.this, R.layout.item_top, null);
                    h = new Holder(convertView);
                    convertView.setTag(h);
                } else {
                    h = (Holder) convertView.getTag();
                }
                h.tv.setText("我是布局2,我应该吸顶的：  " + position);

            }

            return convertView;
        }
    }

    class Holder {
        public TextView tv;

        public Holder(View view) {
            tv = (TextView) view.findViewById(R.id.tv);
        }

    }


}
