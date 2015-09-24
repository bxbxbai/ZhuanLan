package io.bxbxbai.zhuanlan.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.android.volley.toolbox.NetworkImageView;
import io.bxbxbai.common.StopWatch;
import io.bxbxbai.common.activity.BaseActivity;
import io.bxbxbai.common.core.RequestManager;
import io.bxbxbai.common.utils.GlobalExecutor;
import io.bxbxbai.common.view.CircleImageView;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.core.ZhuanLanApi;
import io.bxbxbai.zhuanlan.core.ZhuanLanWebViewClient;
import io.bxbxbai.zhuanlan.utils.Utils;
import io.bxbxbai.zhuanlan.widget.CommonWebView;
import io.bxbxbai.zhuanlan.widget.ObservableScrollView;

import java.io.IOException;
import java.util.Scanner;


/**
 * WebActivity
 *
 * @author bxbxbai
 */
public class StoryActivity extends BaseActivity {

    private static final String KEY_POST = "_post";

    private ObservableScrollView scrollView;
    private CommonWebView mWebView;
    private CircleImageView mAvatarView;
    private NetworkImageView headerImageView;
    private TextView titleView;
    private TextView authorTextView;

    private Post mPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        initToolBar();

        mWebView = ButterKnife.findById(this, R.id.web_view);
        scrollView = ButterKnife.findById(this, R.id.scroll_view);
        titleView = ButterKnife.findById(this, R.id.tv_title);
        authorTextView = ButterKnife.findById(this, R.id.tv_author);
        headerImageView = ButterKnife.findById(this, R.id.iv_article_header);
        mAvatarView = (CircleImageView) findViewById(R.id.iv_avatar);

        scrollView.addOnScrollListener(new ObservableScrollView.OnScrollListener() {
            @Override
            public void onScroll(int distance) {
                StopWatch.log("dis: " + distance);
            }
        });

        mPost = getIntent().getParcelableExtra(KEY_POST);
        if (mPost == null) {
            finish();
            return;
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return menuItem.getItemId() == R.id.action_origin_web_page &&
                        WebActivity.start(StoryActivity.this, ZhuanLanApi.ZHUAN_LAN_URL + mPost.getUrl());
            }
        });
        mWebView.setWebViewClient(new ZhuanLanWebViewClient(this));
//        mWebView.loadDataWithBaseURL(null, CSS_STYLE + mPost.getContent(), MIME_TYPE, ENCODING_UTF_8, null);
        setStory();
    }

    private void setStory() {
        loadHtmlContent(mPost.getContent());
        setTitle(mPost.getTitle());
        headerImageView.setImageUrl(mPost.getImageUrl(), RequestManager.getImageLoader());
        titleView.setText(mPost.getTitle());
        authorTextView.setText(mPost.getAuthorName() + ", " + Utils.convertPublishTime(mPost.getPublishedTime()));

            String id = mPost.getAuthor().getAvatar().getId();
            String picUrl = Utils.getAuthorAvatarUrl(mPost.getAuthor().getAvatar().getTemplate(),
                    id, ZhuanLanApi.PIC_SIZE_XS);
            mAvatarView.setImageUrl(picUrl, RequestManager.getImageLoader());
        GlobalExecutor.MAIN_HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                injectCSS();
            }
        }, 200);
    }

    private void loadHtmlContent(String section) {
        String content = String.format(readFile("template.txt"), section);
        mWebView.loadDataWithBaseURL(null, content, CommonWebView.MIME_TYPE, CommonWebView.ENCODING_UTF_8, null);
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

    public static void startActivity(Context context, Post post) {
        Intent i = new Intent(context, StoryActivity.class);
        i.putExtra(KEY_POST, post);
        context.startActivity(i);
    }
}
