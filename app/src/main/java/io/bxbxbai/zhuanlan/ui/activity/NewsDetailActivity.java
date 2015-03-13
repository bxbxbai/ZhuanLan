package io.bxbxbai.zhuanlan.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.MonthDisplayHelper;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import io.bxbxbai.zhuanlan.R;

/**
 * Created by baia on 14-8-20.
 * @author bxbxbai
 * @since 2014.08.20
 */
public class NewsDetailActivity extends Activity {
    /**
     *
     */
    public static final String FLAG_WEB_CONTENT = "flag_web_content";
    /**
     *
     */
    public static final String FLAG_WEB_TITLE = "flag_web_title";

    WebView mWebView;
    ProgressDialog mDialog;

    ActionBar mActionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        mWebView = (WebView)findViewById(R.id.ww_news_detail);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);

        String content = getIntent().getExtras().getString(FLAG_WEB_CONTENT);
        String title = getIntent().getExtras().getString(FLAG_WEB_TITLE);
        getActionBar().setTitle(title);

        String type = "text/html";
        String encoding = "GBK";
//        mWebView.loadData(content, type, null);
        mWebView.loadDataWithBaseURL(null, content, type, encoding, null);

        String dataBasePath = this.getDir("database", Context.MODE_PRIVATE).getPath();
        mDialog = ProgressDialog.show(this, "Loading...", "加载中...");

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onLoadResource(WebView view, String url) {
                mDialog.show();
                mDialog.onStart();
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mDialog.isShowing()) {
                    mDialog.cancel();
                }
            }
        });
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                return false;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });



//        mWebView.addJavascriptInterface();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
