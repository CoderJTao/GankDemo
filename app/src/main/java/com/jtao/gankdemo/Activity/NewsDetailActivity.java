package com.jtao.gankdemo.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jtao.gankdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends BaseActivity {

    @BindView(R.id.nav_item_left)
    ImageButton leftItem;

    @BindView(R.id.nav_title)
    TextView navTitle;

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
                selfBack();
            }
        });

        navTitle.setVisibility(View.INVISIBLE);

        // 2. 取出 url， 加载网页
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        mWebView.loadUrl(url);

        mWebView.setWebViewClient(new WebViewClient() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
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
