package com.sharevar.appstudio.ui.object;

import android.content.Context;

import com.sharevar.appstudio.object.function.Parameter;

import java.util.HashMap;
import java.util.Map;

public class ParameterAdapterManager {
    private static ParameterAdapterManager instance;
    private Map<String, Class<? extends ParameterAdapter>> adapterMap = new HashMap<>();
    private Context context;

    public static ParameterAdapterManager getInstance(Context context) {
        if (instance == null)
            instance = new ParameterAdapterManager(context);
        return instance;
    }

    public ParameterAdapterManager(Context context) {
        this.context = context;
    }

    public void register(String type, Class<? extends ParameterAdapter> adapterClass) {
        adapterMap.put(type, adapterClass);
    }

    public ParameterAdapter get(Parameter parameter) {
        Class<? extends ParameterAdapter> clazz = adapterMap.get(parameter.getType().getName());
        ParameterAdapter adapter = null;
        try {
            if (clazz == null)
                return null;
            adapter = clazz.getConstructor(Parameter.class).newInstance(parameter);
            adapter.setContext(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adapter;
    }

    public ParameterAdapter getDefault(Parameter parameter) {
        ParameterAdapter adapter = new DefaultParameterAdapter(parameter);
        adapter.setContext(context);
        return adapter;
    }
}
