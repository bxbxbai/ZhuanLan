package io.bxbxbai.zhuanlan.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.ButterKnife;
import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;
import io.bxbxbai.zhuanlan.R;

/**
 * Created by baia on 15/3/14.
 *
 * @author bxbxbai
 */
public class BaseActivity extends ActionBarActivity {

    protected Toolbar toolbar;
    protected MaterialMenuIconToolbar materialMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        toolbar.inflateMenu(R.menu.main);
    }
}
