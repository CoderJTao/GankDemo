package com.jtao.gankdemo.Activity.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jtao.gankdemo.Activity.Model.NewsMoshi;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> tagLists;
    private NewsMoshi mNews;


    public NewsAdapter(Context context, NewsMoshi news, List<String> tags) {
        this.mContext = context;
        this.mNews = news;
        this.tagLists = tags;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
