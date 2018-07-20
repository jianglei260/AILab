package com.sharevar.study.fragment.learn;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.sharevar.study.R;
import com.sharevar.study.base.BaseFragment;
import com.sharevar.study.entity.Course;
import com.sharevar.study.entity.Unit;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.MultiTypeAdapter;

public class CourseContentFragment extends BaseFragment{
    @BindView(R.id.topbar)
    QMUITopBar topBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    MultiTypeAdapter adapter;
    private Course course;
    public CourseContentFragment() {

    }

    @Override
    protected View onCreateView() {
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_course_content,null);
        ButterKnife.bind(this,view);
        initTopBar();
        initRecyclerView();
        return view;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    public void startFragment(QMUIFragment fragment) {
        super.startFragment(fragment);
    }

    private void initRecyclerView(){
        adapter=new MultiTypeAdapter();
        if (course!=null){
            adapter.setItems(course.getUnits());
        }
        adapter.register(Unit.class,new ContentItemViewBinder(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }

    private void initTopBar() {
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });
        if (course!=null){
            topBar.setTitle(course.getName());
        }
    }

}
