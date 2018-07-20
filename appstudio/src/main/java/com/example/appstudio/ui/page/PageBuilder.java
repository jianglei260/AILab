package com.example.appstudio.ui.page;

import com.example.appstudio.data.Layout;
import com.example.appstudio.object.function.Function;

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
