package com.sharevar.appstudio.runtime.sdk;

import com.sharevar.appstudio.common.serializable.XmlUtil;
import com.sharevar.appstudio.object.function.Function;
import com.sharevar.appstudio.object.function.Mode;
import com.sharevar.appstudio.object.function.Option;
import com.sharevar.appstudio.object.function.Parameter;
import com.sharevar.appstudio.object.function.FunctionGroup;
import com.sharevar.appstudio.runtime.core.RuntimeContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class SDK {
    private static SDK instance;
    private RuntimeContext context;
    private List<FunctionGroup> functionGroups;

    public static SDK getInstance(RuntimeContext context) {
        if (instance == null)
            instance = new SDK(context);
        return instance;
    }

    public SDK(RuntimeContext context) {
        this.context = context;
        loadFuction();
    }

    public void loadFuction() {
        try {
            InputStream is = context.getContext().getAssets().open("function.xml");
            InputStreamReader reader = new InputStreamReader(is);
            functionGroups = XmlUtil.listFrom(reader, FunctionGroup.class, Function.class, Mode.class, Parameter.class, Option.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<FunctionGroup> getFunctionGroups() {
        return functionGroups;
    }
}
