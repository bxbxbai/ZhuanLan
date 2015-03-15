package io.bxbxbai.zhuanlan.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.data.GsonRequest;
import io.bxbxbai.zhuanlan.bean.NewsResult;
import io.bxbxbai.zhuanlan.support.Constants;
import io.bxbxbai.zhuanlan.ui.widget.SwipeRefreshLayout;


public class NewsListFragment extends BaseNewsFragment
        implements SwipeRefreshLayout.OnRefreshListener {
    private String date;

    private boolean isAutoRefresh;
    private boolean isToday;

    // Fragment is single in PortalActivity
    private boolean isSingle;
    private boolean isRefreshed = false;

    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            date = bundle.getString("date");
            isToday = bundle.getBoolean("first_page?");
            isSingle = bundle.getBoolean("single?");

            setRetainInstance(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        assert view != null;
        mListView = (ListView) view.findViewById(R.id.news_list);
        mListView.setAdapter(listAdapter);

        mListView.setOnItemClickListener(this);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mListView.setOnItemLongClickListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorScheme(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        clearActionMode();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        isAutoRefresh = pref.getBoolean("auto_refresh?", true);

        GsonRequest<NewsResult> request = new GsonRequest<NewsResult>(Constants.Url.ZHIHU_DAILY_BEFORE+date, NewsResult.class, new Response.Listener<NewsResult>() {
            @Override
            public void onResponse(NewsResult response) {
                if (response != null) {

                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Override
    public void onDestroy() {
        Crouton.cancelAllCroutons();

        super.onDestroy();
    }

    @Override
    protected boolean isCleanListChoice() {
        int position = mListView.getCheckedItemPosition();
        return mListView.getFirstVisiblePosition() > position || mListView.getLastVisiblePosition() < position;
    }

    @Override
    protected void clearListChoice() {
        mListView.clearChoices();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void checkItemAtPosition(int position) {
        mListView.setItemChecked(position, true);
    }


    @Override
    public void onRefresh() {

    }
}