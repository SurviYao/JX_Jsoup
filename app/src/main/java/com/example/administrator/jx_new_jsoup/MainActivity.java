package com.example.administrator.jx_new_jsoup;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends ActivityGroup {

    private Intent[] intents = new Intent[4];
    private String[] tags = {"A", "B", "C", "D"};
    private TabHost tabHost;
    private int[] draws={R.drawable.tab_news,R.drawable.tab_pictures,R.drawable.tab_videos,R.drawable.tab_sc};
    private ImageView iv;
    private TextView tv;
    private String[]text={"新闻","图片","视频","收藏"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabHost = findViewById(R.id.tabhost);
        //允许添加多个Activity
        tabHost.setup(getLocalActivityManager());

        intents();
        addHostView();
    }

    public void intents() {
        intents[0] = new Intent(this, NewsActivity.class);
        intents[1] = new Intent(this, PrictrueActivity.class);
        intents[2] = new Intent(this, VideoActivity.class);
        intents[3] = new Intent(this, SCActivity.class);
    }
    public void addHostView(){
        for (int i = 0; i < intents.length; i++) {
            //根据for循环创建选项卡并添加标签
            TabHost.TabSpec spec = tabHost.newTabSpec(tags[i]);
            //加载4个选项卡布局
            View view= (View) getLayoutInflater().inflate(R.layout.tab_item,null);
            iv=view.findViewById(R.id.iv_tab);
            tv=view.findViewById(R.id.tv_tab);
            iv.setImageResource(draws[i]);
            tv.setText(text[i]);
            tv.setTextColor(getResources().getColor(R.color.tab_text));
            spec.setIndicator(view).setContent(intents[i]);
            tabHost.addTab(spec);
        }
    }


}