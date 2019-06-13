package com.example.administrator.jx_new_jsoup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.jx_new_jsoup.adapter.PrictrueAdapter;
import com.example.administrator.jx_new_jsoup.entity.Prictrue;
import com.example.administrator.jx_new_jsoup.utils.JsoupUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.recyclerview.widget.RecyclerView;

public class PrictrueActivity extends BaseActivity {
    private TextView tv_title;
    private PullLoadMoreRecyclerView rv_image;
    private String weburl = "http://www.jiangxi.gov.cn/col/col478/index.html";
    private List<Prictrue> oList = new ArrayList<>();
    private PrictrueAdapter adapter;
    private int pageindex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prictrue);
        initView();
        dialog.show();
        JsoupUtil.getInstance().getPrictrueList(4, handler, weburl, pageindex);
    }

    private int size;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dialog.dismiss();
            final List<Prictrue> oList = (List<Prictrue>) msg.obj;
            if (oList != null && oList.size() > 0) {
                switch (msg.what) {
                    case 6:
                        size = PrictrueActivity.this.oList.size();
                        for (int i = 0; i < oList.size(); i++) {
                            PrictrueActivity.this.oList.add(oList.get(i));
                        }
                        break;
                    case 4:
                        PrictrueActivity.this.oList.clear();
                        PrictrueActivity.this.oList.addAll(oList);
                        break;
                }
                adapter = new PrictrueAdapter(context, PrictrueActivity.this.oList);
                rv_image.setAdapter(adapter);
                rv_image.setPullLoadMoreCompleted();
                if (msg.what == 6) {
                    RecyclerView.LayoutManager manager = rv_image.getLayoutManager();
                    manager.scrollToPosition(size);
                }
                adapter.setLinstener(new PrictrueAdapter.OnPrictrueItemCilckLinstener() {
                    @Override
                    public void onItemCilckLinstener(View view, int position) {
                        //跳转至图片详情界面
                        Intent intent = new Intent(context, PrictrueDetailActivity.class);
                        intent.putExtra("prictrue",PrictrueActivity.this.oList.get(position));
                        startActivity(intent);
                    }
                });
            } else {
                if (msg.what == 6) {
                    show_short("已经是最后一页");
                }
            }
        }
    };

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        rv_image = findViewById(R.id.rv_image);
        tv_title.setText("图画江西");
        rv_image.setLinearLayout();

        //设置上拉加载的文字
        rv_image.setFooterViewText("Loading……");
        //设置上拉加载的文字颜色
        rv_image.setFooterViewTextColor(R.color.white);
        //设置上拉加载的背景颜色
        rv_image.setFooterViewBackgroundColor(R.color.MIAN);
        //设置下拉刷新的圆形进度条的变换颜色
        rv_image.setColorSchemeResources(R.color.red, R.color.green, R.color.blue);
        //添加监听事件
        rv_image.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                //下拉加载，刷新
                show_short("下拉刷新");
                dialog.show();
                pageindex = 0;
                JsoupUtil.getInstance().getPrictrueList(4, handler, weburl, pageindex);

            }

            @Override
            public void onLoadMore() {
                //上拉，加载下一页数据
                show_short("上拉加载");
                pageindex++;
                dialog.show();
                JsoupUtil.getInstance().getPrictrueList(6, handler, weburl, pageindex);
                rv_image.setPullLoadMoreCompleted();
            }
        });

        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (System.currentTimeMillis()-time<2000){
                   rv_image.scrollToTop();
               }else {
                   time=System.currentTimeMillis();
               }

            }
        });

    }
    private long time;
}
