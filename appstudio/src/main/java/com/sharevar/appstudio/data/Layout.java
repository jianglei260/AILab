package com.sharevar.appstudio.data;

import java.util.ArrayList;
import java.util.List;

public class Layout extends BaseObject {
    private Widget widget;
    private List<Attr> layoutAttrs = new ArrayList<>();
    private List<Action> bindActions = new ArrayList<>();
    private List<Layout> children = new ArrayList<>();
    private List<Attr> bindAttrs = new ArrayList<>();


    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    public List<Attr> getLayoutAttrs() {
        return layoutAttrs;
    }

    public void setLayoutAttrs(List<Attr> layoutAttrs) {
        this.layoutAttrs = layoutAttrs;
    }

    public List<Action> getBindActions() {
        return bindActions;
    }

    public void setBindActions(List<Action> bindActions) {
        this.bindActions = bindActions;
    }

    public List<Layout> getChildren() {
        return children;
    }

    public void setChildren(List<Layout> children) {
        this.children = children;
    }

    public List<Attr> getBindAttrs() {
        return bindAttrs;
    }

    public void setBindAttrs(List<Attr> bindAttrs) {
        this.bindAttrs = bindAttrs;
    }
}
