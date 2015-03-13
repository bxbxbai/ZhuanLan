package io.bxbxbai.zhuanlan.utils;

import android.os.SystemClock;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Android应用中的秒表工具，跟踪某一段代码的执行时间<br/>
 * 调用begin方法启动秒表，并在当前时间打上tag。当你再调用end方法时，TimeTester就会输出这个tag的时间差，并删除tag<br/>
 *
 * 如果你调用lap方法，就相当于计次，不会删除tag，可以多次调用
 *
 * @author xuebin.bai
 * @version 1.0.0
 * @since 2014.08.01
 */
public final class StopWatch {
    private static final String TAG = StopWatch.class.getSimpleName();
    private static final Map<String, Long> TIME = new ConcurrentHashMap<String, Long>();

    private static final int INT_THOUSAND = 1000;
    /**
     * 给当前时间打上一个tag
     * @param tag tag
     */
    public static void begin(String tag) {
        begin(tag, false);
    }

    /**
     * 给当前时间打上一个tag
     * @param tag tag
     * @param needPrint 是否打印begin的tag
     */
    public static void begin(String tag, boolean needPrint) {
        TIME.put(tag, SystemClock.uptimeMillis());
        if (needPrint) {
            Log.i(TAG, "begin: " + tag);
        }
    }

    /**
     * 计算当前时间和之前打上tag的时间差
     *
     * @param tag tag
     * @return 运行的时间(ms)
     */
    public static long end(String tag) {
        return end(tag, "");
    }

    /**
     * 计算当前时间和之前打上tag的时间差
     *
     * @param tag tag
     * @param needPrint 是否打印begin的tag
     * @return 运行的时间(ms)
     */
    public static long end(String tag, boolean needPrint) {
        long t = lap(tag, "", needPrint);
        TIME.remove(tag);
        return t;
    }

    /**
     * 计算当前时间和之前打上tag的时间，并删除tag
     * @param tag tag
     * @param extra 额外信息
     * @return 运行的时间(ms)
     */
    public static long end(String tag, String extra) {
        long t = lap(tag, extra, true);
        TIME.remove(tag);
        return t;
    }


    /**
     * 计算当前时间和之前打上tag的时间差，不删除tag
     *
     * @param tag tag
     */
    public static void lap(String tag) {
        lap(tag, "", true);
    }

    /**
     * 计算当前时间和之前打上tag的时间差，不删除tag
     *
     * @param tag tag
     * @param extra extra
     */
    public static void lap(String tag, String extra) {
        lap(tag, extra, true);
    }

    /**
     * 计算当前时间和之前打上tag的时间差，不删除tag
     * @param tag tag
     * @param extra 额外信息
     * @param needPrint 是否打印begin的tag
     * @return 运行的时间(ms)
     */
    public static long lap(String tag, String extra, boolean needPrint) {
        extra = extra != null && extra.length() > 0 ? ", " + extra : "";
        long t = -1;
        if (TIME.containsKey(tag)) {
            t = (SystemClock.uptimeMillis() - TIME.get(tag));
            String time = t > INT_THOUSAND ? (double)t / INT_THOUSAND + "s" : t + "ms";
            if (needPrint) {
                Log.i(TAG, tag + ": " + time + ", " + extra);
            }
        } else {
            if (needPrint) {
                Log.e(TAG, "You did NOT CALL StopWatch.begin(" + tag + ")");
            }
        }
        return t;
    }

    /**
     * 打一个log
     * @param msg Message
     */
    public static void log(String msg) {
        log(TAG, msg);
    }

    /**
     * 打一个log
     * @param tag Tag
     * @param msg Message
     */
    public static void log(String tag, String msg) {
        Log.i(tag, msg);
    }
}

