package com.jtao.gankdemo.Activity.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

    // 定义一个接口
    public interface OnGirlsItemClickListener {
        void onGirlClick(Bitmap bitmap);
    }
    // 定义自己的属性
    private GirlAdapter.OnGirlsItemClickListener listener;
    // 写一个公共方法，传入listener
    public void setGirlsClickListener(GirlAdapter.OnGirlsItemClickListener listener) {
        this.listener = listener;
    }


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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_girls, viewGroup, false);
        return new GirlViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GirlViewHolder girlViewHolder, int i) {
        if (girls == null || girls.size() < 0 || girls.size() <= i || i < 0) {
            return;
        }

        final NewsSubMoshi girl = girls.get(i);

        girlViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable)girlViewHolder.showGirl.getDrawable()).getBitmap();
                listener.onGirlClick(bitmap);
            }
        });

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
