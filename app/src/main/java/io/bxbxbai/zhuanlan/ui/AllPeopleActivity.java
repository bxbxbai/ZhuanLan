package io.bxbxbai.zhuanlan.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import io.bxbxbai.zhuanlan.R;

/**
 * 客户端内置的专栏用户列表
 *
 * @author bxbxbai
 */
public class AllPeopleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_people);
        initToolBar();
        setTitle(R.string.all_people);

        getSupportFragmentManager().beginTransaction().add(R.id.container,
                PeopleListFragment.newInstance()).commit();
    }

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, AllPeopleActivity.class));
    }
}
