package io.bxbxbai.zhuanlan.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import io.bxbxbai.zhuanlan.App;
import io.bxbxbai.zhuanlan.R;

/**
 * Shared Preference utils
 *
 * @author bxbxbai
 */
public final class PrefUtils {

    private static final String PREF_NAMESPACE = "io.bxbxbai.zhuanlan.PREFS";

    public static final String KEY_HAS_SHORT_CUT = "has_short_cut";
    public static final String KEY_FIRST_ENTER = "first_enter";

    private static SharedPreferences SP;


    public static SharedPreferences getPreferences(Context context) {
        if (SP == null) {
            SP = context.getSharedPreferences(PREF_NAMESPACE, Context.MODE_PRIVATE);
        }
        return SP;
    }

    public static void setValue(@NonNull Context context,
                                @NonNull String key, @NonNull Object value) {
        String type = value.getClass().getSimpleName();
        SharedPreferences.Editor editor = getPreferences(context).edit();

        if (String.class.getSimpleName().equals(type)) {
            editor.putString(key, (String) value);
        } else if (Integer.class.getSimpleName().equals(type)) {
            editor.putInt(key, (Integer) value);
        } else if (Boolean.class.getSimpleName().equals(type)) {
            editor.putBoolean(key, (Boolean) value);
        } else if (Float.class.getSimpleName().equals(type)) {
            editor.putFloat(key, (Float) value);
        } else if (Long.class.getSimpleName().equals(type)) {
            editor.putLong(key, (Long) value);
        }
        editor.apply();
    }


    public static Object getValue(Context context, String key, Object defaultValue) {
        String type = defaultValue.getClass().getSimpleName();
        getPreferences(context);

        if (String.class.getSimpleName().equals(type)) {
            return SP.getString(key, (String) defaultValue);
        } else if (Integer.class.getSimpleName().equals(type)) {
            return SP.getInt(key, (Integer) defaultValue);
        } else if (Boolean.class.getSimpleName().equals(type)) {
            return SP.getBoolean(key, (Boolean) defaultValue);
        } else if (Float.class.getSimpleName().equals(type)) {
            return SP.getFloat(key, (Float) defaultValue);
        } else if (Long.class.getSimpleName().equals(type)) {
            return SP.getLong(key, (Long) defaultValue);
        }
        return defaultValue;
    }


    public static void setHasShortCut(Context context, Boolean has) {
        setValue(context, KEY_HAS_SHORT_CUT, has);
    }

    public static boolean hasShortCut(Context context) {
        return (Boolean)getValue(context, KEY_HAS_SHORT_CUT, false);
    }

    public static void createShortCut(@NonNull Context context) {
        Intent intent = new Intent();
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));
        intent.putExtra("duplicate", false);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));

        Intent i = new Intent();
        i.setAction(App.PACKAGE_NAME);
        i.addCategory("android.intent.category.DEFAULT");

        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, i);
        context.sendBroadcast(intent);
        PrefUtils.setHasShortCut(context, true);
    }
}
