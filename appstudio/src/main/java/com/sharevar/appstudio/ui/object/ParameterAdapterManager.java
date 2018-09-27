package com.sharevar.appstudio.ui.object;

import android.content.Context;

import com.sharevar.appstudio.runtime.core.RuntimeContext;
import com.sharevar.appstudio.runtime.core.VM;
import com.sharevar.appstudio.runtime.core.function.Parameter;

import java.util.HashMap;
import java.util.Map;

public class ParameterAdapterManager {
    private static ParameterAdapterManager instance;
    private Map<String, Class<? extends ParameterAdapter>> adapterMap = new HashMap<>();

    public static ParameterAdapterManager getInstance() {
        if (instance == null)
            instance = new ParameterAdapterManager();
        return instance;
    }

    public void register(String type, Class<? extends ParameterAdapter> adapterClass) {
        adapterMap.put(type, adapterClass);
    }

    public ParameterAdapter get(String id, Parameter parameter) {
        Class<? extends ParameterAdapter> clazz = adapterMap.get(parameter.getType().getName());
        ParameterAdapter adapter = null;
        try {
            if (clazz == null)
                return null;
            adapter = clazz.getConstructor(Parameter.class).newInstance(parameter);
            RuntimeContext runtimeContext = VM.getRuntimeContext(id);
            adapter.setContext(runtimeContext.getContext());
            adapter.runtimeContext = runtimeContext;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adapter;
    }

    public ParameterAdapter getDefault(String id, Parameter parameter) {
        ParameterAdapter adapter = new DefaultParameterAdapter(parameter);
        RuntimeContext runtimeContext = VM.getRuntimeContext(id);
        adapter.setContext(runtimeContext.getContext());
        adapter.runtimeContext = runtimeContext;
        return adapter;
    }
}
