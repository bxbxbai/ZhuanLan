package io.bxbxbai.zhuanlan.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import butterknife.ButterKnife;
import io.bxbxbai.common.activity.BaseActivity;
import io.bxbxbai.common.utils.GlobalExecutor;
import io.bxbxbai.common.view.CircleImageView;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.core.ZhuanLanApi;
import io.bxbxbai.zhuanlan.core.ZhuanLanWebChromeClient;
import io.bxbxbai.zhuanlan.core.ZhuanLanWebViewClient;

import java.io.IOException;
import java.util.Scanner;


/**
 * WebActivity
 *
 * @author bxbxbai
 */
public class NewsDetailActivity extends BaseActivity {

    public static final String KEY_URL = "key_url";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_TITLE = "title";
    public static final String KEY_POST = "post";

    private static final String CSS_STYLE = "<style>*{font-size:42px;line-height:65px;}" +
            "p{color:#000; margin:50px 30px;font-family:Courier New,Arial;}" +
            "img {min-width:100%; max-width:100%; display:block; margin:30px auto 30px;}</style>";

    public static final String ENCODING_UTF_8 = "UTF-8";
    private static final String MIME_TYPE = "text/html";

    private WebView mWebView;
    private CircleImageView mAvatarView;
    private View mLoadingView;

    private String mUrl;
    private String mTitle;
    private Post mPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initToolBar();

        mWebView = (WebView) findViewById(R.id.web_view);
        TextView titleView = (TextView) findViewById(R.id.tv_title);
        TextView nameView = (TextView) findViewById(R.id.tv_name);
        mLoadingView = ButterKnife.findById(this, R.id.v_loading);
//        mAvatarView = (CircleImageView) findViewById(R.id.iv_avatar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mUrl = bundle.getString(KEY_URL);
            mTitle = bundle.getString(KEY_TITLE);
            mPost = bundle.getParcelable(KEY_POST);
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return menuItem.getItemId() == R.id.action_origin_web_page && mPost != null
                        && WebActivity.start(NewsDetailActivity.this,
                        ZhuanLanApi.ZHUAN_LAN_URL + mPost.getUrl());
            }
        });
        setTitle(mTitle);
        initWebView();

        if (!TextUtils.isEmpty(mUrl)) {
            mWebView.loadUrl(mUrl);
            return;
        }
//        mWebView.loadDataWithBaseURL(null, CSS_STYLE + mPost.getContent(), MIME_TYPE, ENCODING_UTF_8, null);
        if (mPost != null) {
            loadHtmlContent(mPost.getContent());
            setTitle(mPost.getTitle());
//            mTitleView.setText(mPost.getTitle());
//            mNameView.setText(mPost.getAuthor().getZhuanlanName());
//
//            String id = mPost.getAuthor().getAvatar().getId();
//            String picUrl = Utils.getAuthorAvatarUrl(mPost.getAuthor().getAvatar().getTemplate(),
//                    id, ZhuanLanApi.PIC_SIZE_XL);
//
//            mAvatarView.setImageUrl(picUrl, App.getInstance().getImageLoader());
            GlobalExecutor.MAIN_HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    injectCSS();
                }
            }, 200);
        } else if (mUrl != null) {
            mWebView.loadUrl(mUrl);
        } else {
            mWebView.loadUrl("http://zhihu.com");
        }
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        //设置缓存模式
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //开启DOM storage API功能
        settings.setDomStorageEnabled(true);
        //开启database storage 功能
        settings.setDatabaseEnabled(true);

        String cacheDir = getFilesDir().getAbsolutePath() + "web_cache";
        settings.setAppCachePath(cacheDir);
        settings.setAppCacheEnabled(true);

        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName(ENCODING_UTF_8);
        settings.setBlockNetworkImage(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm
                .SINGLE_COLUMN);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebChromeClient(new ZhuanLanWebChromeClient(null, mLoadingView));
        mWebView.setWebViewClient(new ZhuanLanWebViewClient(this));
//        mWebView.addJavascriptInterface();
    }

    private void loadHtmlContent(String section) {
        String content = String.format(readFile("template.txt"), section);
        mWebView.loadDataWithBaseURL(null, content, MIME_TYPE, ENCODING_UTF_8, null);
    }

    private void injectCSS() {
        String encoded = Base64.encodeToString(readFile("zhuanlan.main.css").getBytes(), Base64.NO_WRAP);
        mWebView.loadUrl("javascript:(function() {" +
                "var parent = document.getElementsByTagName('head').item(0);" +
                "var style = document.createElement('style');" +
                "style.type = 'text/css';" +
                // Tell the browser to BASE64-decode the string into your script !!!
                "style.innerHTML = window.atob('" + encoded + "');" +
                "parent.appendChild(style)" +
                "})()");
    }

    private String readFile(String fileName) {
        AssetManager manager = getAssets();
        try {
            Scanner scanner = new Scanner(manager.open(fileName));
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNext()) {
                builder.append(scanner.nextLine());
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mPost != null) {
            getMenuInflater().inflate(R.menu.web, menu);
            return true;
        }
        return false;
    }

    public static void startActivity(Context context, String url) {
        Intent i = new Intent(context, NewsDetailActivity.class);
        i.putExtra(KEY_URL, url);
        context.startActivity(i);
    }

    public static void startActivity(Context context, String title, String content) {
        Intent i = new Intent(context, NewsDetailActivity.class);
        i.putExtra(KEY_TITLE, title);
        i.putExtra(KEY_CONTENT, content);
        context.startActivity(i);
    }

    public static void startActivity(Context context, Post post) {
        Intent i = new Intent(context, NewsDetailActivity.class);
        i.putExtra(KEY_POST, post);
        context.startActivity(i);
    }
}
