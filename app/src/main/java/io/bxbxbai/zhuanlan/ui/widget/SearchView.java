package io.bxbxbai.zhuanlan.ui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.*;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import io.bxbxbai.zhuanlan.R;

/**
 * Simplified version of SearchView, only EditText and a clear text button is supported
 * Thanks to code of SearchView in AppCompat
 */

public class SearchView extends LinearLayout {
    public static final AutoCompleteTextViewReflector HIDDEN_METHOD_INVOKER = new AutoCompleteTextViewReflector();

    private boolean mClearingFocus;

    private SearchAutoComplete mQueryTextView;
    private View mSearchPlate;
    private ImageView mClearTextButton;
    private OnQueryTextListener mOnQueryChangeListener;
    private CharSequence mQueryHint;
    private Runnable mShowImeRunnable = new Runnable() {
        public void run() {
            InputMethodManager imm = (InputMethodManager)
                    getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                HIDDEN_METHOD_INVOKER.showSoftInputUnchecked(imm, SearchView.this, 0);
            }
        }
    };

    private Runnable mUpdateDrawableStateRunnable = new Runnable() {
        public void run() {
            updateFocusedState();
        }
    };

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.izzy_search_view, this, true);

        mQueryTextView = (SearchAutoComplete) findViewById(R.id.search_src_text);
        mQueryTextView.setSearchView(this);

        mSearchPlate = findViewById(R.id.search_plate);
        mClearTextButton = (ImageView) findViewById(R.id.search_close_btn);

        mClearTextButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (!TextUtils.isEmpty(mQueryTextView.getText())) {
                    mQueryTextView.setText("");
                    mQueryTextView.requestFocus();
                    setImeVisibility(true);
                }
            }
        });

        mQueryTextView.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int before, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int after) {
            }

            public void afterTextChanged(Editable s) {
                updateCloseButton();
            }
        });

        mQueryTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                onSubmitQuery();
                return true;
            }
        });

        setFocusable(true);
        updateViewsVisibility();
    }

    static boolean isLandscapeMode(Context context) {
        return context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        if (mClearingFocus) return false;
        if (!isFocusable()) return false;

        boolean result = mQueryTextView.requestFocus(direction, previouslyFocusedRect);
        if (result) {
            updateViewsVisibility();
        }

        return result;
    }

    @Override
    public void clearFocus() {
        mClearingFocus = true;
        setImeVisibility(false);
        super.clearFocus();
        mQueryTextView.clearFocus();
        mClearingFocus = false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        postUpdateFocusedState();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.AT_MOST:
                width = Math.min(getPreferredWidth(), width);
                break;
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.UNSPECIFIED:
                width = getPreferredWidth();
                break;
        }
        widthMode = MeasureSpec.EXACTLY;
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, widthMode), heightMeasureSpec);
    }

    @Override
    protected void onDetachedFromWindow() {
        removeCallbacks(mUpdateDrawableStateRunnable);
        super.onDetachedFromWindow();
    }

    private int getPreferredWidth() {
        return (int) (320 * getContext().getResources().getDisplayMetrics().density + 0.5f);
    }

    public void setOnQueryTextListener(OnQueryTextListener listener) {
        mOnQueryChangeListener = listener;
    }

    private void forceSuggestionQuery() {
        HIDDEN_METHOD_INVOKER.doBeforeTextChanged(mQueryTextView);
        HIDDEN_METHOD_INVOKER.doAfterTextChanged(mQueryTextView);
    }

    void onTextFocusChanged() {
        updateViewsVisibility();
        postUpdateFocusedState();
        if (mQueryTextView.hasFocus()) {
            forceSuggestionQuery();
        }
    }

    private void updateViewsVisibility() {
        updateCloseButton();
    }

    private void updateCloseButton() {
        final boolean hasText = !TextUtils.isEmpty(mQueryTextView.getText());
        mClearTextButton.setVisibility(hasText ? VISIBLE : GONE);
        mClearTextButton.getDrawable().setState(hasText ? ENABLED_STATE_SET : EMPTY_STATE_SET);
    }

    private void postUpdateFocusedState() {
        post(mUpdateDrawableStateRunnable);
    }

    private void updateFocusedState() {
        boolean focused = mQueryTextView.hasFocus();
        mSearchPlate.getBackground().setState(focused ? FOCUSED_STATE_SET : EMPTY_STATE_SET);
        invalidate();
    }

    public void setImeVisibility(final boolean visible) {
        if (visible) {
            post(mShowImeRunnable);
        } else {
            removeCallbacks(mShowImeRunnable);
            InputMethodManager imm = (InputMethodManager)
                    getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.hideSoftInputFromWindow(getWindowToken(), 0);
            }
        }
    }

    public void setQueryHint(CharSequence hint) {
        mQueryHint = hint;
        updateQueryHint();
    }

    private CharSequence getDecoratedHint(CharSequence hintText) {
        SpannableStringBuilder ssb = new SpannableStringBuilder("   ");
        ssb.append(hintText);
        Drawable searchIcon = getContext().getResources().getDrawable(R.drawable.ic_action_search);
        int textSize = (int) (mQueryTextView.getTextSize() * 1.25);
        searchIcon.setBounds(0, 0, textSize, textSize);
        ssb.setSpan(new ImageSpan(searchIcon), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    private void updateQueryHint() {
        if (mQueryHint != null) {
            mQueryTextView.setHint(getDecoratedHint(mQueryHint));
        } else {
            mQueryTextView.setHint(getDecoratedHint(""));
        }
    }

    private void onSubmitQuery() {
        CharSequence query = mQueryTextView.getText();
        if (query != null && TextUtils.getTrimmedLength(query) > 0) {
            if (mOnQueryChangeListener != null) {
                mOnQueryChangeListener.onQueryTextSubmit(query.toString());
            }
        }
    }

    public interface OnQueryTextListener {
        boolean onQueryTextSubmit(String query);
    }

}
