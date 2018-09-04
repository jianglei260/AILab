package com.sharevar.appstudio.ui.common;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;
import me.tatarka.bindingcollectionadapter.BindingRecyclerViewAdapter;

public class RecyclerViewAdapter extends MultiTypeAdapter {

    public  class ItemViewHolder extends RecyclerView.ViewHolder {
        private Map<Integer, View> holderMap = new HashMap<>();

        public ItemViewHolder(View itemView) {
            super(itemView);
        }

        public View view(@IdRes int id) {
            View view = holderMap.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                holderMap.put(id, view);
            }
            return view;
        }

    }

    public <T> void register(Class<? extends T> clazz, @LayoutRes final int layoutRes, final RecyclerViewBinder<T> binder) {
        super.register(clazz, new ItemViewBinder<T, ItemViewHolder>() {
            @NonNull
            @Override
            protected ItemViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
                View root = inflater.inflate(layoutRes, parent, false);
                ItemViewHolder viewHolder = new ItemViewHolder(root);
                binder.setViewHolder(viewHolder);
                return viewHolder;
            }

            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, @NonNull T item) {
                binder.setViewHolder(holder);
                binder.bind(item);
            }
        });
    }
}
