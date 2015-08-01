package io.bxbxbai.zhuanlan.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import butterknife.ButterKnife;
import io.bxbxbai.zhuanlan.App;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.utils.JsHandler;
import io.bxbxbai.zhuanlan.utils.T;
import io.bxbxbai.zhuanlan.core.ZhuanLanWebChromeClient;
import io.bxbxbai.zhuanlan.utils.ZhuanlanWebViewClient;
import io.bxbxbai.zhuanlan.widget.FloatView;

public class WebActivity extends BaseActivity {
    private static final String TAG = "AboutActivity";

    private static final String KEY_URL = "key_url";
    private static final String KEY_TITLE = "key_title";

    public static final String URL_BXBXBAI = "http://bxbxbai.gitcafe.io/about/";

    private String url, title;

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initToolBar();
        parseIntent(getIntent());

        ProgressBar bar = ButterKnife.findById(this, R.id.progress_bar);
        bar.setVisibility(View.GONE);
        View v = ButterKnife.findById(this, R.id.v_loading);

        mWebView = ButterKnife.findById(this, R.id.web_view);
        initWebSetting(mWebView);
        mWebView.setWebChromeClient(new ZhuanLanWebChromeClient(bar, v, TextUtils.isEmpty(title) ?
                getSupportActionBar() : null));

        mWebView.setWebViewClient(new ZhuanlanWebViewClient());
        mWebView.loadUrl(url);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        parseIntent(intent);
    }

    private void parseIntent(Intent intent) {
        url = intent.getStringExtra(KEY_URL);
        title = intent.getStringExtra(KEY_TITLE);

        if (TextUtils.isEmpty(url)) {
            T.showShort("木有URL...");
            finish();
        }

        if (TextUtils.isEmpty(title)) {
            getSupportActionBar().setTitle(R.string.app_name);
        } else {
            getSupportActionBar().setTitle(title);
        }
    }

    private void initWebSetting(WebView view) {
        WebSettings settings = view.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setDomStorageEnabled(true);

        settings.setBuiltInZoomControls(false);
        settings.setAllowFileAccess(true);

        settings.setAppCacheEnabled(true);

        JsHandler jsHandler = new JsHandler(this, mWebView);
        mWebView.addJavascriptInterface(jsHandler, "JsHandler");

        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName(NewsDetailActivity.ENCODING_UTF_8);
        settings.setBlockNetworkImage(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
    }

    private void createView() {
        FloatView view = new FloatView(getApplicationContext());

        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags |= 8;
        wmParams.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        wmParams.x = 0;
        wmParams.y = 80;
        wmParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        wmParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getInstance().startActivity(new Intent("com.bxbxbai.zhuanlan.ui.activity.AboutActivity"));
                Log.i(TAG, "onclick");
            }
        });

        wm.addView(view, wmParams);
    }

    public static boolean start(BaseActivity activity, String url, String title) {
        Intent intent = new Intent();
        intent.setClass(activity, WebActivity.class);
        intent.putExtra(KEY_URL, url);
        intent.putExtra(KEY_TITLE, title);
        activity.startActivity(intent);

        return true;
    }

    public static boolean start(BaseActivity activity, String url) {
        return start(activity, url, null);
    }
}
