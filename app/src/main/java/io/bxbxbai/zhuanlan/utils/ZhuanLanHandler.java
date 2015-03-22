package io.bxbxbai.zhuanlan.utils;

import android.os.Handler;
import android.os.Looper;

/**
 *
 * Handler on Main Thread
 * @author bxbxbai
 */
public class ZhuanLanHandler {

    private static final ZhuanLanHandler sHandler = new ZhuanLanHandler();

    private Handler mHandler;

    public ZhuanLanHandler() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static ZhuanLanHandler get() {
        return sHandler;
    }



    public void post(Runnable r) {
        mHandler.post(r);
    }

    public void postDelay(Runnable r, long delayMillis) {
        mHandler.postDelayed(r, delayMillis);
    }

}
