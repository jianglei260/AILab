package com.sharevar.study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sharevar.study.base.BaseFragment;
import com.sharevar.study.base.BaseFragmentActivity;
import com.sharevar.study.fragment.home.HomeFragment;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected int getContextViewId() {
        return R.id.study;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            BaseFragment fragment = new HomeFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }
}
