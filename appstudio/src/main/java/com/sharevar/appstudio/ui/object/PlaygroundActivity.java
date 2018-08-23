package com.sharevar.appstudio.ui.object;

import android.os.Bundle;
import com.sharevar.appstudio.ui.base.BaseActivity;


public class PlaygroundActivity extends BaseActivity {
    PlaygroundFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            fragment = new PlaygroundFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    public PlaygroundFragment getFragment() {
        return fragment;
    }
}
