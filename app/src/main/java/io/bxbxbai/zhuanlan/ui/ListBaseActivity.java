package io.bxbxbai.zhuanlan.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.ButterKnife;
import io.bxbxbai.common.activity.BaseActivity;
import io.bxbxbai.common.core.RequestManager;
import io.bxbxbai.common.view.CircularLoadingView;
import io.bxbxbai.zhuanlan.R;

/**
 * Created by baia on 15/4/11.
 *
 * @author bxbxbai
 */
public class ListBaseActivity extends BaseActivity {

    protected static final String KEY_ID = "id";
    protected static final String KEY_NAME = "name";

    protected RecyclerView recyclerView;
    protected CircularLoadingView mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        initToolBar();

        recyclerView = ButterKnife.findById(this, R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLoadingView = ButterKnife.findById(this, R.id.v_loading);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.getRequestQueue().cancelAll(this);
    }
}
