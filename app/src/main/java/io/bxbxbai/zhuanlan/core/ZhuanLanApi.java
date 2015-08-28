package io.bxbxbai.zhuanlan.core;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import io.bxbxbai.common.T;
import io.bxbxbai.common.core.GsonRequest;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.bean.User;

import java.util.List;

/**
 *
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
}
