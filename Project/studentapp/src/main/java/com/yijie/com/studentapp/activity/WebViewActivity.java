package com.yijie.com.studentapp.activity;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.news_detail_wv)
    WebView newsDetailWv;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_webview);
    }

    @Override
    public void init() {

        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
//        newsDetailWv.loadUrl("http://www.baidu.com/");
        String user = getIntent().getStringExtra("user");
        if (user.equals("隐私条款")){
            newsDetailWv.loadUrl("file:///android_asset/concealement.html");
        }else if (user.equals("新闻消息")){
            newsDetailWv.loadUrl(getIntent().getStringExtra("url"));
        }
        else {
            newsDetailWv.loadUrl("file:///android_asset/serviceagment.html");
        }
        title.setText(getIntent().getStringExtra("user"));
        newsDetailWv.setHorizontalScrollBarEnabled(false);
        newsDetailWv.getSettings().setJavaScriptEnabled(true);
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                commonDialog.show();
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                commonDialog.dismiss();
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        };
        newsDetailWv.setWebViewClient(webViewClient);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
