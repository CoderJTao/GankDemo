package com.jtao.gankdemo.Activity.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jtao.gankdemo.Activity.Model.NewsSubMoshi;
import com.jtao.gankdemo.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryRecycleHolder> {

    private Context mContext;

    private List<NewsSubMoshi> newsList;

    // 定义自己的属性
    private NewsAdapter.OnNewsItemClickListener listener;
    // 写一个公共方法，传入listener
    public void setOnItemClickListener(NewsAdapter.OnNewsItemClickListener listener) {
        this.listener = listener;
    }

    public CategoryAdapter(Context context) {
        this.mContext = context;

        newsList = new ArrayList<>();
    }

    public void setData(List<NewsSubMoshi> lists) {
        this.newsList = lists;

        ((Activity)mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public CategoryRecycleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_news_item, viewGroup, false);

        return new CategoryAdapter.CategoryRecycleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecycleHolder categoryRecycleHolder, int i) {
        if (newsList.size() == 0 || newsList.size() <= i) return;

        // items
        final NewsSubMoshi item = newsList.get(i);

        categoryRecycleHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickItem(item);
            }
        });

        if (item != null) {
            categoryRecycleHolder.new_desc.setText(item.desc);
            categoryRecycleHolder.new_who.setText(item.who);
            categoryRecycleHolder.new_time.setText(item.publishedAt.substring(0, 10));

            if (item.images != null) {
                categoryRecycleHolder.new_showImage.setVisibility(View.VISIBLE);
                String url = item.images.get(0);
                Glide.with(mContext).load(url).into(categoryRecycleHolder.new_showImage);
            } else {
                categoryRecycleHolder.new_showImage.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    /**
     * 每一个item的holder
     */
    public static class CategoryRecycleHolder extends RecyclerView.ViewHolder {
        public final TextView new_desc;
        public final TextView new_who;
        public final TextView new_time;
        public final ImageView new_showImage;

        public CategoryRecycleHolder(View itemView) {
            super(itemView);

            new_desc = itemView.findViewById(R.id.new_item_desc);
            new_who = itemView.findViewById(R.id.new_item_who);
            new_time = itemView.findViewById(R.id.new_item_time);
            new_showImage = itemView.findViewById(R.id.new_item_showimg);
        }
    }
}
