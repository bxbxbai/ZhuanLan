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
public final class ZhuanLanHandler {

    private static final int THREAD_POOL_SIZE = 4;
    public static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private static ExecutorService mExecutorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    private ZhuanLanHandler() {
    }
    /**
     * 在工作线程做
     *
     * @param r runnable
     */
    public static void postOnWorkThread(Runnable r) {
        mExecutorService.submit(r);
    }
}
