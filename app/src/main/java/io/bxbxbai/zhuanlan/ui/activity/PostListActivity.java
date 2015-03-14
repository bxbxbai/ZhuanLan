package io.bxbxbai.zhuanlan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import butterknife.ButterKnife;
import com.android.volley.Response;
import com.balysv.materialmenu.MaterialMenuDrawable;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.adapter.PostListAdapter;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.data.GsonRequest;
import io.bxbxbai.zhuanlan.data.RequestManager;
import io.bxbxbai.zhuanlan.utils.EndlessScrollListener;
import io.bxbxbai.zhuanlan.utils.ZhuanLanApi;

import java.util.List;

/**
 *
 * @author bxbxbai
 */
public class PostListActivity extends BaseActivity {

    private static final String KEY_ID = "id";

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        initToolBar();
        materialMenu.setState(MaterialMenuDrawable.IconState.ARROW);
        listView = ButterKnife.findById(this, R.id.lv_post);

        final PostListAdapter adapter = new PostListAdapter(this, null);
        listView.setAdapter(adapter);

        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

            }
        });

        String id = "limiao";

        final GsonRequest<List<Post>> request = ZhuanLanApi.getPostListRequest(id);
        request.setSuccessListener(new Response.Listener<List<Post>>() {
            @Override
            public void onResponse(List<Post> response) {
                adapter.addAll(response);
            }
        });
        RequestManager.addRequest(request, id);

    }

    public static boolean start(Context context, String id) {
        Intent intent = new Intent();
        intent.setClass(context, PostListActivity.class);
        intent.putExtra(KEY_ID, id);
        context.startActivity(intent);
        return true;
    }
}
