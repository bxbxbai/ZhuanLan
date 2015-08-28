package io.bxbxbai.zhuanlan.activity;

import android.os.Bundle;
import android.widget.ListView;
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
    protected static final String KEY_USER = "_user";

    public static final String KEY_DATA = "data";

    protected ListView listView;
    protected CircularLoadingView mLoadingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        initToolBar();

        listView = ButterKnife.findById(this, R.id.lv_post);
        mLoadingView = ButterKnife.findById(this, R.id.v_loading);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.getRequestQueue().cancelAll(this);
    }
}
