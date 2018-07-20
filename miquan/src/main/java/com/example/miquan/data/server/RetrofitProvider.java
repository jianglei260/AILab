package com.example.miquan.data.server;

import com.example.miquan.MiQuanApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import retrofit2.Retrofit;

/**
 * Created by jianglei on 16/7/11.
 */

public class RetrofitProvider {

    public static String BASE_URl = "http://47.95.115.230:7070";
    public static String DEBUG_URl = "http://192.168.43.186:7070";

    public static Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(new ApiTypeAdapterFactory("data"))
            .create();

    public static Retrofit create() {
//        if (MiQuanApp.getInstance().isDebugVersion())
//            BASE_URl = DEBUG_URl;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URl).addConverterFactory(new CustomConverterFactory(gson)).build();
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URl).addConverterFactory(new CustomConverterFactory(handler, gson)).build();
        return retrofit;
    }

    public static Retrofit create(String url) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(new CustomConverterFactory(gson)).build();
        return retrofit;
    }

}
