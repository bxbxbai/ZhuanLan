package io.bxbxbai.zhuanlan.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.ViewUtils;
import android.support.v7.widget.Toolbar;
import android.view.*;
import butterknife.ButterKnife;
import com.astuetz.PagerSlidingTabStrip;
import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;
import com.nineoldandroids.view.ViewHelper;
import de.greenrobot.event.EventBus;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.support.Constants;
import io.bxbxbai.zhuanlan.ui.fragment.NewsListFragment;
import io.bxbxbai.zhuanlan.ui.fragment.PeopleListFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    public static final int NUM_FRAGMENTS = 7;

    private MaterialMenuIconToolbar materialMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        return prepareIntent(PrefsActivity.class);
                    case R.id.action_pick_date:
                        return prepareIntent(PortalActivity.class);
                    case R.id.action_go_to_search:
                        return PostListActivity.start(MainActivity.this, "limiao");
                    case R.id.action_about:
                        return prepareIntent(CardListActivity.class);
                }
                return false;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo open drawer
            }
        });
        toolbar.inflateMenu(R.menu.main);

        getSupportFragmentManager().beginTransaction().add(R.id.container, PeopleListFragment.instate())
                .commit();

        setOverflowShowAlways();
//        EventBus.getDefault().register(this);
//        PostListActivity.start(this, "");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

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
                }catch (Exception e) {

                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    private void setOverflowShowAlways() {
        ViewConfiguration conf = ViewConfiguration.get(this);
        try {
            Field menuKeyField = conf.getClass().getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(conf, true);
        }catch (Exception e) {

        }
    }

    private boolean prepareIntent(Class clazz) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, clazz);
        startActivity(intent);
        return true;
    }

    final class MainPagerAdapter extends FragmentStatePagerAdapter {
        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Bundle bundle = new Bundle();
            Fragment newFragment = new NewsListFragment();
            newFragment.setArguments(bundle);

            Calendar dateToGetUrl = Calendar.getInstance();
            dateToGetUrl.add(Calendar.DAY_OF_YEAR, 1 - i);
            String date = Constants.Date.simpleDateFormat.format(dateToGetUrl.getTime());

            bundle.putBoolean("first_page?", i == 0);
            bundle.putBoolean("single?", false);
            bundle.putString("date", date);

            return newFragment;
        }

        @Override
        public int getCount() {
            return NUM_FRAGMENTS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Calendar displayDate = Calendar.getInstance();
            displayDate.add(Calendar.DAY_OF_YEAR, -position);

            String date = new SimpleDateFormat(getString(R.string.display_format)).format(displayDate.getTime());
            if (position == 0) {
                return getString(R.string.zhihu_daily_today) + " " + date;
            } else {
                return date;
            }
        }
    }
}