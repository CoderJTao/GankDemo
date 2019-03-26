package com.jtao.gankdemo.Activity.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jtao.gankdemo.Activity.Fragment.NewFragment;
import com.jtao.gankdemo.Activity.MainActivity;
import com.jtao.gankdemo.Activity.Model.NewsMoshi;
import com.jtao.gankdemo.Activity.Model.NewsSubMoshi;
import com.jtao.gankdemo.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private NewsMoshi mNews;

    private List<NewsSubMoshi> newsList;

    private List<String> tagLists;

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEMS  = 2;

    // 定义自己的属性
    private MainActivity.OnNewsItemClickListener listener;
    // 写一个公共方法，传入listener
    public void setOnItemClickListener(MainActivity.OnNewsItemClickListener listener) {
        this.listener = listener;
    }

    public NewsAdapter(Context context) {
        this.mContext = context;

        newsList = new ArrayList<>();
    }

    public void setData(NewsMoshi news) {
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

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEMS;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;

        if (i == TYPE_HEADER) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item_header, viewGroup, false);
            return new NewsHeaderHolder(view);
        } else  {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_news_item, viewGroup, false);
            return new NewsRecycleHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (mNews == null || newsList.size() == 0 || newsList.size() <= i) return;

        if (getItemViewType(i) == TYPE_HEADER) {
            // header
            String imgUrl = mNews.todayGirl;

            NewsHeaderHolder holder = (NewsHeaderHolder)viewHolder;

            if (!imgUrl.equals("")) {
                Glide.with(mContext).load(imgUrl).into(holder.new_header);
            }
        } else {
            // items
            final NewsSubMoshi item = newsList.get(i - 1);

            NewsRecycleHolder holder = (NewsRecycleHolder)viewHolder;

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickItem(item);
                }
            });

            if (item != null) {
                holder.new_desc.setText(item.desc);
                holder.new_who.setText(item.who);
                holder.new_time.setText(item.publishedAt.substring(0, 10));

                if (item.images != null) {
                    holder.new_showImage.setVisibility(View.VISIBLE);
                    String url = item.images.get(0);
                    Glide.with(mContext).load(url).into(holder.new_showImage);
                } else {
                    holder.new_showImage.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size() + 1;
    }

    public static class NewsHeaderHolder extends RecyclerView.ViewHolder {
        public final ImageView new_header;

        public NewsHeaderHolder(@NonNull View itemView) {
            super(itemView);

            new_header = itemView.findViewById(R.id.news_header_img);
        }
    }

    /**
     * 每一个item的holder
     */
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
