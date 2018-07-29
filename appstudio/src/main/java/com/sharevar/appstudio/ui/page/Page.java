package com.sharevar.appstudio.ui.page;

import com.sharevar.appstudio.data.BaseObject;
import com.sharevar.appstudio.data.Layout;
import com.sharevar.appstudio.object.function.Function;

import java.util.List;

public class Page extends BaseObject {
    private List<Function> functions;
    private List<Layout> layouts;
    private String name;

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    public List<Layout> getLayouts() {
        return layouts;
    }

    public void setLayouts(List<Layout> layouts) {
        this.layouts = layouts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
