package com.sharevar.appstudio.context;

public class ProjectContext {
    private static final String BASE_URL="http://192.168.0.5:8080";
    private static ProjectContext context=new ProjectContext();
    public static ProjectContext getContext(){
        return context;
    }
    public String getServerPath(){
        return BASE_URL;
    }
}
