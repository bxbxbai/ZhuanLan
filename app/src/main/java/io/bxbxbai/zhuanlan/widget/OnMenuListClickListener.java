package io.bxbxbai.zhuanlan.widget;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.activity.AllPeopleActivity;
import io.bxbxbai.zhuanlan.activity.RecentPostListActivity;
import io.bxbxbai.zhuanlan.utils.T;

/**
 *
 * 点击事件
 * @author bxbxbai
 */
public class OnMenuListClickListener implements AdapterView.OnItemClickListener {

    private Activity mActivity;
    private DrawerLayout mDrawerLayout;

    public OnMenuListClickListener(Activity activity, DrawerLayout drawerLayout) {
        mActivity = activity;
        mDrawerLayout = drawerLayout;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DrawerMenuContent.DrawerItem item = (DrawerMenuContent.DrawerItem) view.getTag(R.id.key_data);

        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawers();
        }

        switch (item.id) {
            case R.id.menu_search :
                T.showShort("Coming soon...");
                break;

            case R.id.menu_all_people:
                AllPeopleActivity.start(mActivity);
                break;

            case R.id.menu_recent_news :
                RecentPostListActivity.start(mActivity);
                break;

            default:break;
        }
    }
}
