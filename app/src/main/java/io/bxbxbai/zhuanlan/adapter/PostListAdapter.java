package io.bxbxbai.zhuanlan.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import io.bxbxbai.common.view.BaseRecyclerAdapter;
import io.bxbxbai.common.view.BaseViewHolder;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.ui.StoryActivity;

/**
 *
 * @author bxbxbai
 */
public class PostListAdapter extends BaseRecyclerAdapter<Post> {

    private static final int VIEW_TYPE_TEXT = 0;
    private static final int VIEW_TYPE_IMAGE = 1;

    public PostListAdapter(Context context) {
        super(context);
        setOnItemClickListener(new OnItemClickListener<Post>() {
            @Override
            public void onItemClick(View view, int i, Post post) {
                StoryActivity.startActivity(getContext(), post);
            }
        });
    }

    @Override
    public BaseViewHolder<Post> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_IMAGE) {
            return new ImagePostViewHolder(parent);
        } else {
            return new TextPostViewHolder(parent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TextUtils.isEmpty( getItem(position).getImageUrl()) ? VIEW_TYPE_TEXT : VIEW_TYPE_IMAGE;
    }
}
