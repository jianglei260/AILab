package com.sharevar.study.fragment.home;

import android.content.Context;
import android.widget.Toast;

import com.sharevar.study.R;
import com.sharevar.study.data.CourseRepository;
import com.sharevar.study.entity.Course;
import com.sharevar.study.entity.ResponseData;
import com.sharevar.study.utils.IOTask;
import com.sharevar.study.utils.RxUtil;
import com.sharevar.study.utils.UIAction;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

public class HomeLearnController extends HomeController {
    private MultiTypeAdapter adapter;
    public HomeLearnController(Context context) {
        super(context);
        initData();
    }
    public void initData(){
        RxUtil.execute(new IOTask<ResponseData<List<Course>>>() {
            @Override
            public ResponseData<List<Course>> run() {
                return CourseRepository.getInstance().courseList();
            }
        }, new UIAction<ResponseData<List<Course>>>() {
            @Override
            public void onComplete(ResponseData<List<Course>> listResponseData) {
                if (listResponseData.getRspCode().equals("200")){
                    adapter.setItems(listResponseData.getData());
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getContext(),listResponseData.getRspMsg(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected String getTitle() {
        return getResources().getString(R.string.title_learn);
    }

    @Override
    public MultiTypeAdapter getAdapter() {
        if (adapter==null){
            adapter=new MultiTypeAdapter();
            adapter.register(Course.class,new CourseItemViewBinder(this));
        }
        return adapter;
    }
}
