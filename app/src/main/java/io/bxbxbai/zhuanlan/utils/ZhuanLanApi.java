package io.bxbxbai.zhuanlan.utils;

import android.support.v4.util.ArrayMap;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.bean.User;
import io.bxbxbai.zhuanlan.data.GsonRequest;

import java.util.List;
import java.util.Map;

/**
 *
 * @author bxbxbai
 */
public final class ZhuanLanApi {

    public static final int COUNT = 10;

    private static final String POSTS = "/posts";

    private static final String LIMIT = "limit";

    private static final String OFFSET = "offset";

    private static final String API_BASE = "http://zhuanlan.zhihu.com/api/columns/%s";

    private static final String API_POST_LIST = API_BASE + POSTS;



    public static GsonRequest<List<Post>> getPostListRequest(String id, final String offset) {
        String url = new StringBuilder(String.format(API_POST_LIST, id))
                .append("?")
                .append(LIMIT + "=" + COUNT)
                .append("&")
                .append(OFFSET)
                .append("=")
                .append(offset).toString();

        return new GsonRequest<List<Post>>(url, buildDefaultErrorListener());
    }

    public static GsonRequest<User> getUserInfoRequest(String id) {
        return new GsonRequest<User>(String.format(API_BASE, id), User.class,
                null, buildDefaultErrorListener());
    }


    private static Response.ErrorListener buildDefaultErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showLong(error.getMessage());
            }
        };
    }

}
