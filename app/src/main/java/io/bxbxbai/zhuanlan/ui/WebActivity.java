package io.bxbxbai.zhuanlan.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import butterknife.ButterKnife;
import io.bxbxbai.common.Tips;
import io.bxbxbai.common.activity.BaseActivity;
import io.bxbxbai.zhuanlan.ZhuanlanApplication;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.core.ZhuanLanWebChromeClient;
import io.bxbxbai.zhuanlan.core.ZhuanLanWebViewClient;
import io.bxbxbai.zhuanlan.utils.JsHandler;
import io.bxbxbai.zhuanlan.widget.CommonWebView;
import io.bxbxbai.zhuanlan.widget.FloatView;

public class WebActivity extends BaseActivity {
    private static final String TAG = "AboutActivity";

    private static final String KEY_URL = "key_url";
    private static final String KEY_TITLE = "key_title";

    public static final String URL_BXBXBAI = "http://bxbxbai.gitcafe.io/about/";

    private String url, title;

    private CommonWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        parseIntent(getIntent());

        ProgressBar bar = ButterKnife.findById(this, R.id.progress_bar);
        bar.setVisibility(View.GONE);
        View v = ButterKnife.findById(this, R.id.v_loading);

        mWebView = ButterKnife.findById(this, R.id.web_view);
        initWebSetting();

        mWebView.setWebChromeClient(new ZhuanLanWebChromeClient(bar, v,
                TextUtils.isEmpty(title) ? getSupportActionBar() : null));
        mWebView.setWebViewClient(new ZhuanLanWebViewClient(this));
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
            Tips.showToast("木有URL...");
            finish();
        }
        setTitle(TextUtils.isEmpty(title) ? getString(R.string.app_name) : title);
    }

    private void initWebSetting() {
        JsHandler jsHandler = new JsHandler(this, mWebView);
        mWebView.addJavascriptInterface(jsHandler, "JsHandler");
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
                ZhuanlanApplication.getInstance().startActivity(new Intent("com.bxbxbai.zhuanlan.ui.activity.AboutActivity"));
                Log.i(TAG, "onclick");
            }
        });

        wm.addView(view, wmParams);
    }

    public static boolean start(Activity activity, String url, String title) {
        Intent intent = new Intent();
        intent.setClass(activity, WebActivity.class);
        intent.putExtra(KEY_URL, url);
        intent.putExtra(KEY_TITLE, title);
        activity.startActivity(intent);

        return true;
    }

    public static boolean start(Activity activity, String url) {
        return start(activity, url, null);
    }
}
