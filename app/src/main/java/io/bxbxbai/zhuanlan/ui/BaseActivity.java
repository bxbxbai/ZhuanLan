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
import butterknife.ButterKnife;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.balysv.materialmenu.MaterialMenuDrawable.IconState;
import com.balysv.materialmenu.MaterialMenuDrawable.Stroke;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import io.bxbxbai.common.StopWatch;
import io.bxbxbai.common.T;
import io.bxbxbai.common.core.RequestManager;
import io.bxbxbai.zhuanlan.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected MaterialMenuIconToolbar materialMenu;
    protected SystemBarTintManager mTintManager;

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initToolBar();
    }

    protected void initToolBar() {
        this.toolbar = ButterKnife.findById(this, R.id.toolbar);
        if (toolbar != null) {
            this.setSupportActionBar(this.toolbar);
            this.materialMenu = new MaterialMenuIconToolbar(this, -1, Stroke.REGULAR) {
                public int getToolbarViewId() {
                    return R.id.toolbar;
                }
            };
            this.materialMenu.setState(IconState.ARROW);
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
        RequestManager.cancelAll(this);
    }

    protected void sendRequest(Request<?> request) {
        RequestManager.addRequest(request, this.toString());
    }

    protected ErrorListener errorListener() {
        return new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                T.showToast(error.getMessage());
            }
        };
    }

    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        StopWatch.log("level: " + level);
    }
}

