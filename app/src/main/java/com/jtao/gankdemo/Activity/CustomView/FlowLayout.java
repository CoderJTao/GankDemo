package com.jtao.gankdemo.Activity.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 *  自定义流式布局
 *
 */
public class FlowLayout extends ViewGroup {

    private Context mContext;

    public FlowLayout(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    private List<List<View>> mLineViews = new ArrayList<List<View>>();
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    /**
     *  测量所有子View大小，确定ViewGroup的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);



    }


    /**
     * 设置ViewGroup里子View的具体位置
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

}
