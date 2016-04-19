package io.bxbxbai.zhuanlan.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.Bind;
import io.bxbxbai.common.view.CircularLoadingView;
import io.bxbxbai.zhuanlan.R;

/**
 * Created by baia on 15/4/11.
 *
 * @author bxbxbai
 */
public class ListBaseActivity extends ToolBarActivity {

    protected static final String KEY_ID = "id";
    protected static final String KEY_NAME = "name";

    @Bind(R.id.recycler_view)
    protected RecyclerView recyclerView;
    @Bind(R.id.v_loading)
    protected CircularLoadingView mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
