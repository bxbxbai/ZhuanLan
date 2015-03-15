package io.bxbxbai.zhuanlan.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import io.bxbxbai.zhuanlan.App;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.bean.User;
import io.bxbxbai.zhuanlan.utils.ZhuanLanApi;
import io.bxbxbai.zhuanlan.view.CircleImageView;

import java.util.List;

/**
 *
 * @author bxbxbai
 */
public class PeopleListAdapter extends SimpleBaseAdapter<User> {


    public PeopleListAdapter(Context context, List<User> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.layout_people_info;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        User user = getItem(position);

        final CircleImageView imageView = holder.findView(R.id.avatar);

        String id = user.getAuthor().getAvatar().getId();
        String picUrl = user.getAuthor().getAvatar().getTemplate();
        picUrl = picUrl.replace(ZhuanLanApi.TEMPLATE_ID, id);
        picUrl = picUrl.replace(ZhuanLanApi.TEMPLATE_SIZE, ZhuanLanApi.PIC_SIZE_XL);

        imageView.setImageUrl(picUrl, App.getInstance().getImageLoader());

        TextView name = holder.findView(R.id.tv_name);
        name.setText(user.getAuthor().getName());

        TextView follower = holder.findView(R.id.tv_follower);
        follower.setText(context.getString(R.string.follower, user.getFollowerCount()));

        TextView postCount = holder.findView(R.id.tv_post_count);
        postCount.setText(context.getString(R.string.post_count, user.getPostCount()));

        TextView description = holder.findView(R.id.tv_description);
        description.setText(user.getAuthor().getBio());
        return convertView;
    }
}
