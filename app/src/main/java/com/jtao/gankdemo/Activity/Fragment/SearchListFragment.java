package com.jtao.gankdemo.Activity.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtao.gankdemo.Activity.Adapter.NewsAdapter;
import com.jtao.gankdemo.Activity.Adapter.SearchAdapter;
import com.jtao.gankdemo.Activity.ItemDecoration.ItemSeparateLine;
import com.jtao.gankdemo.Activity.ItemDecoration.NewsItemDecoration;
import com.jtao.gankdemo.Activity.Model.SearchItemMoshi;
import com.jtao.gankdemo.Activity.NewsDetailActivity;
import com.jtao.gankdemo.Activity.Util.NetworkService;
import com.jtao.gankdemo.R;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

public class SearchListFragment extends Fragment implements SearchAdapter.SearchItemClickListener {
    private Context mContext;

    private Unbinder unbinder;

    @BindView(R.id.search_list_recycleview)
    RecyclerView mRecyclerView;

    private List<SearchItemMoshi> searchItems = new ArrayList<>();
    private SearchAdapter searchAdapter;
    private ItemSeparateLine separateLine;

    public SearchListFragment() {
    }

    public static SearchListFragment newInstance(Context context) {
        SearchListFragment f = new SearchListFragment();
        f.mContext = context;
        return f;
    }

    public void setKeyword(String keyword) {
        initData(keyword);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_list, container,false);

        unbinder = ButterKnife.bind(this, view);

        initView();

        return view;
    }

    private void initView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.addItemDecoration(separateLine = new ItemSeparateLine(mContext));

        searchAdapter = new SearchAdapter(mContext);
        searchAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(searchAdapter);
    }

    private void initData(String keyword) {
        NetworkService.getSearchData(keyword, new NetworkService.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String jsonStr = response.body().string();
                try {
                    JSONObject object = new JSONObject(jsonStr);

                    // 获取到当前所有categoty
                    JSONArray results = object.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        String jStr = results.getJSONObject(i).toString();

                        Moshi moshi = new Moshi.Builder().build();
                        JsonAdapter<SearchItemMoshi> jsonAdapter = moshi.adapter(SearchItemMoshi.class);

                        SearchItemMoshi item = jsonAdapter.fromJson(jStr);
                        searchItems.add(item);
                    }

                    searchAdapter.setData(searchItems);
                    separateLine.setData(searchItems);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     *  搜索结果item点击
     *
     * @param searchItem
     */
    @Override
    public void onSearchItemClick(SearchItemMoshi searchItem) {
        Intent intent = new Intent(mContext, NewsDetailActivity.class);
        intent.putExtra("url", searchItem.url);
        mContext.startActivity(intent);
    }
}
