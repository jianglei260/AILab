package com.sharevar.appstudio.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sharevar.appstudio.R;

public abstract class BaseListFragment extends BaseFragment {
   public RecyclerView recyclerView;
   public SmartRefreshLayout refreshLayout;
   public View root;
    @Override
    protected View onCreateView() {
        root= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list,null);
        recyclerView=root.findViewById(R.id.recycler_view);
        refreshLayout=root.findViewById(R.id.refresh_layout);
        bindData();
        return root;
    }

    public  abstract void bindData();
}
