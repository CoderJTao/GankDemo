package com.jtao.gankdemo.Activity.Fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import android.widget.Toast;

import com.jtao.gankdemo.Activity.Adapter.NewsAdapter;
import com.jtao.gankdemo.Activity.ItemDecoration.NewsItemDecoration;
import com.jtao.gankdemo.Activity.MainActivity;
import com.jtao.gankdemo.Activity.Model.NewsMoshi;
import com.jtao.gankdemo.Activity.Model.NewsSubMoshi;
import com.jtao.gankdemo.Activity.NewsDetailActivity;
import com.jtao.gankdemo.Activity.Util.NetworkService;
import com.jtao.gankdemo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

public class NewFragment extends Fragment implements NewsAdapter.OnNewsItemClickListener {

    private static Context mContext;

    private String TAG = this.getClass().toString();

    private Unbinder unbinder;

    private List<String> dateLists;

    private NewsMoshi newsData;

    private NewsAdapter newsAdapter;
    private NewsItemDecoration newsItemDecoration;

    @BindView(R.id.newRecyclerView)
    RecyclerView newRecyclerView;

    public NewFragment() {

    }

    public static NewFragment newInstance(Context context) {
        NewFragment f = new NewFragment();
        f.mContext = context;
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: ");

        dateLists = new ArrayList<>();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new, container, false);

        Log.d(TAG, "onCreateView: ");

        unbinder = ButterKnife.bind(this, view);

        initView();

        initData();

        return view;
    }

    private void initView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        newRecyclerView.setLayoutManager(manager);

        newRecyclerView.addItemDecoration(newsItemDecoration = new NewsItemDecoration((mContext)));

        newsAdapter = new NewsAdapter(mContext);
        newsAdapter.setOnItemClickListener(this);
        newRecyclerView.setAdapter(newsAdapter);
    }

    @Override
    public void onClickItem(NewsSubMoshi subItem) {
        Intent intent = new Intent(mContext, NewsDetailActivity.class);
        intent.putExtra("url", subItem.url);
        intent.putExtra("title", subItem.desc);
        mContext.startActivity(intent);
    }

    private void initData() {
        // 获取当日数据
        NetworkService.getToDayData(new NetworkService.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String jsonStr = response.body().string();
                try {
                    JSONObject object = new JSONObject(jsonStr);

                    // 获取到当前所有categoty
                    JSONArray categories = object.getJSONArray("category");

                    JSONObject object1 = object.getJSONObject("results");

                    newsData = new NewsMoshi(categories, object1);

                    newsAdapter.setData(newsData);
                    newsItemDecoration.setData(newsData);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });
    }

    /**
     *  根据日期获取当日数据
     *
     * @param time
     */
    public void getTargetData(String time) {
        NetworkService.getTargetData(time, new NetworkService.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String jsonStr = response.body().string();
                try {
                    JSONObject object = new JSONObject(jsonStr);

                    // 获取到当前所有categoty
                    JSONArray categories = object.getJSONArray("category");

                    JSONObject object1 = object.getJSONObject("results");

                    newsData = new NewsMoshi(categories, object1);

                    newsAdapter.setData(newsData);
                    newsItemDecoration.setData(newsData);

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();

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
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
        Log.d(TAG, "onDestroyView: ");
    }
}
