package io.bxbxbai.zhuanlan.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import butterknife.ButterKnife;
import io.bxbxbai.common.activity.BaseActivity;
import io.bxbxbai.zhuanlan.R;

import java.util.Random;

/**
 * Entry Activity
 *
 * @author bxbxbai
 */
public class EntryActivity extends BaseActivity {

    private static final int ANIMATION_DURATION = 2000;
    private static final float SCALE_END = 1.13F;


    private static final int[] SPLASH_ARRAY = {
        R.drawable.splash0,
        R.drawable.splash1,
        R.drawable.splash2,
        R.drawable.splash3,
        R.drawable.splash4,
        R.drawable.splash5,
        R.drawable.splash6,
        R.drawable.splash7,
        R.drawable.splash8,
        R.drawable.splash9,
        R.drawable.splash10,
        R.drawable.splash11,
        R.drawable.splash12,
        R.drawable.splash13,
        R.drawable.splash14,
        R.drawable.splash15,
        R.drawable.splash16,
    };

    ImageView mSplashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        mSplashImage = ButterKnife.findById(this, R.id.iv_entry);

        Random r = new Random(SystemClock.elapsedRealtime());
        mSplashImage.setImageResource(SPLASH_ARRAY[r.nextInt(SPLASH_ARRAY.length)]);
        animateImage();
        toolbar.setVisibility(View.GONE);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mTintManager.setTintColor(getResources().getColor(android.R.color.transparent));
    }

    private void animateImage() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mSplashImage, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mSplashImage, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                MainActivity.start(EntryActivity.this);
                EntryActivity.this.finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
