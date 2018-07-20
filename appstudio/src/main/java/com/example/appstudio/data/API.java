package com.example.appstudio.data;

import com.example.appstudio.persistent.logic.Op;

import java.util.ArrayList;
import java.util.List;

public class API extends BaseObject {
    private int type;//server or local
    private String name;
    private String path;
    private List<Attr> params=new ArrayList<>();
    private String method;
    private String responseType;
    private List<Op> ops=new ArrayList<>();
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Attr> getParams() {
        return params;
    }

    public void setParams(List<Attr> params) {
        this.params = params;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public List<Op> getOps() {
        return ops;
    }

    public void setOps(List<Op> ops) {
        this.ops = ops;
    }
}
