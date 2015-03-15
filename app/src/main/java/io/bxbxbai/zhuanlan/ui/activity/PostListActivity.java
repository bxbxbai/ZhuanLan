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
import io.bxbxbai.zhuanlan.utils.StopWatch;
import io.bxbxbai.zhuanlan.utils.ToastUtils;
import io.bxbxbai.zhuanlan.utils.ZhuanLanApi;

import java.util.List;

/**
 *
 * @author bxbxbai
 */
public class PostListActivity extends BaseActivity {

    private static final String KEY_ID = "id";

    private ListView listView;

    private String id = "limiao";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        initToolBar();
        materialMenu.setState(MaterialMenuDrawable.IconState.ARROW);
        listView = ButterKnife.findById(this, R.id.lv_post);

        final PostListAdapter adapter = new PostListAdapter(this, null);
        listView.setAdapter(adapter);

        final Response.Listener listener = new Response.Listener<List<Post>>() {
            @Override
            public void onResponse(List<Post> response) {
                if (response.size() == 0) {
                    ToastUtils.showShort("没有数据了");
                }
                adapter.addAll(response);
            }
        };

        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                GsonRequest<List<Post>> request = ZhuanLanApi.getPostListRequest(id,
                        String.valueOf((page - 1) * ZhuanLanApi.COUNT));
                request.setSuccessListener(listener);
                RequestManager.addRequest(request, id);
            }
        });

        GsonRequest<List<Post>> request = ZhuanLanApi.getPostListRequest(id, "0");
        request.setSuccessListener(listener);
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
