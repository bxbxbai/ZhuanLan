package io.bxbxbai.zhuanlan.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.ButterKnife;
import com.android.volley.Response;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.adapter.PostListAdapter;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.data.GsonRequest;
import io.bxbxbai.zhuanlan.data.RequestManager;
import io.bxbxbai.zhuanlan.utils.EndlessScrollListener;
import io.bxbxbai.zhuanlan.utils.ToastUtils;
import io.bxbxbai.zhuanlan.utils.ZhuanLanApi;
import io.bxbxbai.zhuanlan.utils.ZhuanLanRetryPolicy;
import io.bxbxbai.zhuanlan.view.circularprogress.CircularLoadingView;

import java.util.List;

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
}
