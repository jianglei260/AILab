package com.sharevar.appstudio.ui.object;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.sharevar.appstudio.R;
import com.sharevar.appstudio.object.Statement;
import com.sharevar.appstudio.object.Variable;
import com.sharevar.appstudio.object.function.Function;
import com.sharevar.appstudio.object.function.Parameter;
import com.sharevar.appstudio.object.function.CodeBlock;
import com.sharevar.appstudio.ui.base.BaseActivity;
import com.sharevar.appstudio.ui.base.BaseFragment;
import com.sharevar.appstudio.ui.common.RecyclerViewAdapter;
import com.sharevar.appstudio.ui.common.RecyclerViewBinder;

import java.util.ArrayList;
import java.util.List;

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
