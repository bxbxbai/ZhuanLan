package io.bxbxbai.zhuanlan.core;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * Handler Helper
 *
 * @author bxbxbai
 */
public class ZhuanLanHandler {

    private static final int THREAD_POOL_SIZE = 4;
    private static final ZhuanLanHandler HANDLER = new ZhuanLanHandler();

    private Handler mHandler;
    private ExecutorService mExecutorService;

    public ZhuanLanHandler() {
        mHandler = new Handler(Looper.getMainLooper());
        mExecutorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public static ZhuanLanHandler get() {
        return HANDLER;
    }

    public void post(Runnable r) {
        mHandler.post(r);
    }

    public void postDelay(Runnable r, long delayMillis) {
        mHandler.postDelayed(r, delayMillis);
    }

    /**
     * 在工作线程做
     *
     * @param r runnable
     */
    public void postOnWorkThread(Runnable r) {
        mExecutorService.submit(r);
    }
}
