package com.jtao.gankdemo.Activity.ItemDecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class ItemSeparateLine extends RecyclerView.ItemDecoration {
    private Context mContext;

    private Paint mPaint;

    private static final int SEPERATE_LINE_HEIGHT = 2;

    private List mList;

    public ItemSeparateLine(Context context) {
        this.mContext = context;

        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 抗锯齿
        mPaint.setDither(true);
    }

    public void setData(List list) {
        this.mList = list;
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
            if (mList == null || mList.size() ==0 || mList.size() <= position || position < 0) {
                continue;
            }

            drawSeperateLine(c, child);
        }

        c.restore();
    }

    /**
     *  绘制 normal 分隔线
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
        int position = parent.getChildAdapterPosition(view);

        if (mList == null || mList.size() == 0 || mList.size() <= position || position < 0) {
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }

        outRect.set(0, 0, 0, SEPERATE_LINE_HEIGHT);
    }
}
