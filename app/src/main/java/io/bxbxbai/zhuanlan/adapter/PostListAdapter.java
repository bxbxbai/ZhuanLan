package io.bxbxbai.zhuanlan.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import io.bxbxbai.zhuanlan.App;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.utils.StopWatch;
import io.bxbxbai.zhuanlan.utils.Utils;

import java.util.List;

/**
 *
 * @author bxbxbai
 */
public class PostListAdapter extends SimpleBaseAdapter<Post> {


    public PostListAdapter(Context context, List<Post> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.layout_post;
    }

    @Override
    public int getItemViewType(int position) {
        Post post = getItem(position);
        return TextUtils.isEmpty(post.getTitleImage()) ? 0 : 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        Post post = getItem(position);

        TextView title = holder.findView(R.id.tv_title);
        title.setText(post.getTitle());

        TextView author = holder.findView(R.id.tv_name);
        author.setText(post.getAuthor().getName());

        TextView commentCount = holder.findView(R.id.tv_comment_count);
        commentCount.setText(mContext.getString(R.string.comment_count, post.getCommentsCount()));

        TextView days = holder.findView(R.id.tv_date);
        days.setText(Utils.convertPublishTime(post.getPublishedTime()));

        TextView like = holder.findView(R.id.tv_like_count);
        like.setText(String.valueOf(post.getLikesCount()));


        NetworkImageView pic = holder.findView(R.id.iv_pic);
        pic.setImageUrl(post.getTitleImage(), App.getInstance().getImageLoader());
        StopWatch.log("image url: " + post.getTitleImage());
//        Picasso.with(mContext).load(post.getTitleImage()).placeholder(R.drawable.bxbxbai).into(imageView);

        convertView.setTag(R.id.key_data, post);
        return convertView;
    }
}
