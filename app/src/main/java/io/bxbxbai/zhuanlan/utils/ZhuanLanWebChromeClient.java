package io.bxbxbai.zhuanlan.utils;

import android.view.View;
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

    public ZhuanLanWebChromeClient(ProgressBar bar, View loadingView) {
        this.mBar = bar;
        mLoadingView = loadingView;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
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
}
