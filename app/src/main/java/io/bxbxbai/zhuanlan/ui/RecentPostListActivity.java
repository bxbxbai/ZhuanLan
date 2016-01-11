package io.bxbxbai.zhuanlan.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import io.bxbxbai.common.core.GsonRequest;
import io.bxbxbai.common.core.RequestManager;
import io.bxbxbai.common.utils.CommonExecutor;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.adapter.PostListAdapter;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.core.ZhuanLanApi;
import io.bxbxbai.zhuanlan.core.ZhuanLanRetryPolicy;
import io.bxbxbai.zhuanlan.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author bxbxbai
 */
public class RecentPostListActivity extends ListBaseActivity {

    private List<Post> postList;
    private PostListAdapter adapter;

    //按文章的发布时间排序
    final Comparator<Post> mPublishTimeComparator = new Comparator<Post>() {
        @Override
        public int compare(Post lhs, Post rhs) {
            return Utils.compareTime(lhs.getPublishedTime(), rhs.getPublishedTime());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.recent_news);

        adapter = new PostListAdapter(this);
        recyclerView.setAdapter(adapter);
        postList = new ArrayList<>();

        String[] ids = getResources().getStringArray(R.array.people_ids);

        for (int i = 0; ids != null && i < ids.length; i++) {
            String id = ids[i];
            GsonRequest request = buildRequest(id, 0);
            request.setRetryPolicy(new ZhuanLanRetryPolicy());
            RequestManager.addRequest(request, this);
        }
    }

    public GsonRequest buildRequest(String id, int page) {
        String url = String.format(ZhuanLanApi.API_POST_LIST, id);
        GsonRequest request = new GsonRequest<List<Post>>(url, ZhuanLanApi.buildDefaultErrorListener()) {
            @Override
            public void onResponse(List<Post> posts) {
                onSuccess(posts);
            }
        };
        request.addParam(ZhuanLanApi.KEY_LIMIT, String.valueOf(ZhuanLanApi.DEFAULT_COUNT))
                .addParam(ZhuanLanApi.KEY_OFFSET, String.valueOf((page) * ZhuanLanApi.DEFAULT_COUNT));
        return request;
    }

    private void onSuccess(List<Post> posts) {
        if (postList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            mLoadingView.setVisibility(View.GONE);
        }
        postList.addAll(filterNews(posts));
        Collections.sort(postList, mPublishTimeComparator);
        adapter.setItemList(postList);
    }

    public List<Post> filterNews(List<Post> origin) {
        List<Post> list = new ArrayList<>();

        for (Post post : origin) {
            if (Utils.withinDays(post.getPublishedTime(), 7)) {
                list.add(post);
            }
        }
        return list;
    }

    public static boolean start(final Context context) {
        final Intent intent = new Intent();
        intent.setClass(context, RecentPostListActivity.class);
        CommonExecutor.MAIN_HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                context.startActivity(intent);
            }
        }, 300);
        return true;
    }
}
