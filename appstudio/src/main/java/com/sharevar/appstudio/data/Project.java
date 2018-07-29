package com.sharevar.appstudio.data;

import com.sharevar.appstudio.object.Type;
import com.sharevar.appstudio.ui.page.Page;

import java.util.List;

public class Project extends BaseObject {
    private String name;
    private List<Page> pages;
    private List<API> apis;
    private List<Type> entities;
    private List<Icon> icons;
    private String mainPage;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainPage() {
        return mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public List<API> getApis() {
        return apis;
    }

    public void setApis(List<API> apis) {
        this.apis = apis;
    }

    public List<Type> getEntities() {
        return entities;
    }

    public void setEntities(List<Type> entities) {
        this.entities = entities;
    }

    public List<Icon> getIcons() {
        return icons;
    }

    public void setIcons(List<Icon> icons) {
        this.icons = icons;
    }
}
