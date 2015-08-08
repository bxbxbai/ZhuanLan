package io.bxbxbai.zhuanlan.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import io.bxbxbai.common.SimpleBaseAdapter;
import io.bxbxbai.zhuanlan.R;

import java.util.List;

/**
 *
 * @author bxbxbai
 */
public class MenuAdapter extends SimpleBaseAdapter<DrawerMenuContent.DrawerItem> {


    public MenuAdapter(Context context, List<DrawerMenuContent.DrawerItem> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.layout_menu_item;
    }

    @Override
    public void bindData(int position, View convertView, ViewHolder holder) {

        DrawerMenuContent.DrawerItem item = getItem(position);

        ImageView menuIcon = holder.findView(R.id.iv_menu_icon);
        menuIcon.setImageResource(item.icon);

        TextView menuText = holder.findView(R.id.tv_menu_item);
        menuText.setText(item.title);

        convertView.setTag(R.id.key_data, item);
    }
}
