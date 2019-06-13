package com.example.administrator.jx_new_jsoup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.administrator.jx_new_jsoup.R;
import com.example.administrator.jx_new_jsoup.entity.News;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    private List<News> oList;
    private LayoutInflater inflater;

    public NewsAdapter(Context context, List<News> oList) {
        this.context = context;
        this.oList = oList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate
                (R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_news_title
                .setText(oList.get(position).getTitle());
        holder.tv_news_time
                .setText(oList.get(position).getTime());
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linstener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return oList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_news_title, tv_news_time;
        private LinearLayout ll_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_item = itemView.findViewById(R.id.ll_item);
            tv_news_time = itemView.findViewById(R.id.tv_news_time);
            tv_news_title = itemView.findViewById(R.id.tv_news_title);
        }
    }

    private OnItemClickLinstener linstener;

    public void setLinstener(OnItemClickLinstener linstener) {
        this.linstener = linstener;
    }

    public interface OnItemClickLinstener {
        void onItemClick(View view, int position);
    }
}
