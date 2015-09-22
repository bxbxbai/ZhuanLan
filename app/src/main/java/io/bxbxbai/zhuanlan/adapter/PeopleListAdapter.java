package io.bxbxbai.zhuanlan.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import io.bxbxbai.common.SimpleBaseAdapter;
import io.bxbxbai.common.core.RequestManager;
import io.bxbxbai.common.view.CircleImageView;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.activity.PostListActivity;
import io.bxbxbai.zhuanlan.bean.User;
import io.bxbxbai.zhuanlan.bean.UserEntity;
import io.bxbxbai.zhuanlan.core.ZhuanLanApi;
import io.bxbxbai.zhuanlan.utils.Utils;

import java.util.List;

/**
 * @author bxbxbai
 */
public class PeopleListAdapter extends SimpleBaseAdapter<UserEntity> {

    public PeopleListAdapter(Context context, List<UserEntity> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.layout_people_info;
    }

    @Override
    public void bindData(int position, View convertView, ViewHolder holder) {
        final UserEntity user = getItem(position);

        final CircleImageView imageView = holder.findView(R.id.avatar);

        String picUrl = Utils.getAuthorAvatarUrl(user.getAvatarTemplate(),
                user.getAvatarId(), ZhuanLanApi.PIC_SIZE_XL);
        imageView.setImageUrl(picUrl, RequestManager.getImageLoader());
//        Picasso.with(mContext).load(picUrl).placeholder(R.drawable.bxbxbai).into(imageView);

        TextView name = holder.findView(R.id.tv_name);
        name.setText(user.getZhuanlanName());

        TextView follower = holder.findView(R.id.tv_follower);
        follower.setText(mContext.getString(R.string.follower, user.getFollowerCount()));

        TextView postCount = holder.findView(R.id.tv_post_count);
        postCount.setText(mContext.getString(R.string.post_count, user.getPostCount()));

        TextView description = holder.findView(R.id.tv_description);
        description.setText(user.getDescription());

        convertView.setTag(R.id.key_slug, user.getSlug());
        convertView.setTag(R.id.key_name, user.getZhuanlanName());

        View v = holder.findView(R.id.ripple_layout);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostListActivity.start(mContext, user.getSlug(), user.getZhuanlanName());
            }
        });
    }
}
