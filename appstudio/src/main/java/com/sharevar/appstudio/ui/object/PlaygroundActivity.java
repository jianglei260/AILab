package com.sharevar.appstudio.ui.object;

import android.os.Bundle;
import com.sharevar.appstudio.ui.base.BaseActivity;


public class PlaygroundActivity extends BaseActivity {
    CodePlaygroundFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            fragment = new CodePlaygroundFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    public CodePlaygroundFragment getFragment() {
        return fragment;
    }
}
