package com.sharevar.study.fragment.home;

import android.content.Context;

import com.sharevar.study.R;
import com.sharevar.study.entity.Course;

import me.drakeet.multitype.MultiTypeAdapter;

public class HomePracticeController extends HomeController{
    public HomePracticeController(Context context) {
        super(context);
    }

    @Override
    protected String getTitle() {
        return getResources().getString(R.string.title_practice);
    }

    @Override
    public MultiTypeAdapter getAdapter() {
        MultiTypeAdapter adapter=new MultiTypeAdapter();
        return adapter;
    }
}
