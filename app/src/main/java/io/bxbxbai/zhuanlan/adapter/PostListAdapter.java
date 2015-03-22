package io.bxbxbai.zhuanlan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.iconics.typeface.ITypeface;
import com.squareup.picasso.Picasso;
import io.bxbxbai.zhuanlan.App;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.bean.Post;
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
    public View getItemView(int position, View convertView, ViewHolder holder) {
        Post post = getItem(position);

        TextView title = holder.findView(R.id.tv_title);
        title.setText(post.getTitle());

        TextView author = holder.findView(R.id.tv_name);
        author.setText(post.getAuthor().getName());

        TextView commentCount = holder.findView(R.id.tv_comment_count);
        commentCount.setText(context.getString(R.string.comment_count, post.getCommentsCount()));

        TextView days = holder.findView(R.id.tv_date);
        days.setText(Utils.convertPublishTime(post.getPublishedTime()));

        TextView like = holder.findView(R.id.tv_like_count);
        like.setText(context.getString(R.string.like_count, post.getLikesCount()));


        NetworkImageView pic = holder.findView(R.id.iv_pic);
        pic.setImageUrl(post.getTitleImage(), App.getInstance().getImageLoader());
//        Picasso.with(context).load(post.getTitleImage()).placeholder(R.drawable.bxbxbai).into(imageView);

        convertView.setTag(R.id.key_data, post);
        return convertView;
    }
}
