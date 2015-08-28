package io.bxbxbai.zhuanlan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.ButterKnife;
import com.android.volley.Response;
import io.bxbxbai.common.core.GsonRequest;
import io.bxbxbai.common.core.RequestManager;
import io.bxbxbai.common.view.CircularLoadingView;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.adapter.PeopleListAdapter;
import io.bxbxbai.zhuanlan.bean.User;
import io.bxbxbai.zhuanlan.core.ZhuanLanApi;

import java.util.ArrayList;

/**
 *
 * @author bxbxbai
 */
public class PeopleListFragment extends Fragment {

    private ListView mListView;
    private CircularLoadingView mLoadingView;
    private PeopleListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.common_list, container, false);
        mListView = ButterKnife.findById(v, R.id.list_view);
        mLoadingView = ButterKnife.findById(v, R.id.v_loading);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new PeopleListAdapter(getActivity(), new ArrayList<User>());
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String slug = (String) view.getTag(R.id.key_slug);
                final String name = (String) view.getTag(R.id.key_name);
                mListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        PostListActivity.start(getActivity(), slug, name);
                    }
                }, 500);
            }
        });

        String[] ids = getActivity().getResources().getStringArray(R.array.people_ids);
        for (String id : ids) {
            GsonRequest<User> request = new GsonRequest<User>(String.format(ZhuanLanApi.API_BASE, id),
                    ZhuanLanApi.buildDefaultErrorListener()) {
                @Override
                public void onResponse(User user) {
                    mListView.setVisibility(View.VISIBLE);
                    mLoadingView.setVisibility(View.GONE);
                    mAdapter.add(user);
                }
            };
            RequestManager.addRequest(request, this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.getRequestQueue().cancelAll(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RequestManager.getRequestQueue().cancelAll(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    public static PeopleListFragment instate() {
        Bundle bundle = new Bundle();
        PeopleListFragment fragment = new PeopleListFragment();
        fragment.setArguments(bundle);

        return fragment;
    }
}
