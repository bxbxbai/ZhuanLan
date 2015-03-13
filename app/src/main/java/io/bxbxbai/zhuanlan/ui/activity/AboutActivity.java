package io.bxbxbai.zhuanlan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.*;
import io.bxbxbai.zhuanlan.App;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.view.AnimTabsView;
import io.bxbxbai.zhuanlan.view.FloatView;

public class AboutActivity extends FragmentActivity {
    private static final String TAG = "AboutActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
        createView();
    }

    private void createView() {
        FloatView view = new FloatView(getApplicationContext());

        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams wmParams = ((App) getApplication()).getWindowManagerParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags |= 8;
        wmParams.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        wmParams.x = 0;
        wmParams.y = 80;
        wmParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        wmParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getInstance().startActivity(new Intent("com.bxbxbai.zhuanlan.ui.activity.AboutActivity"));
                Log.i(TAG, "onclick");
            }
        });

        wm.addView(view, wmParams);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private AnimTabsView mTabsView;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_about, container, false);
            setupViews(rootView);
            return rootView;
        }

        private void setupViews(View rootView) {
            mTabsView = (AnimTabsView) rootView.findViewById(R.id.publiclisten_tab);
            mTabsView.addItem("推荐");
            mTabsView.addItem("排行榜");
            mTabsView.addItem("歌单");
            mTabsView.addItem("DJ节目");

            mTabsView.setOnAnimTabsItemViewChangeListener(
                    new AnimTabsView.IAnimTabsItemViewChangeListener() {
                @Override
                public void onChange(AnimTabsView tabsView, int oldPosition, int currentPosition) {
                }
            });
        }
    }

}
