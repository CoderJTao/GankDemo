package com.jtao.gankdemo.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jtao.gankdemo.Activity.Database.LikeDao;
import com.jtao.gankdemo.Activity.Model.NewsSubMoshi;
import com.jtao.gankdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends BaseActivity {

    @BindView(R.id.nav_item_left)
    ImageButton leftItem;

    @BindView(R.id.nav_title)
    TextView navTitle;

    @BindView(R.id.nav_item_right)
    ImageButton rightItem;

    @BindView(R.id.webview)
    WebView mWebView;

    private LikeDao likeDao = new LikeDao(this);

    private NewsSubMoshi item;
    private boolean isLiked = false;

    private List<NewsSubMoshi> likeNews = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        ButterKnife.bind(this);

        initData();

        initView();
    }

    private void initData() {
        List<NewsSubMoshi> lists = likeDao.queryAll();

        this.likeNews = lists;
    }

    private void initView() {
        // 1. 初始化返回按钮
        leftItem.setImageResource(R.mipmap.back);
        leftItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selfBack();
            }
        });

        // 2. 取出 url， 加载网页
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        item = intent.getParcelableExtra("news_item");

        mWebView.loadUrl(url);

        mWebView.setWebViewClient(new WebViewClient() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });

        String title = intent.getStringExtra("title");
        navTitle.setText(title);

        // 3. 收藏按钮
        rightItem.setImageResource(R.mipmap.like_s);
        // 未收藏
        rightItem.setColorFilter(Color.WHITE);
        isLiked = false;
        for (NewsSubMoshi moshi: likeNews) {
            if (moshi.newsId.equals(item.newsId)) {
                // 已收藏的
                rightItem.setColorFilter(Color.RED);
                isLiked = true;
            }
        }

        rightItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLiked) {
                    rightItem.setColorFilter(Color.WHITE);
                    setUnLiked();
                } else {
                    rightItem.setColorFilter(Color.RED);
                    setLiked();
                }
            }
        });
    }

    private void setLiked() {
        likeDao.insert(item);
    }

    private void setUnLiked() {
        likeDao.delete(item);
    }


    @Override
    public void onBackPressed() {
        selfBack();
    }

    private void selfBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }

        finish();
    }

    /**
     *  覆盖即将到来的跳转动画
     *
     * @param enterAnim
     * @param exitAnim
     */
    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }
}
