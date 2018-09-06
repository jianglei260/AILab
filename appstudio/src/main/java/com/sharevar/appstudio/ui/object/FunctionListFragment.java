package com.sharevar.appstudio.ui.object;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.sharevar.appstudio.R;
import com.sharevar.appstudio.object.Statement;
import com.sharevar.appstudio.object.function.Function;
import com.sharevar.appstudio.object.function.FunctionGroup;
import com.sharevar.appstudio.runtime.core.VM;
import com.sharevar.appstudio.runtime.sdk.SDK;
import com.sharevar.appstudio.ui.base.BaseFragment;
import com.sharevar.appstudio.ui.common.RecyclerViewAdapter;
import com.sharevar.appstudio.ui.common.RecyclerViewBinder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionListFragment extends BaseFragment {
    QMUITopBar mTopBar;
    QMUITabSegment mTabSegment;
    ViewPager mContentViewPager;
    private List<FunctionGroup> functionGroups;
    private Map<FunctionGroup, View> mPageMap = new HashMap<>();
    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return functionGroups.size();
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            View view = getPageView(functionGroups.get(position));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(view, params);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };

    @Override
    protected View onCreateView() {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_function_list, null);
        mTopBar = rootView.findViewById(R.id.topbar);
        mTabSegment = rootView.findViewById(R.id.tabSegment);
        mContentViewPager = rootView.findViewById(R.id.contentViewPager);
        VM vm = VM.load(getActivity());
        functionGroups = vm.getFunctionGroups();
        initTopBar();
        initTabAndPager();
        return rootView;
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });

        mTopBar.setTitle("函数列表");
    }

    private void initTabAndPager() {
        for (FunctionGroup functionGroup : functionGroups) {
            mTabSegment.addTab(new QMUITabSegment.Tab(functionGroup.getName()));
        }
        int space = QMUIDisplayHelper.dp2px(getContext(), 16);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setMode(QMUITabSegment.MODE_SCROLLABLE);
        mTabSegment.setItemSpaceInScrollMode(space);
        mTabSegment.setupWithViewPager(mContentViewPager, false);
        mTabSegment.setPadding(space, 0, space, 0);
        mContentViewPager.setAdapter(mPagerAdapter);
        mContentViewPager.setCurrentItem(0, false);

    }

    public void insertStatement(Function function){
        Statement statement=new Statement();
        statement.setFunction(function);
        ((PlaygroundActivity)getActivity()).getFragment().insertStatement(statement);
    }

    public View getPageView(FunctionGroup functionGroup) {
        View view = mPageMap.get(functionGroup);
        if (view == null) {
            RecyclerView recyclerView = new RecyclerView(getActivity());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            recyclerView.setLayoutParams(layoutParams);
            view = recyclerView;
            mPageMap.put(functionGroup, view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            RecyclerViewAdapter adapter=new RecyclerViewAdapter();
            adapter.register(Function.class,new RecyclerViewBinder<Function>() {
                @Override
                public void bind(final Function function) {
                    textView(R.id.fun_name).setText(function.getName());
                    textView(R.id.fun_desc).setText(function.getDesc());
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            insertStatement(function);
                            popBackStack();
                        }
                    });
                }

                @Override
                public int layoutRes() {
                    return  R.layout.list_item_repo_function;
                }
            });
            recyclerView.setAdapter(adapter);
            adapter.setItems(functionGroup.getFunctions());
            adapter.notifyDataSetChanged();
        }
        return view;
    }

}
