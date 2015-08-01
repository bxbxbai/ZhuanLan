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

    private View layout;
    private ImageView imageLogo;
    private TextView textTraffic;
    private ProgressBar pBar;
    private WindowManager wm = (WindowManager) getContext()
            .getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

    private Context context;

    private Boolean showFlag = false;

    private int progressTemp;
    private int setTemp;
    private Boolean updateFlag = false;

    int i = 0;

    public FloatView(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        layout = inflater.inflate(R.layout.layout_floatview, null);
        imageLogo = (ImageView) layout.findViewById(R.id.image_logo);
        textTraffic = (TextView) layout.findViewById(R.id.text_traffic);
        pBar = (ProgressBar) layout.findViewById(R.id.bar_progressbar);
        addView(layout);
        this.context = context;

        textTraffic.setText("test");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    SystemClock.sleep(1000);
                    i += 20;
                    FloatView.this.post(new Runnable() {
                        @Override
                        public void run() {
                            pBar.setProgress(i);
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
            wm.removeView(this);
            showFlag = false;
        }

    }


    public void updateViewPosition() {
        wm.updateViewLayout(this, wmParams);
        showFlag = true;
    }

}
