package io.bxbxbai.zhuanlan.ui;

import android.view.ViewGroup;
import io.bxbxbai.zhuanlan.R;

/**
 * Created by xuebin on 15/10/15.
 */
public class BaseDrawerActivity extends BaseActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContentRoot);

    }
}
