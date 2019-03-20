package com.jtao.gankdemo.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jtao.gankdemo.Activity.Adapter.SearchAdapter;
import com.jtao.gankdemo.Activity.Fragment.SearchHistoryFragment;
import com.jtao.gankdemo.Activity.Fragment.SearchListFragment;
import com.jtao.gankdemo.Activity.Model.NewsMoshi;
import com.jtao.gankdemo.Activity.Model.NewsSubMoshi;
import com.jtao.gankdemo.Activity.Model.SearchItemMoshi;
import com.jtao.gankdemo.Activity.Util.NetworkService;
import com.jtao.gankdemo.R;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class SearchActivity extends BaseActivity implements SearchHistoryFragment.HistoryFragmentListener {

    @BindView(R.id.search_item_left)
    ImageButton backBtn;

    @BindView(R.id.search_item_right)
    ImageButton searchBtn;

    @BindView(R.id.search_input)
    EditText inputET;

    @BindView(R.id.search_content)
    LinearLayout contentView;

    private SearchHistoryFragment mHistoryFragment;
    private SearchListFragment mListFragment;

    private List<String> historyList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        // 初始化第一个
        mHistoryFragment = SearchHistoryFragment.newInstance(this);
        mHistoryFragment.setListener(this);

        mListFragment = SearchListFragment.newInstance(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.search_content, mHistoryFragment).show(mHistoryFragment).commit();
    }

    private void saveHistory() {
        String history = inputET.getText().toString();
        historyList.add(history);

        // 存储
        SharedPreferences.Editor editor = getSharedPreferences("search_history", Context.MODE_PRIVATE).edit();

        editor.putStringSet("histories", (Set<String>) historyList);

        editor.commit();
    }

    @OnClick(R.id.search_item_left)
    public void leftItemClick() {
        finish();
    }

    /**
     * 开始搜索
     *
     */
    @OnClick(R.id.search_item_right)
    public void rightItemClick() {
        if (inputET.getText().toString().equals("") || inputET.getText().toString() == null) {
            return;
        }

        // 1. 切换至list fragment
        switchFragment(mHistoryFragment, mListFragment);

        // 2. 开始请求数据
        mListFragment.setKeyword(inputET.getText().toString());
    }


    /**
     *  切换fragment
     *
     * @param last   上一个fragment
     * @param target 切换的目标fragment
     */
    private void switchFragment(Fragment last, Fragment target) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.hide(last);

        if (target.isAdded() == false) {
            transaction.add(R.id.search_content, target);
        } else  {
            transaction.show(target);
        }

        transaction.commitAllowingStateLoss();
    }

    /**
     *  搜索界面标间点击监听
     *
     * @param history
     */
    @Override
    public void historyItemClick(String history) {
        inputET.setText(history);
        inputET.setSelection(history.length());
    }

}
