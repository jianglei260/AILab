package com.sharevar.appstudio.persistent.remote.db;

import com.sharevar.appstudio.persistent.DataRepository;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class DataEngine {
    private static Map<Class, DataRepository> repositoryMap = new HashMap<>();

    public static <T> DataRepository<T> create(Class<T> type) {
        DataRepository repository = repositoryMap.get(type);
        if (repository != null) {
            return repository;
        }
        RepositoryProxy proxy = new RepositoryProxy(type);
        repository = (DataRepository) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{DataRepository.class}, proxy);
        repositoryMap.put(type, repository);
        return repository;
    }
}
