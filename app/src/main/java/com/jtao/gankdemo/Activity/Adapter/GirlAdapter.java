package com.jtao.gankdemo.Activity.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jtao.gankdemo.Activity.Model.NewsSubMoshi;
import com.jtao.gankdemo.R;

import java.util.ArrayList;
import java.util.List;

public class GirlAdapter extends RecyclerView.Adapter<GirlAdapter.GirlViewHolder> {
    private static final int TYPE_VERTICAL = 1;
    private static final int TYPE_GRID = 2;

    private int mType = TYPE_VERTICAL;

    private Context mContext;

    private List<NewsSubMoshi> girls = new ArrayList<>();

    public GirlAdapter(Context context) {
        this.mContext = context;
    }

    public void setShowType(boolean isGrid) {
        if (isGrid) {
            mType = TYPE_GRID;
        } else {
            mType = TYPE_VERTICAL;
        }
    }

    public void setData(List<NewsSubMoshi> lists) {
        this.girls = lists;

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
        return mType;
    }

    @NonNull
    @Override
    public GirlViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
//        if (i == TYPE_VERTICAL) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_girls, viewGroup, false);
//        } else {
//            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_girl_grid, viewGroup,false);
//        }
        return new GirlViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GirlViewHolder girlViewHolder, int i) {
        if (girls == null || girls.size() < 0 || girls.size() <= i || i < 0) {
            return;
        }

        NewsSubMoshi girl = girls.get(i);

        if (girl != null) {
            Glide.with(mContext).load(girl.url).into(girlViewHolder.showGirl);
        }
    }

    @Override
    public int getItemCount() {
        return girls.size();
    }

    public class GirlViewHolder extends RecyclerView.ViewHolder {

        public final ImageView showGirl;

        public GirlViewHolder(@NonNull View itemView) {
            super(itemView);

            showGirl = (ImageView) itemView.findViewById(R.id.item_showgirl);
        }
    }

}
