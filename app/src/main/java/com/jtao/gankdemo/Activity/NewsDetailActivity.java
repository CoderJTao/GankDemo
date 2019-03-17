package com.jtao.gankdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.jtao.gankdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends BaseActivity {

    @BindView(R.id.nav_item_left)
    ImageButton leftItem;

    @BindView(R.id.webview)
    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        // 1. 初始化返回按钮
        leftItem.setImageResource(R.mipmap.back);
        leftItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 返回上一个视图
                onBackPressed();
                finish();
            }
        });

        // 2. 取出 url， 加载网页
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        mWebView.loadUrl(url);
    }
}
