package io.bxbxbai.zhuanlan.core;

import android.os.SystemClock;
import android.support.v4.util.ArrayMap;
import android.view.Choreographer;
import io.bxbxbai.zhuanlan.utils.StopWatch;

import java.util.Map;

/**
 * @author bxbxbai
 */
public class ChoreographerHelper implements Choreographer.FrameCallback {

    private static final String TAG = ChoreographerHelper.class.getSimpleName();
    // 1s
    private static final int FRAME_DURATION = 1000;

    private long mTime;
    private long mFrameCount;

    private Choreographer mChoreographer = Choreographer.getInstance();
    private static Map<String, ChoreographerHelper> sChoreographerMap = new ArrayMap<>();


    private ChoreographerHelper() {
    }

    /**
     * get instance
     *
     * @param tag tag
     * @return ChoreographerHelper
     */
    public static ChoreographerHelper getInstance(Object tag) {
        if (tag == null) {
            throw new IllegalArgumentException("tag can not be null");
        }
        ChoreographerHelper helper = sChoreographerMap.get(tag.toString());
        if (helper == null) {
            helper = new ChoreographerHelper();
            sChoreographerMap.put(tag.toString(), helper);
        }
        return helper;
    }

    @Override
    public void doFrame(long frameTimeNanos) {
        if (SystemClock.elapsedRealtime() - mTime >= FRAME_DURATION) {
            StopWatch.log(TAG, "doFrame: " + mFrameCount);
            mFrameCount = 0;
            mTime = SystemClock.elapsedRealtime();
        } else {
            mFrameCount++;
        }
        mChoreographer.postFrameCallback(this);
    }


    public void start() {
        mChoreographer.postFrameCallback(this);
    }

    public void stop() {
        mChoreographer.removeFrameCallback(this);
        remove();
    }

    private void remove() {
        for (Object tag: sChoreographerMap.keySet()) {
            if (sChoreographerMap.get(tag.toString()) == this) {
                sChoreographerMap.remove(tag.toString());
                return;
            }
        }
    }
}
