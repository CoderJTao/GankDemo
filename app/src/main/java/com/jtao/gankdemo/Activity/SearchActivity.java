package com.jtao.gankdemo.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.jtao.gankdemo.Activity.Fragment.SearchHistoryFragment;
import com.jtao.gankdemo.Activity.Fragment.SearchListFragment;
import com.jtao.gankdemo.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.search_item_left)
    ImageButton backBtn;

    @BindView(R.id.search_item_right)
    ImageButton searchBtn;

    @BindView(R.id.search_input)
    EditText inputET;

    @BindView(R.id.search_content)
    LinearLayout contentView;

    private SearchHistoryFragment historyFragment;
    private SearchListFragment listFragment;

    private List<String> historyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        historyList = new ArrayList<>();
        initData();

        initView();
    }

    private void initView() {
        historyFragment = SearchHistoryFragment.newInstance(this);
        listFragment = SearchListFragment.newInstance(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.search_content, historyFragment).show(historyFragment).commit();

    }

    private void initData() {
        if (historyList.size() > 0) {
            historyList.removeAll(historyList);
        }

        // 创建一个SharedPreferences接口对象
        SharedPreferences read = getSharedPreferences("search_history", Context.MODE_PRIVATE);

        // 获取文件中的值
        HashSet<String> sets = (HashSet<String>) read.getStringSet("histories", new HashSet<String>());

        historyList.addAll(sets);
    }

    private void saveHistory() {
        String history = inputET.getText().toString();
        historyList.add(history);

        // 存储
        SharedPreferences.Editor editor = getSharedPreferences("search_history", Context.MODE_PRIVATE).edit();

        editor.putStringSet("histories", (Set<String>) historyList);

        editor.commit();
    }
}
