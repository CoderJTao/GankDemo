package com.jtao.gankdemo.Activity.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtao.gankdemo.Activity.Adapter.CategoryAdapter;
import com.jtao.gankdemo.Activity.Adapter.NewsAdapter;
import com.jtao.gankdemo.Activity.ItemDecoration.ItemSeparateLine;
import com.jtao.gankdemo.Activity.ItemDecoration.NewsItemDecoration;
import com.jtao.gankdemo.Activity.MainActivity;
import com.jtao.gankdemo.Activity.Model.NewsMoshi;
import com.jtao.gankdemo.Activity.Model.NewsSubMoshi;
import com.jtao.gankdemo.Activity.Model.SearchItemMoshi;
import com.jtao.gankdemo.Activity.NewsDetailActivity;
import com.jtao.gankdemo.Activity.Util.NetworkService;
import com.jtao.gankdemo.R;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Response;

public class CategorySubFragment extends Fragment implements MainActivity.OnNewsItemClickListener {
    private Context mContext;

    private List<NewsSubMoshi> itemsList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;
    private ItemSeparateLine mItemSeparateLine;

    public CategorySubFragment() { }

    public static CategorySubFragment newInstance(Context context) {
        CategorySubFragment f = new CategorySubFragment();
        f.mContext = context;
        return f;
    }

    public String category = "All";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_sub, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.category_sub_recyclerview);

        initView();

        initData();

        return view;
    }

    private void initView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.addItemDecoration(mItemSeparateLine = new ItemSeparateLine((mContext)));

        mAdapter = new CategoryAdapter(mContext);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClickItem(NewsSubMoshi subItem) {
        Intent intent = new Intent(mContext, NewsDetailActivity.class);
        intent.putExtra("url", subItem.url);
        intent.putExtra("title", subItem.desc);
        intent.putExtra("news_item", subItem);
        mContext.startActivity(intent);
    }

    private void initData() {
        if (category.equals("全部")) {
            category = "all";
        }
        NetworkService.getCategoryData(category, new NetworkService.MyNetCall() {
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
                        JsonAdapter<NewsSubMoshi> jsonAdapter = moshi.adapter(NewsSubMoshi.class);

                        NewsSubMoshi item = jsonAdapter.fromJson(jStr);
                        itemsList.add(item);
                    }

                    mAdapter.setData(itemsList);
                    mItemSeparateLine.setData(itemsList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });

    }

}
