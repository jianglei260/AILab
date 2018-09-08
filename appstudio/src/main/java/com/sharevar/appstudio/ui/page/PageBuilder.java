package com.sharevar.appstudio.ui.page;

import com.sharevar.appstudio.data.Layout;
import com.sharevar.appstudio.runtime.core.function.Function;

public class PageBuilder {
    private Page page;

    public PageBuilder() {
        page=new Page();
    }

    public PageBuilder addLayout(Layout layout){
        page.getLayouts().add(layout);
        return this;
    }

    public PageBuilder addFunction(Function function){
        page.getFunctions().add(function);
        return this;
    }
    public Page build(){
        return page;
    }
}
