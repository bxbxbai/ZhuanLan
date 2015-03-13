package io.bxbxbai.zhuanlan;


import android.os.SystemClock;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Android应用中的秒表工具，跟踪某一段代码的执行时间<br/>
 * 调用begin方法启动秒表，并在当前时间打上tag。当你再调用end方法时，TimeTester就会输出这个tag的时间差，并删除tag<br/>
 * <p/>
 * 如果你调用lap方法，就相当于计次，不会删除tag，可以多次调用
 *
 * @author bxbxbai
 * @version 1.0.0
 * @since 2014.08.01
 */
public final class StopWatch {
    private static final String TAG = StopWatch.class.getSimpleName();
    private static final Map<String, Long> TIME = new ConcurrentHashMap<String, Long>();

    /**
     * 给当前时间打上一个tag
     *
     * @param tag tag
     */
    public static void begin(String tag) {
        TIME.put(tag, SystemClock.uptimeMillis());
    }

    /**
     * 计算当前时间和之前打上tag的时间差
     *
     * @param tag tag
     */
    public static void end(String tag) {
        end(tag, "");
    }

    /**
     * 计算当前时间和之前打上tag的时间，并删除tag
     *
     * @param tag   tag
     * @param extra 额外信息
     */
    public static void end(String tag, String extra) {
        lap(tag, extra);
        TIME.remove(tag);
    }


    /**
     * 计算当前时间和之前打上tag的时间差，不删除tag
     *
     * @param tag tag
     */
    public static void lap(String tag) {
        lap(tag, "");
    }

    /**
     * 计算当前时间和之前打上tag的时间差，不删除tag
     *
     * @param tag   tag
     * @param extra 额外信息
     */
    public static void lap(String tag, String extra) {
        extra = extra != null && extra.length() > 0 ? ", " + extra : "";
        if (TIME.containsKey(tag)) {
            Log.i(TAG, tag + ": " + (SystemClock.uptimeMillis() - TIME.get(tag)) + "ms" + extra);
        } else {
            Log.e(TAG, "You did NOT CALL StopWatch.begin(" + tag + ")");
        }
    }
}

