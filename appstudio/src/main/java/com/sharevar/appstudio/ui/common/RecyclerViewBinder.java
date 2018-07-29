package com.sharevar.appstudio.ui.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public abstract class RecyclerViewBinder<T> {
    private Map<Integer, View> holderMap = new HashMap<>();
    private RecyclerView.ViewHolder viewHolder;

    public void setHolderMap(Map<Integer, View> holderMap) {
        this.holderMap = holderMap;
    }

    public void setViewHolder(RecyclerView.ViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

    public View view(int id) {
        View view = holderMap.get(id);
        if (view == null) {
            view = viewHolder.itemView.findViewById(id);
            holderMap.put(id, view);
        }
        return view;
    }

    public abstract void bind(T t);
}
