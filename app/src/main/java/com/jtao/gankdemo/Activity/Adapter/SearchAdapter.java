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

import com.jtao.gankdemo.Activity.Model.SearchItemMoshi;
import com.jtao.gankdemo.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchRecycleHolder> {

    private Context mContext;

    private List<SearchItemMoshi> searchItems = new ArrayList<>();


    private SearchItemClickListener mListener;
    // 定义一个接口，监听item点击事件
    public interface SearchItemClickListener {
        void onSearchItemClick(SearchItemMoshi searchItem);
    }

    public SearchAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<SearchItemMoshi> lists) {
        this.searchItems = lists;

        // 在主线程更新界面
        ((Activity)mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public void setItemClickListener(SearchItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public SearchRecycleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_news_item, viewGroup, false);
        return new SearchRecycleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecycleHolder searchRecycleHolder, int i) {
        if (searchItems.size() == 0 || searchItems.size() <= i) {
            return;
        }

        final SearchItemMoshi searchItem = searchItems.get(i);

        searchRecycleHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSearchItemClick(searchItem);
            }
        });

        if (searchItem != null) {
            searchRecycleHolder.search_desc.setText(searchItem.desc);
            searchRecycleHolder.search_user.setText(searchItem.who);
            searchRecycleHolder.search_time.setText(searchItem.publishedAt.substring(0, 10));
        }
    }

    @Override
    public int getItemCount() {
        return searchItems.size();
    }


    public static class SearchRecycleHolder extends RecyclerView.ViewHolder {

        public final TextView search_desc;
        public final TextView search_user;
        public final TextView search_time;

        public SearchRecycleHolder(View itemView) {
            super(itemView);

            search_desc = (TextView) itemView.findViewById(R.id.new_item_desc);
            search_user = (TextView) itemView.findViewById(R.id.new_item_who);
            search_time = (TextView) itemView.findViewById(R.id.new_item_time);

            ImageView img = (ImageView) itemView.findViewById(R.id.new_item_showimg);
            img.setVisibility(View.GONE);
        }
    }

}
