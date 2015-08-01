package io.bxbxbai.zhuanlan.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.activity.AllPeopleActivity;
import io.bxbxbai.zhuanlan.activity.PostListActivity;
import io.bxbxbai.zhuanlan.activity.SearchActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baia on 15/3/28.
 *
 * @author bxbxbai
 */
public class DrawerMenuContent {

    public static final int DRAWER_MENU_COUNT = 3;

    private static final String FIELD_TITLE = "title";
    private static final String FIELD_ICON = "icon";

    private List<DrawerItem> items;
    private Class[] activities;

    public DrawerMenuContent(Context context) {
        activities = new Class[DRAWER_MENU_COUNT];
        items = new ArrayList<>(DRAWER_MENU_COUNT);

        activities[0] = SearchActivity.class;
        items.add(new DrawerItem(R.id.menu_search, context.getString(R.string.searching),
                R.drawable.ic_search_black_18dp));

        activities[1] = AllPeopleActivity.class;
        items.add(new DrawerItem(R.id.menu_all_people, context.getString(R.string.all_people),
                R.drawable.ic_supervisor_account_black_18dp));

        activities[2] = PostListActivity.class;
        items.add(new DrawerItem(R.id.menu_recent_news, context.getString(R.string.recent_news),
                R.drawable.ic_view_list_black_18dp));
    }

    public List<DrawerItem> getItems() {
        return items;
    }

    public Class getActivity(int pos) {
        if (0 <= pos && pos < activities.length) {
            return activities[pos];
        }
        return null;
    }


    public int getPosition(Class clazz) {
        for (int i = 0; i < activities.length; i++) {
            if (activities[i].equals(clazz)) {
                return i;
            }
        }
        return -1;
    }

    public static class DrawerItem {
        public int id;
        public String title;
        public int icon;

        private DrawerItem(int id, String title, @DrawableRes int icon) {
            this.id = id;
            this.title = title;
            this.icon = icon;
        }
    }
}
