package com.example.administrator.jx_new_jsoup;

import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.administrator.jx_new_jsoup.entity.Video;
import com.example.administrator.jx_new_jsoup.utils.JsoupUtil;
import com.example.administrator.jx_new_jsoup.utils.SqliteUtil;

public class VideoDetailActivity extends BaseActivity {
    private TextView tv_title;
    private VideoView vv;
    private String url;
    private Video video;
    private ImageButton btn_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        video = (Video) getIntent().getSerializableExtra("video");
        initView();
        url = video.getVideodetailurl();
        dialog.show();
        JsoupUtil.getInstance().getVideo(handler, url);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 8:
                    dialog.dismiss();
                    String videourl = (String) msg.obj;
                    if (!TextUtils.isEmpty(videourl)) {
                        vv.setVideoPath(videourl);
                        //将屏幕格式设置为全屏格式
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        //获得屏幕管理者
                        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
                        //获得显示对象
                        android.view.Display display = wm.getDefaultDisplay();
                        Point point = new Point();
                        //判断当前android版本
                        int API_LEVEL = android.os.Build.VERSION.SDK_INT;
                        //当版本大于17的时候获得真实的高宽
                        if (API_LEVEL >= 17) {
                            display.getRealSize(point);
                        } else {
                            display.getSize(point);
                        }
                        int height = point.y;
                        int width = point.x;
                        //将高宽重新赋值给videoview控件
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) vv
                                .getLayoutParams(); // 取控当前的布局参数
                        layoutParams.height = height;
                        layoutParams.width = width;
                        layoutParams.setMargins(0, 0, 0, 0);
                        vv.setLayoutParams(layoutParams);
                        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                vv.resetPivot();
                                vv.start();
                            }
                        });
                        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                vv.start();
                            }
                        });
                        vv.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                            @Override
                            public boolean onError(MediaPlayer mp, int what, int extra) {
                                show_short("视频资源出错");
                                return true;
                            }
                        });
                    }
                    break;
            }
        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_right:

                    if (isSc) {
                        //默认为收藏
                        //取消收藏，从数据库中删除对应的新闻
                        //将图标改为未收藏状态
                        long i = SqliteUtil.getInstance()
                                .delete("tb_video",
                                        "title",
                                        video.getVideoname(),
                                        3);
                        if (i > 0) {
                            btn_right.setImageResource
                                    (R.mipmap.shoucang_new_one);
                            isSc = false;
                        }
                    } else {
                        //默认为未收藏
                        //收藏，将这篇新闻添加至数据库
                        //将图标改为收藏状态
                        long i = SqliteUtil.getInstance().insert(
                                "tb_video", video, 3);
                        if (i > 0) {
                            btn_right.setImageResource(
                                    R.mipmap.shoucang_new_two);
                            isSc = true;
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
        btn_right.setOnClickListener(clickListener);
        btn_right.setBackgroundColor(Color.TRANSPARENT);
        tv_title = findViewById(R.id.tv_title);
        vv = findViewById(R.id.vv);
        tv_title.setText("新闻视播");
        vv.setMediaController(new MediaController(context));

        //从数据库中查询当前这篇新闻有没有被收藏
        //如果有被收藏，收藏图片变为选中图片
        isSc = SqliteUtil.getInstance().isSC(
                "tb_video",
                "title", video.getVideoname());
        if (isSc) {
            btn_right.setImageResource(R.mipmap.shoucang_new_two);
        } else {
            btn_right.setImageResource(R.mipmap.shoucang_new_one);
        }
    }
}
