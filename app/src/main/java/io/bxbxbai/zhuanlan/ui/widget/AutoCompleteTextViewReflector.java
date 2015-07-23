package io.bxbxbai.zhuanlan.ui.widget;

import android.os.ResultReceiver;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;

import java.lang.reflect.Method;

/**
 * Created by baia on 15/3/13.
 */
public class AutoCompleteTextViewReflector {
    private Method doBeforeTextChanged, doAfterTextChanged;
    private Method ensureImeVisible;
    private Method showSoftInputUnchecked;

    AutoCompleteTextViewReflector() {
        try {
            doBeforeTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged");
            doBeforeTextChanged.setAccessible(true);
        } catch (NoSuchMethodException ignored) {

        }
        try {
            doAfterTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged");
            doAfterTextChanged.setAccessible(true);
        } catch (NoSuchMethodException ignored) {

        }
        try {
            ensureImeVisible = AutoCompleteTextView.class.getMethod("ensureImeVisible", boolean.class);
            ensureImeVisible.setAccessible(true);
        } catch (NoSuchMethodException ignored) {

        }
        try {
            showSoftInputUnchecked = InputMethodManager.class.getMethod(
                    "showSoftInputUnchecked", int.class, ResultReceiver.class);
            showSoftInputUnchecked.setAccessible(true);
        } catch (NoSuchMethodException ignored) {

        }
    }

    void doBeforeTextChanged(AutoCompleteTextView view) {
        if (doBeforeTextChanged != null) {
            try {
                doBeforeTextChanged.invoke(view);
            } catch (Exception ignored) {

            }
        }
    }

    void doAfterTextChanged(AutoCompleteTextView view) {
        if (doAfterTextChanged != null) {
            try {
                doAfterTextChanged.invoke(view);
            } catch (Exception ignored) {

            }
        }
    }

    public void ensureImeVisible(AutoCompleteTextView view, boolean visible) {
        if (ensureImeVisible != null) {
            try {
                ensureImeVisible.invoke(view, visible);
            } catch (Exception ignored) {

            }
        }
    }

    void showSoftInputUnchecked(InputMethodManager imm, View view, int flags) {
        if (showSoftInputUnchecked != null) {
            try {
                showSoftInputUnchecked.invoke(imm, flags, null);
                return;
            } catch (Exception ignored) {

            }
        }

        imm.showSoftInput(view, flags);
    }
}
