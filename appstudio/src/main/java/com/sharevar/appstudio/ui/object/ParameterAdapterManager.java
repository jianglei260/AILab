package com.sharevar.appstudio.ui.object;

import com.sharevar.appstudio.object.function.Parameter;

import java.util.HashMap;
import java.util.Map;

public class ParameterAdapterManager {
    private static ParameterAdapterManager instance;
    private Map<String, ParameterAdapter> adapterMap = new HashMap<>();

    public static ParameterAdapterManager getInstance() {
        if (instance == null)
            instance = new ParameterAdapterManager();
        return instance;
    }

    public void register(String type, ParameterAdapter adapter) {
        adapterMap.put(type, adapter);
    }

    public ParameterAdapter get(String type) {
        return adapterMap.get(type);
    }
}
