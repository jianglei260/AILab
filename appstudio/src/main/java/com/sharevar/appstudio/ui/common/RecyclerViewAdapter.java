package com.sharevar.appstudio.ui.common;

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

public  class RecyclerViewAdapter extends MultiTypeAdapter {
    public <T> void register(Class<? extends T> clazz, @LayoutRes final int layoutRes, final RecyclerViewBinder<T> binder){
        super.register(clazz, new ItemViewBinder<T, RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
                View root=inflater.inflate(layoutRes,parent,false);
                RecyclerView.ViewHolder viewHolder= new RecyclerView.ViewHolder(root){

                };
                binder.setViewHolder(viewHolder);
                return viewHolder;
            }

            @Override
            protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @NonNull T item) {
               binder.bind(item);
            }
        });
    }
}
