package com.example.administrator.jx_new_jsoup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jx_new_jsoup.adapter.MyPagerAdapter;
import com.example.administrator.jx_new_jsoup.adapter.NewsAdapter;
import com.example.administrator.jx_new_jsoup.entity.News;
import com.example.administrator.jx_new_jsoup.entity.ScrollImage;
import com.example.administrator.jx_new_jsoup.utils.JsoupUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class NewsActivity extends BaseActivity {
    private TextView tv_title, tv, tv_1, tv_2, tv_3;
    private ViewPager vp;
    private RecyclerView rv;
    private String newsurl = "http://www.jiangxi.gov.cn/col/col382/index.html";
    private String weburl = "http://www.jiangxi.gov.cn";
    private List<ScrollImage> oList = new ArrayList<>();
    private List<ImageView> oImages = new ArrayList<>();
    private MyPagerAdapter adapter;
    private List<News> oNews = new ArrayList<>();
    private NewsAdapter newsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();
        dialog.show();
        JsoupUtil.getInstance().getScrollImsages(handler, weburl);
    }

    public void initImage() {
        for (int i = 0; i < oList.size(); i++) {
            ImageView imageView = new ImageView(context);
            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
            imageView.setLayoutParams(params);
            ImageLoader.getInstance().displayImage(oList.get(i).getImageurl(), imageView, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    ((ImageView) view).setImageBitmap(null);
                    view.setBackground(new BitmapDrawable(loadedImage));

                }
            });
            oImages.add(imageView);

        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    dialog.dismiss();
                    oNews = (List<News>) msg.obj;
                    if (oNews != null && oNews.size() > 0) {
                        newsAdapter = new NewsAdapter(context, oNews);
                        rv.setAdapter(newsAdapter);
                        newsAdapter.setLinstener(new NewsAdapter.OnItemClickLinstener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //跳转至新闻详情页面
                                Intent intent = new Intent(context, NewsDetailActivity.class);
                                intent.putExtra("news",oNews.get(position));
                                startActivity(intent);
                            }
                        });
                    }
                    break;
                case 0x123:
                    vp.setCurrentItem(index % oList.size());
                    break;
                case 1:
                    dialog.dismiss();
                    oList = (List<ScrollImage>) msg.obj;
                    if (oList != null && oList.size() > 0) {
                        initImage();
                        adapter = new MyPagerAdapter(oImages);
                        vp.setAdapter(adapter);
                        tv.setText(oList.get(0).getTitle());
                        vp.setOnPageChangeListener(listener);
                        startThread();
                        vp.setOnTouchListener(touchListener);
                        dialog.show();
                        JsoupUtil.getInstance().getNewsList(handler, newsurl, "con-one-1");
                    }
                    break;
            }
        }
    };
    private int count;
    private boolean istouch = false;
    private int index = 0;

    public void startThread() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    while (!istouch) {
                        try {
                            sleep(500);
                            count++;
                            if (count % 4 == 0) {
                                ++index;
                                handler.sendEmptyMessage(0x123);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            index = position;
            tv.setText(oList.get(position).getTitle());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    istouch = true;
                    break;
                case MotionEvent.ACTION_UP:
                    istouch = false;
                    break;
            }
            return false;
        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.show();
            tv_1.setTextColor(getResources().getColor(R.color.white));
            tv_2.setTextColor(getResources().getColor(R.color.white));
            tv_3.setTextColor(getResources().getColor(R.color.white));
            switch (v.getId()) {
                case R.id.tv_1:
                    tv_1.setTextColor(getResources().getColor(R.color.red));
                    JsoupUtil.getInstance().getNewsList(handler, newsurl, "con-one-1");
                    break;
                case R.id.tv_2:
                    tv_2.setTextColor(getResources().getColor(R.color.red));
                    JsoupUtil.getInstance().getNewsList(handler, newsurl, "con-one-2");
                    break;
                case R.id.tv_3:
                    tv_3.setTextColor(getResources().getColor(R.color.red));
                    JsoupUtil.getInstance().getNewsList(handler, newsurl, "con-one-3");
                    break;
            }
        }
    };

    @SuppressLint("WrongConstant")
    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        vp = findViewById(R.id.vp);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager
                (context, LinearLayoutManager.VERTICAL,
                        false));
        tv = findViewById(R.id.tv);
        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        tv_3 = findViewById(R.id.tv_3);
        tv_1.setOnClickListener(clickListener);
        tv_2.setOnClickListener(clickListener);
        tv_3.setOnClickListener(clickListener);
        tv_1.setTextColor(getResources().getColor(R.color.red));

    }
}
