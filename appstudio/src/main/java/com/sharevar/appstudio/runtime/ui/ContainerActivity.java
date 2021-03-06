package com.sharevar.appstudio.runtime.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.sharevar.appstudio.R;
import com.sharevar.appstudio.common.ds.CollectionOP;
import com.sharevar.appstudio.data.Layout;
import com.sharevar.appstudio.data.Project;
import com.sharevar.appstudio.runtime.core.function.Function;
import com.sharevar.appstudio.runtime.ProjectLoader;
import com.sharevar.appstudio.runtime.ui.stand.LayoutBuilder;
import com.sharevar.appstudio.ui.page.Page;

import java.util.List;

public class ContainerActivity extends AppCompatActivity {
    Project project;
    RelativeLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        project=ProjectLoader.getInstance().findProject(getIntent().getStringExtra("name"));
        container=findViewById(R.id.container);
        if (project!=null)
            load(project);
    }

    private void load(Project project){
        Page mainPage=CollectionOP.findByAttr(project.getPages(),"mainPage",project.getMainPage());
        List<Layout> layouts=mainPage.getLayouts();
        List<Function> functions=mainPage.getFunctions();
        renderLayout(layouts);
        executeFunctions(functions);
    }

    private void renderLayout(List<Layout> layouts){
        for (Layout layout : layouts) {
            View view=LayoutBuilder.buildView(layout,this);
            container.addView(view);
        }
    }

    private void executeFunctions(List<Function> functions){
        for (Function function : functions) {
            function.invoke();
        }
    }
}
