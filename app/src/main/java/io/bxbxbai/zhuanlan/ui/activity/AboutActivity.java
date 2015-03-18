package io.bxbxbai.zhuanlan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import butterknife.ButterKnife;
import io.bxbxbai.zhuanlan.App;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.utils.ZhuanLanWebChromeClient;
import io.bxbxbai.zhuanlan.view.FloatView;

public class AboutActivity extends BaseActivity {
    private static final String TAG = "AboutActivity";

    public static final String URL_BXBXBAI = "http://bxbxbai.gitcafe.io/about/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initToolBar();
        getSupportActionBar().setTitle("About Me");

        WebView view = ButterKnife.findById(this, R.id.web_view);
        ProgressBar bar = ButterKnife.findById(this, R.id.progress_bar);
        bar.setVisibility(View.GONE);
        View v = ButterKnife.findById(this ,R.id.v_loading);

        WebSettings settings = view.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName(WebActivity.ENCODING_UTF_8);
        settings.setBlockNetworkImage(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        view.setWebChromeClient(new ZhuanLanWebChromeClient(bar, v));

        view.loadUrl(URL_BXBXBAI);

//        if(savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment()).commit();
//        }
//        createView();
    }

    private void createView() {
        FloatView view = new FloatView(getApplicationContext());

        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams wmParams = ((App) getApplication()).getWindowManagerParams();
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

}
