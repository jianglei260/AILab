package com.example.appstudio.persistent.remote.db;

import com.example.appstudio.persistent.DataRepository;

import java.lang.reflect.Proxy;

public class DataEngine {
    public static <T> DataRepository<T> create(Class<T> type) {
        RepositoryProxy proxy = new RepositoryProxy(type);
        DataRepository repository = (DataRepository) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{DataRepository.class}, proxy);
        return repository;
    }
}
