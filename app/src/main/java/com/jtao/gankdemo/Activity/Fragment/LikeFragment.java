package com.jtao.gankdemo.Activity.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtao.gankdemo.Activity.Adapter.LikeAdapter;
import com.jtao.gankdemo.Activity.Adapter.NewsAdapter;
import com.jtao.gankdemo.Activity.Database.LikeDao;
import com.jtao.gankdemo.Activity.ItemDecoration.ItemSeparateLine;
import com.jtao.gankdemo.Activity.ItemDecoration.NewsItemDecoration;
import com.jtao.gankdemo.Activity.MainActivity;
import com.jtao.gankdemo.Activity.Model.NewsSubMoshi;
import com.jtao.gankdemo.Activity.NewsDetailActivity;
import com.jtao.gankdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LikeFragment extends Fragment implements MainActivity.OnNewsItemClickListener {

    private Context mContext;

    private String TAG = this.getClass().toString();

    private LikeDao likeDao;

    private List<NewsSubMoshi> likeNews = new ArrayList<>();

    private Unbinder unbinder;
    @BindView(R.id.like_recyclerview)
    RecyclerView mRecyclerView;

    private LikeAdapter mAdapter;
    private ItemSeparateLine mItemSeparateLine;

    public LikeFragment() { }

    public static LikeFragment newInstance(Context context) {
        LikeFragment f = new LikeFragment();
        f.mContext = context;
        f.likeDao = new LikeDao(context);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like, container, false);

        Log.d(TAG, "onCreateView: ");

        unbinder = ButterKnife.bind(this, view);

        initView();

        initData();

        return view;
    }

    private void initView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.addItemDecoration(mItemSeparateLine = new ItemSeparateLine(mContext));

        mAdapter = new LikeAdapter(mContext);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        this.likeNews = likeDao.queryAll();

        mAdapter.setData(this.likeNews);
        mItemSeparateLine.setData(this.likeNews);
    }

    @Override
    public void onClickItem(NewsSubMoshi subItem) {
        Intent intent = new Intent(mContext, NewsDetailActivity.class);
        intent.putExtra("url", subItem.url);
        intent.putExtra("title", subItem.desc);
        intent.putExtra("news_item", subItem);
        mContext.startActivity(intent);
    }



























    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();

        initData();

        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.d(TAG, "onDetach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
        Log.d(TAG, "onDestroyView: ");
    }
}
