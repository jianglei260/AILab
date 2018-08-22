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

    protected RecyclerViewAdapter.ItemViewHolder viewHolder;

    public void setViewHolder(RecyclerViewAdapter.ItemViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

    public View view(@IdRes int id) {
        return viewHolder.view(id);
    }

    public TextView textView(@IdRes int id) {
        return (TextView) viewHolder.view(id);
    }

    public LinearLayout linearLayout(@IdRes int id) {
        return (LinearLayout) viewHolder.view(id);
    }

    public ImageView imageView(@IdRes int id) {
        return (ImageView) viewHolder.view(id);
    }

    public Button button(@IdRes int id) {
        return (Button) viewHolder.view(id);
    }

    public abstract void bind(T t);
}
