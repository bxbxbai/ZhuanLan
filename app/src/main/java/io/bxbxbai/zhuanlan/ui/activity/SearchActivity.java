package io.bxbxbai.zhuanlan.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.ui.fragment.SearchNewsFragment;
import io.bxbxbai.zhuanlan.ui.widget.IzzySearchView;

public class SearchActivity extends FragmentActivity {
    private IzzySearchView searchView;
    private SearchNewsFragment searchNewsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchNewsFragment = new SearchNewsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, searchNewsFragment)
                .commit();
    }

    @Override
    public void onDestroy() {
        Crouton.cancelAllCroutons();
        searchNewsFragment = null;

        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
