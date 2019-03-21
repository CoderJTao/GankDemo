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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jtao.gankdemo.Activity.Model.NewsMoshi;
import com.jtao.gankdemo.Activity.Model.NewsSubMoshi;
import com.jtao.gankdemo.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryRecyclerHolder> {

    private Context mContext;

    private NewsMoshi mNews;

    private List<NewsMoshi> historyItems = new ArrayList<>();

    // 定义一个接口
    public interface OnHistoryItemClickListener {
        void onClickItem(NewsMoshi item);
    }
    // 定义自己的属性
    private HistoryAdapter.OnHistoryItemClickListener listener;
    // 写一个公共方法，传入listener
    public void setOnHistoryClickListener(HistoryAdapter.OnHistoryItemClickListener listener) {
        this.listener = listener;
    }


    public HistoryAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<NewsMoshi> items) {
        this.historyItems = items;

        ((Activity)mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public HistoryRecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history, viewGroup, false);
        return new HistoryRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRecyclerHolder historyRecyclerHolder, int i) {
        if (historyItems.size() == 0 || historyItems.size() <= i || i < 0) return;

        final NewsMoshi item = historyItems.get(i);

        historyRecyclerHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickItem(item);
            }
        });

        if (item != null) {
            // 文字
            String time = item.newsList.get(0).publishedAt.substring(0, 10);
            historyRecyclerHolder.time.setText(time);

            // 图片
            Glide.with(mContext).load(item.todayGirl).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(historyRecyclerHolder.showImg);
        }
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    public class HistoryRecyclerHolder extends RecyclerView.ViewHolder {

        public final TextView time;
        public final ImageView showImg;

        public HistoryRecyclerHolder(@NonNull View itemView) {
            super(itemView);

            time = (TextView) itemView.findViewById(R.id.item_history_date);
            showImg = (ImageView) itemView.findViewById(R.id.item_history_showImg);
        }
    }

}
