package com.example.administrator.jx_new_jsoup.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jx_new_jsoup.R;
import com.example.administrator.jx_new_jsoup.entity.News;
import com.example.administrator.jx_new_jsoup.entity.Prictrue;
import com.example.administrator.jx_new_jsoup.entity.Sc;
import com.example.administrator.jx_new_jsoup.entity.Video;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SCAdapter extends RecyclerView.Adapter<SCAdapter.ViewHolder> {
    private Context context;
    private List<Sc> olist;
    private LayoutInflater inflater;
    private OnScItemClickLinstener linstener;

    public void setLinstener(OnScItemClickLinstener linstener) {
        this.linstener = linstener;
    }

    public SCAdapter(Context context, List<Sc> olist) {
        this.context = context;
        this.olist = olist;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.sc_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.ll_news.setVisibility(View.GONE);
        holder.ll_prictrue.setVisibility(View.GONE);
        holder.ll_video.setVisibility(View.GONE);
        switch (olist.get(position).getType()) {
            case 1:
                holder.ll_news.setVisibility(View.VISIBLE);
                News news = (News) olist.get(position).getObject();
                holder.tv_news_title.setText(news.getTitle());
                holder.tv_news_time.setText(news.getTime());
                break;
            case 2:
                holder.ll_prictrue.setVisibility(View.VISIBLE);
                Prictrue prictrue = (Prictrue) olist.get(position).getObject();
                holder.tv_prictrue_name.setText(prictrue.getName());
                break;
            case 3:
                holder.ll_video.setVisibility(View.VISIBLE);
                final Video video = (Video) olist.get(position).getObject();
                holder.tv_video_name.setText(video.getVideoname());
                holder.tv_video_time.setText(video.getVideotime());
                ImageLoader.getInstance().displayImage(video.getVideoimageurl(), holder.iv_video, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        ((ImageView) view).setImageBitmap(null);
                        view.setBackground(new BitmapDrawable(loadedImage));
                    }
                });
                break;
        }
        holder.ll_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linstener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return olist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_news, ll_prictrue, ll_video, ll_sc;
        private TextView tv_news_title, tv_news_time;
        private TextView tv_prictrue_name;
        private ImageView iv_video;
        private TextView tv_video_name, tv_video_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_sc = itemView.findViewById(R.id.ll_sc);
            ll_news = itemView.findViewById(R.id.ll_news);
            ll_prictrue = itemView.findViewById(R.id.ll_prictrue);
            ll_video = itemView.findViewById(R.id.ll_video);
            tv_news_title = itemView.findViewById(R.id.tv_news_title);
            tv_news_time = itemView.findViewById(R.id.tv_news_time);
            tv_prictrue_name = itemView.findViewById(R.id.tv_prictrue_name);
            iv_video = itemView.findViewById(R.id.iv_video);
            tv_video_name = itemView.findViewById(R.id.tv_video_name);
            tv_video_time = itemView.findViewById(R.id.tv_video_time);


        }
    }

    public interface OnScItemClickLinstener {
        void onItemClick(View view, int position);
    }
}
