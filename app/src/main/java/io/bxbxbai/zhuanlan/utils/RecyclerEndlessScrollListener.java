package io.bxbxbai.zhuanlan.utils;

import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class RecyclerEndlessScrollListener extends RecyclerView.OnScrollListener {
    private int visibleThreshold = 2;
    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int startingPageIndex = 0;

    public RecyclerEndlessScrollListener() {
    }

    public RecyclerEndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public RecyclerEndlessScrollListener(int visibleThreshold, int startPage) {
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startPage;
        this.currentPage = startPage;
    }

    public abstract void onLoadMore(int page, int totalCount);

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(totalItemCount < this.previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if(totalItemCount == 0) {
                this.loading = true;
            }
        }

        if(this.loading && totalItemCount > this.previousTotalItemCount) {
            this.loading = false;
            this.previousTotalItemCount = totalItemCount;
            ++this.currentPage;
        }

        if(!this.loading && totalItemCount - visibleItemCount <= firstVisibleItem + this.visibleThreshold) {
            this.onLoadMore(this.currentPage + 1, totalItemCount);
            this.loading = true;
        }

    }

}
