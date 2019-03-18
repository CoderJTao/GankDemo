package com.jtao.gankdemo.Activity.ItemDecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.jtao.gankdemo.Activity.Model.NewsMoshi;
import com.jtao.gankdemo.Activity.Model.NewsSubMoshi;

import java.util.List;

public class NewsItemDecoration extends RecyclerView.ItemDecoration {

    private Context mContext;
    private Paint mPaint;

    private List<String> tagsList;
    private List<NewsSubMoshi> newsList;

    private static final int HEADER_HEIGHT = 150;
    private static final int SEPERATE_LINE_HEIGHT = 2;

    public NewsItemDecoration(Context context) {
        this.mContext = context;

        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 抗锯齿
        mPaint.setDither(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setData(NewsMoshi news) {
        this.tagsList = news.tagsLists;
        this.newsList = news.newsList;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }

        c.save();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int position = params.getViewLayoutPosition();
            if (newsList == null || newsList.size() ==0 || newsList.size() <= position || position < 0) {
                continue;
            }

            if (position == 0) {
                // 第一条数据肯定是有Header的，取 NewsSubMoshi 里面的额 Type 字段
                drawHeaderView(c, parent, child, newsList.get(position));

                // 绘制分割线
//                drawSeperateLine(c, child);
            } else if (position > 0) {

                // 后续当检测到 NewsSubMoshi 中 type 字段值变化时，画出 Header
                if (!newsList.get(position).type.equals(newsList.get(position-1).type)) {
                    drawHeaderView(c, parent, child, newsList.get(position));
                } else {
//                    drawSeperateLine(c, child);
                }
            }
            drawSeperateLine(c, child);
        }

        c.restore();
    }

    /**
     *  绘制 HeaderView
     *
     * @param canvas
     * @param parent
     * @param child
     * @param item
     */
    private void drawHeaderView(Canvas canvas, RecyclerView parent, View child, NewsSubMoshi item) {
        // 矩形左上顶点 = (ItemView的左边界,ItemView的下边界)
        final int left = child.getLeft();
        final int top = child.getTop() - HEADER_HEIGHT;
        // 矩形右下顶点 = (ItemView的右边界,矩形的下边界)
        final int right = child.getRight();
        final int bottom = top + HEADER_HEIGHT;

        // 通过 canvas 绘制矩形 (头部视图底色)
        mPaint.setColor(Color.rgb(230, 230,230));
        canvas.drawRect(left, top, right, bottom, mPaint);

        // 绘制左边色块
        mPaint.setColor(Color.rgb(0, 133, 119));

        int sep = HEADER_HEIGHT / 4;
        canvas.drawRect(left,top + sep, left + 20, bottom - sep, mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(60);
        mPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(item.type, 80, bottom - HEADER_HEIGHT/3, mPaint);
    }

    /**
     *  绘制 分隔线
     *
     * @param canvas
     * @param child
     */
    private void drawSeperateLine(Canvas canvas, View child) {
        // 矩形左上顶点 = (ItemView的左边界,ItemView的下边界)
        final int left = child.getLeft();
        final int top = child.getBottom();
        // 矩形右下顶点 = (ItemView的右边界,矩形的下边界)
        final int right = child.getRight();
        final int bottom = top + SEPERATE_LINE_HEIGHT;

        mPaint.setColor(Color.rgb(230, 230,230));
        canvas.drawRect(left, top, right, bottom, mPaint);
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int postion = parent.getChildAdapterPosition(view);

        if (newsList == null || newsList.size() == 0 || newsList.size() <= postion || postion < 0) {
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }

        if (postion == 0) {
            outRect.set(0, HEADER_HEIGHT, 0, SEPERATE_LINE_HEIGHT);
        } else if (postion > 0) {
            if (!newsList.get(postion).type.equals(newsList.get(postion-1).type)) {
                outRect.set(0, HEADER_HEIGHT, 0, SEPERATE_LINE_HEIGHT);
            } else {
                outRect.set(0, 0, 0, SEPERATE_LINE_HEIGHT);
            }
        }
    }
}
