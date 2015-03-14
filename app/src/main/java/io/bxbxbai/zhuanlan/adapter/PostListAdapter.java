package io.bxbxbai.zhuanlan.adapter;

import android.content.Context;
import android.view.View;
import io.bxbxbai.zhuanlan.bean.Post;

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
        return 0;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        return null;
    }
}
