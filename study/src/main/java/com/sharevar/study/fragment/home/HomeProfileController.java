package com.sharevar.study.fragment.home;

import android.content.Context;

import com.sharevar.study.R;

import me.drakeet.multitype.MultiTypeAdapter;

public class HomeProfileController extends HomeController {
    public HomeProfileController(Context context) {
        super(context);
    }

    @Override
    protected String getTitle() {
        return getResources().getString(R.string.title_profile);
    }

    @Override
    public MultiTypeAdapter getAdapter() {
        MultiTypeAdapter adapter=new MultiTypeAdapter();
        return adapter;
    }
}
