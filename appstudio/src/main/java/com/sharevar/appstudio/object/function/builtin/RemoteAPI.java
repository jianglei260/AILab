package com.sharevar.appstudio.object.function.builtin;

import com.sharevar.appstudio.common.serializable.JsonUtil;
import com.sharevar.appstudio.object.function.Function;
import com.sharevar.appstudio.object.function.Parameter;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class RemoteAPI extends Function {

    private String url;
    private String method;

    @Override
    public Object invoke() {
        if (method.compareToIgnoreCase("get") == 0) {
            return doGet();
        } else if (method.compareToIgnoreCase("post") == 0) {
            return doPost();
        }
        return null;
    }

    public Object doGet() {
        OkHttpClient client = new OkHttpClient();
        StringBuilder builder = new StringBuilder();
        builder.append(url);
        builder.append("?");
        for (Parameter parameter : parameters) {
            builder.append(parameter.getName());
            builder.append("=");
            builder.append(parameter.getValue());
            builder.append("&");
        }
        Request request = new Request.Builder()
                .url(builder.toString())
                .get()
                .build();
        try {
            okhttp3.Response response = client.newCall(request).execute();
            return JsonUtil.fromJson(response.body().string(), returnType.classType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object doPost() {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (Parameter parameter : parameters) {
            formBodyBuilder.add(parameter.getName(), JsonUtil.toJson(parameter.getValue()));
        }

        Request request = new Request.Builder()
                .url(url)
                .post(formBodyBuilder.build())
                .build();
        try {
            okhttp3.Response response = client.newCall(request).execute();
            return JsonUtil.fromJson(response.body().string(), returnType.classType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
