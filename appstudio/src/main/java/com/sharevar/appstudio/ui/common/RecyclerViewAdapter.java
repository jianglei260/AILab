package com.sharevar.appstudio.ui.common;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharevar.appstudio.common.ds.CollectionOP;
import com.sharevar.appstudio.ui.common.adapter.BinderNotFoundException;
import com.sharevar.appstudio.ui.common.adapter.ClassLinker;
import com.sharevar.appstudio.ui.common.adapter.ItemViewBinder;
import com.sharevar.appstudio.ui.common.adapter.Linker;
import com.sharevar.appstudio.ui.common.adapter.MultiTypeAdapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


import me.tatarka.bindingcollectionadapter.BindingRecyclerViewAdapter;

public class RecyclerViewAdapter extends MultiTypeAdapter {

    public class ItemViewHolder extends RecyclerView.ViewHolder {
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

    @Override
    public int indexInTypesOf(int position, @NonNull Object item) throws BinderNotFoundException {
        return super.indexInTypesOf(position, item);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public <T> void register(Class<? extends T> clazz, final RecyclerViewBinder<T> binder) {
        super.register(clazz, new ItemViewBinder<T, ItemViewHolder>() {
            @NonNull
            @Override
            protected ItemViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
                View root = inflater.inflate(binder.layoutRes(), parent, false);
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

    public <T> void register(Class<? extends T> clazz, final ClassLinkRule rule, final RecyclerViewBinder<T>... binders) {
        final ItemViewBinder[] itemViewBinders = new ItemViewBinder[binders.length];
        for (int i = 0; i < binders.length; i++) {
            ItemViewBinder itemViewBinder = new CustomItemViewBinder(binders[i]);
            itemViewBinders[i] = itemViewBinder;
        }
        super.register(clazz).to(itemViewBinders).withLinker(new Linker() {
            @Override
            public int index(int position, @NonNull Object o) {
                return indexOf(binders, rule.binderClass(o));
            }
        });
    }

    class CustomItemViewBinder extends ItemViewBinder<Object, ItemViewHolder> {
        RecyclerViewBinder binder;
        public CustomItemViewBinder(RecyclerViewBinder binder) {
            this.binder = binder;
        }

        @NonNull
        @Override
        protected ItemViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
            View root = inflater.inflate(binder.layoutRes(), parent, false);
            ItemViewHolder viewHolder = new ItemViewHolder(root);
            binder.setViewHolder(viewHolder);
            return viewHolder;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof CustomItemViewBinder)
                return binder.getClass().equals(((CustomItemViewBinder) obj).binder.getClass());
            return super.equals(obj);
        }

        @Override
        protected void onBindViewHolder(@NonNull ItemViewHolder holder, @NonNull Object item) {
            binder.setViewHolder(holder);
            binder.bind(item);
        }
    }

    public static int indexOf(Object[] collection, Class value) {
        for (int i = 0; i < collection.length; i++) {
            if (collection[i].getClass().isAssignableFrom(value))
                return i;
        }
        return -1;
    }
}
