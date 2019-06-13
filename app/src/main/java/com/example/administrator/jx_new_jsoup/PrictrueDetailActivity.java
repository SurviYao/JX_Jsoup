package com.example.administrator.jx_new_jsoup;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jx_new_jsoup.entity.Prictrue;
import com.example.administrator.jx_new_jsoup.utils.JsoupUtil;
import com.example.administrator.jx_new_jsoup.utils.SqliteUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PrictrueDetailActivity extends BaseActivity {
    private TextView tv_title, tv_time, tv_content;
    private ImageView iv_image;
    private LinearLayout rl;
    private String url;
    private String title;
    private Prictrue prictrue;
    private float X, Y;
    private float x1, y1, x2, y2;
    private double startLine, endLine;
    private Prictrue pri;
    private ImageButton btn_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prictrue_detail);
        pri = (Prictrue) getIntent().getSerializableExtra("prictrue");
        initView();
        url = pri.getImageurl();
        title = pri.getName();
        tv_title.setText(title.split(" ")[0]);
        dialog.show();
        JsoupUtil.getInstance().getPrictrue(handler, url);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 5:
                    dialog.dismiss();
                    prictrue = (Prictrue) msg.obj;
                    if (prictrue != null) {
                        ImageLoader.getInstance().displayImage(prictrue.getImageurl(), iv_image);
                        tv_time.setText(prictrue.getTime());
                        if (TextUtils.isEmpty(prictrue.getcontent())) {
                            tv_content.setText("暂无描述信息");
                        } else
                            tv_content.setText(prictrue.getcontent());
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
                                .delete("tb_prictrue",
                                        "title",
                                        pri.getName(),
                                        2);
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
                                "tb_prictrue", pri, 2);
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
        tv_content = findViewById(R.id.tv_content);
        tv_time = findViewById(R.id.tv_time);
        tv_title = findViewById(R.id.tv_title);
        iv_image = findViewById(R.id.iv_image);
        rl = findViewById(R.id.rl);
        rl.setOnTouchListener(listener);

        //从数据库中查询当前这篇新闻有没有被收藏
        //如果有被收藏，收藏图片变为选中图片
        isSc = SqliteUtil.getInstance().isSC(
                "tb_prictrue",
                "title", pri.getName());
        if (isSc) {
            btn_right.setImageResource(R.mipmap.shoucang_new_two);
        } else {
            btn_right.setImageResource(R.mipmap.shoucang_new_one);
        }


    }

    private View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (event.getPointerCount() == 1) {
                        X = event.getX();
                        Y = event.getY();
                    }
//                    if (event.getPointerCount() == 2) {
//                        x1 = event.getX(0);
//                        y1 = event.getY(0);
//                        x2 = event.getX(1);
//                        y2 = event.getY(1);
//                        startLine = Math.sqrt((Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)));
//                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (event.getPointerCount() == 1) {
                        rl.scrollBy((int) (X - event.getX()), (int) (Y - event.getY()));
                        X = event.getX();
                        Y = event.getY();
                    }

                    if (event.getPointerCount() == 2) {
                        x1 = event.getX(0);
                        y1 = event.getY(0);
                        x2 = event.getX(1);
                        y2 = event.getY(1);
                        endLine = Math.sqrt((Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)));
                        if (startLine <= 0) {
                            startLine = endLine;
                        } else {
                            ViewGroup.LayoutParams params1
                                    = iv_image.getLayoutParams();
                            if (endLine - startLine < -5) {
                                //缩小
                                int width = (int) (iv_image.getWidth() * 0.95);
                                int height = (int) (iv_image.getHeight() * 0.95);
                                params1.width = width;
                                params1.height = height;
                                startLine = endLine;
                                iv_image.setLayoutParams(params1);
                            } else if (endLine - startLine > 5) {
                                //放大
                                int width = (int) (iv_image.getWidth() * 1.05);
                                int height = (int) (iv_image.getHeight() * 1.05);
                                params1.width = width;
                                params1.height = height;
                                startLine = endLine;
                                iv_image.setLayoutParams(params1);
                            }
                        }
                    }

                    break;
            }
            return true;
        }
    };
}
