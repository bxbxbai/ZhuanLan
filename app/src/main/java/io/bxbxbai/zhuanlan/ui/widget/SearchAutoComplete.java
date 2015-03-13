package io.bxbxbai.zhuanlan.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;

import static io.bxbxbai.zhuanlan.ui.widget.IzzySearchView.isLandscapeMode;

/**
 * Created by baia on 15/3/13.
 */
public class SearchAutoComplete extends AutoCompleteTextView {
    private int mThreshold;
    private IzzySearchView mSearchView;

    public SearchAutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
        mThreshold = getThreshold();
    }

    void setSearchView(IzzySearchView searchView) {
        mSearchView = searchView;
    }

    @Override
    public void setThreshold(int threshold) {
        super.setThreshold(threshold);
        mThreshold = threshold;
    }

    @Override
    protected void replaceText(CharSequence text) {
    }

    @Override
    public void performCompletion() {
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

        if (hasWindowFocus && mSearchView.hasFocus() && getVisibility() == VISIBLE) {
            InputMethodManager inputManager = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(this, 0);
            if (isLandscapeMode(getContext())) {
                IzzySearchView.HIDDEN_METHOD_INVOKER.ensureImeVisible(this, true);
            }
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        mSearchView.onTextFocusChanged();
    }

    @Override
    public boolean enoughToFilter() {
        return mThreshold <= 0 || super.enoughToFilter();
    }

    @Override
    public boolean onKeyPreIme(int keyCode, @SuppressWarnings("NullableProblems") KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                KeyEvent.DispatcherState state = getKeyDispatcherState();
                if (state != null) {
                    state.startTracking(event, this);
                }
                return true;
            } else if (event.getAction() == KeyEvent.ACTION_UP) {
                KeyEvent.DispatcherState state = getKeyDispatcherState();
                if (state != null) {
                    state.handleUpEvent(event);
                }
                if (event.isTracking() && !event.isCanceled()) {
                    mSearchView.clearFocus();
                    mSearchView.setImeVisibility(false);
                    return true;
                }
            }
        }
        return super.onKeyPreIme(keyCode, event);
    }
}

