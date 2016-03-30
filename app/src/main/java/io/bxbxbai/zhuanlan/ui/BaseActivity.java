package io.bxbxbai.zhuanlan.ui;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;
import io.bxbxbai.common.StopWatch;
import io.bxbxbai.zhuanlan.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected SystemBarTintManager mTintManager;

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initToolBar();
    }

    protected void initToolBar() {
        this.toolbar = ButterKnife.findById(this, R.id.toolbar);
        if (toolbar != null) {
            this.setSupportActionBar(this.toolbar);
            this.toolbar.setNavigationOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    BaseActivity.this.finish();
                }
            });
        }
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.setTranslucentStatus(true);
        this.mTintManager = new SystemBarTintManager(this);
        this.mTintManager.setStatusBarTintEnabled(true);
        this.mTintManager.setTintColor(this.getResources().getColor(R.color.primaryDark));
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

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window w = this.getWindow();
        LayoutParams params = w.getAttributes();
        if (on) {
            params.flags |= 67108864;
        } else {
            params.flags &= -67108865;
        }

        w.setAttributes(params);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        StopWatch.log("level: " + level);
    }
}

