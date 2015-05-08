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
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.data.RequestManager;
import io.bxbxbai.zhuanlan.utils.StopWatch;
import io.bxbxbai.zhuanlan.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bxbxbai
 */
public class PostListAdapter extends BaseAdapter {

    private static final int VIEW_TYPE_TEXT = 0;
    private static final int VIEW_TYPE_IMAGE = 1;

    private Context mContext;
    private List<Post> mPosts;
    private LayoutInflater mInflater;

    public PostListAdapter(Context context, List<Post> data) {
        mContext = context;
        mPosts = new ArrayList<>();

        if (data != null) {
            mPosts.addAll(data);
        }

        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mPosts.size();
    }

    @Override
    public Object getItem(int position) {
        return mPosts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        Post post = (Post) getItem(position);
        return TextUtils.isEmpty(post.getTitleImage()) ? VIEW_TYPE_TEXT : VIEW_TYPE_IMAGE;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post post = mPosts.get(position);

        int type = getItemViewType(position);

        if (convertView == null) {
            if (type == VIEW_TYPE_IMAGE) {
                convertView = mInflater.inflate(R.layout.layout_post_image, parent, false);
                convertView.setTag(new ViewHolder(convertView));
            } else {
                convertView = mInflater.inflate(R.layout.layout_post_text, parent, false);
                convertView.setTag(new ViewHolder(convertView));
            }
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.mTitle.setText(post.getTitle());
        holder.mAuthor.setText(post.getAuthor().getName());
        holder.mCommentCount.setText(mContext.getString(R.string.comment_count, post.getCommentsCount()));
        holder.mDays.setText(Utils.convertPublishTime(post.getPublishedTime()));
        holder.mLike.setText(String.valueOf(post.getLikesCount()));

        if (type == VIEW_TYPE_IMAGE) {
            holder.mNetworkImageView.setImageUrl(post.getTitleImage(), RequestManager.getImageLoader());
        } else {
            holder.mSummary.setText(post.getSummary());
        }

        convertView.setTag(R.id.key_data, post);
        return convertView;
    }

    private static class ViewHolder {
        TextView mTitle;
        TextView mAuthor;
        TextView mCommentCount;
        TextView mDays;
        TextView mLike;

        NetworkImageView mNetworkImageView;
        TextView mSummary;

        public ViewHolder(View v) {
            mTitle = ButterKnife.findById(v, R.id.tv_title);
            mAuthor = ButterKnife.findById(v, R.id.tv_name);
            mCommentCount = ButterKnife.findById(v, R.id.tv_comment_count);
            mDays = ButterKnife.findById(v, R.id.tv_date);
            mLike = ButterKnife.findById(v, R.id.tv_like_count);
            mNetworkImageView = ButterKnife.findById(v, R.id.iv_pic);
            mSummary = ButterKnife.findById(v, R.id.tv_summary);
        }
    }

    public void addAll(List<Post> posts) {
        mPosts.addAll(posts);
    }

    public void replaceAll(List<Post> posts) {
        mPosts.clear();
        addAll(posts);
    }
}
