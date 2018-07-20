package com.example.miquan.model

import java.util.*

data class Resource(val id: Int, val title: String, val userId: Int, val createdAt: Date, val updatedAt: Date, val status: Int, val img: String, val avatarUrl: String)
data class ResourceDetail(val length: Int, val vip: Boolean, val buyer: Boolean, val content: List<String>)
data class User(val id: Int, val phone: String, val password: String, val token: String, val expire: Long, val nickName: String, val avatarUrl: String, val type: Int, val createdAt: Date, val updatedAt: Date, val status: Int, val vipData: String)
data class Order(val id: Int, val resourceId: Int, val userId: Int, val createdAt: Date, val updatedAt: Date, val status: Int)
data class Price(val month: String, val quarter: String, val year: String)
