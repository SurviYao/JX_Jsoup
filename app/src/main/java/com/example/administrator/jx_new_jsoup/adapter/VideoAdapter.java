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
import com.example.administrator.jx_new_jsoup.entity.Video;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private Context context;
    private List<Video> oList;
    private LayoutInflater inflater;
    private OnVideoItemClickLinstener linstener;

    public void setLinstener(OnVideoItemClickLinstener linstener) {
        this.linstener = linstener;
    }

    public VideoAdapter(Context context, List<Video> oList) {
        this.context = context;
        this.oList = oList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_video_time.setText(oList.get(position).getVideotime());
        holder.tv_video_name.setText(oList.get(position).getVideoname());
        ImageLoader.getInstance().displayImage(oList.get(position).getVideoimageurl(), holder.iv_video, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                ((ImageView) view).setImageBitmap(null);
                view.setBackground(new BitmapDrawable(loadedImage));
            }
        });
        holder.ll_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linstener.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return oList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_video_name, tv_video_time;
        private ImageView iv_video;
        private LinearLayout ll_video;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_video = itemView.findViewById(R.id.ll_video);
            tv_video_name = itemView.findViewById(R.id.tv_video_name);
            tv_video_time = itemView.findViewById(R.id.tv_video_time);
            iv_video = itemView.findViewById(R.id.iv_video);
        }
    }

    public interface OnVideoItemClickLinstener {
        void onItemClick(View view, int position);
    }
}
