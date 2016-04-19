package io.bxbxbai.zhuanlan.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.bean.Post;
import io.bxbxbai.zhuanlan.core.ZhuanLanApi;

/**
 * WebActivity
 *
 * @author bxbxbai
 */
public class StoryActivity extends BaseActivity {

    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_template);

        post = getIntent().getParcelableExtra(StoryFragment.KEY_POST);
        if (post == null) {
            finish();
            return;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, StoryFragment.newInstance(post)).commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setTitle(post.getTitle());
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return menuItem.getItemId() == R.id.action_origin_web_page &&
                        WebActivity.start(StoryActivity.this, ZhuanLanApi.ZHUAN_LAN_URL + post.getUrl());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web, menu);
        return true;
    }

    public static void startActivity(Context context, Post post) {
        Intent i = new Intent(context, StoryActivity.class);
        i.putExtra(StoryFragment.KEY_POST, post);
        context.startActivity(i);
    }
}
