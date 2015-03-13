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
import io.bxbxbai.zhuanlan.task.BaseSearchTask;
import io.bxbxbai.zhuanlan.ui.fragment.SearchNewsFragment;
import io.bxbxbai.zhuanlan.ui.widget.IzzySearchView;

public class SearchActivity extends FragmentActivity {
    private IzzySearchView searchView;
    private SearchNewsFragment searchNewsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

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

    private void initView() {
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowCustomEnabled(true);

        searchView = new IzzySearchView(this);
        searchView.setQueryHint(getResources().getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new IzzySearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                new SearchTask(getString(R.string.display_format)).execute(query);
                searchView.clearFocus();
                return true;
            }
        });

        RelativeLayout relative = new RelativeLayout(this);
        relative.addView(searchView);
        getActionBar().setCustomView(relative);
    }

    class SearchTask extends BaseSearchTask {
        private ProgressDialog dialog;

        public SearchTask(String dateFormat) {
            super(dateFormat);
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(SearchActivity.this);
            dialog.setMessage(getString(R.string.searching));
            dialog.setCancelable(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    SearchTask.this.cancel(true);
                }
            });
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }

            if (!isCancelled()) {
                if (isSearchSuccess) {
                    searchNewsFragment.updateContent(newsList, dateResultList);
                } else {
                    Crouton.makeText(SearchActivity.this,
                            getString(R.string.no_result_found),
                            Style.ALERT).show();
                }
            }
        }
    }
}
