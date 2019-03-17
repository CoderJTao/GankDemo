package com.jtao.gankdemo.Activity.CustomView;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jtao.gankdemo.Activity.Util.DateUtil;
import com.jtao.gankdemo.R;

public class CalendarItem extends LinearLayout {

    // 日期 -- 日
    private TextView mDayText;

    // 日期 -- 周
    private TextView mWeekText;

    // 日期 -- 月
    private TextView mMonthText;

    public String dateStr;

    private Context mConetxt;

    public CalendarItem(Context context) {
        super(context);

        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.calendar_item, this);

        // 获取控件
        mDayText = (TextView) findViewById(R.id.calendar_item_day);
        mWeekText = (TextView) findViewById(R.id.calendar_item_week);
        mMonthText = (TextView) findViewById(R.id.calendar_item_month);
    }

    public void setListenr(View.OnClickListener context) {
        this.setOnClickListener(context);
    }

    /**
     *  设置数据
     *
     * @param dateTime  yyyy-MM-dd
     */
    public void setData(String dateTime) {
        this.dateStr = dateTime;

        String[] strs = dateTime.split("-");
        // 获取 day
        String day = strs[strs.length - 1];
        mDayText.setText(day);

        // 获取 week
        String week = DateUtil.getDayOfWeek(dateTime);
        mWeekText.setText(week);

        // 获取 月份
        String month = strs[1];
        switch (month) {
            case "01":
                mMonthText.setText("一月");
                break;
            case "02":
                mMonthText.setText("二月");
                break;
            case "03":
                mMonthText.setText("三月");
                break;
            case "04":
                mMonthText.setText("四月");
                break;
            case "05":
                mMonthText.setText("五月");
                break;
            case "06":
                mMonthText.setText("六月");
                break;
            case "07":
                mMonthText.setText("七月");
                break;
            case "08":
                mMonthText.setText("八月");
                break;
            case "09":
                mMonthText.setText("九月");
                break;
            case "10":
                mMonthText.setText("十月");
                break;
            case "11":
                mMonthText.setText("十一月");
                break;
            case "12":
                mMonthText.setText("十二月");
                break;
        }

    }


}
