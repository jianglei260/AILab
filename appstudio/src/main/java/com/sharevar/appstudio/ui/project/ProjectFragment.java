package com.sharevar.appstudio.ui.project;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharevar.appstudio.R;
import com.sharevar.appstudio.ui.base.BaseFragment;

public class ProjectFragment extends BaseFragment {

    @Override
    protected View onCreateView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_project,null);
    }
}
