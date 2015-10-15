package io.bxbxbai.zhuanlan.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import io.bxbxbai.common.StopWatch;
import io.bxbxbai.common.T;
import io.bxbxbai.common.core.GsonRequest;
import io.bxbxbai.common.core.RequestManager;
import io.bxbxbai.common.utils.EndlessScrollListener;
import io.bxbxbai.common.utils.GlobalExecutor;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.adapter.PostListAdapter;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.core.ZhuanLanApi;
import io.bxbxbai.zhuanlan.core.ZhuanLanRetryPolicy;

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

//        recyclerView.addOnScrollListener(new EndlessScrollListener() {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//                StopWatch.log("page: " + page);
//                RequestManager.addRequest(buildRequest(page - 1), this);
//            }
//        });

        GsonRequest request = buildRequest(0);
        request.setRetryPolicy(new ZhuanLanRetryPolicy());
        RequestManager.addRequest(request, this.toString());
    }

    private void addData(List<Post> response) {
        if (response.size() == 0) {
            T.showToast("没有数据了");
        }
        recyclerView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
        adapter.addItemList(response);
    }

    public GsonRequest buildRequest(int page) {
        String url = String.format(ZhuanLanApi.API_POST_LIST, id);
        GsonRequest request = new GsonRequest<List<Post>>(url, ZhuanLanApi.buildDefaultErrorListener()) {
            @Override
            public void onResponse(List<Post> posts) {
                addData(posts);
            }
        };
        request.addParam(ZhuanLanApi.KEY_LIMIT, String.valueOf(ZhuanLanApi.DEFAULT_COUNT))
                .addParam(ZhuanLanApi.KEY_OFFSET, String.valueOf((page) * ZhuanLanApi.DEFAULT_COUNT));
        return request;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.getRequestQueue().cancelAll(this);
    }

    public static boolean start(final Context context, String slug, String name) {
        final Intent intent = new Intent();
        intent.setClass(context, PostListActivity.class);
        intent.putExtra(KEY_ID, slug);
        intent.putExtra(KEY_NAME, name);
        GlobalExecutor.MAIN_HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                context.startActivity(intent);
            }
        }, 420);
        return true;
    }
}
