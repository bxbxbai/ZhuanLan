package io.bxbxbai.zhuanlan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import butterknife.ButterKnife;
import io.bxbxbai.common.activity.BaseActivity;
import io.bxbxbai.common.view.CircleImageView;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.core.ZhuanLanWebViewClient;
import io.bxbxbai.zhuanlan.core.ZhuanLanApi;
import io.bxbxbai.zhuanlan.core.ZhuanLanWebChromeClient;


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
    private static final String FILE_NAME = "web.txt";

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

//        TextView textView = (TextView) findViewById(R.id.tv_html);

        String html = "<p>写了这些长时间公众号和知乎专栏，我终于清楚地感觉到了一件事：</p><br><p>" +
                "我的读者都是吃货。</p><br><p>无论是幽静闲雅的茶禅之旅，还是小清新的猫岛探秘，都比不" +
                "上你们在公众号里对我「吃吃吃」的催促。好吧，从今天起，我来写个「寿司百科全书」，争取" +
                "把寿司的食材种类一网打尽。几个事情先说明一下：</p><br><p>1. 这里不教你「如何有逼格" +
                "地吃寿司」，也不会诱使你「在气势上压倒寿司师傅」。吃寿司是一件简单的事情，何必复杂化" +
                "。</p><p>2. 不推荐店面，也不盲目崇拜某些「正宗、传统、神」之类的做法。你去点了寿司" +
                "，觉得好吃，吃着痛快，就足够了。<br></p><p>3. 关于寿司的所有评价，都是来自本人的主观" +
                "感受，不排除片面的可能性。<br></p><p><u>4. 睡觉前别看。</u><br></p><br><p>细分的" +
                "话，寿司的食材种类估计有200-300种，我大约吃过不下150种，基本上覆盖了市面上出现的大部分" +
                "食材。不排除在世界上某个角落，有人在制作黑松露云腿埃及蜜枣吐鲁番蜜瓜手卷寿司，所以请别用" +
                "这个清单来当作什么字典。</p><br><p>另一方面，如果您在国外前往寿司店，但是担心自己点不出" +
                "来东西的话，这个清单也许可以给您一些参考。价格、口味等等的评价，完全根据我个人的经验所言" +
                "，期待您的反馈。</p><br><br><p><b><u>第一部分，寿司食材常见分类</u></b></p><p>1. 赤" +
                "身鱼</p><p>赤身鱼顾名思义，鱼肉呈红色或褐色，普遍属于回游鱼。这种鱼类一般生活在浅海区域或" +
                "者海面附近，在海中的运动量相当大，所以体内的肌肉组织发达，血液含氧量丰富，使得鱼肉呈现红色" +
                "。</p><p>代表鱼类是金枪鱼， 鲣鱼，鰤鱼等。";

//        textView.setVisibility(View.VISIBLE);
//        textView.setTypeface(Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf"));
//        textView.setText(Html.fromHtml(html));

        initWebView();

        if (!TextUtils.isEmpty(mUrl)) {
            mWebView.loadUrl(mUrl);
        } else {
            loadData();

//            ExploreByTouchHelper helper = ExploreByTouchHelper
        }
//        mWebView.loadDataWithBaseURL(null, CSS_STYLE + mPost.getContent(),
//          MIME_TYPE, ENCODING_UTF_8, null);
        if (mPost != null) {
//            mWebView.loadUrl("http://zhuanlan.zhihu.com" + mPost.getUrl());
            mWebView.loadDataWithBaseURL(null, CSS_STYLE + mPost.getContent(),
                    MIME_TYPE, ENCODING_UTF_8, null);
            setTitle(mPost.getTitle());
//            mTitleView.setText(mPost.getTitle());
//            mNameView.setText(mPost.getAuthor().getName());
//
//            String id = mPost.getAuthor().getAvatar().getId();
//            String picUrl = Utils.getAuthorAvatarUrl(mPost.getAuthor().getAvatar().getTemplate(),
//                    id, ZhuanLanApi.PIC_SIZE_XL);
//
//            mAvatarView.setImageUrl(picUrl, App.getInstance().getImageLoader());
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
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebChromeClient(new ZhuanLanWebChromeClient(null, mLoadingView));
        mWebView.setWebViewClient(new ZhuanLanWebViewClient(this));
//        mWebView.addJavascriptInterface();
    }

    private void loadData() {


//        AssetManager manager = getAssets();
//        try {
//            Scanner scanner = new Scanner(manager.open("web.txt"));
//            StringBuilder builder = new StringBuilder();
//            while(scanner.hasNext()) {
//                builder.append(scanner.next());
//            }
//            mWebView.loadDataWithBaseURL(null, builder.toString(), MIME_TYPE, ENCODING_UTF_8, null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
