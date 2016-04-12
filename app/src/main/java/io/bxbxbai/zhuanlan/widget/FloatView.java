package io.bxbxbai.zhuanlan.widget;

import android.content.Context;
import android.os.SystemClock;
import android.view.*;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.bxbxbai.zhuanlan.R;

public class FloatView extends RelativeLayout {

    private float mTouchStartX;
    private float mTouchStartY;
    private int x;
    private int y;

    private ImageView imageLogo;
    private TextView textTraffic;
    private ProgressBar progressBar;
    private WindowManager windowManager;
    private WindowManager.LayoutParams wmParams;

    private Boolean showFlag = false;
    private Boolean updateFlag = false;

    int i = 0;

    public FloatView(Context context) {
        super(context);
        windowManager = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();

        LayoutInflater.from(context).inflate(R.layout.layout_floatview, this);
        imageLogo = (ImageView) findViewById(R.id.image_logo);
        textTraffic = (TextView) findViewById(R.id.text_traffic);
        progressBar = (ProgressBar) findViewById(R.id.bar_progressbar);

        textTraffic.setText("test");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    SystemClock.sleep(1000);
                    i += 20;
                    FloatView.this.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(i);
                        }
                    });
                    if (i > 100) {
                        i = 0;
                    }
                }
            }
        }).start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mTouchStartX = event.getRawX();
                mTouchStartY = event.getRawY();
                x = wmParams.x;
                y = wmParams.y;
                updateFlag = true;

                break;
            case MotionEvent.ACTION_MOVE:
                int xDistance = (int) (event.getRawX() - mTouchStartX);
                wmParams.x = x + xDistance;
                int yDistance = (int) (event.getRawY() - mTouchStartY);
                wmParams.y = y + yDistance;
                updateViewPosition();

                break;

            case MotionEvent.ACTION_UP:
                int xUpDistance = (int) (event.getRawX() - mTouchStartX);
                wmParams.x = x + xUpDistance;
                int yUpDistance = (int) (event.getRawY() - mTouchStartY);
                wmParams.y = y + yUpDistance;

                updateViewPosition();
                updateFlag = false;
                break;
        }
        return true;
    }

    public void removeView() {
        if (showFlag) {
            windowManager.removeView(this);
            showFlag = false;
        }
    }

    public void updateViewPosition() {
        windowManager.updateViewLayout(this, wmParams);
        showFlag = true;
    }
}
