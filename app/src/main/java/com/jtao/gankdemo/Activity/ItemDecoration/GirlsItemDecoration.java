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

public class GirlsItemDecoration extends RecyclerView.ItemDecoration {
    private static final int TYPE_NORMAL_LINE = 1;
    private static final int TYPE_GIRLS_LINE = 2;

    private int mType = TYPE_NORMAL_LINE;

    private Context mContext;

    private Paint mPaint;

    private static final int SEPERATE_GIRLS_LINE_HEIGHT = 10;

    private List mList;

    public GirlsItemDecoration(Context context) {
        this.mContext = context;

        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 抗锯齿
        mPaint.setDither(true);
    }

    public void setType(boolean isGirls) {
        mType = isGirls ? TYPE_GIRLS_LINE : TYPE_NORMAL_LINE;
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

            if (mType == TYPE_GIRLS_LINE) {
                drawGridSeperateLine(c, child, parent);
            } else {
                drawSeperateLine(c, child);
            }
        }

        c.restore();
    }

    /**
     *  绘制 grid 分隔线
     *
     * @param canvas
     * @param child
     */
    private void drawGridSeperateLine(Canvas canvas, View child, RecyclerView parent) {
        int position = parent.getChildAdapterPosition(child);

        if (position % 2 == 0) {
            // 绘制下间隔
            // 矩形左上顶点 = (ItemView的左边界,ItemView的下边界)
            final int ld = child.getLeft();
            final int td = child.getBottom();
            // 矩形右下顶点 = (ItemView的右边界,矩形的下边界)
            final int rd = child.getRight();
            final int bd = td + SEPERATE_GIRLS_LINE_HEIGHT;

            mPaint.setColor(Color.WHITE);
            canvas.drawRect(ld, td, rd, bd, mPaint);

            // 绘制右间隔
            // 矩形左上顶点 = (ItemView的左边界,ItemView的下边界)
            final int ld1 = child.getRight();
            final int td1 = child.getTop();
            // 矩形右下顶点 = (ItemView的右边界,矩形的下边界)
            final int rd1 = child.getRight() + SEPERATE_GIRLS_LINE_HEIGHT;
            final int bd1 = child.getBottom() + SEPERATE_GIRLS_LINE_HEIGHT;

            canvas.drawRect(ld1, td1, rd1, bd1, mPaint);
        } else {
            // 绘制下间隔
            // 绘制下间隔
            // 矩形左上顶点 = (ItemView的左边界,ItemView的下边界)
            final int ld = child.getLeft();
            final int td = child.getBottom();
            // 矩形右下顶点 = (ItemView的右边界,矩形的下边界)
            final int rd = child.getRight();
            final int bd = td + SEPERATE_GIRLS_LINE_HEIGHT;

            mPaint.setColor(Color.WHITE);
            canvas.drawRect(ld, td, rd, bd, mPaint);
        }
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
        final int bottom = top + SEPERATE_GIRLS_LINE_HEIGHT;

        mPaint.setColor(Color.WHITE);
        canvas.drawRect(left, top, right, bottom, mPaint);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        if (mList == null || mList.size() == 0 || mList.size() <= position || position < 0) {
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }

        if (mType == TYPE_GIRLS_LINE) {
            if (position % 2 == 0) {
                // 右、下间隔
                outRect.set(0, 0, SEPERATE_GIRLS_LINE_HEIGHT, SEPERATE_GIRLS_LINE_HEIGHT);
            } else {
                // 下间隔
                outRect.set(0, 0, 0, SEPERATE_GIRLS_LINE_HEIGHT);
            }
        } else {
            outRect.set(0, 0, 0, SEPERATE_GIRLS_LINE_HEIGHT);
        }

    }

}
