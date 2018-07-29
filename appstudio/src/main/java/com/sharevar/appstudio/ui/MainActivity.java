package com.sharevar.appstudio.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.sharevar.appstudio.R;
import com.sharevar.appstudio.data.Layout;
import com.sharevar.appstudio.data.Project;
import com.sharevar.appstudio.persistent.DataRepository;
import com.sharevar.appstudio.persistent.remote.db.DataEngine;
import com.sharevar.appstudio.stand.LayoutBuilder;
import com.sharevar.appstudio.stand.rule.builtin.ViewRule;
import com.sharevar.appstudio.stand.type.Type;
import com.sharevar.appstudio.ui.base.BaseActivity;
import com.sharevar.appstudio.ui.base.BaseFragment;
import com.sharevar.appstudio.ui.entity.EntityFragment;
import com.sharevar.appstudio.ui.project.ProjectFragment;
import com.sharevar.appstudio.utils.IOTask;
import com.sharevar.appstudio.utils.RxUtil;
import com.sharevar.appstudio.utils.UIAction;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private ProjectFragment projectFragment;
    private EntityFragment entityFragment;

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
        projectFragment = new ProjectFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, projectFragment).commit();
        entityFragment=new EntityFragment();
        initNavigationMenu(navigationView);
    }

    public void startFragment(BaseFragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }

    public void initNavigationMenu(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_project:
                        break;
                    case R.id.nav_entity:
                        startFragment(entityFragment);
                        break;
                    case R.id.nav_layout:
                        break;
                    case R.id.nav_res:
                        break;
                    case R.id.nav_api:
                        break;
                }
                return true;
            }
        });
    }
}
