package io.bxbxbai.zhuanlan.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.Window;
import android.widget.ListView;
import butterknife.ButterKnife;
import io.bxbxbai.common.utils.CommonExecutor;
import io.bxbxbai.common.utils.PrefUtils;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.widget.DrawerMenuContent;
import io.bxbxbai.zhuanlan.widget.MenuAdapter;
import io.bxbxbai.zhuanlan.widget.OnMenuListClickListener;

import java.lang.reflect.Method;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private boolean isDrawerOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ChoreographerHelper.getInstance(this).start();
        mDrawerLayout = ButterKnife.findById(this, R.id.drawerLayout);
        initToolbarAndDrawer();

        DrawerMenuContent content = new DrawerMenuContent(this);
        ListView listView = ButterKnife.findById(this, R.id.drawer_list);
        listView.setAdapter(new MenuAdapter(this, content.getItems()));
        listView.setOnItemClickListener(new OnMenuListClickListener(this, mDrawerLayout));

        getSupportFragmentManager().beginTransaction().add(R.id.container,
                PeopleListFragment.newInstance()).commit();

        //第一次启动，会打开抽屉菜单
        CommonExecutor.MAIN_HANDLER.post(new Runnable() {
            @Override
            public void run() {
                if ((boolean) PrefUtils.getValue(MainActivity.this, PrefUtils.KEY_FIRST_ENTER, true)) {
                    CommonExecutor.MAIN_HANDLER.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            openDrawer();
                        }
                    }, 1500);
                    PrefUtils.setValue(MainActivity.this, PrefUtils.KEY_FIRST_ENTER, false);
                }
            }
        });
//        NewsDetailActivity.startActivity(this, "http://music.163.com/m/topic/194001?type=android");
    }

    private void initToolbarAndDrawer() {
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.action_settings:
//                        Tips.showToast("Coming soon...");
//                        break;
////                        return prepareIntent(PrefsActivity.class);
////                    case R.id.action_search:
////                        return PostListActivity.start(MainActivity.this, "limiao");
//                    case R.id.action_about:
//                        return WebActivity.start(MainActivity.this, WebActivity.URL_BXBXBAI, "About Me");
//                }
//                return false;
//            }
//        });

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (materialMenu.getState().equals(IconState.BURGER)) {
//                    materialMenu.animatePressedState(IconState.ARROW);
//                    openDrawer();
//                } else {
//                    materialMenu.animatePressedState(IconState.BURGER);
//                    closeDrawer();
//                }
//            }
//        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        closeDrawer();
        // Otherwise, it may return to the previous fragment stack
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            // Lastly, it will rely on the system behavior for back
            super.onBackPressed();
        }
    }

    public boolean closeDrawer() {
        // If the drawer is open, back will close it
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return true;
        }
        return false;
    }

    private boolean openDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {

                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }
}