package io.bxbxbai.zhuanlan.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import io.bxbxbai.common.core.GsonRequest;
import io.bxbxbai.common.core.RequestManager;
import io.bxbxbai.common.view.CircularLoadingView;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.adapter.PeopleListAdapter;
import io.bxbxbai.zhuanlan.bean.User;
import io.bxbxbai.zhuanlan.bean.UserEntity;
import io.bxbxbai.zhuanlan.core.DataCenter;
import io.bxbxbai.zhuanlan.core.ZhuanLanApi;
import io.bxbxbai.zhuanlan.widget.BaseRecyclerAdapter;

import java.util.List;
import java.util.Map;

/**
 * @author bxbxbai
 */
public class PeopleListFragment extends Fragment {

    private RecyclerView recyclerView;
    private CircularLoadingView mLoadingView;
    private PeopleListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.common_list, container, false);
        recyclerView = ButterKnife.findById(v, R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLoadingView = ButterKnife.findById(v, R.id.v_loading);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new PeopleListAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

        String[] ids = getActivity().getResources().getStringArray(R.array.people_ids);
        List<UserEntity> list = DataCenter.instance().queryAll(UserEntity.class);


        Map<String, UserEntity> map = new ArrayMap<>();
        for (UserEntity entity : list) {
            map.put(entity.getSlug(), entity);
        }

        for (String id : ids) {
            UserEntity entity = map.get(id);
            if (entity != null) {
                mAdapter.addItem(entity);
                showListView();
            } else {
                makeRequest(id);
            }
        }
    }

    private void makeRequest(String id) {
        GsonRequest<User> request = new GsonRequest<User>(String.format(ZhuanLanApi.API_BASE, id),
                ZhuanLanApi.buildDefaultErrorListener()) {
            @Override
            public void onResponse(User user) {
                showListView();
                mAdapter.addItem(user.toUserEntity());
                DataCenter.instance().save(user.toUserEntity());
            }
        };
        RequestManager.addRequest(request, this);
    }

    private void showListView() {
        recyclerView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.getRequestQueue().cancelAll(this);
    }

    public static PeopleListFragment newInstance() {
        Bundle bundle = new Bundle();
        PeopleListFragment fragment = new PeopleListFragment();
        fragment.setArguments(bundle);

        return fragment;
    }
}
