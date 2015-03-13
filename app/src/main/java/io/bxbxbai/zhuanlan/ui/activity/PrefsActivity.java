package io.bxbxbai.zhuanlan.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import io.bxbxbai.zhuanlan.ui.fragment.PrefsFragment;

public class PrefsActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new PrefsFragment())
                .commit();
    }
}