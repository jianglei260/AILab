package com.example.appstudio.persistent;

import com.example.appstudio.data.Response;

import java.util.Optional;

public interface DataRepository<T> {

    <S extends T> Response<S> save(S var1);

    <S extends T> Response<Iterable<S>> saveAll(Iterable<S> var1);

    Response<T> findById(Object var1);

    Response<Boolean> existsById(Object var1);

    Response<Iterable<T>> findAll();

    Response<Iterable<T>> findAllById(Iterable<Object> var1);

    Response<Long> count();

    Response deleteById(Object var1);

    Response delete(T var1);

    Response deleteAll(Iterable<? extends T> var1);

    Response deleteAll();

}
