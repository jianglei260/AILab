package com.sharevar.appstudio.runtime.sdk.net;

import com.sharevar.appstudio.common.serializable.JsonUtil;
import com.sharevar.appstudio.object.function.Function;
import com.sharevar.appstudio.object.function.Mode;
import com.sharevar.appstudio.object.function.Parameter;
import com.sharevar.appstudio.runtime.sdk.FunctionAdapter;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class RemoteAPIAdapter extends FunctionAdapter
{
    private String url;
    private String method;

    public RemoteAPIAdapter(Function function) {
        super(function);
    }

    @Override
    public Object invoke() {
        for (Mode mode : function.getModes()) {
            if (mode.getName().equalsIgnoreCase("get")){
                return doGet(mode.getParameters());
            }else if (mode.getName().equalsIgnoreCase("post")){
                return doPost(mode.getParameters());
            }
        }
        return null;
    }

    public Object doGet(List<Parameter> parameters) {
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
            return JsonUtil.fromJson(response.body().string(), function.getReturnType().classType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object doPost(List<Parameter> parameters) {
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
            return JsonUtil.fromJson(response.body().string(), function.getReturnType().classType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
