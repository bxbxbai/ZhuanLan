package io.bxbxbai.zhuanlan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import io.bxbxbai.common.view.BaseRecyclerAdapter;
import io.bxbxbai.common.view.BaseViewHolder;
import io.bxbxbai.zhuanlan.bean.UserEntity;
import io.bxbxbai.zhuanlan.ui.PostListActivity;

/**
 * @author bxbxbai
 */
public class PeopleListAdapter extends BaseRecyclerAdapter<UserEntity> {

    public PeopleListAdapter(Context context) {
        super(context);
        setOnItemClickListener(new OnItemClickListener<UserEntity>() {
            @Override
            public void onItemClick(View view, int i, UserEntity userEntity) {
                PostListActivity.start(getContext(), userEntity);
            }
        });
    }

    @Override
    public BaseViewHolder<UserEntity> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PeopleViewHolder(parent);
    }
}
