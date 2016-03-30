package io.bxbxbai.zhuanlan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import io.bxbxbai.common.view.CircleImageView;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.bean.UserEntity;
import io.bxbxbai.zhuanlan.core.ZhuanLanApi;
import io.bxbxbai.zhuanlan.ui.PostListActivity;
import io.bxbxbai.zhuanlan.utils.Utils;
import io.bxbxbai.zhuanlan.widget.BaseRecyclerAdapter;
import io.bxbxbai.zhuanlan.widget.BaseViewHolder;

/**
 * @author bxbxbai
 */
public class PeopleListAdapter extends BaseRecyclerAdapter<UserEntity> {

    public PeopleListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder<UserEntity> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PeopleViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<UserEntity> holder, final int position) {
        final UserEntity entity = getItem(position);
        holder.bind(entity);
    }

    private class PeopleViewHolder extends BaseViewHolder<UserEntity> {

        public PeopleViewHolder(ViewGroup parent) {
            super(parent, R.layout.layout_people_info);
        }

        @Override
        public void bind(final UserEntity user) {
            final Context context = itemView.getContext();
            final CircleImageView imageView = findView(R.id.avatar);

            String picUrl = Utils.getAuthorAvatarUrl(user.getAvatarTemplate(),
                    user.getAvatarId(), ZhuanLanApi.PIC_SIZE_XL);
            Glide.with(getContext()).load(picUrl).crossFade().into(imageView);

            TextView name = findView(R.id.tv_name);
            name.setText(user.getZhuanlanName());

            TextView follower = findView(R.id.tv_follower);
            follower.setText(context.getString(R.string.follower, user.getFollowerCount()));

            TextView postCount = findView(R.id.tv_post_count);
            postCount.setText(context.getString(R.string.post_count, user.getPostCount()));

            TextView description = findView(R.id.tv_description);
            description.setText(user.getDescription());

            itemView.setTag(R.id.key_slug, user.getSlug());
            itemView.setTag(R.id.key_name, user.getZhuanlanName());

            findView(R.id.ripple_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostListActivity.start(context, user.getSlug(), user.getZhuanlanName());
                }
            });
        }
    }
}
