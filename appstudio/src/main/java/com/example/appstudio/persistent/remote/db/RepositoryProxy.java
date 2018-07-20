package com.example.appstudio.persistent.remote.db;

import com.example.appstudio.common.serializable.GsonUtil;
import com.example.appstudio.context.ProjectContext;
import com.example.appstudio.data.Response;
import com.example.appstudio.object.DynamicObject;
import com.example.appstudio.object.DynamicObjectRepository;
import com.example.appstudio.persistent.DataRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RepositoryProxy<T> implements InvocationHandler {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private Class<T> paramType;

    public RepositoryProxy(Class<T> paramType) {
        this.paramType = paramType;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object responseData = doRequest((DataRepository<T>) proxy, method, args);
        return responseData;
    }

    public Object doRequest(DataRepository<T> proxy, Method method, Object[] args) throws Exception {
        OkHttpClient client = new OkHttpClient();
        ProjectContext context = ProjectContext.getContext();
        String type = ((ParameterizedType) proxy.getClass().getGenericSuperclass()).getActualTypeArguments()[0].toString();
        String url = context.getServerPath();
        Map<String, Object> data = new HashMap<>();
        data.put("type", type);
        data.put("method", method.getName());
        data.put("params", args);
        String jsonData = GsonUtil.toJson(data);
        RequestBody formBody = new FormBody.Builder()
                .add("object", jsonData)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        Class retType = method.getReturnType();
        Object responseData = GsonUtil.fromJson(response.body().string(), retType);
        return responseData;
    }


}
