package com.example.miquan.data

import android.content.Context
import android.text.TextUtils
import com.example.miquan.data.server.RemoteDataSource
import com.example.miquan.model.User
import com.google.gson.Gson
import java.io.*

class UserRepository {
    companion object {
        val instance = UserRepository()
    }

    var user: User? = null

    fun login(phone: String, smsCode: String, context: Context): User? {
        var user = RemoteDataSource.instance.login(phone, smsCode)
        if (user != null) {
            if (user.token != null)
                saveToLocal(context, user)
            else
                user = null
        }
        return user
    }

    fun getSmsCode(phone: String): Boolean? {
        return RemoteDataSource.instance.smsCode(phone)
    }

    fun getLocalToken(context: Context): User? {
        val gson = Gson()
        val authDataPath = context.filesDir.absolutePath + File.separator + "user.json"
        val authData = File(authDataPath)
        if (!authData.exists())
            return null
        try {
            val reader = FileReader(authData)
            return gson.fromJson<User>(reader, User::class.java!!)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    fun isLogin(context: Context): Boolean {
        val user = getCurrentUser(context) ?: return false
        if (user != null) {
            val epire = user!!.expire
            return if (epire <= System.currentTimeMillis() - 30 * 1000) false else !TextUtils.isEmpty(user.token)
        }
        return false
    }

    fun getCurrentUser(context: Context): User? {
        if (this.user == null)
            this.user = getLocalToken(context)
        return this.user
    }

    fun saveToLocal(context: Context, user: User) {
        val path = context.filesDir.absolutePath + File.separator + "user.json"
        val gson = Gson()
        val json = gson.toJson(user)
        val authData = File(path)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(authData, false)
            val data = json.toByteArray()
            fos.write(data, 0, data.size)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (fos != null)
                    fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    fun logout(context: Context): Boolean {
        val result = deleteToken(context)
        if (result) {
            this.user = null
        }
        return result
    }

    fun deleteToken(context: Context): Boolean {
        val authDataPath = context.filesDir.absolutePath + File.separator + "user.json"
        val authData = File(authDataPath)
        return if (!authData.exists())
            false
        else
            authData.delete()
    }


    fun charge(token: String, type: Int): String? {
        val payToken = RemoteDataSource.instance.charge(token, type)
        return payToken
    }

}