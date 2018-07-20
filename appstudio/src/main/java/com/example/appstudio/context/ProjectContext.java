package com.example.appstudio.context;

public class ProjectContext {
    private static final String BASE_URL="http://localhost:8080/data";
    private static ProjectContext context=new ProjectContext();
    public static ProjectContext getContext(){
        return context;
    }
    public String getServerPath(){
        return BASE_URL;
    }
}
