package io.bxbxbai.zhuanlan.core;

import android.content.Context;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DiskBasedCache;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import io.bxbxbai.common.T;
import io.bxbxbai.common.core.GsonRequest;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.bean.User;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author bxbxbai
 */
public final class ZhuanLanApi {

    public static final int DEFAULT_COUNT = 10;

    public static final String KEY_POSTS = "/posts";
    public static final String KEY_LIMIT = "limit";
    public static final String KEY_OFFSET = "offset";
    public static final String KEY_RATING = "rating";

    public static final String ZHUAN_LAN_URL = "http://zhuanlan.zhihu.com";
    public static final String API_BASE = ZHUAN_LAN_URL + "/api/columns/%s";

    /**
     * slug, post id
     */
    public static final String API_POST_DETAIL = ZHUAN_LAN_URL + "/api/columns/%s/posts/%s";


    public static final class Url {
        public static final String ZHIHU_DAILY_BEFORE = "http://news.at.zhihu.com/api/3/news/before/";
        public static final String ZHIHU_DAILY_OFFLINE_NEWS = "http://news-at.zhihu.com/api/3/news/";
        public static final String ZHIHU_DAILY_PURIFY_HEROKU_BEFORE = "http://zhihu-daily-purify.herokuapp.com/raw/";
        public static final String ZHIHU_DAILY_PURIFY_SAE_BEFORE = "http://zhihudailypurify.sinaapp.com/raw/";
        public static final String SEARCH = "http://zhihudailypurify.sinaapp.com/search/";
    }

    /**
     * 知乎日报启动画面api（手机分辨率的长和宽）
     */
    public static final String API_START_IMAGE = "http://news-at.zhihu.com/api/4/start-image/%d*%d";
    public static final String API_RATING = API_BASE + KEY_POSTS + "{post_id}" + KEY_RATING;
    public static final String API_POST_LIST = API_BASE + KEY_POSTS;

    public static final String PIC_SIZE_XL = "xl";
    public static final String PIC_SIZE_XS = "xs";


    public static final String TEMPLATE_ID = "{id}";
    public static final String TEMPLATE_SIZE = "{size}";

    public static Response.ErrorListener buildDefaultErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                T.showToast(R.string.network_error);
            }
        };
    }


    private static ZhuanLanApi instance = new ZhuanLanApi();

    private Retrofit retrofit;

    private ZhuanLanApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ZHUAN_LAN_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(createHttpClient())
                .build();
    }

    public static <T> T api(Class<T> clazz) {
        return instance.retrofit.create(clazz);
    }

    public static Api getZhuanlanApi() {
        return api(Api.class);
    }

    private static OkHttpClient createHttpClient(Context context) {
        OkHttpClient client = new OkHttpClient();
        client.setCache(new Cache(context.getCacheDir(), 10 * 1024*1024));
        return client;
    }
}
