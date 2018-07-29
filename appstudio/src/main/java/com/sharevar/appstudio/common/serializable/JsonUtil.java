package com.sharevar.appstudio.common.serializable;

import com.sharevar.appstudio.object.DynamicObject;
import com.sharevar.appstudio.object.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Map;

public class JsonUtil {


    public static <T> T fromJson(String json, java.lang.reflect.Type clazz){
        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.fromJson(json,clazz);
    }

    public static String toJson(Object object){
        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(object);
    }

    public static class DynamicObjectTypeAdapter extends TypeAdapter<DynamicObject> {
        private Gson gson;
        private String type;

        public Gson getGson() {
            return gson;
        }

        public void setGson(Gson gson) {
            this.gson = gson;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public DynamicObjectTypeAdapter() {
        }

        @Override
        public void write(JsonWriter out, DynamicObject value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.jsonValue(gson.toJson(value.attrs()));
        }

        @Override
        public DynamicObject read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            Map<String, Object> attrs = gson.fromJson(in.nextString(), Map.class);
            DynamicObject dynamicObject = new DynamicObject(Type.forName(type));
            dynamicObject.setAttrs(attrs);
            return dynamicObject;
        }
    }

}
