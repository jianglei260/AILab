package com.sharevar.appstudio.stand;

import com.sharevar.appstudio.data.API;
import com.sharevar.appstudio.data.Attr;
import com.sharevar.appstudio.persistent.logic.Op;

import java.util.List;

public class APIBuilder {

    public API create(List<Op> ops, String name, String method, int type,String responseType) {
        API api = new API();
        api.setName(name);
        api.setMethod(method);
        api.setType(type);
        api.setOps(ops);
        api.setResponseType(responseType);
        for (Op op : ops) {
            Attr attr = op.getAttr();
            if (attr.getName() != null) {
                if (attr.getValue() != null) {
                    api.getParams().add(attr);
                }
            }
        }
        return api;
    }

    public void createAPI(API api){

    }

    private void createLocal(API api){

    }

    private void createServer(API api){

    }
}
