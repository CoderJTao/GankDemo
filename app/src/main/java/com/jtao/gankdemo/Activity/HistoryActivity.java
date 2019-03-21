package com.jtao.gankdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jtao.gankdemo.Activity.Adapter.HistoryAdapter;
import com.jtao.gankdemo.Activity.Adapter.NewsAdapter;
import com.jtao.gankdemo.Activity.ItemDecoration.NewsItemDecoration;
import com.jtao.gankdemo.Activity.Model.NewsMoshi;
import com.jtao.gankdemo.Activity.Util.NetworkService;
import com.jtao.gankdemo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class HistoryActivity extends BaseActivity implements HistoryAdapter.OnHistoryItemClickListener {

    @BindView(R.id.nav_item_left)
    ImageButton leftItem;

    @BindView(R.id.nav_item_right)
    ImageButton rightItem;

    @BindView(R.id.nav_title)
    TextView mTitle;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private HistoryAdapter mAdapter;

    private List<String> datesList = new ArrayList<>();

    private List<NewsMoshi> historyItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ButterKnife.bind(this);

        initView();

        initDatesList();
    }

    @OnClick(R.id.nav_item_left)
    public void backClick() {
        finish();
    }

    /**
     * 初始化视图
     *
     */
    private void initView() {

        // left item
        leftItem.setImageResource(R.mipmap.back);

        // right item
        rightItem.setVisibility(View.INVISIBLE);

        // title
        mTitle.setText("干货历史");

        // RecyclerView
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        mAdapter = new HistoryAdapter(this);
        mAdapter.setOnHistoryClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     *  初始化所有历史日期
     *
     */
    private void initDatesList() {
        Intent intent = getIntent();
        String[] dates = intent.getStringArrayExtra("dateList");
        this.datesList.addAll(Arrays.asList(dates));


        initListData();
    }

    /**
     *  根据日期获取历史数据
     *
     */
    private void initListData() {
        List<String> lists = datesList.subList(0, 20);

        NetworkService.getHistoryData(lists, new NetworkService.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String jsonStr = response.body().string();
                try {
                    JSONObject object = new JSONObject(jsonStr);

                    // 获取到当前所有categoty
                    JSONArray categories = object.getJSONArray("category");

                    JSONObject object1 = object.getJSONObject("results");

                    NewsMoshi item = new NewsMoshi(categories, object1);

                    historyItems.add(item);

                    if (historyItems.size() == 20) {
                        mAdapter.setData(historyItems);
                    }

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
     *  点击视图回调
     *
     * @param item
     */
    @Override
    public void onClickItem(NewsMoshi item) {
        Toast.makeText(this, "点击Item" + item.todayGirl, Toast.LENGTH_SHORT).show();
    }
}
