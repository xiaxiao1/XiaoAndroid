package com.xiaxiao.xiaoandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.xiaxiao.xiaoandroid.bean.Article;

import java.util.ArrayList;
import java.util.List;

public class AdapterActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);
        listView = (ListView) findViewById(R.id.listview);

        List<Article> list = new ArrayList<>();
        for (int i=0;i<100;i++) {
            Article a = new Article();
            a.setName("haha "+i);
            a.setUrl("0");
            if (i%10==0) {
                a.setUrl("0");
            }
            if (i%3==0) {
                a.setUrl("1");
            }
            if (i%4==0) {
                a.setUrl("2");
            }

            list.add(a);
        }
        ASDAdapter asdAdapter = new ASDAdapter(this, list);
        listView.setAdapter(asdAdapter);
    }
}
