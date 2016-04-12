package io.bxbxbai.zhuanlan.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import io.bxbxbai.common.Tips;
import io.bxbxbai.common.utils.CommonExecutor;
import io.bxbxbai.zhuanlan.adapter.PostListAdapter;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.core.Api;
import io.bxbxbai.zhuanlan.core.SimpleCallback;
import io.bxbxbai.zhuanlan.core.ZhuanLanApi;
import io.bxbxbai.zhuanlan.utils.RecyclerEndlessScrollListener;

import java.util.List;

/**
 * @author bxbxbai
 */
public class PostListActivity extends ListBaseActivity {

    private String id = "bxbxbai";
    private PostListAdapter adapter;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        adapter = new PostListAdapter(this);
        recyclerView.setAdapter(adapter);

        id = getIntent().getStringExtra(KEY_ID);
        String name = getIntent().getStringExtra(KEY_NAME);
        if (!TextUtils.isEmpty(name)) {
            setTitle(name);
        }

        recyclerView.addOnScrollListener(new RecyclerEndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int var2) {
                requestPostList(page - 1);
            }
        });
        requestPostList(0);
    }

    private void addPosts(List<Post> posts) {
        if (posts.size() == 0) {
            Tips.showToast("没有数据了");
        }
        recyclerView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
        adapter.addAllItems(posts);
    }

    public void requestPostList(int page) {
        int offset = page * ZhuanLanApi.DEFAULT_COUNT;
        Api api = ZhuanLanApi.getZhuanlanApi();
        api.getPosts(id, ZhuanLanApi.DEFAULT_COUNT, offset).enqueue(new SimpleCallback<List<Post>>() {
            @Override
            public void onResponse(List<Post> posts, int code, String msg) {
                addPosts(posts);
            }
        });
    }

    public static boolean start(final Context context, String slug, String name) {
        final Intent intent = new Intent();
        intent.setClass(context, PostListActivity.class);
        intent.putExtra(KEY_ID, slug);
        intent.putExtra(KEY_NAME, name);
        CommonExecutor.MAIN_HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                context.startActivity(intent);
            }
        }, 420);
        return true;
    }
}
