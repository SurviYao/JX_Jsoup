package com.example.administrator.jx_new_jsoup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.administrator.jx_new_jsoup.R;
import com.example.administrator.jx_new_jsoup.entity.Prictrue;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PrictrueAdapter extends RecyclerView.Adapter<PrictrueAdapter.Viewholder> {
    private Context context;
    private List<Prictrue> olist;
    private LayoutInflater inflater;
    private OnPrictrueItemCilckLinstener linstener;

    public void setLinstener(OnPrictrueItemCilckLinstener linstener) {
        this.linstener = linstener;
    }

    public PrictrueAdapter(Context context, List<Prictrue> olist) {
        this.context = context;
        this.olist = olist;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.prictrue_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        holder.tv.setText(olist.get(position).getName());
        holder.ll_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linstener.onItemCilckLinstener(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return olist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private LinearLayout ll_image;
        private TextView tv;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ll_image = itemView.findViewById(R.id.ll_image);
            tv = itemView.findViewById(R.id.tv_prictrue_name);
        }
    }

    public interface OnPrictrueItemCilckLinstener {
        void onItemCilckLinstener(View view, int position);
    }
}
