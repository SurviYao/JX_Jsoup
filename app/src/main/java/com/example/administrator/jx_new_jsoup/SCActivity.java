package com.example.administrator.jx_new_jsoup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.jx_new_jsoup.adapter.SCAdapter;
import com.example.administrator.jx_new_jsoup.entity.News;
import com.example.administrator.jx_new_jsoup.entity.Prictrue;
import com.example.administrator.jx_new_jsoup.entity.Sc;
import com.example.administrator.jx_new_jsoup.entity.Video;
import com.example.administrator.jx_new_jsoup.utils.SqliteUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SCActivity extends BaseActivity {
    private TextView tv_title;
    private RecyclerView rv_sc;
    private List<Sc> oList = new ArrayList<>();
    private SCAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sc);
        initView();

    }
    //oncreate--onstart--onresume--onpuase--onstop--onrestart--onstart--onresume
    //onreate--onstaet--onresume--onpause--onstop--ondestory


    @Override
    protected void onResume() {
        super.onResume();
        getdata();
    }

    public void getdata(){
        oList = SqliteUtil.getInstance().getScList();
        if (oList != null && oList.size() > 0) {
            adapter = new SCAdapter(context, oList);
            rv_sc.setAdapter(adapter);
            adapter.setLinstener(new SCAdapter.OnScItemClickLinstener() {
                @Override
                public void onItemClick(View view, int position) {
                    switch (oList.get(position).getType()) {
                        case 1:
                            News news = (News) oList.get(position).getObject();
                            //跳转至新闻详情页面
                            Intent intent = new Intent(context, NewsDetailActivity.class);
                            intent.putExtra("news", news);
                            startActivity(intent);
                            break;
                        case 2:
                            Prictrue prictrue = (Prictrue) oList.get(position).getObject();
                            //跳转至新闻详情页面
                            Intent intent1 = new Intent(context, PrictrueDetailActivity.class);
                            intent1.putExtra("prictrue", prictrue);
                            startActivity(intent1);
                            break;
                        case 3:
                            Video video = (Video) oList.get(position).getObject();
                            //跳转至新闻详情页面
                            Intent intent2 = new Intent(context, VideoDetailActivity.class);
                            intent2.putExtra("video", video);
                            startActivity(intent2);
                            break;
                    }
                }
            });
        }
    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        rv_sc = findViewById(R.id.rv_sc);
        tv_title.setText("我的收藏");
        rv_sc.setLayoutManager(new LinearLayoutManager
                (context,
                        LinearLayoutManager.VERTICAL,
                        false));
    }
}
