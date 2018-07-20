package com.example.appstudio.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.appstudio.R;
import com.example.appstudio.data.Layout;
import com.example.appstudio.data.Project;
import com.example.appstudio.persistent.DataRepository;
import com.example.appstudio.persistent.remote.db.DataEngine;
import com.example.appstudio.stand.LayoutBuilder;
import com.example.appstudio.stand.rule.builtin.ViewRule;
import com.example.appstudio.stand.type.Type;
import com.example.appstudio.ui.base.BaseActivity;
import com.example.appstudio.ui.project.ProjectFragment;
import com.example.appstudio.utils.IOTask;
import com.example.appstudio.utils.RxUtil;
import com.example.appstudio.utils.UIAction;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private ProjectFragment projectFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Layout layout = LayoutBuilder.from(this, R.layout.nav_header_main);
//        setContentView(LayoutBuilder.buildView(layout, this));
//        Type type = ViewRule.toType(Type.class, MainActivity.class.getName());
//        Log.d(TAG, "onCreate type: " + type.getType());

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.toggle_open, R.string.toggle_close);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.inflateHeaderView(R.layout.nav_header_main);
        projectFragment = ProjectFragment.newInstance();
        getFragmentManager().beginTransaction().add(R.id.container, projectFragment).commit();
        RxUtil.execute(new IOTask<Object>() {

            @Override
            public Object run() {
                DataRepository<Project> projectDataRepository = DataEngine.create(Project.class);
                Project project = new Project();
                project.setName("aaa");
                projectDataRepository.save(project);
                return project;
            }
        }, new UIAction<Object>() {
            @Override
            public void onComplete(Object o) {
                Log.d(TAG, "onComplete: " + o.toString());
            }
        });
    }

    public void initNavigationMenu(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_project:
                        break;
                    case R.id.nav_object:
                        break;
                    case R.id.nav_layout:
                        break;
                    case R.id.nav_res:
                        break;
                    case R.id.nav_api:
                        break;
                }
                return false;
            }
        });
    }
}
