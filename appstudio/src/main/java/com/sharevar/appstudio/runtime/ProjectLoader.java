package com.sharevar.appstudio.runtime;

import android.content.Context;
import android.content.Intent;

import com.sharevar.appstudio.common.ds.CollectionOP;
import com.sharevar.appstudio.data.Project;
import com.sharevar.appstudio.runtime.ui.ContainerActivity;

import java.util.ArrayList;
import java.util.List;

public class ProjectLoader {
    public static ProjectLoader instance;
    private Context context;
    private List<Project> projects;
    public static void init(Context context){
        instance=new ProjectLoader();
        instance.context=context;
        instance.projects=new ArrayList<>();
    }

    public static ProjectLoader getInstance() {
        return instance;
    }

    public void load(Project project){
        Intent intent=new Intent(context,ContainerActivity.class);
        intent.putExtra("project",project.getName());
        projects.add(project);
        context.startActivity(intent);
    }

    public Project findProject(String name){
        return CollectionOP.findByAttr(projects,"name",name);
    }


}
