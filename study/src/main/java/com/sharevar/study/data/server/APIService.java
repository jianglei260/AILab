package com.sharevar.study.data.server;

import com.sharevar.study.entity.Course;
import com.sharevar.study.entity.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("/course/list")
     Call<ResponseData<List<Course>>> courseList();
    @GET("/file/content")
    Call<ResponseData<String>> fileContent(@Query("name")String name);
}
