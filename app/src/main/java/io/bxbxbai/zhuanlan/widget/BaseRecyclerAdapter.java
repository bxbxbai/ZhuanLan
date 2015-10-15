package io.bxbxbai.zhuanlan.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuebin on 15/10/15.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {

    private Context context;
    private List<T> dataList;
    protected LayoutInflater inflater;

    protected OnItemClickListener<T> onItemClickListener;

    public BaseRecyclerAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.dataList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public T getItem(int position) {
        if (0 <= position && position < dataList.size()) {
            return dataList.get(position);
        }
        return null;
    }

    public Context getContext() {
        return context;
    }

    public int getDataSize() {
        return dataList.size();
    }

    public void addItem(T item) {
        dataList.add(item);
        notifyDataSetChanged();
    }

    public void addItemList(List<T> list) {
        if (list != null) {
            dataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void setItemList(List<T> list) {
        if (list != null) {
            dataList.clear();
            dataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener<T> {
        public void onItemClicked(View view, T item);
    }
}
