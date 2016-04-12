package io.bxbxbai.zhuanlan.adapter;

import android.databinding.DataBindingUtil;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Arrays;

import butterknife.Bind;
import io.bxbxbai.common.view.BaseViewHolder;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.databinding.ImagePostBinding;

public class ImagePostViewHolder extends BaseViewHolder<Post> {

    @Bind(R.id.iv_pic)
    protected ImageView imageView;

    private ImagePostBinding binding;

    public ImagePostViewHolder(ViewGroup parent) {
        super(parent, R.layout.image_post);
        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    public void bindData(Post post) {
        binding.setPost(post);
        Glide.with(getContext()).load(post.getImageUrl()).crossFade().into(imageView);
    }
}
