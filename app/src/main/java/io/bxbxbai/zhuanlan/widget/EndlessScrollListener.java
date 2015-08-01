package io.bxbxbai.zhuanlan.widget;

import android.widget.AbsListView;

/**
 * Created by baia on 14-10-2.
 * @author bxbxbai
 */
public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

    // the minimum amount of items to have below your current scroll position
    // before loading more
    private int visibleThreshold = 2;

    //the current offset index of data you have loaded
    private int currentPage = 0;

    //the total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;

    //True if we are still waiting for the last set of data to load
    private boolean loading = true;

    //Sets the starting page index
    private int startingPageIndex = 0;


    public EndlessScrollListener() {
    }

    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public EndlessScrollListener(int visibleThreshold, int startPage) {
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startPage;
        this.currentPage = startPage;
    }

    /**
     * define the process for actually loading more data based on page
     *
     * @param page            page
     * @param totalItemsCount total items count
     */
    public abstract void onLoadMore(int page, int totalItemsCount);

    /**
     * this happens many times a second during a scroll, so be wary of the code you places here,
     * We are given a few useful parameters to help us work out if we need to load some more data,
     * but first we check if we are waiting for previous load to finish
     *
     * @param view  AbsListView
     * @param firstVisibleItem firstVisibleItem
     * @param visibleItemCount visibleItemCount
     * @param totalItemCount totalItemCount
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // if the total item count is zero and the previous isn't assume the list is invalidated and
        // should be reset back to initial state
//        StopWatch.log("firstVisible: " + firstVisibleItem + ", visibleItemCount: " + visibleItemCount + ", " +
//                "totalItemCount: " + totalItemCount);
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        // If it's still loading, we check to see if the data set count has changed, if so we conclude it
        // has finished loading and update the current page number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }

        // If it's currently loading, we check to see if we have breached the visible threshold and need to
        // reload more data. If we do need to reload some more data, we execute onLoadMore to fetch the data
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            onLoadMore(currentPage + 1, totalItemCount);
            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        StopWatch.log("scrollstate: " + scrollState);
    }
}
