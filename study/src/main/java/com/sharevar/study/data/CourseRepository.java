package com.sharevar.study.data;

import com.sharevar.study.data.server.RemoteDataSource;
import com.sharevar.study.entity.Chapter;
import com.sharevar.study.entity.Course;
import com.sharevar.study.entity.FlatFragment;
import com.sharevar.study.entity.Fragment;
import com.sharevar.study.entity.Part;
import com.sharevar.study.entity.ResponseData;
import com.sharevar.study.entity.Unit;

import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    private static CourseRepository instance=new CourseRepository();

    public static CourseRepository getInstance() {
        return instance;
    }

    public CourseRepository() {
    }

    public ResponseData<List<Course>> courseList(){
        return RemoteDataSource.getInstance().courseList();
    }

    public List<FlatFragment> toFlatFragments(Unit unit,String content){
        List<FlatFragment> flatFragments=new ArrayList<>();
        for (Chapter chapter : unit.getChapters()) {
            if (!isEmpty(chapter)){
                FlatFragment chapterFlatFragment=createFromFragment(chapter,content);
                flatFragments.add(chapterFlatFragment);
            }
            for (Part part : chapter.getParts()) {
                if (!isEmpty(part)){
                    FlatFragment partFlatFragment=createFromFragment(part,content);
                    flatFragments.add(partFlatFragment);
                }
            }
        }
        return flatFragments;
    }

    public ResponseData<String> getContentFromFile(String path){
        return RemoteDataSource.getInstance().fileContent(path);
    }

    public FlatFragment createFromFragment(Fragment fragment,String content){
        FlatFragment flatFragment=new FlatFragment();
        flatFragment.setName(fragment.getName());
        flatFragment.setContent(content.substring(fragment.getStart(),fragment.getEnd()));
        flatFragment.setSource(fragment);
        return flatFragment;
    }

    public boolean isEmpty(Fragment fragment){
        return (fragment.getEnd()-fragment.getStart())<=fragment.getName().length();
    }
}
