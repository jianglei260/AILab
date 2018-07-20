package com.example.miquan.data.server

import com.example.miquan.model.Price
import com.example.miquan.model.Resource
import com.example.miquan.model.ResourceDetail
import com.example.miquan.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RemoteDataSource {
    companion object {
        val instance = RemoteDataSource()
    }

    val service = RetrofitProvider.create().create(ApiService::class.java)

    init {
    }

    fun resouces(page: Int, token: String = "", limit: Int = 10): List<Resource>? {
        val response = service.resource(page, token, limit).execute()
        return response.body()
    }

    fun resourceDetail(id: Int, token: String = ""): ResourceDetail? {
        return service.resourceDetail(id, token).execute().body()
    }

    fun smsCode(phone: String): Boolean? {
        return service.getCode(phone).execute().body()
    }

    fun login(phone: String, code: String): User? {
        return service.login(phone, code).execute().body()
    }

    fun charge(token: String, type: Int): String? {
        return service.charge(token, type).execute().body()
    }

    fun priceConfig(): Price? {
        return service.priceConfig().execute().body()
    }
}