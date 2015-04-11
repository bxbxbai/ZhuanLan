package io.bxbxbai.zhuanlan.utils;

import android.content.Context;
import android.widget.Toast;
import io.bxbxbai.zhuanlan.App;

/**
 *
 * @author bxbxbai
 */
public final class T {
    private static void show(Context context, int resId, int duration) {
        Toast.makeText(context, resId, duration).show();
    }

    private static void show(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    public static void showShort(int resId) {
        Toast.makeText(App.getInstance(), resId, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(String message) {
        Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(int resId) {
        Toast.makeText(App.getInstance(), resId, Toast.LENGTH_LONG).show();
    }

    public static void showLong(String message) {
        Toast.makeText(App.getInstance(), message, Toast.LENGTH_LONG).show();
    }
}
