package com.example.miquan.data.server;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by jianglei on 16/7/28.
 */

public class CustomConverterFactory extends Converter.Factory {
    private Gson gson;

    public CustomConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        if (type == ResponseBody.class) {
            return StreamingResponseBodyConverter.INSTANCE;
        }
        if (type == Void.class) {
            return VoidResponseBodyConverter.INSTANCE;
        }
        if (type == String.class) {
            return StringConverter.INSTANCE;
        }
        if (type == Boolean.class)
            return new BooleanConverter();
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return RequestBodyConverter.INSTANCE;
    }


    static final class StringConverter implements Converter<ResponseBody, String> {
        static final StringConverter INSTANCE = new StringConverter();

        @Override
        public String convert(ResponseBody value) throws IOException {
            String result = value.string();
            try {
                Log.d("retrofit", result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.print(result);

            return result;
        }
    }

    static final class BooleanConverter implements Converter<ResponseBody, Boolean> {


        @Override
        public Boolean convert(ResponseBody value) throws IOException {
            try {
                String result = value.string();
                try {
                    Log.d("retrofit", result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.print(result);
                JSONObject json = new JSONObject(result);
                int code = json.getInt("code");
                String message = json.getString("message");
                if (code == 200)
                    return true;
                else {
                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    static final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final TypeAdapter<T> adapter;

        GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            String result = value.string();
            try {
                Log.d("retrofit", result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.print(result);
            Reader reader = new StringReader(result);
            JsonReader jsonReader = gson.newJsonReader(reader);
            try {
                return adapter.read(jsonReader);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                value.close();
            }
        }
    }

    static final class VoidResponseBodyConverter implements Converter<ResponseBody, Void> {
        static final VoidResponseBodyConverter INSTANCE = new VoidResponseBodyConverter();

        @Override
        public Void convert(ResponseBody value) throws IOException {
            String result = value.string();
            try {
                Log.d("retrofit", result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.print(result);
            value.close();
            return null;
        }
    }

    static final class RequestBodyConverter implements Converter<RequestBody, RequestBody> {
        static final RequestBodyConverter INSTANCE = new RequestBodyConverter();

        @Override
        public RequestBody convert(RequestBody value) throws IOException {
            try {
                Log.d("retrofit", value.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.print(value.toString());
            return value;
        }
    }

    static final class StreamingResponseBodyConverter
            implements Converter<ResponseBody, ResponseBody> {
        static final StreamingResponseBodyConverter INSTANCE = new StreamingResponseBodyConverter();

        @Override
        public ResponseBody convert(ResponseBody value) throws IOException {
            try {
                Log.d("retrofit", value.string());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.print(value.string());
            return value;
        }
    }

    static final class ToStringConverter implements Converter<Object, String> {
        static final ToStringConverter INSTANCE = new ToStringConverter();

        @Override
        public String convert(Object value) {
            try {
                Log.d("retrofit", value.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.print(value.toString());
            return value.toString();
        }
    }
}
