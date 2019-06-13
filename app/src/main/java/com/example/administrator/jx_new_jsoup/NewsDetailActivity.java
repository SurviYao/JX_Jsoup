package com.example.administrator.jx_new_jsoup;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.jx_new_jsoup.entity.News;
import com.example.administrator.jx_new_jsoup.entity.NewsDetail;
import com.example.administrator.jx_new_jsoup.utils.JsoupUtil;
import com.example.administrator.jx_new_jsoup.utils.SqliteUtil;


public class NewsDetailActivity extends BaseActivity {
    private TextView tv_news_title, tv_time, tv_author, tv_content;
    private ImageButton btn_right;
    private News news;
    private String url, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        news = (News) getIntent().getSerializableExtra("news");
        initView();
        url = news.getUrl();
        title = news.getTitle();
        if (!TextUtils.isEmpty(url)) {
            dialog.show();
            JsoupUtil.getInstance()
                    .getNewsDetail(handler, url);
        }


    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 3:
                    dialog.dismiss();
                    NewsDetail detail = (NewsDetail) msg.obj;
                    if (detail != null) {
                        tv_news_title.setText(title);
                        tv_time.setText(detail.getTime());
                        tv_author.setText(detail.getAuthor().split("\\：")[1]);
                        if (detail.getContent() != null
                                && detail.getContent().size() > 0) {
                            for (int i = 0; i < detail.getContent().size(); i++) {
                                tv_content.append("\t\t" + detail.getContent().get(i) + "\n");
                            }
                        }
                    } else {
                        show_short("解析失败");
                    }
                    break;
            }
        }
    };

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_right:

                    if (isSc) {
                        //默认为收藏
                        //取消收藏，从数据库中删除对应的新闻
                        //将图标改为未收藏状态
                        long i = SqliteUtil.getInstance()
                                .delete("tb_news",
                                        "title",
                                        news.getTitle(),
                                        1);
                        if (i > 0) {
                            btn_right.setImageResource
                                    (R.mipmap.shoucang_new_one);
                            isSc=false;
                        }
                    } else {
                        //默认为未收藏
                        //收藏，将这篇新闻添加至数据库
                        //将图标改为收藏状态
                        long i = SqliteUtil.getInstance().insert(
                                "tb_news", news, 1);
                        if (i > 0) {
                            btn_right.setImageResource(
                                    R.mipmap.shoucang_new_two);
                            isSc=true;
                        }
                    }
                    break;
            }
        }
    };
    private boolean isSc;

    private void initView() {
        btn_right = findViewById(R.id.btn_right);
        btn_right.setVisibility(View.VISIBLE);
        btn_right.setOnClickListener(listener);
        btn_right.setBackgroundColor(Color.TRANSPARENT);
        tv_author = findViewById(R.id.tv_author);
        tv_content = findViewById(R.id.tv_content);
        tv_news_title = findViewById(R.id.tv_news_title);
        tv_time = findViewById(R.id.tv_time);

        //从数据库中查询当前这篇新闻有没有被收藏
        //如果有被收藏，收藏图片变为选中图片
        isSc = SqliteUtil.getInstance().isSC(
                "tb_news",
                "title", news.getTitle());
        if (isSc) {
            btn_right.setImageResource(R.mipmap.shoucang_new_two);
        } else {
            btn_right.setImageResource(R.mipmap.shoucang_new_one);
        }
    }
}
