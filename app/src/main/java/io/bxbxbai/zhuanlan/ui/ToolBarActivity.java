package io.bxbxbai.zhuanlan.ui;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.ButterKnife;
import io.bxbxbai.common.StopWatch;
import io.bxbxbai.zhuanlan.R;

public abstract class ToolBarActivity extends BaseActivity {

    protected Toolbar toolbar;
    protected FrameLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_activity);
        toolbar = ButterKnife.findById(this, R.id.toolbar);
        container = ButterKnife.findById(this, R.id.activity_container);
    }

    @Override
    public void setContentView(int layoutResID) {
        this.setContentView(getLayoutInflater().inflate(layoutResID, container, false));
    }

    @Override
    public void setContentView(View view) {
        container.addView(view, 0);
        ButterKnife.bind(this, container);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        StopWatch.log("level: " + level);
    }
}

