package io.bxbxbai.zhuanlan.adapter;

import android.databinding.DataBindingUtil;
import android.view.ViewGroup;

import io.bxbxbai.common.view.BaseViewHolder;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.databinding.TextPostBinding;

public class TextPostViewHolder extends BaseViewHolder<Post> {

    private TextPostBinding binding;

    public TextPostViewHolder(ViewGroup parent) {
        super(parent, R.layout.text_post);
        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    public void bindData(Post post) {
        binding.setPost(post);
    }
}
