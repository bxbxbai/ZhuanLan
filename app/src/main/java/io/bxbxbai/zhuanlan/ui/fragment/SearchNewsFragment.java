package io.bxbxbai.zhuanlan.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.model.DailyNews;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsFragment extends BaseNewsFragment {
    private List<String> dateResultList = new ArrayList<String>();

    private StickyListHeadersListView mStickyListHeadersListView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        listAdapter.updateDateResultList(dateResultList);
    }

    @Override
    protected boolean isCleanListChoice() {
        int position = mStickyListHeadersListView.getCheckedItemPosition();
        return mStickyListHeadersListView.getFirstVisiblePosition() > position
                || mStickyListHeadersListView.getLastVisiblePosition() < position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        assert view != null;
        mStickyListHeadersListView = (StickyListHeadersListView)
                view.findViewById(R.id.result_list);
        mStickyListHeadersListView.setAdapter(listAdapter);
        mStickyListHeadersListView.setOnItemClickListener(this);
        mStickyListHeadersListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mStickyListHeadersListView.setOnItemLongClickListener(this);

        return view;
    }

    @Override
    protected void clearListChoice() {
        for (int i = 0; i < newsList.size(); i++) {
            mStickyListHeadersListView.setItemChecked(i, false);
        }

        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void checkItemAtPosition(int position) {
        mStickyListHeadersListView.setItemChecked(position, true);
    }

    public void updateContent(List<DailyNews> newsList, List<String> dateResultList) {
        this.newsList = newsList;
        this.dateResultList = dateResultList;

        listAdapter.updateContents(newsList, dateResultList);
        mStickyListHeadersListView.setSelection(0);
    }
}
