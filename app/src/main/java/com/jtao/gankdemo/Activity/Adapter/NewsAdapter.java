package com.jtao.gankdemo.Activity.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jtao.gankdemo.Activity.Fragment.NewFragment;
import com.jtao.gankdemo.Activity.Model.NewsMoshi;
import com.jtao.gankdemo.Activity.Model.NewsSubMoshi;
import com.jtao.gankdemo.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsRecycleHolder> {

    private Context mContext;

    private NewsMoshi mNews;

    private List<NewsSubMoshi> newsList;

    private List<String> tagLists;

    public NewsAdapter(Context context) {
        this.mContext = context;

        newsList = new ArrayList<>();
    }

    public void addData(NewsMoshi news) {
        if (this.mNews != null) {
            this.mNews = null;
        }
        this.mNews = news;

        this.tagLists = news.tagsLists;

        this.newsList = news.newsList;

        // 刷新界面的操作必须要在主线程中运行
        ((Activity)mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public NewsRecycleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_news_item, viewGroup, false);

        return new NewsRecycleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecycleHolder viewHolder, int i) {
        if (mNews == null || newsList.size() == 0 || newsList.size() <= i) return;

        // 设置值
        NewsSubMoshi item = newsList.get(i);

        if (item != null) {
            viewHolder.new_desc.setText(item.desc);
            viewHolder.new_who.setText(item.who);
            viewHolder.new_time.setText(item.publishedAt.substring(0, 10));

            if (item.images != null) {
                viewHolder.new_showImage.setVisibility(View.VISIBLE);
                String url = item.images.get(0);
                Glide.with(mContext).load(url).into(viewHolder.new_showImage);
            } else {
                viewHolder.new_showImage.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsRecycleHolder extends RecyclerView.ViewHolder {
        public final TextView new_desc;
        public final TextView new_who;
        public final TextView new_time;
        public final ImageView new_showImage;

        public NewsRecycleHolder(View itemView) {
            super(itemView);

            new_desc = itemView.findViewById(R.id.new_item_desc);
            new_who = itemView.findViewById(R.id.new_item_who);
            new_time = itemView.findViewById(R.id.new_item_time);
            new_showImage = itemView.findViewById(R.id.new_item_showimg);
        }
    }
}
