package com.sharevar.appstudio.persistent.remote.db;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.sharevar.appstudio.common.serializable.JsonUtil;
import com.sharevar.appstudio.context.ProjectContext;
import com.sharevar.appstudio.runtime.core.function.Function;
import com.sharevar.appstudio.runtime.core.function.Parameter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RepositoryProxy implements InvocationHandler {
    private static final String TAG = "RepositoryProxy";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private Class paramType;

    public RepositoryProxy(Class paramType) {
        this.paramType = paramType;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object responseData = doRequest(proxy, method, args);
        return responseData;
    }

    public Object doRequest(Object proxy, Method method, Object[] args) throws Exception {
        OkHttpClient client = new OkHttpClient();
        ProjectContext context = ProjectContext.getContext();
        String url = context.getServerPath()+"/data";
        Function function=new Function();
        function.setName(method.getName());
        function.setPath(paramType.getName());
        function.setReturnType(com.sharevar.appstudio.object.Type.of(method.getReturnType()));
        List<Parameter> parameters=new ArrayList<>();
        for (Object arg : args) {
            Parameter parameter=new Parameter();
            parameter.setName(arg.getClass().getSimpleName().toLowerCase());
            parameter.setType(com.sharevar.appstudio.object.Type.of(arg));
            parameter.setValue(arg);
            parameters.add(parameter);
        }
        function.setParameters(parameters);
        String jsonData = JsonUtil.toJson(function);
        RequestBody formBody = new FormBody.Builder()
                .add("function", jsonData)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        Class retType = method.getReturnType();
        String data=response.body().string();
        Log.d(TAG, "doRequest: "+data);
        Type tokenType= TypeToken.get(retType).getType();
        if (retType.equals(List.class)){
            tokenType= TypeToken.getParameterized(tokenType,paramType).getType();
        }
        Object responseData = JsonUtil.fromJson(data,tokenType);;

        return responseData;
    }


}
