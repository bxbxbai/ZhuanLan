package io.bxbxbai.zhuanlan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.ButterKnife;
import com.android.volley.Response;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.adapter.PostListAdapter;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.data.GsonRequest;
import io.bxbxbai.zhuanlan.data.RequestManager;
import io.bxbxbai.zhuanlan.utils.*;
import io.bxbxbai.zhuanlan.view.circularprogress.CircularLoadingView;

import java.util.List;

/**
 * @author bxbxbai
 */
public class PostListActivity extends ListBaseActivity {

    private String id = "bxbxbai";

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        final PostListAdapter adapter = new PostListAdapter(this, null);
        listView.setAdapter(adapter);

        final Response.Listener listener = new Response.Listener<List<Post>>() {
            @Override
            public void onResponse(List<Post> response) {
                if (response.size() == 0) {
                    ToastUtils.showShort("没有数据了");
                }
                listView.setVisibility(View.VISIBLE);
                mLoadingView.setVisibility(View.GONE);
                adapter.addAll(response);
            }
        };

        id = getIntent().getStringExtra(KEY_ID);
        String name = getIntent().getStringExtra(KEY_NAME);

        if (!TextUtils.isEmpty(name)) {
            getSupportActionBar().setTitle(name);
        }


        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                GsonRequest request = ZhuanLanApi.getPostListRequest(id,
                        String.valueOf((page - 1) * ZhuanLanApi.DEFAULT_COUNT));
                request.setSuccessListener(listener);
                RequestManager.addRequest(request, this);
            }
        });

        GsonRequest request = ZhuanLanApi.getPostListRequest(id, "0");
        request.setSuccessListener(listener);
        request.setRetryPolicy(new ZhuanLanRetryPolicy());
        RequestManager.addRequest(request, this.toString());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = (Post) view.getTag(R.id.key_data);
                NewsDetailActivity.startActivity(PostListActivity.this, post);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.getRequestQueue().cancelAll(this);
    }

    public static boolean start(Context context, String id) {
        return start(context, id, null);
    }

    public static boolean start(final Context context, String id, String name) {
        final Intent intent = new Intent();
        intent.setClass(context, PostListActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_NAME, name);
        ZhuanLanHandler.get().postDelay(new Runnable() {
            @Override
            public void run() {
                context.startActivity(intent);
            }
        }, 420);
        return true;
    }
}
