package com.sharevar.appstudio.ui.common;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public abstract class RecyclerViewBinder<T> {
    private Map<Integer, View> holderMap = new HashMap<>();
    protected RecyclerView.ViewHolder viewHolder;

    public void setHolderMap(Map<Integer, View> holderMap) {
        this.holderMap = holderMap;
    }

    public void setViewHolder(RecyclerView.ViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

    public View view(@IdRes int id) {
        View view = holderMap.get(id);
        if (view == null) {
            view = viewHolder.itemView.findViewById(id);
            holderMap.put(id, view);
        }
        return view;
    }

    public TextView textView(@IdRes int id){
        return (TextView) view(id);
    }

    public LinearLayout linearLayout(@IdRes int id){
        return (LinearLayout) view(id);
    }

    public ImageView imageView(@IdRes int id){
        return (ImageView) view(id);
    }

    public Button button(@IdRes int id){
        return (Button) view(id);
    }
    public abstract void bind(T t);
}
