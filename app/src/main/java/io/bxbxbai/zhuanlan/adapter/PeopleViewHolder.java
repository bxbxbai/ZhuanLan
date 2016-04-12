package io.bxbxbai.zhuanlan.adapter;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import com.bumptech.glide.Glide;
import io.bxbxbai.common.view.BaseViewHolder;
import io.bxbxbai.common.view.CircleImageView;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.bean.UserEntity;
import io.bxbxbai.zhuanlan.core.ZhuanLanApi;
import io.bxbxbai.zhuanlan.databinding.PeopleInfoBinding;
import io.bxbxbai.zhuanlan.ui.PostListActivity;
import io.bxbxbai.zhuanlan.utils.Utils;

public class PeopleViewHolder extends BaseViewHolder<UserEntity> {

    private PeopleInfoBinding peopleInfoBinding;

    @Bind(R.id.avatar)
    protected CircleImageView imageView;

    public PeopleViewHolder(ViewGroup parent) {
        super(parent, R.layout.people_info);
        peopleInfoBinding = DataBindingUtil.bind(itemView);
    }

    @Override
    public void bindData(final UserEntity userEntity) {
        String picUrl = Utils.getAuthorAvatarUrl(userEntity.getAvatarTemplate(),
                userEntity.getAvatarId(), ZhuanLanApi.PIC_SIZE_XL);
        Glide.with(getContext()).load(picUrl).crossFade().into(imageView);

        peopleInfoBinding.setUser(userEntity);
        itemView.setTag(R.id.key_slug, userEntity.getSlug());
        itemView.setTag(R.id.key_name, userEntity.getZhuanlanName());
    }
}
