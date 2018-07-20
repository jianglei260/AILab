package com.example.miquan.data

import android.util.Log
import com.example.miquan.data.server.RemoteDataSource
import com.example.miquan.model.Resource
import com.example.miquan.model.ResourceDetail


class ResouceRepository {
    companion object {
        val instance = ResouceRepository()
    }

    val cache = HashMap<Int, Resource>()
    val detailCache = HashMap<Int, ResourceDetail>()
    fun resouces(page: Int, token: String = "", limit: Int = 10): List<Resource>? {
        val result = RemoteDataSource.instance.resouces(page, token, limit)
        if (result != null) {
            result.forEach {
                cache.put(it.id, it)
            }
        }
        return result
    }

    fun getResourceFromCache(id: Int): Resource? = cache.get(id)


    fun getResourceDetailFromCache(id: Int): ResourceDetail? {
        val resourceDetail = detailCache.get(id)
        return resourceDetail
    }

    fun getResourceDetail(id: Int, token: String=""): ResourceDetail? {
        Log.d("getResourceDetail", id.toString())
        val resourceDetail = RemoteDataSource.instance.resourceDetail(id, token)
        if (resourceDetail != null)
            detailCache.put(id, resourceDetail)
        return resourceDetail
    }
}