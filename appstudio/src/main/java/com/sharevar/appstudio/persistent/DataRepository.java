package com.sharevar.appstudio.persistent;

import com.google.gson.JsonObject;
import com.sharevar.appstudio.data.Response;
import com.sharevar.appstudio.persistent.logic.Rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface DataRepository<T> {

    String save(JsonObject jsonObject, boolean inflate);
    List<String> saveAll(JsonObject jsonObject, boolean inflate) ;
    T findById(String objectId, boolean fetch) ;
    List<T> findAll(boolean fetch) ;
    List<T> findAllById(List<String> ids, boolean fetch);
    List<T> findByRules(List<Rule> rules, boolean fetch);
    Long count() ;
    boolean deleteById(String objectId,boolean fetch);
    Boolean deleteByRules(List<Rule> rules, boolean fetch);
    List<Boolean> deleteAll(List<String> ids,boolean fetch);
    Boolean deleteAll(boolean fetch);
}
