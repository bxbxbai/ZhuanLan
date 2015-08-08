package io.bxbxbai.zhuanlan.core;

import android.app.Activity;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import io.bxbxbai.common.StopWatch;
import io.bxbxbai.zhuanlan.activity.NewsDetailActivity;

/**
 *
 * @author bxbxbai
 */
public class ZhuanLanWebViewClient extends WebViewClient {


    private Activity mActivity;

    public ZhuanLanWebViewClient(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        StopWatch.log("url " + url);
        NewsDetailActivity.startActivity(mActivity, url);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }


}
