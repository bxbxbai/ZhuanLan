package io.bxbxbai.zhuanlan.ui;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.ButterKnife;
import io.bxbxbai.common.StopWatch;
import io.bxbxbai.zhuanlan.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    protected void initToolBar() {
        this.toolbar = ButterKnife.findById(this, R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    BaseActivity.this.finish();
                }
            });
        }
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initToolBar();
    }

    protected void setTitle(String title) {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public void setTitle(@StringRes int resId) {
        this.setTitle(this.getString(resId));
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        StopWatch.log("level: " + level);
    }
}

