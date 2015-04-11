package io.bxbxbai.zhuanlan.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.ButterKnife;
import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import io.bxbxbai.zhuanlan.R;

/**
 * Created by baia on 15/3/14.
 *
 * @author bxbxbai
 */
public class BaseActivity extends ActionBarActivity {

    protected Toolbar toolbar;
    protected MaterialMenuIconToolbar materialMenu;
    protected SystemBarTintManager mTintManager;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mTintManager = new SystemBarTintManager(this);

        mTintManager.setStatusBarTintEnabled(true);
//        mTintManager.setNavigationBarTintEnabled(true);

        mTintManager.setTintColor(getResources().getColor(R.color.primary));
    }

    protected void initToolBar() {
        toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        materialMenu = new MaterialMenuIconToolbar(this, Color.WHITE, MaterialMenuDrawable.Stroke.REGULAR) {
            @Override
            public int getToolbarViewId() {
                return R.id.toolbar;
            }
        };
        materialMenu.setState(MaterialMenuDrawable.IconState.ARROW);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
