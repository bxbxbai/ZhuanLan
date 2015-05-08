package io.bxbxbai.zhuanlan.utils;

import android.support.v7.app.ActionBar;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import io.bxbxbai.zhuanlan.view.circularprogress.CircularLoadingView;

/**
 *
 * @author bxbxbai
 */
public class ZhuanLanWebChromeClient extends WebChromeClient {

    private ProgressBar mBar;
    private View mLoadingView;
    private ActionBar mActionBar;

    public ZhuanLanWebChromeClient(ProgressBar bar, View loadingView) {
        this(bar, loadingView, null);
    }

    public ZhuanLanWebChromeClient(ProgressBar bar, View loadingView, ActionBar actionBar) {
        mBar = bar;
        mLoadingView = loadingView;
        mActionBar = actionBar;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (mActionBar != null) {
            mActionBar.setTitle(title);
        }
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (mBar != null) {
            mBar.setProgress(newProgress);
            if (newProgress >= 100) {
                mBar.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }


}
