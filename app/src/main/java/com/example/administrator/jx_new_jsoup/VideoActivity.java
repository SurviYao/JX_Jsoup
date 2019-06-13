package com.example.administrator.jx_new_jsoup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.jx_new_jsoup.adapter.VideoAdapter;
import com.example.administrator.jx_new_jsoup.entity.Video;
import com.example.administrator.jx_new_jsoup.utils.JsoupUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VideoActivity extends BaseActivity {
    private TextView tv_title;
    private RecyclerView rv_video;
    private String url = "http://jxtv.cncourt.org/video/sort/tid/20/court/0";
    private List<Video> oList = new ArrayList<>();
    private VideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initView();

        dialog.show();
        JsoupUtil.getInstance().getVideoList(handler, url);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 7:
                    dialog.dismiss();
                    oList = (List<Video>) msg.obj;
                    if (oList != null && oList.size() > 0) {
                        adapter = new VideoAdapter(context, oList);
                        rv_video.setAdapter(adapter);
                        adapter.setLinstener(new VideoAdapter.OnVideoItemClickLinstener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //跳转至视频详情页
                                Intent intent = new Intent(context, VideoDetailActivity.class);
                                intent.putExtra("video", oList.get(position));
                                startActivity(intent);
                            }
                        });
                    }
                    break;
            }
        }
    };

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        rv_video = findViewById(R.id.rv_video);
        tv_title.setText("新闻视播");
        rv_video.setLayoutManager(new LinearLayoutManager
                (context, LinearLayoutManager.VERTICAL,
                        false));
    }
}
