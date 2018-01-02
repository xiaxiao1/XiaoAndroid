package com.xiaxiao.xiaoandroid.customview.xxflowlayout;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    XXFlowLayout xxFlowLayout;
    ScrollView scrollView;
    int size=20;
    ListView listView;
    String msg = "春花秋月何时了，往事知多少。小楼昨夜又东风，故国不堪回首明月中。雕栏玉砌应犹在，只是朱颜改，问君能有几多愁，恰似一江春水向东流。";
    View btn;
    View item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.et);
        xxFlowLayout = (XXFlowLayout) findViewById(R.id.fl);
        xxFlowLayout.setOnChildClickListener(new XXFlowLayout.OnChildClickListener() {
            @Override
            public void onClick(View view, int index, int line) {
                Log.i("xx","index="+index+"   line="+line+"  say="+((TextView)view).getText().toString());
            }
        });
//        xxFlowLayout.setHorizontalUniformed(true);


        scrollView = (ScrollView) findViewById(R.id.sc);
    }

    int index=0;
    public void addView(View view) {
       /*size=13;
        add();*/
        View item2 = View.inflate(this, R.layout.item, null);
        btn = item2.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"你点我干哈啊",Toast.LENGTH_SHORT).show();
            }
        });
        xxFlowLayout.addView(item2);
    }
    public void goFlowListActivity(View view) {
        startActivity(new Intent(this,FlowListViewActivity.class));
    }
    public void addBigView(View view) {
        size=33;
        add();
    }
    public void addMiddleView(View view) {
        size=23;
        add();
    }
    int line=20;
    public void changeLine(View view) {
        xxFlowLayout.setLineSpace(line);
        line = line + 10;
    }
    public void top(View view) {
        xxFlowLayout.setVerticalAlignType(XXFlowLayout.VERTICAL_ALIGN_TOP);
    }
    public void center(View view) {
        xxFlowLayout.setVerticalAlignType(XXFlowLayout.VERTICAL_ALIGN_CENTER);
    }
    public void bottom(View view) {
        xxFlowLayout.setVerticalAlignType(XXFlowLayout.VERTICAL_ALIGN_BASE_BOTTOM);
    }
    boolean uniform=false;
    public void uniform(View view) {
        xxFlowLayout.setHorizontalUniformed(uniform);
        uniform = !uniform;
    }
    int ws=10;
    public void wordSpace(View view) {
        xxFlowLayout.setHorizontalSpace(ws);
        ws+=10;
    }

    public void add() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin=4;
        params.rightMargin=2;
        params.topMargin =6;
        params.bottomMargin =6;
        String s = editText.getText().toString().trim();
        final TextView t = new TextView(this);
        t.setLayoutParams(params);
        int a = (int)(Math.random() * 35);
        int l = (int)(Math.random() * 5+2);
        t.setText(msg.substring(a,a+l)+index);
//        t.setText(" hello "+index);
        index++;
        t.setTextSize(size);
        t.setTextColor(Color.WHITE);
        t.setBackgroundResource(R.drawable.bg);
        /*t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xxFlowLayout.removeView(t);
                Toast.makeText(MainActivity.this,"删除了 "+t.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });*/
        xxFlowLayout.addView(t);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }
}
