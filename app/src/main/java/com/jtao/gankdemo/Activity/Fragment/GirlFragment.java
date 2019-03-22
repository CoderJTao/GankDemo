package com.jtao.gankdemo.Activity.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtao.gankdemo.Activity.Adapter.GirlAdapter;
import com.jtao.gankdemo.Activity.Adapter.NewsAdapter;
import com.jtao.gankdemo.Activity.ItemDecoration.GirlsItemDecoration;
import com.jtao.gankdemo.Activity.ItemDecoration.ItemSeparateLine;
import com.jtao.gankdemo.Activity.ItemDecoration.NewsItemDecoration;
import com.jtao.gankdemo.Activity.Model.NewsMoshi;
import com.jtao.gankdemo.Activity.Model.NewsSubMoshi;
import com.jtao.gankdemo.Activity.Util.NetworkService;
import com.jtao.gankdemo.R;
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

public class GirlFragment extends Fragment {

    private Context mContext;
    private Unbinder unbinder;

    private String TAG = this.getClass().toString();

    private boolean isGrid = false;

    @BindView(R.id.girl_recyclerview)
    RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;

    private GirlAdapter mAdapter;
    private GirlsItemDecoration mItemDecoration;
    private List<NewsSubMoshi> girlsList = new ArrayList<>();


    public GirlFragment() { }

    public static GirlFragment newInstance(Context context) {
        GirlFragment f = new GirlFragment();
        f.mContext = context;
        return f;
    }

    public void changeLayout() {
        isGrid = !isGrid;

        if (isGrid) {
            mLayoutManager = new GridLayoutManager(mContext, 2);
            ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mItemDecoration.setType(true);
        } else {
            mLayoutManager = new LinearLayoutManager(mContext);
            ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mItemDecoration.setType(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, container, false);
        Log.d(TAG, "onCreateView: ");

        unbinder = ButterKnife.bind(this, view);

        initView();

        initData();

        return view;
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(mContext);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mItemDecoration = new GirlsItemDecoration(mContext);
        mItemDecoration.setType(false);

        mRecyclerView.addItemDecoration(mItemDecoration);

        mAdapter = new GirlAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 获取妹子数据
     */
    private void initData() {
        NetworkService.getGirlsData(new NetworkService.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String jsonStr = response.body().string();
                try {
                    JSONObject object = new JSONObject(jsonStr);

                    JSONArray girlsArr = object.getJSONArray("results");
                    for (int i = 0; i < girlsArr.length(); i++) {
                        String str = girlsArr.getJSONObject(i).toString();

                        Moshi moshi = new Moshi.Builder().build();
                        JsonAdapter<NewsSubMoshi> jsonAdapter = moshi.adapter(NewsSubMoshi.class);

                        NewsSubMoshi item = jsonAdapter.fromJson(str);
                        girlsList.add(item);
                    }

                    mAdapter.setData(girlsList);
                    mItemDecoration.setData(girlsList);

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
