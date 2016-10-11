package io.bxbxbai.zhuanlan.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import io.bxbxbai.common.Tips;
import io.bxbxbai.common.utils.CommonExecutor;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.adapter.PostListAdapter;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.bean.UserEntity;
import io.bxbxbai.zhuanlan.core.Api;
import io.bxbxbai.zhuanlan.core.SimpleCallback;
import io.bxbxbai.zhuanlan.core.ZhuanLanApi;
import io.bxbxbai.zhuanlan.utils.RecyclerEndlessScrollListener;
import io.bxbxbai.zhuanlan.utils.Utils;

import java.util.List;

/**
 * @author bxbxbai
 */
public class PostListActivity extends BaseActivity {

    private static final String KEY_USER = "_user";

    @Bind(R.id.recycler_view)
    protected RecyclerView recyclerView;
//    @Bind(R.id.v_loading)
//    protected CircularLoadingView mLoadingView;
    @Bind(R.id.app_bar)
    protected AppBarLayout appBarLayout;
    @Bind(R.id.head_layout)
    protected LinearLayout headerLayout;
    @Bind(R.id.collapsing_toolbar)
    protected CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.tv_name)
    protected TextView nameView;
    @Bind(R.id.tv_bio)
    protected TextView bioView;
    @Bind(R.id.tv_description)
    protected TextView descriptionView;
    @Bind(R.id.iv_avatar)
    protected ImageView avatarView;


    private PostListAdapter adapter;
    private UserEntity userEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        ButterKnife.bind(this);

//        collapsingToolbarLayout.setContentScrim(new BitmapDrawable());
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -headerLayout.getHeight() / 2) {
                    collapsingToolbarLayout.setTitle(userEntity.getZhuanlanName());
                } else {
                    collapsingToolbarLayout.setTitle(" ");
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        adapter = new PostListAdapter(this);
        recyclerView.setAdapter(adapter);

        userEntity = getIntent().getParcelableExtra(KEY_USER);

        String name = userEntity.getZhuanlanName();
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

        nameView.setText(userEntity.getZhuanlanName());
        descriptionView.setText(userEntity.getDescription());
        String picUrl = Utils.getAuthorAvatarUrl(userEntity.getAvatarTemplate(),
                userEntity.getAvatarId(), ZhuanLanApi.PIC_SIZE_XL);
        Glide.with(this).load(picUrl).crossFade().into(avatarView);
    }

    private void addPosts(List<Post> posts) {
        if (posts.size() == 0) {
            Tips.showToast("没有数据了");
        }
        recyclerView.setVisibility(View.VISIBLE);
        adapter.addAllItems(posts);
    }

    public void requestPostList(int page) {
        int offset = page * ZhuanLanApi.DEFAULT_COUNT;
        Api api = ZhuanLanApi.getZhuanlanApi();
        api.getPosts(userEntity.getSlug(), ZhuanLanApi.DEFAULT_COUNT, offset).enqueue(new SimpleCallback<List<Post>>() {
            @Override
            public void onResponse(List<Post> posts, int code, String msg) {
                addPosts(posts);
            }
        });
    }

    public static boolean start(final Context context, UserEntity userEntity) {
        final Intent intent = new Intent();
        intent.setClass(context, PostListActivity.class);
        intent.putExtra(KEY_USER, userEntity);
        CommonExecutor.MAIN_HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                context.startActivity(intent);
            }
        }, 420);
        return true;
    }
}
