package io.bxbxbai.zhuanlan.widget;

import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xuebin on 15/10/15.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements Bindable<T> {

    private SparseArray<View> views = new SparseArray<View>();

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public BaseViewHolder(ViewGroup parent, int layoutId) {
        this(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    public <T extends View> T findView(int resId) {
        View v = (View)this.views.get(resId);
        if(null == v) {
            v = this.itemView.findViewById(resId);
            this.views.put(resId, v);
        }
        return (T)v;
    }

    public String getString(@StringRes int resId, Object... args) {
        return itemView.getResources().getString(resId, args);
    }
}

