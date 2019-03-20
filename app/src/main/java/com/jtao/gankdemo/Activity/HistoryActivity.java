package com.jtao.gankdemo.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jtao.gankdemo.R;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ButterKnife.bind(this);

    }
}
