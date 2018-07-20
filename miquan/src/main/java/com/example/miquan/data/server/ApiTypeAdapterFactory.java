package com.example.miquan.data.server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingzhihu on 15/5/7.
 */
public class ApiTypeAdapterFactory implements TypeAdapterFactory {
    private List<String> elements = new ArrayList<>();


    public ApiTypeAdapterFactory(String... dataElementName) {
        for (int i = 0; i < dataElementName.length; i++) {
            elements.add(dataElementName[i]);
        }
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementTypeAdapter = gson.getAdapter(JsonElement.class);


        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            @Override
            public T read(JsonReader in) throws IOException {
                JsonElement jsonElement = elementTypeAdapter.read(in);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.has("code")) {
                        int status = jsonObject.get("code").getAsInt();
                        String message = jsonObject.get("message").getAsString();
                        if (status == 200) {
                            //do nothing
                        } else {

                        }
                    }
                    for (String element : elements) {
                        if (jsonObject.has(element)) {
                            jsonElement = jsonObject.get(element);
                            break;
                        }
                    }
                }
                return delegate.fromJsonTree(jsonElement);
            }

        }.nullSafe();
    }
}
