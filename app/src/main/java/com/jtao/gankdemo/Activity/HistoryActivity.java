package com.jtao.gankdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jtao.gankdemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends BaseActivity {

    @BindView(R.id.nav_item_left)
    ImageButton leftItem;

    @BindView(R.id.nav_item_right)
    ImageButton rightItem;

    @BindView(R.id.nav_title)
    TextView mTitle;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;


    private List<String> datesList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ButterKnife.bind(this);

        initDatesList();
    }

    /**
     *  初始化所有历史日期
     *
     */
    private void initDatesList() {
        Intent intent = getIntent();
        String[] dates = intent.getStringArrayExtra("dateList");
        this.datesList.addAll(Arrays.asList(dates));
    }

    /**
     *  根据日期获取历史数据
     *
     */
    private void initListData() {


    }
}
