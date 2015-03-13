package io.bxbxbai.zhuanlan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.utils.StopWatch;

import java.io.IOException;
import java.util.Scanner;

/**
 * WebActivity
 *
 * @author bxbxbai
 */
public class WebActivity extends ActionBarActivity {

    public static final String KEY_URL = "key_url";

    public static final String KEY_CONTENT = "content";

    public static final String KEY_TITLE = "title";

    private static final String ENCODING_UTF_8 = "UTF-8";
    private static final String MIME_TYPE = "text/html";
    private static final String FILE_NAME = "web.txt";

    private WebView mWebView;

    private String mUrl;
    private String mTitle;
    private String mContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        MaterialMenuIconToolbar materialMenu = new MaterialMenuIconToolbar(this, Color.WHITE, MaterialMenuDrawable.Stroke.REGULAR) {
            @Override
            public int getToolbarViewId() {
                return R.id.toolbar;
            }
        };
        toolbar.inflateMenu(R.menu.main);
        materialMenu.setState(MaterialMenuDrawable.IconState.ARROW);

        mWebView = (WebView) findViewById(R.id.web_view);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mUrl = bundle.getString(KEY_URL);
            mTitle = bundle.getString(KEY_TITLE);
            mContent = bundle.getString(KEY_CONTENT);
        }

        toolbar.setTitle(mTitle);

        TextView textView = (TextView) findViewById(R.id.tv_html);

        String html = "\"距离大玩家第一次用<b>Lifemiles</b>换票，已经过了两年。这两年来，我算是发现了" +
                "：Lifemiles早就习惯变着法打折卖里程了。<br><br>上个月，Lifemiles曾推出转让里程送100%的" +
                "活动，而近期，他们又有里程<b>买一送一</b>的活动。<br><br><b>科普时间</b><br><br><b" +
                ">Lifemiles是什么？</b><br><br>";

//        textView.setTypeface(Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf"));
//        textView.setText(Html.fromHtml(html));

        initWebView();

        if (!TextUtils.isEmpty(mUrl)) {
            mWebView.loadUrl(mUrl);
        } else {
            loadData();
        }
    }


    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName(ENCODING_UTF_8);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                StopWatch.log("url " + url);
                WebActivity.startActivity(WebActivity.this, url);
                return true;
            }
        });
    }

    private void loadData() {
        mWebView.loadDataWithBaseURL(null, mContent, MIME_TYPE, ENCODING_UTF_8, null);
    }

    public static void startActivity(Context context, String url) {
        Intent i = new Intent(context, WebActivity.class);
        i.putExtra(KEY_URL, url);
        context.startActivity(i);
    }

    public static void startActivity(Context context, String title, String content) {
        Intent i = new Intent(context, WebActivity.class);
        i.putExtra(KEY_TITLE, title);
        i.putExtra(KEY_CONTENT, content);
        context.startActivity(i);
    }
}
