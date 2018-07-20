package com.example.appstudio;


import com.example.appstudio.data.Project;
import com.example.appstudio.persistent.DataRepository;
import com.example.appstudio.persistent.remote.db.DataEngine;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testData(){
        DataRepository<Project> projectDataRepository=DataEngine.create(Project.class);
        Project project=new Project();
        project.setName("aaa");
        projectDataRepository.save(project);
    }
}