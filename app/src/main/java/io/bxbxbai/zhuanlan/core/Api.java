package io.bxbxbai.zhuanlan.core;

import java.util.List;

import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.bean.User;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by xuebin on 15/12/19.
 */
public interface Api {

    public static final String KEY_LIMIT = "limit";
    public static final String KEY_OFFSET = "offset";

    @GET("/api/columns/{id}/posts")
    Call<List<Post>> getPosts(@Path("id") String id, @Query(KEY_LIMIT) int limit, @Query(KEY_OFFSET) int offset);

    @GET("/api/columns/{id}")
    Call<User> getUser(@Path("id") String id);

}
