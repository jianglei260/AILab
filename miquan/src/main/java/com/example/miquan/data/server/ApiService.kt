package com.example.miquan.data.server

import com.example.miquan.model.Price
import com.example.miquan.model.Resource
import com.example.miquan.model.ResourceDetail
import com.example.miquan.model.User
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("/v1/user")
    fun login(@Field("phone") phone: String, @Field("code") code: String): Call<User>

    @FormUrlEncoded
    @POST("/v1/user/getcode")
    fun getCode(@Field("phone") phone: String): Call<Boolean>

    @GET("/v1/resource")
    fun resource(@Query("page") page: Int, @Query("token") token: String, @Query("limit") limit: Int): Call<List<Resource>>

    @GET("/v1/resource/{id}")
    fun resourceDetail(@Path("id") id: Int, @Query("token") token: String = ""): Call<ResourceDetail>

    @FormUrlEncoded
    @POST("/v1/order/vip")
    fun charge(@Field("token") token: String, @Field("type") type: Int): Call<String>

    @GET("/v1/config/price")
    fun priceConfig(): Call<Price>
}