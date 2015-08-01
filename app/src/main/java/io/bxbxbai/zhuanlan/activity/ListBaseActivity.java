package io.bxbxbai.zhuanlan.activity;

import android.os.Bundle;
import android.widget.ListView;
import butterknife.ButterKnife;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.core.data.RequestManager;
import io.bxbxbai.zhuanlan.widget.circularprogress.CircularLoadingView;

/**
 * Created by baia on 15/4/11.
 *
 * @author bxbxbai
 */
public class ListBaseActivity extends BaseActivity {

    protected static final String KEY_ID = "id";
    protected static final String KEY_NAME = "name";

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
