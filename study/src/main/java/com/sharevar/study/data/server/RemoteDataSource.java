package com.sharevar.study.data.server;

import com.sharevar.study.entity.Course;
import com.sharevar.study.entity.ResponseData;

import java.io.IOException;
import java.util.List;

public class RemoteDataSource {
    private APIService service;
    private static RemoteDataSource instance=new RemoteDataSource();

    public static RemoteDataSource getInstance() {
        return instance;
    }

    public RemoteDataSource() {
        service=RetrofitProvider.create().create(APIService.class);
    }

    public ResponseData<List<Course>> courseList(){
        try {
            return service.courseList().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseData<>();
        }
    }

    public ResponseData<String> fileContent(String path){
        try {
            return service.fileContent(path).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseData<>();
        }
    }
}
