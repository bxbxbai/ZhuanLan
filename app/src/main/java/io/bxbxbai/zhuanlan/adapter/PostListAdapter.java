package io.bxbxbai.zhuanlan.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.android.volley.toolbox.NetworkImageView;
import io.bxbxbai.common.core.RequestManager;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.ui.PostListActivity;
import io.bxbxbai.zhuanlan.ui.StoryActivity;
import io.bxbxbai.zhuanlan.utils.Utils;
import io.bxbxbai.zhuanlan.widget.BaseRecyclerAdapter;
import io.bxbxbai.zhuanlan.widget.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bxbxbai
 */
public class PostListAdapter extends BaseRecyclerAdapter<Post> {

    private static final int VIEW_TYPE_TEXT = 0;
    private static final int VIEW_TYPE_IMAGE = 1;

    public PostListAdapter(Context context) {
        super(context);
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
    public void onBindViewHolder(BaseViewHolder<Post> holder, int position) {
        final Post post = getItem(position);
        holder.bind(post);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoryActivity.startActivity(getContext(), post);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        Post post = getItem(position);
        return TextUtils.isEmpty(post.getImageUrl()) ? VIEW_TYPE_TEXT : VIEW_TYPE_IMAGE;
    }

    private class ImagePostViewHolder extends BaseViewHolder<Post> {

        private TextView mTitle;
        private TextView mAuthor;
        private TextView mCommentCount;
        private TextView mDays;
        private TextView mLike;

        private  NetworkImageView mNetworkImageView;

        public ImagePostViewHolder(ViewGroup parent) {
            super(parent, R.layout.layout_post_image);
            mTitle = findView(R.id.tv_title);
            mAuthor = findView(R.id.tv_name);
            mCommentCount = findView(R.id.tv_comment_count);
            mDays = findView(R.id.tv_date);
            mLike = findView(R.id.tv_like_count);
            mNetworkImageView = findView(R.id.iv_pic);
        }

        @Override
        public void bind(Post post) {
            mTitle.setText(post.getTitle());
            mAuthor.setText(post.getAuthorName());
            mCommentCount.setText(getString(R.string.comment_count, post.getCommentsCount()));
            mDays.setText(Utils.convertPublishTime(post.getPublishedTime()));
            mLike.setText(String.valueOf(post.getLikesCount()));
            mNetworkImageView.setImageUrl(post.getImageUrl(), RequestManager.getImageLoader());
        }
    }

    private class TextPostViewHolder extends BaseViewHolder<Post> {

        private TextView mTitle;
        private TextView mAuthor;
        private TextView mCommentCount;
        private TextView mDays;
        private TextView mLike;
        private TextView mSummary;

        public TextPostViewHolder(ViewGroup parent) {
            super(parent, R.layout.layout_post_text);
            mTitle = findView(R.id.tv_title);
            mAuthor = findView(R.id.tv_name);
            mCommentCount = findView(R.id.tv_comment_count);
            mDays = findView(R.id.tv_date);
            mLike = findView(R.id.tv_like_count);
            mSummary = findView(R.id.tv_summary);
        }

        @Override
        public void bind(Post post) {
            mTitle.setText(post.getTitle());
            mAuthor.setText(post.getAuthorName());
            mCommentCount.setText(getString(R.string.comment_count, post.getCommentsCount()));
            mDays.setText(Utils.convertPublishTime(post.getPublishedTime()));
            mLike.setText(String.valueOf(post.getLikesCount()));
            mSummary.setText(Utils.removeHtmlCode(post.getSummary()));
        }
    }
}
