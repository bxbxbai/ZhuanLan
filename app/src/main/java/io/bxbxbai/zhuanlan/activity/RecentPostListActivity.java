package io.bxbxbai.zhuanlan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import com.android.volley.Response;
import io.bxbxbai.common.core.RequestManager;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.adapter.PostListAdapter;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.core.ZhuanLanApi;
import io.bxbxbai.zhuanlan.core.ZhuanLanHandler;
import io.bxbxbai.zhuanlan.core.ZhuanLanRetryPolicy;
import io.bxbxbai.zhuanlan.core.GsonRequest;
import io.bxbxbai.zhuanlan.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author bxbxbai
 */
public class RecentPostListActivity extends ListBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.recent_news);

        final PostListAdapter adapter = new PostListAdapter(this, new ArrayList<Post>());
        listView.setAdapter(adapter);

        //按文章的发布时间排序
        final Comparator<Post> mPublishTimeComparator = new Comparator<Post>() {
            @Override
            public int compare(Post lhs, Post rhs) {
                return Utils.compareTime(lhs.getPublishedTime(), rhs.getPublishedTime());
            }
        };

        final List<Post> postList = new ArrayList<>();


        final Response.Listener listener = new Response.Listener<List<Post>>() {
            @Override
            public void onResponse(List<Post> response) {
                if (postList.size() > 0) {
                    listView.setVisibility(View.VISIBLE);
                    mLoadingView.setVisibility(View.GONE);
                }
                postList.addAll(filterNews(response));
                Collections.sort(postList, mPublishTimeComparator);
                adapter.replaceAll(postList);
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
        };

        String[] ids = getResources().getStringArray(R.array.people_ids);

        for (int i = 0; ids != null && i < ids.length; i++) {
            String id = ids[i];
            GsonRequest request = ZhuanLanApi.getPostListRequest(id, "0");
            request.setSuccessListener(listener);
            request.setRetryPolicy(new ZhuanLanRetryPolicy());
            RequestManager.addRequest(request, this);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = (Post) view.getTag(R.id.key_data);
                NewsDetailActivity.startActivity(RecentPostListActivity.this, post);
            }
        });
    }

    public static boolean start(final Context context) {
        final Intent intent = new Intent();
        intent.setClass(context, RecentPostListActivity.class);
        ZhuanLanHandler.get().postDelay(new Runnable() {
            @Override
            public void run() {
                context.startActivity(intent);
            }
        }, 300);
        return true;
    }
}
